/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

/**
 *
 * @author eduardo
 */
public class Tabuleiro implements Jogada {

    private Posicao[][] posicoes;

    public Tabuleiro() {
        this.posicoes = new Posicao[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                this.posicoes[i][j] = new Posicao();
            }
        }
    }

    public Robo getRobo(int[] pos) {
        return this.posicoes[pos[1]][pos[0]].informaRobo();
    }

    public int calculaDistancia(int[] posInicial, int[] posFinal) {
        int distanciaX = posInicial[0] - posFinal[0];
        int distanciaY = posInicial[1] - posFinal[1];
        return Math.abs(distanciaX + distanciaY);
    }

    public boolean verificaPosicaoOcupada(int[] pos) {
        return this.posicoes[pos[1]][pos[0]].estaOcupada();
    }

    public void removeRobo(int[] pos) {
        this.posicoes[pos[1]][ pos[0]].removeRobo();
    }

    public void moveRobo(Robo peca, int[] pos) {
        this.posicoes[pos[1]][ pos[0]].atribuiRobo(peca);
    }

    public Posicao recuperarPosicao(int x, int y) {
        return this.posicoes[y][x];
    }

    public Posicao getPosicao(int[] pos) {
        return posicoes[pos[1]][ pos[0]];
    }

    public void preparaTabuleiro(Robo[] pcJog1, Robo[] pcJog2) {
        this.posicoes[0][2].atribuiRobo(pcJog1[0]);
        this.posicoes[0][3].atribuiRobo(pcJog1[1]);
        this.posicoes[5][2].atribuiRobo(pcJog2[0]);
        this.posicoes[5][3].atribuiRobo(pcJog2[1]);
        pcJog1[0].setDirecao(2);
        pcJog1[1].setDirecao(2);
    }

    public int calcularDirecao(int[] posInicial, int[] posFinal) {
        int distanciaX = posInicial[0] - posFinal[0];
        int distanciaY = posInicial[1] - posFinal[1];

        if (distanciaX > 0) {
            return 3; // esquerda
        } else if (distanciaX < 0) {
            return 1; // direita
        }
        if (distanciaY > 0) {
            return 0; // cima
        } else if (distanciaY < 0) {
            return 2; // baixo
        }
        return -1;
    }
}
