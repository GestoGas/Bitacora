package com.gestogas.gestoline.utils;

import android.content.Context;

public class DistanciaUtils {

    public static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public static boolean validarDistancia(Context context, int idPuesto, double latEquipo, double lonEquipo, double latEstacion, double lonEstacion, int distMax) {

        double distancia = Double.MAX_VALUE;

        if (idPuesto == 2 || idPuesto == 3) {
            distancia = calcularDistancia(latEquipo, lonEquipo, latEstacion, lonEstacion);
            return distancia <= distMax;
        }else{
            return true;
        }

    }
}
