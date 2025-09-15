package AdminServices;

import java.util.List;

import AdminApplicationModel.ADDAddressPOJO;
import AdminApplicationModel.BankdetailsPOJO;
import AdminApplicationModel.Company;
import AdminApplicationModel.CompanyRegisterDataWrapper;
import AdminApplicationModel.CompanyRegisterPOJO;
import AdminDAO.CompanyRegisterDAO;
import common.BankNames;
import common.Countries;
import common.District;
import common.State;

public class CompanyRegisterServices {

	CompanyRegisterDAO CompRegDAO	= new CompanyRegisterDAO();
	
	public List<Countries> getAllContries() {
        return CompRegDAO.fetchAllCountries();
    }

    public List<State> getAllStates() {
        return CompRegDAO.fetchAllStates();
    }

    public List<District> getDistrictsByStateId(int stateId) {
        return CompRegDAO.fetchDistrictsByStateId(stateId);
    }

    public List<BankNames> getAllBankNames(Boolean isInternationalbank) {
		return CompRegDAO.fetchBanknames(isInternationalbank);
	}
	

    
	public List<Company> GetCompanyRegisterTableData() {
		// TODO Auto-generated method stub
		return CompRegDAO.GetCompanyRegisterTableData();
	}

	public CompanyRegisterDataWrapper getdatatoSetCompanyRegModandView(int selectedcompanyID) {
		
		return CompRegDAO.getDataforModificationViewCompReg(selectedcompanyID);
			
	}
	
	public int deleteCompanyRegisterDetails(int selectedcompanyID) {
		return CompRegDAO.deleteCompanyRegisterDetails(selectedcompanyID);
	}
	
	
	public boolean	logCompanyRegisterTransaction(int companyId, CompanyRegisterPOJO comppoj,
											ADDAddressPOJO addpojo,BankdetailsPOJO[] Bankdet ){
		return CompRegDAO.logCompanyRegisterTransaction(companyId,comppoj,addpojo,Bankdet);
	}
	
/*******************************************
 *Insertion Methods for Company Register 
 *******************************************/
   /*this method is to save Company Register
    * Details in the Table while Add Action */ 
    public Integer saveInCompanyRegister_Tables(int Company_ID,CompanyRegisterPOJO comppoj,ADDAddressPOJO addpojo,BankdetailsPOJO[] Bankdet ) {
   
    	Company_ID	=	CompRegDAO.saveInCompanyRegister_Tables(Company_ID,comppoj,addpojo,Bankdet);
    	
    	return Company_ID;
    }

  /*this method is to Save and update the
   *  Company Register Details in the table
   *   while Modification action */  
    public Integer ModifyCompanyRegister_Tables(int Company_ID,CompanyRegisterPOJO comppoj,ADDAddressPOJO addpojo,BankdetailsPOJO[] Bankdet ) {
    	Company_ID	=	CompRegDAO.ModifyCompanyRegister_Tables(Company_ID,comppoj,addpojo,Bankdet);
    	
    	return Company_ID;
    }
    
    
}
