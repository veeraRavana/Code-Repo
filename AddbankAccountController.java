package AdminController;

import AdminApplicationModel.BankdetailsPOJO;
import AdminServices.CompanyRegisterServices;
import common.BankAccountTablePOJO;
import common.BankNames;
import commonGUI.AlertsandMessages;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class AddbankAccountController extends AlertsandMessages{

	
	AddorModCompanyRegisterController addcompreg;
    CompanyRegisterServices CompRegServ;

    @FXML private CheckBox IsinternationalbankCB;

    @FXML private Label accountNoLB;
    @FXML private Label ifscCodeLB;

    @FXML private TextField holderNameTF;
    @FXML private TextField accountNoTF;
    @FXML private TextField ifscCodeTF;

    @FXML private ComboBox<BankNames> bankNameComboBox;

    @FXML private Button addDetailsBtn;
    @FXML private Button deleteRowBtn;
    @FXML private Button SubmitBtn;

    @FXML private TableView<BankAccountTablePOJO> bankTableView;
    @FXML private TableColumn<BankAccountTablePOJO, String> snCol;
    @FXML private TableColumn<BankAccountTablePOJO, Integer> bankIDcol;
    @FXML private TableColumn<BankAccountTablePOJO, String> bankNameCol;
    @FXML private TableColumn<BankAccountTablePOJO, String> accountNoCol;
    @FXML private TableColumn<BankAccountTablePOJO, String> holderNameCol;
    @FXML private TableColumn<BankAccountTablePOJO, String> ifscCodeCol;

	private ObservableList<BankAccountTablePOJO> bankTableDataList = FXCollections.observableArrayList();
	
    BankdetailsPOJO[] BankdetialPOJO	= null;
    
    int editingRowIndex 		= -1; // for tracking if we are editing
    boolean isPopupOpened		= false;
    int mode					= 0;
      
    public void setComponentsinPopup(AddorModCompanyRegisterController addCompRegController,
    		boolean isPopupOpened,int Mode ) {
		
    	this.addcompreg			= addCompRegController;
    	this.mode				= Mode;
		// if this is true then we have to reload the date 
		if(mode !=0 || isPopupOpened== true) {
//			bankData			= addcompreg.bankDataList;
			this.bankTableDataList 		= deepCopyBankData(addcompreg.bankTableDataList);

			this.isPopupOpened	= isPopupOpened;
			SetBankDetailsTable();
			if(mode==2) {
				
			}
		}

	}
   
    public void DisableEditing() {
    	IsinternationalbankCB.setDisable(true);
    	holderNameTF.setEditable(false);
    	accountNoTF.setEditable(false);
    	ifscCodeTF.setEditable(false);
    	bankNameComboBox.setEditable(false);

    	bankTableView.setEditable(false);    	
    
    	addDetailsBtn.setVisible(false);
    	deleteRowBtn.setVisible(false);
    	
    	SubmitBtn.setVisible(false);
    }
    
	@FXML
	public void initialize() {
		CompRegServ			= new CompanyRegisterServices();
		isIsinternationalbankCB();
        loadBankNames(false);
      
        SetBankDetailsTable();
        istableEdited();
    
        
	}
	
/**********************************************/
/*this is to set the table and the data */
	private void SetBankDetailsTable() {
		  // Dynamic S.No. binding
        snCol.setCellValueFactory(cellData -> {
            int index = bankTableView.getItems().indexOf(cellData.getValue()) + 1;
            return new ReadOnlyObjectWrapper<>(String.valueOf(index));
        });

        bankNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBankName()));
        accountNoCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccountNo()));
        holderNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHolderName()));
        ifscCodeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIfscCode()));
        bankTableView.setItems(bankTableDataList);
             
	}

	
/*This istableEdited method is an action method
 *  that would display the each data in their
 *   respective Fields  */	
	private void istableEdited() {
	    // Set double-click to edit
        bankTableView.setRowFactory(tv -> {
            TableRow<BankAccountTablePOJO> row 		= new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                	BankAccountTablePOJO selected 	= row.getItem();
                    editingRowIndex 				= row.getIndex(); // set index for update
                    loadRowToFields(selected);

                }
            });
            return row;
        });
	}
	
	 public void loadRowToFields(BankAccountTablePOJO row) {
     
	       for (BankNames bank : bankNameComboBox.getItems()) {
	           if (bank.getBankName().equals(row.getBankName())) {
	               bankNameComboBox.setValue(bank);
	               break;
	           }
	       }
	       accountNoTF.setText(row.getAccountNo());
	       holderNameTF.setText(row.getHolderName());
	       ifscCodeTF.setText(row.getIfscCode());
	   }
	
	private ObservableList<BankAccountTablePOJO> bankAccounts = FXCollections.observableArrayList();
	
		public void setExistingBankData(ObservableList<BankAccountTablePOJO> existingData) {
		    if (existingData != null) {
		        bankAccounts.setAll(existingData);
		        bankTableView.setItems(bankAccounts); // assuming this is your table
		    }
		}


		
		
/*this is the IsinternationalbankCB check box 
 * that would be checked if the client has the 
 * International Bank account */		
	private void isIsinternationalbankCB() {
		IsinternationalbankCB.setOnAction(e->{
			if(IsinternationalbankCB.isSelected()) {
				accountNoLB.setText("IBAN");
				accountNoTF.setPromptText("International Bank Account Number");

				ifscCodeLB.setText("SWIFT/BIC Code");
				accountNoTF.setText(" ");
				ifscCodeTF.setText(" ");
				loadBankNames(true);
				
			}else {
				accountNoLB.setText("Account No");
				accountNoTF.setPromptText("Account No");
				
				ifscCodeLB.setText("IFSC Code");
				accountNoTF.setText(" ");
				ifscCodeTF.setText(" ");
				loadBankNames(false);

			}
		});
		
	}

	/*this loadBankNames method loads the data
 *  from the data base to the combo box the
 *   boolean would be enabled if the 
 *   IsInternationalbank check box is Selected */
	private void loadBankNames(Boolean IsInternationalbank) {
		bankNameComboBox.setItems(FXCollections.observableArrayList(CompRegServ.getAllBankNames(IsInternationalbank)));
	}
	
	
	
	
	
