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
public class Controle {
    private int estadoJogo;
    private boolean partidaEmAndamento;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorDaVez;
    private Jogador jogadorLocal;
    private Jogador vencedor;
    private Tabuleiro tabuleiro;
    private AtorJogador atorJogador;
    private int[] posInicial;
    private int[] posFinal;
    //private boolean pecaSelecionada;
    private boolean conectado;
    Movimento movimento;

    public Controle(AtorJogador atorJogador) {
        this.atorJogador = atorJogador;
        tabuleiro = new Tabuleiro();
        
        posInicial = new int[2];
        posFinal = new int[2];
    }

    public void click(int x, int y) {
        if (jogadorLocal == jogadorDaVez) {
            Posicao posicao = tabuleiro.getPosicao(x, y);
            if (posicao.isOcupada() && posicao.getPeca().getJogador() == jogadorDaVez) {
                // testar se a peça é do jogador
                // veriricar se tem peca selecionada, para calcular distancia de movimento                
                posInicial[0] = x;
                posInicial[1] = y;
                clickNaPeca(posicao.getPeca());

            } else {
                Peca peca = jogadorDaVez.getPecaSelecionada();
                if (peca != null) {
                    posFinal[0] = x;
                    posFinal[1] = y;
                    System.out.println("Destino peça selecionado, iniciando calculo de distancia");
                    verificarJogada(posInicial, posFinal);// USE CASE
                    clickNaPeca(peca);
                }
            }
        } else {
            atorJogador.informaJogadorNaoDaVez();
        }
    }

    public void clickNaPeca(Peca peca) {
        if (peca.isSelecionada()) {
            peca.setSelecionada(false);
            //pecaSelecionada = false;
            atorJogador.mudaEstadoSelecaoPeca(false);

        } else {
            peca.setSelecionada(true);
            //pecaSelecionada = true;
            atorJogador.mudaEstadoSelecaoPeca(true);
        }
        System.out.println("Posição Ocupada--Peca Selecionada: " + peca.isSelecionada());
    }

    public void efetuarJogada() {
        throw new UnsupportedOperationException();
    }

    public void passarTurno() {
        if(jogadorDaVez == jogador1){
            System.out.println("Jogador da vez1 "+jogadorDaVez.getNome()+" jogador que vai assumir a vez "+jogador2.getNome());
            jogadorDaVez = jogador2;
        }
        else{
            System.out.println("Jogador da vez2 "+jogadorDaVez.getNome()+" jogador que vai assumir a vez "+jogador2.getNome());
            jogadorDaVez = jogador1;
        }
        if(jogadorDaVez.getNome().equals(jogadorLocal.getNome())){
            jogadorLocal = jogadorDaVez;
            rolarDados();
            movimento = new Movimento(tabuleiro, jogador1, jogador2, jogadorDaVez);
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
        if (jogador1 == null || jogador2 == null) {
            atorJogador.informarFaltaDeJogadores();
            return false;
        }
        jogador1.setId(1);
        jogador2.setId(2);
        tabuleiro.preparaTabuleiro(jogador1.getPecas(), jogador2.getPecas());        
        rolarDados();
        atorJogador.atualizarInterface();
        movimento = new Movimento(tabuleiro, jogador1, jogador2, jogadorDaVez);
        verificarVencedor(jogadorDaVez);
        atorJogador.enviarJogada(movimento);
        return true;
    }

    public void sortearInicio() {
        throw new UnsupportedOperationException();
    }

    public void rolarDados() {
        int[] resultado = jogadorDaVez.rolarDados();
        atorJogador.mostrarAnimacaoDado(resultado);
    }

    public boolean verificarJogada(int[] posInicial, int[] posFinal) {
        Peca peca = tabuleiro.getPeca(posInicial);
        int distancia = tabuleiro.calculaDistancia(posInicial, posFinal);
        System.out.println("Distancia: " + distancia);
        int mov = peca.getMovimento();
        if (mov > 0 && distancia == 1) {
            boolean ocupada = tabuleiro.verificaPosicaoOcupada(posFinal);
            if (ocupada) {
                Peca pecaFinal = tabuleiro.getPeca(posFinal);
                Jogador dono = pecaFinal.getJogador();
                if (dono != jogadorDaVez) {
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
            verificarVencedor(jogadorDaVez);            
            if(jogadorDaVez.pecasAMovimentar() < 1){
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
        if(jogador == jogador1){
            if(jogador2.getPecasAtivas() <=0){
                atorJogador.informaErro("Jogador "+jogador2.getNome()+" não tem mais peças.", "Jogador Sem Peças");
                atorJogador.informaErro("Jogador "+jogador1.getNome()+" Vencedor. Parabéns.", "Vencedor!!!!.");
            }
        }
        if(jogador == jogador2){
            if(jogador1.getPecasAtivas() <=0){
                atorJogador.informaErro("Jogador "+jogador1.getNome()+" não tem mais peças.", "Jogador Sem Peças");
                atorJogador.informaErro("Jogador "+jogador2.getNome()+" Vencedor. Parabéns.", "Vencedor!!!!.");
            }
        }
    }

    public void preparaTeste() {
        jogador1 = new Jogador("teste1");
        jogador2 = new Jogador("teste2");
        jogadorDaVez = jogador2;
        jogadorLocal = jogador2;
        jogador2.rolarDados();

        //partidaEmAndamento = true;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public Jogador getJogadorLocal() {
        return jogadorLocal;
    }

    public void setJogadorLocal(Jogador jogadorLocal) {
        this.jogadorLocal = jogadorLocal;
    }

    public Jogador getJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez(Jogador jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
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
