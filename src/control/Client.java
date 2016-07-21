package control;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import model.message;
import model.relation;
import model.user;
public class Client implements Runnable{
		Socket socket;
		user client;
		List<user> li_friend = new LinkedList<user>();
		List<relation> li_relation = new LinkedList<relation>();
		List<message> li_offlineMsg = new LinkedList<message>();
		List<message> li_historyMsg = new LinkedList<message>();
		BufferedReader input ;
		PrintWriter output;
		Logger Log = Logger.getGlobal();
	public Client(){
		try{
			socket = new Socket("127.0.0.1", 2002);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		}catch(Exception e){
			try{
				input.close();
				output.close();				
				socket.close();
			}catch(Exception ee){}
			//System.out.println("创建客户端出现异常。");
		}
		//System.out.println("创建客户端……");
	}
	public void start(){
		new Thread(this).start();
		Scanner sc = new Scanner(System.in);
		String cmd="";
		while(true){
			//System.out.print(">>>");
			cmd = sc.nextLine();
			switch(cmd){
				case "quit":System.exit(0);break;
			}
			send(cmd);
		}
	}
	private void send(String str){
		output.println(str);
		output.flush();
	}
	public void run(){//用于监听服务器
		String str = "";
		//System.out.println("开始监听服务器……");
		try{
			while(true){
				Thread.sleep(1000);
				str = input.readLine();
				//System.out.println("接收到："+str);
				if(str.length()>0){
					//System.out.print(">>>");
				}
				try{
					String cmd = str.substring(0,3);
					String old_str = str;
					if(str.length()>4){
						str = str.substring(4);	
					}
					switch(cmd){
						case "001"://System.out.println("登录成功");
						{
							String[] li = str.split("&");
							String[] u_str = li[0].split(";");
							String[] para = u_str[0].split(",");
							client = new user(para[0],para[1]);
							for(int i=1;i<u_str.length;i++){
								para = u_str[i].split(",");
								if(para.length==2){
									li_friend.add(new user(para[0],para[1]));	
								}
							}
							if(li[1]!=null&&li[1].length()>1){
								li[1] = li[1].substring(1);
								String[] r_str = li[1].split(";");
								for(int i=0;i<r_str.length;i++){
									para = r_str[i].split(",");
									li_relation.add(new relation(para[0],para[1]));
								}
							}
							if(li[2]!=null&&li[2].length()>1){
								li[2]=li[2].substring(1);
								String[] m_str = li[2].split(";");
								for(int i = 0;i<m_str.length;i++){
									para = m_str[i].split(",");
									li_offlineMsg.add(new message(para[0],para[1],convertToDate(para[2]),para[3]));//convertToDate(str);
								}	
							}							
							for(user u:li_friend){
								//System.out.println(u);
							}
							//System.out.println("001解析完成。");
						}break;         
						case "101"://System.out.println("查询与指定好友的历史消息成功");
						{
							String[] m_str = str.split(";");
							for(int i=0;i<m_str.length;i++){
								String[] para = m_str[i].split(",");
								li_historyMsg.add(new message(para[0],para[1],convertToDate(para[2]),para[3]));
							}
						}break;
						case "111"://System.out.println("点对点接收msg");
						{
							//System.out.println("111:"+str);
							String para[] = str.split(",");
							if(para.length==4){
								message msg = new message(para[0],para[1],convertToDate(para[2]),para[3]);
								li_offlineMsg.add(msg);	
								//System.out.println("消息链中加入消息："+msg.toString());
							}
						}break;
						case "121"://System.out.println("群发好友msg成功");
						{
							//System.out.println("121:"+str);
							String para[] = str.split(",");
							if(para.length==4){
								message msg = new message(para[0],para[1],convertToDate(para[2]),para[3]);
								li_offlineMsg.add(msg);	
								//System.out.println("消息链中加入消息："+msg.toString());	
							}
						}break;
						case "131"://System.out.println("删除历史消息成功");
							break;
						case "201"://System.out.println("获得好友列表成功");
						{
							String[] u_str = str.split(";");
							for(int i=0;i<u_str.length;i++){
								String[] para = u_str[i].split(",");
								if(para.length==2){
									li_friend.add(new user(para[0],para[1]));	
								}
							}
						}break;
						case "211"://System.out.println("添加好友成功");
						{
							String para[] =str.split(",");
							if(para.length==1){
								li_relation.add(new relation(para[0],client.getId()));
								//System.out.println("收到"+para[0]+"的好友请求。");
							}
						}break;
						case "221"://System.out.println("删除好友成功");
							break;
						case "231"://System.out.println("确认添加好友成功");
						{
							String para[] = str.split(",");
							if(para.length==2){
								//li_friend.add(new user(para[0],para[1]));
							}
						}break;
						case "241"://System.out.println("拒绝添加好友");
						{
							
						}break;
						case "301"://System.out.println("更新用户信息成功");
							break;
						case "401"://System.out.println("注册新用户成功");
						{
							String para[] = str.split(",");
							if(para.length==3){
								client = new user(para[0],para[1],para[2]);
							}
						}break;
						//失败
						case "000"://System.out.println("登录失败");
							break;
						case "100"://System.out.println("查询与指定好友的历史消息失败");
							break;
						case "110"://System.out.println("点对点发msg失败");
							break;
						case "120"://System.out.println("群发好友msg失败");
							break;
						case "130"://System.out.println("删除历史消息失败");
							break;
						case "200"://System.out.println("获得好友列表失败");
							break;
						case "210"://System.out.println("添加好友失败");
							break;
						case "220"://System.out.println("删除好友失败");
							break;
						case "230"://System.out.println("确认添加好友失败");
							break;
						case "300"://System.out.println("更新用户信息失败");
							break;
						case "400"://System.out.println("注册新用户失败");
						{
							client = new user("-1","-1");
						}break;
						default: //System.out.print("遇到未识别的返回码:"+old_str);
							break;
					}
				}catch(Exception e){
					//System.out.println("解析服务器返回结果出错。");
					e.printStackTrace();
				}
			}			
		}catch(Exception e){}
	} 
	private static Date convertToDate(String str){//用于将sql.date转化为util.date
		String[] li = str.split(" ");
		if(li.length!=2){
			//System.out.println("converToDate 出错，空格错误");
			return new Date();
		}
		String[] para = li[0].split("-");
		String[] para1 = li[1].split(":");
		return new GregorianCalendar(new Integer(para[0]).intValue(),new Integer(para[1]).intValue(),new Integer(para[2]).intValue(),new Integer(para1[0]).intValue(),new Integer(para1[1]).intValue(),new Integer(para[2]).intValue()).getTime();		
	}
	public Boolean login(String id,String pwd){//00:登录   
		String str = "00:"+id+","+pwd;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean getMessage(String id)//10:查询与指定好友的历史消息
	{
		String str = "10:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean send(String id,String content)//11:点对点发msg
	{
		String str = "11:"+client.getId()+","+id+","+new Date().toLocaleString()+","+content;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean sendAll(String content)//12:群发好友msg
	{
		String str = "12:"+client.getId()+","+client.getId()+","+new Date().toLocaleString()+","+content;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean deleteMessage(String id)//13:删除历史消息
	{
		String str = "13:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean getFriend()//20:获得好友列表
	{
		li_friend=new LinkedList<user>();//获取好友列表列表申请发出后，好友列表清空。
		String str = "20:"+client.getId();
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean addRelation(String id)//21:添加好友
	{
		String str = "21:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		return true;		
	}
	public Boolean deleteRelation(String id)//22:删除好友
	{
		String str = "22:"+new relation(client.getId(),id).toString();
		output.println(str);
		return true;
	}
	public Boolean confirmRelation(String id)//23:确认添加好友
	{
		String str = "23:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		for(relation r:li_relation){
			if(r.getId1().equals(id)||r.getId2().equals(id)){
				li_relation.remove(r);
			}
		}
		return true;
	}
	public Boolean refuseRelation(String id)//24:拒绝好友请求
	{
		String str = "24:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		for(relation r:li_relation){
			if(r.getId1().equals(id)||r.getId2().equals(id)){
				li_relation.remove(r);
			}
		}
		return true;
	}
	public Boolean updateInfo(String name,String pwd)//30:更改用户名
	{
		String str = "30:"+new user(client.getId(),name,pwd).toString();
		output.println(str);
		return true;
	}
	public Boolean updatePwd(String newpwd,String oldpwd)//31:更改用户密码
	{
		String str = "30:"+new user(client.getId(),newpwd,oldpwd).toString();
		output.println(str);
		return true;
	}
	public Boolean register(String name,String pwd)//40:注册新用户
	{
		String str = "40:"+new user(name,pwd).toString();
		output.println(str);	
		return true;		
	}

	public List<user> getLi_friend() {
		return li_friend;
	}
	public void setLi_friend(List<user> li_friend) {
		this.li_friend = li_friend;
	}
	public List<relation> getLi_relation() {
		return li_relation;
	}
	public void setLi_relation(List<relation> li_relation) {
		this.li_relation = li_relation;
	}
	public List<message> getLi_offlineMsg() {
		return li_offlineMsg;
	}
	public void setLi_offlineMsg(List<message> li_offlineMsg) {
		this.li_offlineMsg = li_offlineMsg;
	}
	public List<message> getLi_historyMsg() {
		return li_historyMsg;
	}
	public void setLi_historyMsg(List<message> li_historyMsg) {
		this.li_historyMsg = li_historyMsg;
	}
	
	public user getClient() {
		return client;
	}
	public void logout(){
		client=null;
	}
	public void close(){
		try {
			PrintWriter p = new PrintWriter("off_msg.txt");
			for(message msg:li_offlineMsg){
				p.println(msg.toString());
			}
			p.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			//System.out.println("离线消息丢失");
		}
		try{
			input.close();
			output.close();				
			socket.close();
		}catch(Exception e){
			//System.out.println("客户端退出时出现异常。");
		}
		//System.out.println("客户端已退出。");		
	}
}