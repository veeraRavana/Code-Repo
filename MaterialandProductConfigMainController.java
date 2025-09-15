package AdminController;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import AdminApplication.MaterialandProductConfigMain;
import AdminApplicationModel.MaterialCategoryDataPOJO;
import AdminApplicationModel.MaterialCategoryFlexiTablePOJO;
import AdminApplicationModel.MaterialCategoryMainTablePOJO;
import AdminApplicationModel.MaterialCategoryTypeMainTablePOJO;
import AdminApplicationModel.ProductCategoryDataPOJO;
import AdminApplicationModel.ProductCategoryFlexiTablePOJO;
import AdminApplicationModel.ProductCategoryMainTablePOJO;
import AdminApplicationModel.ProductCategoryTypeMainTablePOJO;
import AdminServices.MaterialandProductConfigServices;
import commonGUI.AlertsandMessages;
import commonGUI.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MaterialandProductConfigMainController extends AlertsandMessages {
	
	MaterialandProductConfigMain MaterialProductCategoryMain;
	MaterialandProductConfigServices MatProdConfigServ ;
	
	@FXML private TableView<MaterialCategoryMainTablePOJO> MaterialCategoryTable;
	@FXML private TableColumn<MaterialCategoryMainTablePOJO,Integer> ColMaterialCategoryID;
	@FXML private TableColumn<MaterialCategoryMainTablePOJO,String> ColMaterialCategoryName;
	@FXML private TableColumn<MaterialCategoryMainTablePOJO,String> ColMaterialCategoryStatus;
	
	@FXML private Label MaterialTypeTableLB;
	@FXML private TableView<MaterialCategoryTypeMainTablePOJO> MaterialTypeTable;
	@FXML private TableColumn<MaterialCategoryTypeMainTablePOJO,Integer> ColMaterialTypeSnno;
	@FXML private TableColumn<MaterialCategoryTypeMainTablePOJO,Integer> ColMaterialTypeCategoryID;
	@FXML private TableColumn<MaterialCategoryTypeMainTablePOJO,Integer> ColMaterialTypeID;
	@FXML private TableColumn<MaterialCategoryTypeMainTablePOJO,String> ColMaterialTypeName;
	@FXML private TableColumn<MaterialCategoryTypeMainTablePOJO,String> ColMaterialTypeUOM;
	@FXML private TableColumn<MaterialCategoryTypeMainTablePOJO,String> ColMaterialTypeStatus;
	
	private Integer MaterialCategoryID		= 0;
	
/*Product Category*/
	@FXML private TableView<ProductCategoryMainTablePOJO> ProductCategoryTable;
	@FXML private TableColumn<ProductCategoryMainTablePOJO,Integer> ColProductCategoryID;
	@FXML private TableColumn<ProductCategoryMainTablePOJO,String> ColProductCategoryName;
	@FXML private TableColumn<ProductCategoryMainTablePOJO,String> ColProductCategoryStatus;
	
	@FXML private Label ProductTypeLbl;
	@FXML private TableView<ProductCategoryTypeMainTablePOJO> ProductTypeTable;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,Integer> ColProductTypeSnno;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,Integer> ColProductTypeCategoryID;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,Integer> ColProductTypeID;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,String> ColProductTypeName;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,String> ColProductTypeUOM;
	@FXML private TableColumn<ProductCategoryTypeMainTablePOJO,String> ColProductTypeStatus;
	
	private Integer ProductCategoryID		= 0;
	
	public void setMaterialCategoryMain(MaterialandProductConfigMain materialCategoryMain) {
		this.MaterialProductCategoryMain 			= materialCategoryMain;
	}
	
	@FXML
	public void initialize() {
		MatProdConfigServ 			= new MaterialandProductConfigServices();
		loadMaterialCategoryScreen();
		loadProductCategoryScreen();
		
		MaterialCategoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
			if (newSel != null) {
				// Ensures selection is finalized before loading type table
				Platform.runLater(() -> {
					int selectedMaterialCategorySeqID = newSel.getMaterialCategoryID();
					loadMaterialTypeTable(selectedMaterialCategorySeqID);
				});
			}
		});
		
		
		ProductCategoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
			if (newSel != null) {
				// Ensures selection is finalized before loading type table
				Platform.runLater(() -> {
					int selectedProductCategorySeqID = newSel.getProductCategoryID();
					loadProductTypeTable(selectedProductCategorySeqID);
				});
			}
		});
		
		
	}
	
	private void loadMaterialCategoryScreen() {
		setDataForMaterialCategory();
		loadMaterialCategoryTable();
	
		setDataForMaterialType();
	}
	
	private void loadProductCategoryScreen() {
		setDataForProductCategory();
		loadProductCategoryTable();
		
		setDataForProductType();

	}
	
