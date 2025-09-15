package AdminApplicationModel;

public class ProductCategoryTypeMainTablePOJO {

	private Integer ProductCategoryTypeSNno;
	private Integer ProductCategoryID;
	private Integer ProductTypeID;
	private String ProductTypeName;
	private Integer ProductTypeUOMID;
	private String ProductTypeUOM;
	private String ProductTypeStatus;

	/*This constructor is for Product category main screen
	 *  table setup and also for Product category Modification
	 *   Screen */
	public ProductCategoryTypeMainTablePOJO(Integer productCategoryTypeSNno,
			Integer productCategoryID,Integer productTypeID,
			String productTypeName,Integer ProductTypeUOMID,
			String ProductTypeUOM, String ProductTypeStatus ) {
		
		this.ProductCategoryTypeSNno 	= productCategoryTypeSNno;
		this.ProductCategoryID 			= productCategoryID;
		this.ProductTypeID 				= productTypeID;
		this.ProductTypeName 			= productTypeName;
		this.ProductTypeUOMID 			= ProductTypeUOMID; 
		this.ProductTypeUOM				= ProductTypeUOM;
		this.ProductTypeStatus 			= ProductTypeStatus;
	}
	
	/*Constructor for Add Product category screen 
	 * (without ProductTypeID) */	
	public ProductCategoryTypeMainTablePOJO(Integer productCategoryTypeSNno,
			Integer productCategoryID, String productTypeName,
			String productTypeUOM, String productTypeStatus) {

	    this.ProductCategoryTypeSNno 	= productCategoryTypeSNno;
	    this.ProductCategoryID 			= productCategoryID;
	    this.ProductTypeName 			= productTypeName;
	    this.ProductTypeUOM 			= productTypeUOM;
	    this.ProductTypeStatus 			= productTypeStatus;
	}
	//common Constructor
	public	ProductCategoryTypeMainTablePOJO(){
		
	}
	public Integer getProductCategoryTypeSNno() {		return ProductCategoryTypeSNno;	}
	public void setProductCategoryTypeSNno(Integer productCategoryTypeSNno) {
		ProductCategoryTypeSNno = productCategoryTypeSNno;
	}

	public Integer getProductCategoryID() {		return ProductCategoryID;	}
	public void setProductCategoryID(Integer productCategoryID) {
		ProductCategoryID = productCategoryID;
	}

	public Integer getProductTypeID() {		return ProductTypeID;	}
	public void setProductTypeID(Integer productTypeID) {
		ProductTypeID = productTypeID;
	}

	public String getProductTypeName() {		return ProductTypeName;	}
	public void setProductTypeName(String productTypeName) {
		ProductTypeName = productTypeName;
	}

	public Integer getProductTypeUOMID() {		return ProductTypeUOMID;	}
	public void setProductTypeUOMID(Integer productTypeUOMID) {
		ProductTypeUOMID = productTypeUOMID;
	}

	public String getProductTypeUOM() {		return ProductTypeUOM;	}
	public void setProductTypeUOM(String productTypeUOM) {
		ProductTypeUOM = productTypeUOM;
	}

	public String getProductTypeStatus() {		return ProductTypeStatus;	}
	public void setProductTypeStatus(String productTypeStatus) {
		ProductTypeStatus = productTypeStatus;
	}
	
}
