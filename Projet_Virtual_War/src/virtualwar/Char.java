package virtualwar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * la classe char qui h�rite de la classe Robot cr�er un Robot qui ne peut se d�placer qu'en ligne sur 2 cases et de tirer 
 * @author salvadoc
 *
 */
public class Char extends Robot {
	
	private final static int ENERGIEDEBASEC = 60;
	private static int coutAction = 1;
	private static int coutDeplacement = 5;
	private static int degatSubis = 6;
	private static String type = "C";
	private int portee;
	
	/**
	 * Contructeur du Char
	 * @param vue vue du char en fonction de l'�quipe
	 * @param l largeur de la coordonn�e initial du char
	 * @param h hauteur de la coordonn�e initial du char
	 * @param equipe �quipe du char
	 * @param portee port�e du char
	 */
	public Char(Vue vue, int l ,int h,int equipe,int portee){
		super(vue,l,h,equipe,type,ENERGIEDEBASEC);
		this.portee = portee;
	}	

	
	public int getCoutAction(){
		return Char.coutAction;
	}
	

	public int getCoutDeplacement(){
		return Char.coutDeplacement;
	}
	

	public int getDegatSubis(){
		return Char.degatSubis;
	}
	
	public int getEnergieDeBase(){
		return Char.ENERGIEDEBASEC;
	}
	
	public int getPortee(){
		return this.portee;
	}
	
	public List<Coordonnees> getDeplacements(){
		ArrayList<Coordonnees> listedep = new ArrayList<>();
		ArrayList<Coordonnees> listedirection = new ArrayList<>();
		listedirection.add(Constante.HAUT);
		listedirection.add(Constante.BAS);
		listedirection.add(Constante.DROITE);
		listedirection.add(Constante.GAUCHE);
		Coordonnees temp;
		Coordonnees temp2;
		for(Coordonnees direction : listedirection){
			temp = new Coordonnees(this.getCoordonnees().getHauteur(),this.getCoordonnees().getLargeur());
			temp.ajoute(direction);
			if(this.getVue().estDisponible(temp)){
				temp2 = new Coordonnees(temp.getHauteur(),temp.getLargeur());
				temp2.ajoute(direction);
				if(this.getVue().estDisponible(temp2)){
					listedep.add(temp2);
				}else{
					listedep.add(temp);
				}
			}
		}
		return listedep;
	}
	@SuppressWarnings("resource")
	public Coordonnees choixMouvement(){
		Coordonnees res = new Coordonnees(-1,-1);
		String choix;
		boolean correct= false;
		Scanner sc = new Scanner(System.in);
		Coordonnees temp;
		while(!correct){
			System.out.println("Dans quelle direction voulez vous vous deplacer ?");
			System.out.println("1.En HAUT 2.En BAS 3.A GAUCHE 4.A DROITE");
			choix = sc.nextLine();
			if(choix.equals("1")){
				res = new Coordonnees(Constante.HAUT.getHauteur(),Constante.HAUT.getLargeur());
				res.ajoute(Constante.HAUT);
				temp = new Coordonnees(this.getCoordonnees().getHauteur()+res.getHauteur(),this.getCoordonnees().getLargeur()+res.getLargeur());
				if(!this.estDans(temp)){
					res.ajoute(Constante.BAS);
				}
				correct = true;
			}
			if(choix.equals("2")){
				res = new Coordonnees(Constante.BAS.getHauteur(),Constante.BAS.getLargeur());
				res.ajoute(Constante.BAS);
				temp = new Coordonnees(this.getCoordonnees().getHauteur()+res.getHauteur(),this.getCoordonnees().getLargeur()+res.getLargeur());
				
				if(!this.estDans(temp)){
					res.ajoute(Constante.HAUT);
				}
				correct = true;
			}
			if(choix.equals("3")){
				res = new Coordonnees(Constante.GAUCHE.getHauteur(),Constante.GAUCHE.getLargeur());
				res.ajoute(Constante.GAUCHE);
				temp = new Coordonnees(this.getCoordonnees().getHauteur()+res.getHauteur(),this.getCoordonnees().getLargeur()+res.getLargeur());

				if(!this.estDans(temp)){
					res.ajoute(Constante.DROITE);
				}
				correct = true;
			}
			if(choix.equals("4")){
				res = new Coordonnees(Constante.DROITE.getHauteur(),Constante.DROITE.getLargeur());
				res.ajoute(Constante.DROITE);
				temp = new Coordonnees(this.getCoordonnees().getHauteur()+res.getHauteur(),this.getCoordonnees().getLargeur()+res.getLargeur());
				if(!this.estDans(temp)){
					res.ajoute(Constante.GAUCHE);
				}
				correct = true;
			}
		}
		return res;
	}

	
	/**
	 * obtenir la coordonn�e de tir possible dans une direction donn�e  
	 * @param Direction dans laquelle on cherche un tir possible
	 * @return une coordonn�e dans laquelle ont peut tirer
	 */
	public Coordonnees CoordsTir(Coordonnees Direction){
		int cpt = 0;
		Coordonnees memoire = new Coordonnees(this.getCoordonnees().getHauteur(),this.getCoordonnees().getLargeur());
		while(cpt < this.portee){
			memoire.ajoute(Direction);
			if(!this.getVue().estDisponible(memoire)){
				// R�cup�re l'information : est ce que la cellule contient un robot ?
				if(this.getVue().getPlateau().getGrille()[memoire.getHauteur()][memoire.getLargeur()].contientRobot){ 
					//R�cup�re l'information est ce que le robot est de l'�quipe ennemi ?
					if(this.getVue().getPlateau().getGrille()[memoire.getHauteur()][memoire.getLargeur()].getContenu().getEquipe() != this.getEquipe() ){
						return memoire;
					}
					else{
						return new Coordonnees(0,0);}
				}
				else{
					return new Coordonnees(0,0);}
			}
			cpt++;
		}
		return new Coordonnees(0,0); 
	}
	
