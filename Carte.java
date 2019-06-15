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
	public Carte(Carte autreCarte) 
	{
		this.declencheur = autreCarte.declencheur;
		this.nom         = autreCarte.nom;
		this.type        = autreCarte.type;
		this.cout        = autreCarte.cout;
		this.piece       = autreCarte.piece;
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
