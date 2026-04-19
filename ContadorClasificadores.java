public class ContadorClasificadores {
    private int terminados = 0;
    private final int total;

    public ContadorClasificadores(int total) {
        this.total = total;
    }

    // True si este hilo es el ultimo en terminar
    public synchronized boolean registrarTerminacion() {
        terminados ++;
        return terminados == total;
    }
}
