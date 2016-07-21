package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.user;
import control.Client;

public class addfriend extends JFrame {

	private Client client;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addfriend frame = new addfriend(new Client());
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
	public addfriend(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 361, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(87, 53, 145, 40);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(87, 116, 254, 38);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("发送");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = ""+textField.getText();
				if(client.getClient().getId().equals(id)||id.equals("")){
					if(id.equals("")){
						lblNewLabel_1.setText("请输入对方账号");
					}else{
						lblNewLabel_1.setText("请不要输入自己的账号");
						textField.setText("");	
					}
				}else{
					List<user> li = client.getLi_friend();
					Boolean flag =true;
					for(user u:li){
						if(u.getId().equals(id)){
							flag =false;
							break;
						}
					}
					if(flag){
						//System.out.println("addfriend("+id+")");
						client.addRelation(id);
						lblNewLabel_1.setText("已给"+id+"发送好友请求,等待对方确认");	
						textField.setText("");
					}else{
						lblNewLabel_1.setText(id+"已经是你的好友了");
						textField.setText("");
					}	
				}
			}
		});
		btnNewButton.setBounds(242, 53, 93, 40);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("对方账号：");
		lblNewLabel.setBounds(10, 52, 80, 40);
		contentPane.add(lblNewLabel);
		

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
