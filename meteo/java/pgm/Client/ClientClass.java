package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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

	/** Menu quitter. */
	private JMenuItem jmi_Quitter;
	
	/** Fenêtre de l'interface. */
	private JFrame jf;

	/** Référence vers la classe maitresse du composant. */
	private Client client;
	
	/**
	 * Constructeur de la classe construisant l'interface graphique.
	 * @param c Référence à la classe maitresse du composant.
	 */
	public ClientClass(Client c) {
		this.client = c;
		
		this.jf = new JFrame("Meteo");

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
		jf.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		        System.exit(0); 
		    }
		});

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
			info.setText("");
			if (listeLocation.getSelectedIndex() != 0) {
				this.client.handleSelectedVille(listeLocation.getSelectedItem().toString());
			}
		}

		/* Action sur le menu Quitter */
		if (e.getSource() == jmi_Quitter) {
			System.exit(0);
		}
	}


	/**
	 * Rempli la liste des endroits où l'on peut avoir 
	 * des informations météos.
	 * @param villes Liste des noms des villes.
	 */
	public void setLocations(Vector<String> villes) {
		this.listeLocation.removeAll();
		this.listeLocation.addItem(new String("Choisir un Aéroport"));
		for (String v : villes) {
			this.listeLocation.addItem(v);
		}
	}
	
	/**
	 * Ajoute du texte à afficher.
	 * @param txt Texte à ajouter.
	 */
	public void addTexte(String txt) {
		this.info.setText(this.info.getText() + txt + "\n");
	}
	
}
