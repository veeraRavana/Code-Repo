package AdminApplicationModel;

import java.util.List;

public class CompanyRegisterDataWrapper {
    private List<Company> companyData;
    private List<ADDAddressPOJO> addressData;
    private List<BankdetailsPOJO> bankDetailsData;

    public CompanyRegisterDataWrapper(List<Company> companyData,
                                      List<ADDAddressPOJO> addressData,
                                      List<BankdetailsPOJO> bankDetailsData) {
        this.companyData = companyData;
        this.addressData = addressData;
        this.bankDetailsData = bankDetailsData;
    }

    public List<Company> getCompanyData() {        return companyData;    }
    public void setCompanyData(List<Company> companyData) {
		this.companyData = companyData;
	}

    public List<ADDAddressPOJO> getAddressData() {        return addressData;    }
	public void setAddressData(List<ADDAddressPOJO> addressData) {
		this.addressData = addressData;
	}

    public List<BankdetailsPOJO> getBankDetailsData() {        return bankDetailsData;    }
    
	public void setBankDetailsData(List<BankdetailsPOJO> bankDetailsData) {
		this.bankDetailsData = bankDetailsData;
	}
    
}
