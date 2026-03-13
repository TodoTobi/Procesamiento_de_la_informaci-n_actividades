// contador global
class Contador {
    private int valor = 0; //si es private no puede ser llamado en inguna parte del codigo fuera de la clase en donde fue creada.

    public synchronized void incrementar() { //esta creando un metodo publico, para habilitar la opcion de ser llamado en todo el codigo
        valor++;
        System.out.println(Thread.currentThread().getName() + " -> contador: " + valor);
    }
}

// implementa Runnable
class TareaContador implements Runnable {

    private Contador contador; 
    
    // esta llamando a la clase anteiror que se llamaba Contador y crea una varibale que tenga las caracteristicas de esa clase
    // de ahi viene la definicion de Objeto y la esencia de los POO

    public TareaContador(Contador contador) { // Es un constructor publico con esos parametros
        this.contador = contador; // Es una forma de abrir una puerta para que el provate pueda recibir datos solo por ese medio
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            contador.incrementar(); // Abre la puerta controlada que llama al metodo incrementar
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido");
            }
        }
    }
}

// Clase principal
public class Main {
    public static void main(String[] args) {

        Contador contador = new Contador(); // Crea un nuevo Objeto usando al metodo contador global 

        Thread hilo1 = new Thread(new TareaContador(contador)); // crea el hilo, le pide que use el meotod runnable, especificamente la peurta controlada
        Thread hilo2 = new Thread(new TareaContador(contador));

        hilo1.start();
        hilo2.start(); // Inicializa los hilos
        
        // los hilos permiten que el programa haga varias cosas al mismo tiempo. 
        // El hilo1 puede estar sumando mientras el hilo2 está esperando o imprimiendo en pantalla.
    }
}