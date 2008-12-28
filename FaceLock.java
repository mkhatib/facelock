import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FaceLock extends JFrame implements ActionListener, ListSelectionListener{
	// Constants
	// Model 
	private FLClient model = new FLClient();
	private JPanel container = new JPanel(new BorderLayout());
	
	// Dimension of the GUI Frame
	private Dimension dimensions = new Dimension(300,500);
	// A Helper Class to enable the JList rendering the Icons besides the names
	private ListCellRenderer contactListCellRenderer = new ContactListCellRenderer();
	// Default Model for the JList, listModel will be used to add new elements to the list.
	private DefaultListModel listModel = new DefaultListModel();

	// The Contact List
	private JList contactList = new JList(listModel);
	// The Toolbar buttons (add, delete, edit ...etc)
	// Login Panel
	private FaceLockLoginPanel loginPanel = new FaceLockLoginPanel(model);
	private JScrollPane scrollPane = new JScrollPane(contactList);
	
	private InformationPanel infoPanel = new InformationPanel(model);
	private JToolBar toolbar = new FLToolBar(model,infoPanel);
	
	
	// {{{ FaceLock constructor
    /**
     * 
     */
    public FaceLock() {
        super("FaceLock!");
		add(container);
		model.addActionListener(this);
		/*
				// Adding Some Demo Records.
				listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Cool.png"), "Mohammad N. Khatib"});
				listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Adore.png"), "Ramiz Abu Khiran"});
				listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Study.png"), "Walid Abu Salah"});
				listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Laugh.png"), "Ra'fat Sabbah"});
				listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Study.png"), "Mohammad Lahaseh"});
				listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Furious.png"), "Samer Shadafan"});
				listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Pudently.png"), "Bilal Ajaleen"});
				*/
		// Setting the Cell Renderer to render the string and Icons
		contactList.setCellRenderer(contactListCellRenderer);
		contactList.setDragEnabled(true);
		// Add the contact list to the center of the panel with JScrollPane to enable Scrolling
		//add(new JScrollPane(contactList));
		container.add(loginPanel);
		// Add the ToolBar to the North of the Panel
		//
		contactList.addListSelectionListener(this);
		// Center the Frame to the Screen
		setLocationRelativeTo(null);
		// Exit when closing the Frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setResizable(false);
		// Set The Frame Size to dimensions
		setSize(dimensions);
		// Show the Frame
		setVisible(true);
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
		if(action.equals("Login Succeeded"))
		{
			setSize(700,500);
			System.out.println("LOOOOOOOOOOOOOOOOOOOOGIN!");
			listModel.clear();
			contactList.removeAll();
			contactList.setModel(listModel);
			//contactList = new JList(listModel);
			scrollPane = new JScrollPane(contactList);
			ArrayList<Contact> contacts = model.getContacts();
			for(int i=0; i < contacts.size(); i++)
				listModel.addElement(contacts.get(i));
			if(contacts.size() > 0)
				infoPanel.setContact(contacts.get(0));
			else
				infoPanel.newContact();
			remove(container);
			container = new JPanel(new BorderLayout());
			container.add(toolbar, BorderLayout.NORTH);
			container.add(scrollPane, BorderLayout.WEST);
			container.add(infoPanel);
			add(container);
			validate();
		}
		else if(action.equals("Logout Succeeded")){
			listModel.clear();
			contactList.removeAll();
			listModel = new DefaultListModel();
			remove(container);
			validate();
			container = new JPanel(new BorderLayout());
			container.add(loginPanel);
			add(container);
			validate();
		}
		else if(action.equals("Updated Successfully") || action.equals("Added Successfully")){
/*			Contact selected = (Contact)contactList.getSelectedValue();
			if(selected == null){
				selected = contacts.get(0);
			}
*/			//if(listModel.getSize() > 0)
			//	listModel = new DefaultListModel();
			listModel.clear();
			contactList.removeAll();
			ArrayList<Contact> contacts = model.getContacts();
			for(int i=0; i < contacts.size(); i++)
				listModel.addElement(contacts.get(i));
			int selectedIndex = contactList.getSelectedIndex();
			contactList.setSelectedIndex(0);
			contactList.setSelectedIndex(selectedIndex);
			contactList.validate();
			infoPanel.setContact(contacts.get(contacts.size()-1));
			//contactList.updateUI();
/*			Contact con = null;
			for(int i=0; i < contacts.size(); i++)
				if((con = contacts.get(i)).getID() == contactID){
					con.setFirstName(firstName);
					con.setLastName(lastName);
					con.setMiddleName(middleName);
					con.setPhone(phone);
					con.setAddress(address);
					con.setEmail(email);
					con.setIcon(icon);
					break;
				}
*/			
				
		}
		else if(action.equals("Deleted Successfully")){
			/*for(int i=0; i < listModel.getSize(); i++)
							if(((Contact)listModel.getElementAt(i)).getID() == e.getID()){
								listModel.remove(i);
								break;
							}*/
			
			listModel.removeElementAt(contactList.getSelectedIndex());
		}
	}

	
	/**
	 * valueChanged
	 *
	 * @param e
	 * @return 
	 */
	public void valueChanged(ListSelectionEvent  e) {
		Contact contact = (Contact)contactList.getSelectedValue();
		//System.out.println("ID SELECTED: " + contact.getID());
		if(contact != null)
			infoPanel.setContact(contact);
		else
			infoPanel.newContact();
	}

	
	
	
	
	
	public static void main(String[] args){
		FaceLock fl = new FaceLock();
		
	}
}
