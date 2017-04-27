package virtualwar;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Plateau {
	private int hauteur;
	private int largeur;
	private Cellule[][] grille;
	
	public Cellule[][] getGrille(){
		return grille;
	}
	
	public void setGrille(Cellule[][] grille){
		this.grille = grille;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	
	public Plateau(int hauteur, int largeur){
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.grille = new Cellule[hauteur+2][largeur+2];
		for(int i = 0; i < this.grille.length; i++){
			for(int y = 0; y < this.grille[0].length; y++){
				if((i==0)||(i == hauteur+1) || (y == 0) || (y == largeur+1)){
					grille[i][y] = new Case(i,y,true);
				}else{
					if(i==1 && y == 1){
						grille[i][y] = new Base(i,y,1);
					}else{
						if(i == hauteur && y == largeur){
							grille[i][y] = new Base(i,y,2);
						}else{
							grille[i][y] = new Case(i,y,false);
						}
					}
				}
			}
		}
	}/*
		+---+
        | x | 	
	    +---+
	
	
	
	
	*/
	
	public ArrayList<Cellule> caseBloque(){
		ArrayList<Cellule> listeBloque = new ArrayList<>();
		listeBloque.add(this.getGrille()[1][2]);
		listeBloque.add(this.getGrille()[2][1]);
		listeBloque.add(this.getGrille()[2][2]);
		listeBloque.add(this.getGrille()[this.getHauteur()-1][this.getLargeur()]);
		listeBloque.add(this.getGrille()[this.getHauteur()][this.getLargeur()-1]);		
		listeBloque.add(this.getGrille()[this.getHauteur()-1][this.getLargeur()-1]);
		//ajouter le chemin
		
		return listeBloque;

	}
	
	
	public void setObstacles(int chance){
		Random r = new Random();
		int nbCase = this.getHauteur()*this.getLargeur() - this.caseBloque().size();
		System.out.println(nbCase);
		int nbObstacle = nbCase *chance/100;
		System.out.println(nbObstacle);
		int cpt = nbObstacle;
		while(cpt>0){
			int x = r.nextInt(this.getHauteur()+1);
			int y = r.nextInt(this.getLargeur()+1);
			System.out.println(cpt);
			if(!this.getGrille()[x][y].getObstacle() && !this.caseBloque().contains(this.getGrille()[x][y])){
				this.getGrille()[x][y].setObstacle(true);
				cpt--;
			}
		}
		System.out.println(cpt);
	}

	public String toString() {
		String affichage = "";
		
		
		for(int i = 0; i < this.grille.length; i++){
			
			for(int y = 0; y < this.grille[0].length; y++){
				affichage+="+---";
			}
			
			affichage+="+\n";
			
			for(int y = 0; y < this.grille[0].length; y++){
				affichage+="| "+ this.getGrille()[i][y].toString() + " ";
			}
			
			
			affichage += "|\n";
		}
		
		for(int y = 0; y < this.grille[0].length; y++){
			affichage+="+---";
		}
		
		return affichage+"+";
	}
	
	@SuppressWarnings("resource")
	public int choixChanceObstacle(){
		Scanner sc = new Scanner(System.in);
		boolean choixOK = false;
		String choix = "";
		while(!choixOK){
			System.out.println("Choississez un pourcentage d'obstacle");
			choix = sc.nextLine();
			System.out.println(Integer.parseInt(choix));
			if(Integer.parseInt(choix)>0 && Integer.parseInt(choix)< 101){
				choixOK = true;
			}
		}
		return Integer.parseInt(choix);
	}
}