package View.MainView;

import Model.StaticModel.MyFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by skrud on 2017-10-27.
 */
public class SearchPanel extends JPanel {
    private MainFrame df;
    private JTextField searchField;
    private JComboBox<String> searchCombo;
    private String category[];

    public SearchPanel(MainFrame df) {
        this.df = df;
//        this.setLayout(null);
        this.setOpaque(false);
        initComboBox();
        initTextField();
        setVisible(true);
    }

    private void initTextField() {
        searchField = new JTextField(20);
        searchField.setFont(MyFont.serif28);
        searchField.setForeground(Color.BLACK);
//        searchField.setOpaque(false);
//        searchField.setBounds(searchCombo.getX()+searchField.getWidth()+20,searchCombo.getY(),120,searchCombo.getHeight());
        searchField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onPressOk();
                }
            }
        });
        add(searchField);
    }

    private void onPressOk() {
        String data = searchField.getText();
        String cate = category[searchCombo.getSelectedIndex()];
        String str = df.getController().getJsonController().getSearchStr(data, cate);
        if(!data.equals("")) df.getController().getNetworkController().sendStr(str);
        else {
            df.getController().setSdms(null);
            df.getController().getGUIController().searchUpdate();
        }
    }

    private void initComboBox() {
        category = new String[]{
                "Title", "UserID"
        };
        searchCombo = new JComboBox<>(category);
        searchCombo.setFont(MyFont.serif25);
        searchCombo.setBorder(new EmptyBorder(1,1,1,1));
//        searchCombo.setBounds(0,0,120,getHeight()-40);
        searchCombo.setAlignmentX(1);
        add(searchCombo);
    }
}
