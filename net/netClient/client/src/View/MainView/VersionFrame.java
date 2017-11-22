package View.MainView;

import Control.MainController;
import Control.UMLController;
import Model.RepoModel;
import Model.StaticModel.MyFont;
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
        setTitle(controller.getLoginController().getMyAccount().getId()+"/"+repoModel.getRepoName());
        setResizable(false);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        conPanel = new JPanel();
        conPanel.setLayout(null);
        conPanel.setBackground(Pallate.e);
        setContentPane(conPanel);
        initBtn();
        initTable();
        actionListener();
        setVisible(true);
    }

    private void initBtn() {
        newBtn = new JButton("New");
        addBtn = new JButton("Member Manage");
        newBtn.setBounds(Size.VERFRAMEWIDTH-Size.BACKBTNWIDTH-5,5,Size.BACKBTNWIDTH-10,Size.SUXMARGIN-10);
        addBtn.setBounds(5,5,Size.CLAZZWIDTH,Size.SUXMARGIN-10);
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
        for(VersionModel vm: repoModel.getVersions()){
            rows = new Vector<String>();
            rows.addElement(vm.getName());
            rows.addElement(vm.getReg_date());
            rows.addElement(vm.getModifiedBy());
            model.addRow(rows);
        }
        verScrollPanel= new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        verScrollPanel.setBounds(0,Size.SUXMARGIN,Size.VERFRAMEWIDTH-5, Size.VERFRAMEHEIGHT-(Size.SUXMARGIN+32));
        conPanel.add(verScrollPanel);

    }

    private void actionListener() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    String ver = (String) table.getValueAt(row,0);
                    controller.getNetworkController().sendStr(controller.getJsonController().getCloneStr(ver));
                }
            }
        });
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==newBtn){
                    System.out.println(controller.getUmlController().getRepoModel().getRepoName());
                    controller.getGUIController().drawView();
                }
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==addBtn){
                    System.out.println("멤버관리");
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
        verScrollPanel.setBounds(0,Size.SUXMARGIN,Size.VERFRAMEWIDTH-5, Size.VERFRAMEHEIGHT-(Size.SUXMARGIN+32));
    }
    private void addRow(VersionModel versionModel){
        rows = new Vector<String>();
        rows.addElement(versionModel.getName());
        rows.addElement(versionModel.getReg_date());
        rows.addElement(versionModel.getModifiedBy());
    }
}
