package Model.StaticModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by skrud on 2017-11-11.
 */
public class MyFont {
    public static final Font serif = new Font("Serif", Font.BOLD, 15);
    public static final Font serif18= new Font("Serif", Font.BOLD, 18);
    public static final Font serif20 = new Font("Serif", Font.BOLD, 20);
    public static final Font serif23 = new Font("Serif", Font.BOLD, 23);
    public static final Font serif25 = new Font("Serif", Font.BOLD, 25);
    public static final Font serif30 = new Font("Serif", Font.BOLD, 30);

    public Font getNBG() {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("client\\Font\\NanumBarunGothic.ttf").openStream());
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(font);
// makesure to derive the size
        font = font.deriveFont(12f);
        return font;
    }
}
