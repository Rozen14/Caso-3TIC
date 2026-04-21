package Caso3;
import java.util.Random;

public class Servidor extends Thread{
    private final Buzon buzon;
    private final Random rand = new Random();
    private final int id;

    public Servidor(int id, Buzon buzon) {
        this.id = id;
        this.buzon = buzon;
    }

    @Override 
    public void run(){
        try {
            while (true) {
                Evento e = buzon.retirar();
                if (e.esFin()) {
                    System.out.println("Servidor " + id + " recibió FIN"); 
                    break;
                }
                Thread.sleep(rand.nextInt(901) + 100);
                System.out.println("Servidor " + id + " procesó " + e);
            }
            System.out.println("Servidor " + id + " terminó");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } 
    }
}
