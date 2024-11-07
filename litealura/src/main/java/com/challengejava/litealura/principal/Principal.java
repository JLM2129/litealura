package com.challengejava.litealura.principal;

import com.challengejava.litealura.model.*;
import com.challengejava.litealura.repository.AutorRepository;
import com.challengejava.litealura.repository.LibroRepository;
import com.challengejava.litealura.service.ConsumoAPI;
import com.challengejava.litealura.service.ConvierteDatos;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosAutor> datosAutors = new ArrayList<>();

    private List<Autor> autores;
    private Optional<Autor> AutorBuscado;
    @Autowired
    private LibroRepository repositorio;
    private AutorRepository autorRepository;  // Agregar AutorRepository
    private List<com.challengejava.litealura.model.Libro> Libro;
    private Long idAutor;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepository = autorRepository;
    }





        public void mostrarMenu() {
            int opcion = -1;

            while (opcion != 0) {
                String menu = """
                1 - Buscar libro por titulo
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                0 - Salir
                """;

                System.out.println(menu);
                System.out.print("Por favor ingrese una opción: ");

                try {
                    opcion = teclado.nextInt();
                    teclado.nextLine(); // Limpia la línea después de leer el número

                    switch (opcion) {
                        case 1 -> buscarLibroWeb();
                        case 2 -> listarLibrosRegistrados();
                        case 3 -> listarAutoresRegistrados();
                        case 4 -> buscarAutoresVivosPorAnio();
                        case 5 -> listarLibrosPorIdioma();
                        case 0 -> System.out.println("Cerrando la aplicación...");
                        default -> System.out.println("Opción inválida");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Carácter no admitido. Solo se permiten números.");
                    teclado.nextLine(); // Limpia el búfer del escáner para evitar el bucle infinito
                }
            }
        }




    private void listarLibrosPorIdioma() {
        while (true) {
            try {
                System.out.println("Ingrese el idioma que desea consultar :");
                String idiomaCompleto = teclado.nextLine().toLowerCase();


                if (!idiomaCompleto.matches("[^,áéíóú]*")) {
                    throw new IllegalArgumentException("Entrada inválida: no se permiten comas ni tildes.");
                }


                String idiomaAbreviado = Idioma.obtenerAbreviatura(idiomaCompleto);

                if (idiomaAbreviado != null) {

                    metodolistarLibrosPorIdioma(idiomaAbreviado);
                    break;
                } else {
                    System.out.println("Idioma no reconocido. Intente con inglés, español, etc.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();

        if (datos != null) {
            Libro libro = new Libro(datos);

            if (datos.autor() != null && !datos.autor().isEmpty()) {
                DatosAutor datosAutor = datos.autor().get(0);

                Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                        .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

                libro.setAutor(autor);
            }

            // Verifica si el libro ya existe en la base de datos
            if (repositorio.findByTitulo(libro.getTitulo()).isPresent()) {
                System.out.println("El libro ya está registrado en la base de datos.");
            } else {
                repositorio.save(libro);
                System.out.println("Libro guardado: " + libro);
            }
        }
    }





    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));

        // Extraer y procesar JSON para obtener el primer resultado de 'results'
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray resultsArray = jsonObject.getAsJsonArray("results");

        if (resultsArray != null && resultsArray.size() > 0) {
            JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
            // Aquí se mapea el JSON del primer libro al objeto DatosLibro
            DatosLibro datos = conversor.obtenerDatos(firstResult.toString(), DatosLibro.class);
            return datos;
        } else {
            System.out.println("No se encontraron resultados para el libro buscado.");
            return null;
        }
    }


    private void listarLibrosRegistrados() {
        List<Libro> libros = repositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("Lista de libros registrados:");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargados: " + libro.getDescargas());
                System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Sin autor"));
                System.out.println("---------------------------");
            });
        }
    }


    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAllWithLibros();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
        } else {
            System.out.println("Lista de autores registrados:");
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Año de muerte: " + autor.getFechaDeMuerte());
                List<Libro> libros = autor.getLibros();
                if (libros != null && !libros.isEmpty()) {
                    System.out.println("Libros asociados:");
                    libros.forEach(libro -> System.out.println(" - " + libro.getTitulo()));
                }
                System.out.println("---------------------------");
            });
        }
    }

    public void listarAutoresVivosAntesDe(int ano) {
        // Llamar al método del repositorio para obtener los autores vivos antes de un año específico
        List<Autor> autores = autorRepository.findAutoresVivosAntesDe(ano);

        // Mostrar los autores en la consola
        if (!autores.isEmpty()) {
            System.out.println("Autores vivos antes del año " + ano + ":");
            for (Autor autor : autores) {
                System.out.println("Nombre: " + autor.getNombre() +
                        ", Año de Nacimiento: " + autor.getFechaDeNacimiento() +
                        ", Año de Muerte: " + (autor.getFechaDeMuerte() != 0 ? autor.getFechaDeMuerte() : "N/A"));
            }
        } else {
            System.out.println("No se encontraron autores vivos antes del año " + ano);
        }
    }

    public void buscarAutoresVivosPorAnio() {
        System.out.println("Ingrese el año límite para buscar autores que estaban vivos antes de ese año:");
        int ano = teclado.nextInt();
        teclado.nextLine();

        listarAutoresVivosAntesDe(ano);
    }



    private void metodolistarLibrosPorIdioma(String idioma) {
        List<Libro> libros = repositorio.findByIdioma(idioma);

        if (!libros.isEmpty()) {
            System.out.println("Libros en el idioma " + idioma + ":");
            for (Libro libro : libros) {
                System.out.println("Título: " + libro.getTitulo() + "Descargas: " + libro.getDescargas() +
                        ", Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "N/A") +
                        ", Idioma: " + (libro.getIdioma() != null ? libro.getIdioma() : "N/A"));
            }
        } else {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        }
    }
        }












