public class Evento {
    private final int idSensor;
    private final int secuencial;
    private final int tipo;
    private final boolean fin;

    public Evento(int idSensor, int secuencial, int tipo) {
        this.idSensor = idSensor;
        this.secuencial = secuencial;
        this.tipo = tipo;
        this.fin = false;
    }

    private Evento() {
        this.idSensor = -1;
        this.secuencial = -1;
        this.tipo = -1;
        this.fin = true;
    }
    
    public static Evento fin(){
        return new Evento();
    }

    public boolean esFin() {
        return fin;
    }

    public int getTipo() {
        return tipo;
    }

    public int getIdSensor() {
        return idSensor;
    }
    
    public int getSecuencial() {
        return secuencial;
    }

    @Override
    public String toString() {
        return fin ? "[FIN]" : "Sensor " + idSensor + " - Evt " + secuencial + " - Tipo " + tipo;
    }
}
