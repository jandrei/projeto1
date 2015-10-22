/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.servidor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 *tela de login do sistema 
 *
 */
public class LoginForm extends JDialog implements ActionListener {

    public LoginForm(JFrame frame) {
        super(frame);
        setTitle("Login");
        initComponents();
    }

    private void initComponents() {
        this.tfLogin = new JTextField(20);
        this.tfSenha = new JPasswordField(20);

        this.btnLogin = new JButton("Entrar");
        this.btnSair = new JButton("Cancelar");

        setLayout(new GridLayout(3, 2));

        this.add(new JLabel("Usuário:"));
        this.add(tfLogin);

        this.add(new JLabel("Senha:"));
        this.add(tfSenha);

        this.add(this.btnSair);
        this.add(this.btnLogin);

        btnLogin.addActionListener(this);
        this.getRootPane().setDefaultButton(btnLogin);
        btnSair.addActionListener(this);

        setLocationRelativeTo(null);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	//se o botao clicado for entrar tenta validar o login, senao clicou no cancelar e entao sai do sistema.
        if ("Entrar".equals(e.getActionCommand())) {
            //super login.
            if (tfLogin.getText().equals("senha")
                    && new String(tfSenha.getPassword()).equals("senha")){
                this.setVisible(false);
                new ServidorTela().setVisible(true);
                return;
            }
            JOptionPane.showMessageDialog(this, "Usuário e senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            System.exit(0);
        }
    }

    private JButton btnLogin;
    private JButton btnSair;

    private JTextField tfLogin;
    private JPasswordField tfSenha;

    public static void main(String[] args) {
        new LoginForm(null).setVisible(true);
    }
}
