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
	private FLMenuBar menubar = new FLMenuBar(model);
	// Dimension of the GUI Frame
	private Dimension loginDimensions = new Dimension(300,600);
	private Dimension listDimensions = new Dimension(750,600);
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

    public FaceLock() {
        super("FaceLock!");
		setJMenuBar(menubar);
		add(container);
		model.addActionListener(this);
		
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
		setSize(loginDimensions);
		// Show the Frame
		setVisible(true);
    }
	
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
			setSize(listDimensions);
			ArrayList<Contact> contacts = model.getContacts();
			for(int i=0; i < contacts.size(); i++)
				listModel.addElement(contacts.get(i));
			contactList.validate();
			
			if(contacts.size() > 0)
			{
				infoPanel.setContact(contacts.get(0));
				infoPanel.setSelectedIndex(0);
			}	
			else
			{
				infoPanel.newContact();
				infoPanel.setSelectedIndex(0);
			}	
			remove(container);
			container = new JPanel(new BorderLayout());
			container.add(toolbar, BorderLayout.NORTH);
			container.add(scrollPane, BorderLayout.WEST);
			container.add(infoPanel);
			add(container);
			contactList.validate();
			contactList.repaint();
			validate();
			
		}
		else if(action.equals("Logout Succeeded")){
			setSize(loginDimensions);
			listModel.clear();
			remove(container);
			container = new JPanel(new BorderLayout());
			container.add(loginPanel);
			add(container);
			
			validate();
		}
		else if(action.equals("Updated Successfully") || action.equals("Added Successfully") ){
			listModel.clear();
			contactList.removeAll();
			ArrayList<Contact> contacts = model.getContacts();
			for(int i=0; i < contacts.size(); i++)
				listModel.addElement(contacts.get(i));
			int selectedIndex = contactList.getSelectedIndex();
			contactList.setSelectedIndex(0);
			contactList.setSelectedIndex(selectedIndex);
			infoPanel.setSelectedIndex(selectedIndex);
			
			
			contactList.validate();
			infoPanel.setContact(contacts.get(contacts.size()-1));	
		}
		
		else if(action.equals("Deleted Successfully")){
			
			listModel.removeElementAt(contactList.getSelectedIndex());
		}
		else if(action.equals("Sorted Successfully")){
			listModel.clear();
			contactList.removeAll();
			ArrayList<Contact> contacts = model.getContacts();
			
			for(int i=0; i < contacts.size(); i++)
				listModel.addElement(contacts.get(i));
			
			contactList.setSelectedIndex(0);
			contactList.validate();
			
		}
		else if(action.equals("Search Done")){
			contactList.setSelectedIndex(e.getID());
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
		{
			infoPanel.setContact(contact);
			infoPanel.setSelectedIndex(contactList.getSelectedIndex());
		}
		else
		{
			infoPanel.newContact();
			infoPanel.setSelectedIndex(0);
		}
	}
	
	public static void main(String[] args){
		FaceLock fl = new FaceLock();
		
	}
}
