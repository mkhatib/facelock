//package .Users.mkhatib.Desktop.Network-Project.facelock;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public  class FLToolBar extends JToolBar{
    
	private final String PATH = "Images/Icons/48x48/";
	private String[] text = 
	{
		"Add User", "Delete User", "Information", "Settings", "Refresh", "Find"	
	};
    private String[] icons =
	{
		"add.png", "delete.png", "info.png", "settings.png", "refresh.png", "find.png"
	};
	// {{{ FLToolBar constructor
    /**
     * 
     */
    public FLToolBar() {
        for(int i=0; i<text.length; i++){
			JButton btn = new JButton(/*text[i],*/new ImageIcon(PATH+icons[i]));
			btn.setBorder(BorderFactory.createEmptyBorder());
			add(btn);
		}
    }
	// }}}
}
