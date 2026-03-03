package model;

public class Nota{

    String titulo;
    String contenido;

    public Nota(String titulo, String contenido){
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getTitulo() {return titulo;}
    public String getContenido() {return contenido;}

    public String Guardado() {return titulo + contenido;}
}