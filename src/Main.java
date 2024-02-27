
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public static void main(String[] args) {
    String archivoCSV = "src/XOR_trn.csv"; 
    String archivoNuevosPuntosCSV = "src/XOR_tst.csv"; 

    try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV));
             BufferedReader brNuevosPuntos = new BufferedReader(new FileReader(archivoNuevosPuntosCSV))) {
        String linea;
        int numFilas = 0;
        int numColumnas = 0;
        // Contar el número de filas y columnas
        while ((linea = br.readLine()) != null) {
            numFilas++;
            String[] partes = linea.split(",");
            numColumnas = partes.length;
        }

        double[][] entradas = new double[numFilas][numColumnas - 1];
        int[] salidasDeseadas = new int[numFilas];

        // Cerrar y volver a abrir el archivo para la lectura real de los datos
        br.close();
        BufferedReader nuevoBr = new BufferedReader(new FileReader(archivoCSV));

        // Leer el archivo CSV nuevamente y cargar los datos
        int filaActual = 0;
        int count = 0;
        while ((linea = nuevoBr.readLine()) != null) {
            String[] partes = linea.split(",");
            for (int i = 0; i < numColumnas; i++) {
                if (i < numColumnas - 1) {
                    entradas[filaActual][i] = Double.parseDouble(partes[i]);
                } else {
                    salidasDeseadas[filaActual] = Integer.parseInt(partes[i]);
                }
            }
            filaActual++;
            count++;
            System.out.println("cantidad de datos;" + count);
        }

        PerceptronSimple perceptron = new PerceptronSimple(2, 0.1);
        perceptron.entrenar(entradas, salidasDeseadas, 1000);

        // Leer nuevos puntos
        while ((linea = brNuevosPuntos.readLine()) != null) {
            String[] nuevoPuntoStr = linea.split(",");
            double[] nuevoPunto = new double[numColumnas - 1];
            for (int i = 0; i < numColumnas - 1; i++) {
                nuevoPunto[i] = Double.parseDouble(nuevoPuntoStr[i]);
            }

            // Clasificar nuevo punto
            System.out.println("Clasificación del nuevo punto: " + perceptron.clasificar(nuevoPunto));
        }
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
}