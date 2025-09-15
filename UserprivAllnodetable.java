package AdminApplicationModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class UserprivAllnodetable {

    private int ModuleID;
    private String ModeuleName;
    private int NodeID;
    private String NodeName;
    private BooleanProperty Isselected;

    public UserprivAllnodetable(int moduleID, String modeuleName,
                                 int nodeID, String nodeName, boolean isselected) {
        this.ModuleID 		= moduleID;
        this.ModeuleName 	= modeuleName;
        this.NodeID 		= nodeID;
        this.NodeName 		= nodeName;
        this.Isselected 	= new SimpleBooleanProperty(isselected);
    }

    public int getModuleID() {
        return ModuleID;
    }

    public void setModuleID(int moduleID) {
        ModuleID = moduleID;
    }

    public String getModeuleName() {
        return ModeuleName;
    }

    public void setModeuleName(String modeuleName) {
        ModeuleName = modeuleName;
    }

    public int getNodeID() {
        return NodeID;
    }

    public void setNodeID(int nodeID) {
        NodeID = nodeID;
    }

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }

    public boolean Isselected() {
        return Isselected.get();
    }

    public void setIsselected(boolean isselected) {
        this.Isselected.set(isselected);
    }

    public BooleanProperty selectedProperty() {
        return Isselected;
    }
}
