package AdminApplicationModel;

public class MaterialCategoryFlexiTablePOJO {
	
	private Integer CategoryMaterialID;
	private Integer CategoryMaterialFlexiSnno;
	private Integer CategorymaterialFlexiID;
	private String CategoryMaterialFlexiName;
	private String CategoryMaterialFlexiComponet;
	private String CategoryMaterialFlexiStatus;
	
/*this constructor is to assign the values 
 * to the table and get the data from the table
 *  in the add and modification screen */
	public MaterialCategoryFlexiTablePOJO( Integer categoryMaterialID,
			Integer categoryMaterialFlexiSnno,Integer categorymaterialFlexiID,
			String categoryMaterialFlexiName,String categoryMaterialFlexiComponet,
			String categoryMaterialFlexiStatus ) {
	
		this.CategoryMaterialFlexiSnno		= categoryMaterialFlexiSnno;
		this.CategoryMaterialID				= categoryMaterialID;
		this.CategorymaterialFlexiID		= categorymaterialFlexiID;
		this.CategoryMaterialFlexiName		= categoryMaterialFlexiName;
		this.CategoryMaterialFlexiComponet	= categoryMaterialFlexiComponet;
		this.CategoryMaterialFlexiStatus	= categoryMaterialFlexiStatus;
	}
/*this is for the ConvertCategoryFlexiPOJOToList method
 *  in the MaterialandProductConfigDAO to convert the
 * given list into pojo so we can reuse the insertion method  */
	public MaterialCategoryFlexiTablePOJO(Integer categoryMaterialFlexiSnno,
			Integer categoryMaterialID,String categoryMaterialFlexiName,
			String categoryMaterialFlexiComponet,String categoryMaterialFlexiStatus ) {
		
		this.CategoryMaterialFlexiSnno		= categoryMaterialFlexiSnno;
		this.CategoryMaterialID				= categoryMaterialID;
		this.CategoryMaterialFlexiName		= categoryMaterialFlexiName;
		this.CategoryMaterialFlexiComponet	= categoryMaterialFlexiComponet;
		this.CategoryMaterialFlexiStatus	= categoryMaterialFlexiStatus;
	}
	
	//common Constructor
	public MaterialCategoryFlexiTablePOJO() {

	}

	public Integer getCategoryMaterialID() {		return CategoryMaterialID;	}
	public void setCategoryMaterialID(Integer categoryMaterialID) {
		CategoryMaterialID 					= categoryMaterialID;
	}
	
	public Integer getCategoryMaterialFlexiSnno() {		return CategoryMaterialFlexiSnno;	}
	public void setCategoryMaterialFlexiSnno(Integer categoryMaterialFlexiSnno) {
		CategoryMaterialFlexiSnno 			= categoryMaterialFlexiSnno;
	}
	
	public Integer getCategorymaterialFlexiID() {		return CategorymaterialFlexiID;	}
	public void setCategorymaterialFlexiID(Integer categorymaterialFlexiID) {
		CategorymaterialFlexiID 			= categorymaterialFlexiID;
	}
	
	public String getCategoryMaterialFlexiName() {		return CategoryMaterialFlexiName;	}
	public void setCategoryMaterialFlexiName(String categoryMaterialFlexiName) {
		CategoryMaterialFlexiName 			= categoryMaterialFlexiName;
	}
	
	public String getCategoryMaterialFlexiComponet() {		return CategoryMaterialFlexiComponet;	}
	public void setCategoryMaterialFlexiComponet(String categoryMaterialFlexiComponet) {
		CategoryMaterialFlexiComponet 		= categoryMaterialFlexiComponet;
	}
	
	public String getCategoryMaterialFlexiStatus() {		return CategoryMaterialFlexiStatus;	}
	public void setCategoryMaterialFlexiStatus(String categoryMaterialFlexiStatus) {
		this.CategoryMaterialFlexiStatus 	= categoryMaterialFlexiStatus;
	}


}
