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
import engine.Atuador;
import engine.Localizacao;
import engine.Tipo;
import formatter.LocalizacaoFormatter;
import formatter.TipoFormatter;

@Form(Atuador.class)
public class TelaAtuador extends JFrame implements ActionListener {

	@Bindable(field = "nome")
	private JTextField nome;

	@Bindable(field = "tipo", formatter = TipoFormatter.class)
	private JTextField tipo;

	@Bindable(field = "localizacao", formatter = LocalizacaoFormatter.class)
	private JTextField localizacao;

	@Bindable(field = "descricao")
	private JTextField descricao;

	@Bindable(field = "valorParaAtuar")
	private JTextField valorParaAtuar;

	private JComboBox cbLocalizacao;

	private JComboBox cbTipo;

	private JButton btRegistrar;

	private Binder binder;

	private Atuador atuador;
	IServidor iot;// = (IServidor) Naming.lookup("rmi://localhost:1099/iot");

	public TelaAtuador(Atuador atuador) {
		super("AtuadorForm - " + atuador.getNome());

		this.atuador = atuador;

		nome = new JTextField(20);
		// nome.setEditable(false);

		tipo = new JTextField(20);
		// tipo.setEditable(false);

		localizacao = new JTextField(30);
		// localizacao.setEditable(false);

		descricao = new JTextField(30);
		// descricao.setEditable(false);

		valorParaAtuar = new JTextField(30);
		valorParaAtuar.setEditable(false);

		cbLocalizacao = new JComboBox(Localizacao.values());

		cbTipo = new JComboBox(Tipo.values());

		btRegistrar = new JButton("Registrar Atuador");
		getRootPane().setDefaultButton(btRegistrar);
		
		setLayout(new GridLayout(7, 2));

		add(new JLabel("Nome:"));
		add(nome);

		add(new JLabel("Tipo:"));
		// add(tipo);
		add(cbTipo);

		// add(new JLabel("Localizacao"));// For GridLayout
		// add(localizacao);
		add(new JLabel("Localizacao"));// For GridLayout
		add(cbLocalizacao);

		add(new JLabel("Descricao"));// For GridLayout
		add(descricao);

		add(new JLabel("Valor Para Atuar"));// For GridLayout
		add(valorParaAtuar);

		add(new JLabel(""));
		add(btRegistrar);
		btRegistrar.addActionListener(this);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		binder = new AnnotatedBinder(this);
		binder.updateView(getAtuador());

		cbLocalizacao.setSelectedItem(getAtuador().getLocalizacao());
		cbTipo.setSelectedItem(getAtuador().getTipo());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		binder.updateModel(getAtuador());
		
		if (nome.getText().isEmpty()) {
			Mensagem.erro(this, "Informe o nome, campo obrigatório.");
			return;
		}
		try {
			getAtuador().setLocalizacao((Localizacao) cbLocalizacao.getSelectedItem());
			getAtuador().setTipo((Tipo) cbTipo.getSelectedItem());

			System.out.println("Registrando = " + getAtuador());
			iot = (IServidor) Naming.lookup("rmi://localhost:1099/iot");
			iot.registrarAtuador(getAtuador());
			
			startMonitorValores();

			this.nome.setEditable(false);;
			this.tipo.setEditable(false);;
			this.localizacao.setEditable(false);;
			this.descricao.setEditable(false);;
			this.valorParaAtuar.setEditable(false);;
			this.cbLocalizacao.setEnabled(false);;
			this.cbTipo.setEnabled(false);;
			this.btRegistrar.setEnabled(false);;
			
		} catch (Exception erro) {
			Mensagem.erro(this, erro.getMessage());
		}
	}
	
	private void startMonitorValores(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(3000);
						Atuador at = iot.obtemAtuador(getAtuador());
						if (at != null) {
							valorParaAtuar.setText(at.getValorParaAtuar()+"");
						} else {
							// System.exit(0);
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
					// System.exit(1);
				}
			}
		}).start();
	}

	public Atuador getAtuador() {
		return atuador;
	}

	public static void main(String[] args) {
		new TelaAtuador(new Atuador(Tipo.LUZ_ON_OFF, Localizacao.SALA, "Luz", "Insira uma descrição aqui."))
				.setVisible(true);
	}

}
