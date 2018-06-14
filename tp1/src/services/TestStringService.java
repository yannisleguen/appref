package services;

import java.io.*;
import outilserveur.Service;
import outilserveur.ServiceManager;

public class TestStringService extends Service {

	static {
		try {
			ServiceManager.init(services.TestStringService.class);
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println(e);
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
				
				// teste la ligne
				boolean test = test(line);
				// et envoie le résultat dans la socket
				out.println(test);
			}
		}
		catch (IOException e) {
			System.err.println("Erreur dans le dialogue des sockets : " + e);
		}

		finally { try {this.getSocket().close();} catch (IOException e2) {} }
	}

	protected boolean test(String line) { // test palindrome
		return line.equals(new StringBuffer(line).reverse().toString());
	}
	
}
