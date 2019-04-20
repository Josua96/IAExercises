/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.sun.javafx.geom.AreaOp;
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
        System.out.println("!!!! Guarde una distancia de un espacio para cada patrón de entrenamiento ");
        System.out.println("Ejemplo 1 1 1 1");
        String pattern;
        String[] list;
        int temp = 0;
        while (counter > 0) {
            pattern = this.requestStringToUser("");
            list = pattern.split(" ");
            this.addLineToList(list, inputs[temp]);
            temp++;
            counter--;
        }
        
        return inputs;
        
    }
    
    public double [] buildResultsData(){
        
        System.out.println("A continuación ingrese los resultados para cada entrada especificada."
                + "Se respeta orden!!!");
        System.out.println("!!!! Guarde una distancia de un espacio para cada valor de salida ");
        System.out.println("Ejemplo 1 1 1 1");
        String pattern;
        String[] list;
        
        pattern = this.requestStringToUser("");
        
        list = pattern.split(" ");
        
        double [] results = new double[list.length];
        
        this.addLineToList(list, results);

        
        return results;
        
    }
    
    

}
