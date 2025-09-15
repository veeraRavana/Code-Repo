package AdminServices;


import java.util.List;

import AdminApplicationModel.TaxProfile;
import AdminApplicationModel.TaxProfilePojo;
import AdminDAO.TaxProfileDAO;

public class TaxProfileService  {
	


	private final TaxProfileDAO taxProfileDAO = new TaxProfileDAO();
    TaxProfilePojo taxprofilepojo	= null;
    
    /**  Fetches all tax profiles */
    public List<TaxProfile> getAllTaxProfiles() {
        return taxProfileDAO.getAllTaxProfiles();
    }

    /**  Deletes a tax profile */
    public boolean deleteTaxProfile(int taxProfileId) {
      boolean isDeleted	=  taxProfileDAO.deleteTaxProfile(taxProfileId);
      return isDeleted;
    }
    
   //prepare the data to save the tax profile  
    public boolean SaveTaxProfile(TaxProfilePojo taxprofilepojo) {
    	this.taxprofilepojo = taxprofilepojo;
 
    	boolean isInserted		=     	 taxProfileDAO.Insert_into_tax_profile(taxprofilepojo);
  	
    	return isInserted;
    }
    
}
