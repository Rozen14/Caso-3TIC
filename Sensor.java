import java.util.Random;

public class Sensor extends Thread{
    private int id;
    private int numEventos;  
    private final int ns;
    private final BuzonEntrada buzonEntrada;
    private final Random rand = new Random();

    public Sensor(int id, int numEventos, int ns, BuzonEntrada buzonEntrada) {
        this.id = id;
        this.numEventos = numEventos;
        this.ns = ns;
        this.buzonEntrada = buzonEntrada;
    }

    @Override
    public void run() {
        for (int curr = 0; curr < numEventos; curr++) {
            int tipo = rand.nextInt(ns) + 1;
            Evento e = new Evento(id, curr, tipo);
            buzonEntrada.depositar(e);
        }
        System.out.println("Sensor " + id + " termino (" + numEventos + " eventos)");
    }
}
