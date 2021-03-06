package graphique;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import virtualwar.*;

//le plateau est constitué d'une hauteur, d'une largeur et d'une grille de cellule sur laquelle se déroule la partie
public class PlateauG extends Application {
	
	Plateau plat = new Plateau(10,10);
	GridPane tableau;
	
	public void start(Stage stage){
		//final String imageURI = new File("/home/infoetu/salvaoc/Images/moustache.png").toURI().toString();
		Image img = new Image("file:/home/infoetu/salvaoc/Images/sad_frog.jpg");
		ImageView imgvue = new ImageView();
		imgvue.setImage(img);
		imgvue.setFitHeight(100);
		imgvue.setFitWidth(100);
		
		HBox fenetre = new HBox();
		tableau = new GridPane();
		tableau.setMaxSize(500, 500);
		
		for(int i = 0; i < plat.getHauteur()+2;i++){
			for(int y = 0; y < plat.getLargeur()+2;y++){
				//Label label =  new Label();
				//label.setText(plat.getGrille()[i][y].toString());
				//label.setStyle("-fx-font : 34px Verdana");
				Canvas c = new Canvas(60,60);
				GraphicsContext gc = c.getGraphicsContext2D();
				gc.drawImage(img,0,0);
			
				tableau.add(c, 1, 1);
			}
		}
		
		fenetre.getChildren().add(tableau);
		Scene s = new Scene(fenetre,500,500);
		stage.setScene(s);
		stage.show();
	}
	

	
	public static void main(String[] args){
		Application.launch(args);
	}
	
}