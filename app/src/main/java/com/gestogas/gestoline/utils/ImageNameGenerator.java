package com.gestogas.gestoline.utils;

import java.util.Random;

public class ImageNameGenerator {

    /**
     * Genera un nombre único para una imagen con un número aleatorio y timestamp.
     * Ejemplo: img_1720202956543_482193.jpg
     *
     * @return Nombre de archivo con extensión .jpg
     */
    public static String generarNombreUnico() {
        long timestamp = System.currentTimeMillis(); // Marca de tiempo actual
        int random = new Random().nextInt(900000) + 100000; // Número aleatorio de 6 cifras
        return "img_" + timestamp + "_" + random + ".jpg";
    }

    /**
     * Genera un nombre corto para una imagen solo con número aleatorio.
     * Ejemplo: img_482193.jpg
     *
     * @return Nombre de archivo con extensión .jpg
     */
    public static String generarNombreCorto() {
        int random = new Random().nextInt(900000) + 100000;
        return "img_" + random + ".jpg";
    }

    /**
     * Genera un nombre único con una extensión personalizada.
     * @param extension Por ejemplo ".png", ".jpeg", etc.
     * @return Nombre de archivo con extensión personalizada
     */
    public static String generarNombreConExtension(String extension) {
        long timestamp = System.currentTimeMillis();
        int random = new Random().nextInt(900000) + 100000;
        return "img_" + timestamp + "_" + random + extension;
    }
}
