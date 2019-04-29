/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Josua
 */
public class OperationsSolver {
    
    public double alpha;
    public OperationsSolver(double alpha) {
        
        this.alpha = alpha;
    }
    
    public double outputLayerErrorSummation(double[] outputLayerError) {
        double result = 0;
        int limit = outputLayerError.length;
        for (int i = 0; i < limit ; i++) {
            result += Math.pow(outputLayerError[i], 2);

        }
        return (1.0 / 2.0) * result;
    }
    //revisada
    public void calculateNet(double[] inputs, double[][] layer, double[] netResult) {

        double netSummation = 0;
        int rows = layer.length;
        int columns;
        for (int j = 0; j < rows; j++) {

            netSummation = 0;
            columns = layer[j].length;
            for (int i = 0; i < columns; i++) {

                netSummation += layer[j][i] * inputs[i];

            }

            netResult[j] = netSummation;
        }

        return;

    }
    
    public void calculateOutput(double[] netResults, double[] layerResults) {
        int limit = netResults.length;
        
        for (int i = 0; i < limit; i++) {
            layerResults[i] = 1 / (1 + Math.pow(Math.E, -netResults[i]));
        }

        return;
    }
    
    public void calculateOutputLayerError(double []wanted, double[] layerError, double[] outputLayer) {

        int limit =  outputLayer.length;
        for (int i = 0; i < limit; i++) {

            layerError[i] = (wanted[i] - outputLayer[i]) * outputLayer[i]*( 1 - outputLayer[i]);

        }

        return;
    }
    
    public boolean endTrainingForThisInput (double ep,double [] outputLayerError){
        int limit = outputLayerError.length;
        System.out.println("");
        System.out.println("Finalizar entrenamiento");
        for (int i = 0; i < limit; i++) {
            System.out.println("");
            System.out.println("Error de la neurona número " + i);
            System.out.println(outputLayerError[i]);
            //con que una neurona de salidad tenga un error mayor o igual al calculado con la formula de ep
            if (Math.abs(outputLayerError[i]) >= ep){
                return false;
            }
            
        }
        
        return true;
        
    }
    
    /********************************************************************************
    * update weights of hidden layer
    * 
    * Xpi(1 - Ypi me parece que es Ypj) sumatoria k en error capa salida ERRORpk * Wkj
    * *****************************************************************************/

    public double summationOutputLayerErrorWeights(int j,double[] outputLayerError,double [][]outputLayer){
        
        int limit = outputLayerError.length;
        double summation = 0.0;
        for (int i = 0; i < limit; i++) {
            
            summation += outputLayerError[i] * outputLayer[i][j];
            
        }
        
        return summation;
        
    }
    
    
    public double calculateHiddenLayerDelta (int neuronIndex,double inputValue,double []hiddenLayerError) {

        return  this.alpha * hiddenLayerError[neuronIndex]  * inputValue;

    }
    
    public void calculateHiddenLayerError(double[]layerResults,double []hiddenLayerError,
            double []outputLayerError,double [][]outputLayer){
        int limit = hiddenLayerError.length;
        
        for (int j = 0; j < limit; j++) {
            hiddenLayerError[j] =  layerResults[j] * (1-layerResults[j]) * this.summationOutputLayerErrorWeights(j, outputLayerError, outputLayer);
            
        }
    }
    
    
    
    public void upateHiddenLayerWeights(double [][]hiddenLayer ,double[]input,double[] hiddenLayerError) {
        int rows = hiddenLayer.length;
        int indexForDelta=0;
        int columns;
        for (int j = 0; j < rows; j++) {

            columns = hiddenLayer[j].length;
            for (int i = 0; i < columns; i++) {

                hiddenLayer[j][i] = hiddenLayer[j][i] +
                        this.calculateHiddenLayerDelta(j,input[i],hiddenLayerError);

            }

        }

    }
    
   
    
    /********************************************************************************
    * update weights of output layer
    * *****************************************************************************/
    
     public double calculateOutputLayerDelta(int index, double[] outputLayerError,double input) {

        return this.alpha * outputLayerError[index] * input;
    }
    
    public void updateOutputLayerWeights(double[][] outputLayer,double[] inputs,double [] outputLayerError) {
        int rows =  outputLayer.length;
        int columns;
        for (int j = 0; j < rows; j++) {
            
            columns = outputLayer[j].length;
            for (int i = 0; i < columns; i++) {

                outputLayer[j][i] = outputLayer[j][i] + calculateOutputLayerDelta(j,outputLayerError,inputs[i]);

            }

        }

    }
    
    
    
    
    
       
}
