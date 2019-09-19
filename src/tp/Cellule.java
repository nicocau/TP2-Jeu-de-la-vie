package tp;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Cellule {

    static final public Color COUL_VERS_ACTIVE = Color.CYAN;
    static final public Color COUL_ACTIVE = Color.ALICEBLUE;
    static final public Color COUL_VERS_DESACTIVE = Color.INDIANRED;
    static final public Color COUL_DESACTIVE = Color.color(0.1, 0, 0);
    static final int SOUS_POPULATION = 1;
    static final int SUR_POPULATION = 4;
    static final int MIN_POPULATION_REGENERATRICE = 3;
    static final int MAX_POPULATION_REGENERATRICE = 3;

    private Boolean vivante;
    private Boolean enTransition;
    private boolean etatPrecedent;
    private boolean etatSuivant;
    private Cellule[][] grille;
    private Circle cercle;
    private int x;
    private int y;

    public Cellule(Cellule[][] grille, int x, int y, Boolean vivante) {
        this.grille = grille;
        this.x = x;
        this.y = y;
        this.enTransition = false;
        this.vivante = vivante;
        this.etatSuivant = vivante;
        this.etatPrecedent = vivante;
    }

    public Boolean isVivante() {
        return this.vivante;
    }

    public void setVivante(Boolean vivante) {
        this.vivante = vivante;
    }

    public void setEtatSuivant(boolean etatSuivant) {
        this.etatSuivant = etatSuivant;
    }

    public void setCercle(Circle cercle) {
        this.cercle = cercle;
    }

    public void evoluer() {
        int taille = this.grille.length;
        int nbVivantes = 0;
        for (int i = -1; i < 2; i++) {
            int xx = ((x + i) + taille) % taille;
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int yy = ((y + j) + taille) % taille;
                if (this.grille[xx][yy].vivante) {
                    nbVivantes++;
                }
            }
        }
        this.etatPrecedent = this.vivante;
        if (this.vivante && (nbVivantes <= Cellule.SOUS_POPULATION || nbVivantes >= Cellule.SUR_POPULATION)) {
            this.etatSuivant = false;
            this.enTransition = true;
        } else {
            if (nbVivantes >= Cellule.MIN_POPULATION_REGENERATRICE && nbVivantes <= Cellule.MAX_POPULATION_REGENERATRICE) {
                this.etatSuivant = true;
                this.enTransition = true;
            }
        }
        switchColor();
    }

    public void switchColor() {
        Color c;
        if (this.enTransition) {
            if (this.etatSuivant) {
                c = Cellule.COUL_VERS_ACTIVE;
            } else {
                c = Cellule.COUL_VERS_DESACTIVE;
            }
            this.enTransition = !this.enTransition;
        } else {
            if (this.etatSuivant) {
                c = Cellule.COUL_ACTIVE;
            } else {
                c = Cellule.COUL_DESACTIVE;
            }
        }
        new FillTransition(Duration.millis(100 / 2), this.cercle, (Color) this.cercle.getFill(), c).play();
    }

    public void clear() {
        this.vivante = false;
        this.etatPrecedent = false;
        this.etatSuivant = false;
        this.cercle.setFill(Cellule.COUL_DESACTIVE);
    }

    public void cliked() {
        this.etatSuivant = !this.etatSuivant;
        this.switchColor();
    }

    public void avancer() {
        this.vivante = this.etatSuivant;
    }
}
