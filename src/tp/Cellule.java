package tp;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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
//TODO : Corriger le bug
        boolean ancienEtas = this.vivante;

        for (int i = 0; i < 9; i++) {
            try {
                int soustraiLigne = ((i - (i % 3)) / 3) - 1;
                int soustraiColone = (i % 3) - 1;
                if (this.grille[this.x + soustraiLigne][this.y + soustraiColone].vivante && i != 4) {
                    nbVoisin += 1;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }

        if (nbVoisin == 2 || nbVoisin == 3) {
            if (nbVoisin == 3) {
                this.setVivante(true);
            }
        } else {
            this.setVivante(false);
        }

        if (this.vivante == ancienEtas) {
            this.setEtatChange(false);
        } else {
            if (this.vivante) {
                this.editColor(Color.DARKSLATEGRAY, Color.ANTIQUEWHITE);
            } else {
                this.editColor(Color.ANTIQUEWHITE, Color.DARKSLATEGRAY);
            }
            this.setEtatChange(true);
        }
    }

    public void clear() {
        this.vivante = false;
        this.editColor(Color.ANTIQUEWHITE, Color.DARKSLATEGRAY);
        this.setEtatChange(true);
    }

    public void cliked() {
        if (isVivante()) {
            this.vivante = false;
            this.editColor(Color.ANTIQUEWHITE, Color.DARKSLATEGRAY);
            this.setEtatChange(true);
        } else {
            this.vivante = true;
            this.editColor(Color.DARKSLATEGRAY, Color.ANTIQUEWHITE);
            this.setEtatChange(true);
        }
    }

    private void editColor(Color color1, Color color2) {
        new FillTransition(Duration.millis(100/2),cercle,color1,color2).play();
    }
}
