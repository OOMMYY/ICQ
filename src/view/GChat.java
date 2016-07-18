package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import model.user;
import control.Client;

public class GChat extends JFrame {

	private Client client;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GChat frame = new GChat(new Client());
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
	public GChat(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		panel.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);
		
		JButton btnNewButton = new JButton("Èº·¢");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea_1.getText();
				user USER = client.getClient();
				client.sendAll(textArea.getText());
				str += client.getClient().getUsername()+":\n  "+textArea.getText()+"\n";
				textArea.setText("");
				textArea_1.setText(str);					
			}
		});
		panel.add(btnNewButton, BorderLayout.EAST);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
