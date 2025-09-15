package AdminController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import AdminApplication.MaterialandProductConfigMain;
import AdminApplicationModel.MaterialCategoryDataPOJO;
import AdminApplicationModel.MaterialCategoryFlexiTablePOJO;
import AdminApplicationModel.MaterialCategoryMainTablePOJO;
import AdminApplicationModel.MaterialCategoryTypeMainTablePOJO;
import AdminApplicationModel.ProductCategoryDataPOJO;
import AdminServices.MaterialandProductConfigServices;
import common.State;
import common.UOMs;
import commonGUI.AlertsandMessages;
import commonGUI.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.converter.DefaultStringConverter;

public class AddModMaterialCategoryController extends AlertsandMessages {
	
    @FXML private TabPane mainTabPane;
    
    public MaterialandProductConfigMain MatandProdConfigMain;
	MaterialandProductConfigServices MatProdConfigServ;
	
	@FXML private Label materialCategoryIDLBl;
	@FXML private TextField materialCategoryIDTXB;
	
	@FXML private Label materialCategoryNameLBL;
	@FXML private TextField materialCategoryNameTXB;
	
	@FXML private CheckBox IsActiveCBX;
	@FXML private Button AddMaterialTypeBtn;
	
	@FXML private Label MaterialTypeTableLbl;
	
	@FXML private TableView <MaterialCategoryTypeMainTablePOJO>  MaterialTypeTable;
	@FXML private TableColumn <MaterialCategoryTypeMainTablePOJO,Integer> ColMaterialTypeTypeSnno;
	@FXML private TableColumn <MaterialCategoryTypeMainTablePOJO,String>  ColMaterialTypeTypeName;
	@FXML private TableColumn <MaterialCategoryTypeMainTablePOJO,String>  ColMaterialTypeUOM;
	@FXML private TableColumn <MaterialCategoryTypeMainTablePOJO,String>  ColMaterialTypeStatus;
	
	ObservableList<MaterialCategoryTypeMainTablePOJO> MaterialTypeData = FXCollections.observableArrayList();
	
	@FXML private Button AddCatMatTypeIntoTableBtn;
	@FXML private Button DelCatMatTypeIntoTableBtn;
	
	@FXML private Label CategoryFlexiTableLbl;
	
	@FXML private TableView <MaterialCategoryFlexiTablePOJO>  CategoryFlexiTable;
	@FXML private TableColumn <MaterialCategoryFlexiTablePOJO,Integer> ColCategoryFlexiSnno;
	@FXML private TableColumn <MaterialCategoryFlexiTablePOJO,Integer> ColCategoryFlexiID;
	@FXML private TableColumn <MaterialCategoryFlexiTablePOJO,String>  ColCategoryFlexiName;
	@FXML private TableColumn <MaterialCategoryFlexiTablePOJO,String>  ColCategoryFlexiFieldComponets;
	@FXML private TableColumn <MaterialCategoryFlexiTablePOJO,String>  ColCategoryFlexiStatus;

	ObservableList<MaterialCategoryFlexiTablePOJO> CategoryFlexiData = FXCollections.observableArrayList();

	@FXML private Button AddMaterialFlexiBtn;
	@FXML private Button DelMaterialFlexiBtn;
	
	@FXML private Button SubmitBtn;
	@FXML private Button CancelBtn;
	
	String Nodename		= null;
    int Mode			= 0;

//   POJO 
    MaterialCategoryDataPOJO MatCatDataPojo;
    
    Integer MaterialCategoryID 		= 0;
    String ScreenName				= "Add Material Category";
    
	public void LoadAddorModMaterialCategoryScreen( MaterialandProductConfigMain materialCategoryMain,
			TabPane TabPane,String Nodename, MaterialCategoryDataPOJO SelectedItemData, int mode ) {
		
		this.MatandProdConfigMain 	= materialCategoryMain;
		this.mainTabPane			= TabPane;
		this.Nodename 				= Nodename;
		this.Mode 					= mode;
		
		if(Mode != 0) {
			ScreenName	= "Modify Material Category";
			SetDataForModification(SelectedItemData);
		}
	}
	
