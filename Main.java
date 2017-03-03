import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void printMenu() { //Prints the main menu
        System.out.print("Please select an option with the corresponding numbers, if you fail to do so, the program will quit.\n");
        System.out.print("Print all matrices (1)\n");
        System.out.print("Add a matrix (2)\n");
        System.out.print("Print a matrix (3)\n");
        System.out.print("Get the scalar multiple of a matrix (4)\n");
        System.out.print("Find the product of two matrices (5)\n");
        System.out.print("Find the determinant of a matrix (6)\n");
        System.out.print("Find the transpose of a matrix (7)\n");
        System.out.print("Find the inverse of a matrix (8)\n");
        System.out.print("Find the rref of a matrix (9)\n");
        System.out.print("Enter your choice here: ");
    }
    public static void main (String args[]) {
        JavaFXVideoPlayerLaunchedFromSwing playtunes = new JavaFXVideoPlayerLaunchedFromSwing();
        //Runtime.getRuntime().exec(playtunes);
        playtunes.main(null);
        JavaFXVideoPlayerLaunchedFromSwing playTunes = new JavaFXVideoPlayerLaunchedFromSwing();
        ArrayList<Matrix> dataStore = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        try {
            MatrixWriter.loadDataStore(dataStore); //Loads saved matrices to dataStore
        }
        catch(IOException e) {
            System.out.print("File does not exist or is corrupted, exiting with Status 1.\n");
            e.printStackTrace();
            System.exit(1);
        }
        while(true) {
            printMenu(); //Prints the default menu for the user
            Integer userChoice = Integer.parseInt(scan.nextLine());
            Matrix userMatrix;
            String matrixName;
            Matrix temp;
            switch (userChoice) {
                case 1: //Print all the arrays
                    for(Matrix i : dataStore) {
                        System.out.print(i.getName() + "\n");
                        Matrix.printDoubleTwoDArray(i.getMatrixObject());
                        System.out.print("\n");
                    }
                    break;
                case 2: //Create a Matrix
                    System.out.print("Enter your matrix dimensions Row by Column\n (If Not Square, the rest will be filled with Zeroes)\n Enter RxC here: ");
                    Integer height = Integer.parseInt(scan.next());
                    Integer width = Integer.parseInt(scan.next());
                    scan.nextLine();
                    //Deciding which dimension is bigger and making that dimension determine the square
                    Double [][] newMatrix;
                    newMatrix = new Double[height][width];
                    //Deciding which dimension is bigger and making that dimension determine the square
                    Matrix.fillInMatrix(newMatrix); //fill in the square matrix with zeroes
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            //Fill in the matrix
                            System.out.print("Enter the Double for Slot. Row: " + (i+1) + " Column: " + (j+1) + " : ");
                            newMatrix[i][j] = scan.nextDouble();
                            System.out.print("\n");
                        }
                    }
                    scan.nextLine();
                    System.out.print("Please enter a name for your matrix: ");
                    String name = scan.nextLine();
                    while(Matrix.searchForNameConflict(name,dataStore)) {
                        System.out.print("Name conflicts, please try again: ");
                        name = scan.nextLine();
                    }
                    userMatrix = new Matrix(newMatrix,width,height,name);
                    dataStore.add(userMatrix);
                    break;
                case 3: //Prints the matrix if found in the matrix data store
                    System.out.print("Enter which matrix you'd like to print: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else Matrix.printDoubleTwoDArray(temp.getMatrixObject());
                    break;
                case 4: //Prints the scalar multiple of a matrix found in the data store
                    System.out.print("Enter which matrix you'd like to find the scalar multiple of: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else {
                        System.out.print("Enter the scalar multiple: ");
                        Integer scalar = Integer.parseInt(scan.nextLine());
                        Double [][] toPrint = new Double[temp.getHeight()][temp.getWidth()];
                        temp.getScalarMultiple(scalar, toPrint);
                        Matrix.printDoubleTwoDArray(toPrint);
                    }
                    break;
                case 5: //Prints the matrix multiple of two matrices
                    System.out.print("Enter the matrix you'd like to multiply another matrix by: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else {
                        System.out.print("Enter the target matrix: ");
                        matrixName = scan.nextLine();
                        Matrix temp2 = Matrix.searchDataStore(matrixName,dataStore);
                        Double[][] product = Matrix.productOfTwoMatrices(temp,temp2);
                        Matrix.printDoubleTwoDArray(product);
                    }
                    break;
                case 6: //Find the determinant of the matrix if the matrix is in the datastore
                    System.out.print("Enter which matrix you'd like to find the determinant of: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    //Determinant is calculated when matrix is formed
                    else System.out.println(temp.getDeterminant());
                    break;
                case 7: //Find the transpose of the matrix if the matrix is in the datastore
                    System.out.print("Enter which matrix you'd like to transpose: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else Matrix.printDoubleTwoDArray(temp.getTranspose());
                    break;
                case 8: //Find the inverse of the matrix if the matrix is in the datastore
                    System.out.print("Enter which matrix you'd like to get the inverse of: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else Matrix.printDoubleTwoDArray(temp.getInverse());
                    break;
                case 9: //Rref of a matrix
                    System.out.print("Enter which matrix you'd like to put into reduced row echelon form: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else Matrix.printDoubleTwoDArray(temp.getRref());
                    break;
                default:
                    try {
                        MatrixWriter.writeDataStoreToFile(dataStore); //Writes saved matrices to a file in order for persistence
                    } catch (IOException e) {
                        System.out.print("File either corrupted or not found, cannot write.\n");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    System.exit(0);
            }
        }
    }
}
