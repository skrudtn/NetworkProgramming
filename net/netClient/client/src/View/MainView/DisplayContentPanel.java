package View.MainView;

import Control.*;
import Model.Animation;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by skrud on 2017-10-07.
 */
public class DisplayContentPanel extends JPanel {
    private MainFrame f;
    private MainController controller;
    private GUIController gc;

    private JPanel searchRepoPanel;
    private JPanel yourRepoPanel;
    private JScrollPane noticeScrollPanel;
    private JScrollPane repoScrollPanel;

    private SearchPanel searchPanel;
    private NoticePanel noticePanel;
    private NoticePanel repoPanel;

    private JButton guideBtn;
    private JButton newBtn;
    private JButton accountBtn;
    private JButton statusBtn;
    private JLabel repoNameLabel;
    private JLabel notiNameLabel;

    private Animation animation;

    public DisplayContentPanel(MainFrame f){
        this.f = f;
        this.controller = f.getController();
        this.gc = controller.getGUIController();
        animation = new Animation();
        initUI();
    }
    private void initUI(){
        setBackground(Pallate.a);
        setLayout(null);
        initHeader();
        initNotice();
        initRepo();
        initBtn();
        setVisible(true);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MyImage.displayBgImage.getImage(), 0, 0, null);
    }
    private void initNotice() {
        noticePanel = new NoticePanel(controller);
        noticePanel.setBorder(new EmptyBorder(0,0,0,0));
        noticeScrollPanel= new JScrollPane(noticePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        noticeScrollPanel.setBounds(searchRepoPanel.getX(), searchRepoPanel.getY()+searchRepoPanel.getHeight(), Size.NOTICE_W, Size.NOTICE_H-(Size.SEARCH_HEADER_H1-Size.SEARCH_HEADER_H2));
        noticeScrollPanel.setBorder(new EmptyBorder(0,0,0,0));
        add(noticeScrollPanel);
        setVisible(true);
    }

    private void initRepo() {
        repoPanel = new NoticePanel(controller);
        repoScrollPanel = new JScrollPane(repoPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        repoScrollPanel.setBounds(yourRepoPanel.getX(), yourRepoPanel.getY()+yourRepoPanel.getHeight(), Size.NOTICE_W, Size.NOTICE_H);
        repoScrollPanel.setBorder(new EmptyBorder(0,0,0,0));
        add(repoScrollPanel);
        setVisible(true);
    }
    private void initHeader(){
        searchRepoPanel = new JPanel();
        yourRepoPanel = new JPanel();
        searchRepoPanel.setBounds(Size.DISPALY_XMARGIN,Size.DISPALY_YMARGIN,Size.SEARCH_HEADER_W,Size.SEARCH_HEADER_H1);
        yourRepoPanel.setBounds(searchRepoPanel.getX()+searchRepoPanel.getWidth()+Size.PANEL_BWN,Size.DISPALY_YMARGIN,Size.SEARCH_HEADER_W,Size.SEARCH_HEADER_H2);
        yourRepoPanel.setOpaque(false);
        searchRepoPanel.setOpaque(false);

        repoNameLabel = new JLabel(MyImage.btn_yourRepo);
        repoNameLabel.setBounds(0,0,Size.SEARCH_REPO_W,Size.SEARCH_REPO_H);
        yourRepoPanel.add(repoNameLabel);

        notiNameLabel = new JLabel(MyImage.btn_searchRepo);
        notiNameLabel.setBounds(0,0,Size.SEARCH_REPO_W,Size.SEARCH_REPO_H);
        searchRepoPanel.add(notiNameLabel);


        searchPanel = new SearchPanel(f);
        searchPanel.setBounds(0,100, searchRepoPanel.getWidth(),searchRepoPanel.getHeight()-notiNameLabel.getHeight());
        searchRepoPanel.add(searchPanel);



        add(searchRepoPanel);
        add(yourRepoPanel);
    }

    private void initBtn() {
        guideBtn = new JButton(MyImage.btn_guidebook);
        newBtn = new JButton(MyImage.btn_addRepo);
        accountBtn = new JButton(MyImage.btn_myInfo);
        statusBtn = new JButton(MyImage.btn_myalert);

        newBtn.setBounds(repoNameLabel.getX()+repoNameLabel.getWidth()+30,repoNameLabel.getY()+20,Size.ADDREPO_W,Size.ADDREPO_H);
        newBtn.setFocusPainted(false);
        newBtn.setBorderPainted(false);
        newBtn.setContentAreaFilled(false);

        accountBtn.setBounds(yourRepoPanel.getX()+yourRepoPanel.getWidth()+Size.BTN_BWN,repoScrollPanel.getY()+20,Size.ACC_BTN_W,Size.ACC_BTN_W);
        accountBtn.setFocusPainted(false);
        accountBtn.setBorderPainted(false);
        accountBtn.setContentAreaFilled(false);

        statusBtn.setBounds(accountBtn.getX(),accountBtn.getY()+accountBtn.getHeight()+Size.BTN_BWN,Size.ACC_BTN_W,Size.ACC_BTN_H);
        statusBtn.setBorderPainted(false);
        statusBtn.setFocusPainted(false);
        statusBtn.setContentAreaFilled(false);

        guideBtn.setBounds(accountBtn.getX(),statusBtn.getY()+statusBtn.getHeight()+Size.BTN_BWN,Size.ACC_BTN_W,Size.ACC_BTN_H);
        guideBtn.setBorderPainted(false);
        guideBtn.setFocusPainted(false);
        guideBtn.setContentAreaFilled(false);

        add(guideBtn);
        add(accountBtn);
        add(statusBtn);
        yourRepoPanel.add(newBtn);
        new Thread(new AnimationThread(animation, statusBtn)).start();
        accountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        gc.newProfileView();
            }
        });
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == newBtn){
                    gc.newRepoView();
                }
            }
        });
        statusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==statusBtn){
                    statusChange(true);
                    gc.displayStatusView();
                }
            }
        });


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

    public void statusChange(boolean isNormal){
        if(isNormal){
            animation.setNormal(true);
        } else {
            animation.setNormal(false);
        }
    }

    public Animation getAnimation() {
        return animation;
    }

}