	@FXML
	public void initialize() {
		MatProdConfigServ			= new MaterialandProductConfigServices();
	//Table Listeners
		setupCategoryMaterialTypeTableListener();
		setupCategoryFlexiTableListener();
		
	}
	
	List<UOMs> uomList 			= new ArrayList<>();
	private void setupCategoryMaterialTypeTableListener() {
		MaterialTypeTable.setEditable(true);
		ColMaterialTypeTypeName.setCellFactory( TextFieldTableCell.forTableColumn() );
		
			ColMaterialTypeTypeName.setOnEditCommit(event -> {
			    MaterialCategoryTypeMainTablePOJO row = event.getRowValue();
			    row.setCategorymaterialTypeName(event.getNewValue());
			});
		
		uomList						= MatProdConfigServ.GetUOMDetails();
// extract only the UOM names for ComboBox
		List<String> uomNames 		= uomList.stream()
									    .map(UOMs::getUOMName)
									    .collect(Collectors.toList());
		ObservableList<String> uomOptions 			= FXCollections.observableArrayList(uomNames);
// now this will work perfectly
		ColMaterialTypeUOM.setCellFactory(ComboBoxTableCell.forTableColumn(uomOptions));
		
		ColMaterialTypeUOM.setOnEditCommit(event -> {
				    MaterialCategoryTypeMainTablePOJO row 	= event.getRowValue();
				    String selectedUOMName 					= event.getNewValue();
				    row.setCategorymaterialTypeUOM(selectedUOMName);
	// Find UOM ID from selected name
				    for (UOMs uom : uomList) {
				        if (uom.getUOMName().equals(selectedUOMName)) {
				            row.setCategorymaterialTypeUOMID(uom.getUOMID());
				            break;
				        }
				    }
			});
		
		ColMaterialTypeStatus.setCellFactory( ComboBoxTableCell.forTableColumn("Active", "Inactive") );

		ColMaterialTypeStatus.setOnEditCommit(event -> {
		    MaterialCategoryTypeMainTablePOJO row = event.getRowValue();
		    row.setCategorymaterialTypeStatus(event.getNewValue());
		});
	}
	private void setupCategoryFlexiTableListener() {
			CategoryFlexiTable.setEditable(true);
			ColCategoryFlexiName.setCellFactory( TextFieldTableCell.forTableColumn() );
			ColCategoryFlexiName.setOnEditCommit(event -> {
				MaterialCategoryFlexiTablePOJO row 	= event.getRowValue();
			    row.setCategoryMaterialFlexiName(event.getNewValue());
			});
			ColCategoryFlexiFieldComponets.setCellFactory( ComboBoxTableCell.forTableColumn("TextField", "ComboBox", "CheckBox") );
			ColCategoryFlexiFieldComponets.setOnEditCommit(event -> {
				MaterialCategoryFlexiTablePOJO row 	= event.getRowValue();
			    row.setCategoryMaterialFlexiComponet(event.getNewValue());
			});
			ColCategoryFlexiStatus.setCellFactory( ComboBoxTableCell.forTableColumn("Active", "Inactive") );
			ColCategoryFlexiStatus.setOnEditCommit(event -> {
				MaterialCategoryFlexiTablePOJO row 	= event.getRowValue();
			    row.setCategoryMaterialFlexiStatus(event.getNewValue());
			});
	    }

	
/***************************************************
 * Modification Components and data sets -STARTS-
 ***************************************************/
	private void SetDataForModification(MaterialCategoryDataPOJO SelectedItemData ) {

		this.MaterialCategoryID		= SelectedItemData.getMaterialCategory()
															.getMaterialCategoryID();;
// set data for Text Field 		
		materialCategoryIDTXB.setText(MaterialCategoryID.toString());
		materialCategoryNameTXB.setText(SelectedItemData.getMaterialCategory().getMaterialCategoryName());
		IsActiveCBX.setSelected( (SelectedItemData.getMaterialCategory().getMaterialCategoryStatus() ).equals("Active") ? true : false);
		
		AlignComponetsforModification();
		setVisibilityForComponets();
		SetTablesColumnsAndData();
	
	}

