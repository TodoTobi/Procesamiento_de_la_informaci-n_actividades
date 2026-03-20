/*
 * ============================================================
 *  EJERCICIO - CONTADOR COMPARTIDO CON SYNCHRONIZED
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa utiliza dos hilos que incrementan un contador
 *  compartido. Se usa 'synchronized' para evitar problemas de
 *  concurrencia (condiciones de carrera).
 *
 *  PROBLEMA:
 *  Cuando varios hilos acceden a una misma variable al mismo
 *  tiempo, pueden generarse resultados incorrectos.
 *
 *  SOLUCIÓN:
 *  Se utiliza 'synchronized' para asegurar que solo un hilo
 *  a la vez pueda modificar el contador.
 *
 *  CONCEPTOS CLAVE:
 *  - Recurso compartido
 *  - Sincronización (synchronized)
 *  - Runnable
 *  - Thread
 *  - Encapsulamiento (private + métodos públicos)
 *
 * ============================================================
 */


// ============================================================
// CLASE: Contador
// Representa el recurso compartido entre los hilos
// ============================================================
class Contador {

    // Variable privada: solo accesible dentro de la clase
    private int valor = 0;

    /*
     * MÉTODO: incrementar
     * ----------------------------------------
     * Incrementa el contador de forma segura.
     *
     * synchronized:
     * Garantiza que solo un hilo a la vez pueda ejecutar
     * este método, evitando inconsistencias.
     */
    public synchronized void incrementar() {

        // Sección crítica (acceso al recurso compartido)
        valor++;

        // Muestra el valor actual del contador
        System.out.println(
            Thread.currentThread().getName() +
            " -> contador: " + valor
        );
    }
}


// ============================================================
// CLASE: TareaContador
// Define la tarea que ejecutarán los hilos
// ============================================================
class TareaContador implements Runnable {

    // Referencia al contador compartido
    private Contador contador;

    /*
     * CONSTRUCTOR
     * ----------------------------------------
     * Recibe el objeto contador que será compartido
     * entre los distintos hilos.
     *
     * IMPORTANTE:
     * Todos los hilos deben usar el MISMO objeto
     * para que realmente sea un recurso compartido.
     */
    public TareaContador(Contador contador) {
        this.contador = contador;
    }

    /*
     * MÉTODO: run
     * ----------------------------------------
     * Define la lógica que ejecuta cada hilo.
     */
    @Override
    public void run() {

        // Cada hilo incrementa el contador 5 veces
        for (int i = 0; i < 5; i++) {

            // Llama al método sincronizado
            contador.incrementar();

            try {
                // Simula tiempo de ejecución
                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido");
            }
        }
    }
}


// ============================================================
// CLASE PRINCIPAL
// ============================================================
public class Main {

    public static void main(String[] args) {

        /*
         * Creación del recurso compartido
         * ----------------------------------------
         * Este objeto será utilizado por ambos hilos.
         */
        Contador contador = new Contador();

        /*
         * Creación de los hilos
         * ----------------------------------------
         * Ambos reciben el MISMO contador.
         */
        Thread hilo1 = new Thread(new TareaContador(contador));
        Thread hilo2 = new Thread(new TareaContador(contador));

        /*
         * Inicio de ejecución
         * ----------------------------------------
         * Los hilos se ejecutan en paralelo.
         */
        hilo1.start();
        hilo2.start();

        /*
         * IMPORTANTE:
         * - Ambos hilos comparten el mismo recurso
         * - synchronized evita errores de concurrencia
         * - El orden de ejecución no está garantizado
         */
    }
}