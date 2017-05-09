package spark;

import java.io.IOException;

import com.developpez.adiguba.shell.Shell;


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
			sendData.command("scp -r spark.tar.gz "+machine+":/").consumeAsString();
			sendData.command("scp -r scala.tar.gz "+machine+":/").consumeAsString();
			sendData.command("scp .bashrc "+machine+":/").consumeAsString();		
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