/**********************************************************************
 * Material Category
 **********************************************************************/	
	private void setDataForMaterialCategory() {
		ColMaterialCategoryID.setCellValueFactory(		new PropertyValueFactory<>("MaterialCategoryID")	);
		ColMaterialCategoryName.setCellValueFactory(	new PropertyValueFactory<>("MaterialCategoryName")	);
		ColMaterialCategoryStatus.setCellValueFactory(	new PropertyValueFactory<>("MaterialCategoryStatus")	);
	}
	private void loadMaterialCategoryTable() {
		ObservableList<MaterialCategoryMainTablePOJO> materialCategoryData	=
							FXCollections.observableArrayList(
									MatProdConfigServ.getAllMaterialCategoriesMainTableListData() );
		MaterialCategoryTable.setItems(materialCategoryData);
	}
	private void setDataForMaterialType() {
		ColMaterialTypeSnno.setCellValueFactory(		new PropertyValueFactory<>("CategorymaterialTypeSNno")	);
		ColMaterialTypeCategoryID.setCellValueFactory(	new PropertyValueFactory<>("CategorymaterialCategoryID")	);
		ColMaterialTypeID.setCellValueFactory(			new PropertyValueFactory<>("CategorymaterialTypeID")	);
		ColMaterialTypeName.setCellValueFactory(		new PropertyValueFactory<>("CategorymaterialTypeName")	);
		ColMaterialTypeUOM.setCellValueFactory(			new PropertyValueFactory<>("CategorymaterialTypeUOM")	);
		ColMaterialTypeStatus.setCellValueFactory(		new PropertyValueFactory<>("CategorymaterialTypeStatus")	);
		
		MaterialTypeTableLB.setVisible(false);
	}
	private void loadMaterialTypeTable(int selectedMaterialCategorySeqID) {
	
		MaterialTypeTable.getItems().clear();
		List<MaterialCategoryTypeMainTablePOJO> freshData =
			MatProdConfigServ.getMaterialTypeCategoryById(selectedMaterialCategorySeqID);
		MaterialTypeTable.getItems().addAll(freshData);
		MaterialTypeTable.setVisible(true);
		MaterialTypeTableLB.setVisible(true);
	}

	public void handleAddMaterialCategory() {
		int Mode = 0; // 0 for Add mode
		MaterialCategoryDataPOJO SelectedItemData = null;
		AddModMaterialCategory(SelectedItemData, Mode);
	}
	
	public void handleModifyMaterialCategory() {
		int Mode = 1; // 1 for Modify mode
		MaterialCategoryDataPOJO SelectedItemData	= prepareDataforMaterialModificationandDelete(Mode);
		AddModMaterialCategory(SelectedItemData, Mode);
	}
	
	public void handleDeleteMaterialCategory() {
		int Mode = 2; // 2 for Delete mode
		MaterialCategoryDataPOJO MatCatDataPOJO	= prepareDataforMaterialModificationandDelete(Mode);
			
		if (JOptionPane.showConfirmDialog(null, "Do you want to Delete the Selected Material Category",
					"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			
				if(Mode==2) {
				
					MaterialCategoryID 	= MatProdConfigServ.DeleteMaterialCategoryTables( MaterialCategoryID);
					
				}
			String Message 		= Mode == 1 ? "Modified" : "Deleted";
			String ScreenName 	= Mode == 1 ? "Modify Material Category" : "Delete Material Category";
			if(MaterialCategoryID !=0) {
				ProductCategoryDataPOJO ProdCatDataPOJO		= null;// Not used in Material Category so it is null
				MatProdConfigServ.MaterialAndProductConfigLog( MaterialCategoryID, MatCatDataPOJO,ProdCatDataPOJO);

				showAlert(ScreenName, "Material Category ID : "+MaterialCategoryID+" has been "+Message );
				loadMaterialCategoryScreenAfterDeleteAction();
			}else {
			
				showAlert(ScreenName, "Material Category ID : "+MaterialCategoryID+" has been "+Message );
				return;
			}
		}
	}

	private MaterialCategoryDataPOJO prepareDataforMaterialModificationandDelete(Integer Mode ) {
	
		String alertmessage 		= Mode == 1 ? "Modify" :"Delete";
		MaterialCategoryMainTablePOJO selectedCategory = MaterialCategoryTable.getSelectionModel().getSelectedItem();
		
		if(selectedCategory != null ) {
				
			    MaterialCategoryMainTablePOJO mainCategory = new MaterialCategoryMainTablePOJO(
			    		selectedCategory.getMaterialCategoryID(),
			    		selectedCategory.getMaterialCategoryName(),
			    		selectedCategory.getMaterialCategoryStatus(),
			        	UserSession.getInstance().getUsername(),
			        	UserSession.getInstance().getUserIp(),
			        	Mode==2 ?"Delete Material Category" : "Modify Material Category"
				    );

// No need to convert ObservableList to Array
				    List<MaterialCategoryTypeMainTablePOJO> typeList 	= new ArrayList<>( MatProdConfigServ.getMaterialTypeCategoryById( selectedCategory.getMaterialCategoryID() ).stream().toList()	);
				    List<MaterialCategoryFlexiTablePOJO> flexiList 		= new ArrayList<>( MatProdConfigServ.getMaterialCategoryFlexiById( selectedCategory.getMaterialCategoryID() ).stream().toList()	);
				    
				    MaterialCategoryID	= selectedCategory.getMaterialCategoryID();
				    return new MaterialCategoryDataPOJO(mainCategory, typeList, flexiList);
 		}else {
 			showAlertWarning("Material Category", "Select a Material Category to "+alertmessage+" ");
 			return new MaterialCategoryDataPOJO();
 		}
	
	}

	private void loadMaterialCategoryScreenAfterDeleteAction() {
		
			setDataForMaterialCategory();
			loadMaterialCategoryTable();
			
			setDataForMaterialType();
			MaterialTypeTable.setVisible(false);
			MaterialTypeTableLB.setVisible(false);
		
	}
	
	private void AddModMaterialCategory( MaterialCategoryDataPOJO SelectedItemData,int Mode) {
		MaterialProductCategoryMain.AddModMaterialCategory(SelectedItemData,Mode);
	}

/**********************************************************************
 * Product Category
 **********************************************************************/	
	
	private void setDataForProductCategory() {
		ColProductCategoryID.setCellValueFactory(		new PropertyValueFactory<>("ProductCategoryID")	);
		ColProductCategoryName.setCellValueFactory(		new PropertyValueFactory<>("ProductCategoryName")	);
		ColProductCategoryStatus.setCellValueFactory(	new PropertyValueFactory<>("ProductCategoryStatus")	);
	}
	private void loadProductCategoryTable() {
		ObservableList<ProductCategoryMainTablePOJO> productCategoryData =
				FXCollections.observableArrayList(
						MatProdConfigServ.getAllProductCategoriesMainTableListData() );
		ProductCategoryTable.setItems(productCategoryData);
	}
	private void setDataForProductType() {
		ColProductTypeSnno.setCellValueFactory(			new PropertyValueFactory<>("ProductCategoryTypeSNno")	);
		ColProductTypeCategoryID.setCellValueFactory(	new PropertyValueFactory<>("ProductCategoryID")	);
		ColProductTypeID.setCellValueFactory(			new PropertyValueFactory<>("ProductTypeID")	);
		ColProductTypeName.setCellValueFactory(			new PropertyValueFactory<>("ProductTypeName")	);
		ColProductTypeUOM.setCellValueFactory(			new PropertyValueFactory<>("ProductTypeUOM")	);
		ColProductTypeStatus.setCellValueFactory(		new PropertyValueFactory<>("ProductTypeStatus")	);
		
		ProductTypeLbl.setVisible(false);
		
	}
	private void loadProductTypeTable(int selectedProductCategorySeqID) {
		
		ProductTypeTable.getItems().clear();
		List<ProductCategoryTypeMainTablePOJO> freshData =
				MatProdConfigServ.getProductTypeCategoryById(selectedProductCategorySeqID);
		ProductTypeTable.getItems().addAll(freshData);
		ProductTypeTable.setVisible(true);
		ProductTypeLbl.setVisible(true);
	}
	
	private void loadProductCategoryScreenAfterDeleteAction() {
		setDataForProductCategory();
		loadProductCategoryTable();
		setDataForProductType();
		MaterialTypeTable.setVisible(false);
		MaterialTypeTableLB.setVisible(false);
	}

	public void handleAddProductCategory() {
		int Mode = 0; // 0 for Add mode
		ProductCategoryDataPOJO SelectedItemData = null;
		MaterialProductCategoryMain.AddModProductCategory(SelectedItemData,Mode);
	}

	public void handleModifyProductCategory() {
		int Mode = 1; // 1 for Modify mode
		ProductCategoryDataPOJO SelectedItemData	= prepareDataforProductModificationandDelete(Mode);
		MaterialProductCategoryMain.AddModProductCategory(SelectedItemData,Mode);
	}
	
	public void handleDeleteProductCategory() {
		int Mode = 2; // 2 for Delete mode
		ProductCategoryDataPOJO ProdCatDataPOJO	= prepareDataforProductModificationandDelete(Mode);
			
		if (JOptionPane.showConfirmDialog(null, "Do you want to Delete the Selected Product Category",
					"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			
				if(Mode==2) {
					ProductCategoryID 	= MatProdConfigServ.DeleteProductCategoryTables( ProductCategoryID);
				}
				
			String Message 		= Mode == 1 ? "Modified" : "Deleted";
			String ScreenName 	= Mode == 1 ? "Modify Material Category" : "Delete Material Category";
			if(MaterialCategoryID !=0) {
				MaterialCategoryDataPOJO MatCatDataPOJO		= null;// Not used in Product Category so it is null
				MatProdConfigServ.MaterialAndProductConfigLog( ProductCategoryID, MatCatDataPOJO,ProdCatDataPOJO);

				showAlert(ScreenName, "Product Category ID : "+ProductCategoryID+" has been "+Message );
				loadProductCategoryScreenAfterDeleteAction();
			}else {
			
				showAlert(ScreenName, "Product Category ID : "+ProductCategoryID+" has been "+Message );
				return;
			}
		}
	}
	
	
	private ProductCategoryDataPOJO prepareDataforProductModificationandDelete(Integer Mode ) {
		String alertmessage 			= Mode == 1 ? "Modify" :"Delete";
		ProductCategoryMainTablePOJO selectedCategory = ProductCategoryTable.getSelectionModel().getSelectedItem();
	
		if(selectedCategory != null ) {
			Integer ProdCategoryID 		= selectedCategory.getProductCategoryID();
			
			ProductCategoryMainTablePOJO mainCategory = new ProductCategoryMainTablePOJO(
					ProdCategoryID,
					selectedCategory.getProductCategoryName(),
					selectedCategory.getProductCategoryStatus(),
					UserSession.getInstance().getUsername(),
					UserSession.getInstance().getUserIp(),
					Mode==2 ?"Delete Product Category" : "Modify Product Category"
				);
			
			List<ProductCategoryTypeMainTablePOJO> typeList 	= new ArrayList<>( MatProdConfigServ.getProductTypeCategoryById( ProdCategoryID ).stream().toList()	);
			List<ProductCategoryFlexiTablePOJO> flexiList 		= new ArrayList<>( MatProdConfigServ.getProductCategoryFlexiById( ProdCategoryID ).stream().toList()	);
			
			return new ProductCategoryDataPOJO(mainCategory, typeList, flexiList);
			
		}else {
			showAlertWarning("Product Category", "Select a Product Category to "+alertmessage+" ");
			return new ProductCategoryDataPOJO();
		}
	}

}	
