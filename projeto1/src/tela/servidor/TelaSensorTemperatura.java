package tela.servidor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.towel.bind.Binder;
import com.towel.bind.annotation.AnnotatedBinder;
import com.towel.bind.annotation.Bindable;
import com.towel.bind.annotation.Form;

import comum.IServidor;
import engine.Constantes;
import engine.Localizacao;
import engine.Sensor;
import engine.Tipo;
import formatter.IntFormatter;

@Form(Sensor.class)
public class TelaSensorTemperatura extends JFrame {

	private String namingLookupServer = null;

	private JTextField tfEnderecoServidor;

	@Bindable(field = "nome")
	private JTextField nome;

	private JComboBox cbLocalizacao;

	private JComboBox cbTipo;

	@Bindable(field = "descricao")
	private JTextField descricao;

	@Bindable(field = "valorLido", formatter = IntFormatter.class)
	private JTextField valorLido;

	private Binder binder;

	private Sensor sensor;
	IServidor iot;// = (IServidor) Naming.lookup("rmi://localhost:1099/iot");
	ControlePorta arduino;

	public TelaSensorTemperatura(String namingLookupServer) {
		super("SensorForm - ARDUINO SENSOR");
		this.namingLookupServer = namingLookupServer;
		this.sensor = new Sensor(Tipo.TEMPERATURA, Localizacao.SALA, "SENSOR DO ARDUINO", "SENSOR DO ARDUINO");

		tfEnderecoServidor = new JTextField(20);
		tfEnderecoServidor.setText(this.namingLookupServer);

		nome = new JTextField(20);

		cbLocalizacao = new JComboBox(Localizacao.values());

		cbTipo = new JComboBox(Tipo.values());

		descricao = new JTextField(30);

		valorLido = new JTextField(30);

		setLayout(new GridLayout(7, 2));

		add(new JLabel("Endereço do servidor:"));
		add(tfEnderecoServidor);

		add(new JLabel("Nome:"));
		add(nome);
		nome.setEditable(false);

		add(new JLabel("Tipo:"));
		add(cbTipo);
		cbTipo.setEnabled(false);
		cbTipo.setSelectedIndex(5);

		add(new JLabel("Localizacao"));// For GridLayout
		add(cbLocalizacao);
		cbLocalizacao.setEnabled(false);
		cbLocalizacao.setSelectedIndex(3);

		add(new JLabel("Descricao"));// For GridLayout
		add(descricao);
		descricao.setEditable(false);

		add(new JLabel("Valor Lido"));// For GridLayout
		add(valorLido);
		valorLido.setEditable(false);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		binder = new AnnotatedBinder(this);
		binder.updateView(getSensor());
		try {
			action();
			arduino = new ControlePorta("/dev/ttyUSB0", 9600);// Linux - porta e
		} catch (Exception e) {
			Mensagem.erro(this, e.getMessage());
			System.exit(0);
		}
	}

	public void action() {
		try {
			getSensor().setNome(nome.getText());
			getSensor().setDescricao(descricao.getText());
			getSensor().setLocalizacao((Localizacao) cbLocalizacao.getSelectedItem());
			getSensor().setTipo((Tipo) cbTipo.getSelectedItem());
			getSensor().setValorLido(valorLido.getText());

			this.namingLookupServer = tfEnderecoServidor.getText();
			iot = (IServidor) Naming.lookup(this.namingLookupServer);
			iot.registrarSensor(getSensor());

			startMonitoring();

			tfEnderecoServidor.setEditable(false);
			nome.setEditable(false);
			descricao.setEditable(false);
			cbLocalizacao.setEnabled(false);
			cbTipo.setEnabled(false);
		} catch (Exception ex) {
			Mensagem.erro(this, ex.getMessage());
		}

	}

	private String ultimoDadoSerialLido = "";
	private synchronized String getUltimoDadoSerialLido(){
		return this.ultimoDadoSerialLido;
	}
	public synchronized void setUltimoDadoSerialLido(String ultimoDadoSerialLido) {
		this.ultimoDadoSerialLido = ultimoDadoSerialLido;
	}
	
	private void startMonitoring() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					while (true) {
						Thread.sleep(500);
						setUltimoDadoSerialLido(arduino.pegaDados());
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					while (true) {
						Thread.sleep(3000);
						valorLido.setText(getUltimoDadoSerialLido());

						getSensor().setValorLido(valorLido.getText());

						iot.registrarValorLido(getSensor());
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	public Sensor getSensor() {
		return sensor;
	}

	public static void main(String[] args) {
		new TelaSensorTemperatura(Constantes.getNamingLokupServer()).setVisible(true);
	}

}