package objectes;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Comanda {
    private int id_comanda;
    private Date dia;
    private SimpleStringProperty taula;
    private boolean finalitzada;


    public Comanda(int id_comanda, Date dia, String taula, Boolean finalitzada){
        this.id_comanda = id_comanda;
        this.dia = dia;
        this.taula = new SimpleStringProperty(taula);
        this.finalitzada = finalitzada;
    }

    public int getId_comanda() {
        return id_comanda;
    }

    public Date getDia() {
        return dia;
    }

    public String getId_taula() {
        return taula.get();
    }

    public boolean isFinalitzada() {
        return finalitzada;
    }
}