import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Class that extends JToolBar and add some buttons to it
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public  class FLToolBar extends JToolBar{
    
	// Path where the icons are
	private final String PATH = "Images/Icons/48x48/";
	// Text of the buttons on the Toolbar
	private String[] text = 
	{
		"Add User", "Delete User", "Information", "Settings", "Refresh", "Find"	
	};
	// Icons of the buttons on the Toolbar
    private String[] icons =
	{
		"add.png", "delete.png", "info.png", "settings.png", "refresh.png", "find.png"
	};
	
	
	// {{{ FLToolBar constructor
    /**
     * 
     */
    public FLToolBar() {
		// Loop Over the Array of text or icons
        for(int i=0; i<text.length; i++){
			// Create new Button with Icon[i]
			JButton btn = new JButton(/*text[i],*/new ImageIcon(PATH+icons[i]));
			// Remove the border of the button
			btn.setBorder(BorderFactory.createEmptyBorder());
			// add it to the JToolBar
			add(btn);
		}
    }
	// }}}
}
