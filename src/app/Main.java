package app;

import utils.Ficheros;
import view.Vista;

public class Main {
    public static void main(String[] args) {

        Ficheros.inicializar();

        Vista vista = new Vista();
        vista.mostrarMenuPrincipal();
    }
}