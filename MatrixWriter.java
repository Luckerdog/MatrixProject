import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class MatrixWriter {
    public static String MatrixPathName = ""; //Define your text file path to store matrices

    public static void writeDataStoreToFile(ArrayList<Matrix> dataStore) throws IOException {
        FileWriter out = new FileWriter(MatrixPathName,true);
        PrintWriter toWrite = new PrintWriter(out);
        File inFile = new File(MatrixPathName);
        Scanner scan = new Scanner(inFile);
        ArrayList<String> usedNames = new ArrayList<>();
        while(scan.hasNext()) {
            String name = scan.next();
            usedNames.add(name);
            scan.nextLine();
        }
        String message;
        for(Matrix i : dataStore) {
            if(usedNames.contains(i.getName())) continue;
            message = new String();
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
        File inFile = new File(MatrixPathName);
        Scanner readMatrix = new Scanner(inFile);
        while(readMatrix.hasNext()) {
            String matrixName = readMatrix.next();
            Integer matrixHeight = Integer.parseInt(readMatrix.next());
            Integer matrixWidth = Integer.parseInt(readMatrix.next());
            int row = matrixHeight.intValue();
            int column = matrixWidth.intValue();
            Double[][] matrixObject = new Double[row][column];
            for(int i = 0; i < matrixHeight.intValue(); i++) {
               for(int j = 0; j < matrixWidth.intValue(); j++)
                   matrixObject[i][j] = Double.parseDouble(readMatrix.next());
            }
            temp = new Matrix(matrixObject,matrixWidth,matrixHeight,matrixName);
            dataStore.add(temp);
        }
        readMatrix.close();
    }
}
