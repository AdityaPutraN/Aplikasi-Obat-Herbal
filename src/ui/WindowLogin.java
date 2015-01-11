package ui;

import java.awt.*;

import javax.swing.*;

import system.*;
import ui.listener.CustActionListener;

public class WindowLogin extends JFrame {

	final private Core core;

	private JButton btnLogin;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JLabel lblUser, lblPass;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public WindowLogin(Core core) {
		super("LOGIN");
		this.core = core;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 200);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setResizable(false);
		JLabel lblHeader = new JLabel("APLIKASI PENJUALAN TOKO OBAT HERBAL");
		lblHeader.setBounds(25,10,250,20);
		 
		container = this.getContentPane();
		container.setLayout(null);
		container.setBackground(Color.GREEN);
		btnLogin = new JButton("Login");
		txtUser = new JTextField(15);
		txtPass = new JPasswordField(15);
		lblUser = new JLabel("Username");
		lblPass = new JLabel("Password");

		lblUser.setHorizontalAlignment(JLabel.RIGHT);
		lblPass.setHorizontalAlignment(JLabel.RIGHT);

		lblUser.setBounds(30, 40, 60, 20);
		txtUser.setBounds(95, 40, 170, 20);
		lblPass.setBounds(30, 80, 60, 20);
		txtPass.setBounds(95, 80, 170, 20);
		btnLogin.setBounds(30, 115, 235, 25);

		btnLogin.addActionListener(new CustActionListener(core, this, btnLogin));
		container.add(lblHeader);
		container.add(lblUser);
		container.add(txtUser);
		container.add(lblPass);
		container.add(txtPass);
		container.add(btnLogin);
	}

	public String getUser() {
		return txtUser.getText();
	}

	public String getPass() {
		return txtPass.getText();
	}
}
