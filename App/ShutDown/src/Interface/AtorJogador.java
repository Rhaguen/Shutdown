/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Entidades.*;
import Rede.AtorNetGames;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando
 */
public class AtorJogador {

    private Partida partida;
    private TelaPrincipal telaPrincipal;
    private AtorNetGames netGames;

    public AtorJogador() {
        this.partida = new Partida(this);
        this.netGames = new AtorNetGames(this);
        this.telaPrincipal = new TelaPrincipal(this);
        this.telaPrincipal.setVisible(true);
    }
    
    public void iniciar(){
        // Conecta ao servidor de jogos
        this.conectar();
        
        // Dispara solicitação de início de partida
        this.netGames.iniciarPartida();
        
        // Desabilita o botão de iniciar partida
        this.telaPrincipal.habilitarIniciar(false);
    }
    
    public void conectar() {
        if (!this.netGames.isConectado()) {
            String nomeJogador = telaPrincipal.solicitaDados("Informe seu Nome:");
            String servidor = telaPrincipal.solicitaDados("Informe qual Servidor para conexão:\nPadrão: venus.inf.ufsc.br");
            if (servidor.equals("")) {
                servidor = "venus.inf.ufsc.br";
            }
            
            if (netGames.conectar(servidor, nomeJogador)){ 
                this.partida.setJogadorLocal(new Jogador(nomeJogador));
            }
            else{
                telaPrincipal.informaErro("Não foi possivel conectar no servidor: " + servidor, "Erro de conexão");
            }
        }
    }
    
    public void iniciarPartida(Integer posicao) {
        this.telaPrincipal.mostrarTelaJogo(true);
        
        String nomeAdversario = netGames.informarNomeAdversario(partida.getJogadorLocal().getNome());
        
        if (posicao == 1) {
            this.partida.setJogador1(partida.getJogadorLocal());
            this.partida.setJogador2(new Jogador(nomeAdversario));
            this.partida.getJogadorLocal().setNaVez(true);
            this.partida.setJogadorDaVez(partida.getJogadorLocal());
            this.telaPrincipal.setNomeJogador1(partida.getJogadorLocal().getNome());
            this.telaPrincipal.setNomeJogador2(nomeAdversario);
        }
        else if (posicao == 2) {
            this.partida.setJogador1(new Jogador(nomeAdversario));
            this.partida.setJogador2(partida.getJogadorLocal());
            this.telaPrincipal.setNomeJogador1(nomeAdversario);
            this.telaPrincipal.setNomeJogador2(partida.getJogadorLocal().getNome());
        }
        
        this.partida.iniciarPartida();
    }
    
