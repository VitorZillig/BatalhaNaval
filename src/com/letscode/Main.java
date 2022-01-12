package com.letscode;

import com.letscode.batalhaNaval.Jogadas;
import com.letscode.batalhaNaval.Jogador;
import com.letscode.batalhaNaval.Menu;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        boolean partida = true;
        while(partida){
            menu.menuOptions();
            Jogadas jogadas = new Jogadas(menu.getMatrizTabuleiro().length);
            Jogador jogador = new Jogador(menu.getMatrizTabuleiro(),menu.getQteNavios(), menu.getPlayersName()[0],menu.getPlayersStatus()[0]);
            Jogador pc = new Jogador(menu.getMatrizTabuleiro(), menu.getQteNavios(), menu.getPlayersName()[1], menu.getPlayersStatus()[1]);
            jogadas.inserirNaviosTabuleiro(jogador);
            jogadas.inserirNaviosTabuleiro(pc);
            jogadas.printRaizTabuleiro(jogador,pc);

            while(jogador.getNaviosRestantes() != 0 && pc.getNaviosRestantes() != 0){
                if (jogadas.getTurno()){
                    jogadas.realizarAtaque(jogador, pc);
                    if (!jogador.getIsBot()) {
                        jogadas.printRaizTabuleiro(jogador, pc);
                    }
                } else {
                    jogadas.realizarAtaque(pc, jogador);
                    jogadas.contarNaviosRestantes(jogador);
                    if (!pc.getIsBot()){
                        jogadas.printRaizTabuleiro(pc,jogador);
                    }
                }
            }
            jogadas.printRaizTabuleiro(jogador,pc);
            System.out.print("\nGostaria de recomeçar a partida? (S/N)");
            String recomecar = sc.next().toUpperCase(Locale.ROOT);
            if (recomecar.charAt(0) == 'N') partida = false;
        }

    }
}