package View.MainView;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by skrud on 2017-10-26.
 */
public class FileDia {
    private JFrame f;
    private FileDialog fileDialog;
    private final static String dir = "D://네프클라//클라//FileDir";

    public FileDia(int flag)//flag 0이면 오픈 1이면 저장
    {
        f=new JFrame();

        if (flag == 0) {//open
            fileDialog = new FileDialog(f,"열기",flag);
            fileDialog.setDirectory(dir);
        } else if (flag == 1) {//save
            fileDialog = new FileDialog(f,"저장",flag);
            fileDialog.setDirectory(dir);
        }

        fileDialog.setVisible(true);
    }


}
