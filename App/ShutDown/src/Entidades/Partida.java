/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import Interface.AtorJogador;

/**
 *
 * @author fernando
 */
public class Partida {
    private boolean partidaEmAndamento;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorAtual;
    private Jogador jogador;
    private Tabuleiro tabuleiro;
    private AtorJogador atorJogador;
    private int[] posicaoInicial;
    private int[] posicaoFinal;
    private boolean conectado;
    private Movimento movimento;
    
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
        return jogador;
    }
    
    public void iniciarPartidaLocal(String nomeJogador){
        setJogadorLocal(new Jogador(nomeJogador));
    }

    public void setJogadorLocal(Jogador jogadorLocal) {
        this.jogador = jogadorLocal;
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
    
    public boolean isJogadorDaVez() {
        return this.jogador == this.jogadorAtual;
    }
    
    public Partida(AtorJogador atorJogador) {
        this.atorJogador = atorJogador;
        this.tabuleiro = new Tabuleiro();
        
        this.posicaoInicial = new int[2];
        this.posicaoFinal = new int[2];
    }
    
    public boolean iniciarPartida() {
        this.jogador1.setId(1);
        this.jogador2.setId(2);
        this.tabuleiro.preparaTabuleiro(jogador1.getRobos(), jogador2.getRobos());
        this.partidaEmAndamento = true;
        
        this.novoTurno();
        
        return true;
    }
    
    public void novoTurno(){
        // Rola os dados para que os robos ganhem pontos de movimento e atualiza a interface
        this.rolarDados();
        this.atorJogador.atualizarInterface();
        
        // Atualiza os dados do jogo e envia ao adversário
        this.movimento = new Movimento(tabuleiro, jogador1, jogador2, jogadorAtual);
        this.atorJogador.enviarJogada(this.movimento);
    }

    public void click(int x, int y) {
        boolean daVez = this.isJogadorDaVez();
        if (daVez){
            Posicao posicao = this.tabuleiro.recuperarPosicao(x, y);
            boolean ocupada = posicao.estaOcupada();
            Robo roboDaVez = posicao.informaRobo();
            if (ocupada && roboDaVez.getJogador() == this.jogadorAtual) {             
                this.posicaoInicial[0] = x;
                this.posicaoInicial[1] = y;    
                clickRobo(roboDaVez);

            } else {
                Robo robo = this.jogadorAtual.informaRoboSelecionado();
                if (robo != null) {
                    this.posicaoFinal[0] = x;
                    this.posicaoFinal[1] = y;
                    boolean valida = tratarJogada();
                    if (valida){
                        this.executarMovimento();
                        this.tratarVencedor();
                        this.verificarPassarTurno();
                    }
                    clickRobo(robo);
                }
            }
        }
    }

    public void clickRobo(Robo robo) {
        boolean selecionado = !robo.estaSelecionado();
        robo.setarSelecionado(selecionado);
        this.atorJogador.mudaEstadoSelecaoPeca(selecionado);
    }
    
    public boolean tratarJogada() {
        // Se não houver partida em andamento, informa que a partida já foi encerrada
        if (!partidaEmAndamento) {
            atorJogador.informaPartidaJahEncerrada();
            return false;
        }
        
        boolean movimentoValido = true;
        
        // Verificar se é apenas 1 tile de distancia
        if (this.tabuleiro.calculaDistancia(this.posicaoInicial, this.posicaoFinal) != 1){
            movimentoValido = false;
            this.atorJogador.informaDistanciaInadequada();
        }
        
        // Verifica se a peça possui pontos de movimento
        if (this.tabuleiro.getRobo(this.posicaoInicial).getMovimento() == 0){
            movimentoValido = false;
            this.atorJogador.informaFaltaPontosMovimento();
        }
        
        // Verificar se a posicao está vazia
        if (this.tabuleiro.verificaPosicaoOcupada(this.posicaoFinal)){
            
            // Se não está vazia, verificar se o robo é do jogador atual
            if (tabuleiro.getRobo(this.posicaoFinal).getJogador() == this.jogadorAtual){
                movimentoValido = false;
                this.atorJogador.informaPosicaoOcupada();
            }
            else{
                
                Robo roboFinal = this.tabuleiro.getRobo(this.posicaoFinal);
                
                // Se for do jogador adversário, verificar o robo está de costas para ser capturada
                if (this.tabuleiro.getPosicao(this.posicaoInicial) == roboFinal.getPosicaoCostas()){
                    Jogador adversario = this.tabuleiro.getRobo(this.posicaoFinal).getJogador();
                    
                    // Remove o robo do jogador e do tabuleiro
                    adversario.removerPeca(roboFinal);
                    this.tabuleiro.removeRobo(this.posicaoFinal);
                }
                else{
                    movimentoValido = false;
                    this.atorJogador.informaPosicaoOcupada();
                }
            }
        }
        
        return movimentoValido;
    }
    
    public void executarMovimento(){
        // Seleciona o robo na posição inicial
        Robo robo = this.tabuleiro.getRobo(this.posicaoInicial);
        
        // Estabelece a posição das costas do robo (posição anterior duh)
        robo.setPosicaoCostas(this.tabuleiro.getPosicao(this.posicaoInicial));
        
        // Reduz a quantidade de movimentos
        robo.diminuiMovimento();
        
        // Move o robo para a posição final
        this.tabuleiro.moveRobo(robo, this.posicaoFinal);
        
        // Remove o robo da posição inicial do tabuleiro.
        // Isto é devido à possível demora do garbage collector do Java
        this.tabuleiro.removeRobo(this.posicaoInicial);
        
        // Calcula e atribui a direção da peça baseado no movimento
        int direcao = this.tabuleiro.calcularDirecao(this.posicaoInicial, this.posicaoFinal);
        robo.setDirecao(direcao);
        
        // Atualiza a interface do jogador e envia a jogada ao adversário
        this.atorJogador.atualizarInterface();
        this.atorJogador.enviarJogada(this.movimento);
    }
    
    public void tratarVencedor() {
        if (this.jogador1.getRobosAtivos() <= 0){
            this.atorJogador.informaErro("Jogador " + this.jogador1.getNome() + " não tem mais peças.", "Jogador Sem Peças");
            
            if (this.jogador == this.jogador2){
                this.atorJogador.informaErro("Parabéns!! Você Venceu!!", "Vencedor!!!!");
            }
            else{
                this.atorJogador.informaErro("Você perdeu. Tente novamente.", "Perdedor");
            }
            
            this.encerrarPartida();
        }
        else if (this.jogador2.getRobosAtivos() <= 0){
            this.atorJogador.informaErro("Jogador " + this.jogador2.getNome() + " não tem mais peças.", "Jogador Sem Peças");
            
            if (this.jogador == this.jogador1){
                this.atorJogador.informaErro("Parabéns!! Você Venceu!!", "Vencedor!!!!");
            }
            else{
                this.atorJogador.informaErro("Você perdeu. Tente novamente.", "Perdedor");
            }
            
            this.encerrarPartida();
        }
    }
    
    public void verificarPassarTurno(){
        if (this.jogadorAtual.pecasAMovimentar() == 0){
            this.atorJogador.enviarMensagem(new Mensagem(1, "passarTurno"));
        }
    }

    public void passarTurno() {
        if(this.jogadorAtual == this.jogador1){
            this.jogadorAtual = this.jogador2;
        }
        else{
            this.jogadorAtual = this.jogador1;
        }
        
        if(this.jogadorAtual.getNome().equals(this.jogador.getNome())){
            this.atualizaJogador();
            this.novoTurno();
        }
    }
    
    public void atualizaJogador(){
        this.jogador = this.jogadorAtual;
    }

    public void encerrarPartida() {
        this.partidaEmAndamento = false;
        this.atorJogador.finalizarPartida();
    }

    public void sortearInicio() {
        throw new UnsupportedOperationException();
    }

    public void rolarDados() {
        int[] resultado = jogadorAtual.rolarDados();
        atorJogador.mostrarAnimacaoDado(resultado);
    }
 
}
