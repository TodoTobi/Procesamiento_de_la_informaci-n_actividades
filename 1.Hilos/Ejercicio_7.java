/*
 * ============================================================
 *  USO DE Callable Y Future EN JAVA
 * ============================================================
 *
 *  DESCRIPCIÓN GENERAL:
 *  Este programa ejecuta una tarea en un hilo que calcula
 *  la suma de los primeros 100 números (1 + 2 + ... + 100)
 *  y retorna el resultado.
 *
 *  PROBLEMA:
 *  Runnable NO permite devolver resultados.
 *
 *  SOLUCIÓN:
 *  Se utiliza:
 *   - Callable<Integer> -> permite retornar un valor
 *   - Future<Integer>   -> permite obtener el resultado
 *
 *  CONCEPTOS CLAVE:
 *  - Callable
 *  - Future
 *  - ExecutorService
 *  - Programación concurrente con retorno de datos
 *
 * ============================================================
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


// ============================================================
// CLASE: TareaSuma
// Implementa Callable para devolver un resultado
// ============================================================
class TareaSuma implements Callable<Integer> {

    /*
     * MÉTODO: call
     * ----------------------------------------
     * Es equivalente a run() pero:
     * - Puede devolver un valor
     * - Puede lanzar excepciones
     */
    @Override
    public Integer call() throws Exception {

        int suma = 0;

        // Cálculo de la suma de 1 a 100
        for (int i = 1; i <= 100; i++) {
            suma += i;
        }

        // Simulación de tiempo de procesamiento
        Thread.sleep(2000);

        System.out.println("Cálculo finalizado en " +
                Thread.currentThread().getName());

        // Retorna el resultado
        return suma;
    }
}


// ============================================================
// CLASE PRINCIPAL
// ============================================================
public class Ejercicio_7 {

    public static void main(String[] args) {

        /*
         * Creación del ExecutorService
         * ----------------------------------------
         * Se usa un pool de 1 hilo porque solo tenemos una tarea.
         */
        ExecutorService executor = Executors.newFixedThreadPool(1);

        /*
         * Envío de la tarea
         * ----------------------------------------
         * submit() devuelve un Future que representa el resultado.
         */
        Future<Integer> resultado = executor.submit(new TareaSuma());

        try {
            /*
             * MÉTODO: get()
             * ----------------------------------------
             * Obtiene el resultado de la tarea.
             *
             * IMPORTANTE:
             * - Bloquea (espera) hasta que el hilo termine.
             */
            System.out.println("Esperando resultado...");

            int sumaFinal = resultado.get();

            System.out.println("Resultado de la suma: " + sumaFinal);

        } catch (Exception e) {
            System.out.println("Error al obtener el resultado");
        }

        /*
         * Cierre del ExecutorService
         */
        executor.shutdown();
    }
}