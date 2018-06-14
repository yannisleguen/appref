package outilserveur;

import java.net.Socket;

public class ServiceManager {
	private ServiceManager() {
		// instanciation interdite
	}
	private static Class<? extends Service> classeService;
	
	// concurrence init<-->newService sur l'attribut ServiceManager.classeService
	// verrou sur l'objet ServiceManager.class
	public static void init(Class<? extends Service> classeService) 
			throws InstantiationException, IllegalAccessException {
		classeService.newInstance();
		synchronized (ServiceManager.class) {
			ServiceManager.classeService = classeService;
		}
	}
	public static Service newService(Socket client_socket) {
		
		Service service = null;
		try {
			synchronized (ServiceManager.class) {
				service = ServiceManager.classeService.newInstance();
			}
			service.setSocket(client_socket);
		} catch (InstantiationException | IllegalAccessException e) {
			// devrait déjà avoir été testé
		}
		return service;
	}

}
