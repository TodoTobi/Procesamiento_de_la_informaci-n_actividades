/**
 * EJERCICIO 13 - Deadlock: Provocar y Solucionar
 * =================================================
 * Objetivo: Demostrar un deadlock con 2 recursos y 2 hilos.
 * Primero provocarlo intencionalmente, luego mostrar la solución.
 *
 * ¿Qué es un Deadlock?
 * ─────────────────────
 * Situación donde dos o más hilos están bloqueados permanentemente,
 * cada uno esperando un recurso que el otro tiene.
 * Condiciones de Coffman (las 4 deben ocurrir simultáneamente):
 *   1. Exclusión mutua: los recursos son de uso exclusivo.
 *   2. Retención y espera: un hilo retiene un recurso mientras espera otro.
 *   3. No apropiación: los recursos no pueden quitarse forzosamente.
 *   4. Espera circular: A espera recurso de B, B espera recurso de A.
 *
 * Solución (rompe la condición 4 - espera circular):
 *   Establecer un ORDEN GLOBAL de adquisición de locks. Si todos los hilos
 *   adquieren los recursos en el mismo orden, no puede formarse un ciclo.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio13_DeadlockSolucion {

    // ── Los dos recursos compartidos (cualquier objeto puede ser un lock) ─────
    // En Java, cualquier objeto tiene un "monitor" que puede usarse como lock.
    // Usamos objetos simples String para representar los recursos.
    static final Object recursoA = new Object(); // Recurso A (ej: impresora)
    static final Object recursoB = new Object(); // Recurso B (ej: escáner)

    // ══════════════════════════════════════════════════════════════════════════
    // PARTE 1: VERSIÓN CON DEADLOCK (comentada para no bloquear el programa)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Simula el deadlock: Hilo1 bloquea A y espera B, Hilo2 bloquea B y espera A.
     * ADVERTENCIA: este método crea un deadlock permanente.
     * Está comentado en el main para no bloquear el programa.
     */
    static void demostrarDeadlock() {

        // Hilo 1: adquiere A primero, luego intenta adquirir B
        Thread hilo1 = new Thread(() -> {
            synchronized (recursoA) { // Hilo1 bloquea recursoA
                System.out.println("[Hilo1] Adquirió recursoA. Intentando adquirir recursoB...");
                try { Thread.sleep(100); } // Pausa para que Hilo2 tome recursoB
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                synchronized (recursoB) { // ← Hilo1 se BLOQUEA aquí (Hilo2 tiene recursoB)
                    System.out.println("[Hilo1] Adquirió ambos recursos."); // NUNCA llega aquí
                }
            }
        }, "Hilo1-Deadlock");

        // Hilo 2: adquiere B primero, luego intenta adquirir A
        Thread hilo2 = new Thread(() -> {
            synchronized (recursoB) { // Hilo2 bloquea recursoB
                System.out.println("[Hilo2] Adquirió recursoB. Intentando adquirir recursoA...");
                try { Thread.sleep(100); } // Pausa para que Hilo1 tome recursoA
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                synchronized (recursoA) { // ← Hilo2 se BLOQUEA aquí (Hilo1 tiene recursoA)
                    System.out.println("[Hilo2] Adquirió ambos recursos."); // NUNCA llega aquí
                }
            }
        }, "Hilo2-Deadlock");

        hilo1.start();
        hilo2.start();

        // Resultado: DEADLOCK. Ambos hilos esperan para siempre.
        // Hilo1: tiene A, espera B.
        // Hilo2: tiene B, espera A.
        // → Ciclo de espera circular → deadlock.
    }

    // ══════════════════════════════════════════════════════════════════════════
    // PARTE 2: VERSIÓN SIN DEADLOCK (solución con orden canónico)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Solución: ambos hilos adquieren los recursos en el MISMO orden
     * (siempre A primero, luego B). Esto rompe la espera circular.
     */
    static void sinDeadlock() throws InterruptedException {

        // Hilo 1: adquiere A primero, luego B (orden: A → B)
        Thread hilo1 = new Thread(() -> {
            synchronized (recursoA) { // Lock A primero (SIEMPRE)
                System.out.println("[Hilo1] Adquirió recursoA.");
                try { Thread.sleep(100); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                synchronized (recursoB) { // Lock B segundo (SIEMPRE)
                    System.out.println("[Hilo1] ✓ Adquirió recursoB. Usando ambos recursos...");
                    try { Thread.sleep(200); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    System.out.println("[Hilo1] ✓ Terminó. Liberando ambos recursos.");
                }
            }
        }, "Hilo1-Seguro");

        // Hilo 2: TAMBIÉN adquiere A primero, luego B (mismo orden: A → B)
        // Antes tenía orden B → A (que causaba deadlock). Ahora es A → B.
        Thread hilo2 = new Thread(() -> {
            synchronized (recursoA) { // Lock A primero (IGUAL que Hilo1)
                System.out.println("[Hilo2] Adquirió recursoA.");
                try { Thread.sleep(100); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                synchronized (recursoB) { // Lock B segundo (IGUAL que Hilo1)
                    System.out.println("[Hilo2] ✓ Adquirió recursoB. Usando ambos recursos...");
                    try { Thread.sleep(200); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    System.out.println("[Hilo2] ✓ Terminó. Liberando ambos recursos.");
                }
            }
        }, "Hilo2-Seguro");

        // ¿Por qué no hay deadlock?
        // Si Hilo1 toma recursoA, Hilo2 queda bloqueado en recursoA (no en recursoB).
        // Hilo1 puede continuar y tomar recursoB sin que nadie se lo impida.
        // Hilo1 termina y libera ambos. Luego Hilo2 adquiere A y B en orden.
        // → No hay espera circular → NO hay deadlock.

        hilo1.start();
        hilo2.start();

        hilo1.join(); // Esperamos a que Hilo1 termine
        hilo2.join(); // Esperamos a que Hilo2 termine
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== EJERCICIO DEADLOCK ===\n");

        // ──────────────────────────────────────────────────────────────────────
        // PARTE A: DEADLOCK INTENCIONAL (solo explicación, código no ejecutado)
        // ──────────────────────────────────────────────────────────────────────
        System.out.println("--- PARTE A: Código con DEADLOCK ---");
        System.out.println("(Código disponible en demostrarDeadlock() - NO ejecutado para no bloquear el programa)");
        System.out.println();
        System.out.println("Hilo1: sync(recursoA) { sync(recursoB) { ... } }  → Orden: A → B");
        System.out.println("Hilo2: sync(recursoB) { sync(recursoA) { ... } }  → Orden: B → A");
        System.out.println("→ Hilo1 tiene A, espera B.");
        System.out.println("→ Hilo2 tiene B, espera A.");
        System.out.println("→ DEADLOCK: ciclo de espera circular permanente.");
        System.out.println();

        // Si quisieras ver el deadlock en acción (el programa se colgará), descomenta:
        // demostrarDeadlock();

        // ──────────────────────────────────────────────────────────────────────
        // PARTE B: SOLUCIÓN SIN DEADLOCK
        // ──────────────────────────────────────────────────────────────────────
        System.out.println("--- PARTE B: Código SIN deadlock (solución) ---");
        System.out.println("Ambos hilos adquieren locks en el MISMO orden: siempre A → B.");
        System.out.println();

        sinDeadlock(); // Ejecutamos la versión segura

        System.out.println();
        System.out.println("=== CONCLUSIÓN ===");
        System.out.println("Solución aplicada: Orden canónico de bloqueo (Lock Ordering).");
        System.out.println("Regla: si necesitás múltiples locks, siempre adquirilos en");
        System.out.println("el mismo orden predefinido en TODOS los hilos del programa.");
        System.out.println("Esto rompe la condición de 'espera circular' de Coffman.");
        System.out.println("Otras soluciones: tryLock() con timeout, usar un solo lock,");
        System.out.println("o rediseñar para evitar la necesidad de múltiples locks.");
    }
}