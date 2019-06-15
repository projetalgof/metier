package miniville.metier;
import miniville.ihm.Controleur;
import java.util.ArrayList;

public class Joueur 
{
	private static int[] jetDe;
	private static int   sommeDe;
	private static int   nbJoueur;

	private String  nom;
	private int     numJoueur;
	private int     piece;
	private boolean aAcheter ; //assure que l'on peu effectuer un seul achat a la fois
	private boolean deuxJet ; //assure que l'on puisse rejeter les de qu'une fois
	// liste de carte posseder par le joueur
	private ArrayList<Carte> listCartes;

	public Joueur(String nom) 
	{
		if(this.jetDe == null) this.jetDe = new int[2];
		this.nom        = nom;
		this.listCartes = new ArrayList<Carte>();
		this.numJoueur  = Joueur.nbJoueur++;
	}
	//active les action de carte qui raporte des piece de la banque
	public void gain(Joueur joueurActif,Controleur ctrl) 
	{
		for(Carte carte : this.listCartes) 
		{
			if( !(carte instanceof CarteRouge) && !(carte instanceof Monument))
			{
				if ((carte.getDeclencheur().indexOf((Joueur.sommeDe + ""))) >= 0) carte.action(this,joueurActif,ctrl);
			}
		}
	}
	//active les action de carte qui rapporte des piece des autre joueur
	public void payer(Joueur joueurActif,Controleur ctrl)
	{
		for(Carte carte : this.listCartes) 
		{
			if(carte instanceof CarteRouge  && !(carte instanceof Monument))
			{
				if ((carte.getDeclencheur().indexOf((Joueur.sommeDe + ""))) >= 0)  carte.action(this,joueurActif,ctrl);
			}
		}
	}
	//jet un nombre de dé
	public void jetDe(int nbDe) 
	{
		int resultat = 0;
		this.jetDe[0] = this.jetDe[1] = 0;
		for (int i = 0; i < nbDe; i++)
		{
			this.jetDe[i] = (int)(Math.random() * 6 + 1);
			resultat += this.jetDe[i];
		}
		this.sommeDe=resultat;
	}
	//verifie si le dernier joueur a fait un double
	public boolean estUnDouble()
	{
		return this.jetDe[0]== this.jetDe[1] ; 
	}
	//ajoute une carte au joueur
	public void ajouterCarte(Carte carte) 
	{
		this.listCartes.add(carte);
	}
	public boolean aGagner()
	{
		boolean gagner ; 
		for(Carte carte : this.listCartes)
		{
			if(carte instanceof Monument && !((Monument)carte).getIsBuild())
				return false ;
		}
		return true ;

	}
	//verifie si la carte est present
	public boolean contains(String nom)
	{
    	for(Carte carte : this.listCartes)
    	{
    		if (carte.getNom().equals(nom)) return true ;
    	}
    	return false ; 
	}

	//----------------------------------------------------------------------------------------------------------------
	//                                             GERE LES MONUMENTS
	//verifie si le monument donner est actif
	public boolean monumentActif(String nom)
	{
		for(Carte carte : this.listCartes)
		{
			if(carte instanceof Monument && carte.getNom().equals(nom))
				return ((Monument)carte).getIsBuild();
		}
		return false; 
	}
	//active le monument donne
	public void activeMonument(String nom)
	{
		for(Carte carte : this.listCartes)
		{
			if(carte instanceof Monument && carte.getNom().equals(nom))
			{
				((Monument)carte).setIsBuild(true);
				break;
			}
		}
	}
	//----------------------------------------------------------------------------------------------------------------
	//                                             GET
	public String     getNom       ()       { return this.nom;      }
	public boolean    getAcheter   ()       { return this.aAcheter; }
	public boolean    getDeuxJet   ()       { return this.deuxJet;  }
	public int        getSommeDe   ()       { return this.sommeDe ; }
	public int        getPiece     ()       { return this.piece;    }
	public int        getNum       ()       { return this.numJoueur;}
	public int        getDe        (int de) { return this.jetDe[de];}

	//retourne une copie profonde de la list de carte
	public ArrayList<Carte> getListCartes() 
	{
		ArrayList<Carte> tmp = new ArrayList<Carte>();
		for (Carte carte : this.listCartes) 
		{
			tmp.add(carte);
		}
		return tmp;
	}
	//retourne le nombre de carte d'un mem type
	public int getNbCarte(String type)
	{
		int i = 0 ;
		for (Carte carte : this.listCartes)
		{
			if(carte.getType().equals(type)) i++ ;
		}
		return i ; 
	}
	//retourne le monument concerné
	public Monument getMonument(String nom )
	{
		for(Carte carte : this.listCartes)
		{
			if(carte instanceof Monument && carte.getNom().equals(nom))
			{
				return (Monument)carte ;
			}
		}
		return null ; 
	}
	//----------------------------------------------------------------------------------------------------------------
	//                                             SET
	//ajoute les piece a piece existente
	public void setPiece(int piece) 
	{
		this.piece += piece;
	}
	//en cas d'achat modifie le boolean
	public void setAcheter(boolean b)
	{
		this.aAcheter=b;
	}
	//en cas de 2eme jet
	public void setDeuxJet(boolean b)
	{
		this.deuxJet=b;
	}
	//modifie le jet de de DEBUG
	public void setJetDe(int a,int b)
	{
		if(Controleur.DEBUG)
		{
			this.jetDe[0]   = a ;
			this.jetDe[1]   = b ;
			this.sommeDe = a + b ;
		}
	}
}
