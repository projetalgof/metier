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
		if(Joueur.jetDe == null) Joueur.jetDe = new int[2];
		this.nom        = nom;
		this.listCartes = new ArrayList<Carte>();
		this.numJoueur  = Joueur.nbJoueur++;
	}
	//active les action de carte qui raporte des piece de la banque
	public void gain(Joueur joueurActif,Controleur ctrl) 
	{
		String de ; 
		ArrayList<Carte> tmpList = this.copyArrayList();
        for(Carte carte : tmpList)
        {
			if( !(carte instanceof CarteRouge) && !(carte instanceof Monument))
			{	
				if(Joueur.sommeDe < 10) de ="0"+Joueur.sommeDe;
				else de=Joueur.sommeDe+"";
				if ((carte.getDeclencheur().indexOf((de))) >= 0) carte.action(this,joueurActif,ctrl);
			}
		}
	}
	//active les action de carte qui rapporte des piece des autre joueur
	public void payer(Joueur joueurActif,Controleur ctrl)
	{
		String de ; 
		for(Carte carte : this.listCartes) 
		{
			if(carte instanceof CarteRouge  && !(carte instanceof Monument))
			{
				if(Joueur.sommeDe < 10) de ="0"+Joueur.sommeDe;
				else de=Joueur.sommeDe+"";
				if ((carte.getDeclencheur().indexOf((de))) >= 0)  carte.action(this,joueurActif,ctrl);
			}
		}
	}
	// remove un Carte
    public void removeUnCarte(Carte autre)
    {
        int indexRemove = -1;
        for(Carte tmp: this.listCartes)
            if(tmp.getNom().equals(autre.getNom()))
            {
                indexRemove = this.listCartes.indexOf(tmp);
                break;
            }
        if(-1 != indexRemove)
            this.listCartes.remove(indexRemove);
    }

    public Carte retirerCarte(String nom)
    {
    	Carte tmp =null;
        for(Carte carte: this.listCartes)
            if(carte.getNom().equals(nom)) tmp=carte;

        if(tmp != null)this.listCartes.remove(tmp);
        return tmp;
    }
	//jet un nombre de dé
	public void jetDe(int nbDe) 
	{
		int resultat = 0;
		Joueur.jetDe[0] = Joueur.jetDe[1] = 0;
		for (int i = 0; i < nbDe; i++)
		{
			Joueur.jetDe[i] = (int)(Math.random() * 6 + 1);
			resultat += Joueur.jetDe[i];
		}
		Joueur.sommeDe=resultat;
	}
	//verifie si le dernier joueur a fait un double
	public boolean estUnDouble()
	{
		return Joueur.jetDe[0] == Joueur.jetDe[1] ; 
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

	// retourne une copie de surface
	private ArrayList<Carte> copyArrayList() 
	{
		ArrayList<Carte> tmp = new ArrayList<Carte>();
		for (Carte carte : this.listCartes) 
		{
			tmp.add(carte);
		}
		return tmp;
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
	public String     getNom       ()       { return this.nom;        }
	public boolean    getAcheter   ()       { return this.aAcheter;   }
	public boolean    getDeuxJet   ()       { return this.deuxJet;    }
	public int        getSommeDe   ()       { return Joueur.sommeDe ; }
	public int        getPiece     ()       { return this.piece;      }
	public int        getNum       ()       { return this.numJoueur;  }
	public int        getDe        (int de) { return Joueur.jetDe[de];}

	//retourne une copie profonde de la list de carte
	public ArrayList<Carte> getListCartes() 
	{
		ArrayList<Carte> tmp = new ArrayList<Carte>();
		for (Carte carte : this.listCartes) 
		{
			tmp.add(Carte.recopieCarte(carte));
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
			Joueur.jetDe[0]   = a ;
			Joueur.jetDe[1]   = b ;
			Joueur.sommeDe = a + b ;
		}
	}
}
