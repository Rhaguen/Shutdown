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
public class Jogador implements Jogada{

    private String nome;
    private int pecasAtivas;
    private boolean naVez;
    private boolean prontoParaJogo;
    private Robo[] pecas;
    private int id;

    public Jogador(String nome) {
        this.nome = nome;
        pecasAtivas = 2;
        pecas = new Robo[2];
        pecas[0] = new Robo(this);
        pecas[1] = new Robo(this);
        /*if(id == 1){
            pecas[0].setDirecao(2);
            pecas[1].setDirecao(2);
        }
        else{
            pecas[0].setDirecao(0);
            pecas[1].setDirecao(0);
        }*/
                
    }

    public int pecasAMovimentar() {
        int quant = 0;
        if(pecas[0].isAtiva()&&(pecas[0].getMovimento() > 0)){
            quant ++;
        }
        if(pecas[1].isAtiva()&&(pecas[1].getMovimento() > 0)){
            quant ++;
        }
        return quant;
    }

    public String getNome() {
        return this.nome;
    }

    public int[] rolarDados() {
        int[] result = new int[pecas.length];
        for (int i = 0; i < pecas.length; i++) {
            result[i] = pecas[i].rolarDado();
        }
        return result;
    }

    public int getPecasAtivas() {
        return this.pecasAtivas;
    }

    public Robo[] getPecas() {
        return pecas;
    }
    
    public Robo informaRoboSelecionado(){                
        if(pecas[0].estaSelecionado()){
            return pecas[0];
        }
        else if (pecas[1].estaSelecionado()){
            return pecas[1];
        }
        else{
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public boolean isNaVez() {
        return naVez;
    }

    public void setNaVez(boolean naVez) {
        this.naVez = naVez;
    }
    
    

    void removerPeca(Robo pecaFinal) {
        if(pecas[0]==pecaFinal){
            pecas[0].setAtiva(false);
            pecasAtivas--;
        }
        if(pecas[1]==pecaFinal){
            pecas[1].setAtiva(false);
            pecasAtivas--;
        }
    }
}
