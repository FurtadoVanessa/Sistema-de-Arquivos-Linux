package Controller;

import java.io.Serializable;

/**
 * 
 * @author Vanessa Furtado
 *
 * Objeto que armazena o numero do bloco livre e a quantidade de blocos livres em sequencia
 *
 */
public class BlocosLivres implements Serializable {
	
	private int endereço;
	private int blocosLivres;
	
	public BlocosLivres(int endereço, int blocosLivres) {
		this.endereço = endereço;
		this.blocosLivres = blocosLivres;
	}
	
	public int getBlocosLivres() {
		return this.blocosLivres;
	}
	
	public int getEndereço() {
		return this.endereço;
	}
	
	public void incrementaLivres(int quantidade) {
		this.blocosLivres+= quantidade + 1 ;
	}

	
	public String toString() {
		return this.endereço+"/"+this.blocosLivres;
	}
}
