package Caso3;

import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {
    private final Queue<Evento> cola = new LinkedList<>();

    public synchronized void agregarEvento(Evento evento) {
        cola.add(evento);
        notifyAll(); // despierta al Broker si estaba esperando
    }

    public synchronized Evento sacarEvento() throws InterruptedException {
        while (cola.isEmpty()) {
            wait();
        }
        return cola.poll();
    }

    public synchronized int getTotalEventos() {
        return cola.size();
    }
}