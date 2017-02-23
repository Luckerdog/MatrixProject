package com.lopez.com.lopez.matrixProject;

import java.util.ArrayList;

/**
 * Created by be127 on 2/14/17.
 */
public class Matrix {
    private Double[][] matrixObject = new Double[1][1];
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

    public Matrix(Double[][] matrixObject, Integer width, Integer height, String name) {
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
        Integer i = 0, j = 0;
        for(int row = 0; row < dimension; row++) {
            for(int col = 0; col < dimension; col++) {
                if(row != forbiddenRow && col != forbiddenColumn) {
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
        Double determinant = 0.0;
        if(rhs.length == 1) {
            return(rhs[0][0]);
        }
        for(int i = 0; i < rhs.length; i++) {
            Double[][] smallerRhs = new Double[rhs.length-1][rhs.length-1];
            for(int r = 1; r < rhs.length; r++) {
                for(int c = 0; c < rhs.length; c++) {
                    if(c < i) smallerRhs[r-1][c] = rhs[r][c];
                    else if(c > i) smallerRhs[r-1][c-1] = rhs[r][c];
                }
            }
            int sign = 1;
            if(i % 2 != 0) sign *= -1;
            determinant += sign * rhs[0][i] * (calculateDeterminant(smallerRhs));
        }
        return determinant;
    }

    public Double[][] getTranspose() {
        Double[][] orig = this.getMatrixObject();
        Double[][] temp = new Double[this.getWidth()][this.getWidth()];
        for(int i = 0; i < this.getWidth(); i++) {
            for(int j = 0; j < this.getWidth(); j++) {
                temp[j][i] = orig[i][j];
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

    public Double[][] getInverse() {
        Matrix temp = new Matrix(this);
        Double inveresedDeterminant = Math.pow(this.getDeterminant(), -1.0);
        Double[][] inverseThis = temp.getAdjoint();
        temp.setMatrixObject(inverseThis);
        inverseThis = temp.getTranspose();
        for(int i = 0; i < this.getHeight(); i++) {
            for(int j = 0; j < this.getWidth(); j++) {
                inverseThis[i][j] = inveresedDeterminant * inverseThis[i][j];
            }
        }
        return inverseThis;
    }
    public Double[][] getRref() {
        Matrix temp = new Matrix(this);
        Double[][] temp_arr = temp.getMatrixObject();
        for(int i = 0; i < temp.getHeight(); i++){
            Double lead =  temp_arr[i][i];
            for(int j = 0; j < temp.getWidth(); j++){
                temp_arr[i][j] /= lead;
            }
            Double neatLead =  temp_arr[i][i+1];
            for(int k = 0; k < temp.getHeight(); k++){

            }
        }
        return temp.getMatrixObject();
    }
    public static void fillInMatrix(Double [][] rhs) {
        for(int i = 0; i < rhs.length; i++) {
            for(int j = 0; j < rhs.length; j++) {
                rhs[i][j] = 0.0;
            }
        }
    }

    public static Matrix searchDataStore(String name, ArrayList<Matrix> dataStore) { //Searches the datastore of matrix's in order to return a Matrix object
        Matrix temp = new Matrix();
        for(Matrix target : dataStore) {
            if(target.getName().equals(name.toUpperCase())) {
                temp = target;
            }
        }
        return temp;
    }

}
