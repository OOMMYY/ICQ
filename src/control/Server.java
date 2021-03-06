package control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import model.message;
import model.relation;
import model.user;
import dao.dao;

public class Server implements Runnable{
	List<user> li = new LinkedList<user>();//在线用户列表
	List<Socket> li_socket = new LinkedList<Socket>();
	ServerSocket server;
	Socket socket ;
	Object lock = new Object();
	Logger Log = Logger.getGlobal();
	public Server(int port,int queueLength){
		try{
			server = new ServerSocket(port,queueLength);//port,queueLength
		}catch(Exception e){}
		System.out.println("服务器创建成功。");
	}
	
	public List<user> getLi() {
		return li;
	}

	public void start(){
		System.out.println("服务器已启动……"+server);
		while(true){
			try{
				socket = server.accept();//系统在这里等待连接	
				new Thread(this).start();
				System.out.println(socket.getInetAddress()+"连接服务器成功。");
			}catch(Exception e){
				System.out.println("server error");
				try{
					socket.close();
				}catch(Exception ee){}
				close();
			}
		}
	}
	//主循环，根据请求码处理用户请求
	public void run(){//线程处理代码段,用以监听与客户端的连接。
		Socket socket = this.socket;
		BufferedReader input;
		PrintWriter output;
		String str = "";
		try {
			input =new BufferedReader( new InputStreamReader(socket.getInputStream()));//线程在这里等待输入流	
			output =new PrintWriter( new OutputStreamWriter(socket.getOutputStream()),true);	
			while(true){
				//00:登录
				//10:查询与指定好友的历史消息
				//11:点对点发msg
				//12:群发好友msg
				//13:删除历史消息
				//20:获得好友列表
				//21:添加好友
				//22:删除好友
				//23:确认添加好友
				//24:拒绝好友请求
				//30:更新用户信息
				//40:注册新用户	
				Thread.sleep(1000);
				try{
					str = input.readLine();
					System.out.println("服务器收到："+str);
				}catch(Exception e){
					socket.close();
					System.out.println("监听断开");
					break;
				}
				String cmd="";
				if(str!=null&&str.length()>2){
					cmd= str.substring(0,2);	
				}
				synchronized (lock) {//synchronized
				try{
				checkStatus();
				switch(cmd){
				case "00":{//处理结果：给客户端发送用户与朋友信息；登录
					str = str.substring(3).trim();
					String[] para = str.split(",");
					if(para.length!=2){
						System.out.println("参数错误。");
						output.println("000");
						break;
					}
					String id = para[0];
					String pwd = para[1];
					user u = new user(id,"*",pwd);
					if(login(u)){
						System.out.println(u+"\t 登录成功。");
						u = getUser(u.getId());
						li.add(u);
						li_socket.add(socket);
						List<user> f_li = getFriend(id);
						String str_f = u.getId()+","+u.getUsername()+";";
						for(user each:f_li){
							str_f = str_f + each.getId()+","+each.getUsername()+";";
						}
						List<relation> li_r = this.getFailRelation(id);
						String str_r ="";
						for(relation r:li_r){
							str_r = str_r +r.toString()+";";
						}
						List<message> li_m = this.getfailmessage(id);
						String str_m = "";
						for(message msg:li_m){
							str_m =str_m+msg.toString()+";";
						}
						str = "001:"+str_f+"&$"+str_r+"&$"+str_m;
						output.println(str);
						for(message msg:li_m){
							deletefailmessage(msg);
						}
						System.out.println("登录处理完成："+str);
					}else{
						output.println("000");
						System.out.println(id+"\t登录失败");
					}
				};break;
				case "10":{//发送聊天记录序列，查询与指定好友的历史消息
					str = str.substring(3).trim();
					String para[] = str.split(",");
					if(para.length!=2){
						System.out.println("参数错误。");
						output.println("100");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					System.out.println("10：查询与指定好友聊天记录。"+id1+","+id2);
					List<message> m_li = this.getMessage(id1, id2);
					String m_str ="";
					for(message msg:m_li){
						m_str =m_str + msg.toString()+";";
					}
					output.println("101:"+m_str);
					System.out.println("聊天记录查询成功："+m_str);
				};break;
				case "11":{//点对点发msg
					System.out.println("点对点msg.");
					str = str.substring(3).trim();
					String[] para = str.split(",");
					if(para.length!=4){
						System.out.println("参数不匹配。");
						output.println("110");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					System.out.println("para[2]:"+para[2]);
					Date date =convertToDate(para[2]);
					System.out.println("date:"+date);
					String content = para[3];
					message msg = new message(id1,id2,date,content);
					if(send(id2,"111:"+msg.toString())){
						System.out.println("转发成功："+msg.toString());
						addmessage(msg);//发送成功后把消息存入数据库
						break;
					}else{
						System.out.println("目标用户未在线。");
						this.addfailmessage(msg);//发送不成功存入离线记录表
						System.out.println("转至离线消息");
					}
				};break;
				case "12":{//群发好友msg
					System.out.println("12：群发好友信息。");
					str = str.substring(3).trim();
					String[] para = str.split(",");
					if(para.length!=4||!para[0].equals(para[1])){
						System.out.println("参数不匹配。");
						output.println("120");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					Date date = convertToDate(para[2]);
					String content = para[3];
					List<user> li_f = getFriend(id1);
					message msg = new message(id1,id2,date,content);
					for(int i=0;i<li_f.size();i++){
						//Boolean flag = false;
						id2 = li_f.get(i).getId();
						msg.setId2(id2);
						if(send(id2,"121:"+msg.toString())){
							addmessage(msg);
							System.out.println("群发消息至"+id2+"成功。");
						}else{
							addfailmessage(msg);
							System.out.println("转至离线消息:"+msg);
						}
					}
					//socket = socket_temp;
					output.println("121");
					System.out.println("群发消息处理成功。");
				};break;
				case "13":{//删除历史消息
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=2){
						System.out.println("参数不匹配。");
						output.println("130");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					if(deleteMessage(id1,id2)){
						System.out.println("历史消息清空："+id1+","+id2);
						output.println("131");
					}else{
						System.out.println("清空失败:"+id1+","+id2);
						output.println("130");
					}
				};break;
				case "20":{//获得好友列表
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=1){
						System.out.println("参数不匹配。");
						output.println("200");
						break;
					}
					String id = para[0];
					List<user> li_f = getFriend(id);
					String str_f = "";
					for(user u:li_f){
						str_f = str_f + u.toString()+";";
					}
					output.println("201:"+str_f);
					System.out.println("成功获取好友列表。");
				};break;
				case "21":{//添加好友
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=2){
						System.out.println("参数不匹配。");
						output.println("210");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					//relation r = new relation((new Integer(id1).intValue()>new Integer(id2).intValue()?id2:id1),(new Integer(id1).intValue()>new Integer(id2).intValue()?id1:id2));
					relation r = new relation(id1,id2);
					addFailRelation(r);
					System.out.println("add temp relation:"+r);
					if(send(id2,"211:"+id1)){
						System.out.println("发出添加好友通知。");
					}else{
						System.out.println("添加好友失败。");
					}
				};break;
				case "22":{//删除好友
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=2){
						System.out.println("参数不匹配。");
						output.println("220");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					if(new Integer(id1).intValue()>new Integer(id2).intValue()){
						String temp = id1;
						id1 = id2;
						id2 = temp;
					}
					relation r = new relation(id1,id2);
					if(deleteRelation(r)){
						System.out.println("删除好友关系:"+id1+","+id2+" 成功。");	
						if(send(id2,"221:"+id1)){
							System.out.println("删除好友通知完成。");
							output.println("221:"+id2);
						}else{
							System.out.println("删除好友通知失败。");
						}
					}else{
						System.out.println("删除好友关系失败。");
						output.println("220");
					}
				};break;
				case "23":{//确认添加好友
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=2){
						System.out.println("参数不匹配。");
						output.println("230");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					relation r = new relation(id1,id2);
					if(addRelation(r)){
						deleteFailRelation(r);
						System.out.println("确认添加好友关系:"+id1+","+id2+" 成功。");	
						if(send(id2,"231:"+id1+","+getUser(id1).getUsername())){
							System.out.println("添加好友通知完成。");
							output.println("231:"+id2+","+getUser(id2).getUsername());
						}else{
							System.out.println("添加好友通知失败。");
							output.println("230");
						}
					}else{
						System.out.println("确认添加好友关系失败。");
						output.println("230");
					}
				};break;
				case "24":{//拒绝好友请求
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=2){
						System.out.println("参数不匹配。");
						output.println("240");
						break;
					}
					String id1 = para[0];
					String id2 = para[1];
					relation r = new relation(id1,id2);
					if(deleteFailRelation(r)){						
						if(send(id2,"241:"+id1)){
							System.out.println("拒绝好友通知完成。");
						}else{
							System.out.println("拒绝好友通知失败。");
							output.println("240");
						}
					}else{
						System.out.println("拒绝添加好友失败。");
						output.println("240");
					}
				};break;
				case "30":{//更改用户名
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=3){
						System.out.println("参数不匹配。");
						output.println("300");
						break;
					}	
					String id = para[0];
					String name = para[1];
					String pwd = para[2];
					user u = new user(id,name,pwd);
					if(verify(u)){
						System.out.println("验证成功："+u);
						if(updateUser(u)){
							System.out.println("用户信息更新成功。");
							output.println("301:"+u.toString());
						}else{
							System.out.println("用户信息更新失败。");
							output.println("300");
						}
					}else{
						System.out.println("验证失败。");
						output.println("300");
					}
				};break;
				case "31":{//更改用户密码
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=3){
						System.out.println("参数不匹配。");
						output.println("310");
						break;
					}	
					String id = para[0];
					String name = para[1];
					String pwd = para[2];
					user u = new user(id,name,pwd);
					if(verify(u)){
						System.out.println("验证成功："+u);
						if(updatePwd(u)){
							System.out.println("用户信息更新成功。");
							u = getUser(id);
							output.println("301:"+u.toString());
						}else{
							System.out.println("用户信息更新失败。");
							output.println("300");
						}
					}else{
						System.out.println("验证失败。");
						output.println("300");
					}
				};break;
				case "40":{//注册新用户
					str = str.substring(3);
					String[] para = str.split(",");
					if(para.length!=2){
						System.out.println("参数不匹配。");
						output.println("400");
						break;
					}
					String name = para[0];
					String pwd = para[1];
					user u = new user("*",name,pwd);
					if(register(u)){
						u = getUser(name,pwd);
						System.out.println("用户注册成功。");
						output.println("401:"+u.toString());
					}else{
						System.out.println("用户注册失败");
						output.println("400");
					}
				};break;
				case "55":output.println("echo\t"+str.substring(2));break;
				default:{
					System.out.println("请求码匹配失败。");
					output.println("-1");
				};break;
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				}//synchronized
			}
		}catch(Exception e){
			try{
				socket.close();
				System.out.println("服务器出现异常。");
				e.printStackTrace();
			}catch(Exception ee){}
		}
	}
	private void checkStatus(){//需要注意删除
		for(int i=0;i<li_socket.size();i++){
			System.out.println("检查第"+i+"个连接的状态。");
			if(!li_socket.get(i).isBound()){
				li.remove(i);
				li_socket.remove(i);
				System.out.println("清除第"+i+"个连接。");
				i--;
			}
		}
		System.out.println("状态扫描完成。");
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
	private Boolean send(String id,String str){
		System.out.println("开始转发");
		Boolean flag = false;
		for(int i=0;i<li.size();i++)
		{
			if(id.equals(li.get(i).getId())){
				Socket socket = li_socket.get(i);
				PrintWriter output;
				try {
					output = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()),true);
					output.println(str);
					output.flush();
					System.out.println("转发完成");
					flag =true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					flag = false;
					System.out.println("error in send"+i+","+str);
				}				
			}
		}
		return flag;
	}
	private String receive(){
		String str ="";
		BufferedReader input =null;
		try{
			input =new BufferedReader( new InputStreamReader(socket.getInputStream()));//线程在这里等待输入流
			while(str.equals("")){
				str = input.readLine();	
			}
		}catch(Exception e){
			System.out.println("socket 接收出错。");
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}
	private List<user> getUserList(){	
		return new dao().getUserList();
	}
	private Boolean register(user u){
		user u_temp = new dao().getUser(u.getUsername(), u.getPwd());
		Boolean flag = false;
		if(u_temp==null){
			new dao().addUser(u);
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	private Boolean login(user u){	
		if(verify(u)){
			return true;
		}else{
			return false;
		}
	}
	private Boolean verify(user u){
		user du = getUser(u.getId());
		return du.getPwd().equals(u.getPwd());
	}	
	private user getUser(String id){	
		return new dao().getUser(id);
	}
	private user getUser(String name,String pwd){
		return new dao().getUser(name,pwd);
	}
	private Boolean deleteUser(String id){
		return new dao().deleteUser(id);
	}
	private Boolean updateUser(user u){		
		return new dao().updateUser(u);
	}
	private Boolean updatePwd(user u){
		return new dao().updatePwd(u);
	}
	private List<relation> getRelation(){	
		return new dao().getRelation();
	}
	private Boolean addRelation(relation r){		
		return new dao().addRelation(r);
	}
	private Boolean deleteRelation(relation r){		
		return new dao().deleteRelation(r);
	}
	private List<relation> getFailRelation(String id){
		return new dao().getFailRelation(id);
	}
	private Boolean addFailRelation(relation r){		
		return new dao().addFailRelation(r);
	}
	private Boolean deleteFailRelation(relation r){
		return new dao().deleteFailRelation(r);
	}
	private List<user> getFriend(String id){
		return new dao().getFriend(id);
	}
	private Boolean addmessage(message msg){
		return new dao().addmessage(msg);
	}
	private List<message> getfailmessage(String id){
		return new dao().getfailmessage(id);
	}
	private Boolean addfailmessage(message msg){
		return new dao().addfailmessage(msg);
	}
	private Boolean deletefailmessage(message msg){	
		return new dao().deletefailmessage(msg);
	}
	private List<message> getMessage(String id)//指定用户聊天记录
	{
		return new dao().getMessage(id);
	}
	private List<message> getMessage(String id1,String id2)//特定的两人的聊天记录
	{
		return new dao().getMessage(id1, id2);
	}
	private List<message> getMessage()//所有聊天记录
	{
		return new dao().getMessage();
	}
	private Boolean deleteMessage(String id1,String id2){
		return new dao().deleteMessage(id1, id2);
	}
	public void close()//关闭服务器;
	{
		try {
			server.close();
			System.out.println("服务器已关闭");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("服务器关闭出错。");
		}
	}
}
