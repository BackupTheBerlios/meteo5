package Pressure;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import EventObjects.PressureEventObject;
import EventObjects.PressureTraiteEventObject;
import InterfaceListener.PressureListener;
import InterfaceListener.PressureTraiteListener;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant la pression atmosphérique.
 */
public class Pressure implements Serializable, PressureListener {
	private static final long serialVersionUID = 1;
	
	/** Constructeur vide pour un composnant. */
	public Pressure() {
	
	}
	
	
	// --------------------------------------
	// Source d'évènements PressureTraite

	/** liste des écouteurs d'évènements Metar */
	private Vector<PressureTraiteListener> pressureTraiteListener = new Vector<PressureTraiteListener>();

	/**
	 * Ajout d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à ajouter à la liste des abbonnés.
	 */
	public synchronized void addPressureTraiteListener(PressureTraiteListener l) {
		this.pressureTraiteListener.addElement(l);
	}

	/**
	 * Supression d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à supprimer de la liste des abbonnés.
	 */
	public synchronized void removePressureTraiteListener(
			PressureTraiteListener l) {
		this.pressureTraiteListener.removeElement(l);
	}

	/**
	 * Méthode qui envoie un évènements contenant les infos sur la pression.
	 */
	private void handleSendPressureTraite(int pres) {
		// Création de l'objet de l'évènement
		PressureTraiteEventObject obj = new PressureTraiteEventObject(this);
		obj.setPressureTraite(pres);

		// Envoi des évènements à tous les auditeurs
		Vector<PressureTraiteListener> l;
		synchronized (this) {
			l = (Vector<PressureTraiteListener>) this.pressureTraiteListener.clone();
		}
		for (PressureTraiteListener item : l) {
			item.handleTraite(obj);
		}
	}
	
	
	// --------------------------------------
	// Réception d'évènement Pression

	/**
	 * Méthode exécutée lors de la réception d'un évènement Pression.
	 * 
	 * @param e
	 *            Objet de l'évènement.
	 */
	public void handleCalcul(PressureEventObject e) {
		int pr = calcul(e.getPressure(), e.getDistances());
		handleSendPressureTraite(pr);
	}

	
	// --------------------
	// Calcul des moyennes

	/**
	 * Calcul les informations.
	 */
	protected int calcul(Vector<Integer> data, Vector<Float> dst) {
		float presMoy = 0.0f;
		float coef = 0.0f;
		
		for (int i = 0; i < data.size(); i++) {
			if (dst.get(i) != 0) {
				presMoy += data.get(i) * (1 / dst.get(i));
				coef += 1 / dst.get(i);
			} else {
				presMoy += data.get(i);
				coef++;
			}
		}

		presMoy /= coef;

		return Math.round(presMoy);
	}
	

	
}
