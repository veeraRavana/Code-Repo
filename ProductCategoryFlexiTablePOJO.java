package AdminApplicationModel;

public class ProductCategoryFlexiTablePOJO {

	private Integer CategoryProductID;
	private Integer CategoryProductFlexiSnno;
	private Integer CategoryProductFlexiID;
	private String CategoryProductFlexiName;
	private String CategoryProductFlexiComponet;
	private String CategoryProductFlexiStatus;
	
	/* This constructor is to assign the values 
	 * to the table and get the data from the table
	 * in the add and modification screen */
	public ProductCategoryFlexiTablePOJO(Integer categoryProductID,
			Integer categoryProductFlexiSnno, Integer categoryProductFlexiID,
			String categoryProductFlexiName, String categoryProductFlexiComponet,
			String categoryProductFlexiStatus) {
	
		this.CategoryProductFlexiSnno = categoryProductFlexiSnno;
		this.CategoryProductID = categoryProductID;
		this.CategoryProductFlexiID = categoryProductFlexiID;
		this.CategoryProductFlexiName = categoryProductFlexiName;
		this.CategoryProductFlexiComponet = categoryProductFlexiComponet;
		this.CategoryProductFlexiStatus = categoryProductFlexiStatus;
	}
	
	 /* This is for the ConvertCategoryFlexiPOJOToList method
	 * in the MaterialandProductConfigDAO to convert the
	 * given list into pojo so we can reuse the insertion method  */
	public ProductCategoryFlexiTablePOJO(Integer categoryProductFlexiSnno,
			Integer categoryProductID, String categoryProductFlexiName,
			String categoryProductFlexiComponet, String categoryProductFlexiStatus) {
		
	
		this.CategoryProductFlexiSnno = categoryProductFlexiSnno;
		this.CategoryProductID = categoryProductID;
		this.CategoryProductFlexiName = categoryProductFlexiName;
		this.CategoryProductFlexiComponet = categoryProductFlexiComponet;
		this.CategoryProductFlexiStatus = categoryProductFlexiStatus;
	}
	
	public ProductCategoryFlexiTablePOJO() {
		// Default constructor
	}

	public Integer getCategoryProductID() {		return CategoryProductID;	}
	public void setCategoryProductID(Integer categoryProductID) {
		CategoryProductID = categoryProductID;
	}

	public Integer getCategoryProductFlexiSnno() {		return CategoryProductFlexiSnno;	}
	public void setCategoryProductFlexiSnno(Integer categoryProductFlexiSnno) {
		CategoryProductFlexiSnno = categoryProductFlexiSnno;
	}

	public Integer getCategoryProductFlexiID() {		return CategoryProductFlexiID;	}
	public void setCategoryProductFlexiID(Integer categoryProductFlexiID) {
		CategoryProductFlexiID = categoryProductFlexiID;
	}

	public String getCategoryProductFlexiName() {		return CategoryProductFlexiName;	}
	public void setCategoryProductFlexiName(String categoryProductFlexiName) {
		CategoryProductFlexiName = categoryProductFlexiName;
	}

	public String getCategoryProductFlexiComponet() {		return CategoryProductFlexiComponet;	}
	public void setCategoryProductFlexiComponet(String categoryProductFlexiComponet) {
		CategoryProductFlexiComponet = categoryProductFlexiComponet;
	}

	public String getCategoryProductFlexiStatus() {		return CategoryProductFlexiStatus;	}
	public void setCategoryProductFlexiStatus(String categoryProductFlexiStatus) {
		CategoryProductFlexiStatus = categoryProductFlexiStatus;
	}
	
}
