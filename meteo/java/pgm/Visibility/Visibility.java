package Visibility;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;

import EventObjects.VisibilityEventObject;
import EventObjects.VisibilityTraiteEventObject;
import EventObjects.WindEventObject;
import EventObjects.WindTraiteEventObject;
import InterfaceListener.VisibilityListener;
import InterfaceListener.VisibilityTraiteListener;
import InterfaceListener.WindTraiteListener;

public class Visibility implements Serializable, VisibilityListener {
	private static final long serialVersionUID = 1;
	
	// --------------------------------------
	// Source d'évènements VisibilityTraite

	/** liste des écouteurs d'évènements Metar */
	private Vector<VisibilityTraiteListener> visibilyTraiteListener = new Vector<VisibilityTraiteListener>();

	/**
	 * Ajout d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à ajouter à la liste des abbonnés.
	 */
	public synchronized void addVisibilityTraiteListener(VisibilityTraiteListener l) {
		this.visibilyTraiteListener.addElement(l);
	}

	/**
	 * Supression d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à supprimer de la liste des abbonnés.
	 */
	public synchronized void removeVisibilityTraiteListener(
			VisibilityTraiteListener l) {
		this.visibilyTraiteListener.removeElement(l);
	}

	/**
	 * Méthode qui envoie un évènements contenant les infos sur le vent.
	 */
	private void handleSendVisibility(int vis) {
		// Création de l'objet de l'évènement
		VisibilityTraiteEventObject obj = new VisibilityTraiteEventObject(this);

		obj.setVisibility(vis);

		// Envoi des évènements à tous les auditeurs
		Vector<VisibilityTraiteListener> l;
		synchronized (this) {
			l = (Vector<VisibilityTraiteListener>) this.visibilyTraiteListener.clone();
		}
		for (VisibilityTraiteListener item : l) {
			item.handleTraite(obj);
		}
	}
	
	
	// --------------------------------------
	// Réception d'évènement Visibility

	/**
	 * Méthode exécutée lors de la réception d'un évènement Visibility.
	 * 
	 * @param e
	 *            Objet de l'évènement.
	 */
	public void handleCalcul(VisibilityEventObject e) {
		int vis = calcul(e.getVisibilites(), e.getDistances());
		handleSendVisibility(vis);
	}

	
	// --------------------
	// Calcul des moyennes

	/**
	 * Calcul les informations.
	 */
	protected int calcul(Vector<Integer> data, Vector<Float> dst) {
		float dirMoy = 0.0f;
		float coef = 0.0f;
		
		for (int i = 0; i < data.size(); i++) {
			if (dst.get(i) != 0) {
				dirMoy += data.get(i) * (1 / dst.get(i));
				coef += 1 / dst.get(i);
			} else {
				dirMoy += data.get(i);
				coef++;
			}
		}

		dirMoy /= coef;

		return Math.round(dirMoy);
	}
	
	
}
