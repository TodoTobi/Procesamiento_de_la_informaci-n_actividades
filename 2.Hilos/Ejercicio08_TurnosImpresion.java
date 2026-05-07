/**
 * EJERCICIO 8 - Turnos de Impresión
 * ====================================
 * Objetivo: Simular 3 hilos que quieren imprimir documentos, pero
 * solo UNO puede usar la impresora a la vez. Usar synchronized.
 *
 * Concepto clave - synchronized:
 *   Cuando un método o bloque está marcado como synchronized sobre un objeto,
 *   solo UN hilo a la vez puede ejecutarlo. Los demás hilos quedan en estado
 *   BLOCKED esperando que el primero libere el "monitor" (lock) del objeto.
 *   Esto garantiza EXCLUSIÓN MUTUA (mutex).
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */
public class Ejercicio08_TurnosImpresion {

    // ══════════════════════════════════════════════════════════════════════════
    // Clase que representa la impresora compartida
    // ══════════════════════════════════════════════════════════════════════════
    static class Impresora {

        // Nombre de la impresora (solo para identificación en los mensajes)
        private final String nombre;

        public Impresora(String nombre) {
            this.nombre = nombre;
        }

        /**
         * Método sincronizado para imprimir un documento.
         * La palabra clave "synchronized" sobre el método de instancia equivale
         * a "synchronized(this)" → el lock es el objeto Impresora.
         * Solo UN hilo puede ejecutar este método a la vez.
         *
         * @param documento  Nombre del documento a imprimir.
         * @param nombreHilo Nombre del hilo/usuario que solicita la impresión.
         */
        public synchronized void imprimir(String documento, String nombreHilo) {
            // En este punto, solo UN hilo a la vez puede estar aquí.
            // Los otros están bloqueados esperando el lock de "this".

            System.out.println("[" + nombre + "] ── " + nombreHilo
                    + " adquirió la impresora.");
            System.out.println("[" + nombre + "] Imprimiendo: '" + documento + "'...");

            try {
                // Simulamos el tiempo que tarda en imprimir (2 segundos)
                // Durante este tiempo, la impresora está "ocupada".
                // Ningún otro hilo puede entrar al método porque está sincronizado.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // El documento terminó de imprimirse
            System.out.println("[" + nombre + "] ✓ '" + documento
                    + "' impreso. " + nombreHilo + " libera la impresora.\n");

            // Al salir del método synchronized, el lock se libera automáticamente
            // y el siguiente hilo en espera puede adquirirlo.
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Clase que representa a cada usuario/hilo que quiere imprimir
    // ══════════════════════════════════════════════════════════════════════════
    static class UsuarioImpresora extends Thread {

        private final Impresora impresora; // Referencia a la impresora compartida
        private final String documento;   // Documento que quiere imprimir

        /**
         * @param nombre    Nombre del usuario (también nombre del hilo).
         * @param impresora La impresora compartida entre todos los hilos.
         * @param documento Nombre del documento a imprimir.
         */
        public UsuarioImpresora(String nombre, Impresora impresora, String documento) {
            super(nombre); // Nombre del Thread
            this.impresora = impresora;
            this.documento = documento;
        }

        @Override
        public void run() {
            // Avisamos que este usuario quiere imprimir
            System.out.println(getName() + " quiere imprimir '" + documento + "'...");

            // Llamamos al método sincronizado.
            // Si la impresora está ocupada, este hilo se bloqueará aquí
            // hasta que el hilo que la usa termine y libere el lock.
            impresora.imprimir(documento, getName());
        }
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== SISTEMA DE TURNOS DE IMPRESIÓN ===\n");

        // Creamos UNA SOLA impresora que será compartida por los 3 hilos.
        // Es fundamental que todos compartan la MISMA instancia para que
        // el lock funcione correctamente. Si cada uno tuviera su propia
        // instancia, el synchronized no los bloquearía entre sí.
        Impresora impresora = new Impresora("Impresora-HP");

        // Creamos 3 hilos/usuarios, cada uno con un documento diferente.
        // Todos apuntan a la MISMA instancia de Impresora.
        UsuarioImpresora usuario1 = new UsuarioImpresora("Usuario-Ana",   impresora, "Informe_Ventas.pdf");
        UsuarioImpresora usuario2 = new UsuarioImpresora("Usuario-Carlos", impresora, "Contrato_2024.docx");
        UsuarioImpresora usuario3 = new UsuarioImpresora("Usuario-Maria",  impresora, "Presentacion.pptx");

        // Lanzamos los 3 hilos casi simultáneamente.
        // Los 3 intentarán acceder al método sincronizado al mismo tiempo,
        // pero solo uno entrará; los otros esperarán su turno.
        usuario1.start();
        usuario2.start();
        usuario3.start();

        // Esperamos a que todos terminen
        usuario1.join();
        usuario2.join();
        usuario3.join();

        System.out.println("=== Todos los documentos fueron impresos. ===");
        System.out.println("Gracias a synchronized, no hubo impresiones simultáneas.");
    }
}