package tela.servidor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import tela.servidor.formatter.IntFormatter;
import tela.servidor.formatter.LocalizacaoFormatter;
import tela.servidor.formatter.TipoFormatter;

import com.towel.bind.Binder;
import com.towel.bind.annotation.AnnotatedBinder;
import com.towel.bind.annotation.Bindable;
import com.towel.bind.annotation.Form;

import comum.IServidor;
import engine.Sensor;
import engine.tipos.Localizacao;
import engine.tipos.Tipo;

@Form(Sensor.class)
public class TelaSensor extends JFrame {

	@Bindable(field = "nome")
	private JTextField nome;

	@Bindable(field = "tipo", formatter = TipoFormatter.class)
	private JTextField tipo;

	@Bindable(field = "localizacao",formatter=LocalizacaoFormatter.class)
	private JTextField localizacao;

	@Bindable(field = "descricao")
	private JTextField descricao;

	@Bindable(field = "valorLido", formatter = IntFormatter.class)
	private JTextField valorLido;

	@Bindable(field = "valorAtribuido", formatter = IntFormatter.class)
	private JTextField valorAtribuido;

	private Binder binder;

	private Sensor sensor;
	IServidor iot;// = (IServidor) Naming.lookup("rmi://localhost:1099/iot");

	public TelaSensor(Sensor sensor) {
		super("SensorForm - "+sensor.getNome());

		this.sensor = sensor;

		nome = new JTextField(20);
		nome.setEditable(false);

		tipo = new JTextField(20);
		tipo.setEditable(false);

		localizacao = new JTextField(30);
		localizacao.setEditable(false);

		descricao = new JTextField(30);
		descricao.setEditable(false);

		valorLido = new JTextField(30);

		valorAtribuido = new JTextField(30);
		valorAtribuido.setEditable(false);

		setLayout(new GridLayout(6, 2));

		add(new JLabel("Nome:"));
		add(nome);

		add(new JLabel("Tipo:"));
		add(tipo);

		add(new JLabel("Localizacao"));// For GridLayout
		add(localizacao);

		add(new JLabel("Descricao"));// For GridLayout
		add(descricao);

		add(new JLabel("Valor Lido"));// For GridLayout
		add(valorLido);

		add(new JLabel("Valor Atribuido"));// For GridLayout
		add(valorAtribuido);

		new Thread(new Runnable() {

			@Override
			public void run() {
		
				try {
					iot = (IServidor) Naming.lookup("rmi://localhost:1099/iot");
					iot.registrarSensor(getSensor());
					
					while (true) {
						Thread.sleep(3000);
						binder.updateModel(getSensor());
						//joga no iot o lido.
						iot.registrarValorLido(sensor);
						
						//pega do iot o setado
						Integer i = iot.obtemValorAtribuido(getSensor());
						
						//seta na tela o valor setado
						getSensor().setValorAtribuido(i);
						binder.updateView(getSensor());
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
					System.exit(1);
				}
			}
		}).start();
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		binder = new AnnotatedBinder(this);
		binder.updateView(getSensor());
	}

	public Sensor getSensor() {
		return sensor;
	}

	public static void main(String[] args) {
		new TelaSensor(new Sensor(Tipo.LUZ_ON_OFF, Localizacao.QUARTO, "luz quarto", "aberta(1) ou fechada(0)")).setVisible(true);
	}

}