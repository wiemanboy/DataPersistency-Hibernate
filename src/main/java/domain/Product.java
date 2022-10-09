package domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    @Transient
    private List<OVChipkaart> ovChipkaartList = new ArrayList<>();

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }

    public void addOvChip(OVChipkaart ovChip){
        if (!ovChipkaartList.contains(ovChip)) {
            ovChipkaartList.add(ovChip);
            ovChip.addProduct(this);
        }
    }

    public void removeOvChip(OVChipkaart ovChip){
        if (!ovChipkaartList.contains(ovChip)) {
            ovChipkaartList.remove(ovChip);
            ovChip.removeProduct(this);
        }
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaartList() {
        return ovChipkaartList;
    }

    public void setOvChipkaartList(List<OVChipkaart> ovChipkaartList) {
        for (OVChipkaart o : ovChipkaartList) {
            addOvChip(o);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productNummer == product.productNummer;
    }

    @Override
    public String toString() {
        List<String> lst = new ArrayList<>();
        for (OVChipkaart o : ovChipkaartList) {
            lst.add(o.getKaartNummer() + " " + o.getKlasse() + " " + " " + o.getSaldo() + " " + o.getGelidgTot());
        }
        return "{" +
                "#" + productNummer +
                " " + naam +
                " " + beschrijving +
                " " + prijs +
                " " + lst +
                '}';
    }
}
