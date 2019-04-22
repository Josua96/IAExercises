/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Random;

/**
 *
 * @author Josua
 */
public class NeuralNetwork {

    private DataRequestor dataR;

    private OperationsSolver opSolver;

    private double[][] inputs;

    private double[] results;

    private double alpha;

    private int inputsAmount;

    private double[] hiddenLayerNet;
    private double[] outputLayerNet;
    private double[][] hiddenLayer;
    private double[][] outputLayer;

    private double[] hiddenLayerError;

    private double[] outputLayerError;

    private double[] hiddenLayerDelta;
    private double[] outputLayerDelta;

    private double[] hiddenLayerResult;

    private double[] outputLayerResult;
    
    private boolean[] optimalResults = {false, false, false, false};

    //el error de salide (MSE) debe ser menor a este parámetro
    double tolerance ;
    /**
     * *******
     * For random weights ******
     */
    private double max;
    private double min;

    public NeuralNetwork(double tolerance) {
        this.tolerance = tolerance;
        this.alpha = 0.25;
        dataR = new DataRequestor();
        opSolver = new OperationsSolver(this.alpha);
        this.min = -2.0;
        this.max = 2.0;

    }

    

    public double getRandomWeight() {
        return this.min + Math.random() * (this.max - this.min);
    }

    /**
     * *
     * Establece pesos aleatorios para una capa
     *
     * @param layer la capa para la que se requiere inicializar los pesos
     */
    public void initLayerWeights(double[][] layer) {
        int rows = layer.length;
        int columns;
        for (int j = 0; j < rows; j++) {

            columns = layer[j].length;
            for (int i = 0; i < columns; i++) {

                layer[j][i] = this.getRandomWeight();

            }
        }
    }

    public void initHiddenAndOutputLayer(int inputsAmount, int neuronInHidden, int neuronsInOutput) {
        //los pesos deben calzar con la cantidad de inputs +1
        this.hiddenLayer = new double[neuronInHidden][inputsAmount + 1];
        // los pesos en cada neurona deben calzar con la cantidad de neuronas de la capa oculta +1
        this.outputLayer = new double[neuronsInOutput][neuronInHidden + 1];

        //inicializando arreglos de las salidas de cada capa, una por cada neurona
        this.hiddenLayerNet = new double[neuronInHidden];
        this.outputLayerNet = new double[neuronsInOutput];

        //inicializar el arreglo de errores en las capas
        this.outputLayerError = new double[neuronsInOutput];
        this.hiddenLayerError = new double[neuronInHidden];

        //para cada peso de la capa debe haber un valor delta
        this.hiddenLayerDelta = new double[neuronInHidden];
        this.outputLayerDelta = new double[neuronsInOutput];

        //inicializar los arreglos de resultados de cada capa
        
        //más uno por el valor BIAS (=1) que tiene que haber siempre
        this.hiddenLayerResult = new double[neuronInHidden+1]; 
        this.outputLayerResult = new double[neuronsInOutput];
    }

    public void setTrainingData(double[][] inputs, double[] results) {
        this.inputs = inputs;
        this.results = results;
    }

    public void initMethod() {

        this.inputsAmount = dataR.requestNumberToUser("Ingrese el número de neuronas"
                + " en la capa de entrada: ");

        int neuronsInHiddenLayer = dataR.requestNumberToUser("Ingrese el número de neuronas"
                + " en la capa oculta: ");

        int neuronsInOutputLayer = dataR.requestNumberToUser("Ingrese el número de neuronas "
                + " en la capa de salida: ");


        this.initHiddenAndOutputLayer(this.inputsAmount, neuronsInHiddenLayer, neuronsInOutputLayer);

        this.initLayerWeights(this.hiddenLayer);

        this.initLayerWeights(this.outputLayer);

        this.getTrainingData();

    }