/*******************************
 * Button Actions and Controls
 *******************************/	
	@FXML
	private void AddDetailstoTable() {

	    String AccholderName 		= holderNameTF.getText().trim();
	    String accountno 			= accountNoTF.getText().trim();
	    String ifsc 				= ifscCodeTF.getText().trim();
	    BankNames selectedBank 		= bankNameComboBox.getValue();
	 
	    boolean isDuplicate = bankTableDataList.stream().anyMatch(item ->
		    item.getAccountNo().equals(accountno) &&
		    item.getBankName().equals(selectedBank.getBankName())
	    );

		if (isDuplicate && editingRowIndex == -1) {
		    showAlertWarning("Duplicate Entry", "This bank account already exists.");
		    return;
		}
		
		if(editingRowIndex	!=	-1) {
			bankTableDataList.remove(editingRowIndex);
		}
		
	  if(ValidateFields()== true) {
		  BankAccountTablePOJO row 	= new BankAccountTablePOJO(
				  	selectedBank.getBankId(),// bank id
			        selectedBank.getBankName(),// bank name
			        accountno,
			        AccholderName,
			        ifsc
			    );
		  // this is to replace the same index with new one
				if(editingRowIndex	!=	-1) {
					bankTableDataList.add(editingRowIndex,row);
				}else {
					bankTableDataList.add(row);
				}
			    
			    clearFields();
	  }

	}
	
	
	
	private void clearFields() {
		// Clear fields except holderName
	    accountNoTF.clear();
	    ifscCodeTF.clear();
	    bankNameComboBox.getSelectionModel().clearSelection();
	    editingRowIndex = -1; // Reset editing state
	}

	private boolean ValidateFields() {
		   String holder 			= holderNameTF.getText().trim();
		    String account 			= accountNoTF.getText().trim();
		    String ifsc 			= ifscCodeTF.getText().trim();
		    BankNames selectedBank 	= bankNameComboBox.getValue();

		    if (holder.isEmpty() || account.isEmpty() || ifsc.isEmpty() || selectedBank == null) {
		    	showAlertWarning("Missing Fields", "Please fill all required fields.");
		        return false;
		    }
		
		    if (IsinternationalbankCB.isSelected()) {
		        if (!ifsc.matches("[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?")) {
		            showAlertWarning("Invalid SWIFT Code", "Please enter a valid SWIFT/BIC code.");
		            return false;
		        }
		    } else {
		        if (!ifsc.matches("^[A-Z]{4}0[A-Z0-9]{6}$")) {
		            showAlertWarning("Invalid IFSC Code", "Please enter a valid IFSC code.");
		            return false;
		        }
		    }

		return true;
		
	}

	
	@FXML
	private void deleteSelectedRow() {
		BankAccountTablePOJO selected = bankTableView.getSelectionModel().getSelectedItem();
	    if (selected != null) {
	    	bankTableDataList.remove(selected);
	        bankTableView.refresh();  // Refresh to update SN numbers
	    } else {
	    	showAlertWarning("No Selection", "Please select a row to delete.");
	    }
	}


	@FXML
	  private void handleSubmit() {
		
		if(bankTableDataList.size()>0) {
			preparedata();
		    addcompreg.setBankData(FXCollections.observableArrayList(bankTableDataList));
		    Stage stage = (Stage) addDetailsBtn.getScene().getWindow();
		    stage.close();
		}else {
			showAlertWarning("No Data", "No Bank Details to Submit,Please Add at least one Bank Detail");
			return;
		}

	}

	public void preparedata() {
		BankdetialPOJO							= new BankdetailsPOJO[bankTableDataList.size()];
		
		for(int i=0;i<bankTableDataList.size();i++) {
			
			BankAccountTablePOJO temPojo	= bankTableDataList.get(i);
			BankdetialPOJO[i]						= new BankdetailsPOJO();
			BankdetialPOJO[i].serialno				= i+1;
			BankdetialPOJO[i].accountNo 			= temPojo.accountNo; //Acc No and IBAN
			BankdetialPOJO[i].bank_ID				= temPojo.bankid; // bank id
			BankdetialPOJO[i].bankName				= temPojo.bankName;
			BankdetialPOJO[i].accountholderName	= temPojo.holderName;
			BankdetialPOJO[i].ifscCode				= temPojo.ifscCode;// ifsc and SWIFT code
			
		}
		// Passing the bankDetails Pojo to the Parent 
		addcompreg.Bankdet			= BankdetialPOJO;
		
	}
	
	@FXML
    private void handleCancel(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }	
	
	
	
	//Integer bank_id, String bankName, String accountNo, String holderName, String ifscCode

	private ObservableList<BankAccountTablePOJO> deepCopyBankData(ObservableList<BankAccountTablePOJO> originalList) {
	 
		ObservableList<BankAccountTablePOJO> copiedList = FXCollections.observableArrayList();
	   
			for (BankAccountTablePOJO item : originalList) {
		        BankAccountTablePOJO copy 	 = new BankAccountTablePOJO(
		            item.getBankid(),
		            item.getBankName(),
		            item.getAccountNo(),
		            item.getHolderName(),
		            item.getIfscCode()
		        );
		        copiedList.add(copy);
		    }
	    return copiedList;
	}

	
}
