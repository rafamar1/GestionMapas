/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionmapas;

import java.util.Objects;

/**
 *
 * @author RafaMar
 */
public class Poblacion implements Comparable{
        
    private String nombre;
    
    private double latitud;
    
    private double longitud;
    
    private int codProvincia;
    
    
    public Poblacion(String nombrePoblacion,int codProvincia, double latitud ,double longitud) {
        this.nombre = nombrePoblacion;
        this.codProvincia = codProvincia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /*Este constructor solo es utilizado para comprobar la validez de las clases en el test*/
    public Poblacion() {
    }
    
    

 public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public int getCodProvincia() {
        return codProvincia;
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
    @Override
    public int compareTo(Object objeto) {
    Poblacion poblacion = (Poblacion) objeto;
    return this.nombre.compareToIgnoreCase(poblacion.nombre);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.nombre);
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
        final Poblacion other = (Poblacion) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
       
    
}
