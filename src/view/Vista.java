package view;

import service.ControladorUsuarios;
import service.ControladorNotas;
import model.Nota;
import java.util.*;

public class Vista {

    private Scanner teclado = new Scanner(System.in);
    private String correoActual = null;

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("== MENÚ PRINCIPAL ==");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> accionRegistrar();
                case 2 -> {
                    accionIniciarSesion();
                    if (correoActual != null) mostrarMenuUsuario();
                }
                case 0 -> System.out.println("Hasta luego");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuUsuario() {
        int opcion;
        do {
            System.out.println("== MENÚ USUARIO ==");
            System.out.println("1. Crear nota");
            System.out.println("2. Listar notas");
            System.out.println("3. Ver nota");
            System.out.println("4. Eliminar nota");
            System.out.println("5. Buscar nota");
            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> accionCrearNota();
                case 2 -> accionListarNotas();
                case 3 -> accionVerNota();
                case 4 -> accionEliminarNota();
                case 5 -> accionBuscarNota();
                case 0 -> {
                    correoActual = null;
                    System.out.println("Sesión cerrada");
                }
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0 && correoActual != null);
    }

    private void accionRegistrar() {
        System.out.print("Correo: ");
        String correo = teclado.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = teclado.nextLine();
        ControladorUsuarios.registrar(correo, contrasena);
    }

    private void accionIniciarSesion() {
        System.out.print("Correo: ");
        String correo = teclado.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = teclado.nextLine();
        correoActual = ControladorUsuarios.iniciarSesion(correo, contrasena);
    }

    private void accionCrearNota() {
        System.out.print("Título: ");
        String titulo = teclado.nextLine();
        System.out.print("Contenido: ");
        String contenido = teclado.nextLine();
        ControladorNotas.crear(correoActual, titulo, contenido);
    }

    private void accionListarNotas() {
        List<String> lineas = ControladorNotas.listar(correoActual);
        if (lineas.isEmpty()) {
            System.out.println("No tienes notas guardadas");
            return;
        }
        System.out.println("- Tus notas -");
        for (int i = 0; i < lineas.size(); i++) {
            String[] partes = lineas.get(i).split(";");
            System.out.println((i + 1) + ". " + partes[0]);
        }
    }

    private void accionVerNota() {
        accionListarNotas();
        System.out.print("Número de nota: ");
        int numero = Integer.parseInt(teclado.nextLine()) - 1;
        Nota nota = ControladorNotas.ver(correoActual, numero);
        if (nota != null) {
            System.out.println("Título: " + nota.getTitulo());
            System.out.println("Contenido: " + nota.getContenido());
        }
    }

    private void accionEliminarNota() {
        accionListarNotas();
        System.out.print("Número de nota a eliminar: ");
        int numero = Integer.parseInt(teclado.nextLine()) - 1;
        ControladorNotas.eliminar(correoActual, numero);
    }

    private void accionBuscarNota() {
        System.out.print("Palabra clave: ");
        String palabra = teclado.nextLine();
        List<String> resultados = ControladorNotas.buscar(correoActual, palabra);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron notas");
            return;
        }
        System.out.println("- Resultados -");
        for (int i = 0; i < resultados.size(); i++) {
            String[] partes = resultados.get(i).split(";");
            System.out.println((i + 1) + ". " + partes[0]);
        }
    }
}