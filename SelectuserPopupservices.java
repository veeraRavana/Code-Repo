package AdminServices;

import java.util.List;

import AdminApplicationModel.SelectUserPopupPOJO;
import AdminDAO.UserPrivilegeDAO;

public class SelectuserPopupservices {

	
	private final UserPrivilegeDAO userprivDAO = new UserPrivilegeDAO();

	public List<SelectUserPopupPOJO> getAllUsernames(){
		return userprivDAO.getAllUsernames();
	}
	
}
