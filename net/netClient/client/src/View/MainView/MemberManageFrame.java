package View.MainView;

import Control.MainController;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by skrud on 2017-11-25.
 */
public class MemberManageFrame extends JFrame{
    private MainController controller;
    private JPanel conPanel;
    private JScrollPane friScroll;
    private JList fList;
    private JScrollPane memScroll;
    private JList mList;
    private JButton rBtn;
    private JButton lBtn;
    private JButton commitBtn;
    private ArrayList<String> friends;
    private ArrayList<String> members;
    private JLabel fLabel;
    private JLabel mLabel;
    private Vector<String> fVector;
    private Vector<String> mVector;
    public MemberManageFrame(MainController controller){
        this.controller = controller;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(Size.VERFRAMEWIDTH, Size.VERFRAMEHEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        this.setTitle("Member Manage");
        friends = controller.getFriends();
        members=controller.getUmlController().getRepoModel().getMembers();
        fVector = new Vector<>();
        mVector = new Vector<>();

        for(String str: friends){
            fVector.add(str);
        }
        for(String str:members){
            mVector.add(str);
        }
        fVector.removeAll(mVector);

        conPanel = new JPanel();
        conPanel.setBackground(Pallate.a);
        conPanel.setLayout(null);
        setContentPane(conPanel);
        initUI();
        this.setVisible(true);
    }

    public void initUI(){
        initLabel();
        initList();
        initBtn();
        addAction();
    }

    private void initLabel() {
        fLabel = new JLabel("Friends");
        mLabel = new JLabel("Members");
        fLabel.setBounds(Size.SIGNUP_XMARGIN,Size.SIGNUP_XMARGIN,Size.MENUBARWIDTH,Size.SIGNUP_XMARGIN);
        fLabel.setFont(MyFont.serif);
        mLabel.setFont(MyFont.serif);
        mLabel.setBounds(getWidth()/2+Size.SIGNUP_XMARGIN,Size.SIGNUP_XMARGIN,Size.MENUBARWIDTH,Size.SIGNUP_XMARGIN);
        conPanel.add(fLabel);
        conPanel.add(mLabel);

    }

    private void initBtn() {
        rBtn = new JButton(MyImage.btn_r);
        lBtn = new JButton(MyImage.btn_l);
        commitBtn = new JButton("Save");
        rBtn.setBounds(getWidth()/2-Size.RBTNW/2+20,friScroll.getY()+friScroll.getHeight()/2-Size.RBTNH*2,Size.RBTNW,Size.RBTNH);
        lBtn.setBounds(rBtn.getX(),rBtn.getY()+Size.RBTNH*3,Size.RBTNW,Size.RBTNH);
        commitBtn.setBounds(getWidth()-Size.BACKBTNWIDTH-20,getHeight()-(Size.BACKBTNHEIGHT+30),Size.BACKBTNWIDTH,Size.BACKBTNHEIGHT-10);
        commitBtn.setFont(MyFont.serif18);
        commitBtn.setBackground(Pallate.a);
        rBtn.setBorderPainted(false);
        rBtn.setFocusPainted(false);
        rBtn.setContentAreaFilled(false);
        lBtn.setBorderPainted(false);
        lBtn.setFocusPainted(false);
        lBtn.setContentAreaFilled(false);


        conPanel.add(rBtn);
        conPanel.add(lBtn);
        conPanel.add(commitBtn);
    }

    void initList(){
        fList = new JList(fVector);
        fList.setFont(MyFont.serif20);
        friScroll = new JScrollPane(fList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mList = new JList(mVector);
        mList.setFont(MyFont.serif20);
        memScroll = new JScrollPane(mList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        friScroll.setBounds(fLabel.getX(),fLabel.getY()+fLabel.getHeight(), getWidth()/2-100,getWidth()/2-100);
        memScroll.setBounds(mLabel.getX(),fLabel.getY()+fLabel.getHeight(), getWidth()/2-100,getWidth()/2-100);

        conPanel.add(friScroll);
        conPanel.add(memScroll);
    }
    void setList(){
        fList.setListData(fVector);
        friScroll.setViewportView(fList);
        mList.setListData(mVector);
        memScroll.setViewportView(mList);
    }

    void addAction(){
        rBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == rBtn){
                    if(!fList.isSelectionEmpty()) {
                        ArrayList<String> selectList= (ArrayList<String>) fList.getSelectedValuesList();
                        mVector.addAll(selectList);
                        fVector.removeAll(selectList);
                        setList();
                    }
                }
            }
        });
        lBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == lBtn){
                    if(!mList.isSelectionEmpty()) {
                        ArrayList<String> selectList= (ArrayList<String>) mList.getSelectedValuesList();
                        mVector.removeAll(selectList);
                        fVector.addAll(selectList);
                        setList();
                    }
                }
            }
        });
        commitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==commitBtn){
                    controller.getNetworkController().sendStr(controller.getJsonController().getMemManageStr(mVector,String.valueOf(controller.getUmlController().getRepoModel().getRepoNo())));
                }
            }
        });
    }

}
