/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Scanner;

/**
 *
 * @author Josua
 */
public class DataRequestor {

    public DataRequestor() {

    }

    public int requestNumberToUser(String inputMessage) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println(inputMessage);
        int number = reader.nextInt();
        return number;
    }
    
    public String requestStringToUser(String inputMessage) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println(inputMessage);
        String text=  reader.nextLine();
        return text;
    }
    
    
    public void addLineToList(String[]list,double []destiny){
       int limite = destiny.length;
        for (int i = 0; i < limite; i++) {
            
            destiny[i] = Double.parseDouble(list[i]);
            
        }
    }
    
    public double [][] buildInputsData(int inputsAmount){
        
        int counter = this.requestNumberToUser("Ingrese la cantidad de patrones de entrenamiento a utilizar? ");
        System.out.println(" ");
        double [][]inputs = new double[counter][inputsAmount];
        System.out.println("A continuación ingrese los datos de cada patrón de "
                + "entramiento junto con el BIAS (entrada con valor siempre de 1)...");
        System.out.println("!!!! No guarde una distancia de un espacio para cada patrón de entrenamiento ");
        System.out.println("Ejemplo 1111");
        String pattern;
        String[] list;
        int temp = 0;
        while (temp < counter) {
            pattern = this.requestStringToUser("");
           
            for (int i = 0; i < inputsAmount; i++) {
                inputs[temp][i] = Double.parseDouble(String.valueOf(pattern.charAt(i)));
            }
 
            temp++;

        }
        
        return inputs;
        
    }
    
    public double [][] buildResultsData(int inputsAmount, int outputsAmount,int resultAmount){
        
        System.out.println("La cantidad de bits para cada resultado será de: "+ String.valueOf(outputsAmount));
        System.out.println(" ");
        double [][]results = new double[resultAmount][outputsAmount];
        
        System.out.println("A continuación ingrese los resultados para cada entrada especificada."
                + "Se respeta orden!!!");
        System.out.println("!!!! No Guarde una distancia de un espacio para cada valor de salida ");
        System.out.println("Ejemplo 1111");
        String pattern;
        String[] list;
          
        int temp=0;
        while(temp < resultAmount){
            
            pattern = this.requestStringToUser("Ingrese el resultado esperado ");
            
            for (int i = 0; i < outputsAmount; i++) {
                results[temp][i] = Double.parseDouble(String.valueOf(pattern.charAt(i)));
            }
            temp++;
        }
        
        return results;
        
    }
    
    

}
