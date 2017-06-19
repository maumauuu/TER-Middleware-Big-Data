package simulator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
//Permet de creer les groupes de connexion du type clusters = root@192.25... root2@...
//utilise un fichier en entrée et un fichier en sortie
public class Groupe_config {
	//On part du principe que le fichier est toujours bien formé
	//premiere colonne nom d'utilisateur
	//deuxieme colonne adresse ip 
	private File Ip = new File("users");
	private File file = new File("IP-list");
	
	private FileWriter fw;
	private FileReader fr;
		
	
	public Groupe_config(){
		try {
			  String str_ip;
			  fr = new FileReader(Ip);
			  str_ip = "";
			  int i = 0;
			  //On récupère le contenu du fichier
			  while((i = fr.read()) != -1){
				  str_ip += (char)i;
			  }
			  
		       
			  //On ecrit dans le fichier en sortiee
		      fw = new FileWriter(file);
		      //on decoupe le fichier en ligne 
		      String[] parts_ip = str_ip.split("\n");
		      for(String item : parts_ip){
		    	  //On decoupe chaque ligne en deux (le nom d'user et l'ip)
		    	  String[] parts_user = item.split(" ");
		    	  for(String item2 : parts_user){
		    		  fw.write(item2);
		    		  //on rajoute le @ apres le nom d'user
		    		  if(item2 != parts_user[1]){
		    			  fw.write("@");
		    		  }
		    	  }
		    	  fw.write(" \n");
		    }
		     
		      fw.close();
		  	
		} 
		    
	     catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
	}
	
}
	
	
	
	