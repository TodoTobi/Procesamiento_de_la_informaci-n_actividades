/**
 * EJERCICIO 3 - Diferencia entre run() y start()
 * =================================================
 * Objetivo: Demostrar la diferencia fundamental entre llamar run()
 * directamente y usar start() para lanzar un hilo.
 *
 * Diferencia clave:
 *   ● run()   → NO crea un nuevo hilo. Ejecuta el código del Runnable
 *               directamente en el hilo que hace la llamada (por ej. main).
 *               Es una llamada a método normal, secuencial, bloqueante.
 *
 *   ● start() → SÍ crea un nuevo hilo del sistema operativo. La JVM llama
 *               a run() en ese nuevo hilo. La ejecución es concurrente y
 *               no bloqueante para quien llamó start().
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio03_RunVsStart {

    public static void main(String[] args) throws InterruptedException {

        // ── Definimos una tarea Runnable reutilizable ─────────────────────────
        // Runnable es una interfaz funcional con un único método: run().
        // Esta tarea imprime el nombre del hilo que la ejecuta.
        Runnable tarea = () -> {
            // Thread.currentThread().getName() retorna el nombre del hilo
            // que está ejecutando este bloque de código en este momento.
            System.out.println("  Ejecutando en hilo: [" + Thread.currentThread().getName() + "]");
            System.out.println("  Trabajo completado.");
        };

        // ══════════════════════════════════════════════════════════════════════
        // CASO A: Llamada a run() directamente
        // ══════════════════════════════════════════════════════════════════════
        System.out.println("=== CASO A: Usando run() ===");
        System.out.println("Hilo actual antes de run(): [" + Thread.currentThread().getName() + "]");

        Thread hiloA = new Thread(tarea, "Hilo-A"); // Creamos el objeto Thread con nombre "Hilo-A"

        // Llamamos run() directamente: NO se crea ningún hilo nuevo.
        // La tarea se ejecuta en el hilo "main" de forma SINCRÓNICA.
        // El hilo "Hilo-A" NUNCA llega a existir como hilo del SO.
        hiloA.run(); // ← Esto es solo una llamada a método normal

        System.out.println("Después de run() → el main continuó (bloqueante).");
        System.out.println();

        // ══════════════════════════════════════════════════════════════════════
        // CASO B: Llamada a start()
        // ══════════════════════════════════════════════════════════════════════
        System.out.println("=== CASO B: Usando start() ===");
        System.out.println("Hilo actual antes de start(): [" + Thread.currentThread().getName() + "]");

        Thread hiloB = new Thread(tarea, "Hilo-B"); // Creamos un nuevo objeto Thread con nombre "Hilo-B"

        // start() le dice a la JVM que cree un nuevo hilo del sistema operativo
        // y ejecute run() en ESE nuevo hilo de forma ASÍNCRONA (concurrente).
        // El main NO espera: continúa inmediatamente después de esta línea.
        hiloB.start(); // ← Esto SÍ crea un nuevo hilo

        // Esta línea puede imprimirse ANTES o DESPUÉS del output de hiloB
        // porque ambos (main e hiloB) corren en paralelo.
        System.out.println("Después de start() → el main continuó sin esperar (no bloqueante).");

        // Esperamos a que hiloB termine para ver su output antes del resumen
        hiloB.join();

        // ══════════════════════════════════════════════════════════════════════
        // EXPLICACIÓN COMPARATIVA
        // ══════════════════════════════════════════════════════════════════════
        System.out.println();
        System.out.println("=== CONCLUSIÓN ===");
        System.out.println("run()   → ejecuta en el hilo MAIN (sin crear hilo nuevo). Bloqueante.");
        System.out.println("start() → crea un NUEVO hilo del SO. No bloqueante. Verdadero paralelismo.");
        System.out.println("Observar el nombre del hilo en cada caso es la prueba definitiva:");
        System.out.println("  Con run()   el hilo siempre dice 'main'.");
        System.out.println("  Con start() el hilo dice el nombre asignado ('Hilo-B').");
    }
}