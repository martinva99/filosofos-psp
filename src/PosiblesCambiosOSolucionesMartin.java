package src;

import java.util.Random;

public class PosiblesCambiosOSolucionesMartin {
    public static boolean[] cubiertos = new boolean[5];

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
                cogerCubiertos(cubierto1, cubierto2);
                System.out.println(">>Filosofo " + nombre + " está comiendo");
                Thread.sleep(r.nextInt(4000) + 1000);
                System.out.println("Filosofo " + nombre + " ha terminado de comer");
                soltarCubiertos(cubierto1, cubierto2);
                Thread.sleep(r.nextInt(4000) + 1000);
                System.out.println(">Filosofo " + nombre + " está pensando");
            } catch (InterruptedException e) {
                System.out.println("Hilo " + nombre + " ha sido interrumpido");
            }
        }
    }

    private synchronized void cogerCubiertos(int c1, int c2) {
        /*
         * Mientras alguno de sus dos cubiertos sean 'true' (están siendo usados), espera
         */
        if (PosiblesCambiosOSolucionesMartin.cubiertos[c1] || PosiblesCambiosOSolucionesMartin.cubiertos[c2]) {
            try {
                System.out.println("Filosofo " + nombre + " esperando");
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    private synchronized void soltarCubiertos(int c1, int c2) {
        PosiblesCambiosOSolucionesMartin.cubiertos[c1] = false;
        PosiblesCambiosOSolucionesMartin.cubiertos[c2] = false;
        notifyAll();
    }
}
