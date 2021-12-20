package com.letscode;

import com.letscode.batalhaNaval.Jogadas;
import com.letscode.batalhaNaval.Jogador;

public class Main {

    public static void main(String[] args) {
        int[][] matriz = new int[10][10];
        Jogadas jogadas = new Jogadas();
        Jogador jogador = new Jogador(matriz,10,jogadas.obterNomeJogador(),false);
        Jogador PC = new Jogador(matriz, 10, "Computador", true);

        jogadas.inserirNaviosTabuleiro(jogador);
        jogadas.inserirNaviosTabuleiro(PC);
        jogadas.raizPrintTabuleiro(jogador.getNome(),jogador.getTabuleiro(),"Computador",PC.getTabuleiro());
        jogadas.realizarAtaque(jogador);
        jogadas.printTabuleiro(jogador.getNome(),jogador.getTabuleiro());
    }
}
