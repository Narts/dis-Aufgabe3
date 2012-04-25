package de.dis2011.authentication;

/**
 * Kleine Helferklasse zum Authentifizieren
 */
public interface Authenticator {
	
	/**
	 * Liest die Authentifizierungsdaten ein und gibt zurück, ob
	 * die Authentifikation erfolgreich war.
	 */
	public boolean authenticate();
}
