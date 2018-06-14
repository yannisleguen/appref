package bri;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.List;
import java.util.Vector;
import appli.User;


class ServiceBRi_PROG implements Runnable {
	
	private Socket client;
	static {
		listUser = new Vector<User>();
	}
	private static List<User> listUser;
	
	ServiceBRi_PROG(Socket socket) {
		client = socket;
	}

	public void run() {
		User usr1 = new User("prog","prog" ,"ftp://localhost:2121/classes/");
		
		listUser.add(usr1);
		
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			//Entrer les credentials
			String login, mdp;
			User currentUser;
			do{
			out.println("Entrez le login :");
			login = in.readLine();
			out.println("Entrez le mdp");
			mdp = in.readLine();
			}while(checkUserCredentials(login, mdp));			
			currentUser = getUser(login, mdp);
			out.println(consigne());
		
			int choix = Integer.parseInt(in.readLine());
			
			
			if (choix == 1) {
				
					out.println("Entrer la classe svp");
					String classeName = in.readLine();
					addService(classeName, currentUser);
					out.println("La classe  a bien été ajoutée !");
					
				
			}
			else if (choix == 2){
				
					out.println("Entrer la classe svp");
					String classeName = in.readLine();
					updateService(classeName, currentUser);
					out.println("La classe à bien été mise à jour !");
					
				
				
			}
			/*else if (choix == 3){
				String newURL = in.readLine();
				try {
					changeFTP(currentUser, newURL);
					out.println("L'adresse à bien étée changée :)");
				} catch (Exception e) {
					// TODO: handle exception
					out.println("Erreur, l'adresse n'est pas valide");
				}
			}*/
	
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


		
			
		
	
	private void updateService(String classeName, User currentUser) {
		ServiceRegistry.removeClass(classeName);
		addService(classeName, currentUser);
		
	}

	protected void addService(String classeName, User usr){
		String fileNameURL = "ftp://localhost:2121/classes/";
		URLClassLoader urlcl = null;
		try {
			urlcl = URLClassLoader.newInstance(new URL[]{new URL(fileNameURL)});
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Class<?> c = null;
		try {
			c = urlcl.loadClass(classeName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServiceRegistry.addService(c);
	}
	protected void changeFTP(User usr, String newURL){
		
	}
	public Boolean checkUserCredentials(String login, String passWd){
		for (User user : listUser) {
			if (user.getLogin()==login && user.getPassWd() == passWd) {
				return true;
			}
		}	
		return false;
	}
	public User getUser(String login, String passWd){
		 for (User user : listUser) {
			 if (user.getLogin()==login && user.getPassWd() == passWd) {
				 return user;
			 }
		}
		return null;
		
	}
	
	protected String consigne(){
		return "Que voulez-vous faire?"
				+ "##Tappez 1 : Pour ajouter un service "
				+ "##Tappez 2 : Pour mettre un jour un service tappez 2"
				+ "##Tappez 3 : Pour Déclarer un changement d'adresse de serveur FTP";
	}
	
	

}
