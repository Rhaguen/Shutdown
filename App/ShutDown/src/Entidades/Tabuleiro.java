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
public class Tabuleiro implements Jogada{
    private Posicao[][] posicoes;

    public Tabuleiro() {
        posicoes = new Posicao[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                posicoes[i][j] = new Posicao();
            }
        }
    }

    public Robo getPeca(int[] pos) {
        return posicoes[pos[1]][pos[0]].getPeca();

    }

    public int calculaDistancia(int[] posInicial, int[] posFinal) {
        int distanciaX = posInicial[0] - posFinal[0];
        int distanciaY = posInicial[1] - posFinal[1];
        return Math.abs(distanciaX + distanciaY);


    }

    public boolean verificaPosicaoOcupada(int[] pos) {
        return posicoes[pos[1]][pos[0]].isOcupada();
    }

    public Robo getPeca(int pos) {
        throw new UnsupportedOperationException();
    }

    public void removePeca(int[] pos) {
        posicoes[pos[1]][ pos[0]].removePeca();
    }

    public void movePeca(Robo peca, int[] pos) {
        posicoes[pos[1]][ pos[0]].setPeca(peca);
    }

    public Posicao recuperarPosicao(int x, int y) {
        return posicoes[y][x];
    }

    public Posicao getPosicao(int[] pos) {
        return posicoes[pos[1]][ pos[0]];
    }

    public void preparaTabuleiro(Robo[] pcJog1, Robo[] pcJog2) {
        posicoes[0][2].setPeca(pcJog1[0]);
        posicoes[0][3].setPeca(pcJog1[1]);
        posicoes[5][2].setPeca(pcJog2[0]);
        posicoes[5][3].setPeca(pcJog2[1]);
        pcJog1[0].setDirecao(2);
        pcJog1[1].setDirecao(2);
    }

    int calcularDirecao(int[] posInicial, int[] posFinal) {
        int distanciaX = posInicial[0] - posFinal[0];
        int distanciaY = posInicial[1] - posFinal[1];

        if (distanciaX > 0) {
            return 3; // esquerda
        } else if (distanciaX < 0) {
            return 1; // direita
        }
        if(distanciaY > 0){
            return 0; // cima
        } else if (distanciaY < 0){
            return 2; // baixo
        }
        return -1;
    }
}
