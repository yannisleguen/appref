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
			out.println(ServiceRegistry.toStringue()+"##Tapez le numéro de service désiré :");
			int choix = Integer.parseInt(in.readLine());
			// instancier le service numéro "choix" en lui passant la socket "client"
			
			Class<? extends Service> service = ServiceRegistry.getServiceClass(choix-1);
			
			
			try {
				service.getConstructor(Socket.class).newInstance(client).run();
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.println("Constructor not instancied");
			}
			
			
			
			
			
			// invoquer run() pour cette instance ou la lancer dans un thread à part 
				
			
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