	/**
	 * obtenir la liste des coordonn�es pour lesquelles le tir est possible
	 * @return liste des coordonn�es ciblables
	 */
	public ArrayList<Coordonnees> getCibles(){
		ArrayList <Coordonnees> listeTir = new ArrayList<>();
		Coordonnees Coordnulle = new Coordonnees(0,0); // Coordonn�es a refuser
		if(!this.CoordsTir(Constante.BAS).equals(Coordnulle)){
			listeTir.add(CoordsTir(Constante.BAS));
		}
		if(!this.CoordsTir(Constante.HAUT).equals(Coordnulle)){
			listeTir.add(CoordsTir(Constante.HAUT));
		}
		if(!this.CoordsTir(Constante.DROITE).equals(Coordnulle)){
			listeTir.add(CoordsTir(Constante.DROITE));
		}
		if(!this.CoordsTir(Constante.GAUCHE).equals(Coordnulle)){
			listeTir.add(CoordsTir(Constante.GAUCHE));
		}
		return listeTir;
	}
	

	@SuppressWarnings("resource")
	public Coordonnees choixCible(){
		Scanner sc = new Scanner(System.in);
		String choix = "";
		boolean choixOK = false;
		if(this.getCibles().isEmpty()){
			System.out.println("Vous n'avez pas de cible � attaquer");
		}else{
			System.out.println("Voici les cibles potentielles de ce robot : " + this.getCibles());
			while(!choixOK){
				System.out.println("Quelle cible voulez vous attaquer ? (Entrez le num�ro de la n-i�me coordonn�e du robot � attaquer)");
				choix = sc.nextLine();
				if(Integer.parseInt(choix) > 0 && Integer.parseInt(choix)<this.getCibles().size()+1){
					choixOK = true;
				}
			}
			return this.getCibles().get(Integer.parseInt(choix)-1);
		}
		return  this.getCoordonnees();
	}
	public void attaque(){
		if(this.getEnergie()<this.getCoutAction()){
			System.out.println("Votre robot n'a pas assez d'�nergie pour attaquer");
		}else{
			Attaque a = new Attaque(this,this.choixCible());
			a.agit();
		}
		
	}
	
	public String toString(){
		String nom = "Char de l'equipe "+this.getEquipe()+", ";
		if(this.getInvoque()){
			nom += this.getCoordonnees().toString()+", Vie : "+this.getEnergie()+"/"+ENERGIEDEBASEC;
		}
		return nom;
	}


	public void regenEnergie() {
		this.setEnergie(this.getEnergie()+2); 
	}

}