	private void SetTablesColumnsAndData() {
		loadMaterialCategoryTypeTableData();
		setDataForMaterialCategoryTypetable();
		
		loadMaterialCategoryFlexiTable();
		setDataForMaterialCategoryFlexiTable();
	}
	
	private void AlignComponetsforModification() {
		/*this method will set the Material category Id
		 *  visible and alter the Material Category Name layout */	
		materialCategoryIDLBl.setLayoutX(60);
		materialCategoryIDLBl.setLayoutY(80);
		
		materialCategoryIDTXB.setLayoutX(200);
		materialCategoryIDTXB.setLayoutY(80);
		
		materialCategoryNameLBL.setLayoutX(60);
		materialCategoryNameLBL.setLayoutY(125);
		
		materialCategoryNameTXB.setLayoutX(200);
		materialCategoryNameTXB.setLayoutY(125);
	}
	
	private void setVisibilityForComponets() {
	
		if(Mode == 1) {
			materialCategoryIDLBl.setVisible(true);
			materialCategoryIDTXB.setVisible(true);
			AddMaterialTypeBtn.setVisible(false);
		}else if(Mode == 0 ){
			materialCategoryIDLBl.setVisible(false);
			materialCategoryIDTXB.setVisible(false);
			AddMaterialTypeBtn.setVisible(true);
		}
		//Material type Table
		MaterialTypeTableLbl.setVisible(true);
		MaterialTypeTable.setVisible(true);
		AddCatMatTypeIntoTableBtn.setVisible(true);
		DelCatMatTypeIntoTableBtn.setVisible(true);
		
		//Material Flexi Table	
		CategoryFlexiTableLbl.setVisible(true);
		CategoryFlexiTable.setVisible(true);
		AddMaterialFlexiBtn.setVisible(true);
		DelMaterialFlexiBtn.setVisible(true);
	}
	
	private void loadMaterialCategoryTypeTableData() {
		MaterialTypeData	= FXCollections.observableArrayList (
								MatProdConfigServ.getMaterialTypeCategoryById( MaterialCategoryID )	);
		MaterialTypeTable.setItems(MaterialTypeData);
	}
	
	private void setDataForMaterialCategoryTypetable() {
		ColMaterialTypeTypeSnno.setCellValueFactory(	new PropertyValueFactory<>("CategorymaterialTypeSNno")		);
		ColMaterialTypeTypeName.setCellValueFactory(	new PropertyValueFactory<>("CategorymaterialTypeName")		);
		ColMaterialTypeUOM.setCellValueFactory(			new PropertyValueFactory<>("CategorymaterialTypeUOM")		);
		ColMaterialTypeStatus.setCellValueFactory(		new PropertyValueFactory<>("CategorymaterialTypeStatus")	);
	}
	
	private void loadMaterialCategoryFlexiTable() {
		CategoryFlexiData 	= FXCollections.observableArrayList (
							MatProdConfigServ.getMaterialCategoryFlexiById( MaterialCategoryID )	);
		CategoryFlexiTable.setItems(CategoryFlexiData);
	}
	
	private void setDataForMaterialCategoryFlexiTable() {
		
		ColCategoryFlexiSnno.setCellValueFactory(				new PropertyValueFactory<>("CategoryMaterialFlexiSnno")		);
		ColCategoryFlexiName.setCellValueFactory(				new PropertyValueFactory<>("CategoryMaterialFlexiName")		);
		ColCategoryFlexiFieldComponets.setCellValueFactory(		new PropertyValueFactory<>("CategoryMaterialFlexiComponet")	);
		ColCategoryFlexiStatus.setCellValueFactory(				new PropertyValueFactory<>("CategoryMaterialFlexiStatus")	);
	}
	
