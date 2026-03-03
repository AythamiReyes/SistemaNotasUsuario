package utils;

public class Validaciones {

    public static boolean noEstaVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean esCorreoValido(String correo) {
        return correo.contains("@") && correo.contains(".");
    }
}