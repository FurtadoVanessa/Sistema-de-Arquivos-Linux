package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializar {
	
	
	public boolean salvarHD() {
		HD hd = HD.getInstance();
		Diretorio diretorio = Diretorio.getInstance();
		
		try {
			FileOutputStream hdsalvo = new FileOutputStream("saida.dat");
			ObjectOutputStream gravar = new ObjectOutputStream(hdsalvo);
			gravar.writeObject(hd);
			gravar.flush();
			gravar.writeObject(diretorio);
			gravar.flush();
			gravar.close();
			hdsalvo.flush();
			hdsalvo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public boolean importarHD(String nome) {
		
		try {
			FileInputStream hdsalvo = new FileInputStream(nome);
			ObjectInputStream leitura = new ObjectInputStream(hdsalvo);
			HD hd = (HD) leitura.readObject();
			Diretorio diretorio = (Diretorio) leitura.readObject();
			
			if(hd == null) {
				System.out.println("Não salvou HD");
			}else {
				System.out.println("O teste do HD deu "+HD.getInstance().carregarHD(hd));
			}
			
			
			if(diretorio == null) {
				System.out.println("Não salvou diretorio");
			}else {
				System.out.println("O teste do diretorio deu "+Diretorio.getInstance().carregarDiretorio(diretorio));
				System.out.println("Tem o arquivo 0 de nome "+Diretorio.getInstance().getDiretorio().get(0).getNome());
			}
		
			leitura.close();
			hdsalvo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	

}
