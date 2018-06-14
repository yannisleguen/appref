package appli;

import bri.ServeurBRi;

public class BRiLaunch {
	private final static int PORT_PROG = 3000;
	private final static int PORT_AMA = 1998;
	
	
	public static void main(String[] args) {
	
		
		
		// URLClassLoader sur ftp
		
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
		System.out.println("Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp");
		System.out.println("Les clients Amamteurs se connectent au serveur 1998 pour lancer une activité");
		System.out.println("Les programmeurs se connectent au serveur sur le port 3000");
		
		new Thread(new ServeurBRi(PORT_PROG)).start();
		new Thread(new ServeurBRi(PORT_AMA)).start();
		
			
	}
}
