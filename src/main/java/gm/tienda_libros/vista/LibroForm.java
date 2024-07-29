package gm.tienda_libros.vista;

import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class LibroForm extends JFrame {
    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma();

    }

    /**
     * Crea la ventana donde se alojara toda la informacion
     */
    private void iniciarForma(){
        //Crea el panel que se mostrara en pantalla
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //Tamaño de la ventana
        setSize(900,700);
        //Esta clase proporciona una interfaz a nivel de alto nivel para diversas tareas de manejo de la interfaz de usuario y recursos gráficos.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth()/2);
        int y = (tamanioPantalla.height - getHeight()/2);
        setLocation(x,y);


    }

    /**
     * Personaliza la creacion de los componentes de los formularios
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
    this.tablaModeloLibros = new DefaultTableModel(0,5);
    String[] cabeceros = {"Id", "Libro","Autor","Precio","Existencias"};
    this.tablaModeloLibros.setColumnIdentifiers(cabeceros);
    // Instancias el objeto JTable
    this.tablaLibros = new JTable(tablaModeloLibros);
    listarLibros();



    }
    private void listarLibros(){
        //limpiar tabla
        tablaModeloLibros.setRowCount(0);
        //Obtener los libros
        var libros = libroServicio.listarLibros();
        //Para recorrer cada registro se hace a traves de la funcion de ForEach con la funcion de Lamda donde a traves de la variable libro crea un arreglo de
        // tipo Objet para llenar cada espacio con la informacion del libro con el metodo get
        libros.forEach((libro)->{
            Object[] renglonLbro = {
                    libro.getIdLibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            //Se agrega el registro del libro a cada una de las columnas segun la existencia en la base de datos
            this.tablaModeloLibros.addRow(renglonLbro);

        });

    }
}
