package AdminController;

import AdminApplication.MaterialMasterMain;
import AdminServices.MaterialMasterServices;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class MaterialMasterMainController {
	
	 @FXML private TabPane mainTabPane; // TabPane for managing tabs

	MaterialMasterMain MaterialMasterMain;
	MaterialMasterServices MatServ;
	
	public void setMaterialMasterMain(MaterialMasterMain materialMasterMain) {
		this.MaterialMasterMain 	= materialMasterMain;
		
	}
	
	@FXML
    public void initialize() {
		MatServ				= new MaterialMasterServices();
    }
	
	private void SetColumn() {
		
	}

}
