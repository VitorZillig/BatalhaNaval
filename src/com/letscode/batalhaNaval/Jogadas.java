package com.letscode.batalhaNaval;

import com.letscode.Tabuleiro;

import java.util.Random;
import java.util.Scanner;

public class Jogadas {
    Scanner sc = new Scanner(System.in);

    public String obterNomeJogador(){
        String nomeJogador;
        System.out.print("Informe seu nome: ");
        nomeJogador = sc.nextLine();
        return nomeJogador;
    }

    public Tabuleiro alocarNaviosAleatoriamente(int qtdeMaximaDeNavios){
        int qtdeRestanteNavios = qtdeMaximaDeNavios;
        int [][] novoTabuleiro = new int[10][10];
        Random numeroAleatorio = new Random();
        int x, y;

        while (qtdeRestanteNavios > 0){
            x=0;
            y=0;
            for(int[] linha : novoTabuleiro){
                for(int coluna : linha){
                    if(numeroAleatorio.nextInt(100)<=10){
                        if(coluna==0){
                            novoTabuleiro[x][y] = 1;
                            qtdeRestanteNavios--;
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
        return new Tabuleiro(novoTabuleiro);
    }

    public void inserirNaviosTabuleiro(Jogador jogador){
        if (jogador.isBot()){
            jogador.getTabuleiro().setMatriz(alocarNaviosAleatoriamente(jogador.getQtdeMaximaDeNavios()));
        } else {
            System.out.print(jogador.getNome() + " deseja que aloquemos pra você as peças no tabuleiro? (S|N)");
            char opcao = Character.toUpperCase(sc.next().charAt(0));
            if(opcao=='S'){
                jogador.getTabuleiro().setMatriz(alocarNaviosAleatoriamente(jogador.getQtdeMaximaDeNavios()));
            }else {
                for (int i=0; i<jogador.getQtdeMaximaDeNavios(); i++){
                    int[] posicao = recebePosicao("Favor informar posição para o " + i+1 + "º navio: (Ex.: A03)");
                    jogador.getTabuleiro().getMatriz()[posicao[0]][posicao[1]] = 1;
                }
            }
        }
    }

    private int[] recebePosicao(String text){
        System.out.print(text);
        String pos = sc.next();
        boolean posicaoOK = verificarEstruturaPosicao(pos);
        while(!posicaoOK){
            System.out.print("Favor informar posição válida (A - J) e (01 - 10): ");
            pos = sc.next();
            posicaoOK = verificarEstruturaPosicao(pos);
        }
        char posicaoColunaCaracter = pos.toLowerCase().charAt(0);
        int posicaoColuna = posicaoColunaCaracter - 97;
        int posicaoLinha = Integer.parseInt(pos.substring(1)) - 1;
        return new int[]{posicaoLinha, posicaoColuna};
    }

    public boolean verificarEstruturaPosicao(String posicao){
        try {
            char posicaoColunaCaracter = posicao.toLowerCase().charAt(0);
            int posicaoColuna = posicaoColunaCaracter - 97; // 97 é o código do char "a".
            int posicaoLinha = Integer.parseInt(posicao.substring(1));
            if(posicaoColuna>10){
                return false;
            }else if(posicaoLinha>10){
                return false;
            }
            String regexVerificacao = "^[A-Za-z][0-9]{2}$";
            return posicao.matches(regexVerificacao);
        } catch (Exception e){
            return false;
        }
    }

    public void atacaNaPosicao(int posX, int posY, Jogador jogador, Jogador pc){
        int[][] tabuleiroJogador = jogador.getTabuleiro().getMatriz();
        int[][] tabuleiroPC = pc.getTabuleiro().getMatriz();
        if(tabuleiroPC[posX][posY] != 0 && tabuleiroPC[posX][posY] != 2 && tabuleiroPC[posX][posY] != 3) {
            if (tabuleiroJogador[posX][posY] == 1) tabuleiroJogador[posX][posY] = 4;
            else tabuleiroJogador[posX][posY] = 3;
            System.out.println("*** Acertou o tiro! :D ***");
        }
        else if(tabuleiroPC[posX][posY] == 0 || tabuleiroPC[posX][posY] == 2 || tabuleiroPC[posX][posY] == 3) {
            if (tabuleiroJogador[posX][posY] == 1) tabuleiroJogador[posX][posY] = 5;
            else tabuleiroJogador[posX][posY] = 2;
            System.out.println("--- Errou o tiro :( ---");
        }
    }

    public void realizarAtaque(Jogador jogador, Jogador pc){
        int[] pos =  recebePosicao("Favor informar posição para ataque: (Ex.: A03)");
        int[][] tabuleiroJogador = jogador.getTabuleiro().getMatriz();
        boolean flag = tabuleiroJogador[pos[0]][pos[1]] != 0 && tabuleiroJogador[pos[0]][pos[1]] != 1;
        while (flag){
            System.out.println("Favor escolha uma posição não selecionada anteriormente!");
            pos = recebePosicao("Favor informar posição para ataque: (Ex.: A03)");
            flag = tabuleiroJogador[pos[0]][pos[1]] != 0 && tabuleiroJogador[pos[0]][pos[1]] != 1;
        }
        atacaNaPosicao(pos[0], pos[1], jogador, pc);
    }

    public void exibirInidiceColunas(){
        //65 é o código do char "A".
        char letraColuna = 65;
        StringBuilder letraCabecalho = new StringBuilder("   | ");
        for(int i = 0; i < 10; i++){
            letraCabecalho.append(letraColuna++).append(" | ");
        }
        System.out.println(letraCabecalho);
    }

    public void printTabuleiro(String nomeJogador, Tabuleiro tabuleiroRecebido){

        System.out.println("--------------------------------------------");
        System.out.println("                 " + nomeJogador + "     ");
        System.out.println("--------------------------------------------");

        exibirInidiceColunas();
        StringBuilder linhaTabuleiro;
        int numeroLinha = 1;
        for(int[] linha : tabuleiroRecebido.getMatriz()){
            if(numeroLinha<10){
                linhaTabuleiro = new StringBuilder((numeroLinha++) + "  |");
            }else {
                linhaTabuleiro = new StringBuilder((numeroLinha++) + " |");
            }

            for(int coluna : linha){
                switch (coluna){
                    case 0: //Vazio
                        linhaTabuleiro.append("   |");
                        break;
                    case 1: //Navio
                        linhaTabuleiro.append(" N |");
                        break;
                    case 2: //Errou o tiro
                        linhaTabuleiro.append(" - |");
                        break;
                    case 3: //Acertou
                        linhaTabuleiro.append(" * |");
                        break;
                    case 4: //Tiro certeiro com navio posicionado
                        linhaTabuleiro.append(" X |");
                        break;
                    case 5: //Tiro na água com navio posicionado
                        linhaTabuleiro.append(" n |");
                        break;
                }
            }
            System.out.println(linhaTabuleiro);
        }
    }

    //Implantar a lógica aqui de printar o tabuleiro do PC apenas caso ele ganhe.
    public void raizPrintTabuleiro(String nomeJogador,Tabuleiro tabuleiroJogador,String nomeJogador2, Tabuleiro tabuleiroPC){
        printTabuleiro(nomeJogador, tabuleiroJogador);
        printTabuleiro(nomeJogador2, tabuleiroPC);
    }
}
