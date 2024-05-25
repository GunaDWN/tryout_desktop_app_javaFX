/*package com.example.tryout_kl1.Controller;

import com.example.tryout_kl1.MainApp;
import com.example.tryout_kl1.Models.Tryout;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TryoutSelectionController {

    @FXML
    private Button backButton;

    @FXML
    private TableView<Tryout> tryoutTable;

    @FXML
    private TableColumn<Tryout, String> idColumn;

    @FXML
    private TableColumn<Tryout, String> nameColumn;

    @FXML
    private TableColumn<Tryout, String> actionColumn;

    @FXML
    public void initialize() {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            // Menambahkan kolom aksi dengan tombol "Start" untuk setiap tryout
            addActionColumn();
            // Menambahkan semua data tryout yang tersedia
            tryoutTable.getItems().addAll(getAvailableTryouts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addActionColumn() {
        // Mengatur sel pada kolom aksi untuk menampilkan tombol "Start"
        actionColumn.setCellFactory(col -> {
            TableCell<Tryout, String> cell = new TableCell<>() {
                final Button btnStart = new Button("Start");

                {
                    btnStart.setOnAction(event -> {
                        Tryout tryout = getTableView().getItems().get(getIndex());
                        handleStartButtonClick(tryout);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnStart);
                    }
                }
            };
            return cell;
        });
    }

    private void handleStartButtonClick(Tryout tryout) {
        // Logika yang diperlukan saat tombol "Start" diklik
        System.out.println("Tombol Start untuk Tryout ID: " + tryout.getId());

        // Panggil metode untuk menampilkan ujianLayout
        showUjianLayout(tryout);
    }

    private void showUjianLayout(Tryout tryout) {
        // Panggil metode di MainApp untuk menampilkan ujianLayout
        MainApp.showScene("ujianLayout.fxml");

        // Selanjutnya, Anda dapat menangani logika khusus untuk melewatkan data Tryout ke ujianLayout jika diperlukan
    }

    private List<Tryout> getAvailableTryouts() throws SQLException {
        List<Tryout> tryouts = new ArrayList<>();
        DbConnector dbConnector = new DbConnector();

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tryout")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Tryout tryout = new Tryout(id, name, duration, score);
                    tryout.setId(resultSet.getString("id_tryout"));
                    tryout.setName(resultSet.getString("nama_tryout"));

                    tryouts.add(tryout);
                }
            }
        }
        return tryouts;
    }

    @FXML
    public void goBackToDashboard() {
        MainApp.showScene("dashboard.fxml");
    }
}*/
