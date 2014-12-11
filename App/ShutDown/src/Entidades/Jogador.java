/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;
import br.ufsc.inf.leobr.cliente.Jogada;
/**
 *
 * @author fernando
 */
public class Jogador implements Jogada{

    private String nome;
    private int robosAtivos;
    private boolean naVez;
    private boolean prontoParaJogo;
    private Robo[] robos;
    private int id;

    public Jogador(String nome) {
        this.nome = nome;
        robosAtivos = 2;
        robos = new Robo[2];
        robos[0] = new Robo(this);
        robos[1] = new Robo(this);
    }

    public int robosAMovimentar() {
        int quant = 0;
        if(robos[0].isAtiva()&&(robos[0].getMovimento() > 0)){
            quant ++;
        }
        if(robos[1].isAtiva()&&(robos[1].getMovimento() > 0)){
            quant ++;
        }
        return quant;
    }

    public String getNome() {
        return this.nome;
    }

    public int[] rolarDados() {
        int[] result = new int[robos.length];
        for (int i = 0; i < robos.length; i++) {
            result[i] = robos[i].rolarDado();
        }
        return result;
    }

    public int getRobosAtivos() {
        return this.robosAtivos;
    }

    public Robo[] getRobos() {
        return robos;
    }
    
    public Robo informaRoboSelecionado(){                
        if(robos[0].estaSelecionado()){
            return robos[0];
        }
        else if (robos[1].estaSelecionado()){
            return robos[1];
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
        if(robos[0]==pecaFinal){
            robos[0].setAtiva(false);
            robosAtivos--;
        }
        if(robos[1]==pecaFinal){
            robos[1].setAtiva(false);
            robosAtivos--;
        }
    }
}
