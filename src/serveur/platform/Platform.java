package serveur.platform;

import serveur.util.ChainObject.person.user.User;

public class Platform {
    public static void main(String[] args) {
        User u = new User(null, null, null, null, null, null, null, null, null);
        System.out.println(u);
        String hash = "c7b75289417981a427391273209894023908878847484926677969035139720";

        int length = hash.length();

        System.out.println("La longueur de la chaîne est de " + length + " caractères.");

    }
}
