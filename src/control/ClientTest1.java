package control;

import view.loginview;

public class ClientTest1 {

	public static void main(String[] args) {
		Client client =null;
		try{
			client = new Client();
			loginview  login = new loginview(client);
			login.setVisible(true);	
			client.start();
		}catch(Exception e){}
		finally{
			client.close();
		}
	}

}
