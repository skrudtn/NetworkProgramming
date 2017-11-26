package Control;

import Model.StaticModel.MyImage;

/**
 * Created by skrud on 2017-11-26.
 */
public class ImageLoadThread implements Runnable {
    @Override
    public void run() {
        MyImage.loadImageFile();
    }
}
