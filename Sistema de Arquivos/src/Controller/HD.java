package Controller;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe de controla o armazenamento de dados no HD. Possui instancia unica dentro de todo o projeto
 * @author Vanessa Furtado
 *
 */
public class HD implements Serializable{
	
	
	private int tamanhoHD;
	private int tamanhoBloco;
	private ArrayList<BlocosLivres> blocosLivres;
	private transient static HD hd = null;
	private ArrayList<Bloco> conteudo;
	
	
	private HD() {
	}
	
	public static HD getInstance() {
		if(hd == null) {
			hd = new HD();
		}
		return hd;
	}
	
	public String toString() {
		String hd = "";
		
		hd+=String.valueOf(tamanhoHD)+"$"+String.valueOf(tamanhoBloco);
		for(BlocosLivres bl : blocosLivres) {
			hd+="["+String.valueOf(bl.getEndereço())+"["+String.valueOf(bl.getBlocosLivres())+"]";
		}
		for(Bloco b : conteudo) {
			hd+="{"+b.getNumero()+"{"+b.getConteudoString()+"}";
		}
		
		return hd; 
	}
	
	public ArrayList<Bloco> getHD(){
		return this.conteudo;
	}
	
	public ArrayList<BlocosLivres> getBlocosLivres(){
		return this.blocosLivres;
	}
	
	
	/**
	 * é criado um HD 
	 * @param tamanhoHD - tamanho do HD a ser criado
	 * @param tamanhoBloco - tamanho do bloco dentro do HD
	 */
	public void criarHD(int tamanhoHD, int tamanhoBloco) {
		this.tamanhoHD = tamanhoHD;
		this.tamanhoBloco = tamanhoBloco;
		int nBlocos = tamanhoHD/tamanhoBloco;
		BlocosLivres bl = new BlocosLivres(1, nBlocos-1);
		blocosLivres = new ArrayList<BlocosLivres>();
		conteudo = new ArrayList<Bloco>();
		blocosLivres.add(bl);
		System.out.println("Foi criado HD com tamanho "+tamanhoHD+" e bloco tamanho "+tamanhoBloco+" totalizando "+nBlocos+" blocos, sendo todos livres no momento");
	}
	
	public int getTamanhoHD() {
		return this.tamanhoHD;
	}
	
	public int getTamanhoBloco() {
		return this.tamanhoBloco;
	}
	
	public int getTamanhoDisponivel() {
		return this.tamanhoHD - (this.tamanhoBloco*conteudo.size());
	}
	
	/**
	 * Preenche um bloco e o armazena no HD
	 * @param conteudo - conteudo a ser gravado no HD
	 * @return o número do bloco criado
	 */
	public int escreverBloco(char conteudo[]) {
		if(blocosLivres.isEmpty()) {
			System.out.println("Não há espaço disponível");
			return -1;
		}else {
			System.out.println("Irá guardar \""+ String.valueOf(conteudo)+"\"");
			BlocosLivres bl = blocosLivres.remove(0);
			Bloco bloco = new Bloco(bl.getEndereço(), conteudo);
			this.conteudo.add(bloco);
			System.out.println("Foi preenchido o bloco: "+bloco.toString());
			if(bl.getBlocosLivres() > 0) {
				blocosLivres.add(0, new BlocosLivres(bl.getEndereço()+1, bl.getBlocosLivres()-1));
				for(BlocosLivres b : blocosLivres) {
					System.out.println("Ha disponivel ainda o bloco "+b.getEndereço()+" com "+b.getBlocosLivres()+" blocos livres a sua frente");
				}
			}
			return bloco.getNumero();
		}
	}
	
