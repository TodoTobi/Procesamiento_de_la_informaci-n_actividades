/*
 * ============================================================
 *  EJERCICIO 2 - HILOS EN PARALELO (NÚMEROS Y LETRAS)
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa crea dos hilos que se ejecutan en paralelo:
 *   - Un hilo imprime números del 1 al 5
 *   - Otro hilo imprime letras de la A a la E
 *
 *  OBJETIVO:
 *  Comprender la ejecución concurrente de múltiples hilos
 *  y cómo sus salidas pueden intercalarse.
 *
 *  CONCEPTOS CLAVE:
 *  - Thread
 *  - Concurrencia (ejecución en paralelo)
 *  - Método run()
 *  - Método start()
 *  - Thread.sleep()
 *
 *  IMPORTANTE:
 *  El orden de salida NO está garantizado, ya que depende
 *  del planificador de la JVM.
 *
 * ============================================================
 */


// ============================================================
// CLASE: HiloContador
// Imprime números del 1 al 5
// ============================================================
class HiloContador extends Thread {

    /*
     * MÉTODO: run
     * ----------------------------------------
     * Define la tarea del hilo: imprimir números.
     */
    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {

            // Muestra el número actual
            System.out.println(i);

            try {
                // Pausa de 500 ms entre cada número
                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido");
            }
        }
    }
}


// ============================================================
// CLASE: HiloContadorLetra
// Imprime letras de la A a la E
// ============================================================
class HiloContadorLetra extends Thread {

    /*
     * MÉTODO: run
     * ----------------------------------------
     * Define la tarea del hilo: imprimir letras.
     */
    @Override
    public void run() {

        // Variable tipo char que comienza en 'A'
        char letra = 'A';

        for (int i = 1; i <= 5; i++) {

            // Muestra la letra actual
            System.out.println(letra);

            // Incrementa la letra (A → B → C → ...)
            letra++;

            try {
                // Pausa de 500 ms entre cada impresión
                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido");
            }
        }
    }
}


// ============================================================
// CLASE PRINCIPAL
// ============================================================
public class Ejercico_2 {

    public static void main(String[] args) {

        /*
         * Creación de los hilos
         * ----------------------------------------
         * Se instancian ambas clases que extienden Thread.
         */
        HiloContador miHilo = new HiloContador();
        HiloContadorLetra miHiloLetra = new HiloContadorLetra();

        /*
         * Inicio de ejecución
         * ----------------------------------------
         * Ambos hilos se ejecutan en paralelo.
         */
        miHilo.start();
        miHiloLetra.start();

        /*
         * IMPORTANTE:
         * No hay control sobre cuál hilo imprime primero.
         * La salida puede variar en cada ejecución.
         */
    }
}