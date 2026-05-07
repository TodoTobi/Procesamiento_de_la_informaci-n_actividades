/**
 * EJERCICIO 14 - Sistema de Pedidos (Tipo App Real) + SwingWorker
 * ================================================================
 * PARTE A: Patrón Productor-Consumidor.
 *   Clientes (hilos) hacen pedidos que se colocan en una cola compartida
 *   (BlockingQueue). Un "cocinero" consume y procesa esos pedidos.
 *
 * PARTE B: Interfaz Swing con barra de progreso usando SwingWorker.
 *   Un botón lanza una tarea pesada. SwingWorker la ejecuta en un hilo
 *   de fondo y actualiza la barra de progreso en el hilo EDT de Swing.
 *
 * Conceptos clave:
 *   - BlockingQueue: cola thread-safe. put() bloquea si está llena.
 *                   take() bloquea si está vacía. Ideal para productor-consumidor.
 *   - LinkedBlockingQueue: implementación de BlockingQueue con lista enlazada.
 *   - SwingWorker<T,V>: clase para ejecutar tareas largas fuera del EDT
 *                       y comunicar progreso de forma segura a Swing.
 *   - EDT (Event Dispatch Thread): el único hilo donde Swing permite
 *                       actualizar la interfaz gráfica.
 *
 * Materia: Laboratorio de Desarrollo de Aplicaciones Informáticas
 * Escuela Técnica N°20 - Polo Educativo Mataderos
 * Profesor: Fernandez Bogarin, Nicolas Nahuel
 */

import java.util.concurrent.BlockingQueue;       // Interfaz de cola bloqueante
import java.util.concurrent.LinkedBlockingQueue; // Implementación de cola enlazada
import java.util.concurrent.TimeUnit;            // Para poll() con timeout
import javax.swing.*;                            // JFrame, JButton, JProgressBar, etc.
import java.awt.*;                               // BorderLayout, FlowLayout

public class Ejercicio14_SistemaPedidos {

    // ══════════════════════════════════════════════════════════════════════════
    // PARTE A: SISTEMA DE PEDIDOS (Productor-Consumidor)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Clase que encapsula un pedido individual de un cliente.
     */
    static class Pedido {
        private final int numero;         // Número único del pedido
        private final String cliente;     // Nombre del cliente que lo hizo
        private final String producto;    // Qué pidió

        public Pedido(int numero, String cliente, String producto) {
            this.numero = numero;
            this.cliente = cliente;
            this.producto = producto;
        }

        @Override
        public String toString() {
            return "Pedido#" + numero + " [" + cliente + " → " + producto + "]";
        }
    }

    /**
     * Hilo Cliente: genera pedidos y los coloca en la cola compartida.
     * Actúa como "Productor" en el patrón Productor-Consumidor.
     */
    static class Cliente extends Thread {

        private final BlockingQueue<Pedido> cola; // Cola compartida con el cocinero
        private final String nombreCliente;        // Nombre de este cliente
        private final String[] menu;               // Productos disponibles

        public Cliente(String nombre, BlockingQueue<Pedido> cola) {
            super(nombre);
            this.nombreCliente = nombre;
            this.cola = cola;
            this.menu = new String[]{"Pizza", "Hamburguesa", "Sushi", "Pasta", "Tacos"};
        }

