TP2 : Jeu de la vie
===

http://emmanuel.adam.free.fr/site/spip.php?article98

https://github.com/EmmanuelADAM/coursJavaAvance/tree/master/TP/JeuDeLaVie

Jeu de la vie
---

Dans le Jeu de la Vie (proposé par Conway 1970), dans une matrice se trouvent des cellules, actives ou inactives.

Le Jeu de la vie consiste à faire évoluer sur un grille un ensemble cellules
selon les règles suivantes :

 - Si une cellule active est entourée de moins de 2 cellules : elle manque de contact et se désactive.
 - Si une cellule active est entourée de plus de 3 cellules : elle est en milieu surpeuplé et se désactive.
 - Si une cellule inactive est entourée de 3 cellules, alors elle s’active.
 - Dans les autres cas, la cellule garde son état.
 
Voici le lien pour la page wikipedia en français http://fr.wikipedia.org/wiki/Jeu_de_la_vie.

Ici http://golly.sourceforge.net/ se trouve le logiciel golly, spécialement dédié aux automates cellulaires.
Possibilité d’utiliser un monde très très étendu...

Enoncé
---

Créer le programme permettant le déroulement d’un jeu de la Vie.
1. Créer les classes Cellule et Matrice :

Les classes suivantes sont à définir :

 - Cellule, avec les variables
   - vivante : booléen
   - etatChange : booléen qui indique si un l’état vient de changer
   - Cellule[][] grille : un lien vers la matrice de cellules
   - Circle cercle : un lien vers sa représentation graphique
   - x, y les coordonnées
   - le constructeur : public Cellule(Cellule [][]grille, int x, int y, boolean vivante)
   - evoluer() qui change la valeur de la cellule par rapport à son voisinage
     On prendra un monde sphérique. ’taille’ étant la dimension de la grille carrée, la case en haut à gauche de la case (0,0) est la case (taille-1, taille-1).

 - Matrice, avec les variables
   - Cellule[][] grille, la grille au temps actuel
   - Cellule[][] grilleAncienne, la grille au temps précédent
   - double densité : pourcentage initiale de cellules actives
   - int taille : la taille de la matrice (carrée)
   - le constructeur public Matrice(int taille, double densite)
   - la fonction void init() qui cree des cellules inactives dans la grille et les recopie dans la grilleAncinne
   - la fonction initHasard() qui active densité% de cellule dans la grille et dans la grilleAncienne
   - copieGrille(), recopie grille dans ancienne grille
   - animGrille(), demande à chaque cellule d’évoluer


2. Interactivité

Ajouter des interactions :

 - l’appui sur ’p’ met en pause la simulation
 - l’appui sur ’d’ démarre la simulation
 - l’appui sur ’e’ efface tout
 - l’appui sur ’h’ active des cellules aléatoirement
 - un clic souris sur la zone de simulation active.desactive des cellules
3. Ralentissez la croissance :

ajouter des étapes afin de voir l’évolution des cellules au ralenti :

 - les cellules actives sont en vert clair
 - les cellules qui se désactivent et seraient désactivés au top suivant sont en vert
 - les cellules qui se réactivent et seraient actives au top suivant sont en vert sombre
 - les cellules inactives sont en vert noir.

4. Animation :

Quand une cellule change d’état, sa couleur s’estompe graduellement.
Remarque : Ceci ralenti la simulation..

Fenêtre JFX pour tester le code
--

On utilisera une fenêtre de type JavaFX.
En JavaFX, une application graphique se déroule sur une scène (scene) de théâtre (stage).
Sur cette scène se trouve des acteurs, les éléments graphiques.
Voici un exemple de code pour l’Application :

```Java
public class JfxAnimVie extends Application {
    /**matrice liee a cet objet graphique*/
    Matrice matrice;
    /**elements graphiques représentant les cellules*/
    public static Circle[][] circles;
    /**taille d'une cellule en pixel*/
    int espace = 10;
    /**taille de la matrice*/
    private int taille;
    /**nombre de celluls initialement actives*/
    private double densite;
    /**délai en ms entre chaque évolution*/
    private int tempo;
    
    @Override
    public void start(Stage primaryStage) {
        taille = 200;
        densite = 0.2;
        //...
    }
}
```

Version courte mais complète
---

Vous trouvez ci-dessous un petit code complet permettant l’exécution du jeu de la Vie en Java FX, en 1 seule classe.

