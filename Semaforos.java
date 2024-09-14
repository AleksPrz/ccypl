import java.util.concurrent.Semaphore;

public class Semaforos {
    public static void main(String[] args) {
        Impresora impresora = new Impresora(1); //Estamos usando solo una impresora
        String[] documentos = {
            "hilos_y_sincronizacion.docx",
            "diseño_responsive.pdf",
            "consultas_SQL_avanzadas.sql",
            "control_de_sensores.pptx",
            "modelo_TCP_IP.pdf",
            "patrones_de_diseño.docx",
            "ejercicios_sincronizacion.txt",
            "flexbox_grid.odt",
            "normalizacion_de_datos.docx",
            "interrupciones_y_timers.pdf",
            "protocolo_HTTP.pdf",
            "genericos_y_lambdas.rtf"
        };
        int numDocumentos = documentos.length;
        Impresion[] impresiones = new Impresion[numDocumentos];
        
        for(int i = 0; i < numDocumentos ; i++){
            impresiones[i] = new Impresion(documentos[i], impresora);
        }

        for(int i = 0; i < numDocumentos ; i++){
            impresiones[i].start();
        }
    }

}

class Impresora {

    private final Semaphore semaforo;

    public Impresora(int impresoras){
        this.semaforo = new Semaphore(impresoras);
    }

    public void imprimir(String documento){
        try {
            semaforo.acquire();
            System.out.println("Imprimiendo " + documento + "...");
            Thread.sleep( (int) (Math.random() * 4000));
            System.out.println("Documento impreso con éxito");
            semaforo.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}

class Impresion extends Thread{

    private final String documento;
    private final Impresora impresora;

    public Impresion(String documento, Impresora impresora){
        
        this.documento = documento;
        this.impresora = impresora;
    }

    @Override
    public void run(){
        impresora.imprimir(documento);
    }
}