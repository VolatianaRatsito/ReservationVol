package models;

public class VilleDesservie {
    private int id;
    private String ville;

    public VilleDesservie(int id, String ville){
        this.id = id;
        this.ville = ville;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
