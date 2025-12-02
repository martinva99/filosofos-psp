package src;

import java.util.Random;

/**
 * <p>
 * Clase que contiene una <b>segunda solución</b> alternativa al problema de los
 * filósofos.
 * </p>
 * 
 * @see Filosofo
 * @author Martín y Ayur
 * @version 2
 * @since 2025-11-27
 */
public class SolucionDos {
    /**
     * <p>
     * Array de booleanos que representan los cubiertos.
     * </p>
     * Si su valor es <i>true</i>, están ocupados por otro filósofo
     */
    public static boolean[] cubiertos = new boolean[5];

    /**
     * <p>
     * Este es el objeto que van a ir bloqueando los filósofos para controlar el
     * flujo de la app
     * </p>
     * Lo usan los métodos marcados con <i>synchronized</i>
     */
    public static final Object lock = new Object();

    /**
     * <p>
     * Método main donde se inicializan los filósofos e, inicialmente, se marcan
     * todos los cubiertos como libres (false)
     * </p>
     * 
     * @param args
     */
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

/**
 * <p>
 * Clase <b>Filósofo</b> que implementa Runnable (son hilos) y pertenece a
 * <b>{@link SolucionDos}</b>
 * </p>
 * 
 * @see SolucionDos
 * 
 * 
 */
class Filosofo implements Runnable {
    /**
     * Sus atributos son:
     * <ul>
     * <li>String nombre</li>
     * <li>Random r (randomiza el tiempo de comer/pensar)</li>
     * <li>int cubierto1, cubierto2</li>
     * </ul>
     */
    private final String nombre;
    private final Random r = new Random();
    private final int cubierto1, cubierto2;

    public Filosofo(String nombre, int c1, int c2) {
        this.nombre = nombre;
        this.cubierto1 = c1;
        this.cubierto2 = c2;
    }

    /**
     * <p>
     * Método que se ejecuta al iniciar los hilos.
     * </p>
     * Controla el flujo de vida de los filósofos:
     * <ol>
     * <li>Intentan coger los cubiertos (si no pueden se quedan en estado
     * <b>WAITING</b>)</li>
     * <li>Comen</li>
     * <li>Dejan los cubiertos</li>
     * <li>Piensan</li>
     * </ol>
     */
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

    /**
     * <p>
     * Método llamado por los filósofos para intentar obtener los cubiertos
     * </p>
     */
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

    /**
     * <p>
     * Método llamado por los filósofos para intentar liberar los cubiertos
     * </p>
     */
    private synchronized void soltarCubiertos() {
        synchronized (SolucionDos.lock) {
            SolucionDos.cubiertos[cubierto1] = false;
            SolucionDos.cubiertos[cubierto2] = false;
            SolucionDos.lock.notifyAll();
        }
    }

    /**
     * Método usado para realizar las pruebas
     * 
     * @return String
     */
    public String getNombre() {
        return nombre;
    }
}