/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rede;

import Interface.AtorJogador;
import Entidades.Movimento;
import Entidades.Mensagem;
import br.ufsc.inf.leobr.cliente.*;
import br.ufsc.inf.leobr.cliente.exception.*;

/**
 *
 * @author fernando
 */
public class AtorNetGames implements OuvidorProxy {

    protected AtorJogador atorJogador;
    protected Proxy proxy;
    private boolean conectado;

    /**
     * @return the conectado
     */
    public boolean isConectado() {
        return conectado;
    }

    /**
     * @param conectado the conectado to set
     */
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public AtorNetGames(AtorJogador atorJogador) {
        super();
        this.atorJogador = atorJogador;
        this.conectado = false;
        this.proxy = Proxy.getInstance();
        this.proxy.addOuvinte(this);
    }

    public boolean conectar(String servidor, String nome){
        try {
            this.proxy.conectar(servidor, nome);
            this.conectado = true;
            return true;
        } catch (NaoPossivelConectarException e) {
            this.atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
            e.printStackTrace();
            return false;
        } catch (ArquivoMultiplayerException e) {
            this.atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
            e.printStackTrace();
            return false;
        } catch (JahConectadoException e) {
            this.atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
            e.printStackTrace();
            return false;
        }
    }

    public boolean desconectar() {
        try {
            this.proxy.desconectar();
            this.conectado = false;
            return true;
        } catch (NaoConectadoException e) {
            this.atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
            e.printStackTrace();
            return false;
        }
    }

    public void iniciarPartida() {
        try {
            this.proxy.iniciarPartida(new Integer(2));
        } catch (NaoConectadoException e) {
            this.atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
            e.printStackTrace();
        }
    }

    public void enviarJogada(Movimento movimento) {
        try {
            this.proxy.enviaJogada(movimento);
        } catch (NaoJogandoException e) {
            this.atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
            e.printStackTrace();
        }
    }

    public void enviarJogada(Mensagem mensagem) {
        try {
            this.proxy.enviaJogada(mensagem);
        } catch (NaoJogandoException e) {
            this.atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
            e.printStackTrace();
        }
    }

    public String informarNomeAdversario(String idUsuario) {
        String aux1 = this.proxy.obterNomeAdversario(new Integer(1));
        String aux2 = this.proxy.obterNomeAdversario(new Integer(2));
        if (aux1.equals(idUsuario)) {
            return aux2;
        } else {
            return aux1;
        }
    }

    @Override
    public void receberJogada(Jogada jogada) {
        if (jogada instanceof Movimento) {
            Movimento movimento = (Movimento) jogada;
            this.atorJogador.receberJogada(movimento);
        } else if (jogada instanceof Mensagem) {
            Mensagem mensagem = (Mensagem) jogada;
            this.atorJogador.receberMensagem(mensagem);
        }
    }

    @Override
    public void finalizarPartidaComErro(String message) {
        this.desconectar();
    }

    @Override
    public void receberMensagem(String msg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void tratarConexaoPerdida() {
        // TODO Auto-generated method stub
    }

    @Override
    public void tratarPartidaNaoIniciada(String message) {
        // TODO Auto-generated method stub
    }

    @Override
    public void iniciarNovaPartida(Integer posicao) {
        atorJogador.iniciarPartida(posicao);
    }
}
