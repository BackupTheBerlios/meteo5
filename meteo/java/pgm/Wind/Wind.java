package Wind;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.WindEventObject;
import EventObjects.WindTraiteEventObject;
import InterfaceListener.WindListener;
import InterfaceListener.WindTraiteListener;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant le vent.
 */
public class Wind implements Serializable, WindListener {
	private static final long serialVersionUID = 1;

	public Wind() {

	}

	// --------------------------------------
	// Source d'évènements WindTraite

	/** liste des écouteurs d'évènements WindTraite */
	private Vector<WindTraiteListener> windTraiteListeners = new Vector<WindTraiteListener>();

	/**
	 * Ajout d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à ajouter à la liste des abbonnés.
	 */
	public synchronized void addWindTraiteListener(WindTraiteListener l) {
		this.windTraiteListeners.addElement(l);
	}

	/**
	 * Supression d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à supprimer de la liste des abbonnés.
	 */
	public synchronized void removeWindTraiteListener(
			WindTraiteListener l) {
		this.windTraiteListeners.removeElement(l);
	}

	/**
	 * Méthode qui envoie un évènements contenant les infos sur le vent.
	 * 
	 * @param dir
	 *            Direction du vent en degré.
	 * @param fmax
	 *            Force maximale du vent.
	 * @param f
	 *            Force actuelle du vent.
	 */
	private void handleSendWind(int dir, int fmax, int f) {
		// Création de l'objet de l'évènement
		WindTraiteEventObject obj = new WindTraiteEventObject(this);
		obj.setDirectionTraite(dir);
		obj.setForceMaxTraite(fmax);
		obj.setForceTraite(f);

		// Envoi des évènements à tous les auditeurs
		Vector<WindTraiteListener> l;
		synchronized (this) {
			l = (Vector<WindTraiteListener>) this.windTraiteListeners.clone();
		}
		for (WindTraiteListener item : l) {
			item.handleTraite(obj);
		}
	}

	// --------------------------------------
	// Réception d'évènement Wind

	/**
	 * Méthode exécutée lors de la réception d'un évènement Wind.
	 * 
	 * @param e
	 *            Objet de l'évènement.
	 */
	public void handleCalcul(WindEventObject e) {
		int dir = calcul(e.getDirections(), e.getDistances());
		int fmax = calcul(e.getForcesMax(), e.getDistances());
		int f = calcul(e.getForces(), e.getDistances());
		handleSendWind(dir, fmax, f);
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
