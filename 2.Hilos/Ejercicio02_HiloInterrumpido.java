/**
 * EJERCICIO 2 - Hilo Interrumpido
 * ================================
 * Objetivo: Crear un hilo que imprime números del 1 al 100 con pausa
 * de 500ms entre cada número. Desde el main, interrumpir ese hilo
 * cuando llegue aproximadamente al número 10.
 *
 * Conceptos clave:
 *   - Thread.sleep(ms): pausa la ejecución del hilo actual N milisegundos.
 *   - thread.interrupt(): envía una señal de interrupción al hilo.
 *   - isInterrupted(): verifica si el hilo recibió una señal de interrupción.
 *   - InterruptedException: excepción lanzada cuando sleep() es interrumpido.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio02_HiloInterrumpido {

    public static void main(String[] args) {

        // ── Definición del hilo contador ─────────────────────────────────────
        Thread hiloContador = new Thread(() -> {

            // Iteramos del 1 al 100
            for (int i = 1; i <= 100; i++) {

                // Verificamos ANTES de imprimir si el hilo fue interrumpido.
                // isInterrupted() retorna true si se llamó interrupt() sobre
                // este hilo y la bandera de interrupción no fue limpiada.
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(">>> El hilo detectó la interrupción en i=" + i);
                    break; // Salimos del bucle limpiamente
                }

                // Imprimimos el número actual
                System.out.println("Contador: " + i);

                try {
                    // Pausa de 500 milisegundos entre cada número.
                    // Si durante esta pausa se llama interrupt(), sleep()
                    // lanza InterruptedException inmediatamente.
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    // sleep() fue interrumpido: el hilo recibió interrupt()
                    // mientras dormía. Mostramos en qué número ocurrió.
                    System.out.println(">>> Hilo interrumpido mientras dormía (último número: " + i + ")");
                    // IMPORTANTE: cuando InterruptedException es capturada,
                    // Java limpia la bandera de interrupción automáticamente.
                    // Debemos restaurarla si queremos que código posterior lo sepa.
                    Thread.currentThread().interrupt();
                    break; // Salimos del bucle ante la interrupción
                }
            }

            // Mensaje final del hilo (se imprime si fue interrumpido o terminó)
            System.out.println("Hilo interrumpido correctamente");
        });

        // ── Lanzamos el hilo ─────────────────────────────────────────────────
        hiloContador.start(); // Inicia la ejecución del hilo en paralelo

        try {
            // El main espera 5200ms ≈ tiempo para que el hilo imprima ~10 números
            // (cada número tarda ~500ms → 10 números × 500ms = 5000ms + margen)
            Thread.sleep(5200);

        } catch (InterruptedException e) {
            // El main también podría ser interrumpido (poco común pero posible)
            System.err.println("El main fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        // ── Interrumpimos el hilo desde el main ──────────────────────────────
        // interrupt() envía la señal al hilo. Si está en sleep(), lanza
        // InterruptedException. Si no está durmiendo, solo activa la bandera.
        hiloContador.interrupt();

        System.out.println("Main: señal de interrupción enviada al hilo.");
    }
}