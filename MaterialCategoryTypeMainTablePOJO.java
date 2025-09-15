package AdminApplicationModel;

public class MaterialCategoryTypeMainTablePOJO {
	
	private Integer CategorymaterialTypeSNno;
	private Integer CategorymaterialCategoryID;
	private Integer CategorymaterialTypeID;
	private String CategorymaterialTypeName;
	private Integer CategorymaterialTypeUOMID;
	private String CategorymaterialTypeUOM;
	private String CategorymaterialTypeStatus;
	
	/*This constructor is for Material category main screen
	 *  table setup and also for Material category Modification
	 *   Screen */
	public MaterialCategoryTypeMainTablePOJO( Integer categorymaterialTypeSNno,
			Integer categorymaterialCategoryID,Integer categorymaterialTypeID,
			String categorymaterialTypeName,
			Integer categorymaterialTypeUOMID ,
			String categorymaterialTypeUOM,String categorymaterialTypeStatus ) {
		
		this.CategorymaterialTypeSNno 	= categorymaterialTypeSNno;
		this.CategorymaterialCategoryID	= categorymaterialCategoryID;
		this.CategorymaterialTypeID 	= categorymaterialTypeID;
		this.CategorymaterialTypeName 	= categorymaterialTypeName;
		this.CategorymaterialTypeUOMID 	= categorymaterialTypeUOMID; // This can be set later if needed
		this.CategorymaterialTypeUOM 	= categorymaterialTypeUOM;
		this.CategorymaterialTypeStatus = categorymaterialTypeStatus;
	}
	
	/*Constructor for Add Material category screen 
	 * (without categorymaterialTypeID) */
	public MaterialCategoryTypeMainTablePOJO(Integer categorymaterialTypeSNno,
			Integer categorymaterialCategoryID, String categorymaterialTypeName,
			String categorymaterialTypeUOM, String categorymaterialTypeStatus) {

	    this.CategorymaterialTypeSNno 	= categorymaterialTypeSNno;
	    this.CategorymaterialCategoryID = categorymaterialCategoryID;
	    this.CategorymaterialTypeName 	= categorymaterialTypeName;
	    this.CategorymaterialTypeUOM 	= categorymaterialTypeUOM;
	    this.CategorymaterialTypeStatus = categorymaterialTypeStatus;
	}
	
	//common Constructor
	public MaterialCategoryTypeMainTablePOJO() {
		
	}
	
	public Integer getCategorymaterialTypeSNno() {		return CategorymaterialTypeSNno;	}
	public void setCategorymaterialTypeSNno(Integer categorymaterialTypeSNno) {
		CategorymaterialTypeSNno = categorymaterialTypeSNno;
	}
	
	public Integer getCategorymaterialCategoryID() {		return CategorymaterialCategoryID;	}
	public void setCategorymaterialCategoryID(Integer categorymaterialCategoryID) {
		CategorymaterialCategoryID = categorymaterialCategoryID;
	}
	
	public Integer getCategorymaterialTypeID() {		return CategorymaterialTypeID;	}
	public void setCategorymaterialTypeID(Integer categorymaterialTypeID) {
		CategorymaterialTypeID = categorymaterialTypeID;
	}
	
	public String getCategorymaterialTypeName() {		return CategorymaterialTypeName;	}
	public void setCategorymaterialTypeName(String categorymaterialTypeName) {
		CategorymaterialTypeName = categorymaterialTypeName;
	}
	
	public Integer getCategorymaterialTypeUOMID() {		return CategorymaterialTypeUOMID;	}
	public void setCategorymaterialTypeUOMID(Integer categorymaterialTypeUOMID) {
		CategorymaterialTypeUOMID = categorymaterialTypeUOMID;
	}
	
	public String getCategorymaterialTypeUOM() {		return CategorymaterialTypeUOM;	}
	public void setCategorymaterialTypeUOM(String categorymaterialTypeUOM) {
		CategorymaterialTypeUOM = categorymaterialTypeUOM;
	}

	public String getCategorymaterialTypeStatus() {		return CategorymaterialTypeStatus;	}
	public void setCategorymaterialTypeStatus(String categorymaterialTypeStatus) {
		CategorymaterialTypeStatus = categorymaterialTypeStatus;
	}
	
	
	
}
