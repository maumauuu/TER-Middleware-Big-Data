package simulator;

import java.util.ArrayList;

public class Simulator {
	
	private ArrayList<Texte> listMachine;
	private Fichier fichier;
	
	public Simulator(){
		listMachine = new ArrayList<Texte>();
		fichier = new Fichier("/home/maumau/.clusterssh/cluster");
	}
	
	public void add(Adresse adresse){
		
		listMachine.add(new Texte(adresse));
	}
	
	public void remove(Texte texte){
		listMachine.remove(texte);
	}
	
	public ArrayList<Texte> getList(){
		return listMachine;
	}
	
	public void remplirListeMachine(){
		listMachine.clear();
		String[] adresses = fichier.getContenu().split("\n");
		int id = 0;
		for(String adresse : adresses){
			String[] element = adresse.split("@");
			if(element.length == 2){
				listMachine.add(new Texte(new Adresse(id, element[0], element[1])));
				id++;
			}
		}
	}

}
