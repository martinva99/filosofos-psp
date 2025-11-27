package src;

import java.util.Random;

public class SolucionDos {
    public static boolean[] cubiertos = new boolean[5];

    // Este es el objeto que van a ir bloqueando los filósofos para controlar el flujo de la app
    public static final Object lock = new Object();

    public static void main(String[] args) {

        for (boolean c : cubiertos) {
            c = false;
        }

        Thread filosofo1 = new Thread(new Filosofo("filosofo1", 4, 0));

        Thread filosofo2 = new Thread(new Filosofo("filosofo2", 0, 1));

        Thread filosofo3 = new Thread(new Filosofo("filosofo3", 1, 2));

        Thread filosofo4 = new Thread(new Filosofo("filosofo4", 2, 3));

        Thread filosofo5 = new Thread(new Filosofo("filosofo5", 3, 4));

        filosofo1.start();
        filosofo2.start();
        filosofo3.start();
        filosofo4.start();
        filosofo5.start();

        // for (int i = 0; i < 100; i++) {
        // if (i % 5 == 0) {
        // System.out.println("\n");
        // System.out.println("Filosofo 2 está " + filosofo2.getState());
        // System.out.println("Filosofo 4 está " + filosofo4.getState());
        // System.out.println("Filosofo 5 está " + filosofo5.getState());
        // System.out.println("\n");
        // }
        // if (i == 99)
        // i = 0;
        // }

    }
}

class Filosofo implements Runnable {
    private final String nombre;
    private final Random r = new Random();
    private final int cubierto1, cubierto2;

    public Filosofo(String nombre, int c1, int c2) {
        this.nombre = nombre;
        this.cubierto1 = c1;
        this.cubierto2 = c2;
    }

    @Override
    public void run() {
        while (true) {
            try {
                getCubiertos();
                System.out.println(">>Filosofo " + nombre + " está comiendo");
                Thread.sleep(r.nextInt(4000) + 1000);
                System.out.println("Filosofo " + nombre + " ha terminado de comer");
                soltarCubiertos();
                Thread.sleep(r.nextInt(4000) + 1000);
            } catch (InterruptedException e) {
                System.out.println("Hilo " + nombre + " ha sido interrumpido");
            }
        }
    }

    private void getCubiertos() {
        /*
         *  Filósofo  Cubierto1   Cubierto2
         *     4          2           3
         * 
         *     5          3           4
         * 
         * Supongamos que ambos despiertan tras un notifyAll():
         * 
         * Filósofo 4 entra en el synchronized(lock) y aún no ha marcado cubierto 2 como
         * ocupado.
         * 
         * Filósofo 5 entra inmediatamente antes de que 4 marque el cubierto 3, y ve que
         * su cubierto 3 está libre.
         * 
         * Resultado: ambos terminan marcando los cubiertos y comen a la vez, aunque
         * comparten el 3.
         * 
         * Solución: Math.min / Math.max -> Ahora todos los filósofos adquieren primero el cubierto de menor índice y luego el de mayor índice
         * 
         * Filósofo 4 entra y marca el cubierto 2; Simultáneamente, el 5 entra y marca el cubierto 3
         * 
         * Filósofo 4 intenta marcar el cubierto 3 pero no puede (ya lo ha marcado el 5);
         * Filósofo 5 marca el cubierto 4
         * 
         * Resultado: Filósofo 5 come antes    
         */
        int minCubierto = Math.min(cubierto1, cubierto2);
        int maxCubierto = Math.max(cubierto1, cubierto2);

        synchronized (SolucionDos.lock) {
            while (SolucionDos.cubiertos[minCubierto] || SolucionDos.cubiertos[maxCubierto]) {
                try {
                    SolucionDos.lock.wait();
                } catch (InterruptedException e) {
                }
            }
            SolucionDos.cubiertos[minCubierto] = true;
            SolucionDos.cubiertos[maxCubierto] = true;
        }

    }

    private synchronized void soltarCubiertos() {
        synchronized (SolucionDos.lock) {
            SolucionDos.cubiertos[cubierto1] = false;
            SolucionDos.cubiertos[cubierto2] = false;
            SolucionDos.lock.notifyAll();
        }
    }

    public String getNombre() {
        return nombre;
    }
}