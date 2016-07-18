package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import model.message;
import model.user;
import control.Client;
import javax.swing.JScrollPane;

public class Chat extends JFrame implements Runnable{

	private Client client;
	private user USER;
	private String msg="";
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat(new Client(),new user("120","Yozane"));
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
	public Chat(Client client,user u) {
		this.client = client;
		this.USER = u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 197, 424, 59);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		panel.add(textArea);
		
		JButton btnNewButton = new JButton("·¢ËÍ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textArea.getText().equals("")){
					client.send(USER.getId(),textArea.getText());
					msg += client.getClient().getUsername()+":\n  "+textArea.getText()+"\n";
					textArea.setText("");
					textArea_1.setText(msg);	
				}
			}
		});
		panel.add(btnNewButton, BorderLayout.EAST);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(5, 20, 424, 177);
		contentPane.add(scrollPane_1);

		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		textArea_1.setEditable(false);
		textArea_1.setBackground(new Color(0xB9D3EE));
		
		JLabel lblNewLabel = new JLabel(USER.getUsername());
		lblNewLabel.setBounds(5, 5, 424, 15);
		contentPane.add(lblNewLabel);
		Thread p = new Thread(this);
		p.start();
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        p.stop();
		    }
		});
		setResizable(false);
	}
	public String getMsg() {
		return msg;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			List<message> li = client.getLi_offlineMsg();
			for(message m:li){
				if(m.getId1().equals(USER.getId())){
					msg = msg+ USER.getUsername()+":"+m.getDate().toLocaleString()+"\n  "+m.getContent()+"\n";
					textArea_1.setText(msg);
					li.remove(m);
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
