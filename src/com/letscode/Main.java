package com.letscode;

import com.letscode.batalhaNaval.Jogadas;
import com.letscode.batalhaNaval.Jogador;

public class Main {

    public static void main(String[] args) {
        int[][] matriz = new int[10][10];
        Jogadas jogadas = new Jogadas();
        Tabuleiro tabuleiroJogador = new Tabuleiro(matriz);
        Tabuleiro tabuleiroPC = new Tabuleiro(matriz);
        Jogador jogador = new Jogador(tabuleiroJogador,10,jogadas.obterNomeJogador());

        jogadas.inserirNaviosTabuleiro(tabuleiroPC,tabuleiroJogador,jogador.getQtdeMaximaDeNavios(),jogador);
        jogadas.raizPrintTabuleiro(jogador.getNome(),tabuleiroJogador,"Computador",tabuleiroPC);
        jogadas.realizarAtaque();
    }
}
