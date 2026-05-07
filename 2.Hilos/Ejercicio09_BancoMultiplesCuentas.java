/**
 * EJERCICIO 9 - Banco con Múltiples Cuentas
 * ===========================================
 * Objetivo: Simular cuentas bancarias con hilos que transfieren dinero.
 * Evitar inconsistencias con sincronización. Detectar y prevenir deadlock.
 *
 * ¿Qué es un Deadlock?
 * ─────────────────────
 * Ocurre cuando dos o más hilos se bloquean mutuamente esperando un
 * recurso que el otro tiene. Ejemplo clásico:
 *   Hilo-A bloquea Cuenta1 y espera Cuenta2.
 *   Hilo-B bloquea Cuenta2 y espera Cuenta1.
 *   → Ninguno puede avanzar. Ciclo de espera infinito.
 *
 * Solución (orden canónico de bloqueo):
 *   Siempre adquirir los locks en el MISMO orden (por ej. por ID ascendente).
 *   Así nunca puede formarse el ciclo de espera.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio09_BancoMultiplesCuentas {

    // ══════════════════════════════════════════════════════════════════════════
    // Clase CuentaBancaria
    // ══════════════════════════════════════════════════════════════════════════
    static class CuentaBancaria {

        private final int id;         // Identificador único de la cuenta
        private double saldo;         // Saldo actual (variable compartida)

        public CuentaBancaria(int id, double saldoInicial) {
            this.id = id;
            this.saldo = saldoInicial;
        }

        // Getter del id (útil para ordenar locks)
        public int getId() { return id; }

        // Getter del saldo (debe usarse dentro de un bloque sincronizado)
        public double getSaldo() { return saldo; }

        /**
         * Deposita dinero en la cuenta.
         * synchronized(this) garantiza que solo un hilo modifica el saldo a la vez.
         */
        public synchronized void depositar(double monto) {
            saldo += monto; // Modifica el saldo de forma segura
        }

        /**
         * Extrae dinero de la cuenta si hay saldo suficiente.
         * @return true si la extracción fue exitosa, false si no hay fondos.
         */
        public synchronized boolean extraer(double monto) {
            if (saldo >= monto) {
                saldo -= monto; // Reduce el saldo de forma segura
                return true;    // Extracción exitosa
            }
            return false; // Fondos insuficientes
        }

        @Override
        public String toString() {
            return "Cuenta#" + id + "(saldo=$" + String.format("%.2f", saldo) + ")";
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Clase Banco: contiene la lógica de transferencia segura
    // ══════════════════════════════════════════════════════════════════════════
    static class Banco {

        /**
         * Transferencia SEGURA (sin deadlock).
         *
         * Estrategia anti-deadlock: "orden canónico de bloqueo".
         * Siempre adquirimos el lock de la cuenta con MENOR id primero.
         * Como todos los hilos siguen el mismo orden, no puede formarse
         * un ciclo de espera circular → deadlock imposible.
         *
         * @param origen   Cuenta de donde sale el dinero.
         * @param destino  Cuenta que recibe el dinero.
         * @param monto    Cantidad a transferir.
         */
        public void transferirSeguro(CuentaBancaria origen, CuentaBancaria destino, double monto) {

            // Determinamos qué cuenta lockear primero según su ID
            CuentaBancaria primerLock  = (origen.getId() < destino.getId()) ? origen  : destino;
            CuentaBancaria segundoLock = (origen.getId() < destino.getId()) ? destino : origen;

            // Adquirimos los locks en orden ascendente de ID (siempre el mismo orden)
            synchronized (primerLock) {          // Lock del menor ID primero
                synchronized (segundoLock) {     // Lock del mayor ID después

                    // Aquí tenemos ambos locks → operación atómica de transferencia
                    if (origen.getSaldo() >= monto) {
                        origen.extraer(monto);       // Debitamos de origen
                        destino.depositar(monto);    // Acreditamos en destino
                        System.out.printf("[%s] Transferencia: $%.2f | %s → %s%n",
                                Thread.currentThread().getName(), monto, origen, destino);
                    } else {
                        System.out.printf("[%s] FONDOS INSUFICIENTES: %s quiso enviar $%.2f%n",
                                Thread.currentThread().getName(), origen, monto);
                    }
                } // Libera segundoLock
            } // Libera primerLock
        }

        /**
         * Transferencia INSEGURA (puede causar deadlock si se activa).
         * SOLO para demostración educativa. NO usar en producción.
         *
         * Esta versión lockea en el orden "natural" (origen primero, destino después),
         * lo cual puede generar deadlock si:
         *   Hilo-A hace: sync(cuenta1) { sync(cuenta2) { ... } }
         *   Hilo-B hace: sync(cuenta2) { sync(cuenta1) { ... } }
         * → A espera que B libere cuenta2, B espera que A libere cuenta1 → DEADLOCK.
         */
        public void transferirInsegura(CuentaBancaria origen, CuentaBancaria destino, double monto) {
            synchronized (origen) {              // Lockea origen primero (orden arbitrario)
                // Simulamos un pequeño delay para aumentar chances de deadlock
                try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                synchronized (destino) {         // Lockea destino después
                    if (origen.getSaldo() >= monto) {
                        origen.extraer(monto);
                        destino.depositar(monto);
                    }
                }
            }
        }
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== BANCO CON MÚLTIPLES CUENTAS ===\n");

        // Creamos 3 cuentas bancarias con saldos iniciales
        CuentaBancaria cuenta1 = new CuentaBancaria(1, 1000.0);
        CuentaBancaria cuenta2 = new CuentaBancaria(2, 1500.0);
        CuentaBancaria cuenta3 = new CuentaBancaria(3, 800.0);

        Banco banco = new Banco(); // Instancia del banco (lógica de transferencia)

        // ── Mostramos saldos iniciales ────────────────────────────────────────
        System.out.println("Saldos iniciales:");
        System.out.println("  " + cuenta1);
        System.out.println("  " + cuenta2);
        System.out.println("  " + cuenta3);
        System.out.println();

        // ── Creamos hilos que hacen transferencias cruzadas ───────────────────
        // Hilo 1: transfiere de cuenta1 → cuenta2 y de cuenta2 → cuenta3
        Thread hilo1 = new Thread(() -> {
            banco.transferirSeguro(cuenta1, cuenta2, 200.0);
            banco.transferirSeguro(cuenta2, cuenta3, 300.0);
            banco.transferirSeguro(cuenta3, cuenta1, 100.0);
        }, "Hilo-Banco-1");

        // Hilo 2: transfiere en sentido contrario (cuenta2 → cuenta1, etc.)
        // Sin orden canónico esto causaría deadlock con Hilo-1.
        Thread hilo2 = new Thread(() -> {
            banco.transferirSeguro(cuenta2, cuenta1, 150.0); // Orden invertido vs hilo1
            banco.transferirSeguro(cuenta3, cuenta2, 250.0);
            banco.transferirSeguro(cuenta1, cuenta3, 50.0);
        }, "Hilo-Banco-2");

        // Hilo 3: más transferencias cruzadas
        Thread hilo3 = new Thread(() -> {
            banco.transferirSeguro(cuenta1, cuenta3, 400.0);
            banco.transferirSeguro(cuenta3, cuenta2, 120.0);
        }, "Hilo-Banco-3");

        // Lanzamos todos los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // Esperamos a que todos terminen
        hilo1.join();
        hilo2.join();
        hilo3.join();

        // ── Saldos finales ────────────────────────────────────────────────────
        double saldoFinal = cuenta1.getSaldo() + cuenta2.getSaldo() + cuenta3.getSaldo();
        System.out.println("\nSaldos finales:");
        System.out.println("  " + cuenta1);
        System.out.println("  " + cuenta2);
        System.out.println("  " + cuenta3);
        System.out.printf("  Total: $%.2f (inicial: $3300.00) → ¿Consistente? %s%n",
                saldoFinal, saldoFinal == 3300.0 ? "✓ SÍ" : "✗ NO (error!)");

        System.out.println();
        System.out.println("=== EXPLICACIÓN ANTI-DEADLOCK ===");
        System.out.println("Clave: siempre adquirimos locks en orden ascendente de ID de cuenta.");
        System.out.println("Esto rompe el ciclo de dependencia circular que causa deadlock.");
    }
}