package miniville.metier;

public class Monument extends Carte 
{
	private boolean isBuild;

	public Monument(String nom,int cout) 
	{
		super("",nom, "monument",cout,0);
		this.isBuild = false;
	}
	public Monument(Monument autreMonument)
	{
		super(autreMonument);
		this.isBuild=autreMonument.isBuild;
	}

  //-------------------------------------------------------------------------------------------------------------------
  //----------------------------------------------------------GET------------------------------------------------------
	public boolean getIsBuild() { return this.isBuild ; }

  //-------------------------------------------------------------------------------------------------------------------
  //----------------------------------------------------------SET------------------------------------------------------
	public void setIsBuild(boolean b ) { this.isBuild = b  ; }
}