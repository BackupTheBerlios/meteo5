package Client;

import java.awt.BorderLayout;
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

/**
 * Classe servant a affiché les informations métérologiques pour la ville
 * choisie.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class ClientClass implements ActionListener {

	/** Liste déroulante contenant la liste des aéroports. */
	private JComboBox listeLocation;

	/** TextArea contenant les informations métérologique pour l'aéroport choisi. */
	private JTextArea info;

	/** Element MeteoReport. */
	private MeteoReport meteo;

	/** Menu quitter. */
	private JMenuItem jmi_Quitter;

	
	/**
	 * Constructeur de la classe construisant l'interface graphique.
	 * @param villes Liste des villes auquelles on peut demander les infos.
	 */
	public ClientClass(Vector<String> villes) {

		meteo = new MeteoReport();

		JFrame jf = new JFrame("Meteo");

		/* Construction de la menu barre */
		JMenuBar jmb_Barre = new JMenuBar();
		JMenu jm_Fichier = new JMenu("Fichier");
		jmi_Quitter = new JMenuItem("Quitter");

		jm_Fichier.add(jmi_Quitter);
		jmb_Barre.add(jm_Fichier);
		jf.setJMenuBar(jmb_Barre);

		/* Panel de support des 2 autres panels */
		JPanel jp_support = new JPanel();
		jp_support.setLayout(new GridLayout(1, 0));

		/* Composant pour la localisation */
		JPanel p1 = new JPanel();
		JLabel lbl_aero = new JLabel("Aéroport :");
		p1.add(lbl_aero);

		listeLocation = new JComboBox();
		listeLocation.addItem(new String("Choisir un Aéroport"));

		for (String v : villes) {
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

		p2.add(BorderLayout.CENTER, info);
		jp_support.add(p2);

		jf.add(jp_support);

		/* Fermeture de la fenetre */
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		listeLocation.addActionListener(this);
		jmi_Quitter.addActionListener(this);

		jf.setSize(620, 250);
		jf.setVisible(true);
	}

	
	/**
	 * Méthode à l'écoute des événements et des actions a effectué selon
	 * l'événement engendrer.
	 * 
	 * @param e
	 *            L'événement.
	 */
	public void actionPerformed(ActionEvent e) {

		/* Action sur la liste déroulante */
		if (e.getSource() == listeLocation) {
			if (listeLocation.getSelectedIndex() != 0) {
				meteo.creerReport(listeLocation.getSelectedItem().toString());
				info.setText(meteo.toString());
			} else {
				info.setText("");
			}
		}

		/* Action sur le menu Quitter */
		if (e.getSource() == jmi_Quitter) {
			System.exit(0);
		}
	}

}
