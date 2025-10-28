package pojos;

import java.io.Serializable;

public class Serie implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nombre;
    private int repeticiones;
    private int tiempoSerie;
    private int tiempoDescanso;
    private String fotoUrl;
    
    public Serie() {
    	super();
    }
    
    public Serie(String nombre, int repeticiones, int tiempoSerie, int tiempoDescanso, String fotoUrl) {
        this.nombre = nombre;
        this.repeticiones = repeticiones;
        this.tiempoSerie = tiempoSerie;
        this.tiempoDescanso = tiempoDescanso;
        this.fotoUrl = fotoUrl;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getRepeticiones() {
        return repeticiones;
    }
    
    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
    
    public int getTiempoSerie() {
        return tiempoSerie;
    }
    
    public void setTiempoSerie(int tiempoSerie) {
        this.tiempoSerie = tiempoSerie;
    }
    
    public int getTiempoDescanso() {
        return tiempoDescanso;
    }
    
    public void setTiempoDescanso(int tiempoDescanso) {
        this.tiempoDescanso = tiempoDescanso;
    }
    
    public String getFotoUrl() {
        return fotoUrl;
    }
    
    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
    
    @Override
    public String toString() {
        return "Serie [nombre=" + nombre + ", repeticiones=" + repeticiones + ", tiempoSerie=" + tiempoSerie
                + ", tiempoDescanso=" + tiempoDescanso + ", fotoUrl=" + fotoUrl + "]";
    }
}