	/**
	 * Limpa um determinado bloco do HD
	 * @param bloco - numero do bloco a ser limpo
	 * @return true se conseguiu limpar, false caso bloco inexistente
	 */
	public boolean limparBloco(int bloco) {
		for(Bloco b : conteudo) {
			if(b.getNumero() == bloco) {
				blocosLivres.add(new BlocosLivres(b.getNumero(), 0));
				conteudo.remove(b);
				return true;
			}
		}
		
		return false;
	}
	
	
	public Bloco getBloco(int bloco) {
		for(Bloco b : conteudo) {
			if(b.getNumero() == bloco) {
				return b;
			}
		}
		
		return null;
	}
	
	/**
	 * Imprime o estado do HD
	 */
	public void estadoHD() {
		System.out.println("\n\n######################### ESTADO DO HD #########################\n\n");
		System.out.println("\n\n######################### BLOCOS OCUPADOS #########################\n\n");
		for(Bloco b : conteudo) {
			System.out.println("Há preenchido o bloco "+b);
		}
		System.out.println("\n\n######################### BLOCOS LIVRES #########################\n\n");
		for(BlocosLivres bl : blocosLivres) {
			System.out.println("Há disponivel o bloco "+bl.getEndereço()+" com "+bl.getBlocosLivres()+" blocos livres a sua frente");
		}
		System.out.println("\n\n######################### FIM DE BLOCOS LIVRES #########################\n\n");
	}
	
	/**
	 * Rearranja os blocos livres de acordo com seu número e quantidade de blocos livres a frente.
	 * Ao avaliar dois blocos livres, é analisado de um é a sequencia do outro (n e n+1) ou se seu numero somado a quantidade de livres a sua frente +1 corresponde ao outro bloco (n+q+1 == x) 
	 */
	public void arranjarBlocosLivres() {
		System.out.println("\n\n@@@@@@@@@@@@@@@@ ARRANJO DE BLOCOS LIVRES @@@@@@@@@@@@@@\n");
		for(BlocosLivres b : blocosLivres) {
			System.out.println("Tem o bloco "+b.getEndereço()+" com blocos livres "+b.getBlocosLivres());
		}
		for(int i=0; i<blocosLivres.size(); i++) {
			for(int j=0; j<blocosLivres.size(); j++) {
					int blocoAtual = blocosLivres.get(i).getEndereço();
					int blocoAvaliado = blocosLivres.get(j).getEndereço();
					System.out.println("Tentando juntar "+blocoAtual+" com "+blocoAvaliado);
					if((blocoAtual+1) == (blocoAvaliado)
							|| (blocoAtual+blocosLivres.get(i).getBlocosLivres()+1) == (blocoAvaliado)){
						blocosLivres.get(i).incrementaLivres(blocosLivres.get(j).getBlocosLivres());
						blocosLivres.remove(j);
						i--;
						j--;
						System.out.println("Juntou "+blocoAtual+" com "+blocoAvaliado);
					}
			}
		}
		System.out.println("\n@@@@@@@@@@@@@@@@ FIM DO ARRANJO DE BLOCOS LIVRES @@@@@@@@@@@@@@\n");	
	}
	
	/**
	 * Formata o HD removendo todo seu conteudo e de todo o diretorio
	 * @param tamanhoHD - tamanho do novo HD a ser formado
	 * @param tamanhoBloco - tamanho do bloco do novo HD
	 * @return true se conseguiu formatar, false caso contrario
	 */
	public boolean formatarHD(int tamanhoHD, int tamanhoBloco) {
		Diretorio diretorio = Diretorio.getInstance();
		if((tamanhoHD % tamanhoBloco)==0) {
			conteudo.removeAll(conteudo);
			diretorio.limparDiretorio();
			criarHD(tamanhoHD, tamanhoBloco);
			return true;
		}
		return false;
	}
	
	public String carregarHD(HD hd) {
		HD.getInstance().tamanhoHD = hd.tamanhoHD;
		HD.getInstance().tamanhoBloco = hd.tamanhoBloco;
		blocosLivres = hd.getBlocosLivres();
		conteudo = hd.conteudo;
		
		return "Carregado";
	}
}
