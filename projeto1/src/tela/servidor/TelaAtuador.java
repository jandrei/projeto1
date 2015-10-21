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
import engine.tipos.Localizacao;
import engine.tipos.Tipo;
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

    @Bindable(field = "localizacao")
    private JComboBox cbLocalizacao;

    @Bindable(field = "tipo", formatter = TipoFormatter.class)
    private JComboBox cbTipo;
    private JButton btRegistrar;

    private Binder binder;

    private Atuador atuador;
    IServidor iot;// = (IServidor) Naming.lookup("rmi://localhost:1099/iot");

    public TelaAtuador(Atuador atuador) {
        super("AtuadorForm - " + atuador.getNome());

        this.atuador = atuador;

        nome = new JTextField(20);
        //nome.setEditable(false);

        tipo = new JTextField(20);
        //tipo.setEditable(false);

        localizacao = new JTextField(30);
        //localizacao.setEditable(false);

        descricao = new JTextField(30);
        //descricao.setEditable(false);

        valorParaAtuar = new JTextField(30);
        valorParaAtuar.setEditable(false);

        cbLocalizacao = new JComboBox(Localizacao.values());
        cbTipo = new JComboBox(Tipo.values());

        btRegistrar = new JButton("Registrar Atuador");

        setLayout(new GridLayout(7, 2));

        add(new JLabel("Nome:"));
        add(nome);

        add(new JLabel("Tipo:"));
        //add(tipo);
        add(cbTipo);

        //add(new JLabel("Localizacao"));// For GridLayout
        //add(localizacao);
        add(new JLabel("Localizacao"));// For GridLayout
        add(cbLocalizacao);

        add(new JLabel("Descricao"));// For GridLayout
        add(descricao);

        add(new JLabel("Valor Para Atuar"));// For GridLayout
        add(valorParaAtuar);
        
        add(new JLabel(""));
        add(btRegistrar);
        btRegistrar.addActionListener(this);

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    iot = (IServidor) Naming.lookup("rmi://localhost:1099/iot");
                    iot.registrarAtuador(getAtuador());

                    while (true) {
                        Thread.sleep(3000);
                        Atuador at = iot.obtemAtuador(getAtuador());
                        if (at != null) {
                            binder.updateView(at);
                        } else {
                            //System.exit(0);
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    //System.exit(1);
                }
            }
        }).start();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        binder = new AnnotatedBinder(this);
        binder.updateView(getAtuador());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (nome.getText().isEmpty()) {
            Mensagem.erro(this, "Informe o nome, campo obrigatório.");
            return;
        }
        try {
            System.out.println(getAtuador());
            //iot.registrarAtuador(getAtuador());
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public Atuador getAtuador() {
        return atuador;
    }

    public static void main(String[] args) {
        new TelaAtuador(new Atuador(Tipo.LUZ_ON_OFF, Localizacao.SALA, "Luz", "aberta(1) ou fechada(0)")).setVisible(true);
    }

}
