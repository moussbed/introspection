package org.mb.introspection;

import java.util.logging.Level;
import java.util.logging.Logger;

@MonAnnotation(name="nom1", value = "valeur1")
public class MaClasse {

    public static Logger LOGGER     = Logger.getLogger("MaClasse");
    private boolean istrue;
    private String machaine;

   public void afficher(){
       LOGGER.log(Level.INFO,machaine);
   };

    public MaClasse() {
    }

    public MaClasse(boolean istrue, String machaine) {
        this.istrue = istrue;
        this.machaine = machaine;
    }

    public void maMethode() {
        System.out.println("maMethode sans param");
    }

    public String maMethode(String param1) {
        System.out.println("maMethode avec String:"+param1);
        return param1;
    }

    public String maMethode(String param1, int param2) {
        String resultat = param1+param2;
        System.out.println("maMethode avec String:"+param1+", int:"+param2);
        return resultat;
    }

    public String maMethode(String param1, Integer param2) {
        String resultat = param1+param2;
        System.out.println("maMethode avec String:"+param1+", int:"+param2);
        return resultat;
    }


    public void maMethode(int param1) {
        System.out.println("maMethode avec int:"+param1);
        if (param1 == 10) {
            throw new IllegalStateException("La valeur 10 n'est pas permise.");
        }
    }

    private void maMethodePrivee() {
        System.out.println("maMethodePrivee sans param");
    }

    public static void maMethodeStatic() {
        System.out.println("maMethodeStatic sans param");
    }
}
