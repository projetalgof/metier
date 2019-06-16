package miniville.metier;
import java.util.ArrayList;

public class Regle 
{
	// regle de base
	public static final int PIECE_DEPART = 3;
	private static final int NB_CARTE_ETEABLISSEMENT_BASE = 6;
	private static final int NB_CARTE_ETEABLISSEMENT_SPECIAL = 4;
	// String de toute les cartes qu'on veux ajouter au jeu
	private static final String[] etablisementBase = { "champs", "ferme", "boulangerie", "cafe", "superette", "foret",
			"fromagerie", "fabriqueMeuble", "mine", "restaurant", "verger", "marcherLegume" };
	private static final String[] etablisementSpecial = { "stade", "chaineTV", "centreAffaire" };
	//list des monument a cree et a posseder pour gagner
	private static final String[] monuments = {"gare","centreCom","parcAtraction","tourRadio"};

	public static void initialisation(ArrayList<Joueur> joueurs, Banque banque) 
	{

		// creation de la banque
		// etablissement de base
		for (int i = 0; i < Regle.etablisementBase.length; i++) 
		{
			Carte tmp = EnumCarte.valueOf(Regle.etablisementBase[i]).creeCarte();
			Regle.creeCarte(banque, tmp, Regle.NB_CARTE_ETEABLISSEMENT_BASE);
		}
		// etablissement special
		for (int i = 0; i < Regle.etablisementSpecial.length; i++) 
		{
			Carte tmp = EnumCarte.valueOf(Regle.etablisementSpecial[i]).creeCarte();
			Regle.creeCarte(banque, tmp, Regle.NB_CARTE_ETEABLISSEMENT_SPECIAL);
		}

		// cree les carte donner au joueur au debut
		Carte champs = EnumCarte.champs.creeCarte();
		Carte boulangerie = EnumCarte.boulangerie.creeCarte();
		Carte tmp;
		for (Joueur joueur : joueurs) 
		{
			//cree et donne les monuments a joueru
			for (int i = 0; i < monuments.length; i++) 
			{
				tmp = EnumCarte.valueOf(Regle.monuments[i]).creeCarte();
				if (tmp != null)
					joueur.ajouterCarte(tmp);
			}

			joueur.setPiece(Regle.PIECE_DEPART); // ajout du nombre de piece de depart
			if (champs != null)
				joueur.ajouterCarte(champs);
			if (boulangerie != null)
				joueur.ajouterCarte(boulangerie);
		}

	}

	// cree et ajoute un certain nombre de carte a la banque
	private static void creeCarte(Banque banque, Carte carte, int nb) 
	{
		Carte tmp =null;
		if(carte != null )
		{
			for (int i = 0; i < nb; i++) 
			{
				tmp=Carte.recopieCarte(carte);
				if(tmp != null )banque.ajouter(tmp);
			}
		}
	}
}
