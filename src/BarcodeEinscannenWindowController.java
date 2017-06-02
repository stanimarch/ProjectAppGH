import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class BarcodeEinscannenWindowController extends Controller {

    @FXML
    TextField textField;

    @FXML
    Text fehler_1;
    @FXML
    Text fehler_2;

    @FXML
    Button abholPosition;
    @FXML
    Button btnEinlagern;

    @FXML
    ProgressIndicator progressIndicator_1;
    @FXML
    ProgressIndicator progressIndicator_2;


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    @FXML
    public void abholPositionFahren() throws InterruptedException {
        System.out.println("public void abholPositionFahren()");
        fehler_1.setVisible(false);
        fehler_2.setVisible(false);
        btnEinlagern.setDisable(true);
        progressIndicator_2.setVisible(false);
        progressIndicator_1.setProgress(-1);
        progressIndicator_1.setVisible(true);
        try {
            JSONObject json = readJsonFromUrl("https://dionysos.informatik.hs-augsburg.de/rest/pickup.php");
            System.out.println(json.toString());
            if (json.getString("status").equals("RBP spuckt Fehler")) {
                btnEinlagern.setDisable(true);
                progressIndicator_1.setProgress(1);
                btnEinlagern.setDisable(false);
            } else {
                btnEinlagern.setDisable(true);
                fehler_1.setVisible(true);
                throw new IOException("FEHLER!");
            }
        } catch (IOException | JSONException e) {
            progressIndicator_1.setVisible(false);
            fehler_1.setVisible(true);
            e.printStackTrace();
        }

    }

    @FXML
    public void inLagerAblegen() throws InterruptedException {
        System.out.println("public void inLagerAblegen()");
        progressIndicator_2.setProgress(-1);
        progressIndicator_2.setVisible(true);
        try {
            JSONObject json = readJsonFromUrl("https://dionysos.informatik.hs-augsburg.de/rest/api/checkall.php");
            System.out.println(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        progressIndicator_2.setProgress(1);
        textField.setText("");
        fehler_1.setVisible(false);
        fehler_2.setVisible(false);
        btnEinlagern.setDisable(true);
        progressIndicator_2.setVisible(false);
        progressIndicator_1.setProgress(-1);
        progressIndicator_1.setVisible(true);

    }

}
