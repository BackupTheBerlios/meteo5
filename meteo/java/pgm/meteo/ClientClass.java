package meteo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import meteo.engine.*;

public class ClientClass implements ActionListener  {

	private JComboBox listeLocation;
	private JTextArea info;
	private MeteoReport meteo;
	private JMenuItem jmi_Quitter;
	
	public ClientClass() {
		
		meteo = new MeteoReport();
		
		JFrame jf = new JFrame("Meteo");
		//jf.setLayout(new BorderLayout());
		
		
		JMenuBar jmb_Barre= new JMenuBar();
		JMenu jm_Fichier = new JMenu("Fichier");
		jmi_Quitter = new JMenuItem("Quitter");
		
		jm_Fichier.add(jmi_Quitter);
		jmb_Barre.add(jm_Fichier);
		jf.setJMenuBar(jmb_Barre);
		
		
		JPanel jp_support = new JPanel();
		jp_support.setLayout(new GridLayout(1,0));
		
		/* Composant pour la localisation */
		JPanel p1 = new JPanel();
		JLabel lbl_aero = new JLabel("Aéroport :");
		p1.add(lbl_aero);
		
		listeLocation = new JComboBox();
		listeLocation.addItem(new String("Choisir un Aéroport"));
			
		Vector<String> villes = new MeteoReport().possibleLocations();
			
		for (String v : villes ){
			listeLocation.addItem(v);
		}
		
		p1.add(listeLocation);
		jp_support.add(p1);
		
		/* Composant pour les informations météorologiques */
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		
		JLabel lbl_info = new JLabel("Informations météorologiques :");
		p2.add(BorderLayout.NORTH, lbl_info);
		
		
		info = new JTextArea();
		
		info.setOpaque(false);
		info.setEditable(false);
		info.setVisible(true);
		
		p2.add(BorderLayout.CENTER ,info);
		jp_support.add(p2);
		
		jf.add(jp_support);
		
		/* Fermeture de la fenetre */
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		listeLocation.addActionListener(this);
		jmi_Quitter.addActionListener(this);
		
		jf.setSize(620,250);
		jf.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==listeLocation){
			
			
			if(listeLocation.getSelectedIndex()!=0){
			
			//info.setText(listeLocation.getSelectedItem().toString());
			meteo.creerReport(listeLocation.getSelectedItem().toString());
			info.setText(meteo.toString());
			}else{
				info.setText("");
			}
		}
		if(e.getSource()==jmi_Quitter){
			System.exit(0);
		}
	}
	
	public static void main (String[] args){
		new ClientClass();
	}

}
