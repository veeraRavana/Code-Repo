package AdminController;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import AdminApplication.CompanyRegisterMain;
import AdminApplicationModel.ADDAddressPOJO;
import AdminApplicationModel.BankdetailsPOJO;
import AdminApplicationModel.Company;
import AdminApplicationModel.CompanyRegisterDataWrapper;
import AdminApplicationModel.CompanyRegisterPOJO;
import AdminServices.CompanyRegisterServices;
import common.BankAccountTablePOJO;
import commonGUI.AlertsandMessages;
import commonGUI.TabManager;
import commonGUI.UserSession;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddorModCompanyRegisterController extends AlertsandMessages {

    private final TabManager tabManager = TabManager.getInstance();
    @FXML private TabPane mainTabPane;
    
    public CompanyRegisterMain compRegMain;   
    CompanyRegisterServices CompRegServ;
    
	CompanyRegisterPOJO comppoj ;
    
    String Nodename		= null;
    int mode			= 0;

    @FXML private TableView<BankAccountTablePOJO> Bankdetailstable;

    @FXML private TableColumn<BankAccountTablePOJO, Integer> bdtsnno;
    @FXML private TableColumn<BankAccountTablePOJO, Integer> bdtBankID;
    @FXML private TableColumn<BankAccountTablePOJO, String> bdtBankname;
    @FXML private TableColumn<BankAccountTablePOJO, String> bdtAccountno;
    @FXML private TableColumn<BankAccountTablePOJO, String> bdtIfscCode;
    @FXML private TableColumn<BankAccountTablePOJO, String> bdtAccountholdername;
    
    public ObservableList<BankAccountTablePOJO> bankTableDataList = FXCollections.observableArrayList();

    @FXML private TextField CompanyNameTF; 
    @FXML private TextField CompanyPhonenoTF; 
    @FXML private TextField CompanyMailaddressTF;
    @FXML private TextField CompanyGSTnoTF;
    @FXML public  TextField CompanyadressTF;
    @FXML public  TextField CompanyOrgin;
    
    @FXML private ComboBox<String> ShipmenttypeComboBox;
    
    ADDAddressPOJO addpojo					= null;// Address pop-up POJO
    BankdetailsPOJO[] Bankdet				= null;// bank Details Pop-up POJO
    CompanyRegisterDataWrapper dataWraper	= null;
    
 // to check the pop up is opened    
    boolean isAddressPopupOpened			= false;
    boolean isBankPopupOpened				= false;
	int Company_ID							= 0;
	
	public void LoadAddorModCompanyRegisterScreen(CompanyRegisterMain companyRegisterMain,
									TabPane TabPane, String nodename,int modes) {
		this.compRegMain		= companyRegisterMain;
		this.mainTabPane		= TabPane;
		this.Nodename			= nodename;
		this.mode 				= modes;
		
		CompanyRegisterDataWrapper DatafromParent;
		if(mode!=0) {
			DatafromParent					= compRegMain.DataWraper;
			List<Company> companies 		= DatafromParent.getCompanyData();
	    	List<ADDAddressPOJO> addresses 	= DatafromParent.getAddressData();
	    	List<BankdetailsPOJO> banks 	= DatafromParent.getBankDetailsData();
	    	
			if(mode==1) {//mode 1 = modification 
				setdataforModView(companies,addresses,banks);
			}else if(mode==2) {//mode 2 = View 
				setdataforModView(companies,addresses,banks);
			}
		}
	
	}
	
	@FXML
	public void initialize() {
		
		CompRegServ			= new CompanyRegisterServices();
		componets();
		istableEdited();
	
	}

	public void setdataforModView(List<Company> companies ,
			List<ADDAddressPOJO> addresses,List<BankdetailsPOJO> banks) {
		setCompanydetails(companies);
		setAddressDetails(addresses);
		SetBankdetails(banks);
	
	}
	
	public void setCompanydetails(List<Company> companies) {

		for (Company company : companies) {
		   Company_ID		= company.getCompanyId();
		    
		   CompanyNameTF.setText(company.getCompanyName().toString()); 
		   CompanyPhonenoTF.setText(company.getPhone().toString());
		   CompanyMailaddressTF.setText(company.getEmail().toString());
		   CompanyGSTnoTF.setText(company.getGst().toString());
		   ShipmenttypeComboBox.setValue(company.getShipment());
		}

	}
	
	public void setAddressDetails(List<ADDAddressPOJO> addresses) {
		
		for(ADDAddressPOJO addaddress : addresses) {
			int addressid 		=	(addaddress.getCompanyAddressID());
			CompanyadressTF.setText(addaddress.getAddress1());	
			CompanyOrgin.setText(addaddress.getCountryorigin());
			addpojo				= addaddress;
		}
	}
	
	public void SetBankdetails(List<BankdetailsPOJO> banks) {
		for(BankdetailsPOJO Banks : banks) {
		
			  BankAccountTablePOJO row	 = new BankAccountTablePOJO(
					  Banks.getBank_ID(),// bank id
					  Banks.getBankName(),// bank name
				      Banks.getAccountNo(),
				      Banks.getHolderName(),
				      Banks.getIfscCode()
				    );
			  bankTableDataList.add(row);
			 
			
		} 

		Bankdetailstable.setItems(bankTableDataList); // refresh
//		setBankData(bankDataList);
	
	}

	public void componets() {
		ShipmenttypeComboBox();//shipment type
		PhonenumberValidation();
		
		setupBankTable();
	}
	
	
	private void PhonenumberValidation() {

		CompanyPhonenoTF.addEventFilter(KeyEvent.KEY_TYPED, event -> {
	            String character 	= event.getCharacter();
	            String currentText 	= CompanyPhonenoTF.getText();
	            // Ignore control keys like Backspace, Delete, Tab, etc.
		        if (character.isEmpty() || character.charAt(0) < 32) {
		            return;
		        }
	            // Allow only digits
	            if (!character.matches("\\d")) {
	                showAlertWarning("Add Company", "Only numeric values are allowed.");
	                event.consume(); // Block character
	                return;
	            }
	          
	            // Limit to 10 digits
	            if (currentText.length() >= 10) {
	                showAlertWarning("Add Company", "Maximum 10 digits allowed.");
	                event.consume(); // Block character
	            }
	        });
	}

	
    private ComboBox ShipmenttypeComboBox(){
    	if(ShipmenttypeComboBox != null) {
    		 // Populate ComboBox with options
    		ShipmenttypeComboBox.setItems(FXCollections.observableArrayList("Transport", "Ship", "Airline", "Courier"));
    	}
 		return ShipmenttypeComboBox; 	
    }
    
    public void SubmitAction() {
    	PrepareData();
    	if(ValidateFields()) {
    	 	if (JOptionPane.showConfirmDialog(null, "Do you want to Submit the Company Details",
    	 						"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
    			
    				if(mode==0) {// add transaction
    					Company_ID			= 0;
    					Company_ID			= CompRegServ.saveInCompanyRegister_Tables(Company_ID,comppoj,addpojo,Bankdet);
   					
    				}
    				else if(mode==1) {// mod transaction
    					Company_ID			= CompRegServ.ModifyCompanyRegister_Tables(Company_ID,comppoj,addpojo,Bankdet);
    				}
    			String Message			= mode == 0 ? "Created" : "Modified";
    			if (Company_ID!=0)	{
    				comppoj.companyId	= Company_ID; // this is to get the company ID for modification
    				CompRegServ.logCompanyRegisterTransaction(Company_ID,comppoj,addpojo,Bankdet);
    				
    				
    				JOptionPane.showMessageDialog(null, "Company ID: "+Company_ID+" has been "+Message);
    				CancelAction();
    			} else {
    				JOptionPane.showMessageDialog(null, "Error occured while saving.Company ID not "+Message);
    				return;
    			}
    		}
    	}
    	
    }
    
    
    public boolean ValidateFields() {
    	
    	if(CompanyNameTF.getText().toString().isEmpty()) {
    		showAlertWarning("Add Company", "Please enter the Company Name");
    		return false;
    	}
    	if(CompanyPhonenoTF.getText().toString().isEmpty()) {
    		showAlertWarning("Add Company", "Please enter the Company Phone Number");
    		return false;
    	}
    	if(CompanyMailaddressTF.getText().toString().isEmpty()) {
    		showAlertWarning("Add Company", "Please enter the Company Mail id");
    		return false;
    	}
    	if(CompanyGSTnoTF.getText().toString().isEmpty()) {
    		showAlertWarning("Add Company", "Please enter the Company GST no");
    		return false;
    	}

    	if (!CompanyGSTnoTF.getText().toString().matches("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")) {
    	    showAlertWarning("Invalid GST Number", "Please enter a valid GST number.");
    	    return false;
    	}

    	if(CompanyadressTF.getText().toString().isEmpty()) {
    		showAlertWarning("Add Company", "Please enter the Company Address");
    		return false;
    	}
    	
    	
    	return true;
    }
    
    private void PrepareData() {
    	// Prepare Company Register POJO  
    	comppoj 				= new CompanyRegisterPOJO();

    	comppoj.companyId		= Company_ID; // this is to get the company ID for modification
    	comppoj.companyName		= CompanyNameTF.getText().trim();
    	comppoj.phoneNumber		= CompanyPhonenoTF.getText().trim();
    	comppoj.gst				= CompanyGSTnoTF.getText().trim();
    	comppoj.Emailaddress	= CompanyMailaddressTF.getText().trim();
    	comppoj.ShipmentType	= ShipmenttypeComboBox.getValue();
    	comppoj.TransactionName = mode == 1 ? "Mod Company": "Add Company";
    	
//    		InetAddress localHost 	= InetAddress.getLocalHost();    --		String ipOnly   		= localHost.getHostAddress(); 
    	
    	comppoj.userIPAddress	= UserSession.getInstance().getUserIp(); // this is to get the IP address of the local machine
    	comppoj.UserName		= UserSession.getInstance().getUsername();
    	
    	prepareBankDetailsdata();

    }
    
    
/*this is to prepare the data while in the main
 *  add or modify Screen,for the action for the scenario
 *  we can add the bank details in the pop up and
 *   then we can also delete the bank details in the add-mod screen*/
	private void prepareBankDetailsdata() {
		Bankdet								= new BankdetailsPOJO[bankTableDataList.size()];
	
		for(int i=0;i<bankTableDataList.size();i++) {
			
			BankAccountTablePOJO temPojo	= bankTableDataList.get(i);
			Bankdet[i]						= new BankdetailsPOJO();
			Bankdet[i].serialno				= i+1;
			Bankdet[i].accountNo 			= temPojo.accountNo; //Acc No and IBAN
			Bankdet[i].bank_ID				= temPojo.bankid; // bank id
			Bankdet[i].bankName				= temPojo.bankName;
			Bankdet[i].accountholderName	= temPojo.holderName;
			Bankdet[i].ifscCode				= temPojo.ifscCode;// ifsc and SWIFT code
			
		}
	
}
    
	public void CancelAction() {

        if (mainTabPane != null) {
        	
            Tab currentTab = mainTabPane.getSelectionModel().getSelectedItem();
            if (currentTab != null) {
            	
                mainTabPane.getTabs().remove(currentTab);
                compRegMain.loadCompanyRegister(Nodename, mainTabPane);
            }
        } else {
            System.out.println("mainTabPane is NULL! Ensure it's correctly set in FXML.");
        }
    
	}

/****************************************
 * Button Actions START
 ****************************************/
	
	public void addAddress() {
	    try {
	    	if(!CompanyNameTF.getText().toString().isEmpty()) {
	    		String fxmlPath		= "/AdminFxml/addAddressPopup.fxml";
		        FXMLLoader loader 	= new FXMLLoader(getClass().getResource(fxmlPath));
		        Parent root 		= loader.load();
					if(addpojo != null) {
						isAddressPopupOpened				= true;
					}
		        AddAddresspopupController popupController 	= loader.getController();
		        popupController.setComponentsinPopup(this,isAddressPopupOpened,mode); //<- Pass reference to parent

		        Stage popupStage 	= new Stage();
		        popupStage.setTitle("Address Pop-up");
		        popupStage.initModality(Modality.APPLICATION_MODAL);
		        popupStage.setScene(new Scene(root));
		        popupStage.showAndWait();
	    	}else {
	    		   showAlert("Add Company ", "Please Enter the Company name");
	               return;
	    	}


	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
/*	public void AddBankaccount() {
		  try {
		    	if(!CompanyNameTF.getText().toString().isEmpty()) {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFxml/AddBankAccountPopup.fxml"));
			        Parent root = loader.load();

			        AddbankAccountController popupController = loader.getController();
			        popupController.setComponentsinPopup(this); // 👈 Pass reference to parent

			        Stage popupStage = new Stage();
			        popupStage.setTitle("Select User");
			        popupStage.initModality(Modality.APPLICATION_MODAL);
			        popupStage.setScene(new Scene(root));
			        popupStage.showAndWait();
		    	}else {
		    		   showAlert("Add Company ", "Please Enter the Company name");
		               return;
		    	}


		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}*/
	
	
	@FXML	
	private void AddBankaccount() {
		AddBankaccountOperation(false,row);
	}
	
	public void AddBankaccountOperation(boolean isedited ,TableRow <BankAccountTablePOJO> row ) {
	    try {
	        if (CompanyNameTF.getText().isEmpty()) {
	            showAlert("Add Company", "Please Enter the Company name");
	            return;
	        }

	        FXMLLoader loader 		= new FXMLLoader(getClass().getResource("/AdminFxml/AddBankAccountPopup.fxml"));
	        Parent root 			= loader.load();
	        AddbankAccountController popupController = loader.getController();
            popupController.setComponentsinPopup(this,isBankPopupOpened,mode);
	 
            if(isedited== true) {
            	BankAccountTablePOJO selected = row.getItem();
                popupController.editingRowIndex 		 = row.getIndex(); // set index for update
                popupController.loadRowToFields(selected);
            }
	        // Pass existing data
//	        popupController.setExistingBankData(bankDataList);

	        Stage popupStage 			= new Stage();
	        popupStage.setTitle("Bank Account Details");
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setScene(new Scene(root));
	        popupStage.showAndWait();
	        Bankdetailstable.setItems(bankTableDataList);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
    TableRow<BankAccountTablePOJO> row 		= new TableRow<>();
	private void istableEdited() {
	    // Set double-click to edit
		Bankdetailstable.setRowFactory(tv -> {
			row 		= new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                	AddBankaccountOperation(true,row);
                }
            });
            return row;
        });
	}
	
	
	public void DeleteAccount() {
		
	}
	
	public void setBankData(ObservableList<BankAccountTablePOJO> data) {
	    if (bankTableDataList == null) {
	    	bankTableDataList = FXCollections.observableArrayList();
	    }

	    bankTableDataList.setAll(data); // set or update the list
		    if (Bankdetailstable != null) {
		    	Bankdetailstable.setItems(bankTableDataList); // refresh
		    }

	    System.out.println("Received bank data: " + data.size() + " records");
	    isBankPopupOpened	= true;
	}

	private void setupBankTable() {
	    bdtsnno.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(Bankdetailstable.getItems().indexOf(cellData.getValue()) + 1));
	    bdtBankname.setCellValueFactory(new PropertyValueFactory<>("bankName"));
	    bdtAccountno.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
	    bdtIfscCode.setCellValueFactory(new PropertyValueFactory<>("ifscCode"));
	    bdtAccountholdername.setCellValueFactory(new PropertyValueFactory<>("holderName"));

	    Bankdetailstable.setItems(bankTableDataList);
	}

	
/****************************************
 * Button Actions START
 ****************************************/	
	
}
