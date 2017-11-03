package Control;

import Model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-01.
 */
public class DBController {
    private static final String DRIVER
            = "oracle.jdbc.driver.OracleDriver";
    private final String URL
            = "jdbc:oracle:thin:@localhost:1521:orcl";
    private final String USER = "ksna";
    private final String PW = "ksna";

    private Connection con = null;

    public DBController(MainController controller) {
        con = getConn();
    }

    private Connection getConn() {
        Connection con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PW);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public boolean signup(LoginModel dbdata) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "INSERT INTO MEMBERS (MEMNO, id, password, name, email, reg_date)" +
                    "VALUES (MEMNO.nextval, '" + dbdata.getId() + "', '" + dbdata.getPw() + "', '" + dbdata.getName() + "', '" + dbdata.getEmail() + "', SYSDATE)";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public LoginModel loginDB(LoginModel dbdata) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "";
        LoginModel rt = new LoginModel();
        try {
            sql = "SELECT * FROM MEMBERS WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dbdata.getId());
            rs = ps.executeQuery();


            if (rs.next()) {
                if (!(rs.getString("id").equals(""))) {
                    //아이디가 같으면
                    rt.setId(rs.getString("id"));
                    rt.setPw(rs.getString("password"));
                    rt.setEmail(rs.getString("email"));
                    rt.setName(rs.getString("name"));
                }
            }
        } catch (
                SQLException e)

        {
            e.printStackTrace();
        } finally

        {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public boolean pwDB(LoginModel dbdata) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "UPDATE MEMBERS SET PASSWORD = ? WHERE ID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dbdata.getPw());
            ps.setString(2, dbdata.getId());
            rs = ps.executeQuery();
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public Boolean overLapDB(String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        Boolean rt = true;
        try {
            sql = "SELECT id FROM MEMBERS WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                rt = false;//""이면 중복아님
            }
        } catch (
                SQLException e)

        {
            e.printStackTrace();
        } finally

        {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public boolean insertCDData(CDModel cdm) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        Boolean rt = true;
        ArrayList<ClazzModel> clazzModels = cdm.getClazzModels();

        try {
            String memNo = "";
            sql = "SELECT MEMNO FROM MEMBERS WHERE MEMBERS.ID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, cdm.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                memNo = rs.getString("MEMNO");
            }
            sql = "INSERT INTO CD (CDNO, CDNAME, REG_DATE, MEMBERNO)" +
                    "VALUES (CDNO.nextval, '" + cdm.getCdName() + "'," + "SYSDATE" + ",'" + memNo + "')";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            sql = "SELECT CDNO.currval FROM CD";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            String cdNO = "";
            if (rs.next()) {
                cdNO = rs.getString(1);
            }

            ArrayList<Association> acList = cdm.getAcList();
            for (Association ac : acList) {
                sql = "INSERT INTO CDASSOCI(ACNO, POINT, REG_DATE, CDNO)" +
                        " VALUES (ACNO.nextval, ?," + "SYSDATE" + ",?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, ac.getPoint());
                ps.setString(2, cdNO);
                rs = ps.executeQuery();
            }


            for (ClazzModel c : clazzModels) {

                sql = "INSERT INTO CLAZZ (CLNO, CLNAME, REG_DATE, CDNO, BOUNDS)" +
                        " VALUES (CLNO.nextval, ?," + "SYSDATE" + ",?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, c.getClassName());
                ps.setString(2, cdNO);
                ps.setString(3, c.getBounds());
                rs = ps.executeQuery();

                sql = "SELECT CLNO.currval FROM CLAZZ";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                String clNO = "";
                if (rs.next()) {
                    clNO = rs.getString(1);
                }
                ArrayList<String> atts = c.getAttributeList();
                for (String att : atts) {
                    sql = "INSERT INTO CDATT(ATTNO, ATTNAME, REG_DATE, CLNO)" +
                            " VALUES (ATTNO.nextval,?," + "SYSDATE" + ",?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, att);
                    ps.setString(2, clNO);
                    rs = ps.executeQuery();
                }

                ArrayList<String> mets = c.getMethodList();
                for (String met : mets) {
                    sql = "INSERT INTO CDMET(METNO, METNAME, REG_DATE, CLNO)" +
                            " VALUES (METNO.nextval,?," + "SYSDATE" + ",?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, met);
                    ps.setString(2, clNO);
                    rs = ps.executeQuery();
                }
            }
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally

        {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public ArrayList<SearchModel> search(SearchPacket sm) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";

        ArrayList<SearchModel> sdms = new ArrayList<>();
        String data = sm.getData();
        String cate = sm.getCategory();

        try {
            if (cate.equals("Title")) {
                data = data.replace("!", "!!")
                        .replace("%", "!%")
                        .replace("_", "!_")
                        .replace("[", "![");
                sql = "SELECT CDNAME,id,TO_CHAR(CD.reg_date,'yyyy-mm-dd hh24:mi:ss') r_date, cd.CDNO\n" +
                        "FROM CD, MEMBERS\n" +
                        "WHERE CD.memberNo = MEMBERS.memNO AND\n" +
                        "  cdName LIKE ? ESCAPE '!'";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + data + "%");
            } else if (cate.equals("UserId")) {
                sql = "SELECT CDNAME,id,TO_CHAR(CD.reg_date,'yyyy-mm-dd hh24:mi:ss') r_date, cd.CDNO\n" +
                        "FROM CD, MEMBERS\n" +
                        "WHERE CD.memberNo = MEMBERS.memNO AND\n" +
                        "MEMBERS.ID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, data);
            }
            rs = ps.executeQuery();

            SearchModel sdm = null;
            while (rs.next()) {
                sdm = new SearchModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                sdms.add(sdm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return sdms;
    }

    public CDModel cllone(CllonePacket cp) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        int cdNo = Integer.parseInt(cp.getCdNo());
        CDModel cdm = new CDModel(cp.getId(), cp.getTitle());

        try {
            sql = "SELECT cl.clName, att.attName, met.metName, cl.CLNO, cl.BOUNDS\n" +
                    "  FROM CD cd, CLAZZ cl, CDATT att, CDMET met\n" +
                    "WHERE cd.cdNO = cl.cdNO and cl.clNO = att.clNO and cl.clNO = met.clNo \n" +
                    "and cd.cdNO = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cdNo);
            rs = ps.executeQuery();
            ClazzModel cm = new ClazzModel();
            int clNo = 0;
            while (rs.next()) {
                if (rs.getInt(4) != clNo) {
                    cm = new ClazzModel();
                    cdm.addClazzModel(cm);
                }
                cm.setClassName(rs.getString(1));
                cm.addAttList(rs.getString(2));
                cm.addMethodList(rs.getString(3));
                clNo = rs.getInt(4);
                cm.setBounds(rs.getString(5));
            }
            sql = "SELECT CDASSOCI.POINT\n" +
                    "FROM CDASSOCI, CD\n" +
                    "WHERE CD.CDNO = CDASSOCI.CDNO\n" +
                    "AND CD.cdNO = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cdNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Association ac = new Association();
                ac.setPoint(rs.getString(1));
                System.out.println(rs.getString(1));
                cdm.addAcList(ac);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return cdm;
    }


    public void closeConnect() {
        try {
            con.close();
        } catch (Exception con) {

        }
    }
}
