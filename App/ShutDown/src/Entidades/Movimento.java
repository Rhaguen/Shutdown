/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import br.ufsc.inf.leobr.cliente.Jogada;

/**
 *
 * @author fernando
 */
public class Movimento implements Jogada {

    protected Tabuleiro tabuleiro;
    protected Jogador jogador1;
    protected Jogador jogador2;
    protected Jogador jogadorDaVez;
    protected boolean passarTurno;

    public Movimento(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2, Jogador jogadorDaVez) {
        this.tabuleiro = tabuleiro;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.jogadorDaVez = jogadorDaVez;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public Jogador getJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez(Jogador jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean isPassarTurno() {
        return passarTurno;
    }

    public void setPassarTurno(boolean passarTurno) {
        this.passarTurno = passarTurno;
    }
}
