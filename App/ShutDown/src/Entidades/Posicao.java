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
public class Posicao implements Jogada {

    private Robo peca;
    private boolean ocupada;

    public Robo informaRobo() {
        return this.peca;
    }

    public boolean estaOcupada() {
        return this.ocupada;
    }

    public void removeRobo() {
        peca = null;
        ocupada = false;
    }

    public void atribuiRobo(Robo peca) {
        this.peca = peca;
        ocupada = true;
    }
}
