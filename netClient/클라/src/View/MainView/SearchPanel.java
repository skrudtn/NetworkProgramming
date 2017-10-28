package View.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by skrud on 2017-10-27.
 */
public class SearchPanel extends JPanel {
    private MainFrame df;
    private TextField searchField;
    private JComboBox<String> searchCombo;
    private JButton btn;
    private String category[];

    public SearchPanel(MainFrame df) {
        this.df = df;

        setLayout(new FlowLayout(100));
        initComboBox();
        initTextField();
//        initBtn();
        setVisible(true);
    }

    private void initBtn() {
        btn = new JButton("ok");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn) {
                    onPressOk();
                }
            }
        });
        add(btn);
    }

    private void initTextField() {
        searchField = new TextField(30);
        searchField.setFont(new Font("Serif", Font.BOLD, 15));
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
    }

    private void initComboBox() {
        category = new String[]{
                "Title", "Contents", "UserID"
        };
        searchCombo = new JComboBox<>(category);
        searchCombo.setAlignmentX(1);
        add(searchCombo);
    }
}
