package services;

import java.io.*;

import outilserveur.Service;
import outilserveur.ServiceManager;

public class ServiceInversion extends Service {

	static {
		try {
			ServiceManager.init(services.ServiceInversion.class);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("chargement classe service a échoué : "+e);
		}
	}

	// Fournit le service (inverse la ligne recue et la
	// retourne)
	public void run() {
		System.out.print("Nouveau client : ");
		try {System.out.println(this.getSocket().getInetAddress());		
			BufferedReader in = new BufferedReader (new InputStreamReader(this.getSocket().getInputStream ( )));
			PrintWriter out = new PrintWriter (this.getSocket().getOutputStream ( ), true);

			while (true) {
				// lit la ligne
				String line = in.readLine();
				System.out.println("Connexion a recu " + line);
		
				// Si cette ligne est vide, le serveur se termine
				if (line.equals("")) break;
				// sinon l'ecrit a l'envers
				String invLine = new String (new StringBuffer(line).reverse());
				// et l'envoie dans la socket
				out.println(invLine);
			}
		}
		catch (IOException e) {
			System.err.println("Erreur dans le dialogue des sockets : " + e);
		}

		finally { try {this.getSocket().close();} catch (IOException e2) {} }
	}
	
}
