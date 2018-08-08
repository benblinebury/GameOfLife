package com.zipcodeconway;
import java.util.Random;

public class ConwayGameOfLife {
    int [][] protoMatrix;
    int [][] neoMatrix;
    SimpleWindow display;
    int dimension;


    public ConwayGameOfLife(Integer dimension) {
        this.dimension = dimension;
        this.display = new SimpleWindow(dimension);
        protoMatrix = createRandomStart(dimension);
        neoMatrix = new int[dimension][dimension];
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        this.dimension = dimension;
        this.display = new SimpleWindow(dimension);
        protoMatrix = startmatrix;
        neoMatrix = new int[dimension][dimension];
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        int[][] endingWorld = sim.simulate(50);


    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        Random random = new Random();
        int[][] newArr = new int[dimension][dimension];

        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++){
                newArr[i][j] = random.nextInt(2);
            }
        }

        return newArr;
    }

    public int[][] simulate(Integer maxGenerations) {

        for(int gen = 0; gen <= maxGenerations; gen++) {
            this.display.display(protoMatrix, gen);
            for(int i = 0; i < protoMatrix.length; i++){
                for(int j = 0; j < protoMatrix.length; j++){
                    neoMatrix[i][j] = isAlive(i, j, protoMatrix);

                }
            }
            copyAndZeroOut(neoMatrix, protoMatrix);
            this.display.sleep(150);
        }
        return protoMatrix;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                current[i][j] = next[i][j];
                next[i][j] = 0;
            }
        }

    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) {
        int count = 0;
        int status = 0;
        int edgeI;
        int edgeJ;


        for (int i = row -1; i <= row + 1; i++){
            for(int j = col -1; j <= col + 1; j++){

                edgeI= i;
                edgeJ = j;

                if(i == - 1){
                    i = dimension - 1;
                }
                if (i == dimension){
                    i = 0;
                }
                if(j == - 1) {
                    j = dimension - 1;
                }
                if(j == dimension){
                    j = 0;
                }

                if(world[i][j] == 1 && !((i == row) && (j == col))) {
                    count++;
                }

                i = edgeI;
                j = edgeJ;
            }

        }

        if(count == 3) {
                status = 1;
            }

            if(world[row][col] == 1 && count == 2) {
                status = 1;
            }

        return status;
    }
}
