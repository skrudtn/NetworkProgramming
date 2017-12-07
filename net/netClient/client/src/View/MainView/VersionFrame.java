package View.MainView;

import Control.MainController;
import Control.UMLController;
import Control.VersionComparator;
import Model.RepoModel;
import Model.StaticModel.MyFont;
import Model.StaticModel.MyImage;
import Model.StaticModel.Pallate;
import Model.StaticModel.Size;
import Model.VersionModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by skrud on 2017-11-17.
 */
public class VersionFrame extends JFrame {
    private MainController controller;
    private UMLController uc;
    private RepoModel repoModel;
    private JScrollPane verScrollPanel;
    private JPanel conPanel;
    private JTable table;
    private JButton newBtn;
    private JButton addBtn;
    private Vector<String> rows;

    public VersionFrame(MainController controller){
        this.controller = controller;
        uc = controller.getUmlController();
        this.repoModel = controller.getUmlController().getRepoModel();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(Size.VERFRAMEWIDTH, Size.VERFRAMEHEIGHT);
        setTitle(repoModel.getCreateBy()+"/"+repoModel.getRepoName());
        setResizable(false);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        conPanel = new JPanel();
        conPanel.setLayout(null);
        conPanel.setBackground(Pallate.a);
        setContentPane(conPanel);
        initBtn();
        initTable();
        actionListener();
        setVisible(true);
    }

    private void initBtn() {
        newBtn = new JButton("New");
        addBtn = new JButton("Member Manage");
        newBtn.setBounds(Size.VERFRAMEWIDTH-Size.BACKBTNWIDTH-5,5,Size.BACKBTNWIDTH-10,Size.SIGNUP_XMARGIN-10);
        newBtn.setFont(MyFont.serif18);
        newBtn.setBackground(Pallate.a);
        addBtn.setBounds(5,5,Size.CLAZZWIDTH,Size.SIGNUP_XMARGIN-10);
        addBtn.setFont(MyFont.serif18);
        addBtn.setBackground(Pallate.a);

        conPanel.add(newBtn);
        conPanel.add(addBtn);
    }

    private void initTable() {
        Vector<String> column = new Vector<>();
        column.add("Name");
        column.add("Date");
        column.add("Modified By");
        DefaultTableModel model = new DefaultTableModel(column,0);

        table = new JTable(model){
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {return false;};
        };
        table.setRowHeight(30);
        table.setFont(MyFont.serif20);
        table.setFillsViewportHeight(true);
        for(VersionModel vm: repoModel.getVersions()) {
            System.out.println(vm.getVer());
        }
        VersionComparator comp = new VersionComparator();
        repoModel.getVersions().sort(comp);
        for(VersionModel vm: repoModel.getVersions()){
            System.out.println(vm.getVer());
            rows = new Vector<>();
            rows.addElement(vm.getName());
            rows.addElement(vm.getReg_date());
            rows.addElement(vm.getModifiedBy());
            model.addRow(rows);
        }
        verScrollPanel= new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        verScrollPanel.setBounds(0,Size.SIGNUP_XMARGIN,Size.VERFRAMEWIDTH-5, Size.VERFRAMEHEIGHT-(Size.SIGNUP_XMARGIN+32));
        conPanel.add(verScrollPanel);

    }

    private void actionListener() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    String ver = (String) table.getValueAt(row,0);
                    controller.getNetworkController().sendStr(controller.getJsonController().getCloneStr(ver,String.valueOf(controller.getUmlController().getRepoModel().getRepoNo())));
                }
            }
        });
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==newBtn){
                    controller.getGUIController().drawView(getTitle());
                }
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==addBtn){
                    controller.getGUIController().newMemberManageFrame();
                }
            }
        });
    }

    public void noPermission(){
        newBtn.setVisible(false);
        addBtn.setVisible(false);
        verScrollPanel.setBounds(0,0,Size.VERFRAMEWIDTH-5, Size.VERFRAMEHEIGHT-(32));
    }
    public void permission(){
        newBtn.setVisible(true);
        addBtn.setVisible(true);
        verScrollPanel.setBounds(0,Size.SIGNUP_XMARGIN,Size.VERFRAMEWIDTH-5, Size.VERFRAMEHEIGHT-(Size.SIGNUP_XMARGIN+32));
    }
    private void addRow(VersionModel versionModel){
        rows = new Vector<String>();
        rows.addElement(versionModel.getName());
        rows.addElement(versionModel.getReg_date());
        rows.addElement(versionModel.getModifiedBy());
    }
}
