package objectes;

import javafx.beans.property.SimpleStringProperty;

public class Plat {
    private int id;
    private SimpleStringProperty nom;
    private SimpleStringProperty taula;
    private SimpleStringProperty ordre;
    private SimpleStringProperty categoria;
    private SimpleStringProperty horaArrivada;

    public Plat(int id, String nom, String taula, String ordre, String categoria, String horaArrivada){
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
        this.taula = new SimpleStringProperty(taula);
        this.ordre = new SimpleStringProperty(ordre);
        this.categoria = new SimpleStringProperty(categoria);
        this.horaArrivada = new SimpleStringProperty(horaArrivada);
    }

    public int getId(){ return id; }

    public String getTaula() {
        return taula.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getOrdre() {
        return ordre.get();
    }

    public String getCategoria() {
        return categoria.get();
    }

    public String getHora() {
        return horaArrivada.get();
    }
}