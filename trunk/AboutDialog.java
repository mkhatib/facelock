// Fri Oct 25 18:07:43 EST 2004
//
// Written by Sean R. Owens, released to the public
// domain.  Share and enjoy.  http://darksleep.com/player

// A very simple custom dialog that takes a string as a parameter,
// displays it in a JLabel, along with two Jbuttons, one labeled Yes,
// and one labeled No, and waits for the user to click one of them.

import javax.swing.JDialog; 
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;


public class AboutDialog extends JDialog implements ActionListener {
    private JPanel myPanel = null;
    private JButton okButton = null;
    private boolean answer = false;
    public boolean getAnswer() { return answer; }

    public AboutDialog(JFrame frame, boolean modal, String[] names, ImageIcon icon) {
        super(frame, modal);
        myPanel = new JPanel();
		myPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		myPanel.setLayout(new GridLayout(names.length + 2,1));
        getContentPane().add(myPanel);
		myPanel.add(new JLabel("This Program Developed By: "));
		for(int i=0; i<names.length; i++){
			JLabel label = new JLabel(names[i]);
			label.setIcon(icon);
			label.setFont(new Font("Serif", 0, 24));
			myPanel.add(label);
		}
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        myPanel.add(okButton);        
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(okButton == e.getSource()) {
            System.err.println("User chose yes.");
            answer = true;
            setVisible(false);
        }
    }
    
}