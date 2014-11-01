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
    private Partida partida;
        private TelaPrincipal telaPrincipal;
        private AtorNetGames netGames;
        
        public AtorJogador(){
           this.partida = new Partida(this);
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
           if(partida.getJogador1().getPecas()[0].isAtiva()){
               telaPrincipal.getPontosMovimentoJog1_1().setText(partida.getJogador1().getPecas()[0].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog1_1().setText("X");
           }
           if(partida.getJogador1().getPecas()[1].isAtiva()){
               telaPrincipal.getPontosMovimentoJog1_2().setText(partida.getJogador1().getPecas()[1].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog1_2().setText("X");
           }
           if(partida.getJogador2().getPecas()[0].isAtiva()){
               telaPrincipal.getPontosMovimentoJog2_1().setText(partida.getJogador2().getPecas()[0].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog2_1().setText("X");
           }
           if(partida.getJogador2().getPecas()[1].isAtiva()){
               telaPrincipal.getPontosMovimentoJog2_2().setText(partida.getJogador2().getPecas()[1].getMovimento()+"");
           } else{
               telaPrincipal.getPontosMovimentoJog2_2().setText("X");
           }
           
          
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    posicao = partida.getTabuleiro().recuperarPosicao(j, i);
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
            partida.click(x, y);
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
        partida.setJogador1(movimento.getJogador1());
        partida.setJogador2(movimento.getJogador2());
        partida.setTabuleiro(movimento.getTabuleiro());
        partida.setJogadorDaVez(movimento.getJogadorDaVez());
        System.out.println("jogador 1 "+partida.getJogador1().getNome()+" jogador 2 "+partida.getJogador2().getNome()+" Jogador da vez "+partida.getJogadorDaVez().getNome()+" Jogador Local "+ partida.getJogadorLocal().getNome());
        if(movimento.isPassarTurno()){
            System.out.println("Movimento eh passar turno: "+movimento.isPassarTurno());
            movimento.setPassarTurno(false);
            System.out.println("tovivio");
            
            System.out.println("ainda tovivio");
            partida.passarTurno();
        }
        this.atualizarInterface();
        partida.verificarVencedor(partida.getJogadorDaVez());
    }

    public void iniciarPartida(Integer posicao) {
        telaPrincipal.mostrarTelaJogo();
        String nomeAdversario = netGames.informarNomeAdversario(partida.getJogadorLocal().getNome());
        if(posicao == 1){
            partida.setJogador1(partida.getJogadorLocal());            
            partida.setJogador2(new Jogador(nomeAdversario));
            partida.getJogadorLocal().setNaVez(true);
            partida.setJogadorDaVez(partida.getJogadorLocal());
            telaPrincipal.getNomeJogador1().setText(partida.getJogadorLocal().getNome());
            telaPrincipal.getNomeJogador2().setText(nomeAdversario);
        }
        if(posicao == 2){
            partida.setJogador1(new Jogador(nomeAdversario));
            partida.setJogador2(partida.getJogadorLocal());
            telaPrincipal.getNomeJogador1().setText(nomeAdversario);
            telaPrincipal.getNomeJogador2().setText(partida.getJogadorLocal().getNome());
        }
        partida.iniciarPartida();             
    }
    
    public void conectar(){
        if(partida.isConectado()){
            telaPrincipal.informaErro("Conexão já estabelecida", "Erro Conexão");
        }
        else{
            String nomeJogador = telaPrincipal.solicitaDados("Informe seu Nome:");
            String servidor = telaPrincipal.solicitaDados("Informe qual Servidor para conexão:\nPadrão: venus.inf.ufsc.br");
            if(servidor.equals("")){
                servidor = "venus.inf.ufsc.br";
            }
            if(netGames.conectar(servidor, nomeJogador)){
                partida.setConectado(true);
                partida.setJogadorLocal(new Jogador(nomeJogador));
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
