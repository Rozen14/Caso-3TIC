import java.util.Random;

public class Sensor extends Thread{
    private final int id;
    private final int numEventos;  
    private final int ns;
    private final Buzon buzonEntrada;
    private final Random rand = new Random();

    public Sensor(int id, int numEventos, int ns, Buzon buzonEntrada) {
        this.id = id;
        this.numEventos = numEventos;
        this.ns = ns;
        this.buzonEntrada = buzonEntrada;
    }

    @Override
    public void run() {
        try {
            for (int curr = 1; curr <= numEventos; curr++) {
                int tipo = rand.nextInt(ns) + 1;
                Evento e = new Evento(id, curr, tipo);
                buzonEntrada.depositar(e);
                Thread.yield(); // espera semi-activa: cede CPU entre eventos
            }
            System.out.println("Sensor " + id + " termino (" + numEventos + " eventos)");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } 
    }
}
