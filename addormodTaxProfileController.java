package AdminController;

import AdminApplication.TaxProfileMain;
import AdminApplicationModel.TaxProfilePojo;
import AdminServices.TaxProfileService;
import commonGUI.AlertsandMessages;
import commonGUI.TabManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class addormodTaxProfileController extends AlertsandMessages {
	
    private final TabManager tabManager = TabManager.getInstance();
    public TaxProfileMain taxpromain;
//files    
    TaxProfilePojo taxProfilePojo		= null;
    TaxProfileService taxprofileservice	= null;
    
//attributes    
    @FXML private TabPane mainTabPane;
    @FXML private Label cGstFieldlbl,sGstFieldlbl;
    @FXML private Button saveTaxButton, cancelTaxButton;
    @FXML private TextField taxNameField, cGstField, sGstField;
    @FXML private ComboBox<String> taxTypeComboBox;
    
    @FXML private MenuItem saveTaxMenu,cancelTaxMenu; 
    
    private Label InputVATlbl;
    String Nodename		= null;
    
    int mode			= 0;
    
	public void setTaxProfileAdd(TaxProfileMain taxProfileMain,TabPane tabPane,String nodeName,int mode) {
		this.taxpromain		= taxProfileMain;
		this.mainTabPane	= tabPane;
		this.Nodename		=  nodeName;
		this.mode 			= mode;
	}
    
    @FXML
    public void initialize() {
    	taxprofileservice	= new TaxProfileService();

		if (saveTaxButton 	!= null) {
		    saveTaxButton.setOnAction(e -> Save_action());
		}
		if (cancelTaxButton != null) {
		    cancelTaxButton.setOnAction(e -> closeTab());
		}
		
		if(saveTaxMenu 		!=null ) {
			saveTaxMenu.setOnAction(e -> Save_action());
		}
		if(cancelTaxMenu 	!=null) {
			cancelTaxMenu.setOnAction(e -> closeTab());	
		}
		
   // this is to add the components to the screen     
		Components();
    	


    }
    public void Components() {
    	 // Tax-type combo box 
    		taxTypeComboBox();
    		InputVATlbl();
    		returnFalse();
    		
       
    }
    public void returnFalse() {
    	cGstFieldlbl.setVisible(false);
        cGstField.setVisible(false); 
        sGstFieldlbl.setVisible(false);
        sGstField.setVisible(false);
         
    }
   
    
   /*to check if the field data are valid */ 
    public void checktextfields() {
    	Cgstfields();
    	Sgstfields();
    	
    }
    
    private ComboBox taxTypeComboBox(){
    	if(taxTypeComboBox != null) {
    		 // Populate ComboBox with options
            taxTypeComboBox.setItems(FXCollections.observableArrayList("GST", "VAT", "Income Tax", "Sales Tax"));
            // Set ComboBox action
            taxTypeComboBox.setOnAction(event -> handleTaxTypeSelection());
    	}
    	
		return taxTypeComboBox;
    	
    }
    
    public void Cgstfields() {
    	cGstField.setOnKeyTyped(event -> {
    	    String text = cGstField.getText();
    	    char ch = event.getCharacter().charAt(0);

    	    // Allow digits and a single decimal point
    	    if (!(Character.isDigit(ch) || (ch == '.' && !text.contains(".")))) {
    	    	showAlert("Tax profile " ,"Only numbers and a single decimal point are allowed.");
    	        event.consume(); // Block invalid input
    	        return;
    	    }

    	    // Prevent more than two decimal places
    	    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() >= 2) {
    	        if (Character.isDigit(ch)) {
    	        	  showAlert("Tax profile " ,"maximum 2 charater allowed ");
    	        	  event.consume(); // Block extra digits after decimal
    	        }
    	    }
    	});
    }
    
    
    public void Sgstfields() {
    	sGstField.setOnKeyTyped(event -> {
    	    String text = sGstField.getText();
    	    char ch = event.getCharacter().charAt(0);

    	    // Allow digits and a single decimal point
    	    if (!(Character.isDigit(ch) || (ch == '.' && !text.contains(".")))) {
    	    	showAlert("Tax profile " ,"Only numbers and a single decimal point are allowed.");
    	        event.consume(); // Block invalid input
    	        return;
    	    }

    	    // Prevent more than two decimal places
    	    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() >= 2) {
    	        if (Character.isDigit(ch)) {
    	        	  showAlert("Tax profile " ,"maximum 2 charater allowed ");
    	        	  event.consume(); // Block extra digits after decimal
    	        }
    	    }
    	});
    }


	 
    private Label InputVATlbl() {
    	if(InputVATlbl	== null) {
    		InputVATlbl= new Label();
    		InputVATlbl.setText("Input VAT");
    		InputVATlbl.setLayoutX(15);
    		InputVATlbl.setLayoutY(30);
    	}
		return InputVATlbl;
    	
    }
   
    public void clearcontents() {
    	// to set the text field contents empty 
    	cGstField.setText("");
    	sGstField.setText("");
    }
    
    private void handleTaxTypeSelection() {
        String selectedTax = taxTypeComboBox.getValue();
        
        clearcontents();
        // Show C-GST and S-GST fields only if GST is selected
        if ("GST".equals(selectedTax)) {
        	cGstFieldlbl.setVisible(true);
            cGstField.setVisible(true);
            sGstFieldlbl.setVisible(true);
            sGstField.setVisible(true);
        } else {
        	cGstFieldlbl.setVisible(false);
            cGstField.setVisible(false); 
            sGstFieldlbl.setVisible(false);
            sGstField.setVisible(false);
        }
    }
    

   public void Save_action() {
	   
	   if(validateFields()!=true) {
		   return;
	   }
	   taxProfilePojo = new TaxProfilePojo();
	   
	   taxProfilePojo.TaxprofileName	= taxNameField.getText() ==null ? " " :taxNameField.getText().toString();
	   taxProfilePojo.TaxprofileType	= taxTypeComboBox.getValue() ==null ? " " :taxTypeComboBox.getValue().toString();
	   
	   //unstable fields
	   taxProfilePojo.SGstper			= Double.parseDouble(sGstField.getText() ==null ? "0.0" :sGstField.getText().toString());
	   taxProfilePojo.CGstper			= Double.parseDouble(cGstField.getText() ==null ? "0.0" :cGstField.getText().toString());
	
	   //mode 0 is the add
	   if(mode==0) {
			   taxProfilePojo.TaxVersion			= 1;

		 }else {
			 taxProfilePojo.TaxVersion			= 1+1;
		 }
	   boolean isInserted =taxprofileservice.SaveTaxProfile(taxProfilePojo);
	   if(isInserted==true) {
		   showAlert("Tax profile " ,"Tax Profile added successfully");
		   closeTab();
	   }else {
		   showAlert("Tax profile" ,"Error Please contact admin");
		   return;
	   }
	  
   }
   
   
   /*this is to validate the all fields and attributes
    *  to make sure the main things are not left out*/
   public boolean validateFields() {
	   
	   
	   String Taxtypecomb = taxTypeComboBox.getValue() ==null ? " " :taxTypeComboBox.getValue().toString();
	   
	   if(taxNameField.getText().isEmpty()) {
		   showAlert("Add Tax profile " ,"kindly fill the necessary details");
		   return false;
	   }
	  
	   if(Taxtypecomb.isBlank() ) {
		   showAlert("Add Tax profile " ,"please select the Tax type");
		   return false;
	   }
	   // this is to find the C gst and Sgst if it is empty or not  
	   if(Taxtypecomb.equalsIgnoreCase("GST")) {
		   
		   if(sGstField.getText().isEmpty()) {
			   showAlert("Add Tax profile " ,"SGST field is Empty ");
			   return false;
		   }
		   if(sGstField.getText().isEmpty()) {
			   showAlert("Add Tax profile " ,"CGST field is Empty ");
			   return false;
		   }
	   }

	return true;
	   
   }
    private void closeTab() {
        if (mainTabPane != null) {
        	
            Tab currentTab = mainTabPane.getSelectionModel().getSelectedItem();
            if (currentTab != null) {
            	
                mainTabPane.getTabs().remove(currentTab);
                taxpromain.loadTaxProfileMain(Nodename, mainTabPane);
            }
        } else {
            System.out.println("mainTabPane is NULL! Ensure it's correctly set in FXML.");
        }
    }


}
