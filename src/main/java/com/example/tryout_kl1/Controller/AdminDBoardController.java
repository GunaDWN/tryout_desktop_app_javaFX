package com.example.tryout_kl1.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminDBoardController {

    @FXML
    private Button btnAvailableTO;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnAddTO;

    @FXML
    private GridPane pnAvailableTO;

    @FXML
    private GridPane pnHome;

    @FXML
    private GridPane pnAddTO;

    @FXML
    private Label usernameLogin;

    @FXML
    private TextField TOidField;

    @FXML
    private TextField TONameField;

    @FXML
    private TextField TOScoreField;

    @FXML
    private ImageView TOImageField;
    @FXML
    private String imagePath;

    @FXML
    private Button btnNextToAddMapel;

    @FXML
    private Label messageLabelAddTO;

    @FXML
    private GridPane pnAddMapel;

    @FXML
    private TextField newMapelField;

    @FXML
    private Button btnAddMapel;

    @FXML
    private GridPane mapelContainer;

    @FXML
    private Button btnNextToAddQuestion;

    @FXML
    private GridPane pnAddQuestion;

    @FXML
    private AnchorPane hBoxRow1;

    @FXML
    private Label MessageLableToChooseMapelFirst;

    @FXML
    private Button btnAddQuestion;

    @FXML
    private ChoiceBox<String> choiceBoxMapel;

    @FXML
    private Button btnSaveQuestionAndNextQuestion;

    @FXML
    private BorderPane listQuestionContent;

    @FXML
    private Label labelQuestionNo;

    @FXML
    private TextArea questionField;

    @FXML
    private Button btnAddAnswerOption;

    @FXML
    private VBox vBoxForOptionPane;


    @FXML
    Label labelDescOption;


    @FXML
    private GridPane listBox;

    @FXML
    private ScrollPane listBoxScrol;

    @FXML
    private Button btnFinish;

    private int rowIndex = 0;

    public void initialize() {
        String firstName = SessionManager.getLoggedInUserFirstName();
        String lastName = SessionManager.getLoggedInUserLastName();
        usernameLogin.setText(firstName + " " + lastName);
    }

    @FXML
    private void handleClick(ActionEvent event) {
        if (event.getSource() == btnAvailableTO) {
            pnAvailableTO.toFront();
        } else if (event.getSource() == btnHome) {
            pnHome.toFront();
        } else if (event.getSource() == btnAddTO) {
            pnAddTO.toFront();
        } else if (event.getSource() == btnNextToAddMapel) {
            if (TOidField.getText().isBlank() == false && TONameField.getText().isBlank() == false && TOScoreField.getText().isBlank() == false && imagePath != null && !imagePath.isBlank()) {
                if (!isTOidUsed(TOidField.getText())) {
                    AddTO();
                    pnAddMapel.toFront();
                    showConfirmation("Selamat", "berhasil menambahkan Tryout !!");
                } else {
                    showAlert("ID sudah digunakan", "buatlah ID yang berbeda !!");
                }
            } else {
                messageLabelAddTO.setText("Silahkan isi form dengan lengkap !!");
            }
        } else if (event.getSource() == btnAddMapel) {
            addMapelHandle();
            displayMapelAfterAdd(TOidField.getText());
        } else if (event.getSource() == btnNextToAddQuestion) {
            hitungsemuaDurasi();
            pnAddQuestion.toFront();
            messageToChooseMapelFirst();
            initializeChoiceBox();
            hideQuestionContent();
        } else if (event.getSource() == btnAddQuestion) {
            showQuestionConten();
            btnAddQuestion.setDisable(true);
            labelQuestionNo.setText("Question No. " + nomorSoal);

        } else if (event.getSource() == btnAddAnswerOption) {
            labelDescOption.setText("Pilih Radio Button Untuk Pilihan Jawaban Benar");
            addAnswerOptionHandle();
        } else if (event.getSource() == btnSaveQuestionAndNextQuestion) {
            nomorSoal++;
            labelQuestionNo.setText("Question No. " + nomorSoal);
            saveQuestionAndOptions();
            vBoxForOptionPane.getChildren().clear();
            questionField.clear();
        }
    }

    private void messageToChooseMapelFirst() {
        MessageLableToChooseMapelFirst.setText("Pilih mapel terleih dahulu");
        MessageLableToChooseMapelFirst.setTextFill(Color.web("#ff0000"));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hBoxRow1.getChildren().remove(MessageLableToChooseMapelFirst);
            }
        }));
        timeline.play();
    }

    @FXML
    public void imageChooserHandle() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*jpeg")
        );
        Stage stage = (Stage) TOImageField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            TOImageField.setImage(image);
        }
    }

    public void AddTO() {
        DbConnector connector = new DbConnector();
        try (Connection connectDB = connector.getConnection()) {
            String TOid = TOidField.getText();
            String TOName = TONameField.getText();
            String TOScore = TOScoreField.getText();

            String query = "INSERT INTO tryout(id_tryout,nama_tryout,skor_tryout,gambar_tryout) VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                preparedStatement.setString(1, TOid);
                preparedStatement.setString(2, TOName);
                preparedStatement.setString(3, TOScore);

                File imageFile = new File(imagePath);
                FileInputStream fis = new FileInputStream(imageFile);
                byte[] imageData = new byte[(int) imageFile.length()];
                fis.read(imageData);
                fis.close();

                preparedStatement.setBytes(4, imageData);
                preparedStatement.executeUpdate();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                System.out.println("Kesalahan pada: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Gagal konek ke: " + e.getMessage());
        }
    }



    private boolean isTOidUsed(String TOid) {
        String checkQuery = "SELECT COUNT(*) FROM tryout WHERE id_tryout = ?";
        try (Connection connectDB = new DbConnector().getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(checkQuery)) {
            preparedStatement.setString(1, TOid);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kesalahan pada pemeriksaan TOid: " + e.getMessage());
        }
        return false;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void AddMapel(String namaMapel) {
        DbConnector connector = new DbConnector();
        try (Connection connectDb = connector.getConnection()) {
            /*String lastInsertedTryoutId = getLastInsertedId();
            String namaMapel = newMapelField.getText();*/
            String query = "INSERT INTO mapel(id_tryout, nama_mapel) VALUES (?,?)";
            try (PreparedStatement preparedStatement = connectDb.prepareStatement(query)) {
                preparedStatement.setString(1, TOidField.getText());
                preparedStatement.setString(2, namaMapel);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error during add Mapel: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error connecting to " + e.getMessage());
        }
        newMapelField.clear();
    }

    /*private String getLastInsertedId(){
        String query = "SELECT id_tryout FROM tryout ORDER BY id_tryout DESC LIMIT 1";
        DbConnector connector = new DbConnector();
        try (Connection connectDB = connector.getConnection();
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getString("id_tryout");
                }
            }catch (SQLException e){
                e.printStackTrace();
                System.out.println("Error getting last inserted Tryout ID: " + e.getMessage());
            }
            return null;
        }*/

    @FXML
    public void addMapelHandle() {
        String namaMapelBaru = newMapelField.getText();
        AddMapel(namaMapelBaru);
        displayMapelAfterAdd(TOidField.getText());
    }

    private void displayMapelAfterAdd(String idTryout) {
        mapelContainer.getChildren().clear();
        rowIndex = 0;
        displayMapelByTryoutId(idTryout);
    }

    public void displayMapelByTryoutId(String idTryout) {
        String query = "SELECT * FROM mapel WHERE id_tryout = ?";
        DbConnector connector = new DbConnector();
        try (Connection connectDB = connector.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, idTryout);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String idMapel = resultSet.getString("id_mapel");
                    String namaMapel = resultSet.getString("nama_mapel");

                    mapelContainer.setVgap(15);
                    mapelContainer.setHgap(10);

                    Label labelNamaMapel = new Label(namaMapel);
                    labelNamaMapel.setStyle("-fx-font-weight: bold");

                    TextField textFieldDurasi = new TextField();
                    textFieldDurasi.setPromptText("Durasi (Minutes)");
                    textFieldDurasi.setMaxHeight(Region.USE_PREF_SIZE);
                    textFieldDurasi.setMaxWidth(Region.USE_PREF_SIZE);
                    textFieldDurasi.setPrefHeight(26.0);
                    textFieldDurasi.setPrefWidth(113.0);

                    Button btnSubmit = new Button("Submit");
                    btnSubmit.setOnAction(event -> saveDurasi(idMapel, textFieldDurasi.getText()));

                    Button btnRemoveMapel = new Button("Remove");
                    btnRemoveMapel.setOnAction(event -> RemoveMapel(idMapel));
                    GridPane.setMargin(btnRemoveMapel, new Insets(0, 0, 0, 67));

                    mapelContainer.add(labelNamaMapel, 0, rowIndex);
                    mapelContainer.add(textFieldDurasi, 1, rowIndex);
                    mapelContainer.add(btnSubmit, 2, rowIndex);
                    mapelContainer.add(btnRemoveMapel, 2, rowIndex);

                    rowIndex++;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error displaying Mapel: " + e.getMessage());
        }
    }

    private void saveDurasi(String idMapel, String durasi) {
        String query = "UPDATE mapel SET durasi_mapel = ? WHERE id_mapel = ?";
        DbConnector connector = new DbConnector();

        try (Connection connectDB = connector.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

            preparedStatement.setString(1, durasi);
            preparedStatement.setString(2, idMapel);

            preparedStatement.executeUpdate();
            Label labelDurasi = new Label("Durasi ditambahkan");
            labelDurasi.setTextFill(Color.web("#003bff"));
            mapelContainer.add(labelDurasi, 2, rowIndex);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Menghilangkan labelDurasi setelah 1 detik
                    mapelContainer.getChildren().remove(labelDurasi);
                }
            }));

            // Memulai timeline
            timeline.play();
        } catch (SQLException e) {
            e.printStackTrace();
            Label labelError = new Label("Kesalahan saat menginput durasi");
            labelError.setTextFill(Color.web("#ff0000"));
            mapelContainer.add(labelError, 2, rowIndex);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Menghilangkan labelDurasi setelah 1 detik
                    mapelContainer.getChildren().remove(labelError);
                }
            }));
            // Memulai timeline
            timeline.play();
            System.out.println("Kesalahan pada: " + e.getMessage());
        }

    }

    private void RemoveMapel(String idMapel) {
        DbConnector connector = new DbConnector();
        String query = "DELETE FROM Mapel WHERE id_mapel = ?";

        try (Connection connectDb = connector.getConnection();
             PreparedStatement preparedStatement = connectDb.prepareStatement(query)) {

            preparedStatement.setString(1, idMapel);
            preparedStatement.executeUpdate();

            // Setelah menghapus mapel, tampilkan kembali mapel yang tersisa
            displayMapelAfterAdd(TOidField.getText());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error during removing mapel: " + e.getMessage());
            // Handle error (misalnya, tampilkan pesan kesalahan kepada pengguna)
        }
    }

    private void hitungsemuaDurasi() {
        String idTryout = TOidField.getText();
        String query = "SELECT SUM(durasi_mapel) AS total_durasi FROM mapel WHERE id_tryout = ?";

        DbConnector connector = new DbConnector();
        try (Connection connectDb = connector.getConnection();
             PreparedStatement preparedStatement = connectDb.prepareStatement(query)) {

            preparedStatement.setString(1, idTryout);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int totalDurasi = resultSet.getInt("total_durasi");
                    updateDurasiTO(idTryout, totalDurasi);
                    System.out.println("Total durasi: " + totalDurasi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("Kesalahan pada: " + e.getMessage());
        }

    }

    private void updateDurasiTO(String idTryout, int totalDurasi) {
        String query = "UPDATE tryout SET durasi_tryout = ? WHERE id_tryout = ?";
        DbConnector connector = new DbConnector();

        try (Connection connectDB = connector.getConnection();
             PreparedStatement updateStatement = connectDB.prepareStatement(query)) {

            updateStatement.setInt(1, totalDurasi);
            updateStatement.setString(2, idTryout);
            updateStatement.executeUpdate();

            System.out.println("Durasi tryout diperbarui: " + totalDurasi);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kesalahan pada: " + e.getMessage());
        }
    }

    private void initializeChoiceBox() {
        ObservableList<String> mapelList = getMapelNamesByTryoutId(TOidField.getText());
        choiceBoxMapel.setItems(mapelList);
        choiceBoxMapel.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
            btnAddQuestion.setDisable(false);
        });
    }

    private ObservableList<String> getMapelNamesByTryoutId(String idTryout) {
        ObservableList<String> mapelList = FXCollections.observableArrayList();
        String query = "SELECT nama_mapel FROM mapel WHERE id_tryout = ?";

        DbConnector connector = new DbConnector();
        try (Connection connectDB = connector.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

            preparedStatement.setString(1, idTryout);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String namaMapel = resultSet.getString("nama_mapel");
                    mapelList.add(namaMapel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapelList;
    }

    private void hideQuestionContent() {
        for (Node child : listQuestionContent.getChildrenUnmodifiable()) {
            child.setVisible(false);
        }
        btnSaveQuestionAndNextQuestion.setVisible(false);

    }

    private void showQuestionConten() {
        for (Node child : listQuestionContent.getChildrenUnmodifiable()) {
            child.setVisible(true);
        }
        btnSaveQuestionAndNextQuestion.setVisible(true);

    }
   private int nomorSoal = 1;
   ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
        public void addAnswerOptionHandle() {

        HBox hBoxForOptionPane = new HBox();

        TextArea answerOptionField = new TextArea();
        answerOptionField.setMaxHeight(Region.USE_PREF_SIZE);
        answerOptionField.setMaxWidth(Region.USE_PREF_SIZE);
        answerOptionField.setPrefHeight(70.0);
        answerOptionField.setPrefWidth(300.0);
        answerOptionField.setWrapText(true);
        answerOptionField.setPromptText("Type an option answer");
        hBoxForOptionPane.setMargin(answerOptionField, new Insets(0, 10, 20, 10));

        RadioButton radioForAnswer = new RadioButton();
        radioForAnswer.setToggleGroup(toggleGroup);
        hBoxForOptionPane.setMargin(radioForAnswer, new Insets(20, 0, 0, 10));

        Button removeButton = new Button("Remove Option");
        removeButton.setOnAction(event -> removeAnswerOption(hBoxForOptionPane));
        hBoxForOptionPane.setMargin(removeButton, new Insets(17, 0, 0, 10));

        hBoxForOptionPane.getChildren().addAll(answerOptionField, radioForAnswer, removeButton);

        vBoxForOptionPane.getChildren().add(hBoxForOptionPane);
    }

    private void removeAnswerOption(HBox hBoxForOptionPane){
        vBoxForOptionPane.getChildren().remove(hBoxForOptionPane);
    }

    private int idMapelSelected = -1;
    @FXML
    private int getIdMapel(String selectedMapel) {
        selectedMapel = choiceBoxMapel.getValue();

        if (selectedMapel != null) {
            String idTryout = TOidField.getText();

            DbConnector connector = new DbConnector();
            String query = "SELECT id_mapel FROM mapel WHERE id_tryout = ? AND nama_mapel = ? ";

            try (Connection connecDB = connector.getConnection();
                 PreparedStatement preparedStatement = connecDB.prepareStatement(query)
            ) {
                preparedStatement.setString(1, idTryout);
                preparedStatement.setString(2, selectedMapel);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        idMapelSelected = resultSet.getInt("id_mapel");
                        System.out.println("Pilihan: " + selectedMapel + ", id_mapel: " + idMapelSelected);
                        return idMapelSelected;
                    } else {
                        System.out.println("Tidak dapat menemukan id_mapel untuk " + selectedMapel);
                        return -1;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @FXML
    private void saveQuestion(int idMapel, String pertanyaan) {
        /*if (idMapelSelected != -1) {
            pertanyaan = questionField.getText();*/
        DbConnector connector = new DbConnector();
        String query = "INSERT INTO Soal(id_mapel,pertanyaan) VALUES (?,?)";

        try (Connection connectDB = connector.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)
        ){
            preparedStatement.setInt(1, idMapel);
            preparedStatement.setString(2, pertanyaan);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Pertanyaan berhasil ditambahkan ke tabel Soal");
            } else {
                System.out.println("Pertanayaan gagal ditambahkan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void saveAnswerOptions(int idSoal) {
        // Mendapatkan seluruh opsi jawaban yang telah dimasukkan oleh user
        ObservableList<Node> optionNodes = vBoxForOptionPane.getChildren();
        for (Node node : optionNodes) {
            if (node instanceof HBox) {
                HBox hBoxOption = (HBox) node;

                if (hBoxOption.getChildren().size() == 3) {
                    Node textFieldNode = hBoxOption.getChildren().get(0);
                    if (textFieldNode instanceof TextArea){
                        TextArea answerOptionField = (TextArea) textFieldNode;
                        String opsiText = answerOptionField.getText();

                        if (hBoxOption.getChildren().get(1) instanceof RadioButton) {
                            RadioButton radioForAnswer = (RadioButton) hBoxOption.getChildren().get(1);
                            boolean isJawabanBenar = radioForAnswer.isSelected();

                            // Menyimpan opsi jawaban ke tabel OpsiJawaban
                            saveAnswerOption(idSoal, opsiText, isJawabanBenar);
                        }
                    }
                }
            }
        }
    }


    private void saveAnswerOption(int idSoal, String opsiText, boolean isJawabanBenar) {
        DbConnector connector = new DbConnector();
        String query = "INSERT INTO OpsiJawaban (id_soal, opsiText) VALUES (?, ?)";

        try (Connection connectDB = connector.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, idSoal);
            preparedStatement.setString(2, opsiText);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idOpsiJawaban = generatedKeys.getInt(1);
                    }
                }
                System.out.println("Opsi jawaban berhasil disimpan.");
            } else {
                System.out.println("Gagal menyimpan opsi jawaban.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int getLastInsertedID(String tableName, String idColumn) {
        DbConnector connector = new DbConnector();
        String query = "SELECT MAX(" + idColumn + ") AS last_id FROM " + tableName;
        try (Connection connectDB = connector.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()){

            if (resultSet.next()){
                return resultSet.getInt("last_id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


    private void saveCorrectAnswer(int idSoal, String jawabanTeks) {
        String query = "UPDATE Soal SET jawaban_benar = ? WHERE id_soal = ?";
        DbConnector connector = new DbConnector();

        try (Connection connectDB = connector.getConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

            preparedStatement.setString(1, jawabanTeks);
            preparedStatement.setInt(2, idSoal);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Jawaban benar berhasil disimpan");
            } else {
                System.out.println("Gagal menyimpan jawaban benar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private String getSelectedAnswerText(int idSoalBaru) {
        for (Node node : vBoxForOptionPane.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;

                if (hBox.getChildren().size() == 3) {
                    Node textFieldNode = hBox.getChildren().get(0);
                    if (textFieldNode instanceof TextArea) {
                        TextArea answerOptionField = (TextArea) textFieldNode;
                        RadioButton radioForAnswer = (RadioButton) hBox.getChildren().get(1);

                        if (radioForAnswer.isSelected()) {
                            saveCorrectAnswer(idSoalBaru, answerOptionField.getText());

                            return answerOptionField.getText();
                        }
                    }
                }
            }
        }
        return "";
    }

    List<Integer> nomorSoalList = new ArrayList<>();
    @FXML
    public void saveQuestionAndOptions(){
        String pertanyaan = questionField.getText();

        String selectedMapel = choiceBoxMapel.getValue();

        int idMapel = getIdMapel(selectedMapel);

        if (idMapel != -1){
            saveQuestion(idMapel,pertanyaan);
            int idSoalBaru = getLastInsertedID("Soal","id_soal");
            saveAnswerOptions(idSoalBaru);
            getSelectedAnswerText(idSoalBaru);
            nomorSoalList.add(idSoalBaru);
            updateListBox();
        }
    }

    private void updateListBox() {
        listBox.getChildren().clear();
        int maxButtonsPerRow = 4;
        int columnCount = 0;
        int rowCount = 0;

        /*int urutan = 1;
        Button button = new Button(""+urutan);
        int row = urutan-1;
        listBox.add(button,0,row);

        listBox.setHgap(10);
        listBox.setVgap(10);
        listBox.setPadding(new Insets(10));

        int rows = 7;
        int cols = 4;
        int urutan = 1;

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                button = new Button(""+urutan);
                listBox.add(button,col,row);
                urutan++;
            }
        }*/
        for (Integer idSoal : nomorSoalList){
            Button button = new Button(""+(rowCount * maxButtonsPerRow + columnCount +1));
            button.setPrefHeight(68);
            button.setPrefWidth(74);
            addQuestionButton(button,idSoal);
            listBox.add(button,columnCount,rowCount);
            columnCount++;

            if (columnCount >= maxButtonsPerRow){
                columnCount =0;
                rowCount++;
            }

        }
        listBox.setHgap(10);
        listBox.setVgap(10);
        listBox.setPadding(new Insets(10));

    }
    private void addQuestionButton(Button button, int idSoal){
        button.setOnAction(event -> {
            System.out.println("Pertanyaan no: " +idSoal);
        });
    }

}