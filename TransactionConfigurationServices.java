package AdminServices;

import AdminApplicationModel.PaymentTypeTable;
import AdminApplicationModel.SalesTypeTable;
import AdminApplicationModel.TransactionConfigurationTypePOJO;
import AdminDAO.TransactionConfigurationDAO;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class TransactionConfigurationServices {

	TransactionConfigurationDAO transConfigDAO = new TransactionConfigurationDAO();
	


/*************************************************************************** 
 *   			Transaction Configuration Main Screen 
 * Transaction Configuration Type Table Data Fetching Method
 ***************************************************************************/	
	public ObservableList<SalesTypeTable> GetSalesTypeTableData() {
		
		return transConfigDAO.GetSalesTypeTableData();
	}
	
	public ObservableList<PaymentTypeTable> GetPaymentTypeTableData() {
		
		return transConfigDAO.GetPaymentTypeTableData();
	}
	
/*this method is to delete the selected
 *  transaction type TransactionID into the 
 *  table by the selected screen */	
	
	public int deleteTransactionConfiguration(int TransactionID, String ScreenName) {
		return transConfigDAO.deleteTransactionConfiguration(TransactionID, ScreenName);
	}
	
/***************************************************************************
 *  				 Add Or Modify  screen 
 *   Transaction Configuration Type Screen
 *   insert, modify  methods
 ***************************************************************************/
	
	public Integer saveOrModifyTransactionConfigurationType(
			Integer TransactionID,TransactionConfigurationTypePOJO transConfigTypePOJO,
			int mode, String ScreenName) {

		TransactionID 			= transConfigDAO.saveOrModifyTransactionConfigurationType(
										TransactionID, transConfigTypePOJO,mode, ScreenName);
		return TransactionID;
	}
	
/*this method is to modify the selected by delete 
 * and insert into  the Transaction type according
 *  to the selected screen */	
	public Integer ModifyTransactionTypeTables(Integer TransactionTypeID,
			TransactionConfigurationTypePOJO transConfigTypePOJO,int mode,String ScreenName) {
		
		TransactionTypeID		= transConfigDAO.ModifyTransactionTypeTables( TransactionTypeID, 
										transConfigTypePOJO, mode, ScreenName);
		return TransactionTypeID;
	}
/*this method is to account and record the each
 *  every transactions in the transaction configuration
 *   screen audit purpose */
	public boolean TransactionConfiguration_Log(Integer TransactionTypeID,
									TransactionConfigurationTypePOJO transConfigTypePOJO,int mode,String ScreenName) {
		
			return transConfigDAO.TransactionConfiguration_Log(TransactionTypeID, transConfigTypePOJO, mode, ScreenName);
	}
	
	
	
}
