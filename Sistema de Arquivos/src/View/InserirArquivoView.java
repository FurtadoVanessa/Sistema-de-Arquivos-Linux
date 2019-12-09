package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.Arquivo;
import Controller.Bloco;
import Controller.Diretorio;
import Controller.HD;
import Controller.iNode;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class InserirArquivoView extends JFrame {

	private JPanel contentPane;
	private JTextField nome;
	private JTextField conteudo;
	private Diretorio diretorio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InserirArquivoView frame = new InserirArquivoView(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InserirArquivoView(Arquivo arquivo) {

		this.diretorio = Diretorio.getInstance();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nome = new JTextField();
		nome.setBounds(10, 90, 133, 20);
		contentPane.add(nome);
		nome.setColumns(10);

		JLabel lblNomeDoArquivo = new JLabel("Nome do Arquivo");
		lblNomeDoArquivo.setBounds(10, 65, 133, 14);
		contentPane.add(lblNomeDoArquivo);

		JLabel lblConteudo = new JLabel("Conteudo");
		lblConteudo.setBounds(10, 234, 70, 14);
		contentPane.add(lblConteudo);

		conteudo = new JTextField();
		conteudo.setBounds(10, 259, 264, 71);
		contentPane.add(conteudo);
		conteudo.setColumns(10);

		JLabel lblLeitura = new JLabel("Leitura");
		lblLeitura.setBounds(10, 121, 46, 14);
		contentPane.add(lblLeitura);

		JLabel lblEscrita = new JLabel("Escrita");
		lblEscrita.setBounds(10, 172, 46, 14);
		contentPane.add(lblEscrita);

		JRadioButton simLeitura = new JRadioButton("Sim");
		simLeitura.setBounds(10, 142, 56, 23);
		contentPane.add(simLeitura);

		JRadioButton naoLeitura = new JRadioButton("Nao");
		naoLeitura.setBounds(68, 142, 56, 23);
		contentPane.add(naoLeitura);

		JRadioButton simEscrita = new JRadioButton("Sim");
		simEscrita.setBounds(10, 193, 56, 23);
		contentPane.add(simEscrita);

		JRadioButton naoEscrita = new JRadioButton("N\u00E3o");
		naoEscrita.setBounds(68, 193, 56, 23);
		contentPane.add(naoEscrita);

		ButtonGroup leitura = new ButtonGroup();
		leitura.add(simLeitura);
		leitura.add(naoLeitura);

		ButtonGroup escrita = new ButtonGroup();
		escrita.add(simEscrita);
		escrita.add(naoEscrita);

		JButton btnGravarArquivo = new JButton("Gravar Arquivo");
		btnGravarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean leitura, escrita;
				leitura = simLeitura.isSelected() ? true : false;
				escrita = simEscrita.isSelected() ? true : false;

				if (arquivo != null) {
					System.out.println("O nome agora vale " + nome.getText());
					boolean editar = diretorio.editarArquivo(nome.getText(), conteudo.getText(), leitura, escrita);
					if (editar) {
						JOptionPane.showMessageDialog(null, "Arquivo editado com sucesso");
						carregaTela();
					} else
						JOptionPane.showMessageDialog(null, "Arquivo inexistente ", "Erro", JOptionPane.ERROR_MESSAGE);
				} else {
					String result = diretorio.criarNovoArquivo(nome.getText(), conteudo.getText().toCharArray(),
							leitura, escrita);
					if (result.compareTo("N") == 0) {
						JOptionPane.showMessageDialog(null, "Arquivo com nome já existente", "Nome já existente",
								JOptionPane.ERROR_MESSAGE);
					}
					if (result.compareTo("E") == 0) {
						JOptionPane.showMessageDialog(null, "Não há espaço suficiente para salvar o arquivo",
								"Espaço Insuficiente", JOptionPane.ERROR_MESSAGE);
					}
					if (result.compareTo("O") == 0) {
						JOptionPane.showMessageDialog(null, "Arquivo criado com sucesso");
						carregaTela();
					}
				}
			}
		});
		btnGravarArquivo.setBounds(80, 351, 119, 23);
		contentPane.add(btnGravarArquivo);

		JButton btnVolta = new JButton("Voltar");
		btnVolta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregaTela();
			}
		});
		btnVolta.setBounds(10, 11, 70, 23);
		contentPane.add(btnVolta);

		if (arquivo != null) {
			nome.setText(arquivo.getNome());
			nome.setEditable(false);
			String texto = "";
			iNode inode = arquivo.getInode();
			for (int i = 0; i < inode.getIdentificadores().length; i++) {
				if (inode.getIdentificadores()[i] != 0) {
					int bloco = inode.getIdentificadores()[i];
					Bloco bloco2 = HD.getInstance().getBloco(bloco);
					System.out.println("Pedou o bloco " + bloco + " com o conteudo " + bloco2.getConteudoString());
					texto += bloco2.getConteudoString();
				}
			}
			conteudo.setText(texto);
			simEscrita.setSelected(inode.isEscrita());
			naoEscrita.setSelected(!inode.isEscrita());
			simLeitura.setSelected(inode.isLeitura());
			naoLeitura.setSelected(!inode.isLeitura());

		}

	}

	public void carregaTela() {
		HDView hd = new HDView();
		hd.carregarTabelas();
		hd.setVisible(true);
		dispose();
	}
}
