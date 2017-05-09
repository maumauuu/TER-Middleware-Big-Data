package simulator;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class Texte extends JLabel{

	private JCheckBox box;
	private Adresse adresse;
	
	public Texte(Adresse adresse){
		this.adresse = adresse;
		box = new JCheckBox();
		this.setText(adresse.toString());
	}
	
	public Adresse getAdresse(){
		return adresse;
	}
	
	public void setAdresse(Adresse adresse){
		this.adresse = adresse;
	}
	
	public JCheckBox getBox(){
		return box;
	}
}
