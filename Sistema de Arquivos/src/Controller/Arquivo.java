package Controller;

import java.io.Serializable;

/**
 * 
 * @author Vanessa Furtado
 * 
 * Objeto arquivo que contem seu nome e respectivo inode
 *  
 */
public class Arquivo implements Serializable {

	private String nome;
	private iNode inode;
	
	
	public Arquivo(String nome) {
		this.nome = nome;
	}
	
	public void setInode(iNode inode) {
		this.inode = inode;
	}
	
	public String toString() {
		return this.nome+"/"+inode.toString();
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public iNode getInode() {
		return this.inode;
	}
	
}
