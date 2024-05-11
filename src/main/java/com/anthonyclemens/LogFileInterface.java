package com.anthonyclemens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogFileInterface {
    //Reads the logfiles from WinALDL
    public List<double[]> getData(String filePath){
        List<double[]> aldlData = new ArrayList<double[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();//Skip the first line
            // Read each line until the end of the file
            while ((line = reader.readLine()) != null) {
                // Split the line into an array of strings
                String[] numberStrings = line.split("\\s+");

                // Convert the array of strings to an array of doubles
                double[] numbers = Arrays.stream(numberStrings)
                                      .mapToDouble(Double::parseDouble)
                                      .toArray();
                numbers = Arrays.copyOf(numbers, numbers.length + 2);
                // Add the array of doubles to the list
                double stft = calcFT(numbers[33]);
                double ltft = calcFT(numbers[36]);
                numbers[numbers.length - 2] = stft;
                numbers[numbers.length - 1] = ltft;
                aldlData.add(numbers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aldlData;
    }

    private double calcFT(double intblm){
        if(intblm>=128){//Formats positive STFT
            return Math.abs(100*(1-intblm/128));
        }
        return -100*(1-intblm/128);
    }

}
