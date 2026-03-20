/*
 * ============================================================
 *  SWINGWORKER - TAREA EN SEGUNDO PLANO CON INTERFAZ GRÁFICA
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa crea una ventana con:
 *   - Un botón "Iniciar proceso"
 *   - Una barra de progreso
 *
 *  FUNCIONAMIENTO:
 *  - Al hacer clic en el botón, se ejecuta una tarea larga
 *    en segundo plano usando SwingWorker.
 *  - La barra de progreso se actualiza sin bloquear la UI.
 *
 *  PROBLEMA:
 *  Si ejecutamos tareas largas en el hilo principal (EDT),
 *  la interfaz se congela.
 *
 *  SOLUCIÓN:
 *  Usar SwingWorker para ejecutar tareas en segundo plano
 *  y actualizar la interfaz de forma segura.
 *
 *  CONCEPTOS CLAVE:
 *  - Swing (JFrame, JButton, JProgressBar)
 *  - Event Dispatch Thread (EDT)
 *  - SwingWorker
 *  - Concurrencia en UI
 *
 * ============================================================
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// ============================================================
// CLASE PRINCIPAL (VENTANA)
// ============================================================
public class Ejercicio_10 extends JFrame {

    // Componentes de la UI
    private JButton boton;
    private JProgressBar barra;

    public Ejercicio_10() {

        // Configuración básica de la ventana
        setTitle("Simulación de Proceso Largo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Layout simple
        setLayout(new FlowLayout());

        // Botón para iniciar el proceso
        boton = new JButton("Iniciar proceso");

        // Barra de progreso (0 a 100)
        barra = new JProgressBar(0, 100);
        barra.setStringPainted(true); // Muestra el %

        // Agregar componentes a la ventana
        add(boton);
        add(barra);

        /*
         * EVENTO DEL BOTÓN
         * ----------------------------------------
         * Al hacer clic, se ejecuta un SwingWorker
         */
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Desactiva el botón para evitar múltiples ejecuciones
                boton.setEnabled(false);

                // Ejecuta el proceso en segundo plano
                new TareaLarga().execute();
            }
        });
    }


    // ============================================================
    // CLASE: TareaLarga (SwingWorker)
    // Ejecuta el proceso en segundo plano
    // ============================================================
    class TareaLarga extends SwingWorker<Void, Integer> {

        /*
         * MÉTODO: doInBackground
         * ----------------------------------------
         * Se ejecuta en un hilo en segundo plano.
         * NO bloquea la interfaz.
         */
        @Override
        protected Void doInBackground() throws Exception {

            // Simulación de tarea larga (0% a 100%)
            for (int i = 0; i <= 100; i += 10) {

                Thread.sleep(500); // Simula trabajo

                // Publica progreso
                setProgress(i);
            }

            return null;
        }

        /*
         * MÉTODO: done
         * ----------------------------------------
         * Se ejecuta cuando termina la tarea.
         * Vuelve al hilo de la UI (EDT).
         */
        @Override
        protected void done() {
            JOptionPane.showMessageDialog(null, "Proceso finalizado");
            boton.setEnabled(true); // Reactiva el botón
        }
    }


    // ============================================================
    // MAIN
    // ============================================================
    public static void main(String[] args) {

        /*
         * IMPORTANTE:
         * Swing debe ejecutarse en el Event Dispatch Thread (EDT)
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Ejercicio_10().setVisible(true);
            }
        });
    }
}