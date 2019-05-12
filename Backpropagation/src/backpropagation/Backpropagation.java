/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpropagation;

import Classes.FileAccessController;
import Classes.NeuralNetwork;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josua
 */
public class Backpropagation {

    static FileAccessController fileInteraction;
    static String hiddenLayerTextFile = "hiddenWeights.txt";
    static String outputLayerTextFile = "outputWeights.txt";

    /**
     * @param args the command line arguments
     */
    public static void trainNeuralNetwork(double[][] inputs, int hiddenNeurons, double[][] results, NeuralNetwork net, int iterations, boolean loadWeights) throws IOException {

        System.out.println("Cantidas de entradas ... " + String.valueOf(inputs[0].length));
        System.out.println("Cantidas de entradas ... " + String.valueOf(hiddenNeurons));
        System.out.println("Cantidas de entradas ... " + String.valueOf(results[0].length));
        net.initHiddenAndOutputLayer(inputs[0].length, hiddenNeurons, results[0].length);

        if (loadWeights) {
            //iniciar la capa oculta
            net.setHiddenLayer(fileInteraction.loadWeights(hiddenLayerTextFile));
            net.setOutputLayer(fileInteraction.loadWeights(outputLayerTextFile));
        } else {
            net.initLayerWeights(net.getHiddenLayer());
            net.initLayerWeights(net.getOutputLayer());
        }

        net.setTrainingData(inputs, results);
        net.setIterations(iterations);
        net.trainNeuralNetworkEndWithIteration();
        net.validate(0);
        net.validate(1);
        net.validate(2);
        net.validate(3);

    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        hiddenLayerTextFile = "hiddenWeights.txt";
        outputLayerTextFile = "outputWeights.txt";
        NeuralNetwork neuralN = new NeuralNetwork(0.25, 5000);
        fileInteraction = new FileAccessController();
        /**
         * *************************************************
         * double [][] entradas = { {0,1},{1,0},{1,1},{0,0}}; double []
         * resultados = {1,1,0,0};
         *
         * XOR Examples
         *
         * EL VALOR BIAS DEBE especificarse cono el último elemento del patrón
         * en cada entrada ***************************************************
         */
        if (args.length > 0) {
            try {
                if (args.length == 2) {

                    //se asume que siempre se cargan pesos para validar
                    /*
                se requiere conocer
                0 la ruta a la carpeta donde están los patrones de validacion.
                1 -a cantidad de bits de cada entrada
                     */
                    double[][] inputs = fileInteraction.getTrainingData(args[0], Integer.parseInt(args[1]));
                    
                    double[][] hiddenLayer = fileInteraction.loadWeights(hiddenLayerTextFile);
                    double[][] outputLayer = fileInteraction.loadWeights(outputLayerTextFile);

                   
                    
                    
                    neuralN.initHiddenAndOutputLayer(inputs[0].length-1, hiddenLayer.length, outputLayer.length);
                    
                    neuralN.setInputs(inputs);
                    neuralN.setHiddenLayer(hiddenLayer);
                    neuralN.setOutputLayer(outputLayer);

                   

                    System.out.println("*************************************************");
                    System.out.println("             Validando              ");
                    System.out.println("*************************************************");

                    neuralN.validate(0);

                    System.out.println("*************************************************");
                    System.out.println("             Validacion finalizada              ");
                    System.out.println("*************************************************");

                    return;

                }
                /*
                se requiere conocer
                0 la ruta a la carpeta donde están los patrones de entrenamiento.
                1 -a cantidad de bits de cada entrada
                2 la ruta de la carpeta donde está el archivo de resultados esperados
                3 la cantidad de bits de para cada respuesta esperada
                4 neuronas en capa oculta
                5 cantidad iteraciones
                6 cargar pesos predeterminados o no 0 sí, 1 = no
                 */
                boolean loadWeights;
                if (Integer.parseInt(args[6]) == 0) {
                    loadWeights = true;
                } else {
                    loadWeights = false;
                }

                double[][] inputs = fileInteraction.getTrainingData(args[0], Integer.parseInt(args[1]));
                double[][] results = fileInteraction.getResultsData(args[2], inputs.length, Integer.parseInt(args[3]));

                
                System.out.println("Entradas");
                /*
                for (int i = 0; i < inputs.length; i++) {
                    neuralN.printArray(inputs[i]);

                }
                */

                System.out.println("Salidas");
                /*
                for (int i = 0; i < results.length; i++) {
                    neuralN.printArray(results[i]);

                }
                */

                trainNeuralNetwork(inputs, Integer.parseInt(args[4]), results, neuralN, Integer.parseInt(args[5]), loadWeights);

                fileInteraction.saveWeights(hiddenLayerTextFile, neuralN.getHiddenLayer());

                fileInteraction.saveWeights(outputLayerTextFile, neuralN.getOutputLayer());

            } catch (IOException ex) {
                Logger.getLogger(Backpropagation.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            neuralN.initMethod();
            neuralN.trainNeuralNetwork();

            fileInteraction.saveWeights(hiddenLayerTextFile, neuralN.getHiddenLayer());

            fileInteraction.saveWeights(outputLayerTextFile, neuralN.getOutputLayer());

            neuralN.validate(0);
            neuralN.validate(1);
            neuralN.validate(2);
            neuralN.validate(3);
        }

    }

}
