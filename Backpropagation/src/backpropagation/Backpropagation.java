/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpropagation;

import Classes.NeuralNetwork;

/**
 *
 * @author Josua
 */
public class Backpropagation {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        /***************************************************
        double [][] entradas = { {0,1},{1,0},{1,1},{0,0}};
        double [] resultados = {1,1,0,0};
        * 
        * XOR Examples
        * 
        * EL VALOR BIAS DEBE especificarse cono el último elemento del patrón en cada entrada
        *****************************************************/
        
        NeuralNetwork neuralN = new NeuralNetwork(0.25);
        neuralN.initMethod();
        neuralN.trainNeuralNetwork();
        neuralN.validate(0);
        neuralN.validate(1);
        neuralN.validate(2);
        neuralN.validate(3);
        
    }
    
}
