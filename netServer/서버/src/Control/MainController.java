package Control;

/**
 * Created by skrud on 2017-10-02.
 */
public class MainController {
    private DBController DBController = null;
    private JsonController jsonController = null;


    public MainController(){
        DBController = new DBController(this);
        jsonController = new JsonController(this);
    }

    public DBController getDBController() {
        return DBController;
    }

    public void setDBController(DBController DBController) {
        this.DBController = DBController;
    }

    public JsonController getJsonController() {
        return jsonController;
    }

    public void setJsonController(JsonController jsonController) {
        this.jsonController = jsonController;
    }

}
