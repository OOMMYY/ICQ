package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import model.user;
import control.Server;

public class ServerView extends JFrame implements Runnable{

	private Server server;
	private JPanel contentPane;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private ByteArrayOutputStream baos;
	private PrintStream old;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ServerView frame = new ServerView();
			frame.setVisible(true);
			frame.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public ServerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    // IMPORTANT: Save the old System.out!
	    old = System.out;
	    // Tell Java to use your special stream
	    System.setOut(ps);
	    // Show what happened
	    System.out.println("Here: " + baos.toString());
		
		setBounds(100, 100, 514, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("关闭服务器");
		ServerView ser = this;
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(351, 232, 137, 23);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 20, 343, 202);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(376, 20, 112, 202);
		contentPane.add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JLabel lblNewLabel = new JLabel("服务器日志：");
		lblNewLabel.setBounds(23, 0, 103, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("在线用户：");
		lblNewLabel_1.setBounds(376, 0, 112, 15);
		contentPane.add(lblNewLabel_1);
		
	}
	public void start(){
		new Thread(this).start();
		try{
			server = new Server(2002,4000);
			server.start();
		}catch(Exception ee){}
		finally{
			server.close();
			System.setOut(old);
			System.out.println("done");
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String str="";
		String str1="";
		while(true){
		    System.out.flush();
		    str = baos.toString();
			textArea.setText(str);
			str1="";
			if(server!=null){
				List<user> li = server.getLi();
				for(int i=0;i<li.size();i++){
					user u= li.get(i);
					str1 += u.getId()+" "+u.getUsername()+"\n";
				}	
			}
			textArea_1.setText(str1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("error in  ServerView sleep(500)");
			}
		}
	}
}
