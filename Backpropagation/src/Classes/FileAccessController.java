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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javafx.scene.paint.Color;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author Josua
 */
public class FileAccessController {

    File folder;

    public FileAccessController() {

    }

    public String[] getFileLines(File file) throws IOException {
        Path filePath = file.toPath();
        Charset charset = Charset.defaultCharset();
        List<String> stringList = Files.readAllLines(filePath, charset);
        String[] lines = stringList.toArray(new String[]{});

        return lines;
    }

    public double[] getfileBits(File file, boolean addBias, int bitAmounts) throws FileNotFoundException, IOException {
        String[] lines = getFileLines(file);
        double[] bits;
        
        //para el bias
        if (addBias) {
            bits = new double[bitAmounts + 1];
            
        } else {
            bits = new double[bitAmounts];
        }

        int limit = lines.length;
        int lineLimit;
        int count = 0;
        for (int i = 0; i < limit; i++) {

            String line = lines[i];
            line = line.replace("\n", "").replace("\r", "");
            System.out.println("Checking line");
            System.out.println(line);
            System.out.println(line.length());
            lineLimit = line.length();
            for (int j = 0; j < lineLimit; j++) {

                bits[count] = Double.parseDouble(String.valueOf(line.charAt(j)));
                count++;
            }
        }
        if (addBias) {
            bits[bits.length - 1] = 1;
        }

        return bits;

    }

    public File[] getFileList(String folderName) {

        this.folder = new File(folderName);

        File[] files = this.folder.listFiles();

        return files;

    }

    public double[][] getTrainingData(String folderName, int bitsAmount) throws IOException {
        File[] files = this.getFileList(folderName);
        System.out.print(files);
        int limit = files.length;
        double[][] data = new double[limit][bitsAmount + 1];
        for (int i = 0; i < limit; i++) {
            File file = files[i];
            System.out.println(file.getName());
            data[i] = this.getfileBits(file, true, bitsAmount);
            System.out.println(String.valueOf(data[i][0]));
            System.out.println(String.valueOf(data[i][1]));
            System.out.println(String.valueOf(data[i][2]));

        }

        return data;

    }

    public double[] getResultFromLine(String line) {
        double[] bits = new double[line.length()];

        int limit = line.length();

        for (int i = 0; i < limit; i++) {

            bits[i] = Double.parseDouble(String.valueOf(line.charAt(i)));

        }

        return bits;

    }

    public double[][] getResultsData(String folderName, int inputs, int bitsAmount) throws IOException {

        File[] files = this.getFileList(folderName);

        //se asume que siempre viene un solo 
        String[] lines = getFileLines(files[0]);

        int limit = lines.length;
        System.out.println("Cantidad de bits de la respuesta " + String.valueOf(bitsAmount));
        double[][] data = new double[inputs][bitsAmount];
        for (int i = 0; i < inputs; i++) {
            System.out.println("Linea " + lines[i]);
            data[i] = this.getResultFromLine(lines[i].replace("\n", "").replace("\r", ""));
        }

        return data;

    }

    public String getLineFromArray(double[] array) {

        int limit = array.length;
        String line = "";
        for (int i = 0; i < limit; i++) {
            line += String.valueOf(array[i]) + " ";
        }

        return line;

    }

    public double[] getWeightsFromLine(String line) {

        line = line.replace("\n", "").replace("\r", "");
        String[] lines = line.split(" ");

        int limit = lines.length;
        double[] array = new double[limit];

        for (int i = 0; i < limit; i++) {

            array[i] = Double.parseDouble(lines[i]);

        }

        return array;

    }

    public double[][] loadWeights(String fileName) throws FileNotFoundException, IOException {

        File file = new File(fileName);
     
        Path filePath = file.toPath();
        Charset charset = Charset.defaultCharset();
        List<String> stringList = Files.readAllLines(filePath, charset);
        String[] lines = stringList.toArray(new String[]{});
        
        int limit = lines.length;
        
        //init layer weights
        double[][] layerWeights = new double[limit]
                [lines[0].replace("\n", "").replace("\r", "").split(" ").length];
        
        
        for (int i = 0; i < limit; i++) {
            
            layerWeights[i] = this.getWeightsFromLine(lines[i]);
            
        }
        
        return layerWeights;
        
    }

    public void saveWeights(String fileName, double[][] layer) throws IOException {

        File file = new File(fileName);

        FileWriter writer = new FileWriter(file);

        int limit = layer.length;
        for (int i = 0; i < limit; i++) {
            writer.write(this.getLineFromArray(layer[i])+"\n");

        }

        writer.close();

    }

}
