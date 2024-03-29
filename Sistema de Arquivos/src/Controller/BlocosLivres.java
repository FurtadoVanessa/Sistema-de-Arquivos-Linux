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
	
	private int enderešo;
	private int blocosLivres;
	
	public BlocosLivres(int enderešo, int blocosLivres) {
		this.enderešo = enderešo;
		this.blocosLivres = blocosLivres;
	}
	
	public int getBlocosLivres() {
		return this.blocosLivres;
	}
	
	public int getEnderešo() {
		return this.enderešo;
	}
	
	public void incrementaLivres(int quantidade) {
		this.blocosLivres+= quantidade + 1 ;
	}

	
	public String toString() {
		return this.enderešo+"/"+this.blocosLivres;
	}
}
