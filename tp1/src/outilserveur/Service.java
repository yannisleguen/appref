package outilserveur;

import java.net.*;

//  la classe Service, � sous-classer pour impl�menter le service dans run()

public abstract  class Service implements Runnable {
	private Socket socket;
	private Thread t;
	
	/**
	 * getSocket sera utilis�e uniquement dans votre service, donc protected
	 * Creation date: (18/12/2003 16:13:51)
	 * 
	 * @return java.net.Socket
	 */

	protected Socket getSocket() {
		return socket;
	}
	
	// impl�menter le run dans la sous-classe

	@Override
	public abstract void run();

	/**
	 * setSocket permet l'init par Serveur, donc (package) 
	 * Creation date:
	 * (17/12/2003 13:54:07)
	 * 
	 * @param newSocket
	 *            java.net.Socket
	 */
	void setSocket(java.net.Socket newSocket) {
		socket = newSocket;
	}

	/**
	 * start uniquement par Serveur Creation date: (17/12/2003 13:54:07)
	 */
	void start() {
		this.t = new Thread(this);
		this.t.start();
	}
	// interruption du service par l'application - comportement suspect, inactivit�, etc
	public void close(){
		this.t.interrupt(); // --> InterruptedException ou InterruptedIOException suivant les m�thodes bloquantes
	}

	@Override
	protected void finalize() throws Throwable {
		socket.close();
	}




}
