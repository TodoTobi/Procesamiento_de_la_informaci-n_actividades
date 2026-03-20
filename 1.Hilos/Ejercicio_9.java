/*
 * ============================================================
 *  SIMULACIÓN DE DESCARGAS CONCURRENTES
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa simula la descarga de archivos utilizando
 *  múltiples hilos en paralelo.
 *
 *  FUNCIONAMIENTO:
 *  - Se crean 3 hilos (descargas simultáneas)
 *  - Cada hilo representa la descarga de un archivo
 *  - Se muestra el progreso en porcentaje (0% → 100%)
 *
 *  PROBLEMA REAL:
 *  En aplicaciones reales (navegadores, gestores de descarga),
 *  múltiples archivos se descargan al mismo tiempo.
 *
 *  SOLUCIÓN:
 *  Se utilizan hilos (Thread) para simular ejecución concurrente
 *  y Thread.sleep() para representar el tiempo de descarga.
 *
 *  CONCEPTOS CLAVE:
 *  - Concurrencia
 *  - Thread
 *  - Runnable
 *  - Simulación de procesos
 *
 * ============================================================
 */


// ============================================================
// CLASE: Descarga
// Representa una tarea de descarga de un archivo
// ============================================================
class Descarga implements Runnable {

    // Nombre del archivo (identificador)
    private String nombreArchivo;

    // Constructor
    public Descarga(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /*
     * MÉTODO: run
     * ----------------------------------------
     * Simula la descarga de un archivo mostrando progreso.
     */
    @Override
    public void run() {

        // Progreso de la descarga (de 0% a 100%)
        for (int progreso = 0; progreso <= 100; progreso += 20) {

            // Muestra el estado actual
            System.out.println(
                Thread.currentThread().getName() +
                " descargando " + nombreArchivo +
                " → " + progreso + "%"
            );

            try {
                // Simula el tiempo que tarda descargar un segmento
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error en la descarga de " + nombreArchivo);
            }
        }

        // Mensaje final al completar la descarga
        System.out.println(
            Thread.currentThread().getName() +
            " COMPLETÓ la descarga de " + nombreArchivo
        );
    }
}


// ============================================================
// CLASE PRINCIPAL
// ============================================================
public class Ejercicio_9 {

    public static void main(String[] args) {

        /*
         * Creación de hilos de descarga
         * ----------------------------------------
         * Cada hilo representa una descarga independiente.
         */
        Thread descarga1 = new Thread(new Descarga("archivo1.zip"), "Descarga-1");
        Thread descarga2 = new Thread(new Descarga("archivo2.mp4"), "Descarga-2");
        Thread descarga3 = new Thread(new Descarga("archivo3.pdf"), "Descarga-3");

        /*
         * Inicio de las descargas
         * ----------------------------------------
         * Se ejecutan en paralelo (concurrentemente)
         */
        descarga1.start();
        descarga2.start();
        descarga3.start();
    }
}