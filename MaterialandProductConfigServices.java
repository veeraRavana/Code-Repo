package AdminServices;

import AdminApplicationModel.MaterialCategoryDataPOJO;
import AdminApplicationModel.MaterialCategoryFlexiTablePOJO;
import AdminApplicationModel.MaterialCategoryMainTablePOJO;
import AdminApplicationModel.MaterialCategoryTypeMainTablePOJO;
import AdminApplicationModel.ProductCategoryDataPOJO;
import AdminApplicationModel.ProductCategoryFlexiTablePOJO;
import AdminApplicationModel.ProductCategoryMainTablePOJO;
import AdminApplicationModel.ProductCategoryTypeMainTablePOJO;
import AdminDAO.MaterialandProductConfigDAO;
import common.UOMs;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class MaterialandProductConfigServices {

	MaterialandProductConfigDAO matProdConfigDAO = new MaterialandProductConfigDAO();

	
	public  ObservableList <MaterialCategoryMainTablePOJO> getAllMaterialCategoriesMainTableListData() {
			return matProdConfigDAO.getAllMaterialCategoriesMainTableListData();
		}
	public ObservableList<MaterialCategoryTypeMainTablePOJO> getMaterialTypeCategoryById(int selectedMaterialCategorySeqID) {
		return matProdConfigDAO.getMaterialTypeCategoryById(selectedMaterialCategorySeqID);
	}
	
	public ObservableList<MaterialCategoryFlexiTablePOJO> getMaterialCategoryFlexiById(int selectedMaterialCategorySeqID) {
		return matProdConfigDAO.getMaterialCategoryFlexiById(selectedMaterialCategorySeqID);
	}
	
	/*************************************************************/
	
	public ObservableList <ProductCategoryMainTablePOJO>getAllProductCategoriesMainTableListData() {
		return matProdConfigDAO.getAllProductCategoriesMainTableListData();
	}
	
	public ObservableList<ProductCategoryTypeMainTablePOJO> getProductTypeCategoryById(int selectedProductCategorySeqID) {
		return matProdConfigDAO.getProductTypeCategoryById(selectedProductCategorySeqID);
	}

	public ObservableList<ProductCategoryFlexiTablePOJO> getProductCategoryFlexiById(int selectedMaterialCategorySeqID) {
		return matProdConfigDAO.getProductCategoryFlexiById(selectedMaterialCategorySeqID);
	}
	
	
	public ObservableList<UOMs> GetUOMDetails() {
		return matProdConfigDAO.GetUOMDetails();
	}

/************************************************************
 * Add Material Category Screen  Save, Modify and Delete
 ************************************************************/	

	public Integer SaveIntoMaterialCategoryTables( Integer MaterialCategoryID, MaterialCategoryDataPOJO MatCatDataPOJO) {
		return matProdConfigDAO.SaveIntoMaterialCategoryTables( MaterialCategoryID, MatCatDataPOJO);
	}

	public Integer ModifyMaterialCategoryTables(Integer MaterialCategoryID, MaterialCategoryDataPOJO MatCatDataPOJO) {
		return matProdConfigDAO.ModifyMaterialCategoryTables( MaterialCategoryID,  MatCatDataPOJO);
	}
	
	public Integer DeleteMaterialCategoryTables(Integer MaterialCategoryID) {
		return matProdConfigDAO.DeleteMaterialCategoryTables(MaterialCategoryID);
	}
	
/************************************************************
 * Add Product Category Screen Save, Modify and Delete
 ************************************************************/	
	
	public Integer SaveIntoProductCategoryTables(Integer productCategoryID, ProductCategoryDataPOJO prodCatDataPOJO) {
		return matProdConfigDAO.SaveIntoProductCategoryTables( productCategoryID, prodCatDataPOJO);
	}
	public Integer ModifyProductCategoryTables(Integer productCategoryID, ProductCategoryDataPOJO prodCatDataPOJO) {
		return matProdConfigDAO.ModifyProductCategoryTables( productCategoryID,  prodCatDataPOJO);
	}
	public Integer DeleteProductCategoryTables(Integer productCategoryID) {
		return matProdConfigDAO.DeleteProductCategoryTables(productCategoryID);
	}
/************************************************************
 * Material and Product Screen Log
 ************************************************************/	
	public Boolean MaterialAndProductConfigLog(Integer MaterialOrProductID, 
			MaterialCategoryDataPOJO MatCatDataPOJO,ProductCategoryDataPOJO ProdCatDataPOJO) {
		return matProdConfigDAO.MaterialAndProductConfigLog(MaterialOrProductID,MatCatDataPOJO,ProdCatDataPOJO);
	}


	
	
	
}
