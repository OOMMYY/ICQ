package dao;

import java.sql.SQLException;
import java.util.List;

import model.relation;

public class test {

	public static void main(String[] args) throws SQLException {
		
		//System.out.println(new dao().getUser("120"));
		//new dao().getMessage("120");
//		List<message> li = new dao().getMessage("120");
//		for(message m:li){
//			System.out.println(m);
//		}
		List<relation> li = new dao().getRelation("120");
		for(relation r:li){
			System.out.println(r);
		}		
	}

}
