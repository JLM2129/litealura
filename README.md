Proyecto LiterAlura - Gestión de Libros y Autores

Este proyecto es una aplicación de consola en Java que permite gestionar un catálogo de libros y autores usando datos de la API Gutendex. Proporciona funcionalidades como búsqueda y consulta de libros, persistencia de datos en PostgreSQL y filtros por autores vivos en un año dado.

Requisitos del entorno
* Java JDK: 17 o superior
* Maven: 4 o superior
* Spring Boot: 3.2.3
* PostgreSQL: 16 o superior
* IDE recomendado: IntelliJ IDEA (opcional)

Configuración inicial

1. Clonar el repositorio:
   
git clone https://github.com/tu-usuario/literalura.git
cd literalura

2. Configurar la base de datos: Crea una base de datos en PostgreSQL y configura el archivo application.properties con tus datos de conexión:

spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

3. Instalar las dependencias:
   
mvn clean install

Estructura

![image](https://github.com/user-attachments/assets/67a08e12-f2ed-4ddc-8d8c-fee340688511)



Funcionalidades del proyecto.

1. Consulta de datos en la API Gutendex: La aplicación realiza consultas a la API Gutendex para buscar libros por título y obtener información relevante como autor, idioma y número de descargas.

2. Almacenamiento de datos en PostgreSQL: Los datos de libros y autores se guardan en una base de datos relacional, usando Spring Data JPA para manejar la persistencia.

3. Interfaz de usuario mediante consola:

* Menú principal: Implementado usando CommandLineRunner y Scanner.
* Opciones disponibles:
* 
  - Registrar y buscar libros.
  - Listar todos los libros almacenados.
  - Filtrar libros por idioma.
  - Listar autores vivos en un año específico.
    
4.  Manipulación y análisis de datos JSON: Usando la biblioteca Jackson, la aplicación convierte los datos JSON obtenidos de la API a objetos Java, facilitando la extracción y manipulación.

Ejecución de la aplicación.

Ejecuta la clase principal MainApplication para iniciar la aplicación. El menú en consola permite seleccionar entre las siguientes opciones:

1: Buscar y registrar un libro.
2: Listar todos los libros registrados.
3: Listar todos los autores con sus libros.
4: Filtrar autores vivos en un año específico.
5: Filtrar libros por idioma.
0: Salir de la aplicación.

Dependencias utilizadas.

* Spring Boot: Para simplificar el desarrollo de aplicaciones.
* Spring Data JPA: Para el acceso a la base de datos.
* Jackson: Para el mapeo de datos JSON.
* HttpClient: Para las solicitudes HTTP a la API de Gutendex.

Próximas mejoras.

* Interfaz gráfica: Desarrollar una GUI para mejorar la experiencia de usuario.
* Validación de entradas: Mejorar el control de errores en las entradas del usuario.
* Consultas avanzadas: Agregar filtros adicionales para las búsquedas.
  
Licencia.

Distribuido bajo la licencia MIT.


