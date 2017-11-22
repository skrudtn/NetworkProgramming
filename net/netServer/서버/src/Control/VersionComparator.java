package Control;

import Model.RepoModel.VersionModel;

import java.util.Comparator;

/**
 * Created by skrud on 2017-11-19.
 */
public class VersionComparator implements Comparator<VersionModel> {
    @Override
    public int compare(VersionModel o1, VersionModel o2) {
        int i1 = o1.getVer();
        int i2 = o1.getVer();

        if(i1>i2){ // order by asc
            return 1;
        } else if(i1<i2){
            return -1;
        } else {
            return 0;
        }
    }
}
