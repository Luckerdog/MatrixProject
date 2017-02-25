package com.lopez.com.MatrixProject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by luckerdog on 2/24/17.
 */
public class MatrixWriter {
    public static void writeDataStoreToFile(ArrayList<Matrix> dataStore) throws IOException {
        PrintWriter toWrite = new PrintWriter("matrixStore.txt");
        String message = new String();
        for(Matrix i : dataStore) {
            message += i.getName() + " " + i.getHeight() + " " + i.getWidth();
            for(Double[] j : i.getMatrixObject()) {
                for(Double n : j) {
                    message += " " + n;
                }
            }
            toWrite.println(message);
        }
        toWrite.close();
    }

    public static void loadDataStore(ArrayList<Matrix> dataStore) throws IOException {
        Matrix temp;
        File inFile = new File("matrixStore.txt");
        Scanner readMatrix = new Scanner(inFile);
        while(readMatrix.hasNext()) {
            String matrixName = readMatrix.next();
            Integer matrixHeight = Integer.parseInt(readMatrix.next());
            Integer matrixWidth = Integer.parseInt(readMatrix.next());
            int row = matrixHeight.intValue();
            int column = matrixWidth.intValue();
            Double [][] matrixObject = new Double[row][column];
            for(int i = 0; i < matrixHeight; i++) {
               for(int j = 0; i < matrixWidth; j++) {
                   matrixObject[i][j] = Double.parseDouble(readMatrix.next());
               }
            }
            temp = new Matrix(matrixObject,matrixWidth,matrixHeight,matrixName);
            dataStore.add(temp);
            readMatrix.next();
        }
        readMatrix.close();
    }
}