    public void getTrainingData() {
        this.setTrainingData(dataR.buildInputsData(this.inputsAmount+1), dataR.buildResultsData());
    }
    
    
    public void printArray(double[] array) {

        System.out.println("");
        int limit = array.length;
        for (int i = 0; i < limit; i++) {
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

    public void updateFlags(boolean[] array, boolean value) {
        int limit = array.length;

        for (int i = 0; i < limit; i++) {
            array[i] = value;

        }
    }

    public void trainNeuralNetwork() {

        int index;
        double outputLayerOptimalError = 2;
        int max = this.inputs.length - 1;
        
        double[] entrada;
        

        Random r = new Random();

        /**
         * hasta que para cada entrada el error sea menor al grado de tolerancia
         * definido por la variable tolerancia
         */
        while (this.inArray(false, this.optimalResults)) {

            System.out.println("Nueva iteracion --------------------------------------------");

            index = r.nextInt(this.inputs.length);

            if (this.optimalResults[index] == true) {
                continue;
            }

            System.out.println("index value : " + index);
            entrada = this.inputs[index];

            System.out.println("Evaluando entrada ........... ");
            printArray(entrada);

            System.out.println("");
            System.out.println("Calculando net y salida de la capa oculta");
            opSolver.calculateNet(entrada, this.hiddenLayer,this.hiddenLayerNet);
            opSolver.calculateOutput(this.hiddenLayerNet, this.hiddenLayerResult);
            

            printArray(this.hiddenLayerNet);
            printArray(this.hiddenLayerResult);

            //es uno la ultima entrada por ser el BIAS, se agrega además de las salidas de las neuronas
            this.hiddenLayerResult[this.hiddenLayerResult.length-1]=1;
            
            System.out.println("Calculando net y salida de la capa de salida");
            opSolver.calculateNet(this.hiddenLayerResult, this.outputLayer, this.outputLayerNet);
            opSolver.calculateOutput(this.outputLayerNet, this.outputLayerResult);

            printArray(this.outputLayerNet);
            printArray(this.outputLayerResult);

            System.out.println("Calculando error capa salida");
            
            opSolver.calcularOutputLayerError(this.results[index],this.outputLayerError,this.outputLayerResult);           

            printArray(this.outputLayerError);

            //capaSalidaErrorOptimo = Math.abs(this.outputLayerErrorSummatory(this.errorCapaSalida));
            outputLayerOptimalError = opSolver.outputLayerErrorSummation(this.outputLayerError);
            
            System.out.println("Error en capa salida es optimo? : ");
            System.out.println(outputLayerOptimalError);
            // si la tasa de errro aún no es la deseada hay que ajustar pesos
            if ( ! this.opSolver.endTrainingForThisInput(outputLayerOptimalError, this.outputLayerError)) {

                /**
                 * el resultado ya no es optimo para la entrada que referencia
                 * el indice ni para las otras
                 */
                this.updateFlags(this.optimalResults, false);

                System.out.println("Error en capa salida no es optimo ");
                System.out.println("Actualizar pesos");
                this.opSolver.calculateHiddenLayerError(this.hiddenLayerResult, this.hiddenLayerError, this.outputLayerError, this.outputLayer);
                
                this.opSolver.updateOutputLayerWeights(this.outputLayer, this.hiddenLayerResult,this.outputLayerError);
                 
                this.opSolver.upateHiddenLayerWeights(this.hiddenLayer, entrada, hiddenLayerError);
                
                System.out.println("Pesos actualizados");

            } 
            
            else 
            {
                this.optimalResults[index] = true;
            }

            System.out.println("Final de la iteracion ---------------------------------");

        }

        
    }
    
    //para está implementación se evalua una entrada en especifica proporciona para el entrenamiento
    public double validate(int index) {
            System.out.println("");
            System.out.println("");
            System.out.println("Validating ------------------------------------");
            System.out.println("Evaluando entrada ........... ");
            printArray(this.inputs[index]);

            System.out.println("");
            System.out.println("Calculando net y salida de la capa oculta");
            opSolver.calculateNet(this.inputs[index], this.hiddenLayer,this.hiddenLayerNet);
            opSolver.calculateOutput(this.hiddenLayerNet, this.hiddenLayerResult);
            

            this.hiddenLayerResult[this.hiddenLayerResult.length-1]=1;
            
            printArray(this.hiddenLayerNet);
            printArray(this.hiddenLayerResult);
            
            System.out.println("Calculando net y salida de la capa de salida");
            opSolver.calculateNet(this.hiddenLayerResult, this.outputLayer, this.outputLayerNet);
            opSolver.calculateOutput(this.outputLayerNet, this.outputLayerResult);

            printArray(this.outputLayerNet);
            printArray(this.outputLayerResult);

            System.out.println("Calculando error capa salida");
            
            opSolver.calcularOutputLayerError(this.results[0],this.outputLayerError,this.outputLayerResult);           

            printArray(this.outputLayerError);

            //capaSalidaErrorOptimo = Math.abs(this.outputLayerErrorSummatory(this.errorCapaSalida));
             double outputLayerOptimalError = opSolver.outputLayerErrorSummation(this.outputLayerError);

            System.out.println("Error en capa salida es optimo? : ");
            System.out.println(outputLayerOptimalError);

        return outputLayerOptimalError;
    }

}
