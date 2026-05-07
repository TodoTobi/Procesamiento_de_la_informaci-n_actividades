/**
 * EJERCICIO 5 - Contador SIN Sincronización (Error Intencional)
 * ==============================================================
 * Objetivo: Demostrar el problema de "condición de carrera" (race condition)
 * cuando múltiples hilos acceden y modifican una variable compartida SIN
 * sincronización. El resultado será incorrecto (menor a 2000).
 *
 * ¿Por qué el resultado es inconsistente?
 * ─────────────────────────────────────────
 * La operación "contador++" NO es atómica. Internamente realiza 3 pasos:
 *   1. LEER el valor actual de contador (ej: 500)
 *   2. SUMAR 1 al valor leído (500 + 1 = 501)
 *   3. ESCRIBIR el resultado en contador (contador = 501)
 *
 * Si dos hilos ejecutan estos pasos intercalados (interleaving):
 *   Hilo1 lee contador = 500
 *   Hilo2 lee contador = 500  ← lee el mismo valor antes de que Hilo1 escriba
 *   Hilo1 escribe 501
 *   Hilo2 escribe 501          ← sobrescribe, perdemos un incremento
 *
 * Resultado: se pierden incrementos → el total final es < 2000.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio05_ContadorSinSincronizar {

    // ── Variable compartida SIN ningún mecanismo de sincronización ───────────
    // Al ser un campo estático accesible por ambos hilos, es susceptible
    // a condiciones de carrera. NO usamos volatile, synchronized ni Atomic.
    private static int contador = 0;

    // Número de veces que cada hilo incrementará el contador
    private static final int INCREMENTOS = 1000;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== CONTADOR SIN SINCRONIZACIÓN (error intencional) ===");
        System.out.println("Cada hilo suma " + INCREMENTOS + " veces → esperado: " + (INCREMENTOS * 2));
        System.out.println();

        // ── Hilo 1: suma 1000 veces al contador ──────────────────────────────
        Thread hilo1 = new Thread(() -> {
            for (int i = 0; i < INCREMENTOS; i++) {
                // contador++ NO ES ATÓMICA: lee, suma 1, escribe.
                // Puede ser interrumpida entre esos 3 pasos por el otro hilo.
                contador++; // ← operación no atómica → race condition posible
            }
            System.out.println("Hilo 1 terminó sus " + INCREMENTOS + " incrementos.");
        }, "Hilo-1");

        // ── Hilo 2: suma 1000 veces al contador ──────────────────────────────
        Thread hilo2 = new Thread(() -> {
            for (int i = 0; i < INCREMENTOS; i++) {
                // Mismo problema: puede leer un valor "viejo" antes de que
                // Hilo-1 escriba su incremento, causando que se pierda uno.
                contador++; // ← operación no atómica → race condition posible
            }
            System.out.println("Hilo 2 terminó sus " + INCREMENTOS + " incrementos.");
        }, "Hilo-2");

        // Iniciamos ambos hilos casi simultáneamente para maximizar colisiones
        hilo1.start();
        hilo2.start();

        // Esperamos a que ambos terminen antes de leer el resultado
        hilo1.join();
        hilo2.join();

        // ── Resultado final ───────────────────────────────────────────────────
        System.out.println();
        System.out.println("Resultado esperado : " + (INCREMENTOS * 2));
        System.out.println("Resultado obtenido : " + contador);

        // El resultado CASI SIEMPRE será menor a 2000 por la race condition.
        // En algunas máquinas muy lentas podría dar 2000 por casualidad.
        if (contador < INCREMENTOS * 2) {
            System.out.println("⚠ ¡Resultado INCORRECTO! Se perdieron "
                    + (INCREMENTOS * 2 - contador) + " incrementos por race condition.");
        } else {
            System.out.println("(Resultado correcto esta vez, pero no está garantizado)");
        }

        System.out.println();
        System.out.println("=== EXPLICACIÓN ===");
        System.out.println("contador++ ejecuta 3 pasos: LEER → SUMAR → ESCRIBIR.");
        System.out.println("Si dos hilos leen el mismo valor antes de que alguno escriba,");
        System.out.println("uno de los incrementos se pierde (se sobreescribe el mismo valor).");
        System.out.println("Esto se llama 'Race Condition' o Condición de Carrera.");
        System.out.println("Solución: usar synchronized, AtomicInteger o volatile (ver Ej. 6).");
    }
}