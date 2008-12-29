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
	private final String PATH = "Images/Icons/";
	// Text of the buttons on the Toolbar
	private String[] text = 
	{
		"Add User", "Delete User", "-" , "Find", "Next", "-", "Sort", "-" ,"Logout", "-" , "About", "Help"
	};
	// Icons of the buttons on the Toolbar
    private String[] icons =
	{
		"add.png", "delete.png", "-" , "find.png", "right.png", "-", "sort2.png", "-" ,  "logout.png", "-" , "about.png", "help.png"
	};
	
	private InformationPanel infoPanel;
	
	private String searchKey="";
	private int searchBy=1;

    public FLToolBar(FLClient model) {
		this.model = model;
		// Loop Over the Array of text or icons
        for(int i=0; i<text.length; i++){
			if(text[i].equals("-")) 
			{	
				addSeparator();
				continue;
			}
			// Create new Button with Icon[i]
			JButton btn = new JButton(text[i],new ImageIcon(PATH+icons[i]));
			// Remove the border of the button
			btn.setBorder(BorderFactory.createEmptyBorder());
			//btn.setVerticalTextPosition(JLabel.BOTTOM);
			//btn.setHorizontalTextPosition(JLabel.CENTER);
			btn.addActionListener(this);
			btn.setActionCommand(text[i]);
			// add it to the JToolBar
			add(btn);
		}
    }
	
	public FLToolBar(FLClient model, InformationPanel infoPanel)
	{
		this(model);
		this.infoPanel = infoPanel;
	}
	
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
		else if(action.equals("Sort")){
			Object[] o = {new JComboBox(new String[]{"By ID", "By First Name", "By Middle Name", "By Last Name", "By Phone", "By Address", "By Email", "By Icon"}), new JComboBox(new String[] {"Ascending", "Descending"})};
			JOptionPane.showMessageDialog(null, o);
			model.sort(((JComboBox)o[0]).getSelectedIndex(), ((JComboBox)o[1]).getSelectedIndex());
		}
		else if(action.equals("Find")){
			Object[] o = {new JComboBox(new String[]{"By ID", "By First Name", "By Middle Name", "By Last Name", "By Phone", "By Address", "By Email", "By Icon"}), new JTextField()};
			JOptionPane.showMessageDialog(null, o);
			searchKey = ((JTextField)o[1]).getText();
			searchBy =  ((JComboBox)o[0]).getSelectedIndex();
			if(searchKey.equals("") && (searchBy == 0 || searchBy == 7)  ) searchBy = 1;
			boolean s = model.search(searchKey, searchBy, infoPanel.getSelectedIndex());
			if(!s) JOptionPane.showMessageDialog(null,"Search Didn't Find Any Results! ","Nothing Found!", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(action.equals("Next")){
			if(searchKey.equals("") && (searchBy == 0 || searchBy == 7)  ) return;
			boolean s = model.search(searchKey, searchBy, infoPanel.getSelectedIndex());
			if(!s) JOptionPane.showMessageDialog(null,"Search Didn't Find Any Results! ","Nothing Found!", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	
}
