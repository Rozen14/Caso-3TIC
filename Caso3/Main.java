package Caso3;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java Main <archivo_config.txt>");
            System.exit(1);
        }
    

    // === 1. Leer configuración ===
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(args[0])) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Error leyendo config: " + e.getMessage());
            System.exit(1);
        }

        int ni   = Integer.parseInt(props.getProperty("ni"));
        int base = Integer.parseInt(props.getProperty("base"));
        int nc   = Integer.parseInt(props.getProperty("nc"));
        int ns   = Integer.parseInt(props.getProperty("ns"));
        int tam1 = Integer.parseInt(props.getProperty("tam1"));
        int tam2 = Integer.parseInt(props.getProperty("tam2"));

        // Total de eventos esperado: base * (1+2+...+ni) = base * ni*(ni+1)/2
        int totalEventos = base * ni * (ni + 1) / 2;

        System.out.println("=== Configuración ===");
        System.out.println("Sensores (ni)            = " + ni);
        System.out.println("Base de eventos          = " + base);
        System.out.println("Clasificadores (nc)      = " + nc);
        System.out.println("Servidores (ns)          = " + ns);
        System.out.println("Cap. buzón clasificación = " + tam1);
        System.out.println("Cap. buzón consolidación = " + tam2);
        System.out.println("Total eventos esperados  = " + totalEventos);
        System.out.println("=====================\n");
    

        // === 2. Crear buzones ===
        Buzon buzonEntrada       = new Buzon();            // ilimitado
        Buzon buzonAlertas       = new Buzon();            // ilimitado
        Buzon buzonClasificacion = new Buzon(tam1);        // acotado

        Buzon[] buzonesServidor = new Buzon[ns];
        for (int i = 0; i < ns; i++) {
            buzonesServidor[i] = new Buzon(tam2);          // acotado
        }


        // === 3. Contador de clasificadores (para detectar "último") ===
        ContadorClasificadores contador = new ContadorClasificadores(nc);


        // === 4. Crear hilos ===
        Sensor[] sensores = new Sensor[ni];
        for (int i = 0; i < ni; i++) {
            int id = i + 1;                 // identificadores empiezan en 1
            int numEventos = base * id;     // sensor k genera base*k eventos
            sensores[i] = new Sensor(id, numEventos, ns, buzonEntrada);
        }

        Broker broker = new Broker(buzonEntrada, buzonAlertas, 
                                    buzonClasificacion, totalEventos);

        Administrador admin = new Administrador(buzonAlertas, 
                                                buzonClasificacion, nc);

        Clasificador[] clasificadores = new Clasificador[nc];
        for (int i = 0; i < nc; i++) {
            clasificadores[i] = new Clasificador(
                i + 1, buzonClasificacion, contador, buzonesServidor);
        }

        Servidor[] servidores = new Servidor[ns];
        for (int i = 0; i < ns; i++) {
            servidores[i] = new Servidor(i + 1, buzonesServidor[i]);
        }

        // === 5. Arrancar ===
        for (Sensor s : sensores)          s.start();
        broker.start();
        admin.start();
        for (Clasificador c : clasificadores) c.start();
        for (Servidor sv : servidores)     sv.start();

        // === 6. Esperar terminación ===
        try {
            for (Sensor s : sensores)             s.join();
            broker.join();
            admin.join();
            for (Clasificador c : clasificadores) c.join();
            for (Servidor sv : servidores)        sv.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main interrumpido");
            return;
        }


        // === 7. Validación: todos los buzones deben estar vacíos ===
        System.out.println("\n=== Validación final ===");
        System.out.println("Buzón entrada vacío?        " + buzonEntrada.estaVacio());
        System.out.println("Buzón alertas vacío?        " + buzonAlertas.estaVacio());
        System.out.println("Buzón clasificación vacío?  " + buzonClasificacion.estaVacio());
        for (int i = 0; i < ns; i++) {
            System.out.println("Buzón servidor " + (i+1) + " vacío?      "
                               + buzonesServidor[i].estaVacio());
        }
        System.out.println("\nSimulación terminada.");
    }
}
