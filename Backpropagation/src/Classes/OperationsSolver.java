/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.sun.javafx.css.CalculatedValue;

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
        int limit = layerResults.length;
        
        for (int i = 0; i < limit; i++) {
            layerResults[i] = 1 / (1 + Math.pow(Math.E, -layerResults[i]));
        }

        return;
    }
    
    public void calcularOutputLayerError(double wanted, double[] layerError, double[] outputLayer) {

        int limit =  outputLayer.length;
        for (int i = 0; i < limit; i++) {

            layerError[i] = wanted - outputLayer[i];

        }

        return;
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
    
    public double calculateHiddenLayerDelta(int neuronIndex,int weightIndex,double salida,double[][]outputLayer,
                                            double [] outputLayerError,double[][]inputs) {

        return  this.alpha * 
                (inputs[neuronIndex][weightIndex] * (1 - salida) * this.summationOutputLayerErrorWeights(neuronIndex, outputLayerError, outputLayer))
                * salida;

    }
    
    
    public void upateHiddenLayerWeights(double [][]hiddenLayer , double [] hiddenLayerResult,
                                        double [][]outputLayer,double []outputLayerError,double [][]inputs) {
        int rows = hiddenLayer.length;
        int indexForDelta=0;
        int columns;
        for (int j = 0; j < rows; j++) {

            columns = hiddenLayer[j].length;
            for (int i = 0; i < columns; i++) {

                hiddenLayer[j][i] = hiddenLayer[j][i] +
                        this.calculateHiddenLayerDelta(j,i,hiddenLayerResult[j],outputLayer,outputLayerError,inputs);

            }

        }

    }
    
   
    
    /********************************************************************************
    * update weights of output layer
    * *****************************************************************************/
    
     public double calculateOutputLayerDelta(int index, double output,double[] outputLayerError) {

        return this.alpha * outputLayerError[index] * output;
    }
    
    public void updateOutputLayerWeights(double[][] outputLayer,double [] outputLayerError,double[] output) {
        int rows =  outputLayer.length;
        int columns;
        for (int j = 0; j < rows; j++) {
            
            columns = outputLayer[j].length;
            for (int i = 0; i < columns; i++) {

                outputLayer[j][i] = outputLayer[j][i] + calculateOutputLayerDelta(j, output[j],outputLayerError);

            }

        }

    }
    
    
    
    
    
       
}
