package domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer")
    private int kaartNummer;
    @Column(name = "geldig_tot")
    private Date gelidgTot;
    private int klasse;
    private double saldo;

    @Transient
    private Reiziger reiziger;

    @Transient
    private List<Product> products = new ArrayList<>();

    public OVChipkaart(int kaartNummer, Date gelidgTot, int klasse, double saldo) {
        this.kaartNummer = kaartNummer;
        this.gelidgTot = gelidgTot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public OVChipkaart() {

    }

    public void addProduct(Product product){
        if (!products.contains(product)) {
            products.add(product);
            product.addOvChip(this);
        }
    }

    public void removeProduct(Product product){
        if (products.contains(product)) {
            products.remove(product);
            product.removeOvChip(this);
        }
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getGelidgTot() {
        return gelidgTot;
    }

    public void setGelidgTot(Date gelidgTot) {
        this.gelidgTot = gelidgTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setProducts(List<Product> products) {
        for (Product p : products) {
            addProduct(p);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OVChipkaart that = (OVChipkaart) o;
        return kaartNummer == that.kaartNummer;
    }

    @Override
    public String toString() {
        List<String> lst = new ArrayList<>();
        for (Product p : products) {
            lst.add(p.getProductNummer() + " " + p.getNaam() + " " + p.getBeschrijving() + " " + p.getPrijs());
        }
        return "domain.OVChipkaart{" +
                "#" + kaartNummer +
                " " + gelidgTot +
                " " + klasse +
                " $" + saldo +
                " " + lst +
                '}';
    }
}
