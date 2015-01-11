package ui.listener;

import java.awt.event.*;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

//import com.sun.org.apache.bcel.internal.generic.FMUL;

import object.*;

import system.*;
import ui.*;

public class CustActionListener implements ActionListener {

	public final static int LOGIN = 0, FORM_TRANSAKSI_ADDBARANG = 1,
			FORM_TRANSAKSI_SUBMIT = 2, FORM_TRANSAKSI_SELECTITEM = 3,
			LOGOUT = 4, SHOW_DATA_BARANG = 5, SHOW_DATA_TRANSAKSI = 6,
			CETAK_BARANG = 7, HAPUS_BARANG = 8, TAMBAH_BARANG = 9,
			HAPUS_TRANS = 10;
	private Core core;

	private JFrame jf;
	private WindowLogin formLogin;
	private WindowReport formReport;
	private WindowFormBarang formBarang;
	private WindowFormTransaksi formTrans;
	private WindowDataTransaksi formDataTrans;
	private WindowDataBarang formDataBarang;

	private JButton btn;
	private JComboBox cb;
	private JMenuItem mi;
	private JTable tabel;

	private JTextField txt;

	private int mode;

	public CustActionListener(Core core, WindowLogin frm, JButton btn) {
		this.core = core;
		this.formLogin = frm;
		this.btn = btn;
		mode = LOGIN;
	}

	public CustActionListener(Core core, WindowFormTransaksi frm, JButton btn) {
		this.core = core;
		this.formTrans = frm;
		this.btn = btn;
		this.mode = FORM_TRANSAKSI_SUBMIT;
	}

	public CustActionListener(WindowFormTransaksi frm, JButton btn) {
		this.formTrans = frm;
		this.btn = btn;
		this.mode = FORM_TRANSAKSI_ADDBARANG;
	}

	public CustActionListener(WindowFormTransaksi frm, JComboBox cb) {
		this.formTrans = frm;
		this.cb = cb;
		mode = FORM_TRANSAKSI_SELECTITEM;
	}

	public CustActionListener(Core core, JFrame jf, JMenuItem mi, int mode) {
		this.core = core;
		this.jf = jf;
		this.mi = mi;
		this.mode = mode;
	}

	public CustActionListener(Core core, WindowFormBarang frm, JMenuItem mi,
			int mode) {
		this.core = core;
		this.formBarang = frm;
		this.mi = mi;
		this.mode = mode;
	}

	public CustActionListener(Core core, WindowFormBarang frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.formBarang = frm;
		this.btn = btn;
		this.tabel = tbl;
		this.mode = mode;
	}

	public CustActionListener(Core core, WindowDataTransaksi frm, JTable tbl,
			JButton btn, int mode) 
	{
		this.core = core;
		this.tabel = tbl;
		this.formDataTrans = frm;
		this.btn = btn;
		this.mode = mode;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (mode) {
		case LOGIN:
			User user = Operator.checkLogin(new User(formLogin.getUser(),
					formLogin.getPass(), false), core.getConnection());
			if (user == null) {
				JOptionPane.showMessageDialog(formLogin,
						"Username atau password tidak tepat");
			} else {
				formLogin.setVisible(false);
				core.login(user);
			}
			break;
		case FORM_TRANSAKSI_SELECTITEM:
			int index = cb.getSelectedIndex();
			formTrans.fillFormByIndex(index);
			break;
		case FORM_TRANSAKSI_ADDBARANG:
			formTrans.addBarangToTable(new DetilTransaksi(formTrans
					.getSelectedBarang(), formTrans.getQtyBarang()));
			break;
		case FORM_TRANSAKSI_SUBMIT:
			formTrans.submitToDB();
			break;
		case LOGOUT:
			core.logout();
			break;
		case SHOW_DATA_BARANG:
			if (core.formDataBarang == null) {
			} else {
				core.formDataBarang.dispose();
			}
			core.formDataBarang = new WindowDataBarang(core);
			core.formDataBarang.setVisible(true);
			break;
		case SHOW_DATA_TRANSAKSI:
			if (core.formDataTrans == null) {
			} else {
				core.formDataTrans.dispose();
			}
			core.formDataTrans = new WindowDataTransaksi(core);
			core.formDataTrans.setVisible(true);
			break;
		case CETAK_BARANG:
			core.printReport(formBarang.getListBarang());
			break;
		case TAMBAH_BARANG:
			formBarang.submitToDB();
			
			break;
		case HAPUS_BARANG:
			if(tabel == null){
			}else{
				if (Operator.hapusBarang(formBarang.getSelectedBarang(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(formBarang,
							"Data barang dihapus");
				}
				formBarang.resetForm();
			}
			break;
		case HAPUS_TRANS:
			if(tabel == null){
			}else{
				if (Operator.hapusTransaksi(formDataTrans.getTransaksi(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(formDataTrans,
							"Data transaksi dihapus");
				}
				formDataTrans.resetForm();
			}
			break;
		}

	}
}
