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
import formatter.LocalizacaoFormatter;
import formatter.TipoFormatter;

@Form(Sensor.class)
public class TelaSensor extends JFrame implements ActionListener {

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

	private JButton btRegistrar;

	private Binder binder;

	private Sensor sensor;
	IServidor iot;// = (IServidor) Naming.lookup("rmi://localhost:1099/iot");

	public TelaSensor(final Sensor sensor, String namingLookupServer) {
		super("SensorForm - " + sensor.getNome());
		this.namingLookupServer = namingLookupServer;
		this.sensor = sensor;

		tfEnderecoServidor = new JTextField(20);
		tfEnderecoServidor.setText(this.namingLookupServer);

		nome = new JTextField(20);

		cbLocalizacao = new JComboBox(Localizacao.values());

		cbTipo = new JComboBox(Tipo.values());

		descricao = new JTextField(30);

		valorLido = new JTextField(30);

		btRegistrar = new JButton("Registrar Sensor");
		getRootPane().setDefaultButton(btRegistrar);

		setLayout(new GridLayout(7, 2));

		add(new JLabel("Endereço do servidor:"));
		add(tfEnderecoServidor);

		add(new JLabel("Nome:"));
		add(nome);

		add(new JLabel("Tipo:"));
		add(cbTipo);

		add(new JLabel("Localizacao"));// For GridLayout
		add(cbLocalizacao);

		add(new JLabel("Descricao"));// For GridLayout
		add(descricao);

		add(new JLabel("Valor Lido"));// For GridLayout
		add(valorLido);

		add(new JLabel(""));
		add(btRegistrar);
		btRegistrar.addActionListener(this);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		binder = new AnnotatedBinder(this);
		binder.updateView(getSensor());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
			btRegistrar.setEnabled(false);

		} catch (Exception ex) {
			Mensagem.erro(this, ex.getMessage());
		}

	}

	private void startMonitoring() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					while (true) {
						Thread.sleep(3000);

						getSensor().setValorLido(valorLido.getText());

						iot.registrarValorLido(getSensor());
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
					// System.exit(1);
				}
			}
		}).start();
	}

	public Sensor getSensor() {
		return sensor;
	}

	public static void main(String[] args) {
		new TelaSensor(new Sensor(Tipo.PORTA, Localizacao.QUARTO, "portao", "aberta(1) ou fechada(0)"),Constantes.getNamingLokupServer()).setVisible(true);
	}

}