/*
 * ============================================================
 *  SIMULACIÓN DE CAJEROS AUTOMÁTICOS CON HILOS (THREADS)
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa simula un sistema bancario donde múltiples
 *  cajeros automáticos (hilos) intentan retirar dinero de una
 *  cuenta bancaria compartida.
 *
 *  PROBLEMA A RESOLVER:
 *  Cuando varios hilos acceden al mismo recurso (saldo),
 *  pueden producirse inconsistencias (ej: saldo negativo).
 *
 *  SOLUCIÓN:
 *  Se utiliza la palabra clave 'synchronized' para asegurar
 *  que solo un hilo pueda ejecutar la operación de retiro
 *  a la vez.
 *
 *  CONCEPTOS CLAVE:
 *  - Hilos (Thread)
 *  - Interfaz Runnable
 *  - Recurso compartido
 *  - Sincronización (synchronized)
 *  - Condición de carrera (race condition)
 *
 * ============================================================
 */


// ============================================================
// CLASE: CuentaBancaria
// Representa el recurso compartido (saldo) entre los hilos.
// ============================================================
class CuentaBancaria {

    // Saldo disponible en la cuenta (recurso crítico)
    private int saldo;

    // Constructor que inicializa el saldo
    public CuentaBancaria(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    /*
     * MÉTODO: retirar
     * ----------------------------------------
     * Permite retirar dinero de la cuenta.
     *
     * synchronized:
     * Garantiza que solo un hilo a la vez pueda ejecutar
     * este método, evitando accesos simultáneos al saldo.
     *
     * FLUJO:
     * 1. Verifica si hay saldo suficiente
     * 2. Simula el tiempo de operación (sleep)
     * 3. Realiza la resta del saldo
     * 4. Muestra el resultado
     */
    public synchronized void retirar(int monto) {

        // Validación de saldo disponible
        if (saldo >= monto) {

            // Mensaje previo a la operación (intención)
            System.out.println(Thread.currentThread().getName() +
                    " intenta retirar $" + monto);

            try {
                // Simula el tiempo que tarda una operación real
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error en la ejecución del hilo");
            }

            // Sección crítica: modificación del recurso compartido
            saldo -= monto;

            // Resultado de la operación
            System.out.println(Thread.currentThread().getName() +
                    " retiró $" + monto +
                    " | Saldo restante: $" + saldo);

        } else {
            // Caso donde no hay saldo suficiente
            System.out.println(Thread.currentThread().getName() +
                    " NO pudo retirar $" + monto +
                    " | Saldo insuficiente: $" + saldo);
        }
    }
}


// ============================================================
// CLASE: Cajero
// Representa la tarea que ejecutará cada hilo.
// Implementa Runnable (buena práctica en Java).
// ============================================================
class Cajero implements Runnable {

    // Referencia a la cuenta compartida
    private CuentaBancaria cuenta;

    // Constructor que recibe la cuenta
    public Cajero(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    /*
     * MÉTODO: run
     * ----------------------------------------
     * Define el comportamiento del hilo.
     *
     * Cada cajero intenta retirar dinero múltiples veces.
     *
     * IMPORTANTE:
     * El hilo NO maneja el saldo directamente,
     * solo invoca el método sincronizado de la cuenta.
     */
    @Override
    public void run() {

        // Cada cajero realiza 3 intentos de retiro
        for (int i = 0; i < 3; i++) {

            // Se solicita retirar $100 en cada intento
            cuenta.retirar(100);
        }
    }
}


// ============================================================
// CLASE PRINCIPAL: Main
// Punto de entrada del programa.
// ============================================================
public class Main {

    public static void main(String[] args) {

        // Creación de la cuenta compartida con saldo inicial
        CuentaBancaria cuenta = new CuentaBancaria(500);

        /*
         * Creación de hilos (cajeros automáticos)
         * Todos comparten la misma cuenta bancaria.
         */
        Thread cajero1 = new Thread(new Cajero(cuenta), "Cajero 1");
        Thread cajero2 = new Thread(new Cajero(cuenta), "Cajero 2");
        Thread cajero3 = new Thread(new Cajero(cuenta), "Cajero 3");

        /*
         * Inicio de ejecución de los hilos
         * IMPORTANTE:
         * No hay orden garantizado en la ejecución.
         * La JVM y el sistema operativo deciden.
         */
        cajero1.start();
        cajero2.start();
        cajero3.start();
    }
}