import java.util.Random;

public class Broker extends Thread {
    private final Buzon buzonEntrada;    
    private final Buzon buzonAlertas;
    private final Buzon buzonClasificacion;
    private final Random random = new Random();
    private final int totalEventosEsperados;

    public Broker(Buzon buzonEntrada, Buzon buzonAlertas, Buzon buzonClasificacion, int totalEventosEsperados) {
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
        try {
            int procesados = 0;
            while (procesados < totalEventosEsperados) {
                Evento e = buzonEntrada.retirar();
                if (detectarAnomalia()) {
                    buzonAlertas.depositar(e);
                    System.out.println("Alerta: '" + e + "' detectada y enviada al buzon de alertas ");
                } else {
                    buzonClasificacion.depositar(e);
                    System.out.println("Evento: '" + e + "' clasificado y enviado al buzon de clasificacion");
                }
                procesados ++;
            }
            buzonAlertas.depositar(Evento.fin());
            System.out.println("Broker terminó (" + procesados + " eventos procesados)");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
