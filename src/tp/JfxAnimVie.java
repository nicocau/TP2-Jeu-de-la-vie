package tp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JfxAnimVie extends Application {

    Matrice matrice;

    public static Circle[][] circles;

    int espace = 10;

    private int taille;

    private double densite;

    private int tempo;

    @Override
    public void start(Stage primaryStage) {
        taille = 20 * 2;
        densite = 0.5;
        tempo = 100;
        construireScenePourJeuDeLaVie(primaryStage);
    }

    void construireScenePourJeuDeLaVie(Stage primaryStage) {
        int largeur = 80 * 4;
        int hauteur = 80 * 4;
        espace = 4 * 2;
        //definir la scene principale
        Group root = new Group();
        Scene scene = new Scene(root, largeur, hauteur, Color.BLACK);
        primaryStage.setTitle("Life...");
        primaryStage.setScene(scene);
        //definir les acteurs
        matrice = new Matrice(taille, densite);
        //definir les costumes
        JfxAnimVie.circles = new Circle[taille][taille];
        //habiller les acteurs
        dessinMatrice(root);

        //afficher le theatre
        primaryStage.show();
        //-----lancer le timer pour faire vivre la matrice
        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(tempo),
                event -> {
                    matrice.avancer();
                    matrice.animGrille();
                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
        scene.setOnKeyReleased(ke -> {
            switch (ke.getText()) {
                case "p":
                case "P":
                    littleCycle.pause();
                    break;
                case "d":
                case "D":
                    littleCycle.play();
                    break;
                case "e":
                case "E":
                    matrice.clear();
                    break;
                case "h":
                case "H":
                    matrice.initHasard();
                    break;
            }
        });
    }

    void dessinMatrice(Group root) {
        Cellule[][] grille = matrice.getGrille();
        int rayon = espace / 2;
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++) {
                Cellule cell = grille[i][j];
                circles[i][j] = new Circle(i * espace + rayon, j * espace + rayon, rayon);
                cell.setCercle(circles[i][j]);
                if (cell.isVivante()) circles[i][j].setFill(Cellule.COUL_ACTIVE);
                else circles[i][j].setFill(Cellule.COUL_DESACTIVE);
                root.getChildren().add(circles[i][j]);

                circles[i][j].setOnMouseClicked(me -> cell.cliked());

            }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
