package Caso3;
import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private final Queue<Evento> cola = new LinkedList<>();
    private final int tam;
    private final boolean acotado;

    public Buzon(int tam) {
        this.tam = tam;
        this.acotado = true;
    }

    public Buzon() {
        this.tam = Integer.MAX_VALUE; // capacidad "ilimitada" de eventos
        this.acotado = false;
    }

    // Productores: espera pasiva cuando se llena
    public synchronized void depositar(Evento e) throws InterruptedException{
        while (cola.size() >= tam) {
            wait();
        }
        cola.add(e);
        if (acotado) notifyAll();   // puede haber productores y consumidores esperando
        else notify();              // solo consumidores pueden estar esperando (para el caso de BuzonEntrada)
    }

    // Consumidor: espera pasiva si esta vacio
    public synchronized Evento retirar() throws InterruptedException {
        while (cola.isEmpty()) { 
            wait();
        }
        Evento e = cola.poll();
        if (acotado) notifyAll(); // En un buzon ilimitado nadie espera por espacio
        return e;
    }

    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }
}
