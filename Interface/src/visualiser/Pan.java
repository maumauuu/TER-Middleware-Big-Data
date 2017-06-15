package visualiser;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.MaskFormatter;

import simulator.Simulator;
import simulator.Texte;
import spark.Spark;


public class Pan extends JPanel{
	
	private Spark spark;
	private Simulator sim;
	private String chemin;
	private String ip;
	private String clas;
	private String jar;
	
	private JButton boutonConnect;
	private JButton boutonSend;
	private JButton boutonInstallerSpark;
	private JButton lancerWorker;
	private JButton lancerMaster;
	private JButton boutonStop;
	private JPanel haut;
	private JPanel bas;
	private JPanel listePanel;
	private JPanel buttonsPanel;
	private JPanel IpPanel;
	
	private JPanel cheminPanel;
	private JButton boutonChemin;
	private JTextArea textChemin;
	private JLabel titreChemin;
	private JFileChooser chooserChemin;
	
	private JLabel titreIP; 
	private JFormattedTextField JtxtIP;
	private JButton boutonOk;
	private MaskFormatter IP;
	
	
	private JTextArea retourEvent;
	
	
	public Pan(){
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		spark = new Spark();
		retourEvent = new JTextArea();

		
		haut = new JPanel();
		bas = new JPanel();
		//ajout du formulaire 
		IpPanel = new JPanel();
		IpPanel.setLayout(new GridLayout(5,1));
		
		boutonOk = new JButton("OK");
		titreIP =new JLabel("Entrer l'adresse ip du master");
		
		try {
			IP = new MaskFormatter("###.###.###.###");
			JtxtIP = new  JFormattedTextField(IP);
			
		}
		catch (ParseException e1) {e1.printStackTrace();}
		IpPanel.add(titreIP);
		IpPanel.add(JtxtIP);
		IpPanel.add(boutonOk);
		
		boutonOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ip = JtxtIP.getText();
				 retourEvent.append("IP mise à jour\n");
				 lancerMaster.setEnabled(true);
			}
			

		});
		
		
		listePanel = new JPanel();
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(10,1));
		sim = new Simulator();
		sim.remplirListeMachine();
		lancerMaster=new JButton ("Lancer Master");
		lancerMaster.setEnabled(false);
		boutonConnect = new JButton("Connecter machines");
		boutonConnect.setEnabled(false);
		boutonInstallerSpark = new JButton("Installer spark");
		boutonInstallerSpark.setEnabled(false);
		boutonStop = new JButton("Quitter Cluster Spark");
		boutonStop.setEnabled(false);
		lancerWorker =new JButton("Lancer Worker");
		lancerWorker.setEnabled(false);
		boutonSend = new JButton("Envoyer un job");
		boutonSend.setEnabled(false);
		listePanel.setLayout(new BoxLayout(listePanel, BoxLayout.PAGE_AXIS));
		
		JPanel all = new JPanel();
		JCheckBox check1 = new JCheckBox("Selectionner tout");
		check1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(check1.isSelected()){
					for(Texte texte : sim.getList()){
						texte.setSelected(true);
					}
				}else{
					for(Texte texte : sim.getList()){
						texte.setSelected(false);
					}
				}
			}
		});
		all.add(check1);
		listePanel.add(all);
		for(Texte texte : sim.getList()){
			listePanel.add(texte);
		}
		
		lancerMaster.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boutonConnect.setEnabled(true);
				spark.lancementMaster(chemin);
				 retourEvent.append("Master lancé\n");
				
			}
		});

		boutonConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				String adresses = "";
				for(Texte texte : sim.getList()){
					if(texte.isSelected()){
						adresses = adresses + texte.getAdresse().toString()+" ";
					}
				}
				retourEvent.append("Connection réussi\n");
				spark.connectionDistance(adresses);
				boutonInstallerSpark.setEnabled(true);
				boutonStop.setEnabled(true);
			}
		});
		
		
		
		boutonInstallerSpark.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Texte texte : sim.getList()){
					if(texte.isSelected()){
						spark.installSpark(texte.getAdresse().toString(),ip);	
						retourEvent.append("Installation de Spark terminé\n");
						lancerWorker.setEnabled(true);


					}
				}				
			}
		});
		
		
		lancerWorker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				spark.lancementWorker();
				 retourEvent.append("Worker(s) lancé(s)\n");
				 boutonSend.setEnabled(true);
			}});
		


		/*boutonSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				spark.sendJob(chemin, ip, clas, jar);
			}
		});*/
		
		
		
		boutonStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Texte texte : sim.getList()){
					if(texte.isSelected()){
						listePanel.remove(texte);
						sim.remove(texte);
						spark.arretCluster(chemin);
						repaint();
					}
				}
			}
		});
		
		
		titreChemin = new JLabel("Indiquer l'emplacement de spark");
		boutonChemin =  new JButton("...");
		textChemin = new JTextArea();
		boutonChemin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooserChemin = new JFileChooser();
				chooserChemin.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    int returnVal = chooserChemin.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	chemin = chooserChemin.getSelectedFile().getAbsolutePath().toString();
			    	textChemin.setText(chemin);
			    }
			}
		});
		
		cheminPanel = new JPanel();
		cheminPanel.setLayout(new GridLayout(1,2));
		cheminPanel.add(textChemin);
		cheminPanel.add(boutonChemin);
		
		
	    IpPanel.add(titreChemin);
	    IpPanel.add(cheminPanel);
	    

		
		
		
		buttonsPanel.add(lancerMaster);
		buttonsPanel.add(boutonConnect);
		buttonsPanel.add(boutonInstallerSpark);
		buttonsPanel.add(lancerWorker);
		//buttonsPanel.add(boutonSend);
		buttonsPanel.add(boutonStop);
		
		haut.add(IpPanel);
		haut.add(buttonsPanel);
		haut.add(listePanel);
		
		textChemin.setEditable(false);
		retourEvent.setEditable(false);
	
		bas.add(retourEvent);
		add(haut);
		add(bas);
	}

}
