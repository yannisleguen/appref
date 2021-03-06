package bri;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;


class ServiceBRi implements Runnable {
	
	private Socket client;
	
	ServiceBRi(Socket socket) {
		client = socket;
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue()+"##Tapez le num�ro de service d�sir� :");
			int choix = Integer.parseInt(in.readLine());
			// instancier le service num�ro "choix" en lui passant la socket "client"
			Class<?> service = ServiceRegistry.getServiceClass(choix);
			
			
			try {
				service.getConstructor().newInstance(client);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Method method = service.getMethod("run");
				try {
					method.invoke(null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			// invoquer run() pour cette instance ou la lancer dans un thread � part 
				
			}
		catch (IOException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}
	

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
