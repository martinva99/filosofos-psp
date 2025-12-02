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
        synchronized (SolucionDos.lock) {
            while (SolucionDos.cubiertos[cubierto1] || SolucionDos.cubiertos[cubierto2]) {
                try {
                    SolucionDos.lock.wait();
                } catch (InterruptedException e) {
                }
            }
            SolucionDos.cubiertos[cubierto1] = true;
            SolucionDos.cubiertos[cubierto2] = true;
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