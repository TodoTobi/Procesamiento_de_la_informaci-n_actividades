// 1. La clase del hilo se queda igual
class HiloContador extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Número: " + i);
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido");
            }
        }
    }
}

// 2. CAMBIAMOS el nombre de esta clase para que no choque
public class HiloNumeros { 
    public static void main(String[] args) {
        HiloContador miHilo = new HiloContador();
        miHilo.start(); 
    }
}
