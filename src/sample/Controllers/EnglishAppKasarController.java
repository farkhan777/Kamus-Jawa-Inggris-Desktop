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
import sample.Helpers.DataModelEnglish;
import sample.Helpers.DbAppConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EnglishAppKasarController implements Initializable {

    @FXML
    private TableView<DataModelEnglish> table;

    @FXML
    private TableColumn<DataModelEnglish, String> col_english;

    @FXML
    private TableColumn<DataModelEnglish,  String> col_javanese;

    @FXML
    private TextField filter;

    @FXML
    void Javanese(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/JavaneseAppHalus.fxml"));

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
    void home(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/HomeApp.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
    }

    @FXML
    void halus(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Views/EnglishAppHalus.fxml"));

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

    ObservableList<DataModelEnglish> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            Connection connection = DbAppConnect.getConnection();

            ResultSet resultSet = connection.createStatement().executeQuery("select * from kasar_eng_jawa");

            while (resultSet.next()){
                observableList.addAll(new DataModelEnglish(resultSet.getString("English"),resultSet.getString("Jawa")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        col_english.setCellValueFactory(new PropertyValueFactory<>("english"));
        col_javanese.setCellValueFactory(new PropertyValueFactory<>("jawa"));

        FilteredList<DataModelEnglish> filteredList = new FilteredList<>(observableList, b -> true);

        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dataModelEnglish -> {

                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (dataModelEnglish.getEnglish().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<DataModelEnglish> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }
}
