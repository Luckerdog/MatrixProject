//package com.lopez.com.MatrixProject;

import java.util.ArrayList;

/**
 * Created by be127 on 2/14/17.
 */
public class Matrix {
    //Class to hold our Matrix
    private Double[][] matrixObject;
    private String name;
    private Integer width;
    private Integer height;
    private Double determinant = 0.0;

    public Matrix() {
        this(new Double[][]{{0.0},{}},1, 1, "EMPTY_MATRIX_NO_PARAMETERS");
    }

    public Matrix(Matrix rhs) {
        this.name = rhs.getName();
        this.determinant = rhs.getDeterminant();
        this.width = rhs.getWidth();
        this.height = rhs.getHeight();
        this.matrixObject = rhs.getMatrixObject();
    }

    public Matrix(Double[][] matrixObject, Integer width, Integer height, String name){
        this.name = name.toUpperCase();
        this.matrixObject = matrixObject;
        this.width = width;
        this.height = height;
        if(width == height && width == 1) this.determinant = this.matrixObject[0][0];
        else this.determinant = calculateDeterminant(this.matrixObject);
    }

    public Double[][] getMatrixObject() {
        return matrixObject;
    }

    public void setMatrixObject(Double[][] matrixObject) {
        this.matrixObject = matrixObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getDeterminant() { return determinant; }

    public void setDeterminant(Double determinant) {
        this.determinant = determinant;
    }

    public static Integer getLongestDouble(Double[][] haystack) { //returns the length of the longest double for formatting purposes
       Integer lengthMax = Double.toString(haystack[0][0]).length(); //set default maxLength to compare to rest of array
       for(Double[] i : haystack) { //for every row
          for(Double j : i) { //for every elem in ever row
              if(Double.toString(j).length() > lengthMax) lengthMax = Double.toString(j).length();
          }
       }
       return lengthMax; //Return longest double in array
    }

    public static void printDoubleTwoDArray(Double[][] toprint) {
        System.out.print("--------Matrix-------\n");
        Integer maxLength = getLongestDouble(toprint); //grab the longest double in array
        String format = "%" + (maxLength+3) + ".3f   "; //Generate variable width format string
        for(Double[] i : toprint) {
            System.out.print("|");
            for(Double j : i) {
                System.out.printf(format,j);
            }
            System.out.print("|");
            System.out.print("\n");
        }
        System.out.print("--------Matrix-------\n");
    }


    public static void getCofactorMatrix(Double mat[][], Double temp[][], Integer forbiddenRow, Integer forbiddenColumn, Integer dimension) {
        Integer i = 0, j = 0; //Gets the cofactor matrix of a NxN matrix
        for(int row = 0; row < dimension; row++) { //for row
            for(int col = 0; col < dimension; col++) { //for column
                if(row != forbiddenRow && col != forbiddenColumn) { //Grab elements that are not forbidden
                    temp[i][j++] = mat[row][col];
                    if(j == dimension-1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public static Double calculateDeterminant(Double [][]rhs) {
        Double determinant = 0.0; //Initialize determinant
        if(rhs.length == 1) {
            return(rhs[0][0]); //If Matrix is 1x1 return number in the only slot
        }
        for(int i = 0; i < rhs.length; i++) {
            Double[][] smallerRhs = new Double[rhs.length-1][rhs.length-1]; //Recursive thinning of matrix size
            for(int r = 1; r < rhs.length; r++) {
                for(int c = 0; c < rhs.length; c++) {
                    if(c < i) smallerRhs[r-1][c] = rhs[r][c]; //Reduces 4x4 to 3x3 to 2x2, and so on to allow for easy calculation of determinant programmtically for NxN (n^3 algo)
                    else if(c > i) smallerRhs[r-1][c-1] = rhs[r][c];
                }
            }
            int sign = 1;
            if(i % 2 != 0) sign *= -1; //Delta function for determinants (-1) ^ [(Row)(Column)]
            determinant += sign * rhs[0][i] * (calculateDeterminant(smallerRhs)); //Call recursively until 1x1 base case
        }
        return determinant;
    }

    public Double[][] getTranspose() { //Change a 2D Double array to the tranpose of said array
        Double[][] orig = this.getMatrixObject();
        Double[][] temp = new Double[this.getWidth()][this.getWidth()];
        for(int i = 0; i < this.getWidth(); i++) {
            for(int j = 0; j < this.getWidth(); j++) {
                temp[j][i] = orig[i][j]; //This is done by switch the row column of every elem i.e. RC (3,1) goes to (1,3) RC (2,1) goes to RC(1,2)
                                        //RC refers to RowColumn
            }
        }
        return temp;
    }

    public Double[][] getAdjoint() {
        Double [][] coFactorMatrix = new Double[this.getWidth()][this.getHeight()];
        Double [][] orig = this.getMatrixObject();
        for(int i = 0; i < this.getHeight(); i++) {
            for(int j = 0; j < this.getWidth(); j++) {
                Double [][] temp = new Double[this.getHeight()-1][this.getWidth()-1];
                getCofactorMatrix(orig,temp,i,j,orig.length);
                //Now temp hold the cofactor matrix for the specified Elem(i,j) in orig
                coFactorMatrix[i][j] = Math.pow(-1.0, (i+j+2)) * calculateDeterminant(temp);
            }
        }
        return coFactorMatrix;
    }

    public Double[][] getInverse() { //The inverse is defined as (1/det(Matrix)) * Transpose(Adjoint(Matrix))
        Matrix temp = new Matrix(this); //Copy the matrix we're targeting (So we don't modify the original)
        Double inveresedDeterminant = Math.pow(this.getDeterminant(), -1.0); //Find the inversed determinant of the matrix
        Double[][] inverseThis = temp.getAdjoint(); //Get the adjoint of the copied matrix
        temp.setMatrixObject(inverseThis); //Set the temp matrix to the adjoint
        inverseThis = temp.getTranspose(); //Tranpose the adjoint
        for(int i = 0; i < this.getHeight(); i++) { //For every row
            for(int j = 0; j < this.getWidth(); j++) { //For every column
                inverseThis[i][j] = inveresedDeterminant * inverseThis[i][j]; //Multiply each element of the T(Adj(Matrix) by Det(Matrix)^-1
            }
        }
        return inverseThis; //Return the inverse
    }
    public Double[][] getRref() { //Return the rref version of the array
        Matrix temp = new Matrix(this); //Establish temporary matrix object
        Double[][] temp_arr = temp.getMatrixObject(); //Grab the 2D array from temp and place it in temp_arr for easier access
        Integer temp_arr_width = temp.getWidth();
        for(int i = 0; i < temp.getHeight(); i++){ //For rows in 2D array
            if(temp_arr[i][i].equals(0.0)) {
                boolean plato = false;
                for(int x = i; x < temp.getHeight(); x++) {
                    if(temp_arr[x][i].compareTo(0.0) != 0) {
                        swapMatrixRows(temp_arr,x,i,temp_arr_width);
                        plato = true;
                        if(plato) break;
                    }
                }
                if(!plato) { //We didn't find a row to swap for
                    System.out.print("This system does not appear to be linearly dependent.\n");
                    break;
                }
            }
            Double lead = temp_arr[i][i];// Leading term is on the diagonal (0,0),(1,1),(2,2)...(n,n)
            for(int j = 0; j < temp.getWidth(); j++){ //For columns in row
                temp_arr[i][j] /= lead; //Divide every term in the row by the leading term to bring leading term to positive 1
            }
            ArrayList<Double> leadsExcludingCurrentRow = new ArrayList<>(); //Is going to hold the numbers above and below the current variable we're making 1
            int currentrow = i; //grab current row from iterative loop
            for(int columnCoeffs = 0; columnCoeffs < temp.getHeight(); columnCoeffs++) {
                if(columnCoeffs != currentrow) {
                    leadsExcludingCurrentRow.add(temp_arr[columnCoeffs][currentrow]);
                }
            }
            int position = 0; //Need to access LECR sequentially
            for(int j = 0; j < temp.getHeight(); j++) {//Modify all rows
                if(j != i) {//Except the row we are on
                    //Take the value in LECR and do EROPS using the value as the modifier
                    if(leadsExcludingCurrentRow.get(position) == 0) continue;
                    elementaryOps(temp_arr,temp_arr_width,i,leadsExcludingCurrentRow.get(position),j);
                    position++;
                }
            }
            /* This for loop should in theory grab the things above and below our current target
                E.G. if we have a matrix of
                        1 2 3
                        6 5 7
                        9 1 4
                If 1 is our target and we're trying to make the 6 and 9 become zeros, this will grab 6 and 9
                If 5 is our target, this will grab 2 and 1
                If 4 is our target this will grab 3 and 7
             */
            //TODO: Make elementary row operations and finish rest of rref algorithm
            //TODO: Finish sanity checking for the infinite solutions case, soltuion as defined with multiple variables, i.e., solution with t,s,etc.
            //TODO: Finish sanity check for matrix with no solution
        }
        return temp.getMatrixObject();
    }

    public void swapMatrixRows(Double[][] matrix, Integer target, Integer home, Integer arr_width) { //Pass in a 2d array, with the row you want to swap from (TARGET) to the row you want to swap to, either way it will swap the rows
        for(int i = 0; i < arr_width; i++) {
            Double temp = matrix[target][i]; //Classic hold variable
            System.out.print("Temp for Row: " + target + " Col: " + i + " Value: " + temp);
            matrix[target][i] = matrix[home][i]; //Import new into old
            matrix[home][i] = temp; //Update new to old
            printDoubleTwoDArray(matrix);
        }
    }
    public void elementaryOps(Double[][] matrix, Integer temp_arr_width, Integer modifiedRow, Double modifier, Integer transformedRow){
        for(int i = 0; i < temp_arr_width; i++){
            printDoubleTwoDArray(matrix);
            Double executioner = (modifier * matrix[modifiedRow][i] * -1); //Take the modifiedRow value multiplied by the modifier
            matrix[transformedRow][i] += executioner;//Combine with the value in the transformedRow
        }
    }
    public static void fillInMatrix(Double [][] rhs) { //Initialize a Matrix's matrixObject (2D Double Array) to all 0.0.
        for(int i = 0; i < rhs.length; i++) {
            for(int j = 0; j < rhs.length; j++) {
                rhs[i][j] = 0.0;
            }
        }
    }

    public static Matrix searchDataStore(String name, ArrayList<Matrix> dataStore) { //Searches the datastore of matrix's in order to return a Matrix object
        Matrix temp = new Matrix(); //Create container Matrix
        for(Matrix target : dataStore) { //Search the dataStore of Matrices
            if(target.getName().equals(name.toUpperCase())) { //If the search name matches a name of a Matrix
                temp = target; //Copy into container Matrix
            }
        }
        return temp; //Return contaier
    }

    public static boolean searchForNameConflict(String name, ArrayList<Matrix> dataStore) { //Searches for name conflict of matrices
       for(Matrix i : dataStore) {
           if(i.getName().equals(name.toUpperCase())) {
               return true;
           }
       }
       return false;
    }

}
