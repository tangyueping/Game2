package typ;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginFrame implements ActionListener {
	private JTextField password;
	private JTextField user;
	private JFrame frame;
	private JButton b1, b2, b3;

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == b1) {
			if (user.getText().equals("123")&&password.getText().equals("123")) {
				Client c = new Client();
				c.lauchFrame();
				c.setFocusable(true);
			}
		}
		if (evt.getSource() == b2) {
		}
		if (evt.getSource() == b3) {
		}
	}

	public static void main(String args[]) {
		try {
			LoginFrame rf = new LoginFrame();
			rf.launchFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoginFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void launchFrame() {
		final JLabel label = new JLabel();
		label.setText("用户:");
		label.setBounds(10, 10, 165, 15);
		frame.getContentPane().add(label);

		user = new JTextField();
		user.setBounds(100, 7, 150, 21);
		frame.getContentPane().add(user);

		final JLabel labelp = new JLabel();
		labelp.setText("密码:");
		labelp.setBounds(10, 34, 73, 15);
		frame.getContentPane().add(labelp);

		password = new JTextField();
		password.setBounds(100, 31, 150, 21);
		frame.getContentPane().add(password);

		b1 = new JButton();
		b1.setText("提交");
		b1.setBounds(150, 60, 99, 23);
		frame.getContentPane().add(b1);

		b1.addActionListener(this);
		frame.setVisible(true);
	}
}
