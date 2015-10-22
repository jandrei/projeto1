/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela.servidor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * Facilita imprimir mensagens para o usuário
 */
public class Mensagem {

    public static void erro(JFrame cpm, String mensagem) {
        JOptionPane.showMessageDialog(cpm, mensagem, "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void aviso(JFrame cpm, String mensagem) {
        JOptionPane.showMessageDialog(cpm, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

}
