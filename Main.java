//package com.lopez.com.MatrixProject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Taylor Lopez on 2/20/2017.
 */
public class Main {

    public static void printMenu() {
        System.out.print("Please select an option with the corresponding numbers, if you fail to do so, the program will quit.\n");
        System.out.print("Add a matrix (1)\n");
        System.out.print("Print a matrix (2)\n");
        System.out.print("Find the determinant of a matrix (3)\n");
        System.out.print("Find the transpose of a matrix (4)\n");
        System.out.print("Find the inverse of a matrix (5)\n");
        System.out.print("Find the rref of a matrix (6)\n");
        System.out.print("Enter your choice here: ");
    }

    public static void main (String args[]) {
        ArrayList<Matrix> dataStore = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        while(true) {
            printMenu(); //Prints the default menu for the user
            Integer userChoice = Integer.parseInt(scan.nextLine());
            Matrix userMatrix;
            String matrixName;
            Matrix temp;
            switch (userChoice) {
                case 1:
                    System.out.print("Enter your matrix dimensions Row by Column\n (If Not Square, the rest will be filled with Zeroes)\n Enter RxC here: ");
                    Integer height = Integer.parseInt(scan.next());
                    Integer width = Integer.parseInt(scan.next());
                    scan.nextLine();
                    //Deciding which dimension is bigger and making that dimension determine the square
                    Double [][] newMatrix;
                    if(height > width) newMatrix = new Double[height][height];
                    else newMatrix = new Double[width][width];
                    //Deciding which dimension is bigger and making that dimension determine the square
                    Matrix.fillInMatrix(newMatrix); //fill in the square matrix with zeroes
                    if(height.equals(width)) { //fill in the entire matrix
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
                        userMatrix = new Matrix(newMatrix,width,height,name);
                        dataStore.add(userMatrix);
                    } else { //fill in the dimension the user requested
                        for(int i = 0; i < height; i++) {
                            for(int j = 0; j < width; j++) {
                                //Fill in the matrix for your specified non-square size
                                System.out.print("Enter the Double for Slot. Row: " + (i+1) + " Column: " + (j+1) + " : ");
                                newMatrix[i][j] = scan.nextDouble();
                                System.out.print("\n");
                            }
                        }
                        scan.nextLine();
                        System.out.print("Please enter a name for your matrix: ");
                        String name = scan.nextLine();
                        userMatrix = new Matrix(newMatrix,width,height,name);
                        dataStore.add(userMatrix);
                    }
                    break;
                case 2: //Prints the matrix if found in the matrix data store
                    System.out.print("Enter which matrix you'd like to print: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else Matrix.printDoubleTwoDArray(temp.getMatrixObject());
                    break;
                case 3: //Find the determinant of the matrix if the matrix is in the datastore
                    System.out.print("Enter which matrix you'd like to find the determinant of: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    //Determinant is calculated when matrix is formed
                    else System.out.println(temp.getDeterminant());
                    break;
                case 4: //Find the transpose of the matrix if the matrix is in the datastore
                    System.out.print("Enter which matrix you'd like to transpose: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else Matrix.printDoubleTwoDArray(temp.getTranspose());
                    break;
                case 5: //Find the inverse of the matrix if the matrix is in the datastore
                    System.out.print("Enter which matrix you'd like to get the inverse of: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    else Matrix.printDoubleTwoDArray(temp.getInverse());
                    break;
                case 6:
                    System.out.print("Enter which matrix you'd like to put into reduced row echelon form: ");
                    matrixName = scan.nextLine();
                    temp = Matrix.searchDataStore(matrixName,dataStore);
                    if(temp.getName().equals("EMPTY_MATRIX_NO_PARAMETERS")) { System.out.print("Can't find that matrix, please make sure you typed the name correctly.\n"); }
                    //else Matrix.printDoubleTwoDArray(temp.getRref());
                    break;
                default:
                    System.exit(1);
            }
        }
    }
}
