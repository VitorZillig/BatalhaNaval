package com.letscode.batalhaNaval;

import java.util.Random;
import java.util.Scanner;


public class Jogadas {
    private Scanner sc = new Scanner(System.in);
    private boolean turno = true;
    private int boardSize;

    public Jogadas(int boradSize) {
        this.boardSize = boradSize;
    }

    public boolean getTurno(){ return this.turno; }

    public Tabuleiro alocarNaviosAleatoriamente(int qtdeMaximaDeNavios){
        int qtdeRestanteNavios = qtdeMaximaDeNavios;
        int [][] novoTabuleiro = new int[this.boardSize][this.boardSize];
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
        if (jogador.getIsBot()){
            jogador.getTabuleiro().setMatriz(alocarNaviosAleatoriamente(jogador.getQtdeMaximaDeNavios()));
        } else {
            System.out.print(jogador.getNome() + " deseja que aloquemos pra você as peças no tabuleiro? (S|N) ");
            char opcao = Character.toUpperCase(sc.next().charAt(0));
            if(opcao=='S'){
                jogador.getTabuleiro().setMatriz(alocarNaviosAleatoriamente(jogador.getQtdeMaximaDeNavios()));
            }else {
                for (int i=0; i<jogador.getQtdeMaximaDeNavios(); i++){
                    int[] posicao = receberPosicao("Favor informar posição para o " + (i+1) + "º navio: (Ex.: A03) ");
                    while(jogador.getTabuleiro().getMatriz()[posicao[0]][posicao[1]] == 1) {
                        System.out.println("Favor escolher uma posição que não esteja ocupada.");
                        posicao = receberPosicao("Favor informar posição para o " + (i+1) + "º navio: (Ex.: A03) ");
                    }
                    jogador.getTabuleiro().getMatriz()[posicao[0]][posicao[1]] = 1;
                }
            }
        }
    }

    private int[] receberPosicao(String text){
        System.out.print(text);
        String pos = sc.next();
        boolean posicaoOK = verificarEstruturaPosicao(pos);
        while(!posicaoOK){
            char lastLetter = (char) (64 + this.boardSize);
            String lastNumber = this.boardSize >= 9 ? String.valueOf(this.boardSize) : "0"+this.boardSize;
            System.out.print("Favor informar posição válida (A - " + lastLetter + ") e (01 - " + lastNumber + "): ");
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
            if(posicaoColuna >= this.boardSize){
                return false;
            }else if(posicaoLinha> this.boardSize){
                return false;
            }
            String regexVerificacao = "^[A-Za-z][0-9]{2}$";
            return posicao.matches(regexVerificacao);
        } catch (Exception e){
            return false;
        }
    }

    public void atacarNaPosicao(int posX, int posY, Jogador jogador, Jogador jogador2){
        int[][] tabuleiroJogador = jogador.getTabuleiro().getMatriz();
        int[][] tabuleiroPC = jogador2.getTabuleiro().getMatriz();
        if(tabuleiroPC[posX][posY] != 0 && tabuleiroPC[posX][posY] != 2 && tabuleiroPC[posX][posY] != 3) {
            if (tabuleiroJogador[posX][posY] == 1) tabuleiroJogador[posX][posY] = 4;
            else tabuleiroJogador[posX][posY] = 3;
            jogador2.setNaviosRestantes(jogador2.getNaviosRestantes() - 1);
            if (jogador.getIsBot()){
                System.out.println("\n");
                System.out.println("********* O bot acertou o tiro :X! *********");
            }else{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println("********** Você acertou o tiro! :D *********");
            }
        }
        else if(tabuleiroPC[posX][posY] == 0 || tabuleiroPC[posX][posY] == 2 || tabuleiroPC[posX][posY] == 3) {
            if (tabuleiroJogador[posX][posY] == 1) tabuleiroJogador[posX][posY] = 5;
            else tabuleiroJogador[posX][posY] = 2;
            if (jogador.getIsBot()){
                System.out.println("\n");
                System.out.println("********** O bot errou o tiro :D! **********");
            }else{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println("----------- Você Errou o tiro :( -----------");
            }
        }
        this.turno = !this.turno;
    }

