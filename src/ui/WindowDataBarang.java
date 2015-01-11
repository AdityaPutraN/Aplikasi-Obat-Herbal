package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import system.*;

public class WindowDataBarang extends JFrame {
	private Core core;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tabel;

	public WindowDataBarang(Core core) {
		super("Data Barang");
		this.core = core;

		this.core = core;
		setResizable(false);
		setSize(480, 360);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		JTable tabel = new JTable(Operator.resultSetToTableModel(Operator
				.getListBarang(core.getConnection())));
		Operator.disableTableEdit(tabel);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.GREEN);
		JPanel pan = new JPanel();
		pan.setBackground(Color.GREEN);
		pan.setBounds(0, 0, 480, 320);
		pan.setLayout(new BorderLayout());
		pan.add(tabel, BorderLayout.CENTER);
		pan.add(tabel.getTableHeader(), BorderLayout.NORTH);
		pan.add(new JScrollPane(tabel), BorderLayout.CENTER);

		getContentPane().add(pan);
	}
}