	private void refreshMaterialTypeSNs() {
	    for (int i = 0; i < MaterialTypeData.size(); i++) {
	    	MaterialTypeData.get(i).setCategorymaterialTypeSNno(i + 1);
	    }
	    MaterialTypeTable.refresh();
	}

	private void refreshFlexiSNs() {
	    for (int i = 0; i < CategoryFlexiData.size(); i++) {
	    	CategoryFlexiData.get(i).setCategoryMaterialFlexiSnno(i + 1);
	    }
	    CategoryFlexiTable.refresh();
	}

/***************************************************
 * Modification Components and data sets -ENDS-
 ***************************************************/
	
	public void HandelAddMaterialTypeTBlAction() {
			setVisibilityForComponets();
	}
/*this method add a new row to the Type table */
	public void handleAddCatMatTypeIntoTable() {
	    if (MaterialTypeData == null || MaterialTypeData.isEmpty()) {
	    	MaterialTypeData = FXCollections.observableArrayList();
	        MaterialTypeTable.setItems(MaterialTypeData); 	// Important: set table's items only once
	        setDataForMaterialCategoryTypetable(); 			// set columns only if needed
	    }

	    MaterialCategoryTypeMainTablePOJO newRow = new MaterialCategoryTypeMainTablePOJO();
	    newRow.setCategorymaterialTypeSNno(MaterialTypeData.size() + 1);
	    newRow.setCategorymaterialTypeName("");
	    newRow.setCategorymaterialTypeUOM(uomList.get(0).getUOMName()); // Default to first UOM
	    newRow.setCategorymaterialTypeUOMID(uomList.get(0).getUOMID());
	    newRow.setCategorymaterialTypeStatus("Active");

	    MaterialTypeData.add(newRow);
	    MaterialTypeTable.refresh(); // optional but safe
	}

	public void handleDelCatMatTypeIntoTable() {
	    MaterialCategoryTypeMainTablePOJO selected = MaterialTypeTable.getSelectionModel().getSelectedItem();
	    if (selected != null) {
	    	MaterialTypeData.remove(selected);
	        MaterialTypeTable.refresh();
	    }
	    refreshMaterialTypeSNs();
	}

/**************************************
 * 		Flexi table
 ***************************************/
	public void handleAddMaterialFlexiBtn() {

		if(CategoryFlexiData.size()>=8) {
			showAlertWarning(ScreenName, "only 8 Flexi Can be added ");
			return;
		}
		
		boolean isDuplicate = CategoryFlexiData.stream().anyMatch(filter -> 
					filter.getCategoryMaterialFlexiName()
					.equalsIgnoreCase("Flexi Name"));

			if (isDuplicate) {
			    showAlertWarning(ScreenName, "Flexi Name already exists");
			    return;
			}
		if(CategoryFlexiData == null || CategoryFlexiData.isEmpty()) {
			CategoryFlexiData 		= FXCollections.observableArrayList();
			  CategoryFlexiTable.setItems(CategoryFlexiData);
			  setDataForMaterialCategoryFlexiTable();	
		}
	    MaterialCategoryFlexiTablePOJO newRow = new MaterialCategoryFlexiTablePOJO();
	    newRow.setCategoryMaterialFlexiSnno(CategoryFlexiData.size() + 1);
	    newRow.setCategoryMaterialFlexiName(" ");
	    newRow.setCategoryMaterialFlexiComponet("TextField");
	    newRow.setCategoryMaterialFlexiStatus("Active");
	    CategoryFlexiData.add(newRow);
	    CategoryFlexiTable.refresh();
	}
	
	public void handleDelMaterialFlexiBtn() {
		MaterialCategoryFlexiTablePOJO selected = CategoryFlexiTable.getSelectionModel().getSelectedItem();
	    if (selected != null) {
	    	CategoryFlexiData.remove(selected);
	    	CategoryFlexiTable.refresh();
	    }
	    refreshFlexiSNs();
	}
	
