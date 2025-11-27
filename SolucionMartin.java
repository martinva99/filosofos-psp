import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class SolucionMartin {
    private static final ReentrantLock cubierto1 = new ReentrantLock(true);
    private static final ReentrantLock cubierto2 = new ReentrantLock(true);
    private static final ReentrantLock cubierto3 = new ReentrantLock(true);
    private static final ReentrantLock cubierto4 = new ReentrantLock(true);
    private static final ReentrantLock cubierto5 = new ReentrantLock(true);

    public static void main(String[] args) {
        Thread filosofo1 = new Thread(() -> {
            while(true){
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

        Thread filosofo2 = new Thread(() -> {
            while(true){
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

        Thread filosofo3 = new Thread(() -> {
            while(true){
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

        Thread filosofo4 = new Thread(() -> {
            while (true){
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

        Thread filosofo5 = new Thread(() -> {
            while (true){
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
        
        filosofo1.start();
        filosofo2.start();
        filosofo3.start();
        filosofo4.start();
        filosofo5.start();

    }
}
