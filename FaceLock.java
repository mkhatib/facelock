import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FaceLock extends JFrame implements ActionListener{
	// Constants
	// Model 
	private FLClient model = new FLClient();
	
	// Dimension of the GUI Frame
	private Dimension dimensions = new Dimension(300,500);
	// A Helper Class to enable the JList rendering the Icons besides the names
	private ListCellRenderer contactListCellRenderer = new ContactListCellRenderer();
	// Default Model for the JList, listModel will be used to add new elements to the list.
	private DefaultListModel listModel = new DefaultListModel();
	// The Contact List
	private JList contactList = new JList(listModel);
	// The Toolbar buttons (add, delete, edit ...etc)
	private JToolBar toolbar = new FLToolBar();
	// Login Panel
	private FaceLockLoginPanel loginPanel = new FaceLockLoginPanel(model);
	
	
	// {{{ FaceLock constructor
    /**
     * 
     */
    public FaceLock() {
        super("FaceLock!");
		model.addActionListener(this);
		
		// Adding Some Demo Records.
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Cool.png"), "Mohammad N. Khatib"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Adore.png"), "Ramiz Abu Khiran"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Study.png"), "Walid Abu Salah"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Laugh.png"), "Ra'fat Sabbah"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Study.png"), "Mohammad Lahaseh"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Furious.png"), "Samer Shadafan"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Pudently.png"), "Bilal Ajaleen"});
		
		// Setting the Cell Renderer to render the string and Icons
		contactList.setCellRenderer(contactListCellRenderer);
		
		// Add the contact list to the center of the panel with JScrollPane to enable Scrolling
		//add(new JScrollPane(contactList));
		add(loginPanel);
		// Add the ToolBar to the North of the Panel
		add(toolbar, BorderLayout.NORTH);
		
		// Center the Frame to the Screen
		setLocationRelativeTo(null);
		// Exit when closing the Frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		
	}

	
	
	
	
	
	public static void main(String[] args){
		FaceLock fl = new FaceLock();
		
	}
}
