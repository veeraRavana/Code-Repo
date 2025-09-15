package AdminApplicationModel;

public class PaymentTypeTable {
 
	private Integer paymentTypeId;
	private String paymentTypeName;
	private String paymentTypeDescription;
	private String paymentTypeStatus;
    
    public PaymentTypeTable(Integer paymentTypeId, String paymentTypeName,String paymentTypeDescription, String paymentTypeStatus) {
        this.paymentTypeId 			= paymentTypeId;
        this.paymentTypeName 		= paymentTypeName;
        this.paymentTypeDescription = paymentTypeDescription;
        this.paymentTypeStatus 		= paymentTypeStatus;
    }
    
// Getters and Setters
	public Integer getPaymentTypeId() {		return paymentTypeId;	}
	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId 			= paymentTypeId;
	}

	public String getPaymentTypeName() {	return paymentTypeName;	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName 		= paymentTypeName;
	}

    public String getPaymentTypeDescription() {		return paymentTypeDescription;	}
	public void setPaymentTypeDescription(String paymentTypeDescription) {
		this.paymentTypeDescription = paymentTypeDescription;
	}
	
	public String getPaymentTypeStatus() {	return paymentTypeStatus;	}
	public void setPaymentTypeStatus(String paymentTypeStatus) {
		this.paymentTypeStatus		= paymentTypeStatus;
	}
}
