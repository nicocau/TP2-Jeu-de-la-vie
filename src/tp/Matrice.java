package tp;

import java.util.Random;

public class Matrice {


    private Cellule[][] grille;
    private double densite;
    private int taille;

    public Matrice(int taille, double densite) {
        this.taille = taille;
        this.densite = densite;
        this.grille = new Cellule[taille][taille];
        this.init();
        this.initHasard();
    }

    public Cellule[][] getGrille() {
        return grille;
    }

    public void init() {
        for (int x = 0; x < this.taille; x++) {
            for (int y = 0; y < this.taille; y++) {
                this.grille[x][y] = new Cellule(this.grille, x, y, false);
            }
        }
    }

    public void initHasard() {
        Random random = new Random();
        for (Cellule[] ligne : this.grille) {
            for (Cellule c : ligne) {
                if (random.nextDouble() < this.densite) {
                    c.setVivante(true);
                    c.setEtatSuivant(true);
                } else {
                    c.setVivante(false);
                    c.setEtatSuivant(false);
                }
            }
        }
    }

    public void clear() {
        for (Cellule[] ligne : this.grille) {
            for (Cellule c : ligne) {
                c.clear();
            }
        }
    }

    public void animGrille() {
        for (Cellule[] ligne : this.grille) {
            for (Cellule c : ligne) {
                c.evoluer();
            }
        }
    }


    public void avancer() {
        for (Cellule[] ligne : this.grille)
            for (Cellule c : ligne)
                c.avancer();
    }
}
