/**
 * EJERCICIO 1 - Orden controlado con join()
 * ==========================================
 * Objetivo: Crear 3 hilos que impriman "Inicio", "Proceso" y "Fin"
 * garantizando que se ejecuten en ese orden estricto usando join().
 *
 * Concepto clave:
 *   join() hace que el hilo actual (quien llama) espere a que el hilo
 *   referenciado termine antes de continuar su propia ejecución.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio01_OrdenConJoin {

    public static void main(String[] args) {

        // ── Definición de los 3 hilos usando expresiones lambda ──────────────
        // Lambda es una forma compacta de implementar la interfaz Runnable.
        // Cada lambda representa la tarea que ejecutará ese hilo.

        // Hilo 1: imprimirá "Inicio"
        Thread hilo1 = new Thread(() -> {
            System.out.println("Hilo 1 → Inicio");
        });

        // Hilo 2: imprimirá "Proceso"
        Thread hilo2 = new Thread(() -> {
            System.out.println("Hilo 2 → Proceso");
        });

        // Hilo 3: imprimirá "Fin"
        Thread hilo3 = new Thread(() -> {
            System.out.println("Hilo 3 → Fin");
        });

        // ── Ejecución en orden controlado ────────────────────────────────────
        try {
            // Iniciamos el hilo 1 y esperamos a que termine antes de continuar.
            hilo1.start(); // Lanza hilo1 en un nuevo hilo de ejecución
            hilo1.join();  // El main se bloquea hasta que hilo1 finalice

            // Solo después de que hilo1 terminó, iniciamos hilo2.
            hilo2.start(); // Lanza hilo2
            hilo2.join();  // El main se bloquea hasta que hilo2 finalice

            // Solo después de que hilo2 terminó, iniciamos hilo3.
            hilo3.start(); // Lanza hilo3
            hilo3.join();  // El main se bloquea hasta que hilo3 finalice

        } catch (InterruptedException e) {
            // InterruptedException se lanza si el hilo que está esperando
            // (en este caso el main) es interrumpido mientras hace join().
            System.err.println("El hilo fue interrumpido: " + e.getMessage());
            // Restauramos el estado de interrupción del hilo actual
            Thread.currentThread().interrupt();
        }

        // Este mensaje siempre se imprime al final porque join() garantizó
        // que los 3 hilos ya terminaron antes de llegar aquí.
        System.out.println("Todos los hilos finalizaron en el orden correcto.");
    }
}