import java.util.Random;

public class Administrador extends Thread{
    private final Buzon buzonAlertas;
    private final Buzon buzonClasificacion;
    private final int nc;
    private final Random random = new Random();

    public Administrador(Buzon buzonAlertas, Buzon buzonClasificacion, int nc) {
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.nc = nc;
    }

    /** Inofensivo si el random [0,20] es múltiplo de 4. */
    private boolean esInofensivo() {
        int v = random.nextInt(21);    // [0, 20]
        return v % 4 == 0;
    }

    @Override
    public void run() {
        try{
            while (true) {
                Evento e = buzonAlertas.retirar();
                if (e.esFin()) break;

                if (esInofensivo()) {
                    buzonClasificacion.depositar(e);
                    System.out.println("Admin → CLASIF (inofensivo): " + e);
                } else {
                    System.out.println("Admin → DESCARTADO (malicioso): " + e);
                }
            }
            // Avisar a los nc clasificadores
            for (int i = 0; i < nc; i++) {
                buzonClasificacion.depositar(Evento.fin());
            }
            System.out.println("Admin terminó (envió " + nc + " FIN a clasificadores)");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
