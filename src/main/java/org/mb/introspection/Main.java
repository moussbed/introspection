package org.mb.introspection;


import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static Logger LOGGER     = Logger.getLogger("Main");

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        System.out.println(ResourceTypeDto.valueOf("anana").getResourceClass());

        /*
        L'API Reflection permet la mise en oeuvre de puissantes fonctionnalités : c'est une des raisons qui fait
        qu'elle est fréquemment utilisée par de nombreux frameworks parmi lesquels Spring ou Hibernate.
        Cependant certaines fonctionnalités peuvent aussi être utilisées à des fins malveillantes qui peuvent
        nuire à la sécurité d'une application (invocation de méthodes, modifications de la valeur de champs,
       ... même si les modificateurs de ces membres ne permettent normalement pas leur accès, ...).

        Les accès à un objet en utilisant l'API Reflection se font en utilisant une implémentation de l'interface AccessibleObject.
        Pour contourner les vérifications de l'accessibilité aux éléments d'un objet, il faut mettre à true la propriété access
        en utilisant la méthode setAccessible(). Par contre cela ne désactive pas les vérifications faites par le SecurityManager,
        s'il y en a un d'activé.

       Par défaut, aucun SecurityManager n'est activé dans une JVM. Pour en activer un, il faut soit :
      - utiliser l'option -Djava.security.manager au lancement de la JVM
      - créer une nouvelle instance de type SecurityManager() et la passer en paramètre de la méthode setSecurityManager de la classe System

        Lorsqu'un SecurityManager est activé sur une JVM, il est nécessaire d'autoriser la permission de type ReflectPermission
        dont le nom est "suppressAccessChecks" pour pouvoir utiliser des fonctionnalités de l'API Reflection.
        Si cette permission n'est pas donnée, alors une exception est levée par la méthode checkPermission() lors de l'utilisation de ces fonctionnalités.

       Il est nécessaire de définir ou de modifier la politique de sécurité pour accorder la permission "suppressAccessChecks"
       à la classe java.lang.reflect.ReflectPermission.
       Il est possible de définir son propre fichier qui contient la définition de la politique de sécurité à appliquer.
       grant {
          permission java.lang.reflect.ReflectPermission "suppressAccessChecks";
       };
       Attention : il faut autoriser la permission "suppressAccessChecks" avec précaution en limitant son effet uniquement
       sur les classes connues pour en avoir besoin. Typiquement, dans l'exemple ci-dessus, cette permission est donnée
       à toutes les classes dans la JVM ce qui peut être à l'origine de problèmes de sécurité.
         */
        System.setProperty("java.security.policy",
                "file:C:\\Users\\bedril.moussakat\\IdeaProjects\\introspection/src/TestExecuterMethode.policy");// Permet l'access au modificateur privée
        System.setSecurityManager(new SecurityManager()); // L'access à l'invocation de methode privée meme si le method.setAccessible(true); est fait

        List<String> maList = new ArrayList<String>();
        List<String> cp = new ArrayList<String>();
        List<String> cp2 = new ArrayList<String>();
        String chaine ="test";
        Class maClasse = chaine.getClass();
        System.out.println(maClasse);
        Class classe = Class.forName("java.lang.String");
        System.out.println("classe de l'objet chaine = "+classe.getName());
        Class c = Object.class;
        System.out.println("classe de Object  = "+c.getName());
        Class[] classPublic= classe.getClasses();

        boolean condition= maClasse==classe;
        if(condition) System.out.println(true);

        int m=  maClasse.getModifiers();
        System.out.println(m);
        if(Modifier.isFinal(m)) maList.add("Final");
        if(Modifier.isPublic(m)) maList.add("Public");
        if(Modifier.isAbstract(m)) maList.add("Abstract");
        if(Modifier.isProtected(m)) maList.add("Protected");
        if(Modifier.isPrivate(m)) maList.add("Private");
        if(Modifier.isNative(m)) maList.add("Native");
        maList.forEach(System.out::println);

        Class[] interfaces = classe.getInterfaces();
        for (Class anInterface : interfaces) {
            System.out.println(anInterface.getName());

        }

        Field[] champs = Inspecteur.class.getFields();
        for (int i = 0; i < champs.length; i++)
            cp2.add(champs[i].getType().getName()+" "+champs[i].getName());

        cp2.forEach(System.out::println);

        // La création d'objets grâce à la classe Class
        Class<MaClasse> maClasseClass = (Class<MaClasse>) Class.forName("org.mb.introspection.MaClasse");
        MaClasse monObject = maClasseClass.newInstance();
        monObject.afficher();

        // La création d'objets grâce à la classe Constructor
        Class<MaClasse> classe2 = (Class<MaClasse>) Class.forName("org.mb.introspection.MaClasse");
        Constructor<MaClasse> constructeur = classe2.getConstructor(boolean.class, String.class);
        MaClasse instance = constructeur.newInstance(Boolean.FALSE, "nom instance");
        instance.afficher();


        Class classeClass = MaClasse.class;
        Annotation[] annotations = classeClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof MonAnnotation) {
                MonAnnotation monAnnotation = (MonAnnotation) annotation;
                System.out.println("nom    : " + monAnnotation.name());
                System.out.println("valeur : " + monAnnotation.value());
            }
        }

        // Invocation dynamique d'une methode

        Class maClasse4 = MaClasse.class;
        MaClasse maClasse3 = (MaClasse) maClasse4.newInstance();
        MaClasse maClas = new MaClasse();
        try {


            Object retour = executerMethode(maClasse3, "maMethode",null);
            System.out.println("Valeur de retour = " + retour);
            //NoSuchMethodException
            retour = executerMethode(maClasse3, "maMethodeStatic", new Object[]{12});
            System.out.println("Valeur de retour = " + retour);
            retour = executerMethode(maClasse3, "maMethode", new
                    Object[]{"Tama"});
            System.out.println("Valeur de retour = " + retour);
            retour = executerMethode(maClasse3, "maMethode", new
                    Object[]{"chaine", 99});
            System.out.println("Valeur de retour = " + retour);

        } catch (Exception ex) {
            //  ex.printStackTrace();
            LOGGER.log(Level.SEVERE,ex.toString());
        }
        System.out.println("J'execute la fin ");

        // IllegalArgumentException

        MaClasse maClasse5 = new MaClasse();
        try {
            Class<?> c1 = maClasse5.getClass();
            Method m1 = c1.getMethod("maMethode");
            System.out.format("Methode : %s%n", m1.toGenericString());
            m1.invoke(maClasse5 , "test");
        } catch (NoSuchMethodException  x){
            x.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }

        // Exception levée par une methode invoquée
        try {
            Method methode = MaClasse.class.getMethod("maMethode",Integer.TYPE);
            methode.invoke(MaClasse.class.newInstance(),10);
        } catch (NoSuchMethodException x) {
            x.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
            Throwable cause = ex.getCause();
            System.out.println("Cause : "+cause.getMessage());
        }

        // Invocation de method privée, IllegalAccessException si on utilise pas le setAccessible(true)
        Method method = MaClasse.class.getDeclaredMethod("maMethodePrivee",null);
        method.setAccessible(true);
        method.invoke(MaClasse.class.newInstance());

        // Invocation de methode d'une classe Generic,
        MaClasseGeneric<Integer> maClasse7 = new MaClasseGeneric<Integer>();
        Class<?> classe7 = maClasse7.getClass();
        /*
        Utilisation Object.class au lieu Integer.Class
        A cause de l'implémentation des generics qui utilise le type erasure,
        le type generic original est perdu à la compilation pour laisser le type Object.
        Lorsque le type de la méthode est un type généric, il faut le remplacer par le type Object
        lorsque l'API Reflection est utilisée pour invoquer la méthode.
         */
        Method methode7 = classe7.getMethod("maMethod", Object.class);
        System.out.format("Methode : %s%n", methode7.toGenericString());
        methode7.invoke(maClasse7, Integer.valueOf(100));

        /*
        Une exception de type SecurityException est levée si la méthode setAccessible() est invoquée
        sur une instance de type Constructor pour la classe Class.
         */
        Class classe10 = Class.class;
        try {
            Constructor constructeur10 = classe10.getDeclaredConstructor();
            constructeur10.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // Covariance n'existe pas avec la genericité
        List<Fruit> fruits = new ArrayList<>();
        List<? extends Fruit> pommes = new ArrayList<Pomme>();

        fruits.add(new Fruit("Red"));
        fruits.add(new Fruit("Green"));
        fruits.add(new Pomme("Yellow"));
        displayFruit(fruits);

        String monstring ="/admin-portal/api/{resource:.+}/{id:.+}";
        String api= "api/";
        String resource =monstring.substring(monstring.indexOf(api)+api.length());
        if(!resource.contains("/"))  System.out.println(resource + " 1");
        resource= resource.substring(0,resource.lastIndexOf("/"));
        System.out.println(resource + " 2");

        //String monstring2= monstring.substring(monstring.indexOf("api/")+4,monstring.lastIndexOf("/")!=-1?monstring.lastIndexOf("/"):monstring.length()-1);
      //  System.out.println(monstring2);


    }


    public static  void displayFruit(List<? extends Fruit> listFruit){

        for(Fruit f : listFruit) System.out.println(f.toString());
    }

    public static Object executerMethode(Object objet, String nomMethode,
                                         Object[] parametres) throws Exception {
        Object retour;
        Class[] typeParametres = null;

        if (parametres != null) {
            typeParametres = new Class[parametres.length];
            for (int i = 0; i < parametres.length; ++i) {
                typeParametres[i] = parametres[i].getClass();
            }
        }

        Method m = objet.getClass().getMethod(nomMethode, typeParametres);
        if (Modifier.isStatic(m.getModifiers())) {
            retour = m.invoke(null, parametres);
        } else {
            retour = m.invoke(objet, parametres);
        }
        return retour;
    }
}
