package objectes;

import javafx.beans.property.SimpleStringProperty;

public class Aliments {
    private int id;
    private SimpleStringProperty nom;
    private SimpleStringProperty descripcio;
    private SimpleStringProperty preu;
    private SimpleStringProperty ordre;
    private SimpleStringProperty categoria;

    public Aliments(int id, String nom, String descripcio, String preu, String ordre, String categoria){
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
        this.descripcio = new SimpleStringProperty(descripcio);
        this.preu = new SimpleStringProperty(preu);
        this.ordre = new SimpleStringProperty(ordre);
        this.categoria = new SimpleStringProperty(categoria);
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom.get();
    }

    public String getDescripcio() {
        return descripcio.get();
    }

    public String getPreu() {
        return preu.get();
    }

    public String getOrdre() {
        return ordre.get();
    }

    public String getCategoria() {
        return categoria.get();
    }
}