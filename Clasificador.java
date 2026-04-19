public class Clasificador extends Thread{
    private final Buzon buzonClasificacion;
    private final ContadorClasificadores contador;
    private final Buzon[] buzonesConsolidacion;
    private final int id;

    public Clasificador(int id, Buzon buzonClasificacion, ContadorClasificadores contador, Buzon[] buzonesConsolidacion) {
        this.id = id;
        this.buzonClasificacion = buzonClasificacion;
        this.contador = contador;
        this.buzonesConsolidacion = buzonesConsolidacion;
    }

    @Override 
    public void run() {
        try {
            while (true) {
                Evento e = buzonClasificacion.retirar();
                if (e.esFin()) {
                    break;
                }
                // Enrutar segun tipo (1..ns)
                int destino = e.getTipo() - 1;
                buzonesConsolidacion[destino].depositar(e);
            }

            // Si es el ultimo clasificador, envia FIN a todos los servidores
            if (contador.registrarTerminacion()) {
                for (Buzon b : buzonesConsolidacion) {
                    b.depositar(Evento.fin());
                }
            }
            System.out.println("Clasificador " + id + " termino");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
