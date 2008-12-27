import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FaceLockLoginPanel extends JPanel implements ActionListener {
    private JTextField usernameTF = new JTextField("Username",15);
	private JPasswordField passwordTF = new JPasswordField("Password",15);
	private JButton loginBtn = new JButton("Login");
	private JButton registerBtn = new JButton("Register");
	private JLabel usernameLbl = new JLabel("Username", JLabel.TRAILING);
	private JLabel passwordLbl = new JLabel("Password", JLabel.TRAILING);
	FLClient model;
	// {{{ FaceLockLoginPanel constructor
    /**
     * 
     */
    public FaceLockLoginPanel(FLClient model) {
		this.model = model;
        
		JPanel usernamePanel = new JPanel(new FlowLayout());
		usernamePanel.add(usernameLbl);
		usernamePanel.add(usernameTF);
		JPanel passwordPanel = new JPanel(new FlowLayout());
		passwordPanel.add(passwordLbl);
		passwordPanel.add(passwordTF);
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		//loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsPanel.add(loginBtn);
		buttonsPanel.add(registerBtn);
		
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0,150)));
		add(usernamePanel);
		add(passwordPanel);
		add(buttonsPanel);
		add(Box.createRigidArea(new Dimension(0,150)));		
		
		
		
		/*Insets insets = this.getInsets();
				Dimension size = usernameLbl.getPreferredSize();
				usernameLbl.setBounds((int)((insets.left + insets.right)/2), (int)((insets.top + insets.bottom)/2),
				             size.width, size.height);
				*/
		
		loginBtn.addActionListener(this);
		registerBtn.addActionListener(this);
    }
	// }}}
	
	/**
	 * actionPerformed
	 *
	 * @param e 
	 * @return 
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals("Login")){
			String username = usernameTF.getText().trim();
			String password = passwordTF.getText().trim();
			boolean loggedIn = model.login(username,password);
			if(!loggedIn)
				JOptionPane.showMessageDialog(this,"Logged In Faild! Make Sure you enter your username and password correctly!", "Logged In Faild!", JOptionPane.ERROR_MESSAGE);
			
		}
		else if(action.equals("Register")){
			String username = usernameTF.getText().trim();
			String password = passwordTF.getText().trim();
			Object[] o = {"Please Confirm your Password:\n", new JPasswordField()};
			JOptionPane.showMessageDialog(null, o);
			String repassword = (((JPasswordField)o[1]).getText());
			if(repassword.equals(password))
			{	
				int status = model.register(username,password,repassword);
				if(status == 0) 
					JOptionPane.showMessageDialog(this,"The user (" + username + ") have been successfully registered!", "Registration Complete!", JOptionPane.INFORMATION_MESSAGE);
				else if (status == -2)
					JOptionPane.showMessageDialog(this,"Registration Faild! The Username is already taken, please use another one!", "Registration Faild!", JOptionPane.ERROR_MESSAGE);
				else if (status == -3)
					JOptionPane.showMessageDialog(this,"Registration Faild! Some Errors prevented the user from being registered! Please retry again later!", "Registration Faild!", JOptionPane.ERROR_MESSAGE);
			}
			else	
				JOptionPane.showMessageDialog(this,"The tow password do not match, please retry again!", "Passwords Mismatch!", JOptionPane.ERROR_MESSAGE);
		}
	}

	

	
}
