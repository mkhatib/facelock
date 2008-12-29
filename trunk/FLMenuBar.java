import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FLMenuBar extends JMenuBar implements ActionListener {
    
	private String[] text = 
	{
		"Add User", "Delete User", "-" , "Find", "-", "Settings", "-" ,"Logout", "-" , "About", "Help"
	};
	// Icons of the buttons on the Toolbar
    private String[] icons =
	{
		"add.png", "delete.png", "-" , "find.png", "-", "settings.png", "-" ,  "logout.png", "-" , "about.png", "help.png"
	};
	
	private FLClient model;
	// {{{ FLMenuBar constructor
    /**
     * 
     */
    public FLMenuBar(FLClient model) {
        super();
		this.model = model;
		JMenu file = new JMenu("File");
		JMenuItem preferences = new JMenuItem("Preferences");
		JMenuItem exit = new JMenuItem("Exit");
		preferences.addActionListener(this);
		exit.addActionListener(this);
		file.add(preferences);
		file.add(exit);
		JMenu more = new JMenu("More");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem about = new JMenuItem("About");
		help.addActionListener(this);
		about.addActionListener(this);
		more.add(help);
		more.add(about);
		add(file);
		add(more);
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
		if(action.equals("Preferences")){
			Object[][] o = {
				{"Host Server IP ", new JTextField(model.getHostServer())},
				{"Host Port #    ", new JTextField("" + model.getHostPort())}
			};
			JOptionPane.showMessageDialog(null,o);
			model.setHostServer(((JTextField)o[0][1]).getText());
			model.setHostPort(Integer.parseInt(((JTextField)o[1][1]).getText()));
		}
		else if(action.equals("Exit")){
			System.exit(0);
		}
		else if(action.equals("Help")){
			JOptionPane.showMessageDialog(null,"If You need any help, please call 911! :P", "Call 911!", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(action.equals("About")){
			String[] names = {"Noura Yousef Salhi - 105.0046", "Dima Samarah - 105.00", "Mohammad N. Khatib - 105.1104"};
			ImageIcon icon = new ImageIcon("Images/Icons/ok.png");
			AboutDialog about = new AboutDialog(null,true,names,icon);
			//JOptionPane.showMessageDialog(null,"This Program was Developed By:\n\t- Noura Yousef Salhi 105.0046\n\t- Dima Samarah 105.00\n\t- Mohammad N. Khatib 105.1104", "About!", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	
}
