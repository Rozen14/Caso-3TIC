package Caso3;

import java.util.Random;

public class Broker extends Thread {
    private final BuzonEntrada buzonEntrada;
    private final int totalEventosEsperados;
    private final BuzonAlertas buzonAlertas;
    private final BuzonClasificacion buzonClasificacion;
    private final Random random = new Random();

    public Broker(BuzonEntrada buzonEntrada, BuzonAlertas buzonAlertas, BuzonClasificacion buzonClasificacion, int totalEventosEsperados) {
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.totalEventosEsperados = totalEventosEsperados;
    }

    public boolean detectarAnomalia() {

        int randomValue = random.nextInt(201);

        if (randomValue % 8 == 0) {
            return true;
        }

        return false;
    }

    @Override
    public void run() {
        while (totalEventosEsperados < Configuracion.TOTAL_EVENTOS) {
            if (detectarAnomalia()) {
                buzonAlertas.agregarAlerta();
                System.out.println("Alerta detecta y enviada al buzon de alertas");
                totalEventosEsperados++;
            } else {
                buzonClasificacion.agregarEvento();
                System.out.println("Evento clasificado y enviado al buzon de clasificacion");
                totalEventosEsperados++;
            }
        }
    }
}
