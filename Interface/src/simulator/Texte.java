package simulator;

import javax.swing.JCheckBox;

public class Texte extends JCheckBox{

	private Adresse adresse;
	
	public Texte(Adresse adresse){
		super(adresse.toString());
		this.adresse = adresse;
		this.setText(adresse.toString());
	}
	
	public Adresse getAdresse(){
		return adresse;
	}
	
	public void setAdresse(Adresse adresse){
		this.adresse = adresse;
	}
}
