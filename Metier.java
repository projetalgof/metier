package miniville.metier;
import miniville.ihm.Controleur;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Metier 
{
  private Controleur ctrl;
  private boolean isEnd; // pour la savoir la fin de parti
  private ArrayList<Joueur> joueurs; // list des joueur dans la parti
  private Joueur joueurActif; // tour du joueur
  private Banque banque; // banque du jeu

  public Metier(Controleur ctrl, int nbJoueur) 
  {
    boolean chargement = nbJoueur >1 ;
    File file ;
    //initialise
    this.ctrl    = ctrl;
    this.isEnd   = false;
    this.joueurs = new ArrayList<Joueur>();
    this.banque  = new Banque();

    if(chargement)
    {
      //creation des joueurs
      for (int i = 0; i < nbJoueur; i++) 
      {
        this.joueurs.add(new Joueur(ctrl.creeJoueur()));    //creation de la banque et distribution des carte au joueurs
      }
      Regle.initialisation(this.joueurs, this.banque); 
    }
    else 
    {
      //verifie si le fichier donner existe bien
      do
        file = new File(this.ctrl.nomFichier()+".txt");
      while(!file.exists());
        this.chargement(file);
    }
  }
  //methode qui fait tourner le jeu
  public void jouer() 
  {
    char choix ;
    //premiere fois , on initialise le joueur a celui qui commence
    if (joueurActif == null) 
    {
      String joueurCommence = this.ctrl.commence();
      for (Joueur joueur : this.joueurs) 
      {
        if (joueur.getNom().equals(joueurCommence))
          joueurActif = joueur;
      }
    }
    this.ctrl.transition(this.joueurActif.getNom());
    this.lanceDe();

    do
    {
      this.ctrl.afficherEtatJoueur(this.joueurActif.getListCartes(),this.joueurActif.getNom(),this.joueurActif.getPiece());
      choix=this.ctrl.choix();
      switch(choix)
      {
        case 'A' : this.achatEtablissement();
          break;
        case 'B' : this.ctrl.afficherBanque();
          break;
        case 'M' : this.achatMonument();
          break;
        case 'R' : this.deuxJet();
          break;
        case 'Z' : if(Controleur.DEBUG) this.joueurActif.setPiece(30);
          break;
      }
    }
    while(choix != 'P' && !this.joueurActif.getAcheter());
    this.ctrl.afficherEtatJoueur(this.joueurActif.getListCartes(),this.joueurActif.getNom(),this.joueurActif.getPiece());
    //on reinitialise l'achat du joueur et on change de joueur
    this.joueurActif.setAcheter(false);
    this.joueurActif.setDeuxJet(false);
    //on verifie si le joueur a gagner
    if(!this.joueurActif.aGagner())
    {
      //on verifie si le joueur a le parc d'atraction et si il a fait un double
      if(this.joueurActif.monumentActif("parc d'attraction") && this.joueurActif.estUnDouble())
      {
        this.ctrl.rejouer(this.joueurActif.getNom());
      }
      else this.joueurActif=joueurs.get((joueurs.indexOf(joueurActif)+1)%joueurs.size());
    }
    else
    {
      this.isEnd = true ;
      this.ctrl.gagner(this.joueurActif.getNom());
    }

  }
  //private meethode
  //chargemement d'un parti a partir du nom de fichier
  private void chargement(File file)
  {
    try
    {
      Scanner sc = new Scanner (file);
      String[] chaine ;

      while ( sc.hasNextLine() )
      {
        chaine = sc.nextLine().split(":");
        if(chaine[0].equals("joueur"))
        {
          if(this.joueurs.isEmpty())
          {
            this.joueurs.add(new Joueur(chaine[1]));
          }
          else
          {
            Joueur tmp = this.rechercherJoueur(chaine[1]);
            if(tmp == null)
              this.joueurs.add(new Joueur(chaine[1]));
          }
        }
      }
      sc.close();
    }
    catch(Exception e) { e.printStackTrace(); }

    Regle.initialisation(this.joueurs, this.banque);  //creation de la banque et distribution des carte au joueurs

    try
    {
      Scanner sc2 = new Scanner ( file );
      String[] chaine ;
      while ( sc2.hasNextLine() )
      {
        chaine = sc2.nextLine().split(":");
        if(chaine.length >1)
        {
          if(chaine[1].equals("carte"))
          {
            for(int i = 2 ; i<chaine.length;i=i+2)
            {
              for(int j = 0; j < new Integer(chaine[i+1]) ;j++)
              {
                Carte tmp = this.banque.retirer(chaine[i]);
                if(tmp != null )this.getJoueur(chaine[0]).ajouterCarte(tmp);
              }
            }
          }
          if(chaine[1].equals("piece"))
          {
            this.getJoueur(chaine[0]).setPiece(-Regle.PIECE_DEPART + new Integer(chaine[2]));
          }
          if(chaine[1].equals("monument"))
          {
            for(int i =2;i<chaine.length;i++)
            {
              this.getJoueur(chaine[0]).activeMonument(chaine[i]);
            }
          }
        }
        if(chaine[0].equals("tour"))
        {
          this.joueurActif=this.getJoueur(chaine[1]);
        }
      }
      sc2.close();
    }
    catch(Exception e) { e.printStackTrace(); }
  }

  private void deuxJet()
  {
    if(this.joueurActif.monumentActif("tour de radio") && !this.joueurActif.getDeuxJet())
    {
      this.lanceDe();
      this.joueurActif.setDeuxJet(true);
    }
    else this.ctrl.erreurLanceDe();
  }
  //lance les de
  private void lanceDe()
  {
    char choix ;
    if(Controleur.DEBUG) this.joueurActif.setJetDe(this.ctrl.debugJetDe(),this.ctrl.debugJetDe());
    else
    {
      //regarde si le monument gare est actif
      if(this.joueurActif.monumentActif("gare"))
      {
        choix=this.ctrl.choixDe();
        if(choix== '1') this.joueurActif.jetDe(1);
        else            this.joueurActif.jetDe(2);
      }
      else this.joueurActif.jetDe(1);
    }
    this.ctrl.jetDe(this.joueurActif.getDe(0),this.joueurActif.getDe(1));
    //les perte et gain de piece
    this.payer();
    this.gain();
  }
  //gere l'achat de nouvelle etablissement
  private void achatEtablissement()
  {
    String achat = this.ctrl.achatEtablissement();
    if(!achat.equals(""))
    {
      if(this.joueurActif.getPiece() >= banque.consulter(achat).getCout())
      {
        //verifie que la carte acheter et violet et si oui non poseder
        if(banque.consulter(achat) instanceof CarteViolet && this.joueurActif.contains(achat))
        {
          this.ctrl.achatVioletErreur();
        }
        else
        {
          Carte tmp = this.banque.retirer(achat);
          if(tmp != null )
          {
            this.joueurActif.ajouterCarte(tmp);
            this.joueurActif.setPiece(-tmp.getCout());
            this.joueurActif.setAcheter(true);
            this.ctrl.achatValide(this.joueurActif.getNom(),tmp.getNom(),tmp.getCout());
          } 
        }
      }
      else this.ctrl.achatErreur();
    }
  }
  //gere l'achat des monument
  private void achatMonument()
  {
    String achat = this.ctrl.achatMonument();
    if(!achat.equals(""))
    {
      if(this.joueurActif.getPiece() >= this.joueurActif.getMonument(achat).getCout())
        if(!this.joueurActif.monumentActif(achat))
        {
          this.joueurActif.activeMonument(achat);
          this.joueurActif.setPiece(-this.joueurActif.getMonument(achat).getCout());
          this.joueurActif.setAcheter(true);
          Carte tmp = this.joueurActif.getMonument(achat) ;
          this.ctrl.achatValide(this.joueurActif.getNom(),tmp.getNom(),tmp.getCout());
        }
        else this.ctrl.achatMonumentErreur();
      else this.ctrl.achatErreur();
    }
  }

  //recherche un joueur specifique
  public Joueur rechercherJoueur(String nom) 
  {
    for (Joueur joueur : this.joueurs) 
    {
      if (joueur.getNom().equals(nom))
        return joueur;
    }
    return null;
  }
  //gere les du au autre joueur
  private void payer()
  {
    int j = joueurs.indexOf(joueurActif);
    for(int i = 1 ; i <this.joueurs.size();i++)
    {
      if(j-1 <0) j=this.joueurs.size()-1;
      else j--;
      this.joueurs.get(j).payer(this.joueurActif,this.ctrl);
    }
  }
  //gere les gain lier au etablisemment
  private void gain()
  {
    for (Joueur joueur : this.joueurs)
    {
      joueur.gain(this.joueurActif,this.ctrl);
    }
  }
  //-------------------------------------------------------------------------------------------------------------------
  //----------------------------------------------------------GET------------------------------------------------------
  public boolean getIsEnd      () { return this.isEnd;       }
  public Joueur  getJoueurActif() { return this.joueurActif; }
  public Banque  getBanque     () { return this.banque;      }

  public int[] getPiece() 
  {
    int[] pieces = new int[this.joueurs.size()];
    for (Joueur joueur : joueurs) 
    {
      pieces[joueur.getNum()] = joueur.getPiece();
    }
    return pieces;
  }
  // retourn un copie de la list de joueur
  public ArrayList<Joueur> getJoueurs() 
  {
    ArrayList<Joueur> tmp = new ArrayList<Joueur>();
    for (Joueur joueur : this.joueurs) 
    {
      tmp.add(joueur);
    }
    return tmp;
  }

  public Joueur getJoueur(String nom) 
  {
    for (Joueur joueur : this.joueurs) 
    {
      if(joueur.getNom().equals(nom)) return joueur ; 
    }
    return null ;
  }

      //-------------------------violet
  //choisit un joueur
  public Joueur choisitUnJoueur(Joueur joueurActif)
  {
    Joueur joueurChoisi = null;
    do
    {
      joueurChoisi = this.rechercherJoueur(this.ctrl.choisitUnJoueur());
      if(null == joueurChoisi || joueurActif.equals(joueurChoisi))
        this.ctrl.erreurSaisirNomJoueur();
    }
    while(null == joueurChoisi || joueurActif.equals(joueurChoisi));
    
    return joueurChoisi;
  }
  //choisit un Carte
  public Carte choisitUnCarte(Joueur joueurChoisit)
  {
    Carte carteChoisit = null;
    do
    {
      carteChoisit = this.rechercherCarte(this.ctrl.choisitUnCarte(), joueurChoisit);
    
       if(null == carteChoisit || carteChoisit instanceof Monument  ||carteChoisit instanceof CarteViolet)
        this.ctrl.erreurSaisirNomCarte();
    }
    while(null == carteChoisit || carteChoisit instanceof Monument
        ||carteChoisit instanceof CarteViolet);
  
    
    return carteChoisit;
  }
  
  private Carte rechercherCarte (String nom,Joueur joueurChoisit)
  {
    for(Carte tmp :joueurChoisit.getListCartes())
      if(nom.equals(tmp.getNom()))
        return tmp;
    return null;
  }
}