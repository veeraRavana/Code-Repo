package AdminController;

import AdminApplicationModel.ADDAddressPOJO;
import AdminServices.CompanyRegisterServices;
import common.Countries;
import common.District;
import common.State;
import commonGUI.AlertsandMessages;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddAddresspopupController extends AlertsandMessages{

	
	AddorModCompanyRegisterController  addcompreg;
	CompanyRegisterServices CompRegServ	;
	
	@FXML public TextField addresstf;
	@FXML public TextField addresscityTf;
	@FXML public TextField PincodeTF;
	
    @FXML private ComboBox<State> StateCombobox;
    @FXML private ComboBox<District> DistrictCombobox;
    @FXML private ComboBox<Countries> CountryCombobox;
    @FXML private CheckBox isheadofficeCB;
    
    @FXML private Button Submitbutton;
    
    public ADDAddressPOJO addpojo;// POJO to set and get the data 
    boolean isAddressPopupOpened	= false;
    int mode						= 0;
    
    public void setComponentsinPopup(AddorModCompanyRegisterController addCompRegController,
    		boolean isaddressPopupOpened, int Mode) {
		this.addcompreg				= addCompRegController;
		this.isAddressPopupOpened	= isaddressPopupOpened;
		this.mode					= Mode;
		addpojo						= addcompreg.addpojo;

		if(mode !=0 || isAddressPopupOpened == true ) {
			setDatainFields();
			
			if(mode	==2) {
				Disableediting();
			}
		}
	}
    
   /*this method will disable all the
    *  fields that are editable*/ 
	private void Disableediting() {
		
		addresstf.setEditable(false);
		addresscityTf.setEditable(false); 
		PincodeTF.setEditable(false); 
		StateCombobox.setEditable(false);
		DistrictCombobox.setEditable(false);
		CountryCombobox.setEditable(false);
		isheadofficeCB.setDisable(false);
		
		Submitbutton.setVisible(false);
	}
    
    private void setDatainFields() {
    	 

    	 addresstf.setText(addpojo.getAddress1().toString());
    	 addresscityTf.setText(addpojo.getAddress2().toString());
    	 PincodeTF.setText(addpojo.getPostalcode().toString());
    	
    	   // Match state
    	    for (State state : StateCombobox.getItems()) {
    	        if (state.getStateName().equals(addpojo.getState())) {
    	            StateCombobox.setValue(state);
    	            loadDistrictComboBox(addpojo.getStateID());
    	            break;
    	        }
    	    }
    	    // Match district
    	    for (District district : DistrictCombobox.getItems()) {
    	        if (district.getDistrictName().equals(addpojo.getDistrict())) {
    	            DistrictCombobox.setValue(district);
    	            break;
    	        }
    	    }

    	    // Match country
    	    for (Countries country : CountryCombobox.getItems()) {
    	        if (country.getCountry_name().equals(addpojo.getCountryorigin())) {
    	            CountryCombobox.setValue(country);
    	            break;
    	        }
    	    }

    	 
    	 isheadofficeCB.setSelected(addpojo.isHeadOffice);
    }
    
    @FXML
    public void initialize() {
    	CompRegServ			= new CompanyRegisterServices();
    	Pincode();
    	CountryCombobox();
    	loadStateComboBox();
        setupStateComboBoxListener();
    }
    
/*******************************
 * Combo box loading and Setup
 *******************************/
    
    private void Pincode() {
    	
    	PincodeTF.addEventFilter(KeyEvent.KEY_TYPED, event -> {
    	    String text = event.getCharacter();
    	    String  CurrentpinCode = PincodeTF.getText();
    	    // Ignore control keys like Backspace, Delete, Tab, etc.
	        if (text.isEmpty() || text.charAt(0) < 32) {
	            return;
	        }
            // Allow only digits
            if (!text.matches("\\d")) {
                showAlertWarning("Add Address", "Only numeric values are allowed.");
                event.consume(); // Block character
                return;
            }
          

            // Limit to 10 digits
            if (CurrentpinCode.length() >= 6) {
                showAlertWarning("Add Address", "maximum 6 charater allowed ");
                event.consume(); // Block character
            }
    	});
    }
    
    private void CountryCombobox(){
     	CountryCombobox.setItems(FXCollections.observableArrayList(CompRegServ.getAllContries()));
	}
    
    // this method is to load State combo-box 
    private void loadStateComboBox() {
        StateCombobox.setItems(FXCollections.observableArrayList(CompRegServ.getAllStates()));
    }

   /*this method setupStateComboBoxListener will
    *  setup the District combo box while 
    *  selecting the State Combo box */
    private void setupStateComboBoxListener() {
        StateCombobox.setOnAction(e -> {
            State selectedState = StateCombobox.getValue();
            if (selectedState != null) {
                loadDistrictComboBox(selectedState.getStateId());
            }
        });
    }

//     this is to load the district combo box by passing the state id 
    private void loadDistrictComboBox(int stateId) {
        DistrictCombobox.setItems(FXCollections.observableArrayList(CompRegServ.getDistrictsByStateId(stateId)));
    }

    
/*******************************
 * Button Actions and Controls
 *******************************/
    

   public void handleSave() {
	  
	   Submitbutton.setOnAction(e ->{
		String screenName	= "Add Address Popup"; 
		
		if(mode == 1) {
			screenName		= "Mod Address Popup"; 
		}
		   if(vaidatePopFields(screenName)) {
			   preparedataForAddress();
			   isAddressPopupOpened	= true;
			   handleCancel(e);
	    	}
		  
	   });
    	
    }
   
   
    private void preparedataForAddress() {
    	ADDAddressPOJO addpojo	= new ADDAddressPOJO();
    		
    	addpojo.Address1		= addresstf.getText().toString();
    	addpojo.Address2		= addresscityTf.getText().toString();

// gets the selected State object
        State selectedState 		= StateCombobox.getValue();
        if (selectedState != null) {
        	addpojo.stateID 		= selectedState.getStateId();     // the numeric ID
            addpojo.State   		= selectedState.getStateName();     // the display name
        }
// gets the selected District object
        District selectedDistrict 	= DistrictCombobox.getValue();
        if (selectedDistrict != null) {
            addpojo.DistrictID 		= selectedDistrict.getDistrictId();
            addpojo.District  		= selectedDistrict.getDistrictName();
        }
//get the selected Country object
       Countries selectCountries 	= CountryCombobox.getValue();
        if (selectCountries != null) {
            addpojo.CountryID 		= selectCountries.getCountry_id();
            addpojo.Countryorigin  	= selectCountries.getCountry_name();
        }
    	addpojo.isHeadOffice		= isheadofficeCB.isSelected();
    	addpojo.Postalcode			= Integer.parseInt(PincodeTF.getText()== null ? "0" :PincodeTF.getText().toString());
    	
    	
   /*this set the main screen data by fetching the values from the pop up */ 	
    	addcompreg.CompanyadressTF.setText(addpojo.Address1);	//1st text Field Address  
    	addcompreg.CompanyOrgin.setText(addpojo.Countryorigin); // Country / Origin 
		
    	addcompreg.addpojo			= addpojo;
    }
    
    
   private boolean vaidatePopFields(String ScreenName) {
	  
	   if(addresstf.getText().toString().isEmpty()) {
		   showAlert(ScreenName ,"Please enter Address");
		   return false;
	   }
	   if(StateCombobox.getSelectionModel().isEmpty()) {
		   showAlert(ScreenName ,"Please select a State ");
		   return false;
	   }
	   if(DistrictCombobox.getSelectionModel().isEmpty()) {
		   showAlert(ScreenName ,"Please select a District ");
		   return false;
	   }
	   if(CountryCombobox.getSelectionModel().isEmpty()) {
		   showAlert(ScreenName,"Please select a Country ");
		   return false;
	   }
	   if(PincodeTF.getText().isEmpty()) {
		   showAlert(ScreenName,"Please enter Postal Code");
		   return false;
	   }
	   return true;
   }
    
    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
