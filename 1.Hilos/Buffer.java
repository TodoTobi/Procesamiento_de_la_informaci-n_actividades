/*
 * ============================================================
 *  PROBLEMA PRODUCTOR - CONSUMIDOR CON wait() / notify()
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa simula un sistema donde:
 *   - Un hilo PRODUCTOR genera números aleatorios
 *   - Un hilo CONSUMIDOR toma esos números y los imprime
 *
 *  PROBLEMA:
 *  El consumidor no debe consumir si no hay datos disponibles,
 *  y el productor no debe sobreescribir datos si aún no fueron consumidos.
 *
 *  SOLUCIÓN:
 *  Se utiliza:
 *   - wait()  -> para que un hilo espere
 *   - notify() -> para despertar al otro hilo
 *
 *  CONCEPTOS CLAVE:
 *  - Comunicación entre hilos
 *  - Monitores (synchronized)
 *  - wait / notify
 *  - Buffer compartido
 *
 * ============================================================
 */

import java.util.Random;


// ============================================================
// CLASE: Buffer
// Representa el espacio compartido entre productor y consumidor
// ============================================================
class Buffer {

    // Dato compartido (solo uno para simplificar)
    private int numero;

    // Indica si hay dato disponible para consumir
    private boolean disponible = false;

    /*
     * MÉTODO: producir
     * ----------------------------------------
     * El productor genera un número y lo guarda en el buffer.
     *
     * Si ya hay un número sin consumir, el productor espera.
     */
    public synchronized void producir(int valor) {

        // Mientras haya un dato sin consumir, el productor espera
        while (disponible) {
            try {
                wait(); // Libera el monitor y espera
            } catch (InterruptedException e) {
                System.out.println("Error en productor");
            }
        }

        // Se produce el dato
        numero = valor;
        System.out.println("Productor generó: " + numero);

        // Marca que ahora hay un dato disponible
        disponible = true;

        // Notifica al consumidor que ya puede consumir
        notify();
    }

    /*
     * MÉTODO: consumir
     * ----------------------------------------
     * El consumidor toma el número del buffer.
     *
     * Si no hay dato disponible, el consumidor espera.
     */
    public synchronized int consumir() {

        // Mientras no haya datos, el consumidor espera
        while (!disponible) {
            try {
                wait(); // Libera el monitor y espera
            } catch (InterruptedException e) {
                System.out.println("Error en consumidor");
            }
        }

        // Consume el dato
        System.out.println("Consumidor tomó: " + numero);

        // Marca que el buffer está libre
        disponible = false;

        // Notifica al productor que puede generar otro número
        notify();

        return numero;
    }
}


// ============================================================
// CLASE: Productor
// Genera números aleatorios continuamente
// ============================================================
class Productor implements Runnable {

    private Buffer buffer;
    private Random random = new Random();

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        // Genera datos continuamente
        for (int i = 0; i < 5; i++) {

            int numero = random.nextInt(100); // número entre 0 y 99

            // Envía el dato al buffer
            buffer.producir(numero);

            try {
                Thread.sleep(500); // Simula tiempo de producción
            } catch (InterruptedException e) {
                System.out.println("Error en productor");
            }
        }
    }
}


// ============================================================
// CLASE: Consumidor
// Consume los números generados por el productor
// ============================================================
class Consumidor implements Runnable {

    private Buffer buffer;

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        // Consume datos continuamente
        for (int i = 0; i < 5; i++) {

            buffer.consumir();

            try {
                Thread.sleep(700); // Simula tiempo de consumo
            } catch (InterruptedException e) {
                System.out.println("Error en consumidor");
            }
        }
    }
}


// ============================================================
// CLASE PRINCIPAL
// ============================================================
public class Main {

    public static void main(String[] args) {

        // Buffer compartido entre productor y consumidor
        Buffer buffer = new Buffer();

        // Creación de hilos
        Thread productor = new Thread(new Productor(buffer), "Productor");
        Thread consumidor = new Thread(new Consumidor(buffer), "Consumidor");

        // Inicio de ejecución
        productor.start();
        consumidor.start();
    }
}