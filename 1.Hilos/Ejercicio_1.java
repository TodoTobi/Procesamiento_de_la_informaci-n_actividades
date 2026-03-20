/*
 * ============================================================
 *  EJERCICIO 1 - CREACIÓN DE UN HILO BÁSICO EN JAVA
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa crea un hilo que imprime números del 1 al 10
 *  con una pausa de 1 segundo entre cada impresión.
 *
 *  OBJETIVO:
 *  Introducir el uso de hilos (Thread) en Java y comprender
 *  cómo ejecutar tareas en paralelo.
 *
 *  CONCEPTOS CLAVE:
 *  - Thread (hilos)
 *  - Método run()
 *  - Método start()
 *  - Thread.sleep()
 *
 *  DIFERENCIA IMPORTANTE:
 *  - run()   → ejecuta el código en el mismo hilo (NO concurrente)
 *  - start() → crea un nuevo hilo (SÍ concurrente)
 *
 * ============================================================
 */


// ============================================================
// CLASE: HiloContador
// Representa un hilo que ejecuta una tarea en paralelo
// ============================================================
class HiloContador extends Thread {

    /*
     * MÉTODO: run
     * ----------------------------------------
     * Contiene la lógica que ejecutará el hilo.
     * Se ejecuta automáticamente al llamar a start().
     */
    @Override
    public void run() {

        // Bucle que imprime números del 1 al 10
        for (int i = 1; i <= 10; i++) {

            // Muestra el número actual
            System.out.println("Número: " + i);

            try {
                /*
                 * Pausa la ejecución del hilo durante 1 segundo
                 * (1000 milisegundos)
                 */
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                // Manejo de interrupciones del hilo
                System.out.println("El hilo fue interrumpido");
            }
        }
    }
}


// ============================================================
// CLASE PRINCIPAL
// Punto de entrada del programa
// ============================================================
public class Ejercicio_1 {

    public static void main(String[] args) {

        /*
         * Creación del hilo
         * ----------------------------------------
         * Se instancia la clase que extiende Thread
         */
        HiloContador miHilo = new HiloContador();

        /*
         * Inicio del hilo
         * ----------------------------------------
         * start() crea un nuevo hilo y ejecuta run()
         * en paralelo al hilo principal (main).
         */
        miHilo.start();

        /*
         * IMPORTANTE:
         * El hilo principal (main) continúa ejecutándose
         * sin esperar a que termine miHilo.
         */
    }
}