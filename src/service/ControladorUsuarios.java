package service;

import model.Usuario;
import utils.Ficheros;
import utils.Validaciones;
import java.security.MessageDigest;
import java.util.*;

public class ControladorUsuarios {

    public static boolean registrar(String correo, String contrasena) {

        if (!Validaciones.noEstaVacio(correo) ||
            !Validaciones.noEstaVacio(contrasena)) {
            System.out.println("Error: los campos no pueden estar vacíos");
            return false;
        }

        if (!Validaciones.esCorreoValido(correo)) {
            System.out.println("Error: el correo no es válido");
            return false;
        }

        if (existeUsuario(correo)) {
            System.out.println("Error: ese correo ya está registrado");
            return false;
        }

        Usuario usuario = new Usuario(correo, hashContrasena(contrasena));
        Ficheros.escribirLinea(Ficheros.FICHERO_USUARIOS, usuario.guardado());
        Ficheros.crearCarpetaUsuario(correo);
        System.out.println("Usuario registrado correctamente");
        return true;
    }

    public static String iniciarSesion(String correo, String contrasena) {

        List<String> lineas = Ficheros.leerLineas(Ficheros.FICHERO_USUARIOS);

        for (String linea : lineas) {
            String[] partes = linea.split(";");
            if (partes[0].equals(correo) &&
                partes[1].equals(hashContrasena(contrasena))) {
                System.out.println("Bienvenido " + correo);
                return correo;
            }
        }

        System.out.println("Error: correo o contraseña incorrectos");
        return null;
    }

    private static boolean existeUsuario(String correo) {
        List<String> lineas = Ficheros.leerLineas(Ficheros.FICHERO_USUARIOS);
        for (String linea : lineas) {
            String[] partes = linea.split(";");
            if (partes[0].equals(correo)) return true;
        }
        return false;
    }

    public static String hashContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contrasena.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return contrasena;
        }
    }
}