	public void HandelSubmit() {
		MaterialCategoryDataPOJO MatCatDataPOJO = PrepareDataforInsertion();
	
		if(MatCatDataPOJO == null) {
			showAlert(ScreenName, "Error in Data preparation");
			return;
		}
		if(ValidateFields()) {
			if (JOptionPane.showConfirmDialog(null, "Do you want to Submit the Material Category Details ?",
						"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
					if(Mode==0) {
						MaterialCategoryID	= 0;
						MaterialCategoryID 	= MatProdConfigServ.SaveIntoMaterialCategoryTables( MaterialCategoryID,MatCatDataPOJO);
						
					}else if(Mode==1) {
						MaterialCategoryID	= MatProdConfigServ.ModifyMaterialCategoryTables( MaterialCategoryID,MatCatDataPOJO);
					}
					
				String Message 				= Mode == 0 ? "Created" : "Modified";	
				if(MaterialCategoryID !=0) {
					ProductCategoryDataPOJO ProdCatDataPOJO		= null;// Not used in Material Category so it is null
					MatProdConfigServ.MaterialAndProductConfigLog( MaterialCategoryID, MatCatDataPOJO,ProdCatDataPOJO);
					
					showAlert(ScreenName, "Material Category ID : "+MaterialCategoryID+" has been "+Message );
					HandelCancel();
				}else {
					showAlertWarning(ScreenName, "Error occured while saving.Material Category not "+Message);
					return;
				}
			}
		}
	}
	
	private MaterialCategoryDataPOJO PrepareDataforInsertion() {
// Build Main Category POJO
	    MaterialCategoryMainTablePOJO mainCategory 			= new MaterialCategoryMainTablePOJO(
	        Mode == 0 ? 0 : Integer.parseInt(materialCategoryIDTXB.getText()),
	        materialCategoryNameTXB.getText(),
	        IsActiveCBX.isSelected() ? "Active" : "Inactive",
        	UserSession.getInstance().getUsername(),
        	UserSession.getInstance().getUserIp(),
        	Mode == 0 ? "Add Material Category" : "Modify Material Category"
        	
	    );

// No need to convert ObservableList to Array
	    List<MaterialCategoryTypeMainTablePOJO> typeList 	= new ArrayList<>(MaterialTypeData);
	    List<MaterialCategoryFlexiTablePOJO> flexiList 		= new ArrayList<>(CategoryFlexiData);

// Wrap in DTO
	    return new MaterialCategoryDataPOJO(mainCategory, typeList, flexiList);
	}

	private Boolean ValidateFields() {
	    if (materialCategoryNameTXB.getText().isEmpty()) {
	        showAlertWarning(ScreenName, "Material Category Name is required.");
	        return false;
	    }
	    if (MaterialTypeData.isEmpty()) {
	        showAlertWarning(ScreenName, "At least one Material Type is required.");
	        return false;
	    }
	    for(MaterialCategoryTypeMainTablePOJO tempMaterialTypeData :MaterialTypeData) {
	    	if(tempMaterialTypeData.getCategorymaterialTypeName().isEmpty()) {
	    		showAlertWarning(ScreenName, "Material Type Name is required.");
	    		return false;
	    	}
	    	if(tempMaterialTypeData.getCategorymaterialTypeUOM().isEmpty()) {
	    		showAlertWarning(ScreenName, "Material Type UOM is required.");
	    		return false;
	    	}
	    }
	    for(MaterialCategoryFlexiTablePOJO tempFlexiData : CategoryFlexiData) {
	    	if(tempFlexiData.getCategoryMaterialFlexiName().isEmpty()) {
	    		showAlertWarning(ScreenName, "Flexi Name is required.");
	    		return false;
	    	}
	    	if(tempFlexiData.getCategoryMaterialFlexiComponet().isEmpty()) {
	    		showAlertWarning(ScreenName, "Flexi Field Componet is required.");
	    		return false;
	    	}
	    	
	    }
	    
		return true;
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
	
}
