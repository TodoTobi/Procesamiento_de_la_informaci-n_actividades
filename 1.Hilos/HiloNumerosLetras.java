// 1. La clase del hilo se queda igual
class HiloContador extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido");
            }
        }
    }
}
 
class HiloContadorLetra extends Thread {
    @Override
    public void run() {
        char letra = 'A';
        for (int i = 1; i <= 5; i++) {
            System.out.println(letra);
            letra++;
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido");
            }
        }
    }
}

// 2. CAMBIAMOS el nombre de esta clase para que no choque
public class HiloNumerosLetras { 
    public static void main(String[] args) {
        HiloContador miHilo = new HiloContador();
        HiloContadorLetra mihiloletra = new HiloContadorLetra();
        miHilo.start(); 
        mihiloletra.start();
    }
}
