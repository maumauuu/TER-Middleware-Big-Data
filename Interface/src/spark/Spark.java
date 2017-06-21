package spark;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.developpez.adiguba.shell.Shell;


public class Spark {
	Shell shellCluster;
	Shell sendData;
	
	public Spark(){
		shellCluster = new Shell();
		sendData = new Shell();
	}
	
	
	public void lancementMaster(String chemin){	
	      try {
			sendData.command(""+ chemin+"/sbin/start-master.sh").consumeAsString();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connectionDistance(String machine) {
			try {
				shellCluster.command("cssh "+machine+" &").consumeAsString();
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void Up_fichier(String IP){
		
		try {
			shellCluster.command("echo \"SPARK_MASTER_HOST=\""+ IP + ">> spark-env.sh ").consumeAsString();
			shellCluster.command("echo \"./spark/bin/spark-class  org.apache.spark.deploy.worker.Worker spark://"+IP +":7077 &\"  >> script2.sh ").consumeAsString();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
	}
	public void installSpark(String machine) {
			
			try {
				sendData.command("scp -r spark.tar.gz " + machine+ ":~/").consumeAsString();
				sendData.command("scp -r scala.tar.gz " + machine+ ":~/").consumeAsString();
				sendData.command("scp .bashrc " + machine +":~/").consumeAsString();
				sendData.command("scp script.sh " + machine+ ":~/").consumeAsString();
				sendData.command("scp script2.sh " + machine+ ":~/").consumeAsString();

			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public void step3(){

		JOptionPane jop1;
		jop1 = new JOptionPane();
		jop1.showMessageDialog(null, "Veuillez entrer la commande suivante dans le terminal de clusterssh: ./script.sh", "Suite", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void step4(String machine){
		try {
			sendData.command("scp spark-env.sh " + machine + ":~/spark/conf").consumeAsString();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void lancementWorker(){
		JOptionPane jop2;
		jop2 = new JOptionPane();
		jop2.showMessageDialog(null, "Veuillez entrer la commande suivante dans le terminal de clusterssh: ./script2.sh", "Suite", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void arretCluster(String chemin) {
		try {
			shellCluster.command( ""+chemin+"/sbin/stop-all.sh").consumeAsString();
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendJob(String chemin, String master, String clas,String jar,String machine){
		try {
			
			sendData.command("scp -r job.tar.gz " + machine + ":~/").consumeAsString();
			sendData.command("scp job.sh " + machine + ":~/").consumeAsString();
			JOptionPane jop3;
			jop3 = new JOptionPane();
			jop3.showMessageDialog(null, "Veuillez entrer la commande suivante dans le terminal de clusterssh: ./job.sh", "Suite", JOptionPane.INFORMATION_MESSAGE);
			
			shellCluster.command( "/usr/local/spark/bin/spark-submit "
					+ "--master spark://" + master +":7077 "
					+ "--class "+clas
					+ "--deploy-mode cluster "+ jar).consumeAsString();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
