package AdminController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import AdminApplication.MaterialandProductConfigMain;
import AdminApplicationModel.MaterialCategoryDataPOJO;
import AdminApplicationModel.ProductCategoryDataPOJO;
import AdminApplicationModel.ProductCategoryFlexiTablePOJO;
import AdminApplicationModel.ProductCategoryMainTablePOJO;
import AdminApplicationModel.ProductCategoryTypeMainTablePOJO;
import AdminServices.MaterialandProductConfigServices;
import common.UOMs;
import commonGUI.AlertsandMessages;
import commonGUI.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class AddModProductCategoryController extends AlertsandMessages {
    @FXML private TabPane mainTabPane;
    private MaterialandProductConfigMain MatandProdConfigMain;
	MaterialandProductConfigServices MatProdConfigServ;
    
	@FXML private Label ProductCategoryIDLBl;
	@FXML private TextField ProductCategoryIDTXB;
	
	@FXML private Label ProductCategoryNameLBL;
	@FXML private TextField ProductCategoryNameTXB;
	
	@FXML private CheckBox IsActiveCBX;
	@FXML private Button AddProductTypeBtn;

	
	@FXML private Label ProductTypeTableLbl;
	
	@FXML private TableView<ProductCategoryTypeMainTablePOJO> ProductTypeTable;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,Integer> ColProductTypeSnno;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,String> ColProductTypeName;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,String> ColProductTypeUOM;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,String> ColProductTypeStatus;
	ObservableList<ProductCategoryTypeMainTablePOJO> ProductTypeData = FXCollections.observableArrayList();
	
	@FXML private Button AddProdCatTypeIntoTableBtn;
	@FXML private Button DelProdCatTypeIntoTableBtn;
	
	@FXML private Label ProductFlexiTableLbl;
	@FXML private TableView<ProductCategoryFlexiTablePOJO> ProductFlexiTable;
	@FXML private TableColumn<ProductCategoryFlexiTablePOJO,Integer> ColProductCategoryFlexiSnno;
	@FXML private TableColumn<ProductCategoryFlexiTablePOJO,String> ColProductCategoryFlexiName;
	@FXML private TableColumn<ProductCategoryFlexiTablePOJO,String> ColProductCategoryFlexiFieldComponets;
	@FXML private TableColumn<ProductCategoryFlexiTablePOJO,String> ColProductCategoryFlexiStatus;
	ObservableList<ProductCategoryFlexiTablePOJO> ProductFlexiData = FXCollections.observableArrayList();
	
	@FXML private Button AddProductFlexiBtn;
	@FXML private Button DelProductFlexiBtn;
	
	@FXML private Button SubmitBtn;
	@FXML private Button CancelBtn;

//	POJO
	ProductCategoryDataPOJO productCategoryDataPOJO;	
	
	String Nodename		= null;
    int Mode			= 0;
    String ScreenName				= "Add Product Category";
    Integer ProductCategoryID 		= 0;
    
	public void LoadAddorModProductCategoryScreen(
			MaterialandProductConfigMain materialCategoryMain,
			TabPane TabPane, String nodename,
			ProductCategoryDataPOJO selectedItemData, Integer mode) {
		
		this.MatandProdConfigMain 	= materialCategoryMain;
		this.mainTabPane			= TabPane;
		this.Nodename 				= nodename;
		this.Mode 					= mode;
		
		if(Mode != 0) {
			ScreenName	= "Modify Product Category";
			SetDataForModification(selectedItemData);
		}
	}
	
	@FXML
	public void initialize() {
		MatProdConfigServ			= new MaterialandProductConfigServices();
	//Table Listeners
		setupProductCategoryTypeTableListener();
		setupCategoryFlexiTableListener();
		
	}

	List<UOMs> uomList 			= new ArrayList<>();
	private void setupProductCategoryTypeTableListener() {
		ProductTypeTable.setEditable(true);
		ColProductTypeName.setCellFactory( TextFieldTableCell.forTableColumn() );
		
			ColProductTypeName.setOnEditCommit(event -> {
				ProductCategoryTypeMainTablePOJO row = event.getRowValue();
			    row.setProductTypeName(event.getNewValue());
			});
		
		uomList						= MatProdConfigServ.GetUOMDetails();
// extract only the UOM names for ComboBox
		List<String> uomNames 		= uomList.stream()
									    .map(UOMs::getUOMName)
									    .collect(Collectors.toList());
		ObservableList<String> uomOptions 			= FXCollections.observableArrayList(uomNames);
// now this will work perfectly
		ColProductTypeUOM.setCellFactory(ComboBoxTableCell.forTableColumn(uomOptions));
			
		ColProductTypeUOM.setOnEditCommit(event -> {
				ProductCategoryTypeMainTablePOJO row 	= event.getRowValue();
				    String selectedUOMName 				= event.getNewValue();
				    row.setProductTypeUOM(selectedUOMName);
// Find UOM ID from selected name
				    for (UOMs uom : uomList) {
				        if (uom.getUOMName().equals(selectedUOMName)) {
				            row.setProductTypeUOMID(uom.getUOMID());        break;
				        }
				    }
			});
		
		ColProductTypeStatus.setCellFactory( ComboBoxTableCell.forTableColumn("Active", "Inactive") );

			ColProductTypeStatus.setOnEditCommit(event -> {
				ProductCategoryTypeMainTablePOJO row = event.getRowValue();
			    row.setProductTypeStatus(event.getNewValue());
			});
	}
	private void setupCategoryFlexiTableListener() {
		ProductFlexiTable.setEditable(true);
		ColProductCategoryFlexiName.setCellFactory( TextFieldTableCell.forTableColumn() );
		ColProductCategoryFlexiName.setOnEditCommit(event -> {
			ProductCategoryFlexiTablePOJO row 	= event.getRowValue();
		    row.setCategoryProductFlexiName(event.getNewValue());
		});
		ColProductCategoryFlexiFieldComponets.setCellFactory( ComboBoxTableCell.forTableColumn("TextField", "ComboBox", "CheckBox") );
		ColProductCategoryFlexiFieldComponets.setOnEditCommit(event -> {
			ProductCategoryFlexiTablePOJO row 	= event.getRowValue();
		    row.setCategoryProductFlexiComponet(event.getNewValue());
		});
		ColProductCategoryFlexiStatus.setCellFactory( ComboBoxTableCell.forTableColumn("Active", "Inactive") );
		ColProductCategoryFlexiStatus.setOnEditCommit(event -> {
			ProductCategoryFlexiTablePOJO row 	= event.getRowValue();
		    row.setCategoryProductFlexiStatus(event.getNewValue());
		});
    }
/***************************************************
 * Modification Components and data sets -STARTS-
 ***************************************************/
	private void SetDataForModification(ProductCategoryDataPOJO selectedItemData) {
		this.ProductCategoryID 			= selectedItemData.getProductCategory()
																.getProductCategoryID();
		ProductCategoryIDTXB.setText(ProductCategoryID.toString());
		ProductCategoryNameTXB.setText(selectedItemData.getProductCategory().getProductCategoryName());
		IsActiveCBX.setSelected( (selectedItemData.getProductCategory().getProductCategoryStatus() ).equals("Active") ? true : false);
	
		AlignComponetsforModification();
		setVisibilityForComponets();
		SetTablesColumnsAndData();
	}
	
	private void SetTablesColumnsAndData() {
		LoadProductCategoryTypeTableData();
		SetDataForProductCategoryTypeTable();
		
		loadProductCategoryFlexiTable();
		setDataForProductFlexiTable();
	}
	
	private void AlignComponetsforModification() {
		/*this method will set the Material category Id
		 *  visible and alter the Material Category Name layout */	
		ProductCategoryIDLBl.setLayoutX(60);
		ProductCategoryIDLBl.setLayoutY(80);
		
		ProductCategoryIDTXB.setLayoutX(200);
		ProductCategoryIDTXB.setLayoutY(80);
		
		ProductCategoryNameLBL.setLayoutX(60);
		ProductCategoryNameLBL.setLayoutY(125);
		
		ProductCategoryNameTXB.setLayoutX(200);
		ProductCategoryNameTXB.setLayoutY(125);
	}
	private void setVisibilityForComponets() {
		
		if(Mode == 1) {
			ProductCategoryIDLBl.setVisible(true);
			ProductCategoryIDTXB.setVisible(true);
			AddProductTypeBtn.setVisible(false);
		}else if(Mode == 0 ){
			ProductCategoryIDLBl.setVisible(false);
			ProductCategoryIDTXB.setVisible(false);
			AddProductTypeBtn.setVisible(true);
		}
		//Material type Table
		ProductTypeTableLbl.setVisible(true);
		ProductTypeTable.setVisible(true);
		AddProdCatTypeIntoTableBtn.setVisible(true);
		DelProdCatTypeIntoTableBtn.setVisible(true);
		
		//Material Flexi Table	
		ProductFlexiTableLbl.setVisible(true);
		ProductFlexiTable.setVisible(true);
		AddProductFlexiBtn.setVisible(true);
		DelProductFlexiBtn.setVisible(true);
	}

	private	void LoadProductCategoryTypeTableData() {
		ProductTypeData = FXCollections.observableArrayList(
				MatProdConfigServ.getProductTypeCategoryById(ProductCategoryID));
		ProductTypeTable.setItems(ProductTypeData);
	}
	private void SetDataForProductCategoryTypeTable() {
		ColProductTypeSnno.setCellValueFactory(		new PropertyValueFactory<>("ProductCategoryTypeSNno") );
		ColProductTypeName.setCellValueFactory(		new PropertyValueFactory<>("ProductTypeName") );
		ColProductTypeUOM.setCellValueFactory(		new PropertyValueFactory<>("ProductTypeUOM") );
		ColProductTypeStatus.setCellValueFactory(	new PropertyValueFactory<>("ProductTypeStatus")	);
	}
	
	private void loadProductCategoryFlexiTable() {
		ProductFlexiData = FXCollections.observableArrayList(
				MatProdConfigServ.getProductCategoryFlexiById(ProductCategoryID));
		ProductFlexiTable.setItems(ProductFlexiData);
	}
	private void setDataForProductFlexiTable() {
		ColProductCategoryFlexiSnno.setCellValueFactory(		new PropertyValueFactory<>("CategoryProductFlexiSnno") );
		ColProductCategoryFlexiName.setCellValueFactory(		new PropertyValueFactory<>("CategoryProductFlexiName") );
		ColProductCategoryFlexiFieldComponets.setCellValueFactory(	new PropertyValueFactory<>("CategoryProductFlexiComponet") );
		ColProductCategoryFlexiStatus.setCellValueFactory(	new PropertyValueFactory<>("CategoryProductFlexiStatus") );

	}
	
	private void RefreshProductTypeSNno() {
	    for (int i = 0; i < ProductTypeData.size(); i++) {
	    	ProductTypeData.get(i).setProductCategoryTypeSNno(i + 1);
	    }
	    ProductTypeTable.refresh();
	}

	private void RefreshProductFlexiSNno() {
	    for (int i = 0; i < ProductFlexiData.size(); i++) {
	    	ProductFlexiData.get(i).setCategoryProductFlexiSnno(i + 1);
	    }
	    ProductFlexiTable.refresh();
	}
/***************************************************
 * Modification Components and data sets -ENDS-
 ***************************************************/
/**************************************
 * 		Product Type table
 ***************************************/
	public void HandelAddProductTypeTBlAction() {
		setVisibilityForComponets();
	}
/*this method add a new row to the Type table */
	public void HandleAddProdCatTypeIntoTable() {
	    if (ProductTypeData == null || ProductTypeData.isEmpty()) {
	    	ProductTypeData = FXCollections.observableArrayList();
	        ProductTypeTable.setItems(ProductTypeData); 	// Important: set table's items only once
	        SetDataForProductCategoryTypeTable(); 			// set columns only if needed
	    }

	    ProductCategoryTypeMainTablePOJO newRow = new ProductCategoryTypeMainTablePOJO();
	    newRow.setProductCategoryTypeSNno(ProductTypeData.size() + 1);
	    newRow.setProductTypeName("");
	    newRow.setProductTypeUOM(uomList.get(0).getUOMName()); // Default to first UOM
	    newRow.setProductTypeUOMID(uomList.get(0).getUOMID());
	    newRow.setProductTypeStatus("Active");

	    ProductTypeData.add(newRow);
	    ProductTypeTable.refresh(); // optional but safe
	
	}		

	public void HandleDelProdCatTypeIntoTable(){
		ProductCategoryTypeMainTablePOJO selected = ProductTypeTable.getSelectionModel().getSelectedItem();
	    if (selected != null) {
	    	ProductTypeData.remove(selected);
	    	ProductTypeTable.refresh();
	    }
	    RefreshProductTypeSNno();	
	}

/**************************************
 * 		Product Flexi table
 ***************************************/

	public void HandleAddProductFlexiBtn() {
		if(ProductFlexiData.size()>=8) {
			showAlertWarning(ScreenName, "only 8 Flexi Can be added ");
			return;
		}
		
		boolean isDuplicate = ProductFlexiData.stream().anyMatch(
				filter -> 
					filter.getCategoryProductFlexiName()
					.equalsIgnoreCase("Flexi Name") );

			if (isDuplicate) {
			    showAlertWarning(ScreenName, "Flexi Name already exists");
			    return;
			}
		if(ProductFlexiData == null || ProductFlexiData.isEmpty()) {
			  ProductFlexiData 		= FXCollections.observableArrayList();
			  ProductFlexiTable.setItems(ProductFlexiData);
			  setDataForProductFlexiTable();	
		}
		ProductCategoryFlexiTablePOJO newRow = new ProductCategoryFlexiTablePOJO();
	    newRow.setCategoryProductFlexiSnno(ProductFlexiData.size() + 1);
	    newRow.setCategoryProductFlexiName(" ");
	    newRow.setCategoryProductFlexiComponet("TextField");
	    newRow.setCategoryProductFlexiStatus("Active");
	    ProductFlexiData.add(newRow);
	    ProductFlexiTable.refresh();
	
	}
	public void HandleDelProductFlexiBtn() {

		ProductCategoryFlexiTablePOJO selected = ProductFlexiTable.getSelectionModel().getSelectedItem();
	    if (selected != null) {
	    	ProductFlexiData.remove(selected);
	    	ProductFlexiTable.refresh();
	    }
	    RefreshProductFlexiSNno();
	
	}

/***************************************
 * Save Logic
****************************************/
	public void HandelSubmit() {
		ProductCategoryDataPOJO ProdCatDataPOJO = PrepareDataforInsertion();
		if(ProdCatDataPOJO == null) {
			showAlert(ScreenName, "Error in Data preparation");
			return;
		}
		if(ValidateFields()) {
			if (JOptionPane.showConfirmDialog(null, "Do you want to Submit the Product Category Details ?",
						"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
					if(Mode==0) {
						ProductCategoryID	= 0;
						ProductCategoryID 	= MatProdConfigServ.SaveIntoProductCategoryTables( ProductCategoryID,ProdCatDataPOJO);
						
					}else if(Mode==1) {
						ProductCategoryID	= MatProdConfigServ.ModifyProductCategoryTables( ProductCategoryID,ProdCatDataPOJO);
					}
					
				String Message 				= Mode == 0 ? "Created" : "Modified";	
				if(ProductCategoryID !=0) {
					MaterialCategoryDataPOJO MatCatDataPOJO 		= null; // Not used in Product Category so it is null
					MatProdConfigServ.MaterialAndProductConfigLog( ProductCategoryID,MatCatDataPOJO, ProdCatDataPOJO);
					
					showAlert(ScreenName, "Product Category ID : "+ProductCategoryID+" has been "+Message );
					HandelCancel();
				}else {
					showAlertWarning(ScreenName, "Error occured while saving.Product Category not "+Message);
					return;
				}
			}
		}
	}
	
	public void HandelCancel() {
        if (mainTabPane != null) {
	         Tab currentTab = mainTabPane.getSelectionModel().getSelectedItem();
	            if (currentTab != null) {
	                mainTabPane.getTabs().remove(currentTab);
	                MatandProdConfigMain.loadMaterialandProductConfig(Nodename, mainTabPane);
	            }
        } else {
            System.out.println("mainTabPane is NULL! Ensure it's correctly set in FXML.");
        }
	}

	private ProductCategoryDataPOJO PrepareDataforInsertion() {

		// Build Main Category POJO
				ProductCategoryMainTablePOJO mainCategory 		= new ProductCategoryMainTablePOJO(
			        Mode == 0 ? 0 : Integer.parseInt(ProductCategoryIDTXB.getText()),
			        ProductCategoryNameTXB.getText(),
			        IsActiveCBX.isSelected() ? "Active" : "Inactive",
		        	UserSession.getInstance().getUsername(),
		        	UserSession.getInstance().getUserIp(),
		        	Mode == 0 ? "Add Product Category" : "Modify Product Category"
		        	
			    );

		// No need to convert ObservableList to Array
			    List<ProductCategoryTypeMainTablePOJO> typeList 	= new ArrayList<>(ProductTypeData);
			    List<ProductCategoryFlexiTablePOJO> flexiList 		= new ArrayList<>(ProductFlexiData);

		// Wrap in DTO
			    return new ProductCategoryDataPOJO(mainCategory, typeList, flexiList);
			
	}
	
	private Boolean ValidateFields() {
	    if (ProductCategoryNameTXB.getText().isEmpty()) {
	        showAlertWarning(ScreenName, "Product Category Name is required.");
	        return false;
	    }
	    if (ProductTypeData.isEmpty()) {
	        showAlertWarning(ScreenName, "At least one Product Type is required.");
	        return false;
	    }
	    for(ProductCategoryTypeMainTablePOJO tempProductTypeData : ProductTypeData) {
	        if (tempProductTypeData.getProductTypeName().isEmpty()) {
	            showAlertWarning(ScreenName, "Product Type Name cannot be empty.");
	            return false;
	        }
	        if (tempProductTypeData.getProductTypeUOM().isEmpty()) {
	            showAlertWarning(ScreenName, "Product Type UOM cannot be empty.");
	            return false;
	        }
	    }
	    for(ProductCategoryFlexiTablePOJO tempProductFlexiData :ProductFlexiData) {
	    	if(tempProductFlexiData.getCategoryProductFlexiName().isEmpty()) {
	    		showAlertWarning(ScreenName, "Flexi Name cannot be empty.");
	    		return false;
	    	}
	    	if(tempProductFlexiData.getCategoryProductFlexiComponet().isEmpty()) {
	    		showAlertWarning(ScreenName, "Flexi Componet cannot be empty.");
	    		return false;
	    	}
	    }
	    
		return true;
	}
	
}
