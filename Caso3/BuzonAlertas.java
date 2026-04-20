package Caso3;

import java.util.LinkedList;
import java.util.Queue;


public class BuzonAlertas {
    private final Queue<Evento> cola = new LinkedList<>();


    public synchronized void agregarAlerta(Evento evento){
        cola.add(evento);
        notifyAll();
    }

    public synchronized Evento sacarAlerta() throws InterruptedException {
        while (cola.isEmpty()) {
            wait();
        }
        return cola.poll();
    }

    public int getTotalAlertas(){
        return cola.size();
    }
    
}
