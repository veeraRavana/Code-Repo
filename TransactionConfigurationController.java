package AdminController;

import javax.swing.JOptionPane;

import AdminApplication.TransactionConfigurationMain;
import AdminApplicationModel.SalesTypeTable;
import AdminApplicationModel.TransactionConfigurationTypePOJO;
import AdminApplicationModel.Company;
import AdminApplicationModel.PaymentTypeTable;
import AdminServices.TransactionConfigurationServices;
import commonGUI.AlertsandMessages;
import commonGUI.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class TransactionConfigurationController extends AlertsandMessages {

	 @FXML private TabPane mainTabPane; // TabPane for managing tabs
	public TransactionConfigurationMain transactionConfigurationMain;
	public TransactionConfigurationServices transConfigServ;
	
	TransactionConfigurationTypePOJO transConfigTypePOJO ;
	
	@FXML public Button AddSalesTypeBtn;
	@FXML public Button ModsalesTypeBtn;
	@FXML public Button DeleteSalesTypeBtn;
	
	@FXML public Button AddPaymentTypeBtn;
	@FXML public Button ModPaymentTypeBtn;
	@FXML public Button DelPaymentTypeBtn;
	

	
	@FXML private  TableView<SalesTypeTable> SalesTypeTable;
    @FXML private TableColumn<SalesTypeTable, Integer> ColSalesTypeID;
    @FXML private TableColumn<SalesTypeTable, String> ColSalesTypename;
    @FXML private TableColumn<SalesTypeTable, String> ColSalesTypeDescription;
    @FXML private TableColumn<SalesTypeTable, String> ColSalesTypeStatus;
    private ObservableList<SalesTypeTable> SalesTypeTableData 		= FXCollections.observableArrayList();
    
    @FXML private  TableView<PaymentTypeTable> PaymentTypeTable;
    @FXML private TableColumn<PaymentTypeTable, Integer> ColPaymentTypeID;
    @FXML private TableColumn<PaymentTypeTable, String> ColPaymentTypename;
    @FXML private TableColumn<PaymentTypeTable, String> ColPaymentTypeDescription;
    @FXML private TableColumn<PaymentTypeTable, String> ColPaymentTypeStatus;
    
    private ObservableList<PaymentTypeTable> PaymentTypeTableData 	= FXCollections.observableArrayList();
	
	public void setTransactionConfigurationMain(TransactionConfigurationMain transactionConfigurationMain) {
		this.transactionConfigurationMain 							= transactionConfigurationMain;
	}
	
	@FXML
	public void initialize() {
		transConfigServ 		= new TransactionConfigurationServices();
		SetColumnname();
		loadTables();
	}
	
	private void SetColumnname() {
		SetSalesTypeColumnData();
		SetPaymentTypeColumnData();
	}
	private void loadTables() {
		loadSalesTypeTable();
		loadPaymentTypeTable();
	}

	private void SetSalesTypeColumnData() {
			ColSalesTypeID.setCellValueFactory(new PropertyValueFactory<>("salesTypeId"));
			ColSalesTypename.setCellValueFactory(new PropertyValueFactory<>("salesTypeName"));
			ColPaymentTypeDescription.setCellValueFactory(new PropertyValueFactory<>("salesTypeDescription"));
			ColSalesTypeStatus.setCellValueFactory(new PropertyValueFactory<>("salesTypeStatus"));
	}
	private void SetPaymentTypeColumnData() {
		 	ColPaymentTypeID.setCellValueFactory(new PropertyValueFactory<>("paymentTypeId"));
			ColPaymentTypename.setCellValueFactory(new PropertyValueFactory<>("paymentTypeName"));
			ColPaymentTypeDescription.setCellValueFactory(new PropertyValueFactory<>("paymentTypeDescription"));
			ColPaymentTypeStatus.setCellValueFactory(new PropertyValueFactory<>("paymentTypeStatus"));
	}
	
	private void loadSalesTypeTable() {

		SalesTypeTableData		= FXCollections.observableArrayList(
										transConfigServ.GetSalesTypeTableData());
		SalesTypeTable.setItems(SalesTypeTableData);
 		SalesTypeTable.refresh();
	}
	
	private void loadPaymentTypeTable() {
		PaymentTypeTableData	= FXCollections.observableArrayList(
										transConfigServ.GetPaymentTypeTableData());
		PaymentTypeTable.setItems(PaymentTypeTableData);
 		PaymentTypeTable.refresh();
	}

	public void AddConfigurationType() {
	
		AddSalesTypeBtn.setOnAction(e -> {
			String ScreenName			= "Sales Type";
			transactionConfigurationMain.AddConfigurationType( ScreenName);
		});
		
		AddPaymentTypeBtn.setOnAction(e -> {
			String ScreenName			= "Payment Type";
			transactionConfigurationMain.AddConfigurationType( ScreenName);
		});

	}
	
	public void ModConfigurationType() {
		
		int Mode		= 1; // Set mode to 1 for modification
		ModsalesTypeBtn.setOnAction(e -> {
			String ScreenName 			= "Sales Type";
			handelmodification(ScreenName);
//			transactionConfigurationMain.ModConfigurationType( ScreenName,Mode,0);
		});
		
		ModPaymentTypeBtn.setOnAction(e -> {
			String ScreenName 			= "Payment Type";
			handelmodification(ScreenName);
//			transactionConfigurationMain.ModConfigurationType( ScreenName,Mode,0); 
		});

	}
	public void DeleteConfigurationType() {
		
		DeleteSalesTypeBtn.setOnAction(e -> {
			String ScreenName 			= "Sales Type";
			deleteConfigurationType(ScreenName);
		});
		
		DelPaymentTypeBtn.setOnAction(e -> {
			String ScreenName 			= "Payment Type";
			deleteConfigurationType(ScreenName);
		});

	}
	
	public void handelmodification(String ScreenName) {
		int Mode 				= 1; // Set mode to 1 for modification
		int TransactionID		= 0;
		TransactionID			= prepareDataForModificationAndDelete(Mode,ScreenName);
// If we reach here, it means a valid selection was made
		transactionConfigurationMain.ModConfigurationType(ScreenName,Mode,TransactionID,transConfigTypePOJO);
	  		
	}
	
	public void deleteConfigurationType(String ScreenName) {
		int Mode 				= 2; // Set mode to 1 for modification
		int TransactionID		= 0;
		TransactionID			= prepareDataForModificationAndDelete( Mode, ScreenName);
		 
		if (JOptionPane.showConfirmDialog(null, "Do you want to Delete the selected " + ScreenName,
				"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//			
			TransactionID		=	transConfigServ.deleteTransactionConfiguration( TransactionID,  ScreenName);
			showAlert(ScreenName , ScreenName+" ID : "+TransactionID+" has been deleted ");

//log the transaction configuration type
			 transConfigServ.TransactionConfiguration_Log(TransactionID, transConfigTypePOJO, 2, ScreenName);
			 refreshTables();
//			transactionConfigurationMain.loadTransactionConfigurationMain(transactionConfigurationMain.Nodename, mainTabPane);
		}
	}
	
	private void refreshTables() {
		SetColumnname();
		loadTables();
	}
	
	private boolean Validate_TransactionConfigurationType(TableView<SalesTypeTable> salesTypeTable,
												TableView<PaymentTypeTable> paymentTypeTable,
												String ScreenName) {
    	
			if(salesTypeTable == null && paymentTypeTable == null) {
				showAlert(ScreenName, "Please select a "+ScreenName+" to modify.");
		  		return false;
		  	}
			return true;
		}
	
	
	private Integer prepareDataForModificationAndDelete(int Mode, String ScreenName) {
		
		int TransactionID						= 0;
		
		SalesTypeTable selectedSalesType 		= SalesTypeTable.getSelectionModel().getSelectedItem() 
														== null ? null : SalesTypeTable.getSelectionModel().getSelectedItem();
		PaymentTypeTable selectedPaymentType 	= PaymentTypeTable.getSelectionModel().getSelectedItem() 
														== null ? null : PaymentTypeTable.getSelectionModel().getSelectedItem();
		
			if(!Validate_TransactionConfigurationType(SalesTypeTable, PaymentTypeTable, ScreenName)) {
				return 0; // If validation fails, exit the method
			}
		
					if(selectedSalesType != null) {
						TransactionID 			= selectedSalesType.getSalesTypeId();
					
					} else if(selectedPaymentType != null) {
						TransactionID 			= selectedPaymentType.getPaymentTypeId();
					
					}
		transConfigTypePOJO 		= new TransactionConfigurationTypePOJO();
		String ModeName 			= Mode == 1 ? "Modify" : "Delete"; // Determine the mode for logging
		if(selectedSalesType != null) {
//			SalesTypeTable selectedSalesType = selectedSalesType.getSelectionModel().getSelectedItem();
		/*	transConfigTypePOJO.setTransactionId(selectedSalesType.getSalesTypeId());
			transConfigTypePOJO.setTransactionTypeName(selectedSalesType.getSalesTypeName());
			transConfigTypePOJO.setTransactionDescription(selectedSalesType.getSalesTypeDescription());
			transConfigTypePOJO.setTransactionStatus(selectedSalesType.getSalesTypeStatus().equals("Active"));*/
			
			transConfigTypePOJO.setTransactionId(selectedSalesType.getSalesTypeId());
			transConfigTypePOJO.setTransactionType(ScreenName); 							// Set the transaction type , e.g., "Sales Type" or "Payment Type"
			transConfigTypePOJO.setTransactionTypeName(selectedSalesType.getSalesTypeName());	// eg :- payment type cash, credit card, etc./ sales type garments sales, electronics, etc.
			transConfigTypePOJO.setTransactionDescription(selectedSalesType.getSalesTypeDescription());
			transConfigTypePOJO.setTransactionStatus(selectedSalesType.getSalesTypeStatus().equals("Active") ? true : false); // Set status based on checkbox active or inactive
			transConfigTypePOJO.setTransactionMode(ModeName+""+ScreenName); 					// Set the transaction mode string eg "Add Sales Type" or "Modify Payment Type"
// Additional fields for user tracking can be set here if needed
			 transConfigTypePOJO.setUserName(UserSession.getInstance().getUsername()); 		// Get the username from the session
			 transConfigTypePOJO.setUserIPAddress(UserSession.getInstance().getUserIp()); 	// Get the user IP address from the session
			
		} else if(selectedPaymentType != null) {
//			PaymentTypeTable selectedPaymentType = paymentTypeTable.getSelectionModel().getSelectedItem();
/*			transConfigTypePOJO.setTransactionId(selectedPaymentType.getPaymentTypeId());
			transConfigTypePOJO.setTransactionTypeName(selectedPaymentType.getPaymentTypeName());
			transConfigTypePOJO.setTransactionDescription(selectedPaymentType.getPaymentTypeDescription());
			transConfigTypePOJO.setTransactionStatus(selectedPaymentType.getPaymentTypeStatus().equals("Active"));
			transConfigTypePOJO.setTransactionMode(ModeName+""+ScreenName); 					// Set the transaction mode string eg "Add Sales Type" or "Modify Payment Type"
*/
			transConfigTypePOJO.setTransactionId(selectedPaymentType.getPaymentTypeId());
			transConfigTypePOJO.setTransactionType(ScreenName); 							// Set the transaction type , e.g., "Sales Type" or "Payment Type"
			transConfigTypePOJO.setTransactionTypeName(selectedPaymentType.getPaymentTypeName());	// eg :- payment type cash, credit card, etc./ sales type garments sales, electronics, etc.
			transConfigTypePOJO.setTransactionDescription(selectedPaymentType.getPaymentTypeDescription());
			transConfigTypePOJO.setTransactionStatus(selectedPaymentType.getPaymentTypeStatus().equals("Active") ? true : false); // Set status based on checkbox active or inactive
			transConfigTypePOJO.setTransactionMode(ModeName+""+ScreenName); 					// Set the transaction mode string eg "Add Sales Type" or "Modify Payment Type"
	
			// Additional fields for user tracking can be set here if needed
			 transConfigTypePOJO.setUserName(UserSession.getInstance().getUsername()); 		// Get the username from the session
			 transConfigTypePOJO.setUserIPAddress(UserSession.getInstance().getUserIp()); 	// Get the user IP address from the session
		}	
		return TransactionID;
		
	}
	

	
	

		
	
	
}
