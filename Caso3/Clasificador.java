package Caso3;

public class Clasificador extends Thread {
    private final BuzonClasificacion buzonClasificacion;
    private final BuzonConsolidacion[] buzonesConsolidacion;
    private final int numero_servidores;

    private static int clasificadoresActivos = 0;
    private static final Object lock = new Object();

    public Clasificador(BuzonClasificacion buzonClasificacion, BuzonConsolidacion[] buzonesConsolidacion, int ns) {
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = buzonesConsolidacion;
        this.numero_servidores = ns;

        synchronized (lock) {
            clasificadoresActivos++;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Evento evento = buzonClasificacion.sacarEvento();

                if (evento.esFin()) {
                    boolean esUltimo;

                    // ✅ Sección crítica: decrementar y verificar si es el último
                    synchronized (lock) {
                        clasificadoresActivos--;
                        esUltimo = (clasificadoresActivos == 0);
                    }

                    if (esUltimo) {
                        // Envía un evento de fin a cada servidor de consolidación
                        for (BuzonConsolidacion buzon : buzonesConsolidacion) {
                            buzon.agregarEvento(new Evento(true));
                        }
                        System.out.println("Clasificador " + getId() +
                                " → ÚLTIMO en terminar, FIN enviado a " + numero_servidores + " servidores.");
                    } else {
                        System.out.println("Clasificador " + getId() + " → terminó.");
                    }
                    break; // Sale del loop en cualquier caso
                }

                // Enruta el evento al servidor correspondiente (servidorDestino es 1-indexed)
                int indiceServidor = evento.getServidorDestino() - 1;
                buzonesConsolidacion[indiceServidor].agregarEvento(evento);
                System.out.println("Clasificador " + getId() +
                        " → Evento " + evento + " → Servidor " + evento.getServidorDestino());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}