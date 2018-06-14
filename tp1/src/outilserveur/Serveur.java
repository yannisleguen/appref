package outilserveur;
// contient les classes Serveur, Service (abstract) et ServiceManager (tout static)
import java.io.*;
import java.net.*;

public class Serveur implements Runnable {
	private ServerSocket listen_socket; // le serveur d'écoute
	private Thread thread;
	
	public Serveur(int port) throws IOException {  
			listen_socket = new ServerSocket(port);
			this.thread = new Thread(this);			
	}

	public void start(){
		this.thread.start();
	}

	// à surcharger si le serveur veut passer d'autres infos au dialogue
	protected void init(Service service) {
		
			}

	public void run() {
		System.out.println("Serveur lancé");
		try {
			while(true) {
				Socket client_socket = listen_socket.accept();
				Service service = ServiceManager.newService(client_socket);
				service.start();
			}
		}
		catch (IOException e) { }
		finally {
			this.close();
		}
		
		System.err.println("Serveur arreté ");
	}
	public void close() {
		try {this.listen_socket.close();} catch (IOException e) {}
	}
}
