package appli;

public class User {
	private String login;
	private String passWd;
	private String ftpAdress;
	
	public User(String login, String passWd, String ftpAdress){
		this.login = login;
		this.passWd = passWd;
		this.ftpAdress = ftpAdress;
	
	}

	public String getFtpAdress() {
		return ftpAdress;
	}

	public void setFtpAdress(String ftpAdress) {
		this.ftpAdress = ftpAdress;
	}

	public String getLogin() {
		return this.login;
	}

	public String getPassWd() {
		return this.passWd;
	}
	

}
