import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * Class that extends JToolBar and add some buttons to it
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public  class FLToolBar extends JToolBar implements ActionListener{
    
	private FLClient model;
	// Path where the icons are
	private final String PATH = "Images/Icons/48x48/";
	// Text of the buttons on the Toolbar
	private String[] text = 
	{
		"Add User", "Delete User", "Information", "Settings", "Refresh", "Find"	,"Logout"
	};
	// Icons of the buttons on the Toolbar
    private String[] icons =
	{
		"add.png", "delete.png", "info.png", "settings.png", "refresh.png", "find.png", "logout.png"
	};
	
	private InformationPanel infoPanel;
	// {{{ FLToolBar constructor
    /**
     * 
     */
    public FLToolBar(FLClient model) {
		this.model = model;
		// Loop Over the Array of text or icons
        for(int i=0; i<text.length; i++){
			// Create new Button with Icon[i]
			JButton btn = new JButton(/*text[i],*/new ImageIcon(PATH+icons[i]));
			// Remove the border of the button
			btn.setBorder(BorderFactory.createEmptyBorder());
			btn.addActionListener(this);
			btn.setActionCommand(text[i]);
			// add it to the JToolBar
			add(btn);
		}
    }
	// }}}
	public FLToolBar(FLClient model, InformationPanel infoPanel)
	{
		this(model);
		this.infoPanel = infoPanel;
	}
	/**
	 * actionPerformed
	 *
	 * @param e 
	 * @return 
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals("Logout")){
			boolean loggedOut = model.logout();
			if(!loggedOut) 
				JOptionPane.showMessageDialog(null, "Faild to Logged Out!", "Logged Out Faild!" , JOptionPane.ERROR_MESSAGE);
		}
		else if(action.equals("Add User")){
			infoPanel.newContact();
		}
		else if(action.equals("Delete User")){
			if(infoPanel.getContact() == null) return;
			boolean deleted = model.deleteContact(infoPanel.getContact().getID());
			if(!deleted) JOptionPane.showMessageDialog(null, "Faild to Delete!", "Faild To Delete!" , JOptionPane.ERROR_MESSAGE);
		}
	}

	
}
