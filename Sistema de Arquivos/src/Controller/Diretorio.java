package Controller;
import java.io.CharArrayReader;
import java.io.Serializable;
import java.util.ArrayList;



/**
 * 
 * @author Vanessa Furtado
 * 
 * Classe de controla o armazenamento de Arquivos no HD. Possui instancia unica dentro de todo o projeto
 *
 */
public class Diretorio implements Serializable{
	
	private ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
	private transient static Diretorio diretorio = null;
	
	private Diretorio() {
	}
	
	public static Diretorio getInstance() {
		if(diretorio == null) {
			diretorio = new Diretorio();
		}
		return diretorio;
	}
	
	public ArrayList<Arquivo> getDiretorio(){
		return this.arquivos;
	}
	
	public String toString() {
		String dir = "";
		
		for(Arquivo v : arquivos) {
			dir+= "@"+v.getNome()+"@"+v.getInode().getTamanho()+"@"+v.getInode().isEscrita()+"@"+v.getInode().isLeitura()+"@"+v.getInode().getIdentificadoresString()+"%";
		}
		
		return dir;
	}
	
	
	/**
	 * Cria um novo arquivo no HD
	 * @param nome - Nome do Arquivo
	 * @param conteudo - Conteudo do Arquivo
	 * @param leitura - permissao de leitura ao arquivo
	 * @param escrita - permissao de escrita ao arquivo
	 * @return - variaveis pra controle se deu certo ou não. 
	 * 			Caso "N" - já existe arquivo com esse nome. 
	 * 			Caso "E" - Não há espaço suficiente no HD para guardar o arquivo
	 * 			Caso "O" - Tudo certo, arquivo foi gravado no HD
	 */
	public String criarNovoArquivo(String nome, char[] conteudo, boolean leitura, boolean escrita) {
		System.out.println("Ira criar o arquivo "+nome+" com conteudo = \""+String.valueOf(conteudo)+"\"");
		if(!validaNome(nome)) {
			System.out.println("*****ERRO***** - Nome já existente");
			return "N";
		}else {
			Arquivo arquivo = new Arquivo(nome);
			
			String c = "";
			int tamanhoBloco = HD.getInstance().getTamanhoBloco();
			if(conteudo.length < HD.getInstance().getTamanhoDisponivel()) {
				iNode inode = new iNode(conteudo.length, escrita, leitura);
				for(int i=0; i<= conteudo.length/tamanhoBloco; i++) {
					if((HD.getInstance().getTamanhoBloco() > (conteudo.length - i*tamanhoBloco))&&(conteudo.length - i*tamanhoBloco>0)) {
						c = new String(conteudo, i*tamanhoBloco, (conteudo.length - i*tamanhoBloco));
						System.out.println("A diferença do conteudo eh de "+(conteudo.length - i*tamanhoBloco));
					}else if((conteudo.length - i*tamanhoBloco)>0){
						System.out.println("Entrou no if mesmo o valor sendo "+(conteudo.length - i*tamanhoBloco));
						c = new String(conteudo, i*tamanhoBloco, tamanhoBloco);
					}else {
						System.out.println("Nao há mais nada para inserir");
						break;
					}
					char[] bloquinho = c.toCharArray();
					inode.inserirBloco(HD.getInstance().escreverBloco(bloquinho));
					arquivo.setInode(inode);
				}
				arquivos.add(arquivo);
			}else {
				System.out.println("Nao ha espaço suficiente disponivel");
				return "E";
			}
		}
		HD.getInstance().arranjarBlocosLivres();
		return "O";
	}
	
	
	
	/**
	 * Edita um arquivo ja existente no HD
	 * @param nome - 
	 * @param nome - Nome do Arquivo
	 * @param conteudo - Conteudo do Arquivo
	 * @param leitura - permissao de leitura ao arquivo
	 * @param escrita - permissao de escrita ao arquivo
	 * @return true caso conseguiu editar, false caso o arquivo não exista
	 */
	public boolean editarArquivo(String nome, String arquivo, boolean leitura, boolean escrita) {
		for(Arquivo v : arquivos) {
			if(v.getNome().compareTo(nome)==0) {
				excluirArquivo(nome);
				HD.getInstance().estadoHD();
				criarNovoArquivo(nome, arquivo.toCharArray(), leitura, escrita);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Imprime o status do diretorio
	 */
	public void relatorioDiretorio() {
		for(Arquivo v : arquivos) {
			System.out.println("Ha  arquivo "+v);
		}
	}
	
	/**
	 * Exclui determinado arquivo do HD
	 * @param nome - Nome do arquivo a ser excluido
	 * @return true caso tenha excluido o arquivo, false caso ele não exista
	 */
	public boolean excluirArquivo(String nome) {
		for(Arquivo v : arquivos) {
			if(v.getNome().compareTo(nome)==0) {
				System.out.println("Ira excluir o arquivo "+nome);
				int[] blocos = v.getInode().getIdentificadores();
				arquivos.remove(v);
				for(int i=0; i< blocos.length; i++) {
					if(blocos[i]!=0) {
						HD.getInstance().limparBloco(blocos[i]);
						System.out.println("Limpou o bloco "+blocos[i]);
					}
				}
				HD.getInstance().arranjarBlocosLivres();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * valida se ja existe um arquivo com esse nome
	 * @param nome - Nome do arquivo
	 * @return true caso não exista, false caso já exista
	 */
	public boolean validaNome(String nome) {
		for(Arquivo v : arquivos) {
			if(v.getNome().compareTo(nome)==0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Exclui todos os arquivos do diretorio
	 */
	public void limparDiretorio() {
		arquivos.removeAll(arquivos);
	}
	
	public String carregarDiretorio(Diretorio diretorio) {
		arquivos = diretorio.arquivos;
		
		return "carregado";
	}
	
}
