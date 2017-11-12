package View.MainView;

import Control.GUIController;
import Control.JsonController;
import Control.MainController;
import Control.NetworkController;
import Model.StaticModel.Pallate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by skrud on 2017-10-07.
 */
public class DisplayContentPanel extends JPanel {
    private final static int HEADERHEIGHET = 80;
    private final static int XMARGIN = 100;
    private final static int YMARGIN = 50;
    private final static int NOTICEWIDTH = 600;
    private final static int NOTICEHEIGHT = 600;
    private static final int LABELHEIGHT = 60;
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

    private JComboBox<String> accountCombo;

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
        componentListener();
        setVisible(true);
    }
    private void componentListener() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    private void initNotice() {
        noticePanel = new NoticePanel(controller);
        noticePanel.setBackground(Pallate.c);
        noticeScrollPanel= new JScrollPane(noticePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        noticeScrollPanel.setBounds(XMARGIN, f.getHeight() - (NOTICEHEIGHT+200), NOTICEWIDTH, NOTICEHEIGHT);
        add(noticeScrollPanel);
        setVisible(true);
    }

    private void initRepo() {
        repoPanel = new NoticePanel(controller);
        repoPanel.setBackground(Pallate.c);
        repoScrollPanel = new JScrollPane(repoPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        repoScrollPanel.setBounds(f.getWidth()-(NOTICEWIDTH+XMARGIN), f.getHeight() - (NOTICEHEIGHT+200), NOTICEWIDTH, NOTICEHEIGHT);
        add(repoScrollPanel);
        setVisible(true);
    }

    private void initHeader(){
        headerPanel = new JPanel();
        headerPanel.setBackground(Pallate.c);
        headerPanel.setBounds(0,0,f.getWidth(),HEADERHEIGHET);
        headerPanel.setLayout(null);

        searchPanel = new SearchPanel(f);
        searchPanel.setBackground(Pallate.c);
        searchPanel.setBounds(XMARGIN,20, 600,HEADERHEIGHET);
        headerPanel.add(searchPanel);


        String account[] = { "Profile", "Setting", "Log Out"};
        accountCombo = new JComboBox<>(account);
        accountCombo.setBounds(f.getWidth()-(150),20,100,headerPanel.getHeight()-40);
        accountCombo.setFont(new Font("Serif", Font.BOLD, 20));
        accountCombo.setAlignmentX(1);
        accountCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=accountCombo.getSelectedIndex();
                switch (i){
                    case 0:
                        gc.newProfileView();
                        break;
                    case 1:
                        System.out.println("세팅"); break;
                    case 2:
                        nc.sendStr(jc.getLogoutStr(controller.getMyAccount().getId()));
                        break;
                }
            }
        });
        accountCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            }
        });
        headerPanel.add(accountCombo);

        add(headerPanel);
    }

    private void initBtn() {
        guideBtn = new JButton("Read the Guide");
        newBtn = new JButton("New repository");

        guideBtn.setBounds(f.getWidth()/2-(200+20), headerPanel.getY()+headerPanel.getHeight()+40, 200,50);
        newBtn.setBounds(f.getWidth()/2+20, headerPanel.getY()+headerPanel.getHeight()+40, 200,50);

        guideBtn.setBackground(Pallate.e);
        guideBtn.setForeground(Color.white);

        newBtn.setBackground(Pallate.e);
        newBtn.setForeground(Color.white);

        guideBtn.setFont(new Font("Serif", Font.BOLD, 15));
        newBtn.setFont(new Font("Serif", Font.BOLD, 15));
        add(guideBtn);
        add(newBtn);
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == newBtn){
                    gc.projectNameView();
                }
            }
        });
    }

    private void initNameLabel(){
        JLabel repoNameLabel = new JLabel("Your Repository");
        repoNameLabel.setBounds(repoScrollPanel.getX(),repoScrollPanel.getY()-LABELHEIGHT,NOTICEWIDTH,LABELHEIGHT);
        repoNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        repoNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        repoNameLabel.setBackground(Pallate.a);
        add(repoNameLabel);

        JLabel notiNameLabel = new JLabel("Search Result");
        notiNameLabel.setBounds(noticeScrollPanel.getX(),noticeScrollPanel.getY()-LABELHEIGHT,NOTICEWIDTH,LABELHEIGHT);
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
}
