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
public class Mensagem implements Jogada {

    private int tipoMensagem;
    private Object mensagem;

    /**
     * @return the tipoMensagem
     */
    public int getTipoMensagem() {
        return tipoMensagem;
    }

    /**
     * @param tipoMensagem the tipoMensagem to set
     */
    public void setTipoMensagem(int tipoMensagem) {
        this.tipoMensagem = tipoMensagem;
    }

    /**
     * @return the mensagem
     */
    public Object getMensagem() {
        return mensagem;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(Object mensagem) {
        this.mensagem = mensagem;
    }

    public Mensagem(int tipoMensagem, Object mensagem) {
        this.tipoMensagem = tipoMensagem;
        this.mensagem = mensagem;
    }
}
