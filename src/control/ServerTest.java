package control;

public class ServerTest {

	public static void main(String[] args) {
		Server server = null;
		try{
			server = new Server(2002,4000);
			server.start();
		}catch(Exception e){}
		finally{
			server.close();
		}
	}

}
