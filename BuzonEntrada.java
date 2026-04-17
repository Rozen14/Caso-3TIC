import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {
    private final Queue<Evento> cola = new LinkedList<>();

    // Productores (sensores): nunca esperan (capacidad ilimitada)
    public synchronized void depositar(Evento e) {
        cola.add(e);
        notify(); // despierta al broker si estaba esperando
    }
    
    // Consumidor (broker): espera pasiva si esta vacio
    public synchronized Evento sacar() throws InterruptedException {
        while (cola.isEmpty()) { 
            wait();
        }
        return cola.poll();
    }
}
