package sample;

import controller.DBTabellenController;

/**
 * Created by Stanislav on 30.05.2017.
 */
public class TestClass {
    public static void main(String[] args) {
        DBTabellenController controller = new DBTabellenController();
        controller.tabellenAnlegen();
        controller.aktualisieren();
    }
}
