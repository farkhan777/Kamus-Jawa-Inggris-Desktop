package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Helpers.DataModelJavanese;
import sample.Helpers.DbAppConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JavaneseAppHalusController implements Initializable {

    @FXML
    private TableView<DataModelJavanese> table;

    @FXML
    private TableColumn<DataModelJavanese, String> col_javanese;

    @FXML
    private TableColumn<DataModelJavanese, String> col_english;

    @FXML
    private TextField filter;

    @FXML
    void home(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/HomeApp.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

    @FXML
    void about_us(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/AboutUs.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

    @FXML
    void english(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/EnglishAppHalus.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }


    @FXML
    void kasar(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/JavaneseAppKasar.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

    double x = 0, y = 0;
    @FXML
    void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    // draggable StackPane
    //observablelist untuk menampilkan data
    ObservableList<DataModelJavanese> observableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Connect to table databese MySql
        try{
            Connection connection = DbAppConnect.getConnection();

            ResultSet resultSet = connection.createStatement().executeQuery("select * from halus");

            while (resultSet.next()){
                observableList.addAll(new DataModelJavanese(resultSet.getString("Jawa"), resultSet.getString("English")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        col_javanese.setCellValueFactory(new PropertyValueFactory<>("jawa"));
        col_english.setCellValueFactory(new PropertyValueFactory<>("english"));

        //Membungkus Observablelist ke dalam FilterList (awalnya menampilkan semua data)
        FilteredList<DataModelJavanese> filteredList = new FilteredList<>(observableList, b -> true);

        // Mengatur prediksi filter setiap kali filter berubah
        filter.textProperty().addListener((observable, oldValue, newValue)-> {
            filteredList.setPredicate(dataModelJavanese -> {

                // Jika filter kosong maka akan menampilkan semua data
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(dataModelJavanese.getJawa().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return  true; // filter jawa cocok
                }else {
                    return false; // gk cocok
                }
            });
        });

        // Membungkus FilteredList ke dalam SortedList
        SortedList<DataModelJavanese> sortedList = new SortedList<>(filteredList);

        /* mengikat komparator SortedList ke komparator TableView.
           Kalau tidak, mengurutkan TableView tidak akan berpengaruh.
        */
        sortedList.comparatorProperty().bind(table.comparatorProperty());

        // Menambahkan sorted and filtered data ke tabel
        table.setItems(sortedList);
    }
}
