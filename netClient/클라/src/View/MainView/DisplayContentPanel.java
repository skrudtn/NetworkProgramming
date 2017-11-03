package View.MainView;

import Control.GUIController;
import Control.MainController;

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
    private MainController mc;
    private GUIController gc;
    private JPanel headerPanel;
    private JScrollPane noticeScrollPanel;
    private JScrollPane repoScrollPanel;

    private SearchPanel searchPanel;
    private NoticePanel noticePanel;
    private NoticePanel reposiPanel;

    private JButton guideBtn;
    private JButton newBtn;

    private JLabel repoNameLabel;
    private JLabel notiNameLabel;

    private JComboBox<String> accountCombo;

    public DisplayContentPanel(MainFrame f){
        this.f = f;
        this.mc = f.getController();
        this.gc = mc.getGUIController();

        initUI();
    }
    private void initUI(){
        setBackground(new Color(255, 254, 238));
        setLayout(null);
        initHeader();
        initReposi();
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

    public void initNotice() {
        noticePanel = new NoticePanel(mc);
        noticePanel.setBackground(new Color(254, 248, 203));
        noticeScrollPanel= new JScrollPane(noticePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        noticeScrollPanel.setBounds(XMARGIN, f.getHeight() - (NOTICEHEIGHT+200), NOTICEWIDTH, NOTICEHEIGHT);
        add(noticeScrollPanel);
        setVisible(true);
    }

    private void initReposi() {
        reposiPanel = new NoticePanel(mc);
        reposiPanel.setBackground(new Color(254, 248, 203));
        repoScrollPanel = new JScrollPane(reposiPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        repoScrollPanel.setBounds(f.getWidth()-(NOTICEWIDTH+XMARGIN), f.getHeight() - (NOTICEHEIGHT+200), NOTICEWIDTH, NOTICEHEIGHT);
        add(repoScrollPanel);
        setVisible(true);
    }

    private void initHeader(){
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(254, 248, 203));
        headerPanel.setBounds(0,0,f.getWidth(),HEADERHEIGHET);
        headerPanel.setLayout(null);

        searchPanel = new SearchPanel(f);
        searchPanel.setBackground(new Color(254, 248, 203));
        searchPanel.setBounds(XMARGIN,20, 600,HEADERHEIGHET);
        headerPanel.add(searchPanel);


        String account[] = { "Profile", "Setting", "Sign Out"};
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
                        System.out.println("프로필"); break;
                    case 1:
                        System.out.println("세팅"); break;
                    case 2:
                        gc.signOut();
                        System.out.println("로그아웃"); break;
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
        newBtn = new JButton("Start a Project");

        guideBtn.setBounds(noticeScrollPanel.getX()+noticeScrollPanel.getWidth()+20, noticeScrollPanel.getY(), 150,50);
        newBtn.setBounds(guideBtn.getX()+guideBtn.getWidth()+20, noticeScrollPanel.getY(), 150,50);

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
        repoNameLabel = new JLabel("Repository");
        repoNameLabel.setBounds(repoScrollPanel.getX(),repoScrollPanel.getY()-LABELHEIGHT,NOTICEWIDTH,LABELHEIGHT);
        repoNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        repoNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        repoNameLabel.setBackground(new Color(254, 248, 203));
        add(repoNameLabel);
        notiNameLabel = new JLabel("Search Result");
        notiNameLabel.setBounds(noticeScrollPanel.getX(),noticeScrollPanel.getY()-LABELHEIGHT,NOTICEWIDTH,LABELHEIGHT);
        notiNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        notiNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        notiNameLabel.setBackground(new Color(254, 248, 203));
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

    public NoticePanel getReposiPanel() {
        return reposiPanel;
    }

    public void setReposiPanel(NoticePanel reposiPanel) {
        this.reposiPanel = reposiPanel;
    }
}
