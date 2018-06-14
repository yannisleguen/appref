package appli;

import java.util.Scanner;

import outilserveur.Serveur;

class AppliServeur {

	public static void main(String[] args) {
	// args[0] est une String d�signant la classe de service que ce serveur devra instancier
	// args[1] est le num�ro de port		
		try {
			// chargement-initialisation de la classe de service
			// celle-ci doit se d�clarer � ServiceManager dans son bloc static
			Class.forName(args[0]); // si pb dans le bloc static --> RuntimeException
			new Serveur(Integer.parseInt(args[1])).start();
			System.out.println("Serveur lanc�");
			
			// changement dynamique deu service
			System.out.println("Tapez une nouvelle classe de service pour changer ");
			Class.forName((new Scanner(System.in)).next());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
