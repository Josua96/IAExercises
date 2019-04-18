/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.sun.org.apache.bcel.internal.generic.FALOAD;
import java.util.Random;
import javax.naming.LimitExceededException;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import sun.nio.cs.ext.IBM037;

/**
 *
 * @author Josua
 */
public class BackpropagationObj {

    private double[][] entradas;

    private double[] resultados;

    private double alpha;

    private int entradasANeurona;
    private int cantidadNeuronas;
    private double[] netCapaOculta;
    private double[] netCapaSalida;
    private double[][] capaOculta;
    private double[][] capaSalida;

    private double[] errorCapaOculta;
    private double[] errorCapaSalida;
    private double[] deltaCapaOculta;
    private double[] deltaCapaSalida;

    private double[] resultadoCapaOculta;

    private double[] resultadoCapaSalida;

    //conocer si el error alcanzado para las distintas entradas es optimo
    private boolean[] resultadosOptimos = {false, false, false, false};

    public BackpropagationObj() {

        alpha = 0.25;

        /**
         * Filas cantidad de neuronas columnas los pesos asociados a cada
         * entrada
         */
        capaOculta = new double[2][2];

        /**
         * Filas cantidad de neuronas columnas los pesos asociados a cada
         * entrada
         */
        capaSalida = new double[1][2];

        //almacenara los resultados de calcular la net para cada neurona de la capa oculta
        netCapaOculta = new double[2];
        //almacenara los resultados de calcular la net para cada neurona de la capa salida
        netCapaSalida = new double[1];

        errorCapaOculta = new double[2];

        errorCapaSalida = new double[1];

        deltaCapaOculta = new double[2];

        deltaCapaSalida = new double[1];

        resultadoCapaOculta = new double[2];

        resultadoCapaSalida = new double[1];

        /**
         * Datos predeteerminados del ejercicio
         */
        capaOculta[0][0] = 0.1;
        capaOculta[0][1] = -0.7;
        capaOculta[1][0] = 0.5;
        capaOculta[1][1] = 0.3;

        capaSalida[0][0] = 0.2;
        capaSalida[0][1] = 0.4;

    }

    public void calcularNet(double[] entradas, double[][] capa, double[] netResultado) {

        double netSumatoria = 0;
        for (int j = 0; j < capa.length; j++) {

            netSumatoria = 0;
            for (int i = 0; i < capa[j].length; i++) {

                netSumatoria += capa[j][i] * entradas[i];

            }

            netResultado[j] = netSumatoria;
        }

        return;

    }

    public void calcularSalida(double[] netResultados, double[] resultadoCapa) {

        for (int i = 0; i < resultadoCapa.length; i++) {
            resultadoCapa[i] = 1 / (1 + Math.pow(Math.E, -resultadoCapa[i]));
        }

        return;
    }

    public void calcularErrorCapaSalida(double deseado, double[] capaError, double[] capaSalida) {

        for (int i = 0; i < capaSalida.length; i++) {

            capaError[i] = deseado - capaSalida[i];

        }

        return;
    }

    public double sumatoriaErrorCapaSalida(double[] errorCapaSalida) {
        double result = 0;
        for (int i = 0; i < errorCapaSalida.length; i++) {
            result += Math.pow(errorCapaSalida[i], 2);

        }
        return (1.0 / 2.0) * result;
    }

    /**
     *
     * @param entradas
     * @param resultados
     */
    public void setDatosEntrenamiento(double[][] entradas, double resultados[]) {
        this.entradas = entradas;
        this.resultados = resultados;
    }

    public double calcularDeltaCapaOculta(int index, double salida) {

        return this.alpha * this.errorCapaOculta[index] * salida;

    }

    public void actualizarPesosCapaOculta() {

        for (int j = 0; j < this.capaOculta.length; j++) {

            for (int i = 0; i < this.capaOculta[j].length; i++) {

                capaOculta[j][i] = capaOculta[j][i] + calcularDeltaCapaOculta(j, this.resultadoCapaOculta[j]);

            }

        }

    }

    public double calcularDeltaCapaSalida(int index, double entrada) {

        return this.alpha * this.errorCapaSalida[index] * entrada;
    }

    public void calcularErrorCapaOculta() {

        //con respecto a los pesos y salidas de la capa de salida
        for (int j = 0; j < this.capaSalida.length; j++) {

            for (int i = 0; i < this.capaSalida[j].length; i++) {
                this.errorCapaOculta[i] = this.capaSalida[j][i] * this.errorCapaSalida[j];
            }

        }

    }

