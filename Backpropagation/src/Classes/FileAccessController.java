/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javafx.scene.paint.Color;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author Josua
 */
public class FileAccessController {

    File folder;

    public FileAccessController() {

    }
    
    public double[] getfileBits(File file,boolean addBias) throws FileNotFoundException, IOException {
        Path filePath = file.toPath();
        Charset charset = Charset.defaultCharset();  
        List<String> stringList = Files.readAllLines(filePath, charset);
        String[] lines = stringList.toArray(new String[]{});
        double []bits;
        //para el bias
        if (addBias){
            bits = new double[lines.length+1];
        }
        else{
            bits = new double[lines.length];
        }
        
        int limit = lines.length;
        int lineLimit;
        int count=0;
        for (int i = 0; i < limit; i++) {
            
            String line = lines[i];
            line = line.replace("\n", "").replace("\r", "");
            System.out.println("Checking line");
            System.out.println(line);
            lineLimit = line.length();
            for (int j = 0; j < lineLimit; j++) {
                
                bits[count] = Double.parseDouble(String.valueOf(line.charAt(j)));
                count++;
            }    
        }
        if (addBias){
            bits[bits.length-1]=1;
        }
        
        return bits;
        
        
    }

    public File[] getFileList(String folderName){
        
        this.folder = new File(folderName);
        
        File[] files = this.folder.listFiles();
        
        return files;
        
    }

    public double[][] getTrainingData(String folderName, int bitsAmount) throws IOException {
        File[] files = this.getFileList(folderName);
        System.out.print(files);
        int limit = files.length;
        double[][] data = new double[limit][bitsAmount+1];
        for (int i = 0; i < limit; i++) {
            File file = files[i];
            System.out.println(file.getName());
            data[i] =  this.getfileBits(file,true);
            System.out.println(String.valueOf(data[i][0]));
            System.out.println(String.valueOf(data[i][1]));
            System.out.println(String.valueOf(data[i][2]));
            
        }
        
        return data;
        
    }

    

    public double[][] getResultsData(String folderName,int inputs,int bitsAmount) throws IOException {
        
        File[] files = this.getFileList(folderName);
        
        
        int limit = files.length;
        System.out.println("Cantidad de bits de la respuesta " + String.valueOf(bitsAmount));
        double[][] data = new double[inputs][bitsAmount];
        for (int i = 0; i < inputs; i++) {
            System.out.println("Salida numero " + String.valueOf(i));
            data[i] =  this.getfileBits(files[i],false);
        }
        
        return data;

    }
    

}
