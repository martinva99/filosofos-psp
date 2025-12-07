package src;

import java.util.Random;

public class EjemploCrudo {

    public static boolean[] cubiertos = new boolean[5];

    // Colores ANSI más vistosos (alta intensidad)
    public static final String RESET          = "\u001B[0m";
    public static final String BRIGHT_RED     = "\u001B[91m";
    public static final String BRIGHT_GREEN   = "\u001B[92m";
    public static final String BRIGHT_YELLOW  = "\u001B[93m";
    public static final String BRIGHT_BLUE    = "\u001B[94m";
    public static final String BRIGHT_MAGENTA = "\u001B[95m";

    public static void main(String[] args) {

        // Inicializar cubiertos a false (aunque ya lo están por defecto)
        for (int i = 0; i < cubiertos.length; i++) {
            cubiertos[i] = false;
        }

        System.out.println(BRIGHT_RED + "Colores asignados a filósofos:" + RESET);
        System.out.println(BRIGHT_RED    + "Filósofo 1" + RESET);
        System.out.println(BRIGHT_GREEN  + "Filósofo 2" + RESET);
        System.out.println(BRIGHT_YELLOW + "Filósofo 3" + RESET);
        System.out.println(BRIGHT_BLUE   + "Filósofo 4" + RESET);
        System.out.println(BRIGHT_MAGENTA+ "Filósofo 5" + RESET);

        Thread filosofo1 = new Thread(new FilosofoMal("filosofo1", 4, 0, BRIGHT_RED));
        Thread filosofo2 = new Thread(new FilosofoMal("filosofo2", 0, 1, BRIGHT_GREEN));
        Thread filosofo3 = new Thread(new FilosofoMal("filosofo3", 1, 2, BRIGHT_YELLOW));
        Thread filosofo4 = new Thread(new FilosofoMal("filosofo4", 2, 3, BRIGHT_BLUE));
        Thread filosofo5 = new Thread(new FilosofoMal("filosofo5", 3, 4, BRIGHT_MAGENTA));

        filosofo1.start();
        filosofo2.start();
        filosofo3.start();
        filosofo4.start();
        filosofo5.start();
    }
}

class FilosofoMal implements Runnable {

    private final String nombre;
    private final Random r = new Random();
    private final int cubierto1, cubierto2;
    private final String color;

    public FilosofoMal(String nombre, int c1, int c2, String color) {
        this.nombre = nombre;
        this.cubierto1 = c1;
        this.cubierto2 = c2;
        this.color = color;
    }

    @Override
    public void run() {
        while (true) {
            try {
                cogerCubiertos();
                System.out.println(color + ">> Filósofo " + nombre + " está comiendo" + EjemploCrudo.RESET);
                Thread.sleep(r.nextInt(4000) + 1000);
                System.out.println(color + "Filósofo " + nombre + " ha terminado de comer" + EjemploCrudo.RESET);
                soltarCubiertos();
                Thread.sleep(r.nextInt(4000) + 1000);
            } catch (InterruptedException e) {
                System.out.println(color + "Hilo " + nombre + " ha sido interrumpido" + EjemploCrudo.RESET);
            }
        }
    }

    private void cogerCubiertos() {
        EjemploCrudo.cubiertos[cubierto1] = true;
        EjemploCrudo.cubiertos[cubierto2] = true;
    }

    private void soltarCubiertos() {
        EjemploCrudo.cubiertos[cubierto1] = false;
        EjemploCrudo.cubiertos[cubierto2] = false;
    }
}
