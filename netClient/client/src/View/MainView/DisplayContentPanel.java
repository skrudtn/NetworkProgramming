package View.MainView;

import Control.GUIController;
import Control.JsonController;
import Control.MainController;
import Control.NetworkController;
import Model.StaticModel.MyFont;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by skrud on 2017-10-07.
 */
public class DisplayContentPanel extends JPanel {
    private MainFrame f;
    private MainController controller;
    private GUIController gc;
    private JsonController jc;
    private NetworkController nc;

    private JPanel headerPanel;
    private JScrollPane noticeScrollPanel;
    private JScrollPane repoScrollPanel;

    private SearchPanel searchPanel;
    private NoticePanel noticePanel;
    private NoticePanel repoPanel;

    private JButton guideBtn;
    private JButton newBtn;
    private JButton accountBtn;


    public DisplayContentPanel(MainFrame f){
        this.f = f;
        this.controller = f.getController();
        this.gc = controller.getGUIController();
        this.jc = controller.getJsonController();
        this.nc = controller.getNetworkController();
        initUI();
    }
    private void initUI(){
        setBackground(Pallate.a);
        setLayout(null);
        initHeader();
        initRepo();
        initNotice();
        initBtn();
        initNameLabel();
        setVisible(true);
    }

    private void initNotice() {
        noticePanel = new NoticePanel(controller);
        noticePanel.setBackground(new Color(253, 255, 237));
        noticeScrollPanel= new JScrollPane(noticePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        noticeScrollPanel.setBounds(Size.DISPALYXMARGIN, f.getHeight() - (Size.NOTICEHEIGHT+200), Size.NOTICEWIDTH, Size.NOTICEHEIGHT);
        add(noticeScrollPanel);
        setVisible(true);
    }

    private void initRepo() {
        repoPanel = new NoticePanel(controller);
        repoPanel.setBackground(new Color(253, 255, 237));
        repoScrollPanel = new JScrollPane(repoPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        repoScrollPanel.setBounds(f.getWidth()-(Size.NOTICEWIDTH+Size.DISPALYXMARGIN), f.getHeight() - (Size.NOTICEHEIGHT+200), Size.NOTICEWIDTH, Size.NOTICEHEIGHT);
        add(repoScrollPanel);
        setVisible(true);
    }
    private void initHeader(){
        headerPanel = new JPanel();
        headerPanel.setBackground(Pallate.c);
        headerPanel.setBounds(0,0,f.getWidth(), Size.HEADERHEIGHET);
        headerPanel.setLayout(null);

        searchPanel = new SearchPanel(f);
        searchPanel.setBackground(Pallate.c);
        searchPanel.setBounds(Size.DISPALYXMARGIN,20, Size.SEARCHPANEWIDTH,Size.HEADERHEIGHET);
        headerPanel.add(searchPanel);


        add(headerPanel);
    }

    private void initBtn() {
        guideBtn = new JButton("Read the Guide");
        newBtn = new JButton("New repository");
        accountBtn = new JButton(getImage("client\\Image\\profileBtn.png",Size.HEADERHEIGHET,Size.HEADERHEIGHET-10));

        guideBtn.setBounds(f.getWidth()/2-(200+20), headerPanel.getY()+headerPanel.getHeight()+40,Size.MENUBARWIDTH,50);
        newBtn.setBounds(f.getWidth()/2+20, headerPanel.getY()+headerPanel.getHeight()+40, Size.MENUBARWIDTH,50);
        accountBtn.setBounds(f.getWidth()-(100),5,Size.HEADERHEIGHET,Size.HEADERHEIGHET-5);

        guideBtn.setBackground(Pallate.e);
        guideBtn.setForeground(Color.white);
        guideBtn.setFont(MyFont.serif);

        newBtn.setFont(MyFont.serif);
        newBtn.setBackground(Pallate.e);
        newBtn.setForeground(Color.white);

        accountBtn.setBorderPainted(false);
        accountBtn.setFocusPainted(false);
        accountBtn.setContentAreaFilled(false);
        accountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        gc.newProfileView();
            }
        });
        headerPanel.add(accountBtn);
        add(guideBtn);
        add(newBtn);
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == newBtn){
                    gc.newRepoView();
                }
            }
        });
    }

    private void initNameLabel(){
        JLabel repoNameLabel = new JLabel("Your Repository");
        repoNameLabel.setBounds(repoScrollPanel.getX(),repoScrollPanel.getY()-Size.LABELHEIGHT,Size.NOTICEWIDTH,Size.LABELHEIGHT);
        repoNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        repoNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        repoNameLabel.setBackground(Pallate.a);
        add(repoNameLabel);

        JLabel notiNameLabel = new JLabel("Search Result");
        notiNameLabel.setBounds(noticeScrollPanel.getX(),noticeScrollPanel.getY()-Size.LABELHEIGHT,Size.NOTICEWIDTH,Size.LABELHEIGHT);
        notiNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        notiNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        notiNameLabel.setBackground(Pallate.a);
        add(notiNameLabel);
    }
    public NoticePanel getNoticePanel() {
        return noticePanel;
    }

    public JScrollPane getNoticeScrollPanel() {
        return noticeScrollPanel;
    }
    public JScrollPane getRepoScrollPanel() {
        return repoScrollPanel;
    }

    public NoticePanel getRepoPanel() {
        return repoPanel;
    }

    public void setRepoPanel(NoticePanel repoPanel) {
        this.repoPanel = repoPanel;
    }

    private ImageIcon getImage(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image changeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(changeImage);
    }
}
