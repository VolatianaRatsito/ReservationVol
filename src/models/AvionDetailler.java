package models;

public class AvionDetailler {
    private int id;
    private Avion avion;
    private TypeSiege typeSiege; // Assurez-vous que cet attribut existe
    private int nombre_siege;
    
    public AvionDetailler(){}

   
    public AvionDetailler(int id, Avion avion ,TypeSiege typeSiege, int nombre_siege){
        this.id = id;
        this.avion = avion;
        this.typeSiege = typeSiege;
        this.nombre_siege =  nombre_siege;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }
    
    // Getter pour l'attribut typeSiege
    public TypeSiege getTypeSiege() {
        return typeSiege;
    }

    public void setTypeSiege(TypeSiege typeSiege) {
        this.typeSiege = typeSiege;
    }

    public int getNombre_siege(){
        return nombre_siege;
    }

    public void setNombre_siege(int nombre_siege){
        this.nombre_siege = nombre_siege;
    }
}
