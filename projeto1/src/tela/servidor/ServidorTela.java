package tela.servidor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.towel.el.annotation.AnnotationResolver;
import com.towel.swing.table.ObjectTableModel;

import comum.IServidor;
import comum.IServidorTela;
import engine.Atuador;
import engine.Constantes;
import engine.Localizacao;
import engine.Sensor;
import engine.Servidor;
import engine.Tipo;

/**
 * 
 * @author jandrei teste teste
 *
 */
public class ServidorTela extends JFrame implements IServidorTela {

	private static final long serialVersionUID = -9061563475864746331L;

	public ServidorTela() {
		super("Servidor não iniciado");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
	}

	public void startServer() {

		try {

			Servidor server = new Servidor(this);
			server.startServer();

			iot = (IServidor) Naming.lookup(Constantes.getNamingLokupServer());
			setTitle("Servidor iniciado no endereço " + Constantes.getNamingLokupServer());
			status.setText(getTitle());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void initComponents() {
		tfFiltroAtuador = new JTextField();
		tfFiltroSensor = new JTextField();

		scrollPaneAtuadores = new JScrollPane();
		scrollPaneSensores = new JScrollPane();
		menubar = new JMenuBar();
		
		
		this.setJMenuBar(menubar);

		JMenu arquivo = new JMenu("Arquivo");
		arquivo.setMnemonic('a');
		JMenuItem menuNovoAtuador = new JMenuItem("Novo atuador", 'a');
		arquivo.add(menuNovoAtuador);

		JMenuItem menuNovoSensor = new JMenuItem("Novo sensor", 's');
		arquivo.add(menuNovoSensor);

		JMenuItem portaServidor = new JMenuItem("Definir Porta Servidor");
		arquivo.add(portaServidor);
		portaServidor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!iniciouSensorOuAtuador) {
						String porta = JOptionPane.showInputDialog("Informe a porta de operação do servidor:");
						Constantes.portaServidor = Integer.parseInt(porta);
					} else {
						Mensagem.aviso(null, "O servidor já foi iniciado, não será possível alterar essa informação.");
					}
				} catch (Exception ex) {
					Mensagem.erro(null, ex.getMessage());
				}

			}
		});

		JMenuItem nomeServidor = new JMenuItem("Definir Nome do Servidor");
		arquivo.add(nomeServidor);
		nomeServidor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!iniciouSensorOuAtuador) {
						String nome = JOptionPane.showInputDialog(
								"Informe o nome do servidor, apenas texto e sem caracteres especiais e sem espaços:");
						Constantes.nomeServidor = nome;
					} else {
						Mensagem.aviso(null, "O servidor já foi iniciado, não será possível alterar essa informação.");
					}
				} catch (Exception ex) {
					Mensagem.erro(null, ex.getMessage());
				}

			}
		});

		JMenuItem start = new JMenuItem("Start Servidor");
		arquivo.add(start);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!iniciouSensorOuAtuador) {
						startServer();
						iniciouSensorOuAtuador = true;
					} else {
						Mensagem.aviso(null, "O servidor já foi iniciado");
					}
				} catch (Exception ex) {
					Mensagem.erro(null, ex.getMessage());
				}

			}
		});

		menubar.add(arquivo);

		menuNovoAtuador.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				iniciouSensorOuAtuador = true;
				new TelaAtuador(new Atuador(Tipo.JANELA, Localizacao.BANHEIRO, "", ""),
						Constantes.getNamingLokupServer()).setVisible(true);
			}
		});
		menuNovoSensor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				iniciouSensorOuAtuador = true;
				new TelaSensor(new Sensor(Tipo.JANELA, Localizacao.BANHEIRO, "", ""), Constantes.getNamingLokupServer())
						.setVisible(true);
			}
		});

		scrollPaneAtuadores.setViewportView(tabelaAtuadores);
		scrollPaneSensores.setViewportView(tabelaSensores);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneAtuadores, scrollPaneSensores);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);

		JLabel lblAtu = new JLabel("Atuadores");
		JLabel lblSensores = new JLabel("Sensores");

		JPanel paSup = new JPanel(new GridLayout(2, 2));
		paSup.add(lblAtu);
		paSup.add(lblSensores);

		JPanel paFiltroAtuador = new JPanel(new GridLayout(1, 2));
		paFiltroAtuador.add(new JLabel("Pesquisar Atuadores:"));
		paFiltroAtuador.add(tfFiltroAtuador);
		paSup.add(paFiltroAtuador);

		JPanel paFiltroSensor = new JPanel(new GridLayout(1, 2));
		paFiltroSensor.add(new JLabel("Pesquisar Sensores:"));
		paFiltroSensor.add(tfFiltroSensor);
		paSup.add(paFiltroSensor);

		JPanel paCenter = new JPanel(new BorderLayout());
		paCenter.add(splitPane, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(paSup, BorderLayout.NORTH);
		this.add(paCenter, BorderLayout.CENTER);
		
		status = new JLabel("Servidor não iniciado(Inicie no menu Arquivo->Start Servidor)");
		StatusBar statusBar = new StatusBar();
		statusBar.add(status);

		add(statusBar, BorderLayout.SOUTH);

		pack();
	}

	@Override
	public void atualizaSensores() {
		try {
			this.sensores = this.iot.getSensores();
			AnnotationResolver resolver2 = new AnnotationResolver(Sensor.class);
			ObjectTableModel<Sensor> tableModel2 = new ObjectTableModel<Sensor>(resolver2,
					"nome,tipo,localizacao,valorLido");
			tableModel2.setData(sensores);
			tabelaSensores.getTableHeader().setReorderingAllowed(false);
			tabelaSensores.setModel(tableModel2);

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel2);
			tabelaSensores.setRowSorter(sorter);
			this.adicinaEventoFiltro(tfFiltroSensor, sorter);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void atualizaAtuadores() {
		try {
			this.atuadores = this.iot.getAtuadores();

			AnnotationResolver resolver = new AnnotationResolver(Atuador.class);
			ObjectTableModel<Atuador> tableModel = new ObjectTableModel<Atuador>(resolver,
					"nome,tipo,localizacao,valorParaAtuar") {
				@Override
				public void setValueAt(Object value, int row, int col) {
					super.setValueAt(value, row, col);
					if (col == 3) {
						Atuador at = atuadores.get(row);
						try {
							iot.atuar(at);
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
				}
			};

			tableModel.setData(atuadores);
			tableModel.setColEditable(3, true);
			tabelaAtuadores.getTableHeader().setReorderingAllowed(false);
			tabelaAtuadores.setModel(tableModel);

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
			tabelaAtuadores.setRowSorter(sorter);
			this.adicinaEventoFiltro(tfFiltroAtuador, sorter);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private void adicinaEventoFiltro(final JTextField tf, final TableRowSorter<?> rowSorter) {
		tf.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				String text = tf.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				String text = tf.getText();
				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}
		});

	}

	public static void main(String[] args) {
		new ServidorTela().setVisible(true);
		;
	}

	// private Servidor server;
	IServidor iot;

	private JTable tabelaAtuadores = new JTable();
	private JTable tabelaSensores = new JTable();

	private JScrollPane scrollPaneAtuadores;
	private JScrollPane scrollPaneSensores;
	private List<Atuador> atuadores;
	private List<Sensor> sensores;
	JMenuBar menubar;
	JTextField tfFiltroAtuador;
	JTextField tfFiltroSensor;
	boolean iniciouSensorOuAtuador = false;
	JLabel status;
}
