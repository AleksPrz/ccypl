import java.util.Random;

public class Hilos {
    /*
     * La multiplicacion de matrices es un problema de algebra lineal
     * muy usual en situaciones de modelado 3D.
     * La multiplicacion de matrices consiste en multiplicar filas y columnas
     * las filas de la matriz resultante son independientes entre si
     * por eso podemos asignar una fila a un hilo distinto.
     */

    public static void main(String[] args) {

        Random random = new Random();

        int filas = random.nextInt(6) + 5; //tamano de entre 5 y 10

        int[][] matriz1 = Matrices.generarMatriz(filas);
        int[][] matriz2 = Matrices.generarMatriz(filas);

        int[][] resultado = new int[filas][matriz2[0].length];

        Multiplicador[] multi = new Multiplicador [filas];

        //Impresion inicial
        System.out.println("---MULTIPLICACION DE MATRICES----\n");
        System.out.println("Matriz 1: ");
        Matrices.imprimirMatriz(matriz1);
        System.out.println("\nMatriz 2");
        Matrices.imprimirMatriz(matriz2);
        System.out.println("");

        //Inicializacion
        for (int i = 0; i < filas ; i++)
            multi[i] = new Multiplicador(matriz1, matriz2, i, resultado);
        
        System.out.println("Iniciando calculos...");
        
        //Iniciar hilos
        for (int i = 0; i < filas; i++)
            multi[i].start();

        for (int i = 0; i < filas; i++){
            try {
                multi[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
        System.out.println("\nResultado:");
        Matrices.imprimirMatriz(resultado);
        System.out.println("");

     }

}

class Multiplicador extends Thread {

    private int fila;
    private int[][] matriz1;
    private int[][] matriz2;
    private int[][] resultado;

    public Multiplicador(int[][] matriz1, int[][] matriz2, int fila, int[][] resultado) {
        this.fila = fila;
        this.matriz1 = matriz1;
        this.matriz2 = matriz2;
        this.resultado = resultado;
    }

    /* Multiplica la fila de la matriz 1 por las columnas de la matriz 2*/
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " computando la fila " + (fila+1) + "...");
        //Itera sobre las columnas de la matriz2
        for (int i = 0; i < matriz2[0].length; i++){ 
            
            int elemento = 0;
            
            //Itera por los elementos de esa columna
            for (int j = 0; j < matriz2.length; j++){
                            // elemento de fila * elemento de columna
                elemento += ( matriz1[fila][j] * matriz2[j][i] );
            }

            resultado[fila][i] = elemento;

        }
        System.out.println(this.getName() + " - TERMINATED");
    }
}

class Matrices {

    public static void imprimirMatriz(int[][] matriz) {
        // Recorrer la matriz y imprimir cada elemento
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] generarMatriz(int tamano) {
        Random rand = new Random();
        int valorMinimo = 0;
        int valorMaximo = 10;
        int[][] matriz = new int[tamano][tamano];
        
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                matriz[i][j] = rand.nextInt((valorMaximo - valorMinimo) + 1) + valorMinimo;
            }
        }
        
        return matriz;
    }
}