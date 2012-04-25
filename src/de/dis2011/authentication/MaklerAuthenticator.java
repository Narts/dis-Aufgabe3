package de.dis2011.authentication;

import de.dis2011.core.ImmoService;
import de.dis2011.data.Makler;
import de.dis2011.util.FormUtil;

/**
 * Authentifiziert einen Makler
 */
public class MaklerAuthenticator implements Authenticator {
	private ImmoService service;
	private Makler lastAuthenticatedMakler;
	
	/**
	 * Konstruktor
	 * @param service Immobilien-Service zum Auffinden des entsprechenden Maklers
	 */
	public MaklerAuthenticator(ImmoService service) {
		this.service = service;
	}
	
	/**
	 * Gibt das Makler-Objekt zum letzten erfolgreich authentisierten Makler zurück
	 */
	public Makler getLastAuthenticatedMakler() {
		return this.lastAuthenticatedMakler;
	}
	
	/**
	 * Fragt nach Makler-Login und -Passwort und überprüft die Eingabe
	 */
	public boolean authenticate() {
		boolean ret;
		
		String login = FormUtil.readString("Makler-Login");
		String password = FormUtil.readPassword("Passwort");
		
		Makler m = service.getMaklerByLogin(login);
		
		if(m == null)
			ret = false;
		else
			ret = password.equals(m.getPasswort());
		
		lastAuthenticatedMakler = m;
		
		if(!ret)
			FormUtil.showMessage("Benutzername oder Passwort falsch!");
		
		return ret;
	}
}
