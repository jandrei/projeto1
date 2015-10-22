package tela.servidor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(200, 200, 600, 200);
		frame.setTitle("Status bar simulator");

		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());

		StatusBar statusBar = new StatusBar();
		contentPane.add(statusBar, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public StatusBar() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(10, 23));

		setBorder(BorderFactory.createLoweredBevelBorder());
		setBackground(SystemColor.control);
	}
}