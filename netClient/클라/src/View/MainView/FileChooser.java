package View.MainView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Created by skrud on 2017-10-26.
 */
public class FileChooser {
    private JFrame frm = new JFrame();
    String userDir = System.getProperty("user.home");
    private JFileChooser fileChooser = new JFileChooser("D://네프클라//클라//FileDir");
    public FileChooser(int flag)//flag 0이면 오픈 1이면 저장
    {

        fileChooser.setFileFilter(new FileNameExtensionFilter(".dat", ".dat"));
        if (flag == 0) {//open
            int returnVal = fileChooser.showOpenDialog(frm);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(fileChooser.getSelectedFile().toString());
                    ObjectInputStream ois = new ObjectInputStream(fis);


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//                File file = fileChooser.getSelectedFile();
            } else {
                System.out.println("취소합니다");
            }
        } else if (flag == 1) {//save
            int returnVal = fileChooser.showSaveDialog(frm);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //File file = fileChooser.getSelectedFile();
//                Project project = MainSystem.pm.getProject();


                try {
                    FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile().toString() + fileChooser.getFileFilter().getDescription());
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
//                    oos.writeObject(project);
                    oos.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println("취소합니다");
            }
        }

    }


}
