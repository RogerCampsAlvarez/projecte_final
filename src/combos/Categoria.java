package combos;

/**
 * Classe per obtenir els camps del combos de la categoria dels aliments
 */
public class Categoria {
    String[] categoria_aliments = {"Carn", "Peix", "Pasta", "Sopa", "Verdura", "Amanida", "Postres"};

    public Categoria(){}

    public String[] getCategoria(){return categoria_aliments;}
}
