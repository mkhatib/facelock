import javax.swing.ImageIcon;

/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class Utilities {
    

	public final static ImageIcon[] ICONS = {
		(new ImageIcon("Images/Smilies/48x48/Adore.png")),
		(new ImageIcon("Images/Smilies/48x48/Cool.png")),
		(new ImageIcon("Images/Smilies/48x48/Cry.png")),
		(new ImageIcon("Images/Smilies/48x48/Furious.png")),
		(new ImageIcon("Images/Smilies/48x48/Laugh.png")),
		(new ImageIcon("Images/Smilies/48x48/Pudently.png")),
		(new ImageIcon("Images/Smilies/48x48/Struggle.png")),
		(new ImageIcon("Images/Smilies/48x48/Study.png")),
		(new ImageIcon("Images/Smilies/48x48/Sweet-angel.png"))
	};
	public final static ImageIcon[] BIG_ICONS = {
		(new ImageIcon("Images/Smilies/128x128/Adore.png")),
		(new ImageIcon("Images/Smilies/128x128/Cool.png")),
		(new ImageIcon("Images/Smilies/128x128/Cry.png")),
		(new ImageIcon("Images/Smilies/128x128/Furious.png")),
		(new ImageIcon("Images/Smilies/128x128/Laugh.png")),
		(new ImageIcon("Images/Smilies/128x128/Pudently.png")),
		(new ImageIcon("Images/Smilies/128x128/Struggle.png")),
		(new ImageIcon("Images/Smilies/128x128/Study.png")),
		(new ImageIcon("Images/Smilies/128x128/Sweet-angel.png"))
	};
	
	// {{{ Utilities constructor
    /**
     * 
     */
    public Utilities() {
        
    }
	
	/**
	 * getImageIcon
	 *
	 * @param icon 
	 * @return 
	 */
	public static ImageIcon getImageIcon(int icon) {
		return ICONS[icon];
	}
	
	/**
	 * getAllIcons
	 *
	 * @param icon 
	 * @return 
	 */
	public static ImageIcon[] getAllIcons() {
		return ICONS;
	}
	
}
