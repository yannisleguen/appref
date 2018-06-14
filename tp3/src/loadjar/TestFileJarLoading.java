package loadjar;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

public class TestFileJarLoading {

	// chargement dynamique d'un jarfile contenant une seule classe
	// cette classe implémente l'interface VerySimple

	public static void main(String[] args) throws MalformedURLException 
			 {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		
		String fileName = "../myclasses/";
		String fileNameURL = "file:"+fileName;  // ou file:///c:/etc
		
		URLClassLoader urlcl = URLClassLoader.newInstance(new URL[] {new URL(fileNameURL)});
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité");
		System.out.println("1 : ajouter une activité nouvelle");
		System.out.println("2 : lancer une activité déjà chargée");
		System.out.println("3 : faire un bilan des activités");
		
		while (true){
			System.out.print(">");

			switch (clavier.next()){
			case "1" : 
				System.out.print("Nom de la classe :");

				try {
					RunnableManager.addRunnable(urlcl.loadClass(clavier.next()).asSubclass(Runnable.class));
				} catch (ClassNotFoundException e) {
					System.out.println(e);;
				}

				break;
			case "2" : 
				System.out.println(RunnableManager.toStringue());
				System.out.print("N° de l'activité :");
				RunnableManager.lance(Integer.parseInt(clavier.next())-1);
				break;
			case "3" : 
				System.out.println(RunnableManager.bilan());
				break;
			}
		}
		
		
	}
	@SuppressWarnings("unused")
	private void test() throws Exception {

		//les classes à charger sont dans le répertoire myclasses du workspace
		//System.out.println(TestFileJarLoading.class.getClassLoader());
		String fileName = "../myclasses/";
		String fileNameURL = "file:"+fileName;  // ou file:///c:/etc
		
		URLClassLoader urlcl = URLClassLoader.newInstance(new URL[] {new URL(fileNameURL)});
		
		
		Class<?> classeChargee1, classeChargee2;
		
		// on charge la classe et on la déclare à RunnableManager
		classeChargee1 = urlcl.loadClass("runnables.Coucou");
		classeChargee2 = urlcl.loadClass("runnables.Salut");

		// on teste si la classe est conforme (Runnable et constructeur vide licite)
		
		// on la déclare à RunnableManager
		RunnableManager.addRunnable(classeChargee1.asSubclass(Runnable.class));
		RunnableManager.addRunnable(classeChargee2.asSubclass(Runnable.class));
		// on crée un thraed qui lance l'activité
		System.out.println(RunnableManager.toStringue());
		RunnableManager.lance(0);
		RunnableManager.lance(1);
		
		
	}
}
