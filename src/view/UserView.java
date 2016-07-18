package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserView extends JPanel {

	/**
	 * Create the panel.
	 */
	public UserView() {
		setLayout(null);
		//this.setBounds(100, 100, 100, 100);
		Dimension dim = this.getPreferredSize();
		double H = dim.getHeight();
		double W = dim.getWidth();
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(0, 0,(int)(W/2), (int)(H/4));
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("name");
		lblNewLabel_1.setBounds(0, (int)((1.0/4)*H),(int)(3*W/4), (int)(3*H/4));
		add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("chat");
		btnNewButton.setBounds((int)(3.0*W/4), (int)((3.0/4)*H),(int)(1.0*W/4), (int)(1.0*H/4));
		add(btnNewButton);

	}
}