```Java
package coursJFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.util.Duration;


public class FenetreApplication extends Application {
    /**
     * matrice representant l'ETAT des cellules
     */
    byte[][] matrice;
    /**
     * copie de la matrice representant l'ETAT des cellules
     */
    byte[][] matriceCopie;
    /**
     * objets graphiques représentant les cellules
     */
    public Circle[][] circles;

    /**
     * taille d'une cellule en pixel
     */
    int espace = 10;
    /**
     * longueur de la matrice (en nombre de cellules sur une rangée)
     */
    private int taille;
    /**
     * densite de cellules actives au départ
     */
    double densite;

    /**
     * délai en ms entre chaque évolution
     */
    private int tempo;


    /**
     * lancement de l'application
     */
    public void start(Stage primaryStage) {
        taille = 60;
        tempo = 60;
        espace = 16;
        densite = 0.2;
        construireScenePourJeuDeLaVie(primaryStage);
    }

    void construireScenePourJeuDeLaVie(Stage primaryStage) {
        int largeur = (taille + 1) * (espace + 1);
        int hauteur = (taille + 1) * (espace + 1);
        //definir la troupe
        Group root = new Group();
        //definir la scene principale
        Scene scene = new Scene(root, largeur, hauteur, Color.BLACK);
        primaryStage.setTitle("Life...");
        primaryStage.setScene(scene);

        //creation et initialisation des cellules
        matrice = new byte[taille][taille];
        matriceCopie = new byte[taille][taille];
        initMatrice(densite);
        copie(matrice, matriceCopie);

        //definir les acteurs (representation des cellules
        circles = new Circle[taille][taille];
        creationTroupe(root);

        //afficher le theatre
        primaryStage.show();


        //-----lancer le timer pour faire vivre la matrice
        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(tempo),
                event -> {
                    //à chaque top, lancer une evolution
                    evoluerMatrice();
                    //puis effectuer une copie de la matrice
                    copie(matrice, matriceCopie);
                }));
        //animation en boucle
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
    }


    /**
     * copie le contenu de matrice dans matriceCopie
     *
     * @param matrice      matrice d'origine
     * @param matriceCopie matrice de destination
     */
    void copie(byte[][] matrice, byte[][] matriceCopie) {
        for (int i = 0; i < taille; i++)
            System.arraycopy(matrice[i], 0, matriceCopie[i], 0, taille);
    }

    /**
     * initialise les cellules à active (1) ou non (0)
     * en fonction de la densite d'activation (entre 0 et 1)
     *
     * @param densite densite de cellule à activer, entre entre 0(aucune cellule active) et 1(toutes cellules actives)
     */
    void initMatrice(double densite) {
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++)
                if (Math.random() < densite)
                    matrice[i][j] = 1;
    }

    /**
     * creation des cercles et de leurs couleurs en fonction de l'etat de leur cellule (cellule située aux même coordonnées (i,j))
     *
     * @param root ensemble des acteurs de la scène dans lequel les cercles seront ajoutés
     */
    void creationTroupe(Group root) {
        int rayon = espace / 2;
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++) {
                circles[i][j] = new Circle((i + .5) * espace, (j + .5) * espace, rayon);
                if (matrice[i][j] == 1) circles[i][j].setFill(Color.ANTIQUEWHITE);
                else circles[i][j].setFill(Color.DARKSLATEGRAY);
                root.getChildren().add(circles[i][j]);
            }
    }

    /**
     * evolution d'une cellule par rapport aux voisines/
     * Met à jour les valeurs dans matrice à partir des valeurs dans matriceCopie.
     *
     * @param x no de colonne de la cellule qui doit evoluer
     * @param y no de ligne de la cellule qui doit evoluer
     */
    void evoluer(int x, int y) {
        int nbVoisinesActives = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int xx = ((x + j) + taille) % taille;
                int yy = ((y + i) + taille) % taille;
                nbVoisinesActives = nbVoisinesActives + matriceCopie[yy][xx];
            }
        if (matriceCopie[y][x] == 1) {
            if (nbVoisinesActives > 3 || nbVoisinesActives < 2) {
                matrice[y][x] = 0;
                circles[y][x].setFill(Color.DARKSLATEGRAY);
            }
        } else {
            if (nbVoisinesActives == 3) {
                matrice[y][x] = 1;
                circles[y][x].setFill(Color.ANTIQUEWHITE);
            }
        }
    }

    /**
     * evolution de l'ensemble de la matrice
     */
    void evoluerMatrice() {
        for (int x = 0; x < taille; x++)
            for (int y = 0; y < taille; y++)
                evoluer(x, y);
    }

    /**
     * lancement du prog
     */
    public static void main(String[] args) {
        launch(args);
    }
}
```