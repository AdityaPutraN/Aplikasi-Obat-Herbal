package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import object.Barang;
import object.DetilTransaksi;
import object.Transaksi;
import object.User;

import system.*;
import ui.listener.CustActionListener;
import ui.listener.CustKeyListener;

public class WindowFormTransaksi extends JFrame {

	final int TGL = 0, KASIR = 1, BARANG = 2, HARGA = 3, JUMLAH = 4;

	private Core core;

	private User user;
	private Transaksi t;

	private JPanel panLeft, panRight, panTable, panGrand;
	private JTextField txtTgl, txtKasir, txtHarga, txtJumlah;
	private JLabel l[] = new JLabel[5];
	private JComboBox cb;
	private JButton btnTambahBarang, btnTambahTransaksi;
	private JTable tabel;

	private DefaultTableModel model;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Vector<String> namaBarang = new Vector<String>();
	private Vector<Barang> barang = new Vector<Barang>();

	public WindowFormTransaksi(Core core) {
		super("Formulir Transaksi - " + core.getDateAsString());
		this.core = core;
		this.user = core.getLoggedInUser();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(720, 400);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setLayout(null);
		container = this.getContentPane();
		container.setBackground(Color.GREEN);
		model = new DefaultTableModel();
		model.addColumn("Nama Item");
		model.addColumn("Jumlah Item");
		model.addColumn("Total Harga");
		tabel = new JTable(model);
		Operator.disableTableEdit(tabel);
		ResultSet rs = Operator.getListBarang(core.getConnection());
		namaBarang.removeAllElements();
		barang.removeAllElements();
		try {
			while (rs.next()) {
				barang.add(new Barang(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getInt(5)));
				if (barang.lastElement().getHarga() > 0)
					namaBarang.add(barang.lastElement().getNamaproducts());
				else
					barang.removeElement(barang.lastElement());
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		cb = new JComboBox(namaBarang);
		txtTgl = new JTextField(core.getDateAsString());
		txtKasir = new JTextField(user.getUsername());
		txtJumlah = new JTextField();
		txtHarga = new JTextField();
		fillFormByIndex(cb.getSelectedIndex());

		panTable = new JPanel(new BorderLayout());
		panTable.setBounds(275, 0, 440, 300);
		panTable.setBackground(Color.GREEN);
		panGrand = new JPanel(null);
		panGrand.setBounds(275, 300, 440, 100);
		panLeft = new JPanel(null);
		panLeft.setBounds(0, 0, 275, 400);

		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);

		JMenu menuUser = new JMenu(
				core.getLoggedInUser().admin() ? "Supervisor " : "Kasir "
						+ core.getLoggedInUser().getUsername());
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));

		/*
		 * JMenu menuTrans = new JMenu("Transaksi"); JMenuItem miTransData = new
		 * JMenuItem("Data Transaksi"); JMenuItem miTransCetak = new
		 * JMenuItem("Cetak Transaksi");
		 */
		JMenu menuBarang = new JMenu("Barang");
		JMenuItem miBarangData = new JMenuItem("Data Barang");
		miBarangData.addActionListener(new CustActionListener(core, this,
				miBarangData, CustActionListener.SHOW_DATA_BARANG));
		// JMenuItem miBarangCetak = new JMenuItem("Cetak Barang");

		menu.add(menuUser);
		menuUser.add(miLogOut);

		/*
		 * menu.add(menuTrans); menuTrans.add(miTransData);
		 * menuTrans.add(miTransCetak);
		 */
		menu.add(menuBarang);
		menuBarang.add(miBarangData);
		// menuBarang.add(miBarangCetak);

		l[TGL] = new JLabel("Tanggal");
		l[KASIR] = new JLabel("Nama Kasir");
		l[BARANG] = new JLabel("Nama Barang ");
		l[HARGA] = new JLabel("Harga Rp.");
		l[JUMLAH] = new JLabel("Jumlah Item");

		txtTgl.setEnabled(false);
		txtKasir.setEnabled(false);
		txtHarga.setEnabled(false);
		txtTgl.setBounds(95, 10, 170, 20);
		txtKasir.setBounds(95, 35, 170, 20);
		cb.setBounds(95, 60, 170, 20);
		txtHarga.setBounds(95, 85, 170, 20);
		txtJumlah.setBounds(95, 110, 170, 20);

		l[TGL].setBounds(10, 10, 80, 20);
		l[KASIR].setBounds(10, 35, 80, 20);
		l[BARANG].setBounds(10, 60, 80, 20);
		l[HARGA].setBounds(10, 85, 80, 20);
		l[JUMLAH].setBounds(10, 110, 80, 20);

		btnTambahBarang = new JButton("Tambah Barang");
		btnTambahBarang.setBounds(105, 155, 140, 20);
		btnTambahBarang.addActionListener(new CustActionListener(this,
				btnTambahBarang));
		btnTambahTransaksi = new JButton("Selesai & Print");
		btnTambahTransaksi.setBounds(165, 10, 120, 20);
		btnTambahTransaksi.addActionListener(new CustActionListener(core, this,
				btnTambahTransaksi));
		txtJumlah.addKeyListener(new CustKeyListener(this, txtJumlah,
				CustKeyListener.NUMBER_ONLY));
		txtJumlah.addKeyListener(new CustKeyListener(this, txtJumlah,
				CustKeyListener.ON_STOCK));
		cb.addActionListener(new CustActionListener(this, cb));
		panLeft.add(cb);
		panLeft.add(txtTgl);
		panLeft.add(txtKasir);
		panLeft.add(txtHarga);
		panLeft.add(txtJumlah);
		for (int i = 0; i < l.length; i++) {
			l[i].setHorizontalAlignment(JLabel.RIGHT);
			panLeft.add(l[i]);
		}
		panTable.add((JTableHeader) tabel.getTableHeader(), BorderLayout.NORTH);
		panTable.add(new JScrollPane(tabel), BorderLayout.CENTER);
		panGrand.add(btnTambahTransaksi);
		panLeft.add(btnTambahBarang);

		container.add(panLeft);
		container.add(panTable);
		container.add(panGrand);

		resetForm();
	}

	public void fillFormByIndex(int index) {
		txtJumlah.setText("1");
		txtHarga.setText(barang.get(index).getHarga()
				* Integer.parseInt(txtJumlah.getText()) + "");
	}

	public void resetForm() {
		int row = tabel.getRowCount() - 1;
		for (int i = row; i >= 0; i--)
			((DefaultTableModel) tabel.getModel()).removeRow(i);
		t = new Transaksi(core.getDate(), user);
	}

	public void addBarangToTable(DetilTransaksi dt) {
		for (int i = 0; i < tabel.getRowCount(); i++) {
			// test apakah sudah ada
		}
		model.addRow(new String[] { dt.getBarang().getIdproducts(),
				dt.getquantity() + "",
				dt.getBarang().getHarga() * dt.getquantity() + "" });
		t.addDetilTransaksi(dt);
	}

	public Vector<Barang> getListBarang() {
		return barang;
	}

	public Barang getSelectedBarang() {
		return barang.get(cb.getSelectedIndex());
	}

	public int getQtyBarang() {
		return Integer.parseInt(txtJumlah.getText());
	}

	public Transaksi getTransaksi() {
		return t;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return t.getDetilTransaksi();
	}

	public void submitToDB() {
		if (Operator.tambahTransaksi(getTransaksi(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		if (JOptionPane.showConfirmDialog(this, "Cetak transaksi?", "",
				JOptionPane.YES_NO_OPTION) == 0) {
			core.printReport(t);
		}
		resetForm();
	}
}
