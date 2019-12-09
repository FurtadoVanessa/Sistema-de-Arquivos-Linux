package Controller;

import java.io.Serializable;

/**
 * 
 * @author Vanessa Furtado
 *
 * Objeto bloco para armazenar seu numero na ordem do HD e seu conteudo
 *
 */
public class Bloco implements Serializable {
	
	private int numero;
	private char[] conteudo;
	
	public Bloco(int numero, char[] conteudo) {
		this.numero = numero;
		this.conteudo = conteudo;
	}
	
	public String toString() {
		return this.numero+"/"+String.valueOf(conteudo);
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public char[] getConteudo() {
		return this.conteudo;
	}
	
	public String getConteudoString() {
		return String.valueOf(conteudo);
	}

}
