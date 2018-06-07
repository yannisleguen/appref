package bri;

import java.util.List;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partagée en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = null;
	}
	private static List<Class<?>> servicesClasses;

// ajoute une classe de service après contrôle de la norme BLTi
	public static void addService() {
		// vérifier la conformité par introspection
		// si non conforme --> exception avec message clair
		// si conforme, ajout au vector
	}
	
// renvoie la classe de service (numService -1)	
	public static void getServiceClass(int numService) {
		
	}
	
// liste les activités présentes
	public static String toStringue() {
		String result = "Activités présentes :##";
		// todo
		return result;
	}

}
