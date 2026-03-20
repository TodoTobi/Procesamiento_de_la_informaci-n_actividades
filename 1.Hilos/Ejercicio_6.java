/*
 * ============================================================
 *  EJECUTORES (ExecutorService) - FixedThreadPool
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa ejecuta múltiples tareas en paralelo utilizando
 *  un pool de hilos administrado por ExecutorService.
 *
 *  PROBLEMA:
 *  Crear y manejar hilos manualmente (Thread) no es eficiente
 *  ni escalable en aplicaciones reales.
 *
 *  SOLUCIÓN:
 *  Se utiliza ExecutorService con un FixedThreadPool(3), lo que
 *  permite reutilizar hilos y controlar la concurrencia.
 *
 *  FUNCIONAMIENTO:
 *  - Se crean 5 tareas
 *  - Solo 3 se ejecutan al mismo tiempo
 *  - Las demás esperan en cola
 *
 *  CONCEPTOS CLAVE:
 *  - ExecutorService
 *  - Thread Pool
 *  - Runnable
 *  - Gestión de concurrencia
 *
 * ============================================================
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// ============================================================
// CLASE: Tarea
// Representa una unidad de trabajo que será ejecutada
// ============================================================
class Tarea implements Runnable {

    private int numero;

    // Constructor que identifica la tarea
    public Tarea(int numero) {
        this.numero = numero;
    }

    /*
     * MÉTODO: run
     * ----------------------------------------
     * Define qué hace cada tarea cuando es ejecutada.
     */
    @Override
    public void run() {

        // Mensaje inicial de ejecución
        System.out.println("Ejecutando tarea " + numero +
                " en " + Thread.currentThread().getName());

        try {
            // Simula una operación costosa (2 segundos)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Error en la tarea " + numero);
        }

        // Mensaje final
        System.out.println("Finalizó tarea " + numero +
                " en " + Thread.currentThread().getName());
    }
}


// ============================================================
// CLASE PRINCIPAL
// ============================================================
public class Ejercicio_6 {

    public static void main(String[] args) {

        /*
         * Creación del ExecutorService
         * ----------------------------------------
         * FixedThreadPool(3):
         * - Máximo 3 hilos ejecutándose al mismo tiempo
         * - Las tareas extra esperan en cola
         */
        ExecutorService executor = Executors.newFixedThreadPool(3);

        /*
         * Envío de tareas al pool
         * ----------------------------------------
         * Se crean 5 tareas, pero solo 3 se ejecutarán en paralelo.
         */
        for (int i = 1; i <= 5; i++) {
            executor.execute(new Tarea(i));
        }

        /*
         * Cierre del ExecutorService
         * ----------------------------------------
         * No acepta nuevas tareas, pero deja terminar las actuales.
         */
        executor.shutdown();
    }
}