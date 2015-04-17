package csc.fresher.finalproject.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener, responsible for creating an entity manager factory when application starts
 * and destroys when application ends
 * @author nvu3
 *
 */
public class EntityManagerFactoryUtil implements ServletContextListener {

	private static EntityManagerFactory emf;
	
	public static void setEntityManagerFactory(){
		emf = Persistence.createEntityManagerFactory("FinalProject");
	}

	/**
	 * Used to get entity manager
	 * 
	 * @return EntityManager
	 */
	public static EntityManager createEntityManager() {
		if (emf == null) {
			throw new IllegalStateException("Context is not initialized yet.");
		}

		return emf.createEntityManager();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// Destroys entity manager factory
		emf.close();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// Creates entity manager factory
		emf = Persistence.createEntityManagerFactory("FinalProject");
	}

}
