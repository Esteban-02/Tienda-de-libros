package gm.tienda_libros.servicio;

import gm.tienda_libros.modelo.Libro;
import gm.tienda_libros.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServicio implements ILibroServicio{

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Override
    public List<Libro> listarLibros() {
        List<Libro> listaLibros = libroRepositorio.findAll();
        return listaLibros;
    }

    @Override
    public Libro buscarLibroID(int id) {
        Libro libro = libroRepositorio.findById(id).orElse(null);
        return libro;
    }

    @Override
    public void guardarLibro(Libro libro) {
        libroRepositorio.save(libro);

    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroRepositorio.delete(libro);
    }
}
