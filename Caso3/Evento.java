package Caso3;


public class Evento {
    private String id;
    private int sensorId;
    private int secuencial;
    private int servidorDestino;
    private boolean esFin;

    public Evento(String id, int sensorId, int secuencial, int servidorDestino) {
        this.id = id;
        this.sensorId = sensorId;
        this.secuencial = secuencial;
        this.servidorDestino = servidorDestino;
        this.esFin = false;
    }

    // Constructor para evento de fin
    public Evento(boolean esFin) {
        this.esFin = esFin;
        this.id = "FIN";
    }

    public boolean esFin() { return esFin; }
    public String getId() { return id; }
    public int getServidorDestino() { return servidorDestino; }

    @Override
    public String toString() {
        return esFin ? "[EVENTO_FIN]" : "[Evento id=" + id + ", sensor=" + sensorId + ", seq=" + secuencial + "]";
    }
 
    
}