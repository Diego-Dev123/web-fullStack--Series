package com.aluracursos.screenmatch.model;

public enum Categoria {
    ACCION("Action", "Accion"),
    ROMANCE("Romance", "Romance"),
    COMEDIA ("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");

    //este es pa q no de error
    private String categotiaOmb;
    private String categoriaspanol;

    Categoria(String categotiaOmb, String categoriaspanol){
        this.categoriaspanol = categoriaspanol;
        this.categotiaOmb = categotiaOmb;

    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()){
            if (categoria.categotiaOmb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException(("Ninguna categoria encontrada: " + text));
    }

    public static Categoria fromSpanol(String text) {
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaspanol.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException(("Ninguna categoria encontrada: " + text));
    }


}
