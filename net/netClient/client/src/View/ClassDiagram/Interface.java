package View.ClassDiagram;

import Model.StaticModel.MyFont;
import View.MainView.DrawPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-12.
 */
public class Interface extends Clazz {

    public Interface(DrawPanel dp, int mousex, int mousey) {
        super(dp, mousex, mousey);
        this.nameTextField.setVisible(false);
        this.nameLabel.setText("<<Interface>>");
        this.nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.nameLabel.setFont(MyFont.serif);
        this.nameLabel.setBorder(null);
        this.nameLabel.setForeground(Color.BLACK);
        this.attTextField.setText("name");
        this.attBtn.setVisible(false);
    }



}
