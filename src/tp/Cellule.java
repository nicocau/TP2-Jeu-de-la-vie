package tp;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Cellule {

    private Boolean vivante;
    private Boolean etatChange;
    private Cellule[][] grille;
    private Circle cercle;
    private int x;
    private int y;

    public Cellule(Cellule[][] grille, int x, int y, Boolean vivante) {
        this.grille = grille;
        this.x = x;
        this.y = y;
        this.vivante = vivante;
        this.etatChange = false;
    }

    public Boolean getVivante() {
        return vivante;
    }

    public Boolean isVivante() {
        return vivante;
    }

    public void setVivante(Boolean vivante) {
        this.vivante = vivante;
    }

    public Boolean getEtatChange() {
        return etatChange;
    }

    public Boolean isEtatChange() {
        return etatChange;
    }

    public void setEtatChange(Boolean etatChange) {
        this.etatChange = etatChange;
    }

    public Cellule[][] getGrille() {
        return grille;
    }

    public void setGrille(Cellule[][] grille) {
        this.grille = grille;
    }

    public Circle getCercle() {
        return cercle;
    }

    public void setCercle(Circle cercle) {
        this.cercle = cercle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void evoluer() {
        int nbVoisin = 0;

        boolean ancienEtas = this.vivante;

        for (int i = 0; i < 9; i++) {
            try {
                if (this.grille[(this.x - 1) + ((i - (i % 3)) / 3)][(this.y - 1) + (i % 3)].vivante) {
                    if (i != 0 && y != 0) {
                        nbVoisin += 1;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }

        if (nbVoisin == 2 || nbVoisin == 3) {
            if (nbVoisin == 3 && !this.getEtatChange()) {
                this.setVivante(true);
            }
        } else {
            this.setVivante(false);
        }

        if (this.vivante == ancienEtas) {
            this.setEtatChange(false);
        } else {
            if (this.vivante) {
                this.cercle.setFill(Color.ANTIQUEWHITE);
            } else {
                this.cercle.setFill(Color.DARKSLATEGRAY);
            }
            this.setEtatChange(true);
        }
    }

    public void clear() {
        this.vivante = false;
        this.cercle.setFill(Color.DARKSLATEGRAY);
        this.setEtatChange(true);
    }

    public void cliked() {
        if (isVivante()) {
            this.vivante = false;
            this.cercle.setFill(Color.DARKSLATEGRAY);
            this.setEtatChange(true);
        } else {
            this.vivante = true;
            this.cercle.setFill(Color.ANTIQUEWHITE);
            this.setEtatChange(true);
        }
    }
}
