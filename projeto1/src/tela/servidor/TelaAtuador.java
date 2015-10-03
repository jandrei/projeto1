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
import comum.tipos.Localizacao;
import comum.tipos.Tipo;
import engine.Atuador;
import engine.Sensor;

@Form(Atuador.class)
public class TelaAtuador extends JFrame {

	@Bindable(field = "nome")
	private JTextField nome;

	@Bindable(field = "tipo", formatter = TipoFormatter.class)
	private JTextField tipo;

	@Bindable(field = "localizacao",formatter=LocalizacaoFormatter.class)
	private JTextField localizacao;

	@Bindable(field = "descricao")
	private JTextField descricao;

	@Bindable(field = "valorParaAtuar", formatter = IntFormatter.class)
	private JTextField valorParaAtuar;

	private Binder binder;

	private Atuador atuador;
	IServidor iot;// = (IServidor) Naming.lookup("rmi://localhost:1099/iot");

	public TelaAtuador(Atuador atuador) {
		super("AtuadorForm - "+atuador.getNome());

		this.atuador = atuador;

		nome = new JTextField(20);
		nome.setEditable(false);

		tipo = new JTextField(20);
		tipo.setEditable(false);

		localizacao = new JTextField(30);
		localizacao.setEditable(false);

		descricao = new JTextField(30);
		descricao.setEditable(false);

		valorParaAtuar= new JTextField(30);


		setLayout(new GridLayout(6, 2));

		add(new JLabel("Nome:"));
		add(nome);

		add(new JLabel("Tipo:"));
		add(tipo);

		add(new JLabel("Localizacao"));// For GridLayout
		add(localizacao);

		add(new JLabel("Descricao"));// For GridLayout
		add(descricao);

		add(new JLabel("Valor Para Atuar"));// For GridLayout
		add(valorParaAtuar);

		new Thread(new Runnable() {

			@Override
			public void run() {
		
				try {
					iot = (IServidor) Naming.lookup("rmi://localhost:1099/iot");
					iot.registrarAtuador(getAtuador());
					
					while (true) {
						Thread.sleep(3000);
						binder.updateModel(getAtuador());

						iot.atuar(getAtuador());
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
		binder.updateView(getAtuador());
	}

	public Atuador getAtuador() {
		return atuador;
	}

	public static void main(String[] args) {
		new TelaAtuador(new Atuador(Tipo.LUZ_ON_OFF, Localizacao.QUARTO, "Luz", "aberta(1) ou fechada(0)")).setVisible(true);
	}

}