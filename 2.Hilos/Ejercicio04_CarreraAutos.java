/**
 * EJERCICIO 4 - Carrera de Autos
 * ================================
 * Objetivo: Simular una carrera con 3 hilos (autos). Cada auto avanza
 * con tiempos aleatorios. El primero en terminar gana. Mostrar ranking final.
 *
 * Conceptos clave:
 *   - synchronized: bloquea un bloque de código para acceso exclusivo.
 *   - volatile: garantiza visibilidad entre hilos sin sincronización completa.
 *   - ArrayList con sincronización: para registrar el orden de llegada.
 *   - Random: para simular tiempos de avance aleatorios.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */

import java.util.ArrayList;       // Lista dinámica para guardar el ranking
import java.util.List;             // Interfaz base de ArrayList
import java.util.Random;           // Para generar tiempos aleatorios

public class Ejercicio04_CarreraAutos {

    // ── Variables compartidas entre todos los hilos ───────────────────────────

    // Lista que registra el orden en que los autos terminan la carrera.
    // Debe ser accedida de forma sincronizada para evitar condiciones de carrera.
    private static final List<String> ranking = new ArrayList<>();

    // Distancia total de la carrera (número de "pasos" que debe avanzar cada auto)
    private static final int DISTANCIA_TOTAL = 10;

    // ── Clase interna que representa cada auto (hilo) ─────────────────────────
    static class Auto extends Thread {

        private final String nombre;   // Nombre del auto (ej. "Auto-Rojo")
        private final Random random;   // Generador de números aleatorios propio

        /**
         * Constructor del auto.
         * @param nombre Identificador del auto para mostrar en pantalla.
         */
        public Auto(String nombre) {
            super(nombre);            // Asigna el nombre también al Thread
            this.nombre = nombre;
            this.random = new Random(); // Cada auto tiene su propio Random
        }

        /**
         * Lógica de carrera: el auto avanza paso a paso con tiempos aleatorios.
         * Este método se ejecuta en el hilo del auto.
         */
        @Override
        public void run() {
            System.out.println(nombre + " → ¡Arrancó!");

            // El auto da DISTANCIA_TOTAL pasos hasta terminar la carrera
            for (int paso = 1; paso <= DISTANCIA_TOTAL; paso++) {

                try {
                    // Tiempo aleatorio entre 100ms y 500ms para simular
                    // velocidades distintas en cada tramo.
                    // random.nextInt(400) → número entre 0 y 399
                    // + 100 → rango final: 100ms a 499ms
                    int tiempoEspera = random.nextInt(400) + 100;
                    Thread.sleep(tiempoEspera);

                } catch (InterruptedException e) {
                    // Si el hilo es interrumpido, salimos limpiamente
                    Thread.currentThread().interrupt();
                    return;
                }

                // Imprimimos el avance del auto con una barra de progreso visual
                String barra = "█".repeat(paso) + "░".repeat(DISTANCIA_TOTAL - paso);
                System.out.println(nombre + " [" + barra + "] paso " + paso + "/" + DISTANCIA_TOTAL);
            }

            // ── El auto terminó la carrera ────────────────────────────────────
            // Acceso sincronizado a la lista compartida "ranking".
            // synchronized(ranking) garantiza que solo UN hilo a la vez
            // puede agregar su nombre y leer el tamaño de la lista.
            synchronized (ranking) {
                ranking.add(nombre);                      // Registramos la llegada
                int posicion = ranking.size();            // Posición en el ranking
                System.out.println("🏁 " + nombre + " llegó en posición #" + posicion + "!");

                // Si es el primer auto en llegar, es el ganador
                if (posicion == 1) {
                    System.out.println("🏆 ¡¡¡ " + nombre + " GANÓ LA CARRERA !!!");
                }
            }
        }
    }

    // ── Main: lanza la carrera ────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   🏎  CARRERA DE AUTOS  🏎    ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("Distancia: " + DISTANCIA_TOTAL + " pasos\n");

        // Creamos los 3 autos con nombres distintivos
        Auto auto1 = new Auto("Auto-Rojo  ");
        Auto auto2 = new Auto("Auto-Azul  ");
        Auto auto3 = new Auto("Auto-Verde ");

        // Iniciamos los 3 hilos simultáneamente
        // start() lanza cada hilo sin esperar al anterior
        auto1.start();
        auto2.start();
        auto3.start();

        // El main espera a que los 3 autos terminen antes de mostrar el ranking
        auto1.join(); // Espera que auto1 termine
        auto2.join(); // Espera que auto2 termine
        auto3.join(); // Espera que auto3 termine

        // ── Mostramos el ranking final ────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       RANKING FINAL          ║");
        System.out.println("╚══════════════════════════════╝");

        // Recorremos la lista en orden (ya está ordenada por orden de llegada)
        for (int i = 0; i < ranking.size(); i++) {
            String medalla = switch (i) {
                case 0 -> "🥇";  // Primer lugar
                case 1 -> "🥈";  // Segundo lugar
                case 2 -> "🥉";  // Tercer lugar
                default -> (i + 1) + "."; // Por si hubiera más
            };
            System.out.println(medalla + " " + (i + 1) + "° lugar: " + ranking.get(i).trim());
        }
    }
}