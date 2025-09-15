package AdminApplicationModel;

public class BankdetailsPOJO {
	/*this is to prepare the data
	 * for the Data Insertions in
	 * the Database*/
	public Integer serialno;
	public Integer bank_ID;// to specifically get the bank id to save and modify the data 
	public String bankName;
    public String accountNo;
    public String accountholderName;
    public String ifscCode;
    
    
    public BankdetailsPOJO() {} // No-arg constructor

    // Getters and Setters
    public Integer getSerialno() { return serialno; }
    public void setSerialno(Integer serialno) { this.serialno = serialno; }
    
	public Integer getBank_ID() {	return bank_ID; }
	public void setBank_ID(Integer bank_ID) {	this.bank_ID = bank_ID; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getHolderName() { return accountholderName; }
    public void setHolderName(String holderName) { this.accountholderName = holderName; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
}
