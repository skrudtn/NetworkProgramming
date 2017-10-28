package View.MainView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

/**
 * Created by skrud on 2017-10-27.
 */
public class ResultPanel extends JPanel {
    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JLabel getIdLabel() {
        return idLabel;
    }

    public JLabel getDateLabel() {
        return dateLabel;
    }

    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel dateLabel;

    public ResultPanel(String title, String id, String date){
        titleLabel = new JLabel();
        idLabel = new JLabel();
        dateLabel = new JLabel();

        setSize(getWidth(),getHeight());
        setBorder(new LineBorder(Color.BLACK,1));
        setBackground(Color.WHITE);
        titleLabel.setBounds(0,0,getWidth(),getHeight()/3);
        idLabel.setBounds(0,titleLabel.getHeight(),getWidth(),getHeight()/3);
        dateLabel.setBounds(0,idLabel.getY()+idLabel.getHeight(),getWidth(),getHeight()/3);

        titleLabel.setText(title);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        idLabel.setText(id);
        dateLabel.setText(date);

        add(titleLabel);
        add(idLabel);
        add(dateLabel);

        action();
        setLayout(new GridLayout(3,1));
        setVisible(true);
    }

    private void action(){
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == titleLabel){
                    System.out.println("titleLabel");
                }
            }
        });
        idLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == titleLabel){
                    System.out.println("id");
                }
            }
        });
        dateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getSource() == titleLabel){
                    System.out.println("date");
                }
            }
        });
    }


}
