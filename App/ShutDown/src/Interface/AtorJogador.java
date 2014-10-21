/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Entidades.*;
import Rede.AtorNetGames;

/**
 *
 * @author eduardo
 */
public class AtorJogador {
    private Controle controle;
        private TelaPrincipal telaPrincipal;
        private AtorNetGames netGames;
        
        public AtorJogador(){
           this.controle = new Controle(this);
           netGames = new AtorNetGames(this);
           telaPrincipal = new TelaPrincipal(this);
           telaPrincipal.setVisible(true);
        }

	public void passarTurno() {
		throw new UnsupportedOperationException();
	}

	public void mostrarTelaInicial() {
		throw new UnsupportedOperationException();
	}

	public boolean confirmarTerminoPartida() {
		throw new UnsupportedOperationException();
	}
        
        public void atualizarInterface(){
           Posicao posicao;
           if(controle.getJogador1().getPecas()[0].isAtiva()){
               telaPrincipal.getPontosMovimentoJog1_1().setText(controle.getJogador1().getPecas()[0].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog1_1().setText("X");
           }
           if(controle.getJogador1().getPecas()[1].isAtiva()){
               telaPrincipal.getPontosMovimentoJog1_2().setText(controle.getJogador1().getPecas()[1].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog1_2().setText("X");
           }
           if(controle.getJogador2().getPecas()[0].isAtiva()){
               telaPrincipal.getPontosMovimentoJog2_1().setText(controle.getJogador2().getPecas()[0].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog2_1().setText("X");
           }
           if(controle.getJogador2().getPecas()[1].isAtiva()){
               telaPrincipal.getPontosMovimentoJog2_2().setText(controle.getJogador2().getPecas()[1].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog2_2().setText("X");
           }
           
          
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    posicao = controle.getTabuleiro().getPosicao(j, i);
                    if (posicao.isOcupada()) {
                        if (posicao.getPeca().getJogador().getId() == 1) {
                            telaPrincipal.getMapaPosicoes()[i][j].setIcon(telaPrincipal.getSeta1()[posicao.getPeca().getDirecao()]);
                        } else {
                            telaPrincipal.getMapaPosicoes()[i][j].setIcon(telaPrincipal.getSeta2()[posicao.getPeca().getDirecao()]);
                        }
                    }else{
                        telaPrincipal.getMapaPosicoes()[i][j].setIcon(telaPrincipal.getPiso());
                    }
                }
            }
        }
        
        public void click(int y, int x){
            System.out.println("X "+x+"   Y "+y);
            controle.click(x, y);
        }

	public boolean confirmarSaidaJogo() {
		throw new UnsupportedOperationException();
	}

	public void iniciarPartida() {
		throw new UnsupportedOperationException();
	}

	public void informarPartidaEmAndamento() {
		telaPrincipal.informaPartidaEmAndamento();
	}

	public void informarFaltaDeJogadores() {
		telaPrincipal.informaFaltaDeJogadores();
	}
        
	public void defineNomeJogador(String nome) {
		throw new UnsupportedOperationException();
	}

	public void informarQuemInicia() {
		throw new UnsupportedOperationException();
	}

	public void mostrarTelaJogo() {
		throw new UnsupportedOperationException();
	}

	public void informaPecaDeOutroJogador() {
		throw new UnsupportedOperationException();
	}

	public void informaPecaJaMovimentou() {
		throw new UnsupportedOperationException();
	}

	public void mudaEstadoSelecaoPeca(boolean selecionado) {
		telaPrincipal.mudaCorSelecao(selecionado);
	}

	public void mostrarAnimacaoDado(int [] num) {
		telaPrincipal.mostrarAnimacaoDado(num);
	}

	public void mostrarNumeroMovimentoPeca(int[] num) {
		throw new UnsupportedOperationException();
	}

	public void informaPosicaoOcupada() {
            telaPrincipal.informaErro("A posição já está ocupada.", "Erro ao movimentar");
	}

	public void informaFaltaPontosMovimento() {
            telaPrincipal.informaErro("Não há pontos de movimento suficiente. Movimente uma casa por vez.", "Não pode movimentar.");
	}
        
        public void informaErro(String msg, String titulo){
            telaPrincipal.informaErro(msg,titulo);
        }

	public void informaJogadorSemPecas(Jogador jogador) {
		throw new UnsupportedOperationException();
	}

	public void informaVencedor(Jogador jogador) {
		throw new UnsupportedOperationException();
	}
        
        public void atualizarTabuleiro(Tabuleiro tabuleiro) {
           
        } 

    public void informaJogadorNaoDaVez() {
        telaPrincipal.informaErro("Ainda não é seu turno. Aguarde sua vez de jogar.", "Não é sua vez.");
    }

    public void receberJogada(Movimento movimento) {
        controle.setJogador1(movimento.getJogador1());
        controle.setJogador2(movimento.getJogador2());
        controle.setTabuleiro(movimento.getTabuleiro());
        controle.setJogadorDaVez(movimento.getJogadorDaVez());
        System.out.println("jogador 1 "+controle.getJogador1().getNome()+" jogador 2 "+controle.getJogador2().getNome()+" Jogador da vez "+controle.getJogadorDaVez().getNome()+" Jogador Local "+ controle.getJogadorLocal().getNome());
        if(movimento.isPassarTurno()){
            System.out.println("Movimento eh passar turno: "+movimento.isPassarTurno());
            movimento.setPassarTurno(false);
            System.out.println("tovivio");
            
            System.out.println("ainda tovivio");
            controle.passarTurno();
        }
        this.atualizarInterface();
        controle.verificarVencedor(controle.getJogadorDaVez());
    }

    public void iniciarPartida(Integer posicao) {
        telaPrincipal.mostrarTelaJogo();
        String nomeAdversario = netGames.informarNomeAdversario(controle.getJogadorLocal().getNome());
        if(posicao == 1){
            controle.setJogador1(controle.getJogadorLocal());            
            controle.setJogador2(new Jogador(nomeAdversario));
            controle.getJogadorLocal().setNaVez(true);
            controle.setJogadorDaVez(controle.getJogadorLocal());
            telaPrincipal.getNomeJogador1().setText(controle.getJogadorLocal().getNome());
            telaPrincipal.getNomeJogador2().setText(nomeAdversario);
        }
        if(posicao == 2){
            controle.setJogador1(new Jogador(nomeAdversario));
            controle.setJogador2(controle.getJogadorLocal());
            telaPrincipal.getNomeJogador1().setText(nomeAdversario);
            telaPrincipal.getNomeJogador2().setText(controle.getJogadorLocal().getNome());
        }
        controle.iniciarPartida();             
    }
    
    public void conectar(){
        if(controle.isConectado()){
            telaPrincipal.informaErro("Conexão já estabelecida", "Erro Conexão");
        }
        else{
            String nomeJogador = telaPrincipal.solicitaDados("Informe seu Nome:");
            String servidor = telaPrincipal.solicitaDados("Informe qual Servidor para conexão:\nPadrão: venus.inf.ufsc.br");
            if(servidor.equals("")){
                servidor = "venus.inf.ufsc.br";
            }
            if(netGames.conectar(servidor, nomeJogador)){
                controle.setConectado(true);
                controle.setJogadorLocal(new Jogador(nomeJogador));
                netGames.iniciarPartida();
            }
            else{
                telaPrincipal.informaErro("Não foi possivel conectar no servidor: "+servidor, "Erro de conexão");
            }
        }      
    }

    public void enviarJogada(Movimento movimento) {
        netGames.enviarJogada(movimento);
    }
}
