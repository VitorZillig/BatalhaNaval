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
        int x= 0, y= 0;

        while (qtdeRestanteNavios>0){
            x=0;
            y=0;
            for(int[] linha : novoTabuleiro){
                for(int coluna : linha){
                    if(numeroAleatorio.nextInt(100)<=10){//Aplicado uma probabilidade de 10% pra escolher a linha.
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
        return new Tabuleiro(novoTabuleiro);
    }
    //Botei para inserir em ambos tabuleiros de forma aleatória.
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
        int posicaoColuna = posicaoColunaCaracter - 97; //O caracter 'a' é um inteiro 97, então subtraindo trago para minha posição 0.
        int posicaoLinha = Integer.parseInt(pos.substring(1)) - 1; //Tirando 1 porque a matriz começa no 0.
        return new int[]{posicaoColuna, posicaoLinha};
    }

    public boolean verificarEstruturaPosicao(String posicao){
        char posicaoColunaCaracter = posicao.toLowerCase().charAt(0);
        int posicaoColuna = posicaoColunaCaracter - 97;
        int posicaoLinha = Integer.parseInt(posicao.substring(1));

        //Validando caso o jogador escolha posição além do j e além do 10, pois nossa matriz é 10x10.
        if(posicaoColuna>10){
            return false;
        }else if(posicaoLinha>10){
            return false;
        }
        String regexVerificacao = "^[A-Za-z]{1}[0-9]{2}$";
        return posicao.matches(regexVerificacao);
    }

    public void atacaNaPosicao(int posX, int posY, Jogador jogador){
        int[][] tabuleiro = jogador.getTabuleiro().getMatriz();
        if(tabuleiro[posX][posY] == 1) tabuleiro[posX][posY] = 3;
        else if(tabuleiro[posX][posY]==0) tabuleiro[posX][posY] = 2;
    }

    public void realizarAtaque(Jogador jogador){
        System.out.print("Favor informar posição para ataque: (Ex.: A03)");
        String posicaoAtacada = sc.next();
        boolean posicaoOK = verificarEstruturaPosicao(posicaoAtacada);
        while(!posicaoOK){
            System.out.print("Favor informar posição válida (A - J) e (01 - 10): ");
            posicaoAtacada = sc.next();
            posicaoOK = verificarEstruturaPosicao(posicaoAtacada);
        }
        char posicaoColunaCaracter = posicaoAtacada.toLowerCase().charAt(0);
        int posicaoColuna = posicaoColunaCaracter - 97; //O caracter 'a' é um inteiro 97, então subtraindo trago para minha posição 0.
        int posicaoLinha = Integer.parseInt(posicaoAtacada.substring(1)) - 1; //Tirando 1 porque a matriz começa no 0.
        atacaNaPosicao(posicaoLinha, posicaoColuna, jogador);
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

    public void printTabuleiro(String nomeJogador, Tabuleiro tabuleiroRecebido){

        System.out.println("--------------------------------------------");
        System.out.println("                 " + nomeJogador + "     ");
        System.out.println("--------------------------------------------");

        exibirInidiceColunas();
        //Montando as linhas
        String linhaTabuleiro = "";
        int numeroLinha = 1;
        for(int[] linha : tabuleiroRecebido.getMatriz()){
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

    //Implantar a lógica aqui de printar o tabuleiro do PC apenas caso ele ganhe.
    public void raizPrintTabuleiro(String nomeJogador,Tabuleiro tabuleiroJogador,String nomeJogador2, Tabuleiro tabuleiroPC){
        printTabuleiro(nomeJogador, tabuleiroJogador);
        printTabuleiro(nomeJogador2, tabuleiroPC);
    }
}
