package com.letscode.batalhaNaval;

public class Tabuleiro {
    private int[][] matriz;

    public Tabuleiro(int matriz[][]){
        this.matriz = matriz;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(Tabuleiro matriz) {
        this.matriz = matriz.getMatriz();
    }

}
