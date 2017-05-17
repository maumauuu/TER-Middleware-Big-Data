package spark;

import java.io.IOException;

import com.developpez.adiguba.shell.Shell;
import javax.swing.JOptionPane;


public class Spark {
	Shell shellCluster;
	Shell sendData;
	
	public Spark(){
		shellCluster = new Shell();
		sendData = new Shell();
		
	}
	
	public void connectionDistance(String machine){
	
		try {
			shellCluster.command("cssh "+machine+" &").consumeAsString();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void installSpark(String machine){
		try {
			sendData.command("scp -r spark.tar.gz "+machine+":~/").consumeAsString();
			sendData.command("scp -r scala.tar.gz "+machine+":~/").consumeAsString();
			sendData.command("scp .bashrc "+machine+":~/").consumeAsString();
			sendData.command("scp script.sh "+machine+":~/").consumeAsString();
			sendData.command("scp spark-env.sh "+machine+":~/conf/").consumeAsString();
			
			JOptionPane jop1, jop2;
			jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "Veuillez entrer la commande suivante dans le terminal de clusterssh: ./script.sh", "Suite", JOptionPane.INFORMATION_MESSAGE);

			jop2 = new JOptionPane();
			jop2.showMessageDialog(null, "Veuillez entrer la commande suivante dans le terminal de clusterssh: export IP_MASTER= suivi de votre adresse ip", "Suite", JOptionPane.INFORMATION_MESSAGE);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
