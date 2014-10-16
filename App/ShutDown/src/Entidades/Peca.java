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
public class Peca implements Jogada{

    private int direcao;
    private boolean ativa;
    private Jogador jogador;
    private boolean movimentou;
    private boolean selecionada;
    private int movimento;
    private Posicao posicaoCostas;

    public Peca(Jogador jogador) {
        this.jogador = jogador;
        selecionada = false;
        ativa = true;
    }

    public boolean isMovimentou() {
        return this.movimentou;
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public void operation() {
        throw new UnsupportedOperationException();
    }

    public void mudaEstadoSelecao() {
        throw new UnsupportedOperationException();
    }

    public int rolarDado() {
        int rolada = (int) (Math.random() * 6) + 1;
        movimento = rolada;
        return rolada;
    }

    public int getMovimento() {
        return this.movimento;
    }

    public Posicao getPosicao() {
        throw new UnsupportedOperationException();
    }

    public Posicao getPosicaoCostas() {
        return this.posicaoCostas;
    }

    public int getDirecao() {
        return direcao;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }
    
    public void setPosicaoCostas(Posicao posicao) {
        this.posicaoCostas = posicao;
    }

    public void diminuiMovimento() {
        movimento--;
        if(movimento <= 0){
            movimentou = true;
        }
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
