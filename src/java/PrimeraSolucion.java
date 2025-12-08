package src.java;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * Solución simple al problema de los filósofos sentados alrededor de una mesa
 * que comparten cubiertos.
 * </p>
 * @author Martín y Ayur
 * @version 1.0
 * @since 2025-11-27
 */

public class PrimeraSolucion {
    /**
     * Cubiertos representados por bloqueos ReentrantLocks.
     * Cada cubierto es compartido entre dos filósofos.
     *
     * Se crea cada {@link ReentrantLock} con fairness {@code true} para favorecer
     * que el hilo que lleva más tiempo esperando obtenga el bloqueo primero.
     * Esto pretende conseguir que ninguno se quede sin comer.
     */
    private static final ReentrantLock cubierto1 = new ReentrantLock(true);
    private static final ReentrantLock cubierto2 = new ReentrantLock(true);
    private static final ReentrantLock cubierto3 = new ReentrantLock(true);
    private static final ReentrantLock cubierto4 = new ReentrantLock(true);
    private static final ReentrantLock cubierto5 = new ReentrantLock(true);

    /**
     * Punto de entrada de la aplicación.
     *
     * <p>
     * Crea y arranca 5 hilos (filósofos). Cada hilo ejecuta un bucle infinito
     * en el que:
     * <ol>
     * <li>Adquiere los dos cubiertos necesarios (bloqueos).</li>
     * <li>Simula la acción de comer durante medio segundo.</li>
     * <li>Libera los cubiertos.</li>
     * <li>Pensa durante un tiempo aleatorio entre 1 y 5 segundos.</li>
     * </ol>
     * </p>
     */
    public static void main(String[] args) {
        // Filósofo 1: usa cubierto1 (izq) y cubierto5 (der)
        Thread filosofo1 = new Thread(() -> {
            while (true) {
                // Adquirir ambos cubiertos
                cubierto1.lock();
                cubierto5.lock();
                try {
                    System.out.println("Filosofo 1 comiendo");
                    Thread.sleep(500);
                    System.out.println("Filosofo 1 termina de comer");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cubierto1.unlock();
                    cubierto5.unlock();
                }
                try {
                    int tiempo = ThreadLocalRandom.current().nextInt(1000, 5000);
                    System.out.println("Filosofo 1 pensando");
                    Thread.currentThread().sleep(tiempo);
                    System.out.println("Filosofo 1 termina de pensar");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Filósofo 2: usa cubierto1 (izq) y cubierto2 (der)
        Thread filosofo2 = new Thread(() -> {
            while (true) {
                cubierto1.lock();
                cubierto2.lock();
                try {
                    System.out.println("Filosofo 2 comiendo");
                    Thread.sleep(500);
                    System.out.println("Filosofo 2 termina de comer");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cubierto1.unlock();
                    cubierto2.unlock();
                }
                try {
                    int tiempo = ThreadLocalRandom.current().nextInt(1000, 5000);
                    System.out.println("Filosofo 2 pensando");
                    Thread.currentThread().sleep(tiempo);
                    System.out.println("Filosofo 2 termina de pensar");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Filósofo 3: usa cubierto2 (izq) y cubierto3 (der)
        Thread filosofo3 = new Thread(() -> {
            while (true) {
                cubierto2.lock();
                cubierto3.lock();
                try {
                    System.out.println("Filosofo 3 comiendo");
                    Thread.sleep(500);
                    System.out.println("Filosofo 3 termina de comer");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cubierto2.unlock();
                    cubierto3.unlock();
                }
                try {
                    int tiempo = ThreadLocalRandom.current().nextInt(1000, 5000);
                    System.out.println("Filosofo 3 pensando");
                    Thread.currentThread().sleep(tiempo);
                    System.out.println("Filosofo 3 termina de pensar");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Filósofo 4: usa cubierto3 (izq) y cubierto4 (der)
        Thread filosofo4 = new Thread(() -> {
            while (true) {
                cubierto3.lock();
                cubierto4.lock();
                try {
                    System.out.println("Filosofo 4 comiendo");
                    Thread.sleep(500);
                    System.out.println("Filosofo 4 termina de comer");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cubierto3.unlock();
                    cubierto4.unlock();
                }
                try {
                    int tiempo = ThreadLocalRandom.current().nextInt(1000, 5000);
                    System.out.println("Filosofo 4 pensando");
                    Thread.currentThread().sleep(tiempo);
                    System.out.println("Filosofo 4 termina de pensar");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Filósofo 5: usa cubierto4 (izq) y cubierto5 (der)
        Thread filosofo5 = new Thread(() -> {
            while (true) {
                cubierto4.lock();
                cubierto5.lock();
                try {
                    System.out.println("Filosofo 5 comiendo");
                    Thread.sleep(500);
                    System.out.println("Filosofo 5 termina de comer");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cubierto4.unlock();
                    cubierto5.unlock();
                }
                try {
                    int tiempo = ThreadLocalRandom.current().nextInt(1000, 5000);
                    System.out.println("Filosofo 5 pensando");
                    Thread.currentThread().sleep(tiempo);
                    System.out.println("Filosofo 5 termina de pensar");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Arrancar los hilos (filósofos)
        filosofo1.start();
        filosofo2.start();
        filosofo3.start();
        filosofo4.start();
        filosofo5.start();
    }
}
