package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import model.message;
import model.user;
import control.Client;

public class ClientView extends JFrame implements Runnable{

	private Client client;
	private List<Chat> li_chat=new LinkedList<Chat>();
	private JPanel contentPane;
	private Object lock = new Object();
	private List<JPanel> li_panel_1=new LinkedList<JPanel>();
	private List<JButton> li_btnNewButton=new LinkedList<JButton>();
	private List<JLabel> li_JlblNewLabel_2=new LinkedList<JLabel>();
	private List<user> li_u=new LinkedList<user>();
	private JButton button_2;
	private JPanel panel;
	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientView frame = new ClientView(new Client());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientView(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//System.out.println(client.getClient());
		JLabel lblNewLabel = new JLabel(client.getClient().toString());
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.SOUTH);
		
		JButton button = new JButton("\u7FA4\u53D1\u6D88\u606F");
		button.addActionListener(new ActionListener() {//群发消息
			public void actionPerformed(ActionEvent e) {
				new GChat(client).setVisible(true);
			}
		});
		toolBar.add(button);
		
		JButton button_1 = new JButton("\u6DFB\u52A0\u597D\u53CB");
		button_1.addActionListener(new ActionListener() {//添加好友
			public void actionPerformed(ActionEvent e) {
				new addfriend(client).setVisible(true);
			}
		});
		toolBar.add(button_1);
		
		button_2 = new JButton("\u597D\u53CB\u8BF7\u6C42");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//处理好友请求
				new ConformFriend(client).setVisible(true);
			}
		});
		toolBar.add(button_2);
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		 	
		JLabel lblNewLabel_1 = new JLabel("好友列表：");
		scrollPane.setColumnHeaderView(lblNewLabel_1);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		
		li_u = client.getLi_friend();
		panel.setLayout(new GridLayout(li_u.size()+10, 1, 0, 0));
		for(int i=0;i<li_u.size();i++){
			li_chat.add(null);
		}
		for(int i=0;i<li_u.size();i++){
			user u= li_u.get(i);
			JPanel panel_1 = new JPanel();
			li_panel_1.add(panel_1);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			JButton btnNewButton = new JButton("chat");
			li_btnNewButton.add(btnNewButton);
			btnNewButton.setName(""+i);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					synchronized(lock){
						JButton button = (JButton) e.getSource();
						int num = new Integer(button.getName()).intValue();
						Chat chat = li_chat.get(num);
						if(true){
							chat=new Chat(client,li_u.get(num));
							li_chat.set(num, chat);
							chat.setVisible(true);
							chat.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							li_btnNewButton.get(num).setBackground(new Color(100,100,100));	
						}
					}
				}
			});
			
			panel_1.add(btnNewButton, BorderLayout.EAST);
			
			JLabel JlblNewLabel_2 = new JLabel(u.getId()+" "+u.getUsername());
			li_JlblNewLabel_2.add(JlblNewLabel_2);
			panel_1.add(JlblNewLabel_2, BorderLayout.CENTER);
		}		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			List<user> li_curuser=new LinkedList<user>();
			client.getFriend();//先用一个新的好友链替代原有链，再等待接收数据
			while(li_curuser.size()==0){
				li_curuser=client.getLi_friend();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//System.out.println("error in getli_friend");
				}
			}
			//System.out.println();
			//System.out.print("li_u:");
			for(int i=0;i<li_u.size();i++){
				//System.out.print(li_u.get(i)+";");
			}
			//System.out.println();
			//System.out.print("li_curuser:");
			for(int i=0;i<li_curuser.size();i++){
				user user_cur = li_curuser.get(i);
				Boolean flag =false;//包含为真，不包含为假
				for(int j=0;j<li_u.size();j++){
					if(li_u.get(j).getId().equals(user_cur.getId())){
						flag=true;
						break;
					}
				}
				if(!flag){//不包含则添加
					int index = li_u.size();
					li_u.add(user_cur);
					li_chat.add(null);
					JPanel panel_1 = new JPanel();
					li_panel_1.add(panel_1);
					panel.add(panel_1);
					panel_1.setLayout(new BorderLayout(0, 0));
					JButton btnNewButton = new JButton("chat");
					li_btnNewButton.add(btnNewButton);
					btnNewButton.setName(""+index);
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							synchronized(lock){
								JButton button = (JButton) e.getSource();
								int num = new Integer(button.getName()).intValue();
								Chat chat = li_chat.get(num);
								if(chat==null||true){
									chat=new Chat(client,li_u.get(num));
									li_chat.set(num,chat);
									chat.setVisible(true);
									chat.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
									li_btnNewButton.get(num).setBackground(new Color(100,100,100));	
								}
							}
						}
					});					
					panel_1.add(btnNewButton, BorderLayout.EAST);	
					JLabel JlblNewLabel_2 = new JLabel(user_cur.getId()+" "+user_cur.getUsername());
					li_JlblNewLabel_2.add(JlblNewLabel_2);
					panel_1.add(JlblNewLabel_2, BorderLayout.CENTER);
					//System.out.println("add"+i);
				}
			}
			revalidate();
			repaint();
			for(int i=0;i<li_u.size();i++){
				user user_cur = li_u.get(i);
				Boolean flag =false;
				for(int j=0;j<li_curuser.size();j++){
					if(li_curuser.get(j).getId().equals(user_cur.getId())){
						flag=true;
						break;
					}
				}
				if(!flag){
					//System.out.println("remove"+i);
					li_curuser.remove(i);
					li_u.remove(i);
					li_chat.remove(i);
					li_panel_1.remove(i);
					i--;
				}
			}
			revalidate();
			repaint();
			List<message> li_m = client.getLi_offlineMsg();		
			Set<String> set_m = new HashSet<String>();
			for(message msg:li_m){
				set_m.add(msg.getId1());
			}
			for(int i=0;i<li_u.size();i++){
				if(set_m.contains(li_u.get(i).getId())){
					li_btnNewButton.get(i).setBackground(new Color(255,100,100));	
				}else{
					li_btnNewButton.get(i).setBackground(new Color(0x6495ED));	
				}
				
			}
			if(client.getLi_relation().size()>0){
				button_2.setEnabled(true);
			}else{
				button_2.setEnabled(false);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//System.out.println("error in run sleep(500)");
			}	
		}

	}

}
