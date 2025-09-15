package AdminApplicationModel;

public class SalesTypeTable {

	private Integer salesTypeId;
	private String salesTypeName;
	private String salesTypeDescription;
	private String salesTypeStatus;
	
	public SalesTypeTable(Integer salesTypeId,String salesTypeName, String salesTypeDescription,String salesTypeStatus) {
		this.salesTypeId 			= salesTypeId;
		this.salesTypeName 			= salesTypeName;
		this.salesTypeDescription 	= salesTypeDescription;
		this.salesTypeStatus 		= salesTypeStatus;
	}
	
	public Integer getSalesTypeId() {		return salesTypeId;	}
	public void setSalesTypeId(Integer salesTypeId) {
		this.salesTypeId 			= salesTypeId;
	}

	public String getSalesTypeName() {		return salesTypeName;	}
	public void setSalesTypeName(String salesTypeName) {
		this.salesTypeName 			= salesTypeName;
	}

	public String getSalesTypeDescription() {	return salesTypeDescription;	}
	public void setSalesTypeDescription(String salesTypeDescription) {
		this.salesTypeDescription 	= salesTypeDescription;
	}
	
	public String getSalesTypeStatus() {	return salesTypeStatus;	}
	public void setSalesTypeStatus(String salesTypeStatus) {
		this.salesTypeStatus 		= salesTypeStatus;
	}
}
