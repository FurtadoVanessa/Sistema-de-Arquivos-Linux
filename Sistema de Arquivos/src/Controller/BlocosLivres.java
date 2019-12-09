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
	
	private int endere�o;
	private int blocosLivres;
	
	public BlocosLivres(int endere�o, int blocosLivres) {
		this.endere�o = endere�o;
		this.blocosLivres = blocosLivres;
	}
	
	public int getBlocosLivres() {
		return this.blocosLivres;
	}
	
	public int getEndere�o() {
		return this.endere�o;
	}
	
	public void incrementaLivres(int quantidade) {
		this.blocosLivres+= quantidade + 1 ;
	}

	
	public String toString() {
		return this.endere�o+"/"+this.blocosLivres;
	}
}