    public void atualizarInterface() {
        Posicao posicao;
        if (partida.getJogador1().getRobos()[0].isAtiva()) {
            telaPrincipal.getPontosMovimentoJog1_1().setText(partida.getJogador1().getRobos()[0].getMovimento() + "");
        } else {
            telaPrincipal.getPontosMovimentoJog1_1().setText("X");
        }
        if (partida.getJogador1().getRobos()[1].isAtiva()) {
            telaPrincipal.getPontosMovimentoJog1_2().setText(partida.getJogador1().getRobos()[1].getMovimento() + "");
        } else {
            telaPrincipal.getPontosMovimentoJog1_2().setText("X");
        }
        if (partida.getJogador2().getRobos()[0].isAtiva()) {
            telaPrincipal.getPontosMovimentoJog2_1().setText(partida.getJogador2().getRobos()[0].getMovimento() + "");
        } else {
            telaPrincipal.getPontosMovimentoJog2_1().setText("X");
        }
        if (partida.getJogador2().getRobos()[1].isAtiva()) {
            telaPrincipal.getPontosMovimentoJog2_2().setText(partida.getJogador2().getRobos()[1].getMovimento() + "");
        } else {
            telaPrincipal.getPontosMovimentoJog2_2().setText("X");
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                posicao = partida.getTabuleiro().recuperarPosicao(j, i);
                if (posicao.estaOcupada()) {
                    if (posicao.informaRobo().getJogador().getId() == 1) {
                        telaPrincipal.getMapaPosicoes()[i][j].setIcon(telaPrincipal.getSeta1()[posicao.informaRobo().getDirecao()]);
                    } else {
                        telaPrincipal.getMapaPosicoes()[i][j].setIcon(telaPrincipal.getSeta2()[posicao.informaRobo().getDirecao()]);
                    }
                } else {
                    telaPrincipal.getMapaPosicoes()[i][j].setIcon(telaPrincipal.getPiso());
                }
            }
        }
    }

    public void click(int y, int x) {
        this.partida.click(x, y);
        if (!this.partida.isJogadorDaVez()) {
            this.informaNaoDaVez();
        }
    }

    public void informarPartidaEmAndamento() {
        telaPrincipal.informaPartidaEmAndamento();
    }

    public void informarFaltaDeJogadores() {
        telaPrincipal.informaFaltaDeJogadores();
    }

    public void mudaEstadoSelecaoPeca(boolean selecionado) {
        telaPrincipal.mudaCorSelecao(selecionado);
    }

    public void mostrarAnimacaoDado(int[] num) {
        telaPrincipal.mostrarAnimacaoDado(num);
    }

    public void informaPosicaoOcupada() {
        telaPrincipal.informaErro("A posição já está ocupada.", "Não pode movimentar");
    }

    public void informaFaltaPontosMovimento() {
        telaPrincipal.informaErro("Não há pontos de movimento suficiente. Movimente uma casa por vez.", "Não pode movimentar.");
    }

    public void informaDistanciaInadequada() {
        telaPrincipal.informaErro("Só é possivel mover a peça uma posição por vez.", "Não pode movimentar");
    }

    public void informaPartidaJahEncerrada() {
        telaPrincipal.informaErro("A partida já foi encerrada.", "Não pode movimentar");
    }

    public void informaErro(String msg, String titulo) {
        telaPrincipal.informaErro(msg, titulo);
    }

    public void informaNaoDaVez() {
        telaPrincipal.informaErro("Ainda não é seu turno. Aguarde sua vez de jogar.", "Não é sua vez.");
    }

    public void receberJogada(Movimento movimento) {
        // Atualiza o jogo de acordo com os dados recebidos no movimento
        partida.setJogador1(movimento.getJogador1());
        partida.setJogador2(movimento.getJogador2());
        partida.setTabuleiro(movimento.getTabuleiro());
        partida.setJogadorDaVez(movimento.getJogadorDaVez());
        
        // Atualiza a interface
        this.atualizarInterface();
        
        // Verifica se houve vencedor
        this.partida.tratarVencedor();
    }

    public void receberMensagem(Mensagem mensagem) {
        switch (mensagem.getTipoMensagem()) {
            // Passar Turno
            case 1:
                this.partida.passarTurno();
                this.atualizarInterface();
                break;
                
            // Desistencia do adversário
            case 2:
                desistir(true);
                break;
        }
    }

    public void desistir(boolean adversarioDesistiu) {
        // Verifica quem desistiu
        if (adversarioDesistiu) {
            telaPrincipal.informaErro("Seu adversário Desistiu!! Parabéns, você venceu!!", "Vencedor!!");
        } else {
            // O jogador que desiste envia mensagem ao outro jogador informando a desistencia
            netGames.enviarJogada(new Mensagem(2, "desistir"));
            
            telaPrincipal.informaErro("Você Desistiu. Seu Kitter!!.", "Kitter!!");
        }
        
        this.finalizarPartida();
    }
    
    public void finalizarPartida() {
        // Desconecta do servidor de jogo
        this.netGames.desconectar();
        
        // Reinicia os dados de partida e interface
        zerarJogo();
        zerarInterface();
    }

    public void zerarJogo() {
        this.partida = new Partida(this);
    }

    public void zerarInterface() {
        telaPrincipal.mostrarTelaJogo(false);
    }

    public void enviarJogada(Movimento movimento) {
        netGames.enviarJogada(movimento);
    }
    
    public void enviarMensagem(Mensagem mensagem) {
        netGames.enviarJogada(mensagem);
    }
    
}
