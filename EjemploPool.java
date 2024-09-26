import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class EjemploPool {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        String[] fileNames = { "reporte.pdf", "foto.jpg", "proyecto.pptx", "backup.sql", "ventas.xlsx",
                "resumen.docx", "podcast.mp3", "promo.mp4", "main.java", "esquema.png" };

        for (int i = 0; i < fileNames.length; i++) {
            pool.submit(new Descarga(fileNames[i]));
        }

        pool.shutdown(); //Se encarga de ejecutar las tareas usando los 3 hilos del pool
    }

}

class Descarga implements Runnable {

    String archivo;
    static Random random = new Random();

    public Descarga(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public void run() {
        System.out.println(
                "Iniciando descarga de archivo " + archivo + " en el hilo " + Thread.currentThread().getName());
        try {
            Thread.sleep(random.nextInt(3000) + 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Archivo " + archivo + " descargado");
    }

}
