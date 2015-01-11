package ui;

import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import object.Barang;
import system.*;
import ui.listener.CustActionListener;
import ui.listener.CustKeyListener;

public class WindowFormBarang extends JFrame {
	private Core core;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField txtID, txtNama, txtIDSup, txtHarga, txtStok;
	private JTable tabel;
	private JLabel lblID, lblNama, lblIDSup, lblHarga, lblStok;

	private Vector<Barang> barang = new Vector<Barang>();
	private Vector<String> namaBarang = new Vector<String>();

	public WindowFormBarang(final Core core) {
		super("Formulir Barang");
		this.core = core;
		setResizable(false);

		setSize(810, 272);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(Color.GREEN);
		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);

		JMenu menuUser = new JMenu(
				core.getLoggedInUser().admin() ? " Admin " : " Kasir "
						+ core.getLoggedInUser().getUsername());
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));

		JMenu menuTrans = new JMenu("Transaksi");
		JMenuItem TransData = new JMenuItem("Data Transaksi");
		TransData.addActionListener(new CustActionListener(core, this,
				TransData, CustActionListener.SHOW_DATA_TRANSAKSI));
		/*
		 * JMenuItem miTransCetak = new JMenuItem("Cetak Transaksi");
		 */
		JMenu menuBarang = new JMenu("Barang");
		// JMenuItem miBarangData = new JMenuItem("Data Barang");
		/*
		 * miBarangData.addActionListener(new CustActionListener(core, this,
		 * miBarangData, CustActionListener.SHOW_DATA_BARANG));
		 */
		JMenuItem miBarangCetak = new JMenuItem("Cetak Barang");
		miBarangCetak.addActionListener(new CustActionListener(core, this,
				miBarangCetak, CustActionListener.CETAK_BARANG));
		menu.add(menuUser);
		menuUser.add(miLogOut);

		menu.add(menuTrans);
		// menuTrans.add(miTransCetak);
		menuTrans.add(TransData);
		menu.add(menuBarang);
		// menuBarang.add(miBarangData);
		menuBarang.add(miBarangCetak);

		ResultSet rs = Operator.getListBarang(core.getConnection());
		try {
			while (rs.next()) {
				barang.add(new Barang(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		tabel = new JTable(Operator.resultSetToTableModel(Operator
				.getListBarang(core.getConnection())));
		Operator.disableTableEdit(tabel);
		JPanel panTabel = new JPanel();

		panTabel.setLayout(new BorderLayout());
		panTabel.setBackground(Color.GREEN);
		panTabel.add(new JScrollPane(tabel), BorderLayout.CENTER);

		txtID = new JTextField();
		txtNama = new JTextField();
		txtIDSup = new JTextField();
		txtHarga = new JTextField();
		txtStok = new JTextField();

		txtID.setBounds(115, 10, 170, 20);
		txtNama.setBounds(115, 35, 170, 20);
		txtIDSup.setBounds(115, 60, 170, 20);
		txtHarga.setBounds(115, 85, 170, 20);
		txtHarga.addKeyListener(new CustKeyListener(core, this, txtHarga,
				CustKeyListener.NUMBER_ONLY));
		txtStok.setBounds(115, 110, 170, 20);
		txtStok.addKeyListener(new CustKeyListener(core, this, txtStok,
				CustKeyListener.NUMBER_ONLY));

		panTabel.setBounds(295, 10, 500, 200);
		lblID = new JLabel("ID Products");
		lblNama = new JLabel("Nama Products");
		lblIDSup = new JLabel("ID Supplier");
		lblHarga = new JLabel("Harga");
		lblStok = new JLabel("Stok");
		
		lblID.setBounds(10, 10, 100, 20);
		lblID.setHorizontalAlignment(JLabel.RIGHT);
		lblNama.setBounds(10, 35, 100, 20);
		lblNama.setHorizontalAlignment(JLabel.RIGHT);
		lblIDSup.setBounds(10, 60, 100, 20);
		lblIDSup.setHorizontalAlignment(JLabel.RIGHT);
		lblHarga.setBounds(10, 85, 100, 20);
		lblHarga.setHorizontalAlignment(JLabel.RIGHT);
		lblStok.setBounds(10, 110, 100, 20);
		lblStok.setHorizontalAlignment(JLabel.RIGHT);

		JButton buttonTambah = new JButton("Tambah");
		JButton buttonDelete = new JButton("Delete");

		buttonTambah.setBounds(205, 135, 80, 20);
		buttonTambah.addActionListener(new CustActionListener(core, this,tabel,
				buttonTambah, CustActionListener.TAMBAH_BARANG));
		buttonDelete.setBounds(115, 135, 80, 20);
		buttonDelete.addActionListener(new CustActionListener(core, this,tabel,
				buttonDelete, CustActionListener.HAPUS_BARANG));
		// Add Content
		container.add(txtID);
		container.add(txtNama);
		container.add(txtIDSup);
		container.add(txtHarga);
		container.add(txtStok);
		container.add(panTabel);
		container.add(lblID);
		container.add(lblNama);
		container.add(lblIDSup);
		container.add(lblHarga);
		container.add(lblStok);

		container.add(buttonDelete);
		container.add(buttonTambah);
	}

	public Vector<Barang> getListBarang() {
		return barang;
	}

	public Barang getSelectedBarang() {
		return barang.get(tabel.getSelectedRow());
	}

	public void submitToDB() {
		if (Operator.tambahBarang(getBarang(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data Telah Ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi Kesalahan");
		}
		
		((DefaultTableModel)tabel.getModel()).addRow(new Object[]{txtID.getText(),txtNama.getText(),txtIDSup.getText(),txtHarga.getText(),txtStok.getText()});

		txtID.setText("");
		txtNama.setText("");
		txtIDSup.setText("");
		txtHarga.setText("");
		txtStok.setText("");
	}

	public void resetForm() {
		txtID.setText("");
		txtNama.setText("");
		txtIDSup.setText("");
		txtHarga.setText("");
		txtStok.setText("");
		
		if (tabel.getSelectedRow() >= 0) {
			((DefaultTableModel) tabel.getModel())
					.removeRow(tabel.getSelectedRow());
		}
	}


	public Barang getBarang() {
		return new Barang(txtID.getText(), txtNama.getText(),txtIDSup.getText(),
				Integer.parseInt(txtHarga.getText()),Integer.parseInt(txtHarga.getText()));
	}
}
