
package gestionmapas;

import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author RafaMar
 */
public class Provincia implements Comparable{
    
    private TreeSet<Poblacion> listaPoblaciones = new TreeSet();
    
    private int idProvincia;
    
    private String nombre;

    public Provincia(String nombre, int idProvincia) {
        this.nombre = nombre;
        this.idProvincia = idProvincia;
        
    }

    public TreeSet<Poblacion> getListaPoblaciones() {
        return listaPoblaciones;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setListaPoblaciones(TreeSet<Poblacion> listaPoblaciones) {
        this.listaPoblaciones = listaPoblaciones;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Provincia other = (Provincia) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int compareTo(Object objeto) {
        Provincia provincia = (Provincia) objeto;
        return this.getNombre().compareToIgnoreCase(provincia.getNombre());
    }
    
}
