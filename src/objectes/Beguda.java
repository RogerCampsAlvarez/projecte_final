package objectes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Objecte per guardar les begudes de les comandes
 */
public class Beguda {
    private int id;
    private SimpleStringProperty nom;
    private SimpleStringProperty taula;
    private SimpleStringProperty tipus;
    private SimpleStringProperty horaArrivada;

    public Beguda(int id, String nom, String taula, String tipus, String horaArrivada){
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
        this.taula = new SimpleStringProperty(taula);
        this.tipus = new SimpleStringProperty(tipus);
        this.horaArrivada = new SimpleStringProperty(horaArrivada);
    }

    public int getId(){ return id; }

    public String getTaula() {
        return taula.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getTipus() {
        return tipus.get();
    }

    public String getHora() {
        return horaArrivada.get();
    }
}