import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class test{
	public static void main(String[] args) {
		String pwd="123";
		String p=encryp("123");
		System.out.println(encryp(pwd));
		System.out.println(p.equals(encryp("123")));
	}
	public static String encryp(String pwd){
		byte[] message=null;
		message = pwd.getBytes();
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encrypwd =md.digest(message);
		BigInteger bigInteger = new BigInteger(1, encrypwd);
		return bigInteger.toString(16);
	}
}