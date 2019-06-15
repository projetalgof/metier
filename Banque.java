package miniville.metier;
import java.util.ArrayList;
public class Banque
{
	private ArrayList<Carte> cartes ;

	public Banque()
	{
		this.cartes = new ArrayList<Carte>();
	}
	//ajoute une carte
	public void ajouter(Carte carte)
	{
		this.cartes.add(carte);
	}
	//retire une carte precise
	public Carte retirer(String nomCarte)
	{
		Carte tmp =null;
		for(Carte carte : cartes)
		{
			if(carte.getNom().equals(nomCarte))
			{
				tmp=carte ;
				break;
			}
		}
		if(tmp !=null)
		{
			this.cartes.remove(tmp);
			return tmp ;
		}
		return null ;
	}
	//consulte la carte pour avoir les infos
	public Carte consulter(String nom)
	{
		for(Carte carte : this.cartes)
    	{
    		if (carte.getNom().equals(nom)) return carte;
    	}
    	return null; 
	}
	//verifie si la carte est present
	public boolean contains(String nom)
	{
    	for(Carte carte : this.cartes)
    	{
    		if (carte.getNom().equals(nom)) return true ;
    	}
    	return false ; 
	}
	//retourne un copie profonde de la liste
	public ArrayList<Carte> getListCartes() 
	{
		ArrayList<Carte> tmp = new ArrayList<Carte>();

    	for(Carte carte : this.cartes)
    	{
    		tmp.add(new Carte (carte));
    	}
    	return tmp ; 
	}

}