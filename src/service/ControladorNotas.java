package service;

import model.Nota;
import utils.Ficheros;
import utils.Validaciones;
import java.util.*;

public class ControladorNotas {

    public static void crear(String correo, String titulo, String contenido) {

        if (!Validaciones.noEstaVacio(titulo) || !Validaciones.noEstaVacio(contenido)) {
            System.out.println("Los campos no pueden estar vacíos");
            return;
        }

        Nota nota = new Nota(titulo, contenido);
        Ficheros.escribirLinea(Ficheros.getRutaNotas(correo), nota.guardado());
        System.out.println("Nota creada correctamente");
    }

    public static List<String> listar(String correo) {
        return Ficheros.leerLineas(Ficheros.getRutaNotas(correo));
    }

    public static Nota ver(String correo, int numero) {
        List<String> lineas = listar(correo);

        if (numero < 0 || numero >= lineas.size()) {
            System.out.println("Número incorrecto");
            return null;
        }

        String[] partes = lineas.get(numero).split(";");
        return new Nota(partes[0], partes[1]);
    }

    public static void eliminar(String correo, int numero) {
        List<String> lineas = listar(correo);

        if (numero < 0 || numero >= lineas.size()) {
            System.out.println("Número incorrecto");
            return;
        }

        lineas.remove(numero);
        Ficheros.sobreescribir(Ficheros.getRutaNotas(correo), lineas);
        System.out.println("Nota eliminada correctamente");
    }

    public static List<String> buscar(String correo, String palabra) {
        List<String> lineas = listar(correo);
        List<String> resultados = new ArrayList<>();

        for (String linea : lineas) {
            if (linea.toLowerCase().contains(palabra.toLowerCase())) {
                resultados.add(linea);
            }
        }
        return resultados;
    }
}