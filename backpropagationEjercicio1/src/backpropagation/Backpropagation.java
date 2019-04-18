/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpropagation;
import Classes.BackpropagationObj;

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
        BackpropagationObj backPropagation = new BackpropagationObj();
        
        double [][] entradas = { {0,1},{1,0},{1,1},{0,0}};
        double [] resultados = {1,1,0,0};
        backPropagation.setDatosEntrenamiento(entradas, resultados);
        
        System.out.println("ENTRENANDO .................................... ");
        
        backPropagation.entrenar();
        
        double [][] tentradas = {{0,1}};
        double [] tresultados = {1};
        
        backPropagation.setDatosEntrenamiento(tentradas, tresultados);
        
        System.out.println("VALIDANDO 0 1  1................................");
        
        backPropagation.validar();
        double [][] tentrada = {{1,0}};
        double [] tresultado = {1};
        
        
        backPropagation.setDatosEntrenamiento(tentrada, tresultado);
        
        System.out.println("VALIDANDO 1 0 1 ................................");
        
        backPropagation.validar();
        
        
        double [][] tentrad = {{1,1}};
        double [] tresulta = {0};
        
        
        backPropagation.setDatosEntrenamiento(tentrad, tresultados);
        
        System.out.println("VALIDANDO 1 1 0 ................................");
        
        backPropagation.validar();
        
        double [][] tentra = {{0,0}};
        double [] tresult = {0};
        
        backPropagation.setDatosEntrenamiento(tentra, tresult);
        
        System.out.println("VALIDANDO 0 0 0................................");
        
        backPropagation.validar();
    }
    
}
