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
			//System.out.println("�����ͻ��˳����쳣��");
		}
		//System.out.println("�����ͻ��ˡ���");
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
	public void run(){//���ڼ���������
		String str = "";
		//System.out.println("��ʼ��������������");
		try{
			while(true){
				Thread.sleep(1000);
				str = input.readLine();
				//System.out.println("���յ���"+str);
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
						case "001"://System.out.println("��¼�ɹ�");
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
							//System.out.println("001������ɡ�");
						}break;         
						case "101"://System.out.println("��ѯ��ָ�����ѵ���ʷ��Ϣ�ɹ�");
						{
							String[] m_str = str.split(";");
							for(int i=0;i<m_str.length;i++){
								String[] para = m_str[i].split(",");
								li_historyMsg.add(new message(para[0],para[1],convertToDate(para[2]),para[3]));
							}
						}break;
						case "111"://System.out.println("��Ե����msg");
						{
							//System.out.println("111:"+str);
							String para[] = str.split(",");
							if(para.length==4){
								message msg = new message(para[0],para[1],convertToDate(para[2]),para[3]);
								li_offlineMsg.add(msg);	
								//System.out.println("��Ϣ���м�����Ϣ��"+msg.toString());
							}
						}break;
						case "121"://System.out.println("Ⱥ������msg�ɹ�");
						{
							//System.out.println("121:"+str);
							String para[] = str.split(",");
							if(para.length==4){
								message msg = new message(para[0],para[1],convertToDate(para[2]),para[3]);
								li_offlineMsg.add(msg);	
								//System.out.println("��Ϣ���м�����Ϣ��"+msg.toString());	
							}
						}break;
						case "131"://System.out.println("ɾ����ʷ��Ϣ�ɹ�");
							break;
						case "201"://System.out.println("��ú����б�ɹ�");
						{
							String[] u_str = str.split(";");
							for(int i=0;i<u_str.length;i++){
								String[] para = u_str[i].split(",");
								if(para.length==2){
									li_friend.add(new user(para[0],para[1]));	
								}
							}
						}break;
						case "211"://System.out.println("��Ӻ��ѳɹ�");
						{
							String para[] =str.split(",");
							if(para.length==1){
								li_relation.add(new relation(para[0],client.getId()));
								//System.out.println("�յ�"+para[0]+"�ĺ�������");
							}
						}break;
						case "221"://System.out.println("ɾ�����ѳɹ�");
							break;
						case "231"://System.out.println("ȷ����Ӻ��ѳɹ�");
						{
							String para[] = str.split(",");
							if(para.length==2){
								//li_friend.add(new user(para[0],para[1]));
							}
						}break;
						case "241"://System.out.println("�ܾ���Ӻ���");
						{
							
						}break;
						case "301"://System.out.println("�����û���Ϣ�ɹ�");
							break;
						case "401"://System.out.println("ע�����û��ɹ�");
						{
							String para[] = str.split(",");
							if(para.length==3){
								client = new user(para[0],para[1],para[2]);
							}
						}break;
						//ʧ��
						case "000"://System.out.println("��¼ʧ��");
							break;
						case "100"://System.out.println("��ѯ��ָ�����ѵ���ʷ��Ϣʧ��");
							break;
						case "110"://System.out.println("��Ե㷢msgʧ��");
							break;
						case "120"://System.out.println("Ⱥ������msgʧ��");
							break;
						case "130"://System.out.println("ɾ����ʷ��Ϣʧ��");
							break;
						case "200"://System.out.println("��ú����б�ʧ��");
							break;
						case "210"://System.out.println("��Ӻ���ʧ��");
							break;
						case "220"://System.out.println("ɾ������ʧ��");
							break;
						case "230"://System.out.println("ȷ����Ӻ���ʧ��");
							break;
						case "300"://System.out.println("�����û���Ϣʧ��");
							break;
						case "400"://System.out.println("ע�����û�ʧ��");
						{
							client = new user("-1","-1");
						}break;
						default: //System.out.print("����δʶ��ķ�����:"+old_str);
							break;
					}
				}catch(Exception e){
					//System.out.println("�������������ؽ������");
					e.printStackTrace();
				}
			}			
		}catch(Exception e){}
	} 
	private static Date convertToDate(String str){//���ڽ�sql.dateת��Ϊutil.date
		String[] li = str.split(" ");
		if(li.length!=2){
			//System.out.println("converToDate �����ո����");
			return new Date();
		}
		String[] para = li[0].split("-");
		String[] para1 = li[1].split(":");
		return new GregorianCalendar(new Integer(para[0]).intValue(),new Integer(para[1]).intValue(),new Integer(para[2]).intValue(),new Integer(para1[0]).intValue(),new Integer(para1[1]).intValue(),new Integer(para[2]).intValue()).getTime();		
	}
	public Boolean login(String id,String pwd){//00:��¼   
		String str = "00:"+id+","+pwd;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean getMessage(String id)//10:��ѯ��ָ�����ѵ���ʷ��Ϣ
	{
		String str = "10:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean send(String id,String content)//11:��Ե㷢msg
	{
		String str = "11:"+client.getId()+","+id+","+new Date().toLocaleString()+","+content;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean sendAll(String content)//12:Ⱥ������msg
	{
		String str = "12:"+client.getId()+","+client.getId()+","+new Date().toLocaleString()+","+content;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean deleteMessage(String id)//13:ɾ����ʷ��Ϣ
	{
		String str = "13:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean getFriend()//20:��ú����б�
	{
		li_friend=new LinkedList<user>();//��ȡ�����б��б����뷢���󣬺����б���ա�
		String str = "20:"+client.getId();
		output.println(str);
		output.flush();
		return true;
	}
	public Boolean addRelation(String id)//21:��Ӻ���
	{
		String str = "21:"+client.getId()+","+id;
		output.println(str);
		output.flush();
		return true;		
	}
	public Boolean deleteRelation(String id)//22:ɾ������
	{
		String str = "22:"+new relation(client.getId(),id).toString();
		output.println(str);
		return true;
	}
	public Boolean confirmRelation(String id)//23:ȷ����Ӻ���
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
	public Boolean refuseRelation(String id)//24:�ܾ���������
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
	public Boolean updateInfo(String name,String pwd)//30:�����û���
	{
		String str = "30:"+new user(client.getId(),name,pwd).toString();
		output.println(str);
		return true;
	}
	public Boolean updatePwd(String newpwd,String oldpwd)//31:�����û�����
	{
		String str = "30:"+new user(client.getId(),newpwd,oldpwd).toString();
		output.println(str);
		return true;
	}
	public Boolean register(String name,String pwd)//40:ע�����û�
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
			//System.out.println("������Ϣ��ʧ");
		}
		try{
			input.close();
			output.close();				
			socket.close();
		}catch(Exception e){
			//System.out.println("�ͻ����˳�ʱ�����쳣��");
		}
		//System.out.println("�ͻ������˳���");		
	}
}