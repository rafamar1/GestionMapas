/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import gestionmapas.GestionMapas;
import gestionmapas.Poblacion;
import gestionmapas.Provincia;
import java.util.TreeSet;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RafaMar
 */
public class TestBusqueda {
    
    public TestBusqueda() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void devuelveRondaCuandoBuscoRonda(){
        
        GestionMapas gestionMapa = new GestionMapas();
        String filtro ="Ronda";
        String[] shouldReturn ={"Ronda"};
        
        Poblacion[] poblaciones = gestionMapa.obtenerArrayPoblacionesEspaña();
        
        JTable tablaLocal = new JTable();
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Localidad", poblaciones);

        tablaLocal.setModel(tableModel);

        TableRowSorter<TableModel> tableRS = new TableRowSorter<TableModel>(tableModel);
        tableRS.setRowFilter(RowFilter.regexFilter(filtro, 0));
        tablaLocal.setRowSorter(tableRS);
        int cantidad = tablaLocal.getRowCount();
        String[] resultadosBusqueda = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultadosBusqueda[i] = tablaLocal.getValueAt(i, 0).toString();
            System.out.println(resultadosBusqueda[i]);
        }
        
        System.out.println("Resultados de la Busqueda: "+cantidad);
        assertEquals(shouldReturn, resultadosBusqueda);
        
    }
    
    @Test
    public void devuelve3PueblosCuandoBuscoSevi(){
        
        GestionMapas gestionMapa = new GestionMapas();
        String filtro ="Sevi";
        String[] shouldReturn ={"Sevilla","Sevilla la Nueva","Sevilleja de la Jara"};
        
        Poblacion[] poblaciones = gestionMapa.obtenerArrayPoblacionesEspaña();
        
        JTable tablaLocal = new JTable();
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Localidad", poblaciones);

        tablaLocal.setModel(tableModel);

        TableRowSorter<TableModel> tableRS = new TableRowSorter<TableModel>(tableModel);
        tableRS.setRowFilter(RowFilter.regexFilter(filtro, 0));
        tablaLocal.setRowSorter(tableRS);
        int cantidad = tablaLocal.getRowCount();
        String[] resultadosBusqueda = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultadosBusqueda[i] = tablaLocal.getValueAt(i, 0).toString();
        }
        assertEquals(shouldReturn, resultadosBusqueda);
        
    }
    
    @Test
    public void localidadEspañaIgualLocalidadProvincia(){
        GestionMapas gestionMapa = new GestionMapas();
        TreeSet<Provincia> provincias  = gestionMapa.getProvinciasEspaña();
        TreeSet<Poblacion> poblacionesEspaña = gestionMapa.getPueblosEspaña();
        
        Poblacion beguesEspaña = new Poblacion();
        Poblacion beguesProvincia = new Poblacion();
        /*Buscamos Begues dentro de la Provincia de Barcelona*/
        for (Provincia provincia : provincias) {
            if (provincia.getNombre().equalsIgnoreCase("Barcelona")){
                TreeSet<Poblacion> poblacionesBarcelona = provincia.getListaPoblaciones();
                for (Poblacion poblacion : poblacionesBarcelona) {
                    if (poblacion.getNombre().equalsIgnoreCase("Begues")){
                        beguesProvincia = new Poblacion(poblacion.getNombre(), poblacion.getCodProvincia(), poblacion.getLatitud(),poblacion.getLongitud());
                        
                    }
                }
            }
        }
        
        /*Buscamos Begues dentro de todas las localidades de España*/
        for (Poblacion poblacion : poblacionesEspaña) {
            if (poblacion.getNombre().equalsIgnoreCase("Begues")){
                        beguesEspaña = new Poblacion(poblacion.getNombre(), poblacion.getCodProvincia(), poblacion.getLatitud(),poblacion.getLongitud());
                    }
        }
        System.out.println("**Test de Localidades**");
        System.out.println("Localidad en España: "+beguesEspaña.getNombre());
        System.out.println("Localidad en Provincia: "+beguesProvincia.getNombre());
        assertEquals(beguesProvincia, beguesEspaña);
    }
}
