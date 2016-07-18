package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.user;
import control.Client;

public class registerview extends JDialog implements Runnable{

	private Client client;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			registerview dialog = new registerview(new Client());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public registerview(Client client){
		this.client = client;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");

		label.setBounds(118, 39, 73, 28);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(128, 77, 54, 26);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u91CD\u65B0\u8F93\u5165\u5BC6\u7801\uFF1A");
		label_2.setBounds(97, 113, 116, 31);
		contentPanel.add(label_2);
		
		textField = new JTextField();
		textField.setBounds(190, 39, 150, 28);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(190, 77, 150, 26);
		contentPanel.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(191, 116, 150, 25);
		
		registerview review = this;
		
		contentPanel.add(passwordField_1);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(145, 174, 183, 28);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						char [] pwd = passwordField.getPassword();
						char [] pwd1 = passwordField_1.getPassword();
						Boolean flag = true;
						if(pwd.length!=pwd1.length){
							flag =false;
						}
						String str = "";
						for(int i=0;flag&&i<pwd.length;i++){
							if(pwd[i]!=pwd1[i]){
								flag =false;
							}
							str +=pwd[i];
							pwd[i]=' ';
							pwd1[i]=' ';
						}
						if(flag){
							client.register(textField.getText(), str);
							new Thread(review).start(); 
						}else{
							JOptionPane.showMessageDialog(null, "两次密码不一致", "请重新输入", JOptionPane.ERROR_MESSAGE);
						}					
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setResizable(false);
	}
	public void run(){
		user u = client.getClient();
		lblNewLabel.setText("系统正在处理……");
		while(u==null){
			u=client.getClient();
			System.out.println(u);
		}
		Icon icon =new ImageIcon("1.png");
		if(u.getUsername().equals("-1")){
			JOptionPane.showConfirmDialog(null, "该用户名已被占用"+":\n请重新输入用户名", "注册失败", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE,icon);
		}else{
			JOptionPane.showConfirmDialog(null, u.getUsername()+":\n你的账号是："+u.getId(), "注册成功", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE,icon);
		}
		textField.setText("");
		passwordField.setText("");
		passwordField_1.setText("");
		client.logout();
		lblNewLabel.setText("系统处理成功");
	}
}
