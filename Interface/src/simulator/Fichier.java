package simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Fichier {
	
	private String contenu;
	private String chemin;
	
	public Fichier(String chemin){
		this.contenu = "";
		this.chemin = chemin;
		
		fileToString();
	}
	
	public String getContenu(){
		return contenu;
	}
	
	public void setContenu(String contenu){
		this.contenu = contenu;
	}
	
	private void fileToString(){
		FileInputStream fis = null;
	      try {

	         fis = new FileInputStream(new File(chemin));

	         byte[] buf = new byte[8];

	         int n = 0;

	         while ((n = fis.read(buf)) >= 0) {

	            // On écrit dans notre deuxième fichier avec l'objet adéquat

	        	 for (byte bit : buf) {

	                 contenu = contenu + (char) bit;

	              }
	        	 
	            buf = new byte[8];


	         }

	      } catch (FileNotFoundException e) {

	         e.printStackTrace();

	      } catch (IOException e) {

	         // Celle-ci se produit lors d'une erreur d'écriture ou de lecture

	         e.printStackTrace();

	      } finally {

	         try {

	            if (fis != null)

	               fis.close();

	         } catch (IOException e) {

	            e.printStackTrace();

	         }
	      }	      
	}

}
