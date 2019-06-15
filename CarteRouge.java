package miniville.metier;
import miniville.ihm.Controleur;
public class CarteRouge extends Carte
{
	public CarteRouge(String declencheur, String nom, String type, int cout,int piece)
	{
		super(declencheur, nom, type, cout,piece);
	}
	public CarteRouge(CarteRouge carte)
	{
		super(carte);
	}
	//effet de la carte
	public void action(Joueur propietaire,Joueur joueurActif,Controleur ctrl)
	{
		int nbPiece=0;
		int piece ;
		//verifie si le centre comercial est actif
		if(propietaire.monumentActif("centre commercial")) 
			piece=this.piece+1;
		else piece=this.piece ;
		//si le joueur actif a sufisament de piece
		if (joueurActif.getPiece() - piece >= 0) 
		{
			joueurActif.setPiece(-piece) ;
			propietaire.setPiece( piece) ;
			nbPiece=piece;
		} 
		else 
		{
			propietaire.setPiece( joueurActif.getPiece()) ;
			joueurActif.setPiece(-joueurActif.getPiece()) ;
			nbPiece=joueurActif.getPiece();
		}
		ctrl.effetCartePaimment(joueurActif.getNom(),propietaire.getNom(),this.getNom(),nbPiece);
	}
}