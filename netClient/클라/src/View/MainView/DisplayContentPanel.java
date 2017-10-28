package View.MainView;

import Control.GUIController;
import Control.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by skrud on 2017-10-07.
 */
public class DisplayContentPanel extends JPanel {
    private final static int HEADERHEIGHET = 80;
    private final static int XMARGIN = 100;
    private final static int YMARGIN = 50;
    private final static int REPOSIWIDTH = 400;
    private final static int REPOSIHEIGHT = 600;
    private final static int NOTICEWIDTH = 600;
    private final static int NOTICEHEIGHT = 600;
    private MainFrame f;
    private MainController mc;
    private GUIController gc;
    private JPanel headerPanel;
    private JPanel reposiPanel;
    JScrollPane scrollPane;
    private SearchPanel searchPanel;
    private NoticePanel noticePanel;
    private JButton guideBtn;
    private JButton newBtn;


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
        componentListener();
        setVisible(true);
    }
    private void componentListener() {
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposiPanel.setBounds(f.getWidth()-(REPOSIWIDTH+100),headerPanel.getHeight()+YMARGIN,REPOSIWIDTH,REPOSIHEIGHT);
                noticePanel.setBounds(XMARGIN, headerPanel.getHeight()+YMARGIN, NOTICEWIDTH, NOTICEHEIGHT);
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
        JLabel nameLabel = new JLabel("Result Board");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameLabel.setBackground(new Color(254, 248, 203));
        nameLabel.setBounds(0,0,NOTICEWIDTH,60);

        noticePanel = new NoticePanel(mc);
        noticePanel.setBounds(XMARGIN, f.getHeight() - (NOTICEHEIGHT+200), NOTICEWIDTH, NOTICEHEIGHT);
        noticePanel.setBackground(new Color(254, 248, 203));
        noticePanel.add(nameLabel);
        add(noticePanel);

        /*
        scrollPane = new JScrollPane(noticePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        */
        setVisible(true);
    }

    private void initReposi() {
//        ImageIcon icon = new ImageIcon("Image/sea.jpg");
//        reposiPanel = new JPanel(){
//            public void paintComponent(Graphics g){
//                g.drawImage(icon.getImage(),0,0,null);
//                setOpaque(false);
//                super.paintComponent(g);
//            }
//        };
        reposiPanel = new JPanel();
        reposiPanel.setBackground(new Color(254, 248, 203));
        reposiPanel.setLayout(null);

        JLabel nameLabel = new JLabel("  repositories");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameLabel.setBackground(new Color(254, 248, 203));
        nameLabel.setBounds(0,0,REPOSIWIDTH,60);
        reposiPanel.add(nameLabel);

        add(reposiPanel);
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
        accountCombo.setAlignmentX(1);
        headerPanel.add(accountCombo);

        add(headerPanel);
    }

    private void initBtn() {
        guideBtn = new JButton("Read the Guide");
        newBtn = new JButton("Start a Project");

        guideBtn.setBounds(noticePanel.getX()+noticePanel.getWidth()+20, noticePanel.getY(), 150,50);
//        guideBtn.setBounds(0,0, 100,60);
        newBtn.setBounds(guideBtn.getX()+guideBtn.getWidth()+20, noticePanel.getY(), 150,50);

        add(guideBtn);
        add(newBtn);
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == newBtn){
                    gc.drawView();
                }
            }
        });
    }


    public NoticePanel getNoticePanel() {
        return noticePanel;
    }


}
