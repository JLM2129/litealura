package com.challengejava.litealura.model;

public enum Idioma {
    INGLES("ingles", "en"),
    ESPANOL("español", "es"),
    FRANCES("frances", "fr"),
    PORTUGUES("portugues","pt"),
    ALEMAN("aleman","de"),
    ITALIANO("italiano","it"),
    RUSO("ruso","ru"),
    CHINO("chino","zh"),
    JAPONES("japones","ja");

    private final String nombreCompleto;
    private final String abreviatura;


    Idioma(String nombreCompleto, String abreviatura) {
        this.nombreCompleto = nombreCompleto;
        this.abreviatura = abreviatura;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    // Metodo estático para obtener la abreviatura basada en el nombre completo
    public static String obtenerAbreviatura(String nombreCompleto) {
        for (Idioma idioma : values()) {
            if (idioma.nombreCompleto.equalsIgnoreCase(nombreCompleto)) {
                return idioma.abreviatura;
            }
        }
        return null; // Devuelve null si no se encuentra el idioma
    }
}