        @Override
        public void run() {
            // Cada cliente hace 3 pedidos
            for (int i = 1; i <= 3; i++) {
                // Seleccionamos un producto aleatoriamente del menú
                String producto = menu[(int)(Math.random() * menu.length)];
                Pedido pedido = new Pedido(hashCode() + i, nombreCliente, producto);

                try {
                    System.out.println("[" + nombreCliente + "] 🛒 Enviando: " + pedido);
                    // put() coloca el pedido en la cola.
                    // Si la cola está llena (capacidad máxima alcanzada), BLOQUEA
                    // hasta que el cocinero libere espacio consumiendo un pedido.
                    cola.put(pedido);
                    Thread.sleep((long)(Math.random() * 500) + 200); // Pausa entre pedidos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[" + nombreCliente + "] ✓ Terminó de hacer pedidos.");
        }
    }

    /**
     * Hilo Cocinero: consume pedidos de la cola y los "cocina".
     * Actúa como "Consumidor" en el patrón Productor-Consumidor.
     */
    static class Cocinero extends Thread {

        private final BlockingQueue<Pedido> cola; // Cola compartida con los clientes
        private volatile boolean activo = true;    // Flag para detener el cocinero

        public Cocinero(BlockingQueue<Pedido> cola) {
            super("Cocinero");
            this.cola = cola;
        }

        /** Señala al cocinero que debe detenerse cuando la cola esté vacía. */
        public void detener() { activo = false; }

        @Override
        public void run() {
            System.out.println("[Cocinero] 👨‍🍳 Abrió la cocina. Esperando pedidos...\n");

            while (activo || !cola.isEmpty()) {
                try {
                    // poll(tiempo, unidad): intenta tomar un pedido.
                    // Si la cola está vacía, espera hasta 1 segundo.
                    // Retorna null si no llegó ningún pedido en ese tiempo.
                    // (Mejor que take() porque permite verificar la bandera 'activo')
                    Pedido pedido = cola.poll(1, TimeUnit.SECONDS);

                    if (pedido != null) {
                        System.out.println("[Cocinero] 🍳 Procesando: " + pedido + "...");
                        // Simulamos el tiempo de cocina (1-2 segundos)
                        Thread.sleep((long)(Math.random() * 1000) + 1000);
                        System.out.println("[Cocinero] ✓ Listo: " + pedido + "\n");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("[Cocinero] 🔒 Cerró la cocina. Todos los pedidos procesados.");
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // PARTE B: SWING CON SWINGWORKER Y BARRA DE PROGRESO
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * SwingWorker<String, Integer>:
     *   - String: tipo del resultado final (retornado por doInBackground y get())
     *   - Integer: tipo de los valores de progreso intermedios (publish/process)
     */
    static class TareaPesada extends SwingWorker<String, Integer> {

        private final JProgressBar barraProgreso; // Referencia a la barra de la UI
        private final JLabel etiquetaEstado;       // Etiqueta de estado

        public TareaPesada(JProgressBar barra, JLabel etiqueta) {
            this.barraProgreso = barra;
            this.etiquetaEstado = etiqueta;
        }

        /**
         * doInBackground(): se ejecuta en un HILO DE FONDO (no en el EDT).
         * Aquí va el trabajo pesado (cálculos, descargas, etc.).
         * NO modificar la UI directamente desde aquí (usar publish/setProgress).
         */
        @Override
        protected String doInBackground() throws Exception {
            setProgress(0); // Inicializamos el progreso en 0%

            // Simulamos una tarea pesada dividida en 10 pasos
            for (int paso = 1; paso <= 10; paso++) {
                Thread.sleep(400); // Simulamos trabajo (400ms por paso)

                // publish() envía un valor intermedio al método process()
                // que corre en el EDT → seguro para actualizar la UI
                publish(paso * 10);

                // setProgress() actualiza la propiedad "progress" del SwingWorker
                // (para quienes usen PropertyChangeListener)
                setProgress(paso * 10);
            }

            return "Tarea completada exitosamente."; // Resultado final
        }

        /**
         * process(): se ejecuta en el EDT (Event Dispatch Thread).
         * Recibe los valores publicados por publish() y actualiza la UI.
         * Es SEGURO actualizar componentes Swing aquí.
         *
         * @param chunks Lista de valores publicados (pueden llegar varios a la vez)
         */
        @Override
        protected void process(java.util.List<Integer> chunks) {
            // Tomamos el último valor (el más reciente)
            int ultimoProgreso = chunks.get(chunks.size() - 1);
            // Actualizamos la barra de progreso en el EDT (seguro)
            barraProgreso.setValue(ultimoProgreso);
            etiquetaEstado.setText("Progreso: " + ultimoProgreso + "%");
        }

        /**
         * done(): se ejecuta en el EDT cuando doInBackground() termina.
         * Ideal para mostrar el resultado final o habilitar botones.
         */
        @Override
        protected void done() {
            try {
                String resultado = get(); // Obtiene el valor retornado por doInBackground()
                etiquetaEstado.setText("✓ " + resultado);
                barraProgreso.setValue(100);
            } catch (Exception e) {
                etiquetaEstado.setText("Error: " + e.getMessage());
            }
        }
    }

    /** Crea y muestra la ventana Swing con el botón y la barra de progreso. */
    static void crearVentanaSwing() {
        // Swing debe crearse siempre en el EDT usando invokeLater
        SwingUtilities.invokeLater(() -> {

            // ── Creamos la ventana principal ──────────────────────────────────
            JFrame frame = new JFrame("Sistema de Pedidos - SwingWorker");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new BorderLayout(10, 10));

            // ── Componentes de la UI ──────────────────────────────────────────
            JButton btnIniciar = new JButton("▶ Iniciar Tarea Pesada");
            JProgressBar barra = new JProgressBar(0, 100); // De 0 a 100%
            barra.setStringPainted(true);  // Muestra el % dentro de la barra
            barra.setValue(0);

            JLabel etiqueta = new JLabel("Presioná el botón para iniciar.", SwingConstants.CENTER);

            // ── Panel de controles ────────────────────────────────────────────
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(btnIniciar);

            // ── Agregar componentes al frame ──────────────────────────────────
            frame.add(etiqueta,  BorderLayout.NORTH);
            frame.add(barra,     BorderLayout.CENTER);
            frame.add(panel,     BorderLayout.SOUTH);

            // ── Listener del botón ────────────────────────────────────────────
            btnIniciar.addActionListener(e -> {
                btnIniciar.setEnabled(false); // Deshabilitamos el botón mientras trabaja
                etiqueta.setText("Procesando...");
                barra.setValue(0);

                // Creamos y ejecutamos el SwingWorker
                // execute() lanza doInBackground() en un hilo del pool de Swing
                new TareaPesada(barra, etiqueta).execute();
            });

            frame.setLocationRelativeTo(null); // Centramos la ventana
            frame.setVisible(true); // Mostramos la ventana
        });
    }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== PARTE A: SISTEMA DE PEDIDOS (Productor-Consumidor) ===\n");

        // Cola de capacidad máxima 5 (si hay más de 5 pedidos, los clientes esperan)
        BlockingQueue<Pedido> cola = new LinkedBlockingQueue<>(5);

        // Creamos 3 clientes (productores) y 1 cocinero (consumidor)
        Cliente cliente1 = new Cliente("Ana",    cola);
        Cliente cliente2 = new Cliente("Carlos", cola);
        Cliente cliente3 = new Cliente("Maria",  cola);
        Cocinero cocinero = new Cocinero(cola);

        // Iniciamos el cocinero primero para que esté listo
        cocinero.start();
        // Luego los clientes
        cliente1.start();
        cliente2.start();
        cliente3.start();

        // Esperamos a que todos los clientes terminen de hacer sus pedidos
        cliente1.join();
        cliente2.join();
        cliente3.join();

        System.out.println("\nTodos los clientes terminaron. Señalizando al cocinero...");
        // Señalamos al cocinero que no habrá más pedidos nuevos
        cocinero.detener();
        // Esperamos a que termine de procesar los pedidos pendientes
        cocinero.join();

        System.out.println("\n=== PARTE A COMPLETADA ===\n");
        System.out.println("=== PARTE B: SWING CON SWINGWORKER ===");
        System.out.println("Abriendo ventana Swing con barra de progreso...");
        System.out.println("(Cerrá la ventana para terminar el programa)\n");

        // Lanzamos la ventana Swing (Parte B)
        crearVentanaSwing();
    }
}