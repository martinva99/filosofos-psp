import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <p>
 * Clase que contiene una <b>segunda solución</b> alternativa al problema de los
 * filósofos.
 * </p>
 *
 * @author Martín y Ayur
 * @version 3
 * @see Filosofo
 * @since 2025-11-27
 */
public class SolucionDos extends Application implements Initializable {
    /**
     * <p>
     * Array de booleanos que representan los cubiertos.
     * </p>
     * Si su valor es <i>true</i>, están ocupados por otro filósofo
     */
    public static boolean[] cubiertos = new boolean[5];

    @FXML
    private Line cub1;

    @FXML
    private Line cub2;

    @FXML
    private Line cub3;

    @FXML
    private Line cub4;

    @FXML
    private Line cub5;
    @FXML
    private Label lblF1;

    @FXML
    private Label lblF2;

    @FXML
    private Label lblF3;

    @FXML
    private Label lblF4;

    @FXML
    private Label lblF5;

    private Filosofo[] filosofos;
    private Thread[] hilos;


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
     * Códigos de color blanco ANSI (por defecto) para la salida en consola.
     * </p>
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * <p>
     * Códigos de color verde ANSI para la salida en consola.
     * </p>
     */
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * <p>
     * Códigos de color azul ANSI para la salida en consola.
     * </p>
     */
    public static final String ANSI_BLUE = "\u001B[34m";

    /**
     * <p>
     * Códigos de color azul ANSI para la salida en consola.
     * </p>
     */
    public static final String ANSI_NS = "\u001B[334m";

    public static Stage primaryStage;


    /**
     * <p>
     * Método main donde se inicializan los filósofos, se marcan
     * todos los cubiertos como libres (false) y, cuando los filósofos están
     * esperando (WAITING), se ponen a pensar (se imprime en verde
     * "pensando").
     * </p>
     */
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ilustracion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Ilustración - Problema de los filósofos");
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);  // asegura que todos los hilos mueran
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar cubiertos y Labels
        Arrays.fill(cubiertos, false);
        lblF1.setVisible(false);
        lblF2.setVisible(false);
        lblF3.setVisible(false);
        lblF4.setVisible(false);
        lblF5.setVisible(false);
        cub1.setStroke(Color.GREEN);
        cub2.setStroke(Color.GREEN);
        cub3.setStroke(Color.GREEN);
        cub4.setStroke(Color.GREEN);
        cub5.setStroke(Color.GREEN);


        // Crear hilos de filósofos
        filosofos = new Filosofo[5];
        hilos = new Thread[5];

        filosofos[0] = new Filosofo("filosofo1", 4, 0, lblF1, cub1, cub5);
        filosofos[1] = new Filosofo("filosofo2", 0, 1, lblF2, cub1, cub2);
        filosofos[2] = new Filosofo("filosofo3", 1, 2, lblF3, cub2, cub3);
        filosofos[3] = new Filosofo("filosofo4", 2, 3, lblF4, cub3, cub4);
        filosofos[4] = new Filosofo("filosofo5", 3, 4, lblF5, cub4, cub5);

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(filosofos[i]);
            hilos[i].start();
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarLabels()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void actualizarLabels() {
        actualizarLabel(filosofos[0], lblF1, 1);
        actualizarLabel(filosofos[1], lblF2, 2);
        actualizarLabel(filosofos[2], lblF3, 3);
        actualizarLabel(filosofos[3], lblF4, 4);
        actualizarLabel(filosofos[4], lblF5, 5);
    }

    private void actualizarLabel(Filosofo f, Label lbl, int numero) {

        switch (f.getEstado()){
            case PENSANDO -> {
                lbl.setText("Pensando 💭");
                lbl.setVisible(true);
                System.out.println(ANSI_NS + "El filosofo " + numero + " está pensando (WAITING) 💭" + ANSI_RESET);
            }
            case HAMBRIENTO -> {
                lbl.setText("Esperando...");
                lbl.setVisible(true);
                System.out.println(ANSI_GREEN + "El filosofo " + numero + " está hambriento" + ANSI_RESET);
            }
            case COMIENDO -> {
                lbl.setText("Comiendo 🍔");
                lbl.setVisible(true);
                System.out.println(ANSI_BLUE + "El filosofo " + numero + " está comiendo 🍔" + ANSI_RESET);
            }
        }
    }
}

enum Estado {
    PENSANDO,
    HAMBRIENTO,
    COMIENDO
}

/**
 * <p>
 * Clase <b>Filósofo</b> que implementa Runnable (son hilos) y pertenece a
 * <b>{@link SolucionDos}</b>
 * </p>
 *
 * @see SolucionDos
 */
class Filosofo implements Runnable {

    private final String nombre;
    private final Random r = new Random();
    private final int cubierto1, cubierto2;
    private final Label lbl;
    private final Line c1, c2;
    private Estado estado;

    public Filosofo(String nombre, int c1, int c2, Label lbl, Line cub1, Line cub2) {
        this.nombre = nombre;
        this.cubierto1 = c1;
        this.cubierto2 = c2;
        this.lbl = lbl;
        this.c1 = cub1;
        this.c2 = cub2;
    }

    /**
     * <p>
     * Método que se ejecuta al iniciar los hilos.
     * </p>
     * Controla el flujo de vida de los filósofos:
     * <ol>
     * <li>Intentan coger los cubiertos (si no pueden se quedan en estado
     * <b>WAITING</b>)</li>
     * <li>Comen (se imprime en azul "comiendo")</li>
     * <li>Dejan los cubiertos</li>
     * <li>Piensan</li>
     * </ol>
     */
    @Override
    public void run() {
        while (true) {
            try {
                estado = Estado.HAMBRIENTO;
                getCubiertos();

                estado = Estado.COMIENDO;
                Platform.runLater(() -> {
                    lbl.setText("Comiendo");
                    lbl.setVisible(true);
                    c1.setStroke(Color.RED);
                    c2.setStroke(Color.RED);
                });
                Thread.sleep(r.nextInt(4000) + 1000);
                Platform.runLater(() -> {
                    lbl.setVisible(false);
                    c1.setStroke(Color.GREEN);
                    c2.setStroke(Color.GREEN);
                });
                soltarCubiertos();

                estado = Estado.PENSANDO;
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
            int primero = Math.min(cubierto1, cubierto2);
            int segundo = Math.max(cubierto1, cubierto2);
            while (SolucionDos.cubiertos[primero] || SolucionDos.cubiertos[segundo]) {
                try {
                    SolucionDos.lock.wait();
                } catch (InterruptedException e) {
                }
            }
            c1.setStroke(Color.RED);
            c2.setStroke(Color.RED);
            SolucionDos.cubiertos[primero] = true;
            SolucionDos.cubiertos[segundo] = true;
        }

    }

    /**
     * <p>
     * Método llamado por los filósofos para intentar liberar los cubiertos
     * </p>
     */
    private void soltarCubiertos() {
        synchronized (SolucionDos.lock) {
            c1.setStroke(Color.GREEN);
            c2.setStroke(Color.GREEN);
            int primero = Math.min(cubierto1, cubierto2);
            int segundo = Math.max(cubierto1, cubierto2);

            SolucionDos.cubiertos[primero] = false;
            SolucionDos.cubiertos[segundo] = false;

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

    public Estado getEstado() {
        return estado;
    }
}