package modelo;

import java.util.List;

public class Factura {
    private String id;
    private String cif;
    private List<String> items;
    private double total_precio;
    private char moneda;

    public Factura(String id, String cif, List<String> items, double total_precio, char moneda) {
        this.id = id;
        this.cif = cif;
        this.items = items;
        this.total_precio = total_precio;
        this.moneda = moneda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getTotal() {
        return String.format("%.2f %c", total_precio, moneda);
    }

    public double getTotal_precio() {
        return total_precio;
    }

    public char getMoneda() {
        return moneda;
    }

    private String itemsToString() {
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append(item + "\n\t");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Factura -> %s\n\tCIF: %s\n\tItems:\n\t%s\n\tTotal: %.2f€", this.cif, itemsToString(), getTotal());
    }
}
