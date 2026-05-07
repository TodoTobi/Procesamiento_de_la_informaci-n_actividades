/**
 * EJERCICIO 12 - Chat Simple entre Hilos
 * =========================================
 * Objetivo: Simular un chat donde Hilo A envía mensajes y Hilo B los recibe,
 * usando wait() y notify() para sincronizar el intercambio.
 *
 * Conceptos clave:
 *   - wait()   : el hilo que llama este método LIBERA el lock del objeto y
 *                queda en estado WAITING hasta que otro hilo llame notify().
 *   - notify() : despierta a UNO de los hilos que están en wait() sobre
 *                el mismo objeto. El hilo despertado pasa a BLOCKED
 *                esperando recuperar el lock.
 *   - notifyAll(): despierta a TODOS los hilos en wait() (más seguro).
 *   - Ambos (wait/notify) SOLO pueden llamarse dentro de un bloque
 *     synchronized sobre el mismo objeto. De lo contrario lanza
 *     IllegalMonitorStateException.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio12_ChatEntreHilos {

    // ══════════════════════════════════════════════════════════════════════════
    // Canal de comunicación compartido entre los hilos
    // ══════════════════════════════════════════════════════════════════════════
    static class CanalChat {

        private String mensaje = null;     // El mensaje actual en el canal (null = vacío)
        private boolean hayMensaje = false; // Flag: true si hay un mensaje sin leer

        /**
         * Envía un mensaje al canal.
         * Si ya hay un mensaje sin leer, el emisor espera (wait) a que
         * el receptor lo consuma.
         *
         * @param texto  El mensaje a enviar.
         * @param emisor Nombre del hilo emisor (para el log).
         */
        public synchronized void enviar(String texto, String emisor) {

            // Si ya hay un mensaje pendiente, esperamos a que sea consumido
            while (hayMensaje) {
                try {
                    // wait() libera el lock y suspende este hilo.
                    // Será despertado por notify() cuando el receptor lea el mensaje.
                    System.out.println("[" + emisor + "] Esperando que el receptor lea el mensaje anterior...");
                    wait(); // Libera el monitor y espera
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // No hay mensaje pendiente: podemos enviar el nuestro
            this.mensaje = texto;
            this.hayMensaje = true; // Marcamos que hay un mensaje nuevo

            System.out.println("[" + emisor + "] ✉ Envió: \"" + texto + "\"");

            // Notificamos al receptor (que puede estar en wait)
            // para que sepa que hay un mensaje nuevo disponible.
            notify(); // Despierta el hilo que esté esperando en este objeto
        }

        /**
         * Recibe un mensaje del canal.
         * Si no hay mensaje disponible, el receptor espera (wait) a que
         * el emisor envíe uno.
         *
         * @param receptor Nombre del hilo receptor (para el log).
         * @return El texto del mensaje recibido.
         */
        public synchronized String recibir(String receptor) {

            // Si NO hay mensaje, esperamos a que el emisor envíe uno
            while (!hayMensaje) {
                try {
                    // wait() libera el lock y suspende este hilo.
                    // Será despertado por notify() cuando el emisor envíe un mensaje.
                    System.out.println("[" + receptor + "] Esperando mensaje...");
                    wait(); // Libera el monitor y espera
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

            // Hay un mensaje disponible: lo leemos y limpiamos el canal
            String mensajeRecibido = this.mensaje;
            this.mensaje = null;   // Limpiamos el mensaje
            this.hayMensaje = false; // Canal vacío nuevamente

            System.out.println("[" + receptor + "] 📩 Recibió: \"" + mensajeRecibido + "\"");

            // Notificamos al emisor (que puede estar esperando para enviar otro)
            notify();

            return mensajeRecibido;
        }
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== CHAT SIMPLE ENTRE HILOS (wait/notify) ===\n");

        CanalChat canal = new CanalChat(); // Canal compartido entre A y B

        // Lista de mensajes que enviará Hilo A
        String[] mensajes = {
            "¡Hola Hilo B!",
            "¿Cómo estás?",
            "¿Pudiste resolver el ejercicio?",
            "Yo lo hice con wait() y notify()",
            "FIN" // Mensaje especial que indica fin de la conversación
        };

        // ══════════════════════════════════════════════════════════════════════
        // HILO A: Emisor - envía mensajes al canal
        // ══════════════════════════════════════════════════════════════════════
        Thread hiloA = new Thread(() -> {
            for (String msg : mensajes) {
                canal.enviar(msg, "Hilo-A"); // Envía cada mensaje usando wait/notify

                try {
                    // Pausa entre mensajes para simular que un humano escribe
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[Hilo-A] Conversación finalizada.");
        }, "Hilo-A");

        // ══════════════════════════════════════════════════════════════════════
        // HILO B: Receptor - recibe y procesa mensajes del canal
        // ══════════════════════════════════════════════════════════════════════
        Thread hiloB = new Thread(() -> {
            while (true) {
                // Recibe el siguiente mensaje (espera si no hay ninguno)
                String mensajeRecibido = canal.recibir("Hilo-B");

                if (mensajeRecibido == null) break; // Interrumpido

                // Si recibe "FIN", termina la conversación
                if ("FIN".equals(mensajeRecibido)) {
                    System.out.println("[Hilo-B] Mensaje de cierre recibido. Terminando.");
                    break;
                }

                // Procesa el mensaje (aquí simplemente responde)
                System.out.println("[Hilo-B] 💬 Procesando: \"" + mensajeRecibido + "\"");
                System.out.println();
            }
            System.out.println("[Hilo-B] Conversación finalizada.");
        }, "Hilo-B");

        // Iniciamos ambos hilos
        hiloB.start(); // Iniciamos el receptor primero (ya estará en wait esperando)
        hiloA.start(); // Luego el emisor

        // Esperamos a que ambos terminen
        hiloA.join();
        hiloB.join();

        System.out.println("\n=== Chat terminado. ===");
        System.out.println("wait() y notify() sincronizaron el intercambio de mensajes.");
        System.out.println("Nunca hubo un mensaje leído dos veces ni perdido.");
    }
}