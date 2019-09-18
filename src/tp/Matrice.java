package tp;

import java.util.Random;

public class Matrice {


    private Cellule[][] grille;
    private Cellule[][] grilleAncienne;
    private double densite;
    private int taille;

    public Matrice(int taille, double densite) {
        this.taille = taille;
        this.densite = densite;
        this.init();
        this.initHasard();
    }

    public Cellule[][] getGrille() {
        return grille;
    }

    public void setGrille(Cellule[][] grille) {
        this.grille = grille;
    }

    public Cellule[][] getGrilleAncienne() {
        return grilleAncienne;
    }

    public void setGrilleAncienne(Cellule[][] grilleAncienne) {
        this.grilleAncienne = grilleAncienne;
    }

    public double getDensite() {
        return densite;
    }

    public void setDensite(double densite) {
        this.densite = densite;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void init() {
        this.grille = new Cellule[this.taille][this.taille];
        this.grilleAncienne = new Cellule[this.taille][this.taille];

        for (int x = 0; x < this.taille; x++) {
            for (int y = 0; y < this.taille; y++) {
                this.grille[x][y] = new Cellule(this.grille,x,y,false);
                Cellule celluleNew = this.grille[x][y];
                this.grilleAncienne[x][y] = new Cellule(this.grille,celluleNew.getX(),celluleNew.getY(),celluleNew.getVivante());
            }
        }
    }

    public void initHasard() {
        Random random = new Random();
        for (int x = 0; x < this.taille; x++) {
            for (int y = 0; y < this.taille; y++) {
                this.grille[x][y].setVivante(this.densite<random.nextDouble());
                this.grilleAncienne[x][y].setVivante(this.densite<random.nextDouble());
            }
        }
    }

    public void copieGrilles() {
        for (int x = 0; x < this.taille; x++) {
            for (int y = 0; y < this.taille; y++) {
                Cellule celluleNew = this.grille[x][y];
                this.grilleAncienne[x][y].setVivante(celluleNew.getVivante());
                this.grilleAncienne[x][y].setEtatChange(celluleNew.getEtatChange());
            }
        }
    }

    public void animGrille() {
        for (int x = 0; x < this.taille; x++) {
            for (int y = 0; y < this.taille; y++) {
                this.grille[x][y].evoluer();
            }
        }
    }
}
