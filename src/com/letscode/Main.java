package com.letscode;

import com.letscode.batalhaNaval.Jogadas;
import com.letscode.batalhaNaval.Jogador;
import com.letscode.batalhaNaval.Menu;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        int[][] matriz = new int[10][10];
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        boolean partida = true;
        while(partida){
            menu.menuOptions();
            Jogadas jogadas = new Jogadas();
            Jogador jogador = new Jogador(menu.getMatrizTabuleiro(),menu.getQteNavios(), menu.getPlayersName()[0],menu.getPlayersStatus()[0]);
            Jogador pc = new Jogador(menu.getMatrizTabuleiro(), menu.getQteNavios(), menu.getPlayersName()[1], menu.getPlayersStatus()[1]);
            jogadas.inserirNaviosTabuleiro(jogador);
            jogadas.inserirNaviosTabuleiro(pc);
            jogadas.raizPrintTabuleiro(jogador,pc);

            while(jogador.getNaviosRestantes() != 0 && pc.getNaviosRestantes() != 0){
                if (jogadas.getTurno()){
                    jogadas.realizarAtaque(jogador, pc);
                    jogadas.contarNaviosRestantes(pc);
                    if (!jogador.isBot()) {
                        jogadas.raizPrintTabuleiro(jogador, pc);
                    }
                } else {
                    jogadas.realizarAtaque(pc, jogador);
                    jogadas.contarNaviosRestantes(jogador);
                    if (!pc.isBot()){
                        jogadas.raizPrintTabuleiro(pc,jogador);
                    }
                }
            }
            jogadas.raizPrintTabuleiro(jogador,pc);
            System.out.print("\nGostaria de recomeçar a partida? (S/N)");
            String recomecar = sc.next().toUpperCase(Locale.ROOT);
            if (recomecar.charAt(0) == 'N') partida = false;
        }

    }
}