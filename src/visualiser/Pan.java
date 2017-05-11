package visualiser;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simulator.Simulator;

import simulator.Texte;
import spark.Spark;


public class Pan extends JPanel{
	
	private Spark spark;
	private Simulator sim;
	private JButton boutonConnect;
	private JButton boutonSuppr;
	private JPanel listePanel;
	private JPanel buttonsPanel;
	private JButton boutonInstallerSpark;

	
	public Pan(){
		spark = new Spark();
		
		listePanel = new JPanel();
		buttonsPanel = new JPanel();
		
		sim = new Simulator();
		sim.remplirListeMachine();
		boutonConnect = new JButton("Connecter machines");
		boutonInstallerSpark = new JButton("Installer spark");
		boutonSuppr = new JButton("Supprimer machine");

		for(Texte texte : sim.getList()){
			listePanel.add(texte.getBox());
			listePanel.add(texte);
		}
		add(listePanel);
		boutonConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String adresses = "";
				for(Texte texte : sim.getList()){
					if(texte.getBox().isSelected()){
						adresses = adresses + texte.getAdresse().toString()+" ";
					}
				}
				System.out.println("Connected !: "+adresses);
				spark.connectionDistance(adresses);
			}
		});
		boutonSuppr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Texte texte : sim.getList()){
					if(texte.getBox().isSelected()){
						listePanel.remove(texte.getBox());
						listePanel.remove(texte);
						sim.remove(texte);
						repaint();
					}
				}
			}
		});
		boutonInstallerSpark.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Texte texte : sim.getList()){
					if(texte.getBox().isSelected()){
						 spark.installSpark(texte.getAdresse().toString());
					}
				}				
			}
		});
		
		buttonsPanel.add(boutonConnect);
		buttonsPanel.add(boutonInstallerSpark);
		buttonsPanel.add(boutonSuppr);
		
		add(buttonsPanel);
	}

}
