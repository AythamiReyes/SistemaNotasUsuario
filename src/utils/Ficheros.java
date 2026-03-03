package utils;

import java.nio.file.*;
import java.util.*;

public class Ficheros {

    public static final String FICHERO_USUARIOS = "data/usuarios.txt";
    public static final String CARPETA_USUARIOS = "data/usuarios/";

    public static void inicializar() {
        try {
            Files.createDirectories(Path.of(CARPETA_USUARIOS));
            if (!Files.exists(Path.of(FICHERO_USUARIOS))) {
                Files.createFile(Path.of(FICHERO_USUARIOS));
            }
        } catch (Exception e) {
            System.out.println("Error al inicializar: " + e.getMessage());
        }
    }

    public static List<String> leerLineas(String ruta) {
        try {
            return Files.readAllLines(Path.of(ruta));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void escribirLinea(String ruta, String linea) {
        try (var escritor = Files.newBufferedWriter(
                Path.of(ruta), StandardOpenOption.APPEND)) {
            escritor.write(linea);
            escritor.newLine();
        } catch (Exception e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }

    public static void sobreescribir(String ruta, List<String> lineas) {
        String rutaTemporal = ruta + ".tmp";
        try {
            Files.write(Path.of(rutaTemporal), lineas);
            Files.move(Path.of(rutaTemporal), Path.of(ruta),
                StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public static String sanitizarEmail(String correo) {
        return correo.replace("@", "_en_").replace(".", "_");
    }

    public static String getRutaNotas(String correo) {
        return CARPETA_USUARIOS + sanitizarEmail(correo) + "/notas.txt";
    }

    public static void crearCarpetaUsuario(String correo) {
        try {
            String ruta = CARPETA_USUARIOS + sanitizarEmail(correo) + "/";
            Files.createDirectories(Path.of(ruta));
            if (!Files.exists(Path.of(getRutaNotas(correo)))) {
                Files.createFile(Path.of(getRutaNotas(correo)));
            }
        } catch (Exception e) {
            System.out.println("Error al crear carpeta: " + e.getMessage());
        }
    }
}