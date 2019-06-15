package miniville.metier;
import miniville.ihm.Controleur;
public class CarteVerte extends Carte
{
	private String typeMultiple;

	public CarteVerte(String declencheur, String nom, String type, int cout,int piece,String typeMultiple) 
	{
		super(declencheur, nom, type, cout,piece);
		this.typeMultiple = typeMultiple;

	}
	public CarteVerte(CarteVerte carte)
	{
		super(carte);
		this.typeMultiple = carte.typeMultiple;
	}

	public void action(Joueur propietaire,Joueur joueurActif,Controleur ctrl)
	{
		if(propietaire == joueurActif)
		{
			//verifie si l'effet a un multiple a faire
			if(this.typeMultiple == null)
			{
				propietaire.setPiece(this.piece);
				ctrl.effetCarte(propietaire.getNom(),this.nom,this.piece);
			}
			else
			{
				int facteur = propietaire.getNbCarte(this.typeMultiple) ;
				propietaire.setPiece(this.piece * facteur) ;
				ctrl.effetCarte(propietaire.getNom(),this.nom,this.piece * facteur);
			} 
		}
	}
}