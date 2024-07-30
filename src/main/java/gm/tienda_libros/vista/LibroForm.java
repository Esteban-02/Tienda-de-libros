package gm.tienda_libros.vista;

import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class LibroForm extends JFrame {
    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JTextField idTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;

    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma();
        agregarButton.addActionListener(e -> agregarLibro());
        modificarButton.addActionListener(e -> modificarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
                
            }
        });


    }

    /**
     * Selecciona un registro en la tabla para ser eliminado de la tabla y la base de datos
     */
    private void eliminarLibro() {
        var renglon = tablaLibros.getSelectedRow();
        if (renglon==-1){
            mostrarMensaje("Seleccione un libro");
            return;
        }

        int  idLibro = Integer.parseInt(idTexto.getText());
        String nombreLibro = libroTexto.getText();
        String autor = autorTexto.getText();
        double precio = Double.parseDouble(precioTexto.getText());
        int existencias = Integer.parseInt(existenciasTexto.getText());
        this.libroServicio.eliminarLibro(new Libro(idLibro,nombreLibro,autor,precio,existencias));
        mostrarMensaje("Libro eliminado");
        limpiarFormulario();
        listarLibros();
    }


    /**
     * Se selecciona un libro que esta en la lista y se muestra en los textField para realizar cambios o solamente para mostrar la informacion
     */
    private void cargarLibroSeleccionado(){
        // los indices de las columnas inician en 0
        var renglon = tablaLibros.getSelectedRow();
        if (renglon!= -1){ // regresa -1 si no se selecciono un registro
            //Se selecciona un libro, el renglon que se esta seleccionando y con el valor 0 se hace referencia a la primera columna
            String idLibro = tablaLibros.getModel().getValueAt(renglon, 0).toString();
            idTexto.setText(idLibro);
            String nombreLibro = tablaLibros.getModel().getValueAt(renglon,1).toString();
            libroTexto.setText(nombreLibro);
            String autor = tablaLibros.getModel().getValueAt(renglon,2).toString();
            autorTexto.setText(autor);
            String precio = tablaLibros.getModel().getValueAt(renglon,3).toString();
            precioTexto.setText(precio);
            String existencias = tablaLibros.getModel().getValueAt(renglon,4).toString();
            existenciasTexto.setText(existencias);

        }
    }

    /**
     * Se agrega un nuevo libro a la base de datos y a la lista
     */
    private void agregarLibro() {
        //leer los valores del formulario
        if (libroTexto.getText().equals("")){
             mostrarMensaje("Proporciona el nombre del libro");
             libroTexto.requestFocusInWindow();
             return;
        }
        String nombreLibro = libroTexto.getText();
        String autor = autorTexto.getText();
        double precio = Double.parseDouble(precioTexto.getText());
        int existencias= Integer.parseInt(existenciasTexto.getText());
        // Crear el objeto libro
        Libro libro = new Libro();
        libro.setNombreLibro(nombreLibro);
        libro.setAutor(autor);
        libro.setPrecio(precio);
        libro.setExistencias(existencias);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se agrego el libro");
        limpiarFormulario();
        listarLibros();
    }

    /**
     * Selecciona un registro de un libro para luego ser modificado en la base de datos y en la tabla de libros
     */
    private void modificarLibro(){

        if (this.idTexto.getText().equals("")){
            mostrarMensaje("Debe seleccionar un registro");
        }else{
            if (libroTexto.getText().equals("")){
                mostrarMensaje("Proporcione un nombre para el libro");
                return;
            }
                int id = Integer.parseInt(idTexto.getText());
                String nombreLibro = libroTexto.getText();
                String autor = autorTexto.getText();
                double precio = Double.parseDouble(precioTexto.getText());
                int existencia = Integer.parseInt(existenciasTexto.getText());
                this.libroServicio.guardarLibro(new Libro(id,nombreLibro,autor,precio,existencia));
                mostrarMensaje("Libro modificado");
                limpiarFormulario();
                listarLibros();
        }

    }

    /**
     * Limpia los textField
     */
    private void limpiarFormulario() {
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");

    }

    /**
     * Metodo que recibe como parametro una cadena y lo devuelve como un mensaje de dialogo
     * @param mensaje Recibe una cadena para presentarlo como un mensaje
     */
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Lista los libros guardados en la base de datos y los muestra en la tabla
     */
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
        // creamos el elemento idTexto
        idTexto = new JTextField("");

        // se modifica el metodo del cual no va a permitir que se pueda editar en la tabla, es decir, no aparezca el cursor de escritura en las seldas
        this.tablaModeloLibros = new DefaultTableModel(0,5){
        @Override
        public boolean isCellEditable(int row, int column){     // No edita en las celdas
            return false;
        }
    };
    String[] cabeceros = {"Id", "Libro","Autor","Precio","Existencias"};
    this.tablaModeloLibros.setColumnIdentifiers(cabeceros);
    // Instancias el objeto JTable
    this.tablaLibros = new JTable(tablaModeloLibros);

    //Evitar que se seleccionen varios registros de la tabla
    tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);      //Con esto se garantiza que solamente se va a seleccionar un solo registro
    listarLibros();



    }

}
