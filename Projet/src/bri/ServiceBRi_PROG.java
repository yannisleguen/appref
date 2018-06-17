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
		User usr2 = new User("ylgn", "ylgn", "ftp://yleguen.com:2121/ylgn");
		
		listUser.add(usr1);
		listUser.add(usr2);
		
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			//Entrer les credentials
			String login, mdp;
			User currentUser;
			do{
			out.println("Entrez le login svp :");
			login = in.readLine();
			out.println("Entrez le mdp svp");
			mdp = in.readLine();
			}while(!checkUserCredentials(login, mdp));			
			currentUser = getUser(login, mdp);
			out.println(consigne());
			
			int choix = Integer.parseInt(in.readLine());
			
			
			if (choix == 1) {
				
					out.println("Entrer la classe svp");
					String classeName = in.readLine();
					addService(classeName, currentUser);
					out.println("La classe  a bien �t� ajout�e !");
					
				
			}
			else if (choix == 2){
					out.println(ServiceRegistry.toStringue()+"##Tapez le num�ro du service a maj :");
					choix=Integer.parseInt(in.readLine());				
					updateService(currentUser,choix);
					out.println("La classe � bien �t� mise � jour !");
					
				
				
			}
			else if (choix == 3){
				
				out.println("Merci d'entrer le nouvel URL ftp");
				String newURL = in.readLine();
				try {
					changeFTP(currentUser, newURL);
					out.println("L'adresse � bien �t�e chang�e :)");
				} catch (Exception e) {
					// TODO: handle exception
					out.println("Erreur, l'adresse n'est pas valide");
				}
			}
	
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


		
			
		
	
	private void updateService(User currentUser, int choice) {
		String className =ServiceRegistry.getServiceClass(choice-1).getName();
		
		ServiceRegistry.removeClass(choice-1);
		
		addService(className, currentUser);
		
	}

	protected void addService(String classeName, User usr){
		String fileNameURL = usr.getFtpAdress();
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
		usr.setFtpAdress(newURL);
	}
	public Boolean checkUserCredentials(String login, String passWd){
		for (int i = 0; i < listUser.size(); i++) {
			if (listUser.get(i).getLogin().equals(login) && listUser.get(i).getPassWd().equals(passWd)) {
				return true;
			}
		}
		/*for (User user : listUser) {
			return (user.getLogin().equals(login) && user.getPassWd().equals(passWd)); 
				}*/
		return false;
	}
	public User getUser(String login, String passWd){
		 for (User user : listUser) {
			 if (user.getLogin().equals(login) && user.getPassWd().equals(passWd)) {
				 return user;
			 }
		}
		return null;
		
	}
	
	protected String consigne(){
		return "Que voulez-vous faire?"
				+ "##Tappez 1 : Pour ajouter un service "
				+ "##Tappez 2 : Pour mettre un jour un service tappez 2"
				+ "##Tappez 3 : Pour D�clarer un changement d'adresse de serveur FTP";
	}
	
	

}
