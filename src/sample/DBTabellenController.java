package sample;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DBTabellenController extends Controller {
    @FXML
    TableView<Product> table;

    void tabelleAnzeigen(){
        //Id column
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        //idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //Farbe column
        TableColumn<Product, String> farbeColumn = new TableColumn<>("FARBE");
        //farbeColumn.setMinWidth(100);
        farbeColumn.setCellValueFactory(new PropertyValueFactory<>("farbe"));

        //Id column
        TableColumn<Product, Integer> mengeColumn = new TableColumn<>("MENGE");
        //mengeColumn.setMinWidth(100);
        mengeColumn.setCellValueFactory(new PropertyValueFactory<>("menge"));

        table = new TableView<>();
        table.setItems(main.getProduct());
        table.getColumns().addAll(idColumn,farbeColumn,mengeColumn);
    }
}
