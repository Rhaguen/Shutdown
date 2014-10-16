/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Rede;
import javax.swing.JOptionPane;
import Interface.AtorJogador;
import Entidades.Movimento;
import br.ufsc.inf.leobr.cliente.*;
import br.ufsc.inf.leobr.cliente.exception.*;
/**
 *
 * @author eduardo
 */
public class AtorNetGames implements OuvidorProxy{
    protected AtorJogador atorJogador;
	protected Proxy proxy;
	
	public AtorNetGames (AtorJogador atorJogador){
		super();
		this.atorJogador = atorJogador;
		this.proxy = Proxy.getInstance();
		proxy.addOuvinte(this);	
	}

	public boolean conectar(String servidor, String nome) {
		try {
			proxy.conectar(servidor, nome);
			return true;
		} catch (JahConectadoException e) {
			atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
			e.printStackTrace();
			return false;
		} catch (NaoPossivelConectarException e) {
			atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
			e.printStackTrace();
			return false;
		} catch (ArquivoMultiplayerException e) {
			atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
			e.printStackTrace();
			return false;
		}
	}

	public boolean desconectar() {
		try {
			proxy.desconectar();
			return true;
		} catch (NaoConectadoException e) {
			atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
			e.printStackTrace();
			return false;
		}
	}

	public void iniciarPartida() {
		try {
			proxy.iniciarPartida(new Integer(2));
                        System.out.println("Chamou Iniciar Partida");
		} catch (NaoConectadoException e) {
			atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
			e.printStackTrace();
		}
	}

	public void enviarJogada(Movimento movimento) {
		try {
			proxy.enviaJogada(movimento);
		} catch (NaoJogandoException e) {
			atorJogador.informaErro(e.getMessage(), "Erro de Conexão");
			e.printStackTrace();
		}
	}

	public String informarNomeAdversario(String idUsuario) {
		String aux1 = proxy.obterNomeAdversario(new Integer(1));
		String aux2 = proxy.obterNomeAdversario(new Integer(2));;
		if (aux1.equals(idUsuario)){
			return aux2;
		} else {
			return aux1;
		}		
}

	public void receberJogada(Jogada jogada) {
		Movimento movimento = (Movimento) jogada;
		atorJogador.receberJogada(movimento);
	}

	public void finalizarPartidaComErro(String message) {
		// TODO Auto-generated method stub
		
	}

	public void receberMensagem(String msg) {
		// TODO Auto-generated method stub
		
	}

	public void tratarConexaoPerdida() {
		// TODO Auto-generated method stub
		
	}

	public void tratarPartidaNaoIniciada(String message) {
		// TODO Auto-generated method stub
		
	}

	public void iniciarNovaPartida(Integer posicao) {
            System.out.println("Recebeu solicitação de inicio "+posicao);
            atorJogador.iniciarPartida(posicao);
                
	}
}
