package AdminController;

import java.util.List;

import javax.swing.JOptionPane;

import AdminApplication.CompanyRegisterMain;
import AdminApplicationModel.ADDAddressPOJO;
import AdminApplicationModel.BankdetailsPOJO;
import AdminApplicationModel.Company;
import AdminApplicationModel.CompanyRegisterDataWrapper;
import AdminApplicationModel.CompanyRegisterPOJO;
import AdminServices.CompanyRegisterServices;
import commonGUI.AlertsandMessages;
import commonGUI.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompanyRegistermainController extends AlertsandMessages {

	 @FXML private TabPane mainTabPane; // TabPane for managing tabs
	 public CompanyRegisterMain compRegMain;
	 CompanyRegisterServices CompRegServ;
	
    @FXML private TableView<Company> companyTable;
    @FXML private TableColumn<Company, Integer> colCompanyId;
    @FXML private TableColumn<Company, String> colCompanyName;
    @FXML private TableColumn<Company, String> colAddress;
    @FXML private TableColumn<Company, String> colPhone;
    @FXML private TableColumn<Company, String> colGST;
    @FXML private TableColumn<Company, String> colNationality;

//    private ObservableList<Company> companyList = FXCollections.observableArrayList();
   
    CompanyRegisterPOJO Comppoj ; 
    ADDAddressPOJO Addpojo					= null;// Address pop-up POJO
    BankdetailsPOJO[] Bankdet				= null;// bank Details Pop-up POJO
    
    CompanyRegisterDataWrapper dataWraper;
    
	public void setCompanyRegisterMain(CompanyRegisterMain compRegMain) {
		this.compRegMain 	=	compRegMain;
	}
    
    @FXML
    public void initialize() {
    	CompRegServ			= new CompanyRegisterServices();
    	SetColumnname();
    	loadCompanyRegisterTable();
    }

    
    private void SetColumnname() {
    	  colCompanyId.setCellValueFactory(new PropertyValueFactory<>("companyId"));
          colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
          colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
          colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
          colGST.setCellValueFactory(new PropertyValueFactory<>("gst"));
          colNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
           
    }
    private void loadCompanyRegisterTable(){
    	List <Company> companyregData 	= CompRegServ.GetCompanyRegisterTableData() ;
        ObservableList<Company> data 	= FXCollections.observableArrayList(companyregData);
        companyTable.setItems(data);
    			
    }
   
  // operation is ok   
    public void handleAddCompany() {
    	compRegMain.addCompanyRegister();

    }

    public void handleModifyCompany() {
    	Company selected 		= companyTable.getSelectionModel().getSelectedItem();
    	int selectedcompanyID	= selected.getCompanyId();// this get the selected Company ID
    	if(!ValidateSelectedCompany(selected)) {
    		return;
    	}
   /*this is to prepare the data for the
    *  Modify and view screen */ 
    	dataWraper  			= CompRegServ.getdatatoSetCompanyRegModandView(selectedcompanyID);
    	//mode : 1
    	compRegMain.modViewCompanyRegister(1, dataWraper);
    }
    
    public void handleDeleteCompany() {
    	Company selected 		= companyTable.getSelectionModel().getSelectedItem();
    	int selectedcompanyID	= selected.getCompanyId();// this get the selected Company ID
    	if(!ValidateSelectedCompany(selected)) {
    		return;
    	}   
		// This is to prepare the data for the delete company   
    	prepareDataforDeleteCompany(selectedcompanyID);
    	
    	if (JOptionPane.showConfirmDialog(null, "Do you want to Delete the selected Company Details",
				"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
    		
    		//this is to delete the company register details			
    		selectedcompanyID	= CompRegServ.deleteCompanyRegisterDetails(selectedcompanyID);
    		
			if (selectedcompanyID!=0)	{
				// this is to log the company register transaction
				CompRegServ.logCompanyRegisterTransaction(selectedcompanyID,Comppoj,Addpojo,Bankdet);
				JOptionPane.showMessageDialog(null, "Company ID: "+selectedcompanyID+" has been Deleted");
		    	SetColumnname();
		    	loadCompanyRegisterTable();
			} else {
				JOptionPane.showMessageDialog(null, "Error occured while Deleting...!.Company ID "+selectedcompanyID+" not created");
				return;
			}
    	}
    	
    }
    
    public void prepareDataforDeleteCompany(int selectedcompanyID) {
    	dataWraper  					= CompRegServ.getdatatoSetCompanyRegModandView(selectedcompanyID);
		List<Company> companies 		= dataWraper.getCompanyData();
    	List<ADDAddressPOJO> addresses 	= dataWraper.getAddressData();
    	List<BankdetailsPOJO> banks 	= dataWraper.getBankDetailsData();

		setCompanydetails(companies);
		setAddressDetails(addresses);
		SetBankdetails(banks);

    }
    
    public void setCompanydetails(List<Company> companies) {
    	CompanyRegisterPOJO	comppoj = new CompanyRegisterPOJO();
  	
		comppoj.companyId		= companies.get(0).getCompanyId();
		comppoj.companyName		= companies.get(0).getCompanyName().trim();
		comppoj.phoneNumber		= companies.get(0).getPhone().trim();
		comppoj.gst				= companies.get(0).getGst().trim();
		comppoj.Emailaddress	= companies.get(0).getEmail().trim();
		comppoj.ShipmentType	= companies.get(0).getShipment().trim();
		comppoj.TransactionName = "Del Company";
		
		comppoj.userIPAddress	= UserSession.getInstance().getUserIp(); // this is to get the IP address of the local machine
		comppoj.UserName		= UserSession.getInstance().getUsername();

		Comppoj					= comppoj;
	}
    
    public void setAddressDetails(List<ADDAddressPOJO> addresses) {
    	  
    	ADDAddressPOJO addpojo	= new ADDAddressPOJO();
    	
    	addpojo.CompanyID		= addresses.get(0).getCompanyID(); // this is to get the company ID	
    	addpojo.companyAddressID= addresses.get(0).getCompanyAddressID(); // this is to get the address ID
    	
    	addpojo.Address1		= addresses.get(0).getAddress1().trim();
    	addpojo.Address2		= addresses.get(0).getAddress2().trim();

    	addpojo.stateID 		= addresses.get(0).getStateID();     	 // the numeric ID
        addpojo.State   		= addresses.get(0).State.toString();     // the display name

        addpojo.DistrictID 		= addresses.get(0).getDistrictID();
        addpojo.District  		= addresses.get(0).getDistrict().toString();

        addpojo.CountryID 		= addresses.get(0).getCountryID();
        addpojo.Countryorigin  	= addresses.get(0).getCountryorigin().toString();

    	addpojo.isHeadOffice	= addresses.get(0).isHeadOffice();
    	addpojo.Postalcode		= Integer.parseInt(addresses.get(0).getPostalcode().toString());
    	
    	Addpojo					= addpojo;
	}
    
    public void SetBankdetails(List<BankdetailsPOJO> banks) {
		BankdetailsPOJO[] bankdet			= new BankdetailsPOJO[banks.size()];
		
		for(int i=0;i<banks.size();i++) {
			
			BankdetailsPOJO temPojo			= banks.get(i);
			bankdet[i]						= new BankdetailsPOJO();
			bankdet[i].serialno				= i+1;
			bankdet[i].accountNo 			= temPojo.accountNo; //Acc No and IBAN
			bankdet[i].bank_ID				= temPojo.bank_ID; // bank id
			bankdet[i].bankName				= temPojo.bankName;
			bankdet[i].accountholderName	= temPojo.accountholderName;
			bankdet[i].ifscCode				= temPojo.ifscCode;// ifsc and SWIFT code
			
		}
		Bankdet								= bankdet;	
		
	}
    
    private boolean ValidateSelectedCompany(Company selected) {
    	if(selected	== null) {
    		showAlertWarning("Company Register", "Please select a company");
    		return false ;
    	}
    	int selectedcompanyID	= selected.getCompanyId();// this get the selected Company ID
//    	CompRegServ.getdatatoValidate();
    	// validate if the company is used in the billing or invoice with the register invoice 
    	return true;
    	
    }
  
}

