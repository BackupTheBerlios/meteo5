package meteo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import meteo.engine.*;

public class ClientClass implements ActionListener  {

	private JComboBox listeLocation;
	private JLabel info;
	private MeteoReport meteo;
	
	public ClientClass() {
		
		meteo = new MeteoReport();
		
		JFrame jf = new JFrame("Meteo");
		
		 listeLocation = new JComboBox();
		
		Vector<String> villes = new MeteoReport().possibleLocations();
		
		for (String v : villes ){
			listeLocation.addItem(v);
		}
		
		
		jf.setLayout(new GridLayout(1,0));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		
		p1.add(listeLocation);
		jf.add(p1);
		
		info = new JLabel("");
		p2.add(info);
		jf.add(p2);
		
		/* Fermeture de la fenetre */
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		listeLocation.addActionListener(this);
		
		jf.setSize(250,500);
		jf.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==listeLocation){
			meteo.creerReport(listeLocation.getSelectedItem().toString());
			
			info.setText(meteo.toString());
		}
		
	}
	
	public static void main (String[] args){
		new ClientClass();
	}

}
