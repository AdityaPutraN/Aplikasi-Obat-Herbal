package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import javax.swing.table.*;

import ui.*;
import object.*;

public class Core {

	final public static boolean GUI = true, CUI = false;
	final public static int PRODUCTS = 0, DETILTRANS = 1, TRANSAKSI = 2,
			PENGGUNA = 3;

	public WindowLogin formLogin = new WindowLogin(this);
	public WindowReport formReport;
	public WindowFormTransaksi formTrans;
	public WindowFormBarang formBarang;
	public WindowDataTransaksi formDataTrans;
	public WindowDataBarang formDataBarang;

	private Connection con;
	private User logUser;

	private static Calendar tgl = Calendar.getInstance();
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"E, dd MMMM yyyy");

	public Core(boolean GUI) {
		tesKoneksi();
		if (GUI) {
			formLogin.setVisible(true);
		} else {
			// CMD STYLE (~-,-)~ NOT IMPLEMENTED YET!!!
		}
	}

	private void tesKoneksi() {
		try {
			Class.forName(Config.Database_Driver);
			con = DriverManager.getConnection(Config.URL,Config.username, Config.password);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
	}

	public void printReport(Transaksi trns) {
		int ID = Operator.getLastIDTrans(con);

		String header = "\n"
				+ "\t\t        Herbal Shop"
				+ "\n\t\t       Surabaya "
				+ "\n\t \t\t  No. "
				+ ID
				+ "\nKasir : "
				+ logUser.getUsername()
				+ "\n=================================================================", data = "", footer = "\n"
				+ "\n---------------------------------------"
				+ "\nTotal : "
				+ trns.getTotalItem()
				+ " Item      "
				+ trns.getTotalHrg()
				+ "\n================================================================="
				+ "\nTgl " + trns.getTglAsString();
		for (int i = 0; i < trns.getDetilTransaksi().size(); i++) {
			DetilTransaksi dt = trns.getDetilTransaksi().get(i);
			data = data + "\n" + dt.getBarang().getIdproducts() + "\t"
					+ dt.getquantity() + "x\t" + dt.getquantity()
					* dt.getBarang().getHarga();
		}
		formReport = new WindowReport(this,
				new String[] { header, data, footer });
		formReport.setVisible(true);
	}

	public void printReport(Vector<Barang> brg) {
		String header = "\n"
				+ "\t\t    Herbal Shop"
				+ "\n\t\t    Surabaya"
				+ "\n\t   Stok barang tgl "
				+ getDateAsString()
				+ "\nKasir : "
				+ logUser.getUsername()
				+ "\n===============================================================", data = "", footer = "\n===============================================================";

		
		for (int i = 0; i < brg.size(); i++) {
			
			Barang _brg = brg.get(i);
			data = data + "\n  " + _brg.getIdproducts();
			for (int a = 0; a < 25 - _brg.getIdproducts().length(); a++) {
				data = data + " ";
			}
			
			data = data + _brg.getHarga();
			for (int a = 0; a < 10 - ("" + _brg.getHarga()).length(); a++) {
				data = data + " ";
			}
			
			data += _brg.getIdsupplier();
			for (int a = 0; a < 10 - _brg.getIdsupplier().length(); a++) {
				data = data + " ";
			}
			
			data += _brg.getHarga();
		}
		formReport = new WindowReport(this,
				new String[] { header, data, footer });
		formReport.setVisible(true);
	}

	public void login(User usr) {
		this.logUser = new User(usr);
		if (usr.admin()) {
			formBarang = new WindowFormBarang(this);
			formBarang.setVisible(true);
		} else {
			formTrans = new WindowFormTransaksi(this);
			formTrans.setVisible(true);
		}
	}

	public void logout() {
		if (logUser.admin()) {
			formBarang.dispose();
		} else {
			formTrans.dispose();
		}
		formLogin.dispose();
		formLogin = new WindowLogin(this);
		formLogin.setVisible(true);
		logUser = null;
	}

	public User getLoggedInUser() {
		return logUser;
	}

	public Connection getConnection() {
		return con;
	}

	public Date getDate() {
		return (Date) tgl.getTime();
	}

	public String getDateAsString() {
		return formatter.format(tgl.getTime());
	}

	public void reloadForm() {

	}
}
