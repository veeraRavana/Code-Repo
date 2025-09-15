package AdminApplicationModel;

import java.util.List;

public class ProductCategoryDataPOJO {

	private ProductCategoryMainTablePOJO productCategory;
	private List<ProductCategoryTypeMainTablePOJO> productCategoryTypeList;
	private List<ProductCategoryFlexiTablePOJO> productCategoryFlexiList;
	
	public ProductCategoryDataPOJO() {}
	
	public ProductCategoryDataPOJO(
			ProductCategoryMainTablePOJO productCategory,
			List<ProductCategoryTypeMainTablePOJO> productCategoryTypeList,
			List<ProductCategoryFlexiTablePOJO> productCategoryFlexiList) {
		
		this.productCategory 			= productCategory;
		this.productCategoryTypeList 	= productCategoryTypeList;
		this.productCategoryFlexiList 	= productCategoryFlexiList;
	}

	public ProductCategoryMainTablePOJO getProductCategory() {		return productCategory;	}
	public void setProductCategory(ProductCategoryMainTablePOJO productCategory) {
		this.productCategory 			= productCategory;
	}

	public List<ProductCategoryTypeMainTablePOJO> getProductCategoryTypeList() {		return productCategoryTypeList;	}
	public void setProductCategoryTypeList(List<ProductCategoryTypeMainTablePOJO> productCategoryTypeList) {
		this.productCategoryTypeList 	= productCategoryTypeList;
	}

	public List<ProductCategoryFlexiTablePOJO> getProductCategoryFlexiList() {		return productCategoryFlexiList;	}
	public void setProductCategoryFlexiList(List<ProductCategoryFlexiTablePOJO> productCategoryFlexiList) {
		this.productCategoryFlexiList 	= productCategoryFlexiList;
	}
	
}
