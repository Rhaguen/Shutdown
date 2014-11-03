/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import Interface.AtorJogador;

/**
 *
 * @author eduardo
 */
public class Partida {
    private boolean partidaEmAndamento;
    private Jogador jogadorA;
    private Jogador jogadorB;
    private Jogador jogadorAtual;
    private Jogador jogadorTemp;
    private Tabuleiro tabuleiro;
    private AtorJogador atorJogador;
    private int[] posicoesIniciais;
    private int[] posicoesFinais;
    private boolean conectado;
    Movimento movimento;

    public Partida(AtorJogador atorJogador) {
        this.atorJogador = atorJogador;
        tabuleiro = new Tabuleiro();
        
        posicoesIniciais = new int[2];
        posicoesFinais = new int[2];
    }

    public boolean click(int x, int y) {
        if (jogadorTemp == jogadorAtual) {
            Posicao posicao = tabuleiro.recuperarPosicao(x, y);
            if (posicao.estaOcupada() && posicao.informaRobo().getJogador() == jogadorAtual) {
                // testar se a peça é do jogador
                // veriricar se tem peca selecionada, para calcular distancia de movimento                
                posicoesIniciais[0] = x;
                posicoesIniciais[1] = y;    
                clickRobo(posicao.informaRobo());

            } else {
                Robo robo = jogadorAtual.informaRoboSelecionado();
                if (robo != null) {
                    posicoesFinais[0] = x;
                    posicoesFinais[1] = y;
                    System.out.println("Destino peça selecionado, iniciando calculo de distancia");
                    verificarJogada(posicoesIniciais, posicoesFinais);// USE CASE
                    clickRobo(robo);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void clickRobo(Robo robo) {
        boolean selecionado = !robo.estaSelecionado();
        robo.setarSelecionado(selecionado);
        atorJogador.mudaEstadoSelecaoPeca(selecionado);
        
//        if (robo.estaSelecionado()) {
//            robo.setarSelecionado(false);
//            //pecaSelecionada = false;
//            atorJogador.mudaEstadoSelecaoPeca(false);
//
//        } else {
//            robo.setarSelecionado(true);
//            //pecaSelecionada = true;
//            atorJogador.mudaEstadoSelecaoPeca(true);
//        }
        System.out.println("Posição Ocupada--Peca Selecionada: " + robo.estaSelecionado());
    }

    public void passarTurno() {
        if(jogadorAtual == jogadorA){
            System.out.println("Jogador da vez1 "+jogadorAtual.getNome()+" jogador que vai assumir a vez "+jogadorB.getNome());
            jogadorAtual = jogadorB;
        }
        else{
            System.out.println("Jogador da vez2 "+jogadorAtual.getNome()+" jogador que vai assumir a vez "+jogadorB.getNome());
            jogadorAtual = jogadorA;
        }
        if(jogadorAtual.getNome().equals(jogadorTemp.getNome())){
            jogadorTemp = jogadorAtual;
            rolarDados();
            movimento = new Movimento(tabuleiro, jogadorA, jogadorB, jogadorAtual);
            System.out.println("Rolou dados na passada de turno");
            atorJogador.atualizarInterface();
        }
    }
    

    public void encerrarPartida() {
        throw new UnsupportedOperationException();
    }

    public boolean iniciarPartida() {
        if (partidaEmAndamento) {
            atorJogador.informarPartidaEmAndamento();
            return false;
        }
        if (jogadorA == null || jogadorB == null) {
            atorJogador.informarFaltaDeJogadores();
            return false;
        }
        jogadorA.setId(1);
        jogadorB.setId(2);
        tabuleiro.preparaTabuleiro(jogadorA.getPecas(), jogadorB.getPecas());        
        rolarDados();
        atorJogador.atualizarInterface();
        movimento = new Movimento(tabuleiro, jogadorA, jogadorB, jogadorAtual);
        verificarVencedor(jogadorAtual);
        atorJogador.enviarJogada(movimento);
        return true;
    }

    public void sortearInicio() {
        throw new UnsupportedOperationException();
    }

    public void rolarDados() {
        int[] resultado = jogadorAtual.rolarDados();
        atorJogador.mostrarAnimacaoDado(resultado);
    }

    public boolean verificarJogada(int[] posInicial, int[] posFinal) {
        Robo peca = tabuleiro.getPeca(posInicial);
        int distancia = tabuleiro.calculaDistancia(posInicial, posFinal);
        System.out.println("Distancia: " + distancia);
        int mov = peca.getMovimento();
        if (mov > 0 && distancia == 1) {
            boolean ocupada = tabuleiro.verificaPosicaoOcupada(posFinal);
            if (ocupada) {
                Robo pecaFinal = tabuleiro.getPeca(posFinal);
                Jogador dono = pecaFinal.getJogador();
                if (dono != jogadorAtual) {
                    Posicao costasDefensor = pecaFinal.getPosicaoCostas();
                    if (tabuleiro.getPosicao(posInicial) == costasDefensor) {
                        tabuleiro.removePeca(posFinal);
                        dono.removerPeca(pecaFinal);
                    } else {
                        atorJogador.informaPosicaoOcupada();
                        return false;
                    }
                } else {
                    atorJogador.informaPosicaoOcupada();
                    return false;
                }
            }

            System.out.println("Jogada Valida");
            peca.setPosicaoCostas(tabuleiro.getPosicao(posInicial));
            peca.diminuiMovimento();
            tabuleiro.movePeca(peca, posFinal);
            tabuleiro.removePeca(posInicial);
            int direcao = tabuleiro.calcularDirecao(posInicial, posFinal);
            peca.setDirecao(direcao);

            atorJogador.atualizarInterface();
            atorJogador.enviarJogada(movimento);
            verificarVencedor(jogadorAtual);            
            if(jogadorAtual.pecasAMovimentar() < 1){
                movimento.setPassarTurno(true);
                atorJogador.enviarJogada(movimento);
                passarTurno();
            }
            
            return true;

        } else {
            atorJogador.informaFaltaPontosMovimento();
        }
        System.out.println("Jogada Invalida");
        return false;
    }

    public void verificarVencedor(Jogador jogador) {
        if(jogador == jogadorA){
            if(jogadorB.getPecasAtivas() <=0){
                atorJogador.informaErro("Jogador "+jogadorB.getNome()+" não tem mais peças.", "Jogador Sem Peças");
                atorJogador.informaErro("Jogador "+jogadorA.getNome()+" Vencedor. Parabéns.", "Vencedor!!!!.");
            }
        }
        if(jogador == jogadorB){
            if(jogadorA.getPecasAtivas() <=0){
                atorJogador.informaErro("Jogador "+jogadorA.getNome()+" não tem mais peças.", "Jogador Sem Peças");
                atorJogador.informaErro("Jogador "+jogadorB.getNome()+" Vencedor. Parabéns.", "Vencedor!!!!.");
            }
        }
    }

    public void preparaTeste() {
        jogadorA = new Jogador("teste1");
        jogadorB = new Jogador("teste2");
        jogadorAtual = jogadorB;
        jogadorTemp = jogadorB;
        jogadorB.rolarDados();

        //partidaEmAndamento = true;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Jogador getJogador1() {
        return jogadorA;
    }

    public Jogador getJogador2() {
        return jogadorB;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogadorA = jogador1;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogadorB = jogador2;
    }

    public Jogador getJogadorLocal() {
        return jogadorTemp;
    }

    public void setJogadorLocal(Jogador jogadorLocal) {
        this.jogadorTemp = jogadorLocal;
    }

    public Jogador getJogadorDaVez() {
        return jogadorAtual;
    }

    public void setJogadorDaVez(Jogador jogadorDaVez) {
        this.jogadorAtual = jogadorDaVez;
    }
    
    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
}
