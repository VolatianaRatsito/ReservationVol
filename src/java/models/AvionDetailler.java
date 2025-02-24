package models;

public class AvionDetailler {
    private int id;
    private Avion avion;
    private TypeSiege siege;
    private int nombre_siege;
    
    public AvionDetailler(){}

   
    public AvionDetailler(int id, Avion avion ,TypeSiege siege, int nombre_siege){
        this.id = id;
        this.avion = avion;
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
    
    public TypeSiege getSiege(){
        return siege;
    }

    public void setSiege(TypeSiege siege){
        this.siege = siege;
    }

    public int getNombre_siege(){
        return nombre_siege;
    }

    public void setNombre_siege(int nombre_siege){
        this.nombre_siege = nombre_siege;
    }
}
