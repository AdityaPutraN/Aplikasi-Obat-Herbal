package object;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Transaksi {
	private int idtrans;
	private Vector<DetilTransaksi> detilTransaksi = new Vector<DetilTransaksi>();
	private Date tgl;
	private User user;
	private SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

	public Transaksi(int idtrans, Vector<DetilTransaksi> detilTransaksi, Date tgl,
			User user) {
		this.idtrans = idtrans;
		this.detilTransaksi = detilTransaksi;
		this.tgl = tgl;
		this.user = user;
	}

	public Transaksi(Date tgl, User user) {
		this.tgl = tgl;
		this.user = user;
	}

	public Transaksi(int idtrans) {
		this.idtrans = idtrans;
	}

	public int getId() {
		return idtrans;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return detilTransaksi;
	}

	public Date getTgl() {
		return tgl;
	}

	public String getTglAsString() {
		return format.format(tgl.getTime());
	}

	public User getUser() {
		return user;
	}

	public int getTotalItem() {
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getquantity();
		}
		return total;
	}

	public int getTotalHrg() {
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getBarang().getHarga()
					* detilTransaksi.get(i).getquantity();
		}
		return total;
	}

	public void addDetilTransaksi(DetilTransaksi dt) {
		detilTransaksi.add(dt);
	}
}
