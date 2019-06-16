package miniville.metier;
import miniville.ihm.Controleur;
public class CarteViolet extends Carte
{
	private String typeMultiple;

	public CarteViolet(String declencheur, String nom, String type, int cout,int piece,String typeMultiple) 
	{
		super(declencheur, nom, type, cout,piece);
		this.typeMultiple = typeMultiple;

	}
	public CarteViolet(CarteViolet carte)
	{
		super(carte);
		this.typeMultiple = carte.typeMultiple;
	}

	public void action(Joueur propietaire,Joueur joueurActif,Controleur ctrl)
	{
		if(propietaire == joueurActif)
		{
			//verifie si l'effet a un multiple a faire
			if("stade".equals(this.nom))
				for(Joueur tmp : ctrl.getMetier().getJoueurs())
				{
					int nbPiece=0;
					if(!tmp.equals(joueurActif))
					{
						if (tmp.getPiece() - this.piece >= 0) 
						{
							tmp.setPiece(-this.piece) ;
							propietaire.setPiece( this.piece) ;
							nbPiece=this.piece;
						} 
						else 
						{
							propietaire.setPiece( tmp.getPiece()) ;
							nbPiece=tmp.getPiece();
							tmp.setPiece(-tmp.getPiece()) ;
						}
						ctrl.effetCartePaimment(tmp.getNom(),propietaire.getNom(),this.getNom(),nbPiece);	
					}
				}
			else if("chaine TV".equals(this.nom))
			{
				Joueur tmp = ctrl.getMetier().choisitUnJoueur(joueurActif);
				int nbPiece=0;
				if (tmp.getPiece() - this.piece >= 0) 
				{
					tmp.setPiece(-this.piece);
					nbPiece=this.piece;
					joueurActif.setPiece(this.piece);
				}
				else
				{
					joueurActif.setPiece( tmp.getPiece()) ;
					nbPiece=tmp.getPiece();
					tmp.setPiece(-tmp.getPiece()) ;
				}
				ctrl.effetCartePaimment(tmp.getNom(),propietaire.getNom(),this.getNom(),nbPiece);	
			}
			else if("centre d'affaire".equals(this.nom))
			{
				Carte carteChoisit = null;
				Joueur joueurChoisit = ctrl.getMetier().choisitUnJoueur(joueurActif);
				
				ctrl.donnerLeCarteAQu(joueurActif.getNom());
				carteChoisit = ctrl.getMetier().choisitUnCarte(joueurChoisit);
				joueurActif.ajouterCarte(carteChoisit);
				joueurChoisit.getListCartes().remove(carteChoisit);
				
				ctrl.donnerLeCarteAQu(joueurChoisit.getNom());
				carteChoisit =  ctrl.getMetier().choisitUnCarte(joueurActif);
				joueurChoisit.ajouterCarte(carteChoisit);
				joueurActif.getListCartes().remove(carteChoisit);
				
			}
				
		}
	}
	

}