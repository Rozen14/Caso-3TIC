package Caso3;
import java.util.LinkedList;
import java.util.Queue;

public class BuzonClasificacion {
    private final Queue<Evento> cola = new LinkedList<>();
    private final int capacidad;

    public BuzonClasificacion(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void agregarEvento(Evento evento) throws InterruptedException {
        while (cola.size() >= capacidad){
            wait();
        }
        cola.add(evento);
        notifyAll();    
    }

    public synchronized Evento sacarEvento() throws InterruptedException {
        while (cola.isEmpty()) {
            wait();
        }
        Evento evento = cola.poll();
        notifyAll();
        return evento;
    }

    public synchronized int getTotalEventos(){
        return cola.size();
    }
    
}
