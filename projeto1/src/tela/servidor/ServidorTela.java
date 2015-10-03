package tela.servidor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.Naming;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import servidor.Servidor;

import com.towel.el.annotation.AnnotationResolver;
import com.towel.swing.table.ObjectTableModel;
import comum.IServidor;

import engine.Atuador;
import engine.Sensor;

public class ServidorTela extends JFrame implements IServidorTela {

	private static final long serialVersionUID = -9061563475864746331L;

	public ServidorTela() {
		super("Servidor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		try {
			Servidor server = new Servidor(this);
			server.startServer();

			iot = (IServidor) Naming.lookup("rmi://localhost:1099/iot");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	//private Servidor server;
	IServidor iot;

	private JTable tabelaAtuadores = new JTable();
	private JTable tabelaSensores = new JTable();

	private JScrollPane scrollPaneAtuadores;
	private JScrollPane scrollPaneSensores;
	private List<Atuador> atuadores;
	private List<Sensor> sensores;

	private void initComponents() {
		scrollPaneAtuadores = new JScrollPane();
		scrollPaneSensores = new JScrollPane();

		scrollPaneAtuadores.setViewportView(tabelaAtuadores);
		scrollPaneSensores.setViewportView(tabelaSensores);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneAtuadores, scrollPaneSensores);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);

		JLabel lblAtu = new JLabel("Atuadores");
		JLabel lblSensores = new JLabel("Sensores");

		JPanel paSup = new JPanel(new GridLayout(1, 2));
		paSup.add(lblAtu);
		paSup.add(lblSensores);
		JPanel paCenter = new JPanel(new BorderLayout());
		paCenter.add(splitPane,BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(paSup, BorderLayout.NORTH);
		this.add(paCenter, BorderLayout.CENTER);

		pack();
	}

		
	@Override
	public void atualizaTela() {
		try {
			this.atuadores = this.iot.getAtuadores();
			this.sensores = this.iot.getSensores();
			
			
			AnnotationResolver resolver = new AnnotationResolver(Atuador.class);
			ObjectTableModel<Atuador> tableModel = new ObjectTableModel<Atuador>(resolver, "nome,tipo,localizacao,valorParaAtuar");
			tableModel.setData(atuadores);
			//tableModel.setColEditable(3, true);
			tabelaAtuadores.getTableHeader().setReorderingAllowed(false);
			tabelaAtuadores.setModel(tableModel);
			
			
			AnnotationResolver resolver2 = new AnnotationResolver(Sensor.class);
			ObjectTableModel<Sensor> tableModel2 = new ObjectTableModel<Sensor>(resolver2, "nome,tipo,localizacao,valorLido,valorAtribuido");
			tableModel2.setData(sensores);
			//tableModel2.setColEditable(3, true);
			//tableModel2.setColEditable(5, true);
			tabelaSensores.getTableHeader().setReorderingAllowed(false);
			tabelaSensores.setModel(tableModel2);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new ServidorTela().setVisible(true);
		;
	}

}
