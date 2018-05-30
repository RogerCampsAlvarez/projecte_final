package objectes;

import javafx.beans.property.SimpleStringProperty;

public class Taula {
    private int id;
    public SimpleStringProperty nom;

    public Taula(int id, String nom){
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom.get();
    }
}
