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
	
	public void installSpark(String machine,String IP) {
		try {
			shellCluster.command("echo \"SPARK_MASTER_HOST=\""+ IP + ">> spark-env.sh ").consumeAsString();
			shellCluster.command("echo \"./spark/sbin/start-slave.sh spark://\""+IP +":7077 >> script.sh ").consumeAsString();
			//sendData.command("scp -r spark.tar.gz " + machine + ":~/").consumeAsString();
			//sendData.command("scp -r scala.tar.gz " + machine + ":~/").consumeAsString();
			sendData.command("scp .bashrc " + machine + ":~/").consumeAsString();
			sendData.command("scp script.sh " + machine + ":~/").consumeAsString();
			sendData.command("scp spark-env.sh " + machine + ":~/spark/conf").consumeAsString();

		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
