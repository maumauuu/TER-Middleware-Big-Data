package visualiser;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import javax.swing.*;
import simulator.Simulator;

import simulator.Texte;
import spark.Spark;

import javax.swing.BoxLayout;


public class Pan extends JPanel{
	
	private Spark spark;
	private Simulator sim;
	private JButton boutonConnect;
	private JButton boutonSuppr;
	private JPanel listePanel;
	private JPanel buttonsPanel;
	private JPanel IpPanel;
	private JButton boutonInstallerSpark;
	private JFormattedTextField JtxtIP;
	private JButton boutonOk;
	private MaskFormatter IP;
	private String ip;


	
	public Pan(){
		spark = new Spark();
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//ajout du formaulaire 
		IpPanel = new JPanel();
		boutonOk = new JButton("OK");
		try {
			IP = new MaskFormatter("###.###.###.###");
			JtxtIP = new  JFormattedTextField(IP);
		} catch (ParseException e1) {e1.printStackTrace();}
		
		IpPanel.add(JtxtIP);
		IpPanel.add(boutonOk);
		boutonOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ip = JtxtIP.getText();
				System.out.println("IP mise à jour ");
			}
			

		});
		
		
		listePanel = new JPanel();
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(10,1));
		sim = new Simulator();
		sim.remplirListeMachine();
		boutonConnect = new JButton("Connecter machines");
		boutonInstallerSpark = new JButton("Installer spark");
		boutonSuppr = new JButton("Supprimer machine");
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

		boutonConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String adresses = "";
				for(Texte texte : sim.getList()){
					if(texte.isSelected()){
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
					if(texte.isSelected()){
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
					if(texte.isSelected()){
						 spark.installSpark(texte.getAdresse().toString(),ip);
					}
				}				
			}
		});
		
		buttonsPanel.add(boutonConnect);
		buttonsPanel.add(boutonInstallerSpark);
		buttonsPanel.add(boutonSuppr);
		
		add(IpPanel);
		add(buttonsPanel);
		add(listePanel);
	}

}
