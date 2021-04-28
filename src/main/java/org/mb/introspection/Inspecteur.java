package org.mb.introspection;

public class Inspecteur {
    private  Class classe;
    private  String nomClasse;

    public Inspecteur(String nomClasse) throws ClassNotFoundException {
        this.nomClasse = nomClasse;
        classe = Class.forName(nomClasse);
    }
}