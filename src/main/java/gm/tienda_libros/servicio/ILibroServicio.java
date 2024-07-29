package gm.tienda_libros.servicio;

import gm.tienda_libros.modelo.Libro;

import java.util.List;

public interface ILibroServicio {
    public List<Libro> listarLibros();
    public Libro buscarLibroID(int id);
    public void guardarLibro(Libro libro);
    public void eliminarLibro(Libro libro);



}
