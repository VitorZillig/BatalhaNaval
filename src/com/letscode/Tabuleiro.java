package com.letscode;

import java.util.Random;
import java.util.Scanner;

public class Tabuleiro {
    String nomeJogador, nomeJogador2;
    Scanner sc = new Scanner(System.in);
    public int tabuleiroPC[][] = new int[10][10];
    public int tabuleiroJogador[][] = new int[10][10];
    public int qtdeMaximaDeNavios = 10;

    //Botei para inserir em ambos tabuleiros de forma aleatória.
    public void inserirNaviosTabuleiro(){
        tabuleiroPC = alocarNaviosTabuleiro();
        tabuleiroJogador = alocarNaviosTabuleiro();
    }

    public void obterNomeJogador(){
        System.out.print("Informe seu nome: ");
        this.nomeJogador = sc.nextLine();
        this.nomeJogador2 = "Computador";
    }

    public void exibirInidiceColunas(){
        //65 é o código do char "A".
        char letraColuna = 65;
        String letraCabecalho = "   | ";
        for(int i = 0; i < 10; i++){
            letraCabecalho += (letraColuna++) + " | ";
        }
        System.out.println(letraCabecalho);
    }

    //Implantar a lógica aqui de printar o tabuleiro do PC apenas caso ele ganhe.
    public void raizPrintTabuleiro(){
        printTabuleiro(nomeJogador, tabuleiroJogador);
        printTabuleiro(nomeJogador2, tabuleiroPC);
    }

    public void printTabuleiro(String nomeJogador, int[][]tabuleiroRecebido){

        System.out.println("--------------------------------------------");
        System.out.println("                 " + nomeJogador + "     ");
        System.out.println("--------------------------------------------");

        exibirInidiceColunas();
        //Montando as linhas
        String linhaTabuleiro = "";
        int numeroLinha = 1;
        for(int[] linha : tabuleiroRecebido){
            if(numeroLinha<10){
                linhaTabuleiro = (numeroLinha++) + "  |";
            }else {
                linhaTabuleiro = (numeroLinha++) + " |";
            }

            for(int coluna : linha){
                switch (coluna){
                    case 0: //Vazio
                        linhaTabuleiro += "   |";
                        break;
                    case 1: //Navio
                            linhaTabuleiro += " N |";
                            break;
                    case 2: //Errou o tiro
                        linhaTabuleiro += " - |";
                        break;
                    case 3: //Acertou
                        linhaTabuleiro += " * |";
                        break;
                    case 4: //Tiro certeiro com navio posicionado
                        linhaTabuleiro += " X |";
                        break;
                    case 5: //Tiro na água com navio posicionado
                        linhaTabuleiro += " n |";
                        break;
                }
            }
            System.out.println(linhaTabuleiro);
        }
    }

    public int[][] alocarNaviosTabuleiro(){
        int qtdeRestanteNavios = qtdeMaximaDeNavios;
        int [][] novoTabuleiro = new int[10][10];
        Random numeroAleatorio = new Random();
        int x= 0, y= 0;

        while (qtdeRestanteNavios>0){
            x=0;
            y=0;
            for(int[] linha : novoTabuleiro){
                for(int coluna : linha){
                    if(numeroAleatorio.nextInt(100)<=10){
                        //0 refere-se ao campo em branco, sem navios;
                        if(coluna==0){
                            //1 refere-se ao campo que possui navio.
                            novoTabuleiro[x][y] = 1;
                            qtdeRestanteNavios--;
                            break;
                        }
                        if(qtdeRestanteNavios<=0){
                            break;
                        }
                    }
                    y++;
                }
                y=0;
                x++;
                if(qtdeRestanteNavios<=0){
                    break;
                }
            }
        }
        return novoTabuleiro;
    }
}
