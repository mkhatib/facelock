// This Class is just for making the List of 
// Contacts Display an Icon besides the Name.

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public class ContactListCellRenderer implements ListCellRenderer {
    private JLabel label = new JLabel(" ",JLabel.LEFT);
	private Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Border emptyBorder = BorderFactory.createEmptyBorder(2,2,2,2);

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		Object[] pair = (Object[])value;
		label.setOpaque(true);
		label.setIcon((ImageIcon) pair[0]);
		label.setText(pair[1].toString());
		
		if(isSelected){
			label.setForeground(list.getSelectionForeground());
			label.setBackground(list.getSelectionBackground());
		}
		else {
			label.setForeground(list.getForeground());
			label.setBackground(list.getBackground());
		}
		
		label.setBorder(cellHasFocus ? lineBorder : emptyBorder);
		
		return label;
	}
	
	// {{{ ContactList constructor
    /**
     * 
     */
    public ContactListCellRenderer() {
        
    }
	// }}}
}
