package gestionmapas;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import miventana.MiVentana;

/**
 *
 * @author RafaMar
 */
public class GestionMapas {

    private MiVentana miVentana;
    private TreeSet<Provincia> provinciasEspaña = new TreeSet();
    private TreeSet<Poblacion> pueblosEspaña = new TreeSet();

    public GestionMapas() {
        miVentana = new MiVentana("Búsqueda de Poblaciones");

        try {
            cargarProvinciasDeFichero("src/datos/provincias.txt");
            cargarPoblacionesDeFichero("src/datos/poblaciones.txt");
        } catch (IOException ex) {
            Logger.getLogger(GestionMapas.class.getName()).log(Level.SEVERE, null, ex);
        }
        insertarPoblacionesEnProvincias();
        miVentana.setJMenuBar(null);
        miVentana.setSize(460, 370);
        miVentana.setResizable(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        miVentana.setLocation(dim.width / 2 - miVentana.getSize().width / 2, dim.height / 2 - miVentana.getSize().height / 2);
        miVentana.setVisible(true);
    }

    private void cargarProvinciasDeFichero(String nombreFichero) throws IOException {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        String linea;
        try {
            fileReader = new FileReader(nombreFichero);
            bufferedReader = new BufferedReader(fileReader);
            Provincia provincia;
            while ((linea = bufferedReader.readLine()) != null) {

                String[] datosProvincia = linea.split(",");
                provincia = new Provincia(datosProvincia[2], Integer.parseInt(datosProvincia[1]));
                provinciasEspaña.add(provincia);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se puede abrir el fichero -> " + nombreFichero);
        }
    }

    private void cargarPoblacionesDeFichero(String nombreFichero) {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        String linea;
        try {
            fileReader = new FileReader(nombreFichero);
            bufferedReader = new BufferedReader(fileReader);
            Poblacion poblacion;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datosPoblacion = linea.split(",");
                poblacion = new Poblacion(datosPoblacion[2], Integer.parseInt(datosPoblacion[1]), Double.parseDouble(datosPoblacion[5]), Double.parseDouble(datosPoblacion[6]));
                pueblosEspaña.add(poblacion);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se puede abrir el fichero -> " + nombreFichero);
        } catch (IOException ex) {
            Logger.getLogger(GestionMapas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertarPoblacionesEnProvincias() {
        for (Poblacion poblacion : pueblosEspaña) {
            for (Provincia provincia : provinciasEspaña) {
                if (poblacion.getCodProvincia() == provincia.getIdProvincia()) {
                    provincia.getListaPoblaciones().add(poblacion);
                }
            }
        }

    }

    private void colocarInterfaz() {
        Container contentPane = miVentana.getContentPane();
        
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Búsqueda"));

        JPanel panelProvincias = new JPanel(new GridBagLayout());

        panelProvincias.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Búsqueda de Localidades por Provincia"));
        Provincia[] provincias = obtenerArrayProvincias();
        JLabel etiquetaProvincia = new JLabel("Provincias");
        JLabel etiquetaLocalidad = new JLabel("Localidades");

        JComboBox comboBoxProvincias = new JComboBox(provincias);
        comboBoxProvincias.setPreferredSize(new Dimension(175, 25));
        JComboBox comboBoxPoblaciones = new JComboBox();
        comboBoxPoblaciones.setPreferredSize(new Dimension(175, 25));

        GridBagConstraints gBConstraints = new GridBagConstraints();
        gBConstraints.anchor = GridBagConstraints.NORTHWEST;
        gBConstraints.gridx = 0;
        gBConstraints.gridy = 0;
        panelProvincias.add(etiquetaProvincia, gBConstraints);
        gBConstraints.gridx = 1;
        panelProvincias.add(comboBoxProvincias, gBConstraints);
        gBConstraints.insets = new Insets(10, 0, 10, 0);
        gBConstraints.gridx = 0;
        gBConstraints.gridy = 1;
        panelProvincias.add(etiquetaLocalidad, gBConstraints);
        gBConstraints.gridx = 1;
        panelProvincias.add(comboBoxPoblaciones, gBConstraints);
        comboBoxProvincias.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    TreeSet<Poblacion> treePoblaciones = new TreeSet();
                    for (Provincia provincia : provinciasEspaña) {
                        if (((Provincia) comboBoxProvincias.getSelectedItem()).getNombre().equals(provincia.getNombre())) {
                            treePoblaciones = provincia.getListaPoblaciones();
                        }
                    }

                    Poblacion[] poblaciones = obtenerArrayPoblaciones(treePoblaciones);
                    comboBoxPoblaciones.setModel(new DefaultComboBoxModel(poblaciones));
                    gBConstraints.gridx = 0;
                    gBConstraints.gridy = 1;
                    panelProvincias.add(etiquetaLocalidad, gBConstraints);
                    gBConstraints.gridx = 1;
                    panelProvincias.add(comboBoxPoblaciones, gBConstraints);
                }
            }
        });
        comboBoxPoblaciones.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    for (Poblacion poblacion : pueblosEspaña) {
                        if (((Poblacion) comboBoxPoblaciones.getSelectedItem()).getNombre().equals(poblacion.getNombre())) {
                            JOptionPane.showMessageDialog(null, poblacion.getNombre() + ": \n-Latitud-> "
                                    + poblacion.getLatitud() + " \n-Longitud-> "
                                    + poblacion.getLongitud(), "Localidad Seleccionada", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Búsqueda de Localidades de España"));

        JLabel etiquetaBúsqueda = new JLabel("Búsqueda");
        JTextField jTextBusqueda = new JTextField(12);
        JTable tablaLocal = new JTable();

        tablaLocal.setPreferredScrollableViewportSize(new Dimension(200, 100));
        jTextBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                Poblacion[] poblaciones = obtenerArrayPoblacionesEspaña();

                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Localidad", poblaciones);

                tablaLocal.setModel(tableModel);

                TableRowSorter<TableModel> tableRS = new TableRowSorter<TableModel>(tableModel);
                tableRS.setRowFilter(RowFilter.regexFilter(jTextBusqueda.getText(), 0));
                tablaLocal.setRowSorter(tableRS);

            }
        });

        tablaLocal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaLocal.getSelectedRow();
                Poblacion poblacion = (Poblacion) tablaLocal.getValueAt(fila, 0);
                JOptionPane.showMessageDialog(null, poblacion.getNombre() + ": \n-Latitud-> "
                        + poblacion.getLatitud() + " \n-Longitud-> "
                        + poblacion.getLongitud(), "Localidad Seleccionada", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panelBusqueda.add(etiquetaBúsqueda);
        panelBusqueda.add(jTextBusqueda);
        panelBusqueda.add(new JScrollPane(tablaLocal));
        
        panelPrincipal.add(panelProvincias, BorderLayout.CENTER);
        panelPrincipal.add(panelBusqueda, BorderLayout.SOUTH);
        
        panelBusqueda.setBackground(Color.getHSBColor(15, 15,15 ));
        panelProvincias.setBackground(Color.getHSBColor(15, 15,15 ));
        panelPrincipal.setBackground(Color.getHSBColor(15, 15,15 ));
        
        contentPane.add(panelPrincipal);
        miVentana.setVisible(true);

    }

    private Provincia[] obtenerArrayProvincias() {
        Provincia[] provincias = new Provincia[provinciasEspaña.size()];
        int i = 0;
        for (Provincia provincia : provinciasEspaña) {
            provincias[i] = provincia;
            i++;
        }
        return provincias;
    }

    public Poblacion[] obtenerArrayPoblacionesEspaña() {
        Poblacion[] poblaciones = new Poblacion[pueblosEspaña.size()];
        int i = 0;
        for (Poblacion poblacion : pueblosEspaña) {
            poblaciones[i] = poblacion;
            i++;
        }
        return poblaciones;
    }

    private Poblacion[] obtenerArrayPoblaciones(TreeSet<Poblacion> treePoblaciones) {
        Poblacion[] poblaciones = new Poblacion[treePoblaciones.size()];
        int i = 0;
        for (Poblacion poblacion : treePoblaciones) {
            poblaciones[i] = poblacion;
            i++;
        }
        return poblaciones;
    }

    public static void main(String[] args) throws IOException {
        GestionMapas gestion = new GestionMapas();
        gestion.colocarInterfaz();
    }

    public TreeSet<Provincia> getProvinciasEspaña() {
        return provinciasEspaña;
    }

    public void setProvinciasEspaña(TreeSet<Provincia> provinciasEspaña) {
        this.provinciasEspaña = provinciasEspaña;
    }

    public TreeSet<Poblacion> getPueblosEspaña() {
        return pueblosEspaña;
    }

    public void setPueblosEspaña(TreeSet<Poblacion> pueblosEspaña) {
        this.pueblosEspaña = pueblosEspaña;
    }

}
