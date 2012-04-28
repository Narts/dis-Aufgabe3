package de.dis2011.editor;

import java.util.Iterator;
import java.util.Set;

import de.dis2011.core.ImmoService;
import de.dis2011.data.Haus;
import de.dis2011.data.Kaufvertrag;
import de.dis2011.data.Makler;
import de.dis2011.data.Mietvertrag;
import de.dis2011.data.Person;
import de.dis2011.data.Wohnung;
import de.dis2011.menu.AppartmentSelectionMenu;
import de.dis2011.menu.HouseSelectionMenu;
import de.dis2011.menu.Menu;
import de.dis2011.menu.PersonSelectionMenu;
import de.dis2011.util.FormUtil;
import de.dis2011.util.Helper;

/**
 * Klasse für die Menüs zur Verwaltung von Verträgen
 */
public class VertragsEditor {
	///Immobilien-Service, der genutzt werden soll
	private ImmoService service;
	
	///Makler, zu dessen Immobilien Verträge geschlossen werden dürfen
	private Makler verwalter;
	
	public VertragsEditor(ImmoService service, Makler verwalter) {
		this.service = service;
		this.verwalter = verwalter;
	}
	
	/**
	 * Vertragsmenü
	 */
	public void showVertragsMenu() {
		//Menüoptionen
		final int NEW_LEASING_CONTRACT = 0;
		final int NEW_SALE_CONTRACT = 1;
		final int SHOW_CONTRACTS = 2;
		final int BACK = 3;
		
		//Vertragsverwaltung
		Menu maklerMenu = new Menu("Vertrags-Verwaltung");
		maklerMenu.addEntry("Neuer Mietvertrag", NEW_LEASING_CONTRACT);
		maklerMenu.addEntry("Neuer Kaufvertrag", NEW_SALE_CONTRACT);
		maklerMenu.addEntry("Verträge ansehen", SHOW_CONTRACTS);
		
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_LEASING_CONTRACT:
					newMietvertrag();
					break;
				case NEW_SALE_CONTRACT:
					newKaufvertrag();
					break;
				case SHOW_CONTRACTS:
					zeigeVertraege();
					break;
				case BACK:
					return;
			}
		}
	}
	
	public void zeigeVertraege() {
		//Mietverträge anzeigen
		System.out.println("Mietverträge\n-----------------");
		Set<Mietvertrag> mvs = service.getAllMietvertraegeForMakler(verwalter);
		Iterator<Mietvertrag> itmv = mvs.iterator();
		while(itmv.hasNext()) {
			Mietvertrag mv = itmv.next();
			Wohnung whng = service.getWohnungById(mv.getImmobId());
			System.out.println("Mietvertrag "+mv.getVertragsnummer()+"\n"+
							"\tGeschlossen am "+Helper.dateToString(mv.getDatum())+" in "+mv.getOrt()+"\n"+
							"\tMieter:        "+service.getPersonById(mv.getPersonId()).getVorname()+" "+service.getPersonById(mv.getPersonId()).getNachname()+"\n"+
							"\tWohnung:       "+whng.getStrasse()+" "+whng.getHausnummer()+", "+whng.getPlz()+" "+whng.getOrt()+"\n"+
							"\tMietbeginn:    "+Helper.dateToString(mv.getMietbeginn())+", Dauer: "+mv.getDauer()+" Monate\n"+
							"\tMietpreis:     "+whng.getMietpreis()+" Euro, Nebenkosten: "+mv.getNebenkosten()+" Euro\n");
		}
		
		System.out.println("");
		
		//Kaufverträge anzeigen
		System.out.println("Kaufverträge\n-----------------");
		Set<Kaufvertrag> kvs = service.getAllKaufvertraegeForMakler(verwalter);
		Iterator<Kaufvertrag> itkv = kvs.iterator();
		while(itkv.hasNext()) {
			Kaufvertrag kv = itkv.next();
			System.out.println("Kaufvertrag "+kv.getVertragsnummer()+"\n"+
							"\tGeschlossen am "+Helper.dateToString(kv.getDatum())+" in "+kv.getOrt()+"\n"+
							"\tMieter:        "+service.getPersonById(kv.getPersonId()).getVorname()+" "+service.getPersonById(kv.getPersonId()).getNachname()+"\n"+
							"\tHaus:          "+service.getHausById(kv.getImmobId()).getStrasse()+" "+service.getHausById(kv.getImmobId()).getHausnummer()+", "+service.getHausById(kv.getImmobId()).getPlz()+" "+service.getHausById(kv.getImmobId()).getOrt()+"\n"+
							"\tKaufpreis:     "+service.getHausById(kv.getImmobId()).getKaufpreis()+" Euro\n"+
							"\tRaten:         "+kv.getAnzahlRaten()+", Ratenzins: "+kv.getRatenzins()+"%\n");
		}
	}
	
	
	/**
	 * Menü zum anlegen eines neuen Mietvertrags
	 */
	public void newMietvertrag() {
		//Alle Wohnungen des Maklers finden
		Set<Wohnung> wohnungen = service.getAllWohnungenForMakler(verwalter);
		
		//Auswahlmenü für die Wohnungen 
		AppartmentSelectionMenu asm = new AppartmentSelectionMenu("Wohnung für Vertrag auswählen", wohnungen);
		int wid = asm.show();
		
		//Falls kein Abbruch: Auswahl der Person
		if(wid != AppartmentSelectionMenu.BACK) {
			//Alle Personen laden
			Set<Person> personen = service.getAllPersons();
			
			//Menü zur Auswahl der Person
			PersonSelectionMenu psm = new PersonSelectionMenu("Person für Vertrag auswählen", personen);
			int pid = psm.show();
			
			//Falls kein Abbruch: Vertragsdaten abfragen und Vertrag anlegen
			if(pid != PersonSelectionMenu.BACK) {
				Set<Integer> immobIdSet =  service.getImmobIdInAllVertraege();
				if (immobIdSet.contains((Integer)wid)) {
					System.out.println("Diese Wohnung ist schon gemietet");
				}else{
					Mietvertrag m = new Mietvertrag();
			        m.setImmobId(wid);
					m.setPersonId(pid);
					m.setDatum(FormUtil.readDate("Datum"));
					m.setOrt(FormUtil.readString("Ort"));
					m.setMietbeginn(FormUtil.readDate("Mietbeginn"));
					m.setDauer(FormUtil.readInt("Dauer in Monaten"));
					m.setNebenkosten(FormUtil.readInt("Nebenkosten"));
					
					service.addMietvertrag(m);
					
					System.out.println("Mietvertrag mit der ID "+m.getVertragsnummer()+" wurde erzeugt.");
				}
				
			}
		}
	}
	
	/**
	 * Menü zum anlegen eines neuen Kaufvertrags
	 */
	public void newKaufvertrag() {
		//Alle Häuser des Maklers finden
		Set<Haus> haeuser = service.getAllHaeuserForMakler(verwalter);
		
		//Auswahlmenü für das Haus
		HouseSelectionMenu asm = new HouseSelectionMenu("Haus für Vertrag auswählen", haeuser);
		int hid = asm.show();
		
		//Falls kein Abbruch: Auswahl der Person
		if(hid != AppartmentSelectionMenu.BACK) {
			//Alle Personen laden
			Set<Person> personen = service.getAllPersons();
			
			//Menü zur Auswahl der Person
			PersonSelectionMenu psm = new PersonSelectionMenu("Person für Vertrag auswählen", personen);
			int pid = psm.show();
			
			//Falls kein Abbruch: Vertragsdaten abfragen und Vertrag anlegen
			if(pid != PersonSelectionMenu.BACK) {
				Set<Integer> immobIdSet =  service.getImmobIdInAllVertraege();
				if (immobIdSet.contains((Integer)hid)) {
					System.out.println("Dieses Haus ist schon gekauft");
				}else{
					Kaufvertrag k = new Kaufvertrag();
					k.setImmobId(hid);
					k.setPersonId(pid);
					k.setDatum(FormUtil.readDate("Datum"));
					k.setOrt(FormUtil.readString("Ort"));
					k.setAnzahlRaten(FormUtil.readInt("Anzahl Raten"));
					k.setRatenzins(FormUtil.readInt("Ratenzins"));
					
					service.addKaufvertrag(k);
					
					System.out.println("Kaufvertrag mit der ID "+k.getVertragsnummer()+" wurde erzeugt.");
				}
				
			}
		}
	}
}
