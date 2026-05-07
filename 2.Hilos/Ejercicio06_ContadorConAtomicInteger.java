/**
 * EJERCICIO 6 - Contador CON AtomicInteger
 * =========================================
 * Objetivo: Rehacer el ejercicio anterior usando AtomicInteger para
 * garantizar que el contador siempre dé 2000, sin race conditions.
 *
 * Concepto clave - AtomicInteger:
 *   Clase del paquete java.util.concurrent.atomic que provee operaciones
 *   enteras atómicas (indivisibles) usando instrucciones de hardware
 *   CAS (Compare-And-Swap). Garantiza que incrementAndGet() es atómica:
 *   leer, sumar y escribir ocurren como UNA sola operación indivisible.
 *
 * Ventaja vs synchronized:
 *   - No bloquea hilos (no-blocking): usa operaciones CAS del CPU.
 *   - Más eficiente que synchronized para operaciones simples de contador.
 *   - Código más limpio y seguro.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */

import java.util.concurrent.atomic.AtomicInteger; // Importamos AtomicInteger

public class Ejercicio06_ContadorConAtomicInteger {

    // ── Contador atómico compartido ───────────────────────────────────────────
    // AtomicInteger reemplaza a "int contador = 0" del ejercicio anterior.
    // Su valor inicial se pasa por constructor (aquí: 0).
    private static final AtomicInteger contadorAtomico = new AtomicInteger(0);

    // Para comparación: contador normal SIN sincronización (como en Ej. 5)
    private static int contadorNormal = 0;

    // Número de incrementos por hilo
    private static final int INCREMENTOS = 1000;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== CONTADOR CON AtomicInteger ===");
        System.out.println("Cada hilo suma " + INCREMENTOS + " veces → esperado: " + (INCREMENTOS * 2));
        System.out.println();

        // ── PARTE A: Con AtomicInteger (CORRECTO) ─────────────────────────────

        // Hilo 1 usando AtomicInteger
        Thread hilo1Atomic = new Thread(() -> {
            for (int i = 0; i < INCREMENTOS; i++) {
                // incrementAndGet() es equivalente a "++contador" pero ATÓMICA.
                // Internamente usa Compare-And-Swap (CAS) del hardware:
                // compara el valor actual con el esperado y solo escribe
                // si nadie más lo modificó entretanto. Si hubo conflicto, reintenta.
                contadorAtomico.incrementAndGet();
            }
            System.out.println("[Atomic] Hilo 1 terminó.");
        }, "Hilo-Atomic-1");

        // Hilo 2 usando AtomicInteger
        Thread hilo2Atomic = new Thread(() -> {
            for (int i = 0; i < INCREMENTOS; i++) {
                contadorAtomico.incrementAndGet(); // Atómico: nunca pierde incrementos
            }
            System.out.println("[Atomic] Hilo 2 terminó.");
        }, "Hilo-Atomic-2");

        // Lanzamos ambos hilos simultáneamente
        hilo1Atomic.start();
        hilo2Atomic.start();
        // Esperamos a que ambos terminen
        hilo1Atomic.join();
        hilo2Atomic.join();

        // Leemos el resultado con get() → devuelve el int actual del AtomicInteger
        System.out.println("[Atomic] Resultado obtenido : " + contadorAtomico.get());
        System.out.println("[Atomic] Resultado esperado : " + (INCREMENTOS * 2));
        System.out.println("[Atomic] ¿Correcto? " + (contadorAtomico.get() == INCREMENTOS * 2 ? "✓ SÍ" : "✗ NO"));
        System.out.println();

        // ── PARTE B: Sin sincronización (referencia del Ej. 5) ───────────────

        Thread hilo1Normal = new Thread(() -> {
            for (int i = 0; i < INCREMENTOS; i++) {
                contadorNormal++; // No atómica → race condition
            }
        }, "Hilo-Normal-1");

        Thread hilo2Normal = new Thread(() -> {
            for (int i = 0; i < INCREMENTOS; i++) {
                contadorNormal++; // No atómica → race condition
            }
        }, "Hilo-Normal-2");

        hilo1Normal.start();
        hilo2Normal.start();
        hilo1Normal.join();
        hilo2Normal.join();

        System.out.println("[Normal] Resultado obtenido : " + contadorNormal);
        System.out.println("[Normal] Resultado esperado : " + (INCREMENTOS * 2));
        System.out.println("[Normal] ¿Correcto? " + (contadorNormal == INCREMENTOS * 2 ? "✓ SÍ (casualidad)" : "✗ NO (race condition)"));
        System.out.println();

        // ── Comparación final ─────────────────────────────────────────────────
        System.out.println("=== COMPARACIÓN ===");
        System.out.println("AtomicInteger: " + contadorAtomico.get() + " → SIEMPRE correcto.");
        System.out.println("int normal:    " + contadorNormal + " → resultado impredecible.");
        System.out.println();
        System.out.println("Otros métodos útiles de AtomicInteger:");
        System.out.println("  get()              → leer valor actual");
        System.out.println("  set(n)             → asignar valor");
        System.out.println("  incrementAndGet()  → ++valor (atómico)");
        System.out.println("  decrementAndGet()  → --valor (atómico)");
        System.out.println("  addAndGet(n)       → valor += n (atómico)");
        System.out.println("  compareAndSet(e,n) → si valor==e, asigna n (CAS)");
    }
}