package miniville.metier;
import miniville.ihm.Controleur;
import java.util.*;

public class Carte implements Comparable<Carte> 
{
	protected String declencheur; // String pour gerais les declencheur multiple
	protected String nom   ;
	protected String type  ;
	protected int    cout  ;
	protected int    piece ; 

	public Carte(String declencheur, String nom, String type,int cout,int piece) 
	{
		this.declencheur = declencheur;
		this.nom         = nom   ;
		this.type        = type  ;
		this.cout        = cout  ;
		this.piece       = piece ;
	}

	// constructeur par recopie
	protected Carte(Carte autreCarte) 
	{
		this(autreCarte.declencheur,autreCarte.nom,autreCarte.type,autreCarte.cout,autreCarte.piece);
	}
	public static Carte recopieCarte(Carte autreCarte)
	{
		Carte tmp =null;
		if     (autreCarte instanceof CarteRouge)   tmp = new CarteRouge((CarteRouge)autreCarte);
		else if(autreCarte instanceof CarteVerte)   tmp = new CarteVerte((CarteVerte)autreCarte);
		else if(autreCarte instanceof CarteBleu)    tmp = new CarteBleu((CarteBleu)autreCarte);
		else if(autreCarte instanceof CarteViolet)  tmp = new CarteViolet((CarteViolet)autreCarte);
		else if(autreCarte instanceof Monument)     tmp = new Monument((Monument)autreCarte);
		return tmp ; 
	}

	//activation de l'effet carte
	public void action(Joueur propietaire,Joueur joueurActif,Controleur ctrl){}

	public int compareTo(Carte c) 
	{
		return this.nom.compareTo(c.nom);
	}

	//----------------------------------------------------------------------------------------------------------------
	//                                             GET
	public String getDeclencheur () { return this.declencheur; }
	public int    getPiece       () { return this.piece ;      }
	public String getNom         () { return this.nom;         }
	public String getType        () { return this.type;        }
	public int    getCout        () { return this.cout;        }

}
