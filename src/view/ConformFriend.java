package view;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.relation;
import control.Client;

public class ConformFriend extends JFrame {

	private Client client;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConformFriend frame = new ConformFriend(new Client());
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
	public ConformFriend(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 1, 0, 0));
		List<relation> li = client.getLi_relation(); 
		for(int i=0;i<li.size();i++){
			relation r = li.get(i);
			JPanel panel_1 = new JPanel();
			contentPane.add(panel_1);
			panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JLabel lblNewLabel = new JLabel(r.getId1()+"\t\t���������Ϊ����");
			panel_1.add(lblNewLabel);
			
			JButton btnNewButton = new JButton("ͬ��");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton button = (JButton) e.getSource();
					if(r.getId1()==null){
						button.setEnabled(false);
					}else{
						client.confirmRelation(r.getId1());
						Icon icon = new ImageIcon("receive.jpg");
						JOptionPane.showMessageDialog(null,"����ͬ�����"+r.getId1()+"Ϊ����","��Ӻ���", JOptionPane.OK_OPTION,icon);
						button.setText("��ͬ��");
						button.setEnabled(false);
						r.setId1(null);
					}
				}
			});
			panel_1.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("�ܾ�");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton button = (JButton) e.getSource();
					if(r.getId1()==null){
						button.setEnabled(false);
					}else{
						client.refuseRelation(r.getId1());
						Icon icon = new ImageIcon("refuse.jpg");
						JOptionPane.showMessageDialog(null,"��ܾ���"+r.getId1()+"�ĺ�������","�ܾ�����",JOptionPane.OK_OPTION,icon);	
						button.setText("�Ѿܾ�");
						button.setEnabled(false);
						r.setId1(null);
					}
				}
			});
			panel_1.add(btnNewButton_1);

		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
