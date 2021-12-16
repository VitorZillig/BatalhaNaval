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
        System.out.print(this.nomeJogador + " deseja que aloquemos pra você as peças no tabuleiro? (S|N)");
        char opcao = Character.toUpperCase(sc.next().charAt(0));
        if(opcao=='S'){
            tabuleiroJogador = alocarNaviosTabuleiro();
        }else { //Implementar lógica para escolha pelo próprio jogador do posicionamento das peças.
            System.out.println("Implementar lógica...");
        }

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
        return novoTabuleiro;
    }

    public void realizarAtaque(){
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
}
