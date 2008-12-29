import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
 
public final class InformationPanel extends JPanel implements ActionListener {
    private FLClient model;
	private Contact currentContact;
	private int selectedIndex=0;
	// Fields 
	private JTextField firstNameTF = new JTextField(15);
	private JTextField middleNameTF = new JTextField(15);
	private JTextField lastNameTF = new JTextField(15);
	private JTextField phoneTF = new JTextField(15);
	private JTextField addressTF = new JTextField();
	private JTextField emailTF = new JTextField(15);
	private JComboBox iconCB = new JComboBox(Utilities.ICONS);
	
	// Labels
	private JLabel firstNameLbl = new JLabel("First Name");
	private JLabel middleNameLbl = new JLabel("Middle Name");
	private JLabel lastNameLbl = new JLabel("Last Name");
	private JLabel phoneLbl = new JLabel("Phone");
	private JLabel addressLbl = new JLabel("Address");
	private JLabel emailLbl = new JLabel("Email");
	private JLabel iconLbl = new JLabel("Icon");
	
	// Buttons
	private JButton saveBtn = new JButton("Save" ,new ImageIcon("Images/Icons/ok.png"));
	private JButton updateBtn = new JButton("Update");
	private JButton cancelBtn = new JButton("Cancel", new ImageIcon("Images/Icons/cancel.png"));
	
	// Layout
	//private GroupLayout layout = new GroupLayout(this);
	private boolean isNew = false;
	// {{{ InformationPanel constructor

    public InformationPanel(FLClient model) {
        this.model = model;
		setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Contact Information"),new EmptyBorder(10,50,10,50)));
		
		setLayout(new GridLayout(8,2,1,1));
		add(firstNameLbl);
		add(firstNameTF);
		
		add(middleNameLbl);
		add(middleNameTF);
		
		add(lastNameLbl);
		add(lastNameTF);
		
		add(phoneLbl);
		add(phoneTF);
		
		add(addressLbl);
		add(addressTF);
		
		add(emailLbl);
		add(emailTF);
		
		add(iconLbl);
		add(iconCB);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(saveBtn);
		//buttonsPanel.add(updateBtn);
		buttonsPanel.add(cancelBtn);
		add(Box.createRigidArea(new Dimension(0,0)));
		add(buttonsPanel);
		
		
		cancelBtn.addActionListener(this);
		saveBtn.addActionListener(this);

    }
	public InformationPanel(FLClient model, Contact contact) {
		this(model);
        
		this.currentContact = contact;
		updateFields();
		
    }
	
	/**
	 * updateFields
	 *
	 * @param  
	 * @return 
	 */
	public void updateFields() {
		if(currentContact == null) newContact();
		firstNameTF.setText(currentContact.getFirstName());
		middleNameTF.setText(currentContact.getMiddleName());
		lastNameTF.setText(currentContact.getLastName());
		phoneTF.setText(currentContact.getPhone());
		addressTF.setText(currentContact.getAddress());
		emailTF.setText(currentContact.getEmail());
		iconCB.setSelectedIndex(currentContact.getIcon());
	}
	
	/**
	 * setContact
	 *
	 * @param contact 
	 * @return 
	 */
	public void setContact(Contact contact) {
		this.currentContact = contact;
		isNew = false;
		updateFields();
	}

	/**
	 * getContact
	 *
	 * @param  
	 * @return 
	 */
	public Contact getContact() {
		return currentContact;
	}
	
	/**
	 * setSelectedIndex
	 *
	 * @param  
	 * @return 
	 */
	public void setSelectedIndex(int index ) {
		this.selectedIndex = index;
	}

	/**
	 * getSelectedIndex
	 *
	 * @param  
	 * @return 
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * newContact
	 *
	 * @param  
	 * @return 
	 */
	public void newContact() {
		firstNameTF.setText("");
		middleNameTF.setText("");
		lastNameTF.setText("");
		phoneTF.setText("");
		addressTF.setText("");
		emailTF.setText("");
		iconCB.setSelectedIndex(0);
		isNew = true;	
	}

	/**
	 * actionPerformed
	 *
	 * @param  
	 * @return 
	 */
	public void actionPerformed(ActionEvent e ) {
		String action = e.getActionCommand();
		if(action.equals("Save")){
			if(!validateFields()){
				JOptionPane.showMessageDialog(null, "Please Fill All the Fields!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				if(isNew){
					boolean saved =  model.addContact(firstNameTF.getText(),middleNameTF.getText(),lastNameTF.getText(), phoneTF.getText(), addressTF.getText(), emailTF.getText(), iconCB.getSelectedIndex() );
					if(!saved) JOptionPane.showMessageDialog(null, "Contact Was Not Saved! Please Try Again!", "Not Saved!", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					boolean updated = model.updateContact(currentContact.getID(),firstNameTF.getText(),middleNameTF.getText(),lastNameTF.getText(), phoneTF.getText(), addressTF.getText(), emailTF.getText(), iconCB.getSelectedIndex() );
					if (!updated) JOptionPane.showMessageDialog(null, "Contact Was Not Saved! Please Try Again!", "Not Saved!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(action.equals("Cancel")){
			updateFields();
		}
	}

	/**
	 * validateFields
	 *
	 * @param  
	 * @return 
	 */
	public boolean validateFields() {
		if(firstNameTF.getText().trim().length() == 0 ) return false;
		if(middleNameTF.getText().trim().length() == 0 ) return false;
		if(lastNameTF.getText().trim().length() == 0 ) return false;
		if(phoneTF.getText().trim().length() == 0 ) return false;
		if(addressTF.getText().trim().length() == 0 ) return false;
		if(emailTF.getText().trim().length() == 0 ) return false;
		return true;
	}

	
	
	
}
