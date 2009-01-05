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
	
	// Text
	private JLabel firstNameTxt 	= new JLabel();
	private JLabel middleNameTxt	= new JLabel();
	private JLabel lastNameTxt 		= new JLabel();
	private JLabel phoneTxt 		= new JLabel();
	private JLabel addressTxt 		= new JLabel();
	private JLabel emailTxt 		= new JLabel();
	private JLabel iconTxt 			= new JLabel();
	
	// Buttons
	private JButton saveBtn = new JButton("Save" ,new ImageIcon("Images/Icons/ok.png"));
	private JButton editBtn = new JButton("Edit");
	private JButton cancelBtn = new JButton("Cancel", new ImageIcon("Images/Icons/cancel.png"));
	
	// Layout
	//private GroupLayout layout = new GroupLayout(this);
	private boolean isNew = false;
	// {{{ InformationPanel constructor

    public InformationPanel(FLClient model) {
        this.model = model;
		setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Contact Information"),new EmptyBorder(10,50,10,50)));
		
		showInfoPanel();

		cancelBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		editBtn.addActionListener(this);
    }


	/**
	 * showInfoPanel
	 *
	 * @param  
	 * @return 
	 */
	public void showInfoPanel() {
		removeAll();
		validate();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.CENTER);
		
		JPanel upperPanel = new JPanel();
		layout.layoutContainer(upperPanel);
		upperPanel.setLayout(layout);
		upperPanel.add(iconTxt);
		Font font = new Font("Calibri", Font.BOLD, 32);
		JPanel namePanel = new JPanel();
		
		firstNameTxt.setFont(font);
		middleNameTxt.setFont(font);
		lastNameTxt.setFont(font);
		
		
		namePanel.add(firstNameTxt);
		namePanel.add(middleNameTxt);
		namePanel.add(lastNameTxt);
		
		upperPanel.add(namePanel);
		
		JPanel lowerPanel = new JPanel(new GridLayout(4,2,1,1));
		lowerPanel.add(phoneLbl);
		lowerPanel.add(phoneTxt);
		lowerPanel.add(addressLbl);
		lowerPanel.add(addressTxt);
		lowerPanel.add(emailLbl);
		lowerPanel.add(emailTxt);
		lowerPanel.add(Box.createRigidArea(new Dimension(0,0)));
		
		
		
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.RIGHT);
		JPanel editPanel = new JPanel(fl);
		editPanel.add(editBtn);
		
		lowerPanel.add(editPanel);
		
		
		
		add(upperPanel/*,BorderLayout.NORTH*/);

		add(lowerPanel/*, BorderLayout.CENTER*/);
		
		
		
		/*setLayout(new GridLayout(8,2,1,1));
		add(firstNameLbl);
		add(firstNameTxt);
		
		add(middleNameLbl);
		add(middleNameTxt);
		
		add(lastNameLbl);
		add(lastNameTxt);
		
		add(phoneLbl);
		add(phoneTxt);
		
		add(addressLbl);
		add(addressTxt);
		
		add(emailLbl);
		add(emailTxt);
		
		add(iconLbl);
		add(iconTxt);
		add(Box.createRigidArea(new Dimension(0,0)));
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.RIGHT);
		JPanel editPanel = new JPanel(fl);
		
		editPanel.add(editBtn);
		add(editPanel);*/
		updateInfo();
//		add(cancelBtn);
		validate();
		repaint();
	}

	

	/**
	 * showEditPanel
	 *
	 * @param  
	 * @return 
	 */
	public void showEditPanel() {
		removeAll();
		validate();
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
		
		add(saveBtn);
		add(cancelBtn);
		
		add(saveBtn);
		add(cancelBtn);
		
		validate();
		repaint();
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
		if(currentContact == null) 			newContact();
		firstNameTF.setText(currentContact.getFirstName());
		middleNameTF.setText(currentContact.getMiddleName());
		lastNameTF.setText(currentContact.getLastName());
		phoneTF.setText(currentContact.getPhone());
		addressTF.setText(currentContact.getAddress());
		emailTF.setText(currentContact.getEmail());
		iconCB.setSelectedIndex(currentContact.getIcon());
	}
	
	/**
	 * updateLabels
	 *
	 * @param  
	 * @return 
	 */
	public void updateInfo() {
		if(currentContact == null) return;
		firstNameTxt.setText(currentContact.getFirstName());
		middleNameTxt.setText(currentContact.getMiddleName());
		lastNameTxt.setText(currentContact.getLastName());
		phoneTxt.setText(currentContact.getPhone());
		addressTxt.setText(currentContact.getAddress());
		emailTxt.setText(currentContact.getEmail());
		iconTxt.setIcon(Utilities.BIG_ICONS[currentContact.getIcon()]);
		
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
		showInfoPanel();
		updateInfo();
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
		showEditPanel();
		
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
		else if(action.equals("Edit")){
			showEditPanel();
			updateFields();
		}
		else if(action.equals("Cancel")){
			//updateFields();
			showInfoPanel();
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
