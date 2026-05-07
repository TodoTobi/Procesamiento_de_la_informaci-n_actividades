/**
 * EJERCICIO 11 - Descarga con Progreso Compartido
 * =================================================
 * Objetivo: Simular 3 hilos descargando partes de un archivo. Todos
 * actualizan una variable compartida de progreso total y muestran
 * el porcentaje (ej: "Progreso total: 35%").
 *
 * Conceptos clave:
 *   - AtomicInteger: para acumular el progreso sin race conditions.
 *   - volatile: para flags de control visibles entre hilos.
 *   - Hilo monitor: hilo dedicado solo a mostrar el progreso periodicamente.
 *   - Simulación de descarga con porcentajes parciales por segmento.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */

import java.util.concurrent.atomic.AtomicInteger; // Para progreso compartido atómico

public class Ejercicio11_DescargaProgreso {

    // ── Variables compartidas entre hilos ────────────────────────────────────

    // Progreso total acumulado (de 0 a 100).
    // AtomicInteger porque múltiples hilos lo modifican concurrentemente.
    private static final AtomicInteger progresoTotal = new AtomicInteger(0);

    // Flag que indica si la descarga completa terminó.
    // volatile para que el hilo monitor vea el cambio inmediatamente.
    private static volatile boolean descargaCompleta = false;

    // Tamaño total del "archivo" en unidades arbitrarias (100 = 100%)
    private static final int TOTAL_UNIDADES = 100;

    // ══════════════════════════════════════════════════════════════════════════
    // Clase que representa una parte del archivo a descargar
    // ══════════════════════════════════════════════════════════════════════════
    static class SegmentoDescarga extends Thread {

        private final String nombreSegmento; // Ej: "Segmento-1"
        private final int unidadesADescargar; // Cuántas unidades debe descargar este segmento
        private final int velocidadMs;        // Tiempo por unidad (simula velocidad)

        /**
         * @param nombre      Identificador del segmento.
         * @param unidades    Cantidad de unidades que descargará (parte del 100%).
         * @param velocidadMs Milisegundos por unidad (menor = más rápido).
         */
        public SegmentoDescarga(String nombre, int unidades, int velocidadMs) {
            super(nombre);
            this.nombreSegmento = nombre;
            this.unidadesADescargar = unidades;
            this.velocidadMs = velocidadMs;
        }

        @Override
        public void run() {
            System.out.println("[" + nombreSegmento + "] Iniciando descarga de "
                    + unidadesADescargar + " unidades...");

            // Descargamos unidad por unidad
            for (int u = 1; u <= unidadesADescargar; u++) {

                try {
                    // Cada unidad tarda velocidadMs milisegundos en "descargarse"
                    Thread.sleep(velocidadMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("[" + nombreSegmento + "] ¡Interrumpido!");
                    return;
                }

                // addAndGet(1): suma 1 al progreso total de forma atómica y
                // devuelve el nuevo valor. Equivale a "++progresoTotal" pero seguro.
                int nuevoProgreso = progresoTotal.addAndGet(1);

                // Imprimimos el avance de este segmento en particular
                int porcentajeLocal = (u * 100) / unidadesADescargar;
                System.out.printf("[%s] Descargando... %d%% local | Total general: %d%%%n",
                        nombreSegmento, porcentajeLocal, nuevoProgreso);
            }

            System.out.println("[" + nombreSegmento + "] ✓ Descarga completada.\n");
        }
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== DESCARGA DE ARCHIVO CON PROGRESO COMPARTIDO ===");
        System.out.println("Simulando 3 segmentos descargando en paralelo...\n");

        // ── Creamos los 3 segmentos de descarga ───────────────────────────────
        // El archivo tiene 100 unidades en total:
        //   Segmento 1: 40% (velocidad: 200ms/unidad → más lento)
        //   Segmento 2: 35% (velocidad: 150ms/unidad → velocidad media)
        //   Segmento 3: 25% (velocidad: 100ms/unidad → más rápido)
        // Total: 40 + 35 + 25 = 100 unidades = 100%
        SegmentoDescarga seg1 = new SegmentoDescarga("Segmento-1 (40%)", 40, 200);
        SegmentoDescarga seg2 = new SegmentoDescarga("Segmento-2 (35%)", 35, 150);
        SegmentoDescarga seg3 = new SegmentoDescarga("Segmento-3 (25%)", 25, 100);

        // ── Hilo monitor: muestra el progreso total periódicamente ────────────
        Thread monitor = new Thread(() -> {
            System.out.println("[MONITOR] Monitoreando progreso...\n");
            while (!descargaCompleta) {
                // Leemos el progreso actual (get() es atómico en AtomicInteger)
                int progreso = progresoTotal.get();

                // Construimos una barra de progreso visual
                int barraLlena = progreso / 5; // Escala: 100% → 20 caracteres
                String barra = "█".repeat(barraLlena) + "░".repeat(20 - barraLlena);

                // Imprimimos el resumen de progreso
                System.out.printf(">>> Progreso total: %3d%% [%s]%n", progreso, barra);

                if (progreso >= TOTAL_UNIDADES) break; // Llegamos al 100%, salimos

                try {
                    Thread.sleep(800); // Actualizamos el display cada 800ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            // Impresión final cuando llegamos al 100%
            System.out.printf("%n>>> Progreso total: 100%% [████████████████████] ✓ COMPLETADO%n%n");
        }, "Monitor-Progreso");

        // ── Iniciamos todos los hilos ─────────────────────────────────────────
        monitor.start(); // Primero el monitor para que capture desde 0%
        seg1.start();    // Lanzamos los 3 segmentos en paralelo
        seg2.start();
        seg3.start();

        // ── Esperamos a que los 3 segmentos terminen ──────────────────────────
        seg1.join();
        seg2.join();
        seg3.join();

        // Señalizamos al monitor que la descarga terminó
        descargaCompleta = true;
        monitor.join(); // Esperamos que el monitor imprima el último mensaje

        // ── Resultado final ───────────────────────────────────────────────────
        System.out.println("=== DESCARGA FINALIZADA ===");
        System.out.println("Progreso final registrado: " + progresoTotal.get() + "%");
        System.out.println("Los 3 segmentos contribuyeron al progreso compartido sin conflictos.");
    }
}