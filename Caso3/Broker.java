package Caso3;

import java.util.Random;

public class Broker extends Thread {
    private final BuzonEntrada buzonEntrada;
    private final BuzonAlertas buzonAlertas;
    private final BuzonClasificacion buzonClasificacion;
    private final int totalEventosEsperados;
    private final Random random = new Random();

    public Broker(BuzonEntrada buzonEntrada,
                  BuzonAlertas buzonAlertas,
                  BuzonClasificacion buzonClasificacion,
                  int totalEventosEsperados) {
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.totalEventosEsperados = totalEventosEsperados;
    }

    private boolean detectarAnomalia() {
        int valor = random.nextInt(201);
        return valor % 8 == 0;
    }

    @Override
    public void run() {
        int procesados = 0;

        while (procesados < totalEventosEsperados) {
            try {
                Evento evento = buzonEntrada.sacarEvento();

                if (detectarAnomalia()) {
                    buzonAlertas.agregarAlerta(evento);
                    System.out.println("Broker → Alerta enviada: " + evento);
                } else {
                    buzonClasificacion.agregarEvento(evento);
                    System.out.println("Broker → Evento para clasificación: " + evento);
                }
                procesados++;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        buzonAlertas.agregarAlerta(new Evento(true));
        System.out.println("Broker → Evento de FIN enviado al Administrador.");
    }
}