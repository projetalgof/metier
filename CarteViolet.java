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
			ctrl.effetViolet(this.nom);
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
				String nomCarte;
				//controle et enrengistre le joueur choisie
				Joueur joueurChoisit = ctrl.getMetier().choisitUnJoueur(joueurActif);
				//affiche letat du joueur choisi
				ctrl.afficherEtatJoueur(joueurChoisit.getListCartes(),joueurChoisit.getNom(),joueurChoisit.getPiece());
				//affiche demande
				ctrl.donnerLeCarte(joueurActif.getNom());
				//controle et enrengistre la carte choisi a echanger
				carteChoisit = ctrl.getMetier().choisitUnCarte(joueurChoisit);
				nomCarte=carteChoisit.getNom();
				//on donne la carte au joueur actif
				joueurActif.ajouterCarte(carteChoisit);
				//on suprime la carte au joueur choisi
				joueurChoisit.removeUnCarte(carteChoisit);
				
				//affiche la demande
				ctrl.donnerLeCarte(joueurChoisit.getNom());
				//affiche letat du joueur actif
				ctrl.afficherEtatJoueur(joueurActif.getListCartes(),joueurActif.getNom(),joueurActif.getPiece());
				carteChoisit =  ctrl.getMetier().choisitUnCarte(joueurActif);
				//on donne la carte au joueur choisi
				joueurChoisit.ajouterCarte(carteChoisit);
				joueurActif.removeUnCarte(carteChoisit);

				ctrl.echange(joueurActif.getNom(),nomCarte,joueurChoisit.getNom(),carteChoisit.getNom());
				
			}
				
		}
	}
	

}