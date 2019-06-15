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

			for (int i = 0; i < monuments.length; i++) 
			{
				tmp = EnumCarte.valueOf(Regle.monuments[i]).creeCarte();
				if (tmp != null)
					joueur.ajouterCarte(tmp);
			}

			joueur.setPiece(Regle.PIECE_DEPART); // ajout du nombre de piece de depart
			tmp = champs;
			if (tmp != null)
				joueur.ajouterCarte(tmp);
			tmp = boulangerie;
			if (tmp != null)
				joueur.ajouterCarte(tmp);
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
				//verifie la class pour utilise le bon constructeur par recopie
				if     (carte instanceof CarteRouge)   tmp = new CarteRouge((CarteRouge)carte);
				else if(carte instanceof CarteVerte)   tmp = new CarteVerte((CarteVerte)carte);
				else if(carte instanceof CarteBleu)    tmp = new CarteBleu((CarteBleu)carte);
				else if(carte instanceof CarteViolet)  tmp = new CarteViolet((CarteViolet)carte);
				if(tmp != null )banque.ajouter(tmp);
			}
		}
	}
}
