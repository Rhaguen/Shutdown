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
public class Posicao implements Jogada{

	private Peca peca;
	private boolean ocupada;
                
        
	public Peca getPeca() {
		return this.peca;
	}

	public boolean isOcupada() {
		return this.ocupada;
	}

	public void removePeca() {
            peca = null;
            ocupada = false;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
                ocupada = true;
	}
}