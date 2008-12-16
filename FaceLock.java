//package .Users.mkhatib.Desktop.Network-Project.facelock;
import java.awt.*;
import javax.swing.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class FaceLock extends JFrame {
    
	// Constants
	private Dimension dimensions = new Dimension(300,500);
	private ListCellRenderer contactListCellRenderer = new ContactListCellRenderer();
	private DefaultListModel listModel = new DefaultListModel();
	private JList contactList = new JList(listModel);
	private JToolBar toolbar = new FLToolBar();
	// {{{ FaceLock constructor
    /**
     * 
     */
    public FaceLock() {
        super("FaceLock!");
		
		
		// Demo
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Cool.png"), "Mohammad N. Khatib"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Adore.png"), "Ramiz Abu Khiran"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Study.png"), "Walid Abu Salah"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Laugh.png"), "Ra'fat Sabbah"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Study.png"), "Mohammad Lahaseh"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Furious.png"), "Samer Shadafan"});
		listModel.addElement(new Object[] {new ImageIcon("Images/Smilies/48x48/Pudently.png"), "Bilal Ajaleen"});
		
		contactList.setCellRenderer(contactListCellRenderer);
		add(new JScrollPane(contactList));
		add(toolbar, BorderLayout.NORTH);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(dimensions);
		setVisible(true);
    }
	// }}}
	
	
	
	
	
	
	public static void main(String[] args){
		FaceLock fl = new FaceLock();
		
	}
}
