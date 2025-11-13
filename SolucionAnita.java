import java.util.Random;
import java.util.random.*;

public class SolucionAnita {

/* 
2 PROBLEMA DE LOS FILÓSOFOS
En una mesa hay procesos que simulan el comportamiento de unos filósofos
que intentan comer de un plato. Cada filósofo tiene un cubierto a su izquierda
y uno a su derecha y para poder comer tiene que conseguir los dos. Si lo
consigue, mostrará un mensaje en pantalla que indique «Filosofo 2
comiendo».
Después de comer, soltará los cubiertos y esperará al azar un tiempo entre
1000 y 5000 milisegundos, indicando por pantalla «El filósofo 2 está
pensando».
En general todos los objetos de la clase Filósofo están en un bucle infinito
dedicándose a comer y a pensar.
Simular este problema en un programa Java que muestre el progreso de
todos sin caer en problemas de sincronización ni de inanición.
*/
  public static void main(String[] args) {
    System.out.println("Hello World");

    Filosofo filosofo = new Filosofo();
    filosofo.numeroFilosofo = 1;


    esperar(filosofo.numeroFilosofo);



  }

  public class Filosofo {
    int numeroFilosofo;

    public String esperar(this.numeroFilosofo) {
    Random r = new Random();
    return "El filósofo " + numeroFilosofo + " está pensando...";
    int tiempoDeEspera = r.nextInt(4000) + 1000;
    
  }

    public String comer(this.numeroFilosofo) {
      return "El filósofo" + numeroFilosofo +" está comiendo...";
    }
  }

  

}
