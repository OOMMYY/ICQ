package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import model.message;
import model.relation;
import model.user;
public class dao{
	static String username="root";
	static String pwd = "";
	static String url = "jdbc:mysql://127.0.0.1:3306/superqq"; 
	static String driver = "org.gjt.mm.mysql.Driver";
	static Connection con;
	static Statement statement;
	public dao(){}
	public static void main(String[] args){
		dao d =  new dao();
	}
	private static Date convertToDate(String str){//用于将sql.date转化为util.date
		String[] li = str.split(" ");
		if(li.length!=2){
			System.out.println("converToDate 出错，空格错误");
			return new Date();
		}
		String[] para = li[0].split("-");
		String[] para1 = li[1].split(":");
		return new GregorianCalendar(new Integer(para[0]).intValue(),new Integer(para[1]).intValue(),new Integer(para[2]).intValue(),new Integer(para1[0]).intValue(),new Integer(para1[1]).intValue(),new Integer(para[2]).intValue()).getTime();		
	}
	private void start(){
		try {
			Class.forName( driver );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("error in loading driver.");
		}
		try {
			con = DriverManager.getConnection(url,username,pwd);
			statement = con.createStatement();  	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in connect database.");
		} 	
	}
	private void close(){
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in close statement.");
		} 
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in close connection.");
		}
	}
	public user getUser(String id){
		start();
		List<user> li = new LinkedList<user>();
		try {
			ResultSet re = statement.executeQuery("select * from user where id ="+id+";");
			while(re.next()){
				li.add(new user(re.getString(1),re.getString(2),re.getString(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getUser("+id+")");
		} 
		finally{close();}
		return li.get(0);
	}
	public user getUser(String name,String pwd){
		start();
		List<user> li = new LinkedList<user>();
		try {
			ResultSet re = statement.executeQuery("select * from user where name ='"+name+"';");
			while(re.next()){
				li.add(new user(re.getString(1),re.getString(2),re.getString(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getUser("+name+","+pwd+")");
		} 
		finally{close();}
		if(li.size()!=0){
			return li.get(0);	
		}else{
			return null;
		}
	}
	public List<relation> getRelation(String id){
		start();
		List<relation> li = new LinkedList<relation>();
		try {
			statement = con.createStatement();  
			ResultSet re = statement.executeQuery("select * from relation where id1 ="+id+"||id2 ="+id+";");
			while(re.next()){
				li.add(new relation(re.getString(1),re.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getRelation("+id+")");
		} 
		finally{close();}
		return li;
	}

	public List<user> getUserList(){
		start();
		List<user> li=new LinkedList<user>();
		ResultSet re;
		try {
			re = statement.executeQuery("select * from user;");
			while(re.next()){
				li.add(new user(re.getString(1),re.getString(2),re.getString(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{close();}
		return li;
	}
	public Boolean addUser(user u){
		start();
		Boolean flag = true;
		try {
			statement.execute("insert into user(name,pwd) values('"+u.getUsername()+"','"+u.getPwd()+"');");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in addUser:"+u);
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public Boolean deleteUser(String id){
		start();
		Boolean flag = true;
		try {
			statement.execute("delete from user where id ='"+id+"';");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in deleteUser:id="+id);
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public Boolean updateUser(user u){
		start();
		Boolean flag=true;
		try {
			statement.execute("update user set name='"+u.getUsername()+"',pwd ='"+u.getPwd()+"' where id ='"+u.getId()+"';");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in update:"+u);
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public Boolean updatePwd(user u){
		start();
		Boolean flag=true;
		try {
			statement.execute("update user set pwd='"+u.getUsername()+"' where id ='"+u.getId()+"';");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in updatepwd:"+u);
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public List<relation> getRelation(){
		start();
		List<relation> li=new LinkedList<relation>();
		ResultSet re;
		try {
			re = statement.executeQuery("select * from relation;");
			while(re.next()){
				li.add(new relation(re.getString(1),re.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getRelation().");
		}
		finally{close();}
		return li;
	}
	public Boolean addRelation(relation r){//先对id1，id2排序,再插入
		start();
		Boolean flag = true;
		try {
			String id1 = r.getId1();
			String id2 = r.getId2();
			if(new Integer(id1).intValue()>new Integer(id2).intValue()){
				String id = id1;
				id1 = id2;
				id2 = id;
			}
			statement.execute("insert into relation(id1,id2) values('"+id1+"','"+id2+"');");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in addRelation:"+r);
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public Boolean deleteRelation(relation r){//确保id有序（非减）
		start();
		Boolean flag = true;
		try {
			String id1 = r.getId1();
			String id2 = r.getId2();
			if(new Integer(id1).intValue()>new Integer(id2).intValue()){
				String id = id1;
				id1 = id2;
				id2 = id;
			}
			statement.execute("delete from relation where id1 = '"+id1+"'&&id2 ='"+id2+"';");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in deleteRelation("+r+").");
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public List<relation> getFailRelation(String id){
		start();
		List<relation> li=new LinkedList<relation>();
		ResultSet re;
		try {
			re = statement.executeQuery("select * from failrelation where id2 = '"+id+"';");
			while(re.next()){
				li.add(new relation(re.getString(1),re.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getFailRelation:id="+id);
		}
		finally{close();}
		return li;
	}
	public Boolean addFailRelation(relation r){
		start();
		Boolean flag =true;
		try {
			statement.execute("insert into failrelation(id1,id2) values('"+r.getId1()+"','"+r.getId2()+"');");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in addFailRelation("+r+").");
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public Boolean deleteFailRelation(relation r){
		start();
		Boolean flag = true;
		try {
			statement.execute("delete from failrelation where (id1='"+r.getId1()+"'&&id2 ='"+r.getId2()+"')||(id1='"+r.getId2()+"'&&id2='"+r.getId1()+"');");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in deleteFailRelation("+r+").");
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public List<user> getFriend(String id){
		start();
		List<user> li=new LinkedList<user>();
		ResultSet re;
		try {
			re = statement.executeQuery("select user.id,user.name from user,relation where (relation.id1 = '"+id+"'&&relation.id2 = user.id )||(relation.id2 = '"+id+"'&&relation.id1 = user.id);");
			while(re.next()){
				li.add(new user(re.getString(1),re.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getFriend("+id+").");
		}
		finally{close();}
		return li;
	}
	public Boolean addmessage(message msg){
		start();
		Boolean flag = true;
		try {
			statement.execute("insert into message(id1,id2,time,content) values('"+msg.getId1()+"','"+msg.getId2()+"','"+msg.getDate().toLocaleString()+"','"+msg.getContent()+"');");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error in addmessage("+msg+").");
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public List<message> getfailmessage(String id){
		start();
		List<message> li=new LinkedList<message>();
		ResultSet re;
		try {
			re = statement.executeQuery("select * from failmessage where id2 = '"+id+"';");
			while(re.next()){
				Date date = convertToDate(re.getString(3));
				li.add(new message(re.getString(1),re.getString(2),date,re.getString(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getfailmessage("+id+").");
		}
		finally{close();}
		return li;
	}
	public  Boolean addfailmessage(message msg){
		start();
		Boolean flag =true;
		try {
			statement.execute("insert into failmessage(id1,id2,time,content) values('"+msg.getId1()+"','"+msg.getId2()+"','"+msg.getDate().toLocaleString()+"','"+msg.getContent()+"');");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in addfailmessage("+msg+").");
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public  Boolean deletefailmessage(message msg){//待修正--应用id确定被删除离线消息
		start();
		Boolean flag = true;
		try {
			statement.execute("delete from failmessage where id1 = '"+msg.getId1()+"'&&id2 ='"+msg.getId2()+"';");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in deletefailmessage("+msg+").");
			flag = false;
		}
		finally{close();}
		return flag;
	}
	public List<message> getMessage(){
		start();
		List<message> li=new LinkedList<message>();
		ResultSet re;
		try {
			re = statement.executeQuery("select * from message;");
			while(re.next()){
				li.add(new message(re.getString(1),re.getString(2),re.getDate(3),re.getString(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getMessage().");
		}
		finally{close();}
		return li;
	}
	public List<message> getMessage(String id){
		start();
		List<message> li = new LinkedList<message>();
		try {
			ResultSet re = statement.executeQuery("select * from message where id1 ="+id+";");
			while(re.next()){
				String str = re.getString(3);
				Date date = new GregorianCalendar(new Integer(str.substring(0,4)).intValue(),new Integer(str.substring(5, 7)).intValue(),new Integer(str.substring(8, 10)).intValue(),new Integer(str.substring(11, 13)).intValue(),new Integer(str.substring(14, 16)).intValue(),new Integer(str.substring(17, 19)).intValue()).getTime();
				li.add(new message(re.getString(1),re.getString(2),date,re.getString(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getMessage("+id+")");
		} 	
		return li;		
	}
	public List<message> getMessage(String id1,String id2){//注意顺序，id1 查询与 id2的聊天记录。
		start();
		List<message> li=new LinkedList<message>();
		ResultSet re;
		try {
			re = statement.executeQuery("select * from message where (d1=1&&id1='"+id1+"'&&id2='"+id2+"')||(d2=1&&id1='"+id2+"'&&id2='"+id1+"') order by time ASC;");
			while(re.next()){
				li.add(new message(re.getString(1),re.getString(2),re.getDate(3),re.getString(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error in getMessage("+id1+","+id2+").");
		}
		finally{close();}
		return li;
	}
	public Boolean deleteMessage(String id1,String id2){
		start();
		Boolean flag = true;
		try {
			statement.execute("update message set d1 = 0 where id1 ='"+id1+"'&&id2='"+id2+"';");
			statement.execute("update message set d2 = 0 where id2 ='"+id1+"'&&id1='"+id2+"';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in deleteMessage("+id1+","+id2+").");
			flag = false;
		}
		finally{close();}
		return flag;
	}
}