package models;

public class TypeSiege {
    private int id;
    private String types;

    // Constructeurs
    public TypeSiege() {}

    public TypeSiege(int id, String types) {
        this.id = id;
        this.types = types;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    // Méthode toString pour afficher l'objet
    @Override
    public String toString() {
        return "TypeSiege{id=" + id + ", types='" + types + "'}";
    }
}