    public void actualizaPesosCapaSalida(double[][] capaSalida, double[] salidas) {

        for (int j = 0; j < capaSalida.length; j++) {

            for (int i = 0; i < capaSalida[j].length; i++) {

                capaSalida[j][i] = capaSalida[j][i] + calcularDeltaCapaSalida(j, salidas[j]);

            }

        }

    }

    public void printArray(double[] array) {

        System.out.println("");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            System.out.print(" ");

        }
        System.out.println("");
    }

    public boolean inArray(boolean valor, boolean[] array) {

        int limite = array.length;
        for (int i = 0; i < limite; i++) {

            if (array[i] == valor) {
                return true;
            }
        }

        return false;
    }

    public void actualizarBanderas(boolean[] array, boolean valor) {
        int limite = array.length;

        for (int i = 0; i < limite; i++) {
            array[i] = valor;

        }
    }

    public void entrenar() {

        int index;
        double capaSalidaErrorOptimo = 2;
        int max = this.entradas.length - 1;
        double tolerancia = 0.25;

        Random r = new Random();

        /**
         * hasta que para cada entrada el error sea menor al grado de tolerancia
         * definido por la variable tolerancia
         */
        while (this.inArray(false, this.resultadosOptimos)) {

            System.out.println("Nueva iteracion --------------------------------------------");

            index = r.nextInt(4);

            if (this.resultadosOptimos[index] == true) {
                continue;
            }

            System.out.println("index value : " + index);
            double[] entrada = this.entradas[index];

            System.out.println("Evaluando entrada ........... ");
            printArray(entrada);

            System.out.println("");
            System.out.println("Calculando net y salida de la capa oculta");
            this.calcularNet(entrada, this.capaOculta, this.netCapaOculta);
            this.calcularSalida(this.netCapaOculta, this.resultadoCapaOculta);

            printArray(this.netCapaOculta);
            printArray(this.resultadoCapaOculta);

            System.out.println("Calculando net y salida de la capa de salida");
            this.calcularNet(this.resultadoCapaOculta, this.capaSalida, this.netCapaSalida);
            this.calcularSalida(this.netCapaSalida, this.resultadoCapaSalida);

            printArray(this.netCapaSalida);
            printArray(this.resultadoCapaSalida);

            System.out.println("Calculando error capa salida");
            this.calcularErrorCapaSalida(this.resultados[index],
                    this.errorCapaSalida,
                    this.resultadoCapaSalida);

            printArray(this.errorCapaSalida);
            
            //capaSalidaErrorOptimo = Math.abs(this.sumatoriaErrorCapaSalida(this.errorCapaSalida));
            
            capaSalidaErrorOptimo = this.sumatoriaErrorCapaSalida(this.errorCapaSalida);
            
            System.out.println("Error en capa salida es optimo? : ");
            System.out.println(capaSalidaErrorOptimo);
            // si la tasa de errro aún no es la deseada hay que ajustar pesos
            if (capaSalidaErrorOptimo > tolerancia || capaSalidaErrorOptimo < (tolerancia-tolerancia)) {

                /**
                 * el resultado ya no es optimo para la entrada que referencia
                 * el indice ni para las otras
                 */
                this.actualizarBanderas(this.resultadosOptimos, false);

                System.out.println("Error en capa salida no es optimo ");
                System.out.println("Actualizar pesos");

                this.calcularErrorCapaOculta();
                this.actualizaPesosCapaSalida(this.capaSalida, this.resultadoCapaSalida);
                
                this.actualizarPesosCapaOculta();

                System.out.println("Pesos actualizados");

            } else {
                this.resultadosOptimos[index] = true;
            }

            System.out.println("Final de la iteracion ---------------------------------");

        }

        this.calcularNet(resultados, capaOculta, netCapaOculta);
    }

    public double validar() {

        System.out.println("Evaluando entrada ........... ");

        printArray(this.entradas[0]);
        System.out.println("");
        System.out.println("Calculando net y salida de la capa oculta");
        this.calcularNet(this.entradas[0], this.capaOculta, this.netCapaOculta);
        this.calcularSalida(this.netCapaOculta, this.resultadoCapaOculta);

        System.out.println("Calculando net y salida de la capa de salida");
        this.calcularNet(this.resultadoCapaOculta, this.capaSalida, this.netCapaSalida);
        this.calcularSalida(this.netCapaSalida, this.resultadoCapaSalida);

        this.calcularErrorCapaSalida(this.resultados[0],
                this.errorCapaSalida,
                this.resultadoCapaSalida);
        double capaSalidaErrorOptimo = this.sumatoriaErrorCapaSalida(this.errorCapaSalida);

        System.out.println("Error en capa oculta es optimo: ");
        System.out.println(capaSalidaErrorOptimo);

        return capaSalidaErrorOptimo;
    }

}
