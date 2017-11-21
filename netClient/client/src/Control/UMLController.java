package Control;

import Model.ClassDiagramModel.CDModel;
import Model.RepoModel;

import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-19.
 */
public class UMLController {
    private ArrayList<RepoModel> repoModels;
    private CDModel cdModel;
    private RepoModel repoModel;
    UMLController(){
        repoModels = new ArrayList<>();
        cdModel = new CDModel();
        repoModel = new RepoModel();
    }

    public CDModel getCdModel() {
        return cdModel;
    }

    public void setCdModel(CDModel cdModel) {
        this.cdModel = cdModel;
    }

    public RepoModel getRepoModel() {
        return repoModel;
    }

    public void setRepoModel(RepoModel repoModel) {
        this.repoModel = repoModel;
    }
}
