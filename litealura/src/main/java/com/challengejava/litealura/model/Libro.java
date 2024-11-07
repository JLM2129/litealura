package com.challengejava.litealura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
private int descargas;
    private String idioma;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")  // Nombre de la columna en la tabla de libros
    private Autor autor;



    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();
        this.idioma = datosLibro.idioma().isEmpty() ? "Desconocido" : datosLibro.idioma().get(0); // Toma el primer idioma
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }
    public Libro() {

    }
    @Override
    public String toString() {
        return          ", autor='" + autor + '\'' +
                        ", titulo='" + titulo + '\'' +
                        ",descargas=" + descargas + '\'' +
                        ", idioma=" + idioma ;


    }


}
