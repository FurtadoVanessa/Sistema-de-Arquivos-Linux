package Controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * 
 * Estrutura de armazenamento de atributos e blocos de um arquivo
 * @author Vanessa Furtado
 *
 */
public class iNode implements Serializable {
	
	private int tamanho;
	private boolean escrita;
	private boolean leitura;
	private Date dataDeCriacao;
	private Date dataDeModificacao;
	private int[] identificadores;
	private int tamanhoRestante;
	private ArrayList<Integer> blocoExcedente;

	
	public iNode(int tamanho, boolean escrita, boolean leitura) {
		this.tamanho = tamanho;
		this.leitura = leitura;
		this.escrita = escrita;
		this.identificadores = new int[12];
		this.tamanhoRestante = 12;
		this.blocoExcedente = new ArrayList<Integer>();
	}
	
	public int getTamanho() {
		return tamanho;
	}


	public boolean isEscrita() {
		return escrita;
	}

	public void setEscrita(boolean escrita) {
		this.escrita = escrita;
	}

	public boolean isLeitura() {
		return leitura;
	}

	public void setLeitura(boolean leitura) {
		this.leitura = leitura;
	}


	public void inserirBloco(int bloco) {
		if(this.tamanhoRestante >= 0) {
			this.identificadores[12 - this.tamanhoRestante] = bloco;
		}else {
			this.blocoExcedente.add(bloco);
		}
		this.tamanhoRestante --;
	}
	
	public String toString() {
		String inode = " de tamanho "+this.tamanho+" com os blocos ";
		for(int i=0; i<12 - tamanhoRestante; i++) {
			inode+= String.valueOf(identificadores[i]+" - ");
		}
		if(!this.blocoExcedente.isEmpty()) {
			for(Integer i : this.blocoExcedente) {
				inode+= " "+i+" -";
			}
		}
		return inode;
	}
	
	public String getIdentificadoresString() {
		String inode = "";
		for(int i=0; i<12 - tamanhoRestante; i++) {
			inode+= String.valueOf(identificadores[i]+" - ");
		}
		return inode;
	}
	
	public int[] getIdentificadores() {
		return this.identificadores;
	}
}
