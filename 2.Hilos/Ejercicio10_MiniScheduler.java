/**
 * EJERCICIO 10 - Sistema de Tareas (Mini Scheduler) con ExecutorService
 * ========================================================================
 * Objetivo: Crear 5 tareas distintas y ejecutarlas con un pool de 2 hilos,
 * observando cómo el ExecutorService organiza y distribuye el trabajo.
 *
 * Concepto clave - ExecutorService y Thread Pool:
 *   En vez de crear y gestionar hilos manualmente, ExecutorService mantiene
 *   un "pool" (grupo) de hilos reutilizables. Cuando enviamos una tarea
 *   (Runnable/Callable), el pool la asigna al primer hilo disponible.
 *   Si todos están ocupados, la tarea espera en una cola interna (LinkedBlockingQueue).
 *
 * Ventajas del pool:
 *   - Reutilización: los hilos no se destruyen al terminar una tarea.
 *   - Control: limitamos cuántos hilos corren en paralelo.
 *   - Gestión de ciclo de vida: shutdown() y awaitTermination().
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */

import java.util.concurrent.ExecutorService;    // Interfaz del pool de hilos
import java.util.concurrent.Executors;           // Fábrica para crear pools
import java.util.concurrent.TimeUnit;            // Unidades de tiempo para awaitTermination

public class Ejercicio10_MiniScheduler {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== MINI SCHEDULER CON ExecutorService ===");
        System.out.println("Pool de 2 hilos, 5 tareas a ejecutar.\n");

        // ── Creamos el pool de hilos ──────────────────────────────────────────
        // newFixedThreadPool(2) → crea exactamente 2 hilos que persisten
        // mientras el pool esté activo. Si hay más tareas que hilos, las
        // tareas excedentes esperan en una cola FIFO interna.
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // ══════════════════════════════════════════════════════════════════════
        // Definimos las 5 tareas como Runnable (lambda)
        // ══════════════════════════════════════════════════════════════════════

        // TAREA 1: Imprime números del 1 al 5
        Runnable tarea1 = () -> {
            String nombreHilo = Thread.currentThread().getName();
            System.out.println("[" + nombreHilo + "] TAREA-1 Inicio: imprimiendo números");
            for (int i = 1; i <= 5; i++) {
                System.out.println("[" + nombreHilo + "] TAREA-1 → Número: " + i);
                try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println("[" + nombreHilo + "] TAREA-1 Completada.\n");
        };

        // TAREA 2: Imprime letras A a E
        Runnable tarea2 = () -> {
            String nombreHilo = Thread.currentThread().getName();
            System.out.println("[" + nombreHilo + "] TAREA-2 Inicio: imprimiendo letras");
            for (char c = 'A'; c <= 'E'; c++) {
                System.out.println("[" + nombreHilo + "] TAREA-2 → Letra: " + c);
                try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println("[" + nombreHilo + "] TAREA-2 Completada.\n");
        };

        // TAREA 3: Hace cálculos matemáticos (factoriales)
        Runnable tarea3 = () -> {
            String nombreHilo = Thread.currentThread().getName();
            System.out.println("[" + nombreHilo + "] TAREA-3 Inicio: calculando factoriales");
            for (int n = 1; n <= 5; n++) {
                long factorial = 1;
                for (int i = 1; i <= n; i++) factorial *= i;
                System.out.println("[" + nombreHilo + "] TAREA-3 → " + n + "! = " + factorial);
                try { Thread.sleep(250); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println("[" + nombreHilo + "] TAREA-3 Completada.\n");
        };

        // TAREA 4: Simula descarga de archivos
        Runnable tarea4 = () -> {
            String nombreHilo = Thread.currentThread().getName();
            System.out.println("[" + nombreHilo + "] TAREA-4 Inicio: descargando archivo");
            String[] archivos = {"datos.csv", "imagen.png", "reporte.pdf"};
            for (String archivo : archivos) {
                System.out.println("[" + nombreHilo + "] TAREA-4 → Descargando: " + archivo);
                try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("[" + nombreHilo + "] TAREA-4 → ✓ " + archivo + " descargado.");
            }
            System.out.println("[" + nombreHilo + "] TAREA-4 Completada.\n");
        };

        // TAREA 5: Genera y muestra tabla de multiplicar del 3
        Runnable tarea5 = () -> {
            String nombreHilo = Thread.currentThread().getName();
            System.out.println("[" + nombreHilo + "] TAREA-5 Inicio: tabla de multiplicar del 3");
            for (int i = 1; i <= 5; i++) {
                System.out.println("[" + nombreHilo + "] TAREA-5 → 3 × " + i + " = " + (3 * i));
                try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println("[" + nombreHilo + "] TAREA-5 Completada.\n");
        };

        // ── Enviamos las 5 tareas al pool ─────────────────────────────────────
        // submit() (o execute()) agrega la tarea a la cola interna del pool.
        // El pool asigna cada tarea al primer hilo libre.
        // Con 2 hilos: las primeras 2 tareas empiezan inmediatamente,
        // las siguientes esperan en la cola hasta que un hilo quede libre.
        System.out.println("Enviando 5 tareas al pool de 2 hilos...\n");
        pool.execute(tarea1); // Asignada a pool-thread-1 (si está libre)
        pool.execute(tarea2); // Asignada a pool-thread-2 (si está libre)
        pool.execute(tarea3); // Espera en cola → se asigna cuando libere alguno
        pool.execute(tarea4); // Espera en cola
        pool.execute(tarea5); // Espera en cola

        // ── Cerramos el pool ordenadamente ───────────────────────────────────
        // shutdown(): indica al pool que NO acepte nuevas tareas, pero
        // permite que las tareas ya en cola o ejecutándose terminen.
        // NO espera: retorna inmediatamente.
        pool.shutdown();

        // awaitTermination(tiempo, unidad): bloquea hasta que:
        //   a) todas las tareas terminaron, O
        //   b) se agotó el tiempo límite
        // Retorna true si terminó, false si venció el tiempo.
        boolean terminado = pool.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("=== RESUMEN ===");
        if (terminado) {
            System.out.println("✓ Todas las tareas completadas exitosamente.");
        } else {
            System.out.println("⚠ Tiempo límite alcanzado: algunas tareas podrían no haber terminado.");
        }
        System.out.println("El pool con 2 hilos procesó 5 tareas de forma eficiente.");
        System.out.println("Observar los nombres de hilo (pool-N-thread-M) en la salida.");
    }
}