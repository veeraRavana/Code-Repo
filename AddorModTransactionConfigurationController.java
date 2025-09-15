package AdminController;

import javax.swing.JOptionPane;

import AdminApplication.TransactionConfigurationMain;
import AdminApplicationModel.TransactionConfigurationTypePOJO;
import AdminServices.TransactionConfigurationServices;
import commonGUI.AlertsandMessages;
import commonGUI.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AddorModTransactionConfigurationController extends AlertsandMessages {

	// This class is used to handle the addition or modification of transaction configurations
	// It will be used in the TransactionConfigurationMain class

	@FXML
	public TabPane mainTabPane; // TabPane for managing tabs
	// Reference to the TransactionConfigurationMain instance
	
	public TransactionConfigurationMain transConfigMain;
	public TransactionConfigurationServices transConfigServ;
	
	TransactionConfigurationTypePOJO transConfigTypePOJO ;
	
	String Nodename 	= null; // Node name for the tab
	int mode 			= 0; 	// 0 for Add mode, 1 for Modify mode
	String ScreenName 	= null; // Name of the screen (e.g., "Sales Type" or "Payment Type")
	
//	FXML Components for the UI
	@FXML private Label TypeIDLbl;
	@FXML private TextField TypeIDTF;
	
	@FXML private Label TypeNameLbl;
	@FXML private TextField TypeNameTF;
	
	@FXML private Label DescLbl;	
	@FXML private TextArea DescTA;
	
	@FXML private CheckBox StatusCB;
	
	@FXML private Button SubmitBtn;
	@FXML private Button CancelBtn;

	int TransactionTypeId = 0; // This variable will hold the transaction ID for the current operation

	
	
	
	public void setTransactionConfigurationAdd(
			TransactionConfigurationMain transactionConfigurationMain,
			TabPane TabPane,String nodename, int mode, String ScreenName,
			TransactionConfigurationTypePOJO transConfigTypePOJO) {

		this.transConfigMain	= transactionConfigurationMain;
		this.mainTabPane 		= TabPane;
		this.Nodename			= nodename;
		this.mode 				= mode;
		this.ScreenName			= ScreenName;
		setTransactionConfigurationComponets(ScreenName);
		
		if(mode	!= 0) {
				
			if(ScreenName.equals("Sales Type")) {
				setDataForModification(transConfigTypePOJO);
			} else if (ScreenName.equals("Payment Type")) {
				setDataForModification(transConfigTypePOJO);
			}
			
			
		}

	}
	
	@FXML
	public void initialize() {
		transConfigServ 		= new TransactionConfigurationServices();
	}
	
	
	private void setTransactionConfigurationComponets(String ScreenName) {
		// This method will set the components based on the ScreenName
		// It can be used to initialize the UI components for Sales Type or Payment Type
		if (ScreenName.equals("Sales Type")) {
			SalesTypeComponents();
		} else if (ScreenName.equals("Payment Type")) {
			PaymentTypeComponents();
		}

	}
	
	private void SalesTypeComponents() {
		
		TypeIDLbl.setText("Sales Type ID");
		TypeNameLbl.setText("Sales Type Name");
		TypeNameTF.setPromptText("Enter Sales Type Name");
		
		SetlayoutByMode(); // Set the ratio for the screen based on the mode
		
	}
	
	private void PaymentTypeComponents() {
		// This method will set the components for Payment Type
		TypeIDLbl.setText("Payment Type ID");
		TypeNameLbl.setText("Payment Type Name");
		TypeNameTF.setPromptText("Enter Payment Type Name");
		
		SetlayoutByMode(); // Set the ratio for the screen based on the mode
		
	}

	private void uomComponents() {

		TypeIDLbl.setText("UOM ID");
		TypeNameLbl.setText("UOM Name");
		TypeNameTF.setPromptText("Enter UOM Name");
		SetlayoutByMode(); // Set the ratio for the screen based on the mode
	}
	

	
	private void SetlayoutByMode() {
		if (mode == 1) {

			TypeIDLbl.setVisible(true);
			TypeIDTF.setVisible(true);
			TypeIDTF.setEditable(false); // Set the Sales Type ID and Payment Type IDfor Modification mode as non editable 
	
			// Set X and Y position for Modify mode
			setOriginalPositioning();
		} else {
			// Set X and Y position for Add mode
			setAddScreenPositioning();
			
		}
	}
	
/* This method is for the Modification Screen for
 *  both Sales Type and Payment Type*/	
 	private void setOriginalPositioning() {
		// This method can be used to set the original ratio for the components
		// It can be used to maintain the aspect ratio of the UI components
		// For now, it is left empty as per the original code
		TypeIDLbl.setLayoutX(46.0); // X position
		TypeIDLbl.setLayoutY(77.0); // Y position
		
		TypeIDTF.setLayoutX(172.0); // X position
		TypeIDTF.setLayoutY(73.0); // Y position
		
		TypeNameLbl.setLayoutX(46.0); // X position
		TypeNameLbl.setLayoutY(118.0); // Y position
		
		TypeNameTF.setLayoutX(172.0); // X position
		TypeNameTF.setLayoutY(114.0); // Y position
		
		DescLbl.setLayoutX(46.0); // X position
		DescLbl.setLayoutY(164.0); // Y position
		
		DescTA.setLayoutX(172.0); // X position
		DescTA.setLayoutY(164.0); // Y position
	}
	
/*this method is to set the Ratio of the Components
 *  to align for the Add Screen  */	
	private void setAddScreenPositioning() {
		TypeNameLbl.setLayoutX(46.0); // X position
		TypeNameLbl.setLayoutY(77.0); // Y position
		
		TypeNameTF.setLayoutX(172.0); // X position
		TypeNameTF.setLayoutY(73.0); // Y position
		
		DescLbl.setLayoutX(46.0); // X position
		DescLbl.setLayoutY(118.0); // Y position
		
		DescTA.setLayoutX(172.0); // X position
		DescTA.setLayoutY(114.0); // Y position
	}
	

	@FXML
	private void handleSubmit() {
    	PrepareData();
    	
    	if(ValidateFields()) {
    	 	if (JOptionPane.showConfirmDialog(null, "Do you want to Submit the details?",
    	 						"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
	 		
    	 		if(mode == 0) {
    	 			TransactionTypeId		= 0;
        	 		TransactionTypeId 		= transConfigServ.saveOrModifyTransactionConfigurationType(TransactionTypeId, transConfigTypePOJO, mode, ScreenName);
		 		} else {
		 			TransactionTypeId 		= transConfigServ.ModifyTransactionTypeTables(TransactionTypeId, transConfigTypePOJO, mode, ScreenName);
		 		}
		 		
		 		 // Check if the TransactionTypeId is not zero, indicating success}
    	 		String Message			= mode == 0 ? "Created" : "Modified";
    	 		if (TransactionTypeId != 0) {
    	 			//log the transaction configuration type
    	 			 transConfigServ.TransactionConfiguration_Log(TransactionTypeId, transConfigTypePOJO, mode, ScreenName);
    	 	
    	 		
    				JOptionPane.showMessageDialog(null, ScreenName+" ID : "+TransactionTypeId+"  has been "+Message);
    				handleCancel();
    	 		}else {
    				JOptionPane.showMessageDialog(null, "Error occured while saving. "+ScreenName+" not "+Message);
    				return;
    			}
    		}
    	}
	}
	
	
	private void PrepareData() {
		
		transConfigTypePOJO 	= new TransactionConfigurationTypePOJO();
		
		String Mode 			= mode == 0 ? "Add" : "Modify"; // Determine the mode for logging
		// This method prepares the data for saving or modifying the transaction configuration type
			if (mode == 1) {
				transConfigTypePOJO.setTransactionId(Integer.parseInt(TypeIDTF.getText().trim()));
			} else {
				transConfigTypePOJO.setTransactionId(0); // For Add mode, set Type ID to 0
			}
		transConfigTypePOJO.setTransactionType(ScreenName); 							// Set the transaction type , e.g., "Sales Type" or "Payment Type"
		transConfigTypePOJO.setTransactionTypeName(TypeNameTF.getText().toString());	// eg :- payment type cash, credit card, etc./ sales type garments sales, electronics, etc.
		transConfigTypePOJO.setTransactionDescription(DescTA.getText().trim());
		transConfigTypePOJO.setTransactionStatus(StatusCB.isSelected() ? true : false); // Set status based on checkbox active or inactive
		transConfigTypePOJO.setTransactionMode(Mode+""+ScreenName); 					// Set the transaction mode string eg "Add Sales Type" or "Modify Payment Type"
		
// Additional fields for user tracking can be set here if needed
		 transConfigTypePOJO.setUserName(UserSession.getInstance().getUsername()); 		// Get the username from the session
		 transConfigTypePOJO.setUserIPAddress(UserSession.getInstance().getUserIp()); 	// Get the user IP address from the session
		
	
	}
	
	
	private void setDataForModification(TransactionConfigurationTypePOJO transConfigTypePOJO)  {
			TransactionTypeId 		= transConfigTypePOJO.getTransactionId(); // Get the Transaction ID from the POJO
			TypeIDTF.setText(String.valueOf(transConfigTypePOJO.getTransactionId()));
			TypeNameTF.setText(transConfigTypePOJO.getTransactionTypeName());
			DescTA.setText(transConfigTypePOJO.getTransactionDescription());
			StatusCB.setSelected(transConfigTypePOJO.getTransactionStatus());

	}
	
	 public boolean ValidateFields() {
		 if(TypeNameTF.getText().isEmpty()) {
			 showAlertWarning("Type Name cannot be empty", "Please enter a valid "+ScreenName+" Name");
			 return false;
			 
		 }
		 return true;
	 }
	 
	@FXML
	private void handleCancel() {
		
		 if (mainTabPane != null) {
	        	
	            Tab currentTab 	= mainTabPane.getSelectionModel().getSelectedItem();
	            if (currentTab != null) {
	            	
	                mainTabPane.getTabs().remove(currentTab);
	                transConfigMain.loadTransactionConfigurationMain(Nodename, mainTabPane);
	            }
	        } else {
	            System.out.println("mainTabPane is NULL! Ensure it's correctly set in FXML.");
	        }
	}
	
	
}
