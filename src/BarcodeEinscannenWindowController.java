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
    Button btn_inPosition;
    @FXML
    Button btn_einlagern;

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
        btn_einlagern.setDisable(true);
        progressIndicator_2.setVisible(false);
        progressIndicator_1.setProgress(-1);
        progressIndicator_1.setVisible(true);
        try {
            JSONObject json = readJsonFromUrl("https://dionysos.informatik.hs-augsburg.de/rest/api/pickup.php");
            System.out.println(json.toString());
            if (json.getString("status").equals("OK")) {
                try {
                    for (int i = 0; i < 30; i++) {
                        JSONObject json_2 = readJsonFromUrl("https://dionysos.informatik.hs-augsburg.de/rest/api/status.php?snr=" + json.getString("satznr"));
                        if (json_2.getString("status").equals("0"))
                            System.out.println("Warte...");
                        else if (json_2.getString("status").equals("-1")) {
                            throw new RPException("RP-Fehler beim Einlagern!");
                        } else if (json_2.getString("status").equals("1")) {
                            progressIndicator_1.setProgress(1);
                            fehler_1.setVisible(false);
                            btn_einlagern.setDisable(false);
                            break;
                        }
                        Thread.sleep(1000);
                    }
                } catch (RPException e) {
                    e.getMessage();
                    progressIndicator_1.setVisible(false);
                    fehler_1.setText("status.php?snr=" + json.getString("satznr") + ", RP-Fehler");
                    fehler_1.setVisible(true);
                }
            } else {
                throw new RPException("FEHLER!");
            }
        } catch (IOException e) {
            System.out.println("rest/pickup.php, IOException");
            progressIndicator_1.setVisible(false);
            fehler_1.setText("rest/pickup.php, IOException");
            fehler_1.setVisible(true);
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("rest/pickup.php, JSONException");
            progressIndicator_1.setVisible(false);
            fehler_1.setText("rest/pickup.php, JSONException");
            fehler_1.setVisible(true);
            e.printStackTrace();
        } catch (RPException e) {
            System.out.println("rest/pickup.php, RPException");
            progressIndicator_1.setVisible(false);
            fehler_1.setText("Fehler: RP-Fehler beim Fahren!");
            fehler_1.setVisible(true);
            e.getMessage();
        }

    }

    @FXML
    public void inLagerAblegen() throws InterruptedException {
        System.out.println("public void inLagerAblegen()");
        JSONObject json_1;
        JSONObject json_2;
        fehler_2.setVisible(false);

        if (textField.getText().equals("100") || textField.getText().equals("200") ||
                textField.getText().equals("300") || textField.getText().equals("400") ||
                textField.getText().equals("500")) {
            progressIndicator_2.setProgress(-1.0);
            progressIndicator_2.setVisible(true);
            try {
                json_1 = readJsonFromUrl("https://dionysos.informatik.hs-augsburg.de/rest/api/insert.php?anh=" + textField.getText());
                System.out.println(json_1.toString());
                fehler_2.setText("Das Produkt wird eingelagert! (ReNr: " + ", FaRn: " + ")");
                fehler_2.setVisible(true);
                try {
                    for (int i = 0; i < 10; i++) {
                        json_2 = readJsonFromUrl("https://dionysos.informatik.hs-augsburg.de/rest/api/status.php?snr=" + json_1.getString("satznr"));
                        if (json_2.getString("status").equals("0")) {
                            fehler_2.setVisible(true);
                            fehler_2.setText("Das Produkt wird noch eingelagert!");
                            System.out.println("Das Produkt wird noch eingelagert!");
                        } else if (json_2.getString("status").equals("-1")) {
                            progressIndicator_1.setVisible(false);
                            progressIndicator_2.setVisible(false);
                            btn_einlagern.setDisable(true);
                            throw new RPException("RP-Fehler beim Einlagern!");
                        } else {
                            progressIndicator_2.setProgress(1);
                            textField.setText("");
                            fehler_1.setVisible(false);
                            fehler_2.setText("Das Produkt wurde eingelagert :)");
                            progressIndicator_1.setProgress(-1);
                            progressIndicator_1.setVisible(false);
                            btn_einlagern.setDisable(true);
                            break;
                        }
                        Thread.sleep(1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("status.php?snr=" + json_1.getString("satznr") + ", JSONException");
                    fehler_2.setText("Fehler: Probieren Sie es nochmal!");
                    fehler_2.setVisible(true);
                    progressIndicator_2.setVisible(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("insert.php?anh=" + textField.getText() + ", IOException");
                fehler_2.setText("Fehler: Probieren Sie es nochmal!");
                fehler_2.setVisible(true);
                progressIndicator_2.setVisible(false);
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("insert.php?anh=" + textField.getText() + ", JSONException");
                fehler_2.setText("Fehler: Probieren Sie es nochmal!");
                fehler_2.setVisible(true);
                progressIndicator_2.setVisible(false);
            } catch (RPException e) {
                e.getMessage();
                fehler_2.setText("Fehler: RP-Fehler beim Einlagern!");
                fehler_2.setVisible(true);
            }
        } else if (textField.getText().equals("")) {
            fehler_2.setText("Fehler: Scannen Sie den Barcode ein!");
            fehler_2.setVisible(true);
        } else {
            fehler_2.setText("Fehler: Produktcode existiert in DB nicht!");
            fehler_2.setVisible(true);
        }

    }

}
