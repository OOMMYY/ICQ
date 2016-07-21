package view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.Client;

public class loginview extends JFrame implements Runnable {
	private Client client;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblNewLabel_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginview frame = new loginview(new Client());
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
	public loginview(Client client) {
		this.client = client;
		Toolkit tool = getToolkit();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setBounds(79, 83, 81, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密码:");
		lblNewLabel_1.setBounds(79, 127, 81, 30);
		getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(170, 88, 165, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(170, 132, 165, 21);
		getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("登录");
		loginview lv = this;
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(lv).start();
			}
		});
		btnNewButton.setBounds(79, 188, 93, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("注册");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new registerview(client).setVisible(true);;
			}
		});
		btnNewButton_1.setBounds(182, 188, 93, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("取消");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(285, 188, 93, 23);
		getContentPane().add(btnNewButton_2);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(122, 24, 256, 33);
		getContentPane().add(lblNewLabel_2);
		setResizable(false);
	}
	public String encryp(String pwd){
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
	public void run(){
		char[] ch = passwordField.getPassword();
		String pwd = "";
		for(int i=0;i<ch.length;i++){
			pwd +=ch[i];
			ch[i]=' ';
		}
		client.login(textField.getText(),encryp(pwd));
		int time =0;
		lblNewLabel_2.setText("正在登录……");
		while(client.getClient()==null&&time<100){
			time++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//System.out.println("延时");
			}	
		}
		if(time==100){
			lblNewLabel_2.setText("登录超时，请使用正确的用户名与密码");
		}else{
			setVisible(false);
			ClientView clientview = new ClientView(client);
			clientview.setVisible(true);
			new Thread(clientview).start();
		}
	}
}
