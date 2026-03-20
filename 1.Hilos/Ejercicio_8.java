  /*
 * ============================================================
 *  PREVENCIÓN DE DEADLOCKS EN JAVA
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa muestra:
 *   1. Cómo se produce un DEADLOCK
 *   2. Cómo solucionarlo cambiando el orden de los bloqueos
 *
 *  ¿QUÉ ES UN DEADLOCK?
 *  Es una situación donde dos hilos quedan bloqueados
 *  indefinidamente porque:
 *   - Hilo A tiene recurso 1 y espera recurso 2
 *   - Hilo B tiene recurso 2 y espera recurso 1
 *
 *  RESULTADO:
 *   ❌ Ninguno avanza
 *   ❌ El programa se "congela"
 *
 *  SOLUCIÓN:
 *   ✔ Mantener un orden consistente al adquirir locks
 *
 * ============================================================
 */


// ============================================================
// CLASE: Recurso
// Representa un recurso compartido
// ============================================================
class Recurso {

    private String nombre;

    public Recurso(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}


// ============================================================
// CLASE: TareaDeadlock
// Simula un deadlock (orden incorrecto de bloqueos)
// ============================================================
class TareaDeadlock implements Runnable {

    private Recurso r1;
    private Recurso r2;

    public TareaDeadlock(Recurso r1, Recurso r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public void run() {

        // Primer bloqueo
        synchronized (r1) {
            System.out.println(Thread.currentThread().getName() +
                    " bloqueó " + r1.getNombre());

            try {
                Thread.sleep(500); // Simula trabajo
            } catch (InterruptedException e) {}

            // Segundo bloqueo (puede causar deadlock)
            System.out.println(Thread.currentThread().getName() +
                    " intenta bloquear " + r2.getNombre());

            synchronized (r2) {
                System.out.println(Thread.currentThread().getName() +
                        " bloqueó " + r2.getNombre());
            }
        }
    }
}


// ============================================================
// CLASE: TareaSinDeadlock
// Solución: mismo orden de bloqueo para todos los hilos
// ============================================================
class TareaSinDeadlock implements Runnable {

    private Recurso r1;
    private Recurso r2;

    public TareaSinDeadlock(Recurso r1, Recurso r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public void run() {

        // ORDEN FIJO: siempre r1 -> r2
        synchronized (r1) {
            System.out.println(Thread.currentThread().getName() +
                    " bloqueó " + r1.getNombre());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}

            synchronized (r2) {
                System.out.println(Thread.currentThread().getName() +
                        " bloqueó " + r2.getNombre());
            }
        }
    }
}


// ============================================================
// CLASE PRINCIPAL
// ============================================================
public class Ejercicio_8 {

    public static void main(String[] args) {

        // Creación de recursos compartidos
        Recurso recurso1 = new Recurso("Recurso 1");
        Recurso recurso2 = new Recurso("Recurso 2");

        /*
         * ====================================================
         *  EJEMPLO 1: DEADLOCK (comentado para evitar bloqueo)
         * ====================================================
         *
         * Descomentar para ver el problema:
         */

        /*
        Thread hilo1 = new Thread(new TareaDeadlock(recurso1, recurso2), "Hilo 1");
        Thread hilo2 = new Thread(new TareaDeadlock(recurso2, recurso1), "Hilo 2");

        hilo1.start();
        hilo2.start();
        */

        /*
         * ====================================================
         *  EJEMPLO 2: SOLUCIÓN (SIN DEADLOCK)
         * ====================================================
         *
         * Ambos hilos adquieren los recursos en el mismo orden
         */

        Thread hilo1 = new Thread(new TareaSinDeadlock(recurso1, recurso2), "Hilo 1");
        Thread hilo2 = new Thread(new TareaSinDeadlock(recurso1, recurso2), "Hilo 2");

        hilo1.start();
        hilo2.start();
    }
}