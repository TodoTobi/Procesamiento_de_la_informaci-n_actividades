/**
 * EJERCICIO 7 - Semáforo de Tráfico
 * ====================================
 * Objetivo: Simular un semáforo con 3 estados (Verde, Amarillo, Rojo).
 * Un hilo cambia el estado cada 2 segundos.
 * Otro hilo "autos" solo avanza cuando el semáforo está en Verde.
 *
 * Conceptos clave:
 *   - volatile: garantiza que los cambios de la variable sean visibles
 *               inmediatamente a todos los hilos (evita caching en registros).
 *   - enum: tipo enumerado para representar los estados del semáforo.
 *   - Acceso controlado mediante lectura de variable compartida volatile.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio07_Semaforo {

    // ── Enumeración de estados del semáforo ───────────────────────────────────
    // enum crea un tipo con valores constantes predefinidos.
    // Más seguro y legible que usar String o int para representar estados.
    enum EstadoSemaforo {
        VERDE,    // Los autos pueden avanzar
        AMARILLO, // Los autos deben prepararse para detenerse
        ROJO      // Los autos deben detenerse
    }

    // ── Variable de estado compartida ────────────────────────────────────────
    // volatile garantiza que cuando el hilo-semaforo escribe un nuevo estado,
    // el hilo-autos lo ve inmediatamente sin leer un valor cacheado en CPU.
    // Sin volatile, el hilo-autos podría leer un valor "viejo" de su caché.
    private static volatile EstadoSemaforo estadoActual = EstadoSemaforo.ROJO;

    // Flag para señalizar cuándo terminar la simulación
    private static volatile boolean simulacionActiva = true;

    // Duración total de la simulación (en ms)
    private static final int DURACION_SIMULACION = 14000; // 14 segundos

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== SIMULACIÓN DE SEMÁFORO ===");
        System.out.println("Duración: " + (DURACION_SIMULACION / 1000) + " segundos\n");

        // ══════════════════════════════════════════════════════════════════════
        // HILO SEMÁFORO: cambia el estado cada 2 segundos
        // ══════════════════════════════════════════════════════════════════════
        Thread hiloSemaforo = new Thread(() -> {

            // Secuencia cíclica de estados: Verde → Amarillo → Rojo → Verde...
            EstadoSemaforo[] secuencia = {
                EstadoSemaforo.VERDE,
                EstadoSemaforo.AMARILLO,
                EstadoSemaforo.ROJO
            };

            int indice = 0; // Índice actual en la secuencia

            // Cicla mientras la simulación esté activa
            while (simulacionActiva) {
                // Cambiamos al siguiente estado de la secuencia
                estadoActual = secuencia[indice];

                // Representación visual del estado actual
                String simbolo = switch (estadoActual) {
                    case VERDE    -> "🟢 VERDE   ";
                    case AMARILLO -> "🟡 AMARILLO";
                    case ROJO     -> "🔴 ROJO    ";
                };
                System.out.println("[SEMÁFORO] → " + simbolo);

                // Avanzamos al siguiente estado (circular: 0→1→2→0→...)
                indice = (indice + 1) % secuencia.length;

                try {
                    // Mantenemos cada estado durante 2 segundos (2000ms)
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break; // Salimos del while si somos interrumpidos
                }
            }
            System.out.println("[SEMÁFORO] Apagado.");

        }, "Hilo-Semaforo");

        // ══════════════════════════════════════════════════════════════════════
        // HILO AUTOS: solo avanza cuando el semáforo está en VERDE
        // ══════════════════════════════════════════════════════════════════════
        Thread hiloAutos = new Thread(() -> {

            int autoNumero = 1; // Número de auto que "pasa"

            while (simulacionActiva) {
                // Verificamos el estado actual del semáforo
                if (estadoActual == EstadoSemaforo.VERDE) {
                    // El semáforo está en verde: el auto puede avanzar
                    System.out.println("  [AUTO " + autoNumero + "] 🚗 ¡Avanzando! (semáforo verde)");
                    autoNumero++;

                    try {
                        // El auto tarda 600ms en "pasar" la intersección
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }

                } else {
                    // El semáforo NO está en verde: el auto espera
                    String accion = estadoActual == EstadoSemaforo.AMARILLO
                            ? "🚗 Preparando frenada..."
                            : "🚗 Detenido.";
                    System.out.println("  [AUTO " + autoNumero + "] " + accion
                            + " (semáforo: " + estadoActual + ")");

                    try {
                        // Verificamos el estado cada 500ms para no hacer busy-waiting
                        // (busy-wait = loop sin sleep, desperdiciaría CPU al 100%)
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            System.out.println("  [AUTOS] Simulación terminada.");

        }, "Hilo-Autos");

        // ── Iniciamos ambos hilos ─────────────────────────────────────────────
        hiloSemaforo.start();
        hiloAutos.start();

        // El main espera la duración de la simulación y luego la detiene
        Thread.sleep(DURACION_SIMULACION);

        // Señalizamos a ambos hilos que deben terminar
        simulacionActiva = false;

        // Interrumpimos los hilos para que salgan del sleep() rápidamente
        hiloSemaforo.interrupt();
        hiloAutos.interrupt();

        // Esperamos a que ambos terminen limpiamente
        hiloSemaforo.join();
        hiloAutos.join();

        System.out.println("\n=== Simulación finalizada. ===");
    }
}