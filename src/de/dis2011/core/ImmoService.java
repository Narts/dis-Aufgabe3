package de.dis2011.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.dis2011.data.Haus;
import de.dis2011.data.Immobilie;
import de.dis2011.data.Kaufvertrag;
import de.dis2011.data.Makler;
import de.dis2011.data.Mietvertrag;
import de.dis2011.data.Person;
import de.dis2011.data.Vertrag;
import de.dis2011.data.Wohnung;

/**
 * Klasse zur Verwaltung aller Datenbank-Entitäten.
 * 
 * TODO: Aktuell werden alle Daten im Speicher gehalten. Ziel der Übung
 * ist es, schrittweise die Datenverwaltung in die Datenbank auszulagern.
 * Wenn die Arbeit erledigt ist, werden alle Sets dieser Klasse überflüssig.
 */
public class ImmoService {	
	//Hibernate Session
	private SessionFactory sessionFactory;
	
	public ImmoService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * Finde einen Makler mit gegebener Id
	 * @param id Die ID des Maklers
	 * @return Makler mit der ID oder null
	 */
	public Makler getMaklerById(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Makler aMakler = (Makler) session.get(Makler.class, id);
		session.getTransaction().commit();
		return aMakler;
	}
	
	/**
	 * Finde einen Makler mit gegebenem Login
	 * @param login Der Login des Maklers
	 * @return Makler mit der ID oder null
	 */
	public Makler getMaklerByLogin(String login) {		
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Makler as m where m.login = ?";
		session.beginTransaction();
		Makler amakler = (Makler) session
			.createQuery(hql)
			.setParameter(0,login)
			.uniqueResult();
		
		session.getTransaction().commit();
		return amakler;
	}
	
	/**
	 * Gibt alle Makler zurück
	 */
	public Set<Makler> getAllMakler() {
		List<Makler> mList = new ArrayList<Makler>();
		Set<Makler> mSet = new HashSet<Makler>();

		Session session = sessionFactory.getCurrentSession();
		String hql = "from Makler";
		session.beginTransaction();
	    mList = (List<Makler>) session
			.createQuery(hql)
			.list();
		session.getTransaction().commit();
		for (int i = 0; i < mList.size(); i++) {
			mSet.add(mList.get(i));
		}
		return mSet;
	}
	
	/**
	 * Finde eine Person mit gegebener Id
	 * @param id Die ID der Person
	 * @return Person mit der ID oder null
	 */
	public Person getPersonById(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Person aPerson = (Person) session.get(Person.class, id);
		session.getTransaction().commit();
		return aPerson;
	}
	
	/**
	 * Fügt einen Makler hinzu
	 * @param m Der Makler
	 */
	public void addMakler(Makler m) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
	}
	
	/**
	 * Löscht einen Makler
	 * @param m Der Makler
	 */
	public void deleteMakler(Makler m) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(m);
        session.getTransaction().commit();
	}
	
	/**
	 * Fügt eine Person hinzu
	 * @param p Die Person
	 */
	public void addPerson(Person p) {
//personen.add(p);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}
	
	/**
	 * Gibt alle Personen zurück
	 */
	public Set<Person> getAllPersons() {
		Set<Person> pSet = new HashSet<Person>();
		Session session = sessionFactory.getCurrentSession();
		List<Person> pList = new ArrayList<Person>();
		String hql = "from Person";

		session.beginTransaction();
		pList = (List<Person>) session
			.createQuery(hql)
			.list();
		session.getTransaction().commit();
		for (int i = 0; i < pList.size(); i++) {
			pSet.add(pList.get(i));
		}
		return pSet;		
	}
	
	/**
	 * Löscht eine Person
	 * @param p Die Person
	 */
	public void deletePerson(Person p) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
		
//personen.remove(p);
	}
	
	/**
	 * Fügt ein Haus hinzu
	 * @param h Das Haus
	 */
	public void addHaus(Haus h) {
//haeuser.add(h);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(h);
		session.getTransaction().commit();
	}
	
	/**
	 * Gibt alle Häuser eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Häuser, die vom Makler verwaltet werden
	 */
	public Set<Haus> getAllHaeuserForMakler(Makler m) {
		List<Haus> ret0 = new ArrayList<Haus>();
		Set<Haus> ret = new HashSet<Haus>();
		Session session = sessionFactory.getCurrentSession();
		int maklerID = m.getId();
		String hql = "from Haus where maklerid = maklerID";
		session.beginTransaction();
	    ret0 = (List<Haus>) session
			.createQuery(hql)
			.list();
	    for (int i = 0; i < ret0.size(); i++) {
			ret.add(ret0.get(i));
		}
		
		session.getTransaction().commit();
		return ret;	
	}
	
	/**
	 * Findet ein Haus mit gegebener ID
	 * @param m Der Makler
	 * @return Das Haus oder null, falls nicht gefunden
	 */
	public Haus getHausById(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Haus aHaus = (Haus) session.get(Haus.class, id);
		session.getTransaction().commit();
		return aHaus;	

	}
	
	/**
	 * Löscht ein Haus
	 * @param p Das Haus
	 */
	public void deleteHouse(Haus h) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(h);
		session.getTransaction().commit();
		
