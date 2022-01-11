package com.letscode.batalhaNaval;

import java.util.Scanner;

public class Menu {
    int[][] matrizTabuleiro = new int[10][10];
    int qteNavios = 10;
    boolean[] playersState = new boolean[]{true, true};
    String[] playersName= new String[]{"Computador", "Computador"};
    int gameMode = 2;

    Scanner sc =  new Scanner(System.in);

    public int[][] getMatrizTabuleiro() { return matrizTabuleiro; }

    public int getQteNavios() { return qteNavios; }

    public String[] getPlayersName() { return playersName; }

    public boolean[] getPlayersStatus() { return playersState; }

    private int playerInput() {
        int input = 0;
        boolean goodInput = true;
        while (goodInput){
            goodInput = false;
            String inputString = this.sc.next();
            try {
                input = Integer.parseInt(inputString);
            }catch (Exception e) {
                System.out.print("Por favor, utilize um número válido: ");
                goodInput = true;
            }
        }
        return input;
    }

    public void menuOptions() {
        System.out.print("Com quantos navios gostaria de jogar? (Default: 10) ");
        this.qteNavios = playerInput();

        System.out.print("Qual tamanho do Tabuleiro deseja? (Default: 10) ");
        int squareSize = playerInput();
        this.matrizTabuleiro = new int[squareSize][squareSize];
        System.out.print("\n1 -> 1  vs PC\n" +
                           "2 -> PC vs PC\n");
        System.out.print("Selecione o modo de jogo: (Default: 1 vs PC) ");
        this.gameMode = playerInput();

        switch (gameMode){
            case 2:
                break;
            default:
                this.playersName[0] = obterNomeJogador('1');
                this.playersState = new boolean[]{false, true};
        }
    }

    private String obterNomeJogador(char playerNumber){
        System.out.print("Informe seu nome jogador " + playerNumber + " : ");
        return this.sc.next();
    }
}

