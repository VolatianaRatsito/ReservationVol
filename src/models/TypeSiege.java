package models;

public class TypeSiege {
    private int id;
    private String typeNom; // Correspond à la colonne "type_nom"
   
    public TypeSiege(){}

    // Constructeur
    public TypeSiege(int id, String typeNom) {
        this.id = id;
        this.typeNom = typeNom;
    }

    // Getter pour l'ID
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    // Getter pour le nom du type de siège
    public String getTypeNom() {
        return typeNom;
    }

    // Setter pour le nom du type de siège (si nécessaire)
    public void setTypeNom(String typeNom) {
        this.typeNom = typeNom;
    }
}
