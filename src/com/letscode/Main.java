package com.letscode;

import com.letscode.batalhaNaval.Jogadas;
import com.letscode.batalhaNaval.Jogador;

public class Main {

    public static void main(String[] args) {
        int[][] matriz = new int[10][10];
        Jogadas jogadas = new Jogadas();
        Jogador jogador = new Jogador(matriz,10,jogadas.obterNomeJogador(),false);
        Jogador pc = new Jogador(matriz, 10, "Computador", true);

        jogadas.inserirNaviosTabuleiro(jogador);
        jogadas.inserirNaviosTabuleiro(pc);
        jogadas.raizPrintTabuleiro(jogador.getNome(),jogador.getTabuleiro(),"Computador",pc.getTabuleiro());
        jogadas.realizarAtaque(jogador, pc);
        jogadas.raizPrintTabuleiro(jogador.getNome(),jogador.getTabuleiro(),"Computador",pc.getTabuleiro());
        jogadas.realizarAtaque(jogador, pc);
        jogadas.raizPrintTabuleiro(jogador.getNome(),jogador.getTabuleiro(),"Computador",pc.getTabuleiro());
        jogadas.realizarAtaque(jogador, pc);
        jogadas.raizPrintTabuleiro(jogador.getNome(),jogador.getTabuleiro(),"Computador",pc.getTabuleiro());
    }
}
