package com.letscode.batalhaNaval;

import com.letscode.Tabuleiro;

import java.util.Random;

public class Jogador {
    private String nome;
    private Tabuleiro tabuleiro;
    private int qtdeMaximaDeNavios;

    public Jogador(Tabuleiro tabuleiro, int qtdeMaximaDeNavios, String nome) {
        this.nome = nome;
        this.tabuleiro = tabuleiro;
        this.qtdeMaximaDeNavios = qtdeMaximaDeNavios;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public int getQtdeMaximaDeNavios() {
        return qtdeMaximaDeNavios;
    }

    public void setQtdeMaximaDeNavios(int qtdeMaximaDeNavios) {
        this.qtdeMaximaDeNavios = qtdeMaximaDeNavios;
    }

}