//		haeuser.remove(h);
	}
	
	/**
	 * Fügt eine Wohnung hinzu
	 * @param w die Wohnung
	 */
	public void addWohnung(Wohnung w) {
//wohnungen.add(w);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();
	}
	
	/**
	 * Gibt alle Wohnungen eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Wohnungen, die vom Makler verwaltet werden
	 */
	public Set<Wohnung> getAllWohnungenForMakler(Makler m) {
		List<Wohnung> ret = new ArrayList<Wohnung>();
		Set<Wohnung> fet = new HashSet<Wohnung>();
		Session session = sessionFactory.getCurrentSession();
		int maklerID = m.getId();
		String hql = "from Wohnung where maklerid = maklerID";
		session.beginTransaction();
	    ret = (List<Wohnung>) session
			.createQuery(hql)
			.list();
		session.getTransaction().commit();
		
		for (int i = 0; i < ret.size(); i++) {
			fet.add(ret.get(i));
		}
		return fet;
	}
	
	/**
	 * Findet eine Wohnung mit gegebener ID
	 * @param id Die ID
	 * @return Die Wohnung oder null, falls nicht gefunden
	 */
	public Wohnung getWohnungById(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Wohnung aWohnung = (Wohnung) session.get(Wohnung.class, id);
		session.getTransaction().commit();
		return aWohnung;
	}
	
	/**
	 * Löscht eine Wohnung
	 * @param p Die Wohnung
	 */
	public void deleteWohnung(Wohnung w) {
//		wohnungen.remove(w);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(w);
		session.getTransaction().commit();

	}
	
	
	/**
	 * Fügt einen Mietvertrag hinzu
	 * @param w Der Mietvertrag
	 */
	public void addMietvertrag(Mietvertrag m) {
//mietvertraege.add(m);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
	}
	
	/**
	 * Fügt einen Kaufvertrag hinzu
	 * @param w Der Kaufvertrag
	 */
	public void addKaufvertrag(Kaufvertrag k) {
//kaufvertraege.add(k);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(k);
		session.getTransaction().commit();
	}
	
	/**
	 * Gibt alle Mietverträge zu Wohnungen eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Mietverträge, die zu Wohnungen gehören, die vom Makler verwaltet werden
	 */
	public Set<Mietvertrag> getAllMietvertraegeForMakler(Makler m) {
		Set<Mietvertrag> ret = new HashSet<Mietvertrag>();
		List<Immobilie> iet = new ArrayList<Immobilie>();
		int maklerID = m.getId();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Immobilie where maklerid = maklerID "; // m verfuegt ueber immobilie
		String hql2 = "from Mietvertrag where immobId = ? "; 

		session.beginTransaction();
	    iet = (List<Immobilie>) session
			.createQuery(hql)
			.list();
	    
	    
	    for (int i = 0; i < iet.size(); i++) {
	    	int immobID = iet.get(i).getId();
	    	Vertrag vtg = (Vertrag)session
	    	.createQuery(hql2)
	    	.setInteger(0, immobID)
	    	.uniqueResult();
			if (vtg instanceof Mietvertrag) {
				Mietvertrag ret0 = (Mietvertrag)vtg;
				ret.add(ret0);
			}
		}
		session.getTransaction().commit();
		return ret;
	}
	
	/**
	 * Gibt alle Kaufverträge zu Wohnungen eines Maklers zurück
	 * @param m Der Makler
	 * @return Alle Kaufverträge, die zu Häusern gehören, die vom Makler verwaltet werden
	 */
	public Set<Kaufvertrag> getAllKaufvertraegeForMakler(Makler m) {		
		Set<Kaufvertrag> ret = new HashSet<Kaufvertrag>();
		List<Immobilie> iet = new ArrayList<Immobilie>();
		int maklerID = m.getId();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Immobilie where maklerid = maklerID "; // m verfuegt ueber immobilie
		String hql2 = "from Kaufvertrag where immobId = ? "; 

		session.beginTransaction();
	    iet = (List<Immobilie>) session
			.createQuery(hql)
			.list();
	    
	    for (int i = 0; i < iet.size(); i++) {
	    	int immobID = iet.get(i).getId();
	    	Vertrag vtg = (Vertrag)session
	    	.createQuery(hql2)
	    	.setInteger(0, immobID)
	    	.uniqueResult();
			if (vtg instanceof Kaufvertrag) {
				Kaufvertrag ret0 = (Kaufvertrag)vtg;
				ret.add(ret0);
			}
		}
	    
		session.getTransaction().commit();

		return ret;
	}
	
	/**
	 * Findet einen Mietvertrag mit gegebener ID
	 * @param id Die ID
	 * @return Der Mietvertrag oder null, falls nicht gefunden
	 */
	public Mietvertrag getMietvertragById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Mietvertrag aMietvertrag = (Mietvertrag) session.get(Mietvertrag.class, id);
		session.getTransaction().commit();
		return aMietvertrag;
	}
	
	/**
	 * Findet alle Mietverträge, die Wohnungen eines gegebenen Verwalters betreffen
	 * @param id Der Verwalter
	 * @return Set aus Mietverträgen
	 */
	public Set<Mietvertrag> getMietvertragByVerwalter(Makler m) {
		Set<Mietvertrag> ret = new HashSet<Mietvertrag>();
		Set<Immobilie> iet = new HashSet<Immobilie>();

		Session session = sessionFactory.getCurrentSession();
		String hql = "form Immobilie where maklerid = m "; // m verfuegt ueber immobilie
		String hql2 = "form Mietvertrag where immobId in ? "; 

		session.beginTransaction();
	    iet = (Set<Immobilie>) session
			.createQuery(hql)
			.list();
	    
        ret = (Set<Mietvertrag>) session
        .createQuery(hql2)
        .setParameter(0, iet)
        .list();
		
		session.getTransaction().commit();
		return ret;
	}
	
	/**
	 * Findet alle Kaufverträge, die Häuser eines gegebenen Verwalters betreffen
	 * @param id Der Verwalter
	 * @return Set aus Kaufverträgen
	 */
	public Set<Kaufvertrag> getKaufvertragByVerwalter(Makler m) {
//		Set<Kaufvertrag> ret = new HashSet<Kaufvertrag>();
//		Iterator<Kaufvertrag> it = kaufvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Kaufvertrag k = it.next();
//			
//			if(getHausById(k.getImmobId()).getVerwalter().getId() == m.getId())
//				ret.add(k);
//		}
//		
//		return ret;
		Set<Kaufvertrag> ret = new HashSet<Kaufvertrag>();
		Set<Immobilie> iet = new HashSet<Immobilie>();
		Session session = sessionFactory.getCurrentSession();
		String hql = "form Immobilie where maklerid = m "; // m verfuegt ueber immobilie
		String hql2 = "form Kaufvertrag where immobId in ? "; 

		session.beginTransaction();
	    iet = (Set<Immobilie>) session
			.createQuery(hql)
			.list();
	    
        ret = (Set<Kaufvertrag>) session
        .createQuery(hql2)
        .setParameter(0, iet)
        .list();
		
		session.getTransaction().commit();
		
		return ret;
	}
	
	/**
	 * Findet einen Kaufvertrag mit gegebener ID
	 * @param id Die ID
	 * @return Der Kaufvertrag oder null, falls nicht gefunden
	 */
	public Kaufvertrag getKaufvertragById(int id) {
//		Iterator<Kaufvertrag> it = kaufvertraege.iterator();
//		
//		while(it.hasNext()) {
//			Kaufvertrag k = it.next();
//			
//			if(k.getVertragsnummer() == id)
//				return k;
//		}s
//		
//		return null;
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Kaufvertrag aKaufvertrag = (Kaufvertrag) session.get(Kaufvertrag.class, id);
		session.getTransaction().commit();
		return aKaufvertrag;
	}
	
	/**
	 * Löscht einen Mietvertrag
	 * @param m Der Mietvertrag
	 */
	public void deleteMietvertrag(Mietvertrag m) {
//		wohnungen.remove(m);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(m);
		session.getTransaction().commit();

	}
	
	/**
	 * update einen Makler
	 * @param m Der Makler
	 */
    public void updateMakler(Makler m)
    {
    	Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
    	session.update(m);
		session.getTransaction().commit();

    }
    
	/**
	 * update einen Person
	 * @param p Der Person
	 */
    public void updatePerson(Person p)
    {
    	Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
    	session.update(p);
		session.getTransaction().commit();

    }
    
	/**
	 * update einen Haus
	 * @param h das Haus
	 */
    public void updateHaus(Haus h)
    {
    	Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
    	session.update(h);
		session.getTransaction().commit();

    }
    
	/**
	 * update eine Wohnung
	 * @param w die Wohnung
	 */
    public void updateWohnung(Wohnung w)
    {
    	Session session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
    	session.update(w);
		session.getTransaction().commit();

    }
    
	/**
	 * Gibt alle Verträgezurück
	 * 
	 * @return Alle Verträge
	 */
	public Set<Integer> getImmobIdInAllVertraege() {
		Set<Integer> ret = new HashSet<Integer>();
		List<Vertrag> iet = new ArrayList<Vertrag>();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Vertrag"; 
		session.beginTransaction();
	    iet = (List<Vertrag>) session
			.createQuery(hql)
			.list();
	    
	    
	    for (int i = 0; i < iet.size(); i++) {
			ret.add(iet.get(i).getImmobId());
		}
		session.getTransaction().commit();
		return ret;
	}
}
