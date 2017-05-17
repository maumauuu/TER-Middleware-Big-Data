package simulator;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

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