    public void realizarAtaque(Jogador jogador, Jogador jogador2){
        if (jogador.getIsBot()){
            Random numeroAleatorio = new Random();
            int linha = numeroAleatorio.nextInt(this.boardSize);
            int coluna = numeroAleatorio.nextInt(this.boardSize);
            int[][] tabuleiro = jogador.getTabuleiro().getMatriz();
            while(tabuleiro[linha][coluna] != 0 && tabuleiro[linha][coluna] != 1){
                linha = numeroAleatorio.nextInt(this.boardSize);
                coluna = numeroAleatorio.nextInt(this.boardSize);
            }
            atacarNaPosicao(linha, coluna, jogador, jogador2);
        }else {
            int[] pos =  receberPosicao("Favor informar posição para ataque: (Ex.: A03) ");
            int[][] tabuleiroJogador = jogador.getTabuleiro().getMatriz();
            boolean flag = tabuleiroJogador[pos[0]][pos[1]] != 0 && tabuleiroJogador[pos[0]][pos[1]] != 1;
            while (flag){
                System.out.println("Favor escolha uma posição não selecionada anteriormente!");
                pos = receberPosicao("Favor informar posição para ataque: (Ex.: A03) ");
                flag = tabuleiroJogador[pos[0]][pos[1]] != 0 && tabuleiroJogador[pos[0]][pos[1]] != 1;
            }
            atacarNaPosicao(pos[0], pos[1], jogador, jogador2);
        }
    }

    public void exibirInidiceColunas(){
        char letraColuna = 65; //65 é o código do char "A".
        StringBuilder letraCabecalho = new StringBuilder("   | ");
        for(int i = 0; i < this.boardSize; i++){
            letraCabecalho.append(letraColuna++).append(" | ");
        }
        System.out.println(letraCabecalho);
    }

    public void formater(String nomeJogador, String delimiter, boolean loser, String beforeDelimiter){
        int stringSize = 4*this.boardSize+4;
        StringBuilder beforeAfter = new StringBuilder(stringSize);
        beforeAfter.append(String.valueOf(beforeDelimiter).repeat(stringSize));
        System.out.println(beforeAfter);

        StringBuilder sb = new StringBuilder(stringSize);
        if (!loser) sb.append("WINNER ");
        else sb.append(String.valueOf(delimiter).repeat(Math.max(0,7)));
        sb.append(String.valueOf(delimiter).repeat(Math.max(0, (stringSize - sb.length()*2 - nomeJogador.length() - 2)/2)));
        sb.append(" ").append(nomeJogador).append(" ");
        sb.append(String.valueOf(delimiter).repeat(Math.max(0, (stringSize - sb.length()-7))));
        if (!loser) sb.append(" WINNER");
        else sb.append((String.valueOf(delimiter).repeat(Math.max(0,7))));
        System.out.println(sb);

        System.out.println(beforeAfter);
    }

    public void printTabuleiro(String nomeJogador, Tabuleiro tabuleiroRecebido, boolean loser){

        if (!loser){
            formater(nomeJogador,"*",false,"*");
        } else {
            formater(nomeJogador," ",true,"-");
        }

        exibirInidiceColunas();
        StringBuilder linhaTabuleiro;
        int numeroLinha = 1;
        for(int[] linha : tabuleiroRecebido.getMatriz()){
            if(numeroLinha< 10){
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

    public void contarNaviosRestantes(Jogador jogador){
        System.out.println();
        String text = jogador.getQtdeMaximaDeNavios() - jogador.getNaviosRestantes() == 1 ? " navio seu ******" : " navios seus ****";
        System.out.println("**** O adversário acertou "+(jogador.getQtdeMaximaDeNavios()-jogador.getNaviosRestantes()) + text);
    }

    public void printRaizTabuleiro(Jogador jogador, Jogador jogador2){
        if (jogador2.getNaviosRestantes() == 0 || jogador.getNaviosRestantes() == 0){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            printTabuleiro(jogador.getNome(), jogador.getTabuleiro(), jogador.getNaviosRestantes() == 0);
            System.out.println();
            printTabuleiro(jogador2.getNome(), jogador2.getTabuleiro(), jogador2.getNaviosRestantes() == 0);
        }else printTabuleiro(jogador.getNome(), jogador.getTabuleiro(), true);
    }
}
