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

    private JsonController jc;
    private Connection con = null;

    public DBController(MainController controller) {
        jc = controller.getJsonController();
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

    public boolean insertCDData(CDModel cdm, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        Boolean rt = true;
        ArrayList<ClazzModel> clazzModels = cdm.getClazzModels();

        try {
            String memNo = "";
            sql = "SELECT MEMNO FROM MEMBERS WHERE MEMBERS.ID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                memNo = rs.getString("MEMNO");
            }
            sql = "INSERT INTO CD (CDNO, CDNAME, REG_DATE, MEMBERNO)" +
                    "VALUES (CDNO.nextval, '" + cdm.getCdName() + "'," + "SYSDATE" + ",'" + memNo + "')";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            for (ClazzModel c : clazzModels) {
                String cdNo = "";
                sql = "SELECT CDNO FROM CD WHERE CD.CDNAME = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, cdm.getCdName());
                rs = ps.executeQuery();
                if (rs.next()) {
                    cdNo = rs.getString("cdno");
                }

                sql = "INSERT INTO CLAZZ (CLNO, CLNAME, REG_DATE, CDNO)" +
                        " VALUES (CLNO.nextval,'" + c.getClassName() + "'," + "SYSDATE" + ",'" + cdNo + "')";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                String clNo = "";
                ArrayList<String> atts = c.getAttributeList();
                for (String att : atts) {
                    sql = "SELECT CLNO FROM CLAZZ WHERE CLAZZ.CLNAME= ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, c.getClassName());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        clNo = rs.getString("clno");
                    }

                    sql = "INSERT INTO CDATT(ATTNO, ATTNAME, REG_DATE, CLNO)" +
                            " VALUES (ATTNO.nextval,'" + att + "'," + "SYSDATE" + ",'" + clNo + "')";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                }

                ArrayList<String> mets = c.getMethodList();
                for (String met : mets) {
                    sql = "INSERT INTO CDMET(METNO, METNAME, REG_DATE, CLNO)" +
                            " VALUES (METNO.nextval,'" + met + "'," + "SYSDATE" + ",'" + clNo + "')";
                    ps = con.prepareStatement(sql);
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

    public ArrayList<SearchDataModel> search(SearchModel sm) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";

        ArrayList<SearchDataModel> sdms = new ArrayList<>();
        String data = sm.getData();
        String cate = sm.getCategory();

        data = data.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        try {
//            if (cate.equals("Title")) {
            sql = "SELECT CDNAME,id,CD.reg_date\n" +
                    "  FROM CD, MEMBERS\n" +
                    "WHERE CD.memberNo = MEMBERS.memNO AND\n" +
                    "  cdName LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + data + "%");
            rs = ps.executeQuery();
//            } else if (cate.equals("UserID")) {
//                sql = "SELECT * FROM MEMBERS WHERE ID = ? ESCAPE '!'";
//            } else if (cate.equals("Contents")) {
//                sql = "SELECT * FROM CLAZZ WHERE CLNAME LIKE ? ESCAPE '!'";
//            }
            while (rs.next()) {
                SearchDataModel sdm = new SearchDataModel(rs.getString(1),rs.getString(2),rs.getString(3));
                sdms.add(sdm);
            }
        } catch (SQLException e){
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

    public void closeCon() {
        try {
            con.close();
        } catch (Exception con) {

        }
    }
}
