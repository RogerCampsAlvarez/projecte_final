package objectes;

import javafx.beans.property.SimpleStringProperty;

public class CaixaDiaria {

    private SimpleStringProperty dia;
    private SimpleStringProperty caixa;

    public CaixaDiaria( String dia, String caixa ) {
        this.dia = new SimpleStringProperty( dia );
        this.caixa = new SimpleStringProperty( caixa );
    }

    public String getDia() {
        return dia.get();
    }

    public SimpleStringProperty diaProperty() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia.set(dia);
    }

    public String getCaixa() {
        return caixa.get();
    }

    public SimpleStringProperty caixaProperty() {
        return caixa;
    }

    public void setCaixa(String caixa) {
        this.caixa.set(caixa);
    }
}
