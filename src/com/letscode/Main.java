package com.letscode;

import com.letscode.batalhaNaval.Jogadas;
import com.letscode.batalhaNaval.Jogador;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] matriz = new int[10][10];
        Scanner sc = new Scanner(System.in);

        boolean partida = true;
        while(partida){
            Jogadas jogadas = new Jogadas();
            Jogador jogador = new Jogador(matriz,10,jogadas.obterNomeJogador(),false);
            Jogador pc = new Jogador(matriz, 10, "Computador", true);
            jogadas.inserirNaviosTabuleiro(jogador);
            jogadas.inserirNaviosTabuleiro(pc);
            jogadas.raizPrintTabuleiro(jogador,pc);

            while(jogador.getNaviosRestantes() != 0 && pc.getNaviosRestantes() != 0){
                if (jogadas.getTurno()){
                    jogadas.realizarAtaque(jogador, pc);
                    jogadas.raizPrintTabuleiro(jogador,pc);
                } else {
                    jogadas.realizarAtaque(pc, jogador);
                }
            }
            jogadas.raizPrintTabuleiro(jogador,pc);
            System.out.print("\nGostaria de recomeçar a partida? (S/N)");
            String recomecar = sc.next().toUpperCase(Locale.ROOT);
            if (recomecar.charAt(0) == 'N') partida = false;
        }

    }
}