package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;

import Controller.*;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HDView extends JFrame {

	private JPanel contentPane;
	private JTextField tamanhoHD;
	private JTextField tamanhoBloco;
	private JList HDtable;
	private JTable blocosLivresTable;
	private HD hd;
	private Diretorio diretorio;
	private JTable diretorioTable;
	private JTable inodeTable;
	private JLabel lblQuantidade, quantidade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HDView frame = new HDView();
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
	public HDView() {
		hd = HD.getInstance();
		diretorio = Diretorio.getInstance();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 912, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHd = new JLabel("HD");
		lblHd.setBounds(10, 11, 46, 14);
		contentPane.add(lblHd);

		JLabel lblTamnho = new JLabel("Tamanho: ");
		lblTamnho.setBounds(10, 27, 75, 14);
		contentPane.add(lblTamnho);

		JLabel lblBloco = new JLabel("Bloco");
		lblBloco.setBounds(369, 11, 46, 14);
		contentPane.add(lblBloco);

		JLabel lblTamanho = new JLabel("Tamanho: ");
		lblTamanho.setBounds(338, 27, 77, 14);
		contentPane.add(lblTamanho);

		lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(338, 52, 119, 14);
		contentPane.add(lblQuantidade);

		tamanhoHD = new JTextField();
		tamanhoHD.setBounds(66, 24, 46, 20);
		contentPane.add(tamanhoHD);
		tamanhoHD.setColumns(10);

		tamanhoBloco = new JTextField();
		tamanhoBloco.setBounds(393, 24, 46, 20);
		contentPane.add(tamanhoBloco);
		tamanhoBloco.setColumns(10);

		HDtable = new JList<String>();
		HDtable.setVisibleRowCount(1);
		HDtable.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		blocosLivresTable = new JTable();
		blocosLivresTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Bloco", "Livres" }));

		JButton btnCriarHd = new JButton("Criar HD");
		btnCriarHd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int th = Integer.parseInt(tamanhoHD.getText());
				int tb = Integer.parseInt(tamanhoBloco.getText());
				if((th % tb) == 0) {
					hd.criarHD(th, tb);
					carregarTabelas();
				}else {
					JOptionPane.showMessageDialog(null, "Tamanho do HD deve ser divisivel pelo tamanho do bloco", "Erro no tamanho", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCriarHd.setBounds(541, 23, 89, 23);
		contentPane.add(btnCriarHd);

		JScrollPane ScrollBlocos = new JScrollPane();
		ScrollBlocos.setBounds(10, 200, 158, 250);
		ScrollBlocos.setViewportView(blocosLivresTable);
		contentPane.add(ScrollBlocos);

		JButton btnInserirArquivo = new JButton("Inserir Arquivo");
		btnInserirArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (HD.getInstance().getTamanhoHD() == 0) {
					JOptionPane.showMessageDialog(null, "Por favor, crie um HD", "SEM HD", JOptionPane.ERROR_MESSAGE);
				} else {
					InserirArquivoView inserir = new InserirArquivoView(null);
					inserir.setVisible(true);
					dispose();
				}
			}
		});
		btnInserirArquivo.setBounds(756, 203, 119, 23);
		contentPane.add(btnInserirArquivo);

		JScrollPane scrollDiretorio = new JScrollPane();
		scrollDiretorio.setBounds(194, 200, 200, 250);
		contentPane.add(scrollDiretorio);

		diretorioTable = new JTable();
		diretorioTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Inode" }));
		scrollDiretorio.setViewportView(diretorioTable);

		JScrollPane scrollInode = new JScrollPane();
		scrollInode.setBounds(421, 200, 325, 250);
		contentPane.add(scrollInode);

		inodeTable = new JTable();
		inodeTable.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Tamanho", "Leitura", "Escrita", "Blocos" }));
		scrollInode.setViewportView(inodeTable);

		JButton btnEditarArquivo = new JButton("Editar Arquivo");
		btnEditarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (HD.getInstance().getTamanhoHD() == 0) {
					JOptionPane.showMessageDialog(null, "Por favor, crie um HD", "SEM HD", JOptionPane.ERROR_MESSAGE);
				} else {
					int arq = diretorioTable.getSelectedRow();
					if (arq == -1) {
						JOptionPane.showMessageDialog(null, "Por favor, selecione um arquivo no diretorio",
								"SEM ARQUIVO", JOptionPane.ERROR_MESSAGE);
					} else {
						Arquivo arquivo = diretorio.getDiretorio().get(arq);
						if(arquivo.getInode().isEscrita()) {
							InserirArquivoView editar = new InserirArquivoView(arquivo);
							editar.setVisible(true);
						}else {
							JOptionPane.showMessageDialog(null, "Nao eh possivel editar arquivo", "Permissão negada", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		btnEditarArquivo.setBounds(756, 237, 119, 23);
		contentPane.add(btnEditarArquivo);

		JButton btnRemoverArquivo = new JButton("Remover Arquivo");
		btnRemoverArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (HD.getInstance().getTamanhoHD() == 0) {
					JOptionPane.showMessageDialog(null, "Por favor, crie um HD", "SEM HD", JOptionPane.ERROR_MESSAGE);
				} else {
					int arq = diretorioTable.getSelectedRow();
					if (arq == -1) {
						JOptionPane.showMessageDialog(null, "Por favor, selecione um arquivo no diretorio",
								"SEM ARQUIVO", JOptionPane.ERROR_MESSAGE);
					} else {
						Arquivo arquivo = diretorio.getDiretorio().get(arq);
						boolean remover = diretorio.excluirArquivo(arquivo.getNome());
						if (remover) {
							JOptionPane.showMessageDialog(null, "Arquivo removido com sucesso");
							carregarTabelas();
						}
					}
				}
			}
		});
		btnRemoverArquivo.setBounds(756, 271, 119, 23);
		contentPane.add(btnRemoverArquivo);

		JButton btnFormatarHd = new JButton("FORMATAR HD");
		btnFormatarHd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tamanhoHD = JOptionPane.showInputDialog("Digite o tamanho do HD", "tamanho do HD");
				String tamanhoBloco = JOptionPane.showInputDialog("Digite o tamanho do bloco", "tamanho do bloco");
				boolean formatar = HD.getInstance().formatarHD(Integer.parseInt(tamanhoHD), Integer.parseInt(tamanhoBloco));
				if(formatar) {
					JOptionPane.showMessageDialog(null, "HD formatado com sucesso");
					carregarTabelas();
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao formatar. Tamanho do HD deve ser divisível pelo tamanho do bloco","Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnFormatarHd.setBounds(640, 23, 167, 23);
		contentPane.add(btnFormatarHd);

		JLabel lblBlocosLivres = new JLabel("Blocos Livres");
		lblBlocosLivres.setBounds(42, 175, 119, 14);
		contentPane.add(lblBlocosLivres);

		JLabel lblDiretorio = new JLabel("Diretorio");
		lblDiretorio.setBounds(258, 175, 75, 14);
		contentPane.add(lblDiretorio);

		JLabel lblInode = new JLabel("i-node");
		lblInode.setBounds(529, 175, 46, 14);
		contentPane.add(lblInode);
		
		quantidade = new JLabel("");
		quantidade.setBounds(403, 52, 46, 14);
		contentPane.add(quantidade);
		
		JButton btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Serializar salvar = new Serializar();
				if(salvar.importarHD("saida.dat")) {
					JOptionPane.showMessageDialog(null, "HD importado com sucesso");
					carregarTabelas();
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao importar", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnImportar.setBounds(541, 48, 89, 23);
		contentPane.add(btnImportar);
		
		JButton btnSalvarEstado = new JButton("Salvar Estado");
		btnSalvarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Serializar salvar = new Serializar();
				if(salvar.salvarHD()) {
					JOptionPane.showMessageDialog(null, "HD exportado com sucesso");
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao exportar", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSalvarEstado.setBounds(640, 48, 167, 23);
		contentPane.add(btnSalvarEstado);

	}

	public void carregarTabelas() {
		HDtable.clearSelection();
		DefaultTableModel dm = (DefaultTableModel)  blocosLivresTable.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		dm = (DefaultTableModel)  inodeTable.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		dm = (DefaultTableModel)  diretorioTable.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		
		System.out.println("Entrou para carregar as tabelas");
		ArrayList<Bloco> bloco = HD.getInstance().getHD();

		tamanhoHD.setText(String.valueOf(hd.getTamanhoHD()));
		tamanhoBloco.setText(String.valueOf(hd.getTamanhoBloco()));
		quantidade.setText(String.valueOf(hd.getTamanhoHD() / hd.getTamanhoBloco()));
		HDtable.setModel(new DefaultListModel<String>());

		for (Bloco b : bloco) {
			String data = "";
			for (char c : b.getConteudo()) {
				data += String.valueOf(c);
			}
			DefaultListModel<String> tb = (DefaultListModel<String>) HDtable.getModel();
			tb.addElement(data);

		}
		HDtable.setBounds(10, 70, 650, 100);
		contentPane.add(HDtable);

		for (BlocosLivres bl : HD.getInstance().getBlocosLivres()) {
			String[] data = new String[2];
			data[0] = String.valueOf(bl.getEndereço());
			data[1] = String.valueOf(bl.getBlocosLivres());
			System.out.println("Sera colocado na tabela os valores " + data[0] + " e " + data[1]);
			DefaultTableModel tb = (DefaultTableModel) blocosLivresTable.getModel();
			tb.addRow(data);
		}
		blocosLivresTable.setBounds(10, 200, 50, 50);

		for (Arquivo v : Diretorio.getInstance().getDiretorio()) {
			String[] data = new String[2];
			data[0] = v.getNome();
			data[1] = "-->";
			String[] dataInode = new String[4];
			dataInode[0] = String.valueOf(v.getInode().getTamanho());
			dataInode[2] = ((v.getInode().isEscrita()) ? "SIM" : "NAO");
			dataInode[1] = ((v.getInode().isLeitura()) ? "SIM" : "NAO");
			dataInode[3] = v.getInode().getIdentificadoresString();

			System.out.println("Ira inserir o Arquivo " + data[0] + " com INODE " + data[1]);
			DefaultTableModel tb = (DefaultTableModel) diretorioTable.getModel();
			tb.addRow(data);
			DefaultTableModel tbinode = (DefaultTableModel) inodeTable.getModel();
			tbinode.addRow(dataInode);
		}
		
		
		HDtable.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                      boolean isSelected, boolean cellHasFocus) {
                 Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                 setBackground(generateColor());

                 return c;
            }

       });
  }
		
		
		

	public Color generateColor() {
		int red = (int) Math.round(255 * Math.random());
		int green = (int) Math.round(255 * Math.random());
		int blue = (int) Math.round(255 * Math.random());
		Color color = new Color(red, green, blue);
		return color;
	}
}
