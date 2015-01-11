package object;

public class Barang {
	private String IDProduct, NamaProd, IDSupplier;
	private int harga, stok;
	
	public Barang(String idprod, String namaprod ,String idsupplier, int harga, int stok){
		this.IDProduct=idprod;
		this.NamaProd=namaprod;
		this.IDSupplier=idsupplier;
		this.harga=harga;
		this.stok=stok;
	}
	
	public Barang(String namaprod ,String idsupplier, int harga, int stok){
		this.NamaProd=namaprod;
		this.IDSupplier=idsupplier;
		this.harga=harga;
		this.stok=stok;
	}

	public String getIdproducts() {
		return IDProduct;
	}

	public String getNamaproducts() {
		return NamaProd;
	}

	public String getIdsupplier() {
		return IDSupplier;
	}
	
	public int getHarga() {
		return harga;
	}

	public int getStok() {
		return stok;
	}
}
