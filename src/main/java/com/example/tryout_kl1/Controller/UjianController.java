/*package com.example.tryout_kl1.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.tryout_kl1.Models.Soal;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UjianController {
    @FXML
    private Label pertanyaanLabel;
    @FXML
    private RadioButton opsi1RadioButton;
    @FXML
    private RadioButton opsi2RadioButton;
    @FXML
    private RadioButton opsi3RadioButton;
    @FXML
    private RadioButton opsi4RadioButton;
    @FXML
    private RadioButton opsi5RadioButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;

    private List<Soal>soalList;
    private int currentIndex;
    @FXML
    private Label MapelLabel;

    @FXML
    private Label numberLabel;

    public void initialize(){
        ToggleGroup toogleGroup = new ToggleGroup();
        opsi1RadioButton.setToggleGroup(toogleGroup);
        opsi2RadioButton.setToggleGroup(toogleGroup);
        opsi3RadioButton.setToggleGroup(toogleGroup);
        opsi4RadioButton.setToggleGroup(toogleGroup);
        opsi5RadioButton.setToggleGroup(toogleGroup);

        soalList = getSoalList("M1");
        if(!soalList.isEmpty()){
            showSoal(soalList.get(currentIndex));
        }
    }

    @FXML
    public void handleNextButtonClick(){
        if (currentIndex<soalList.size()-1){
            currentIndex++;
            showSoal(soalList.get(currentIndex));
        }
    }

    @FXML
    public void handlePrevButtonClick(){
        if (currentIndex>0){
            currentIndex--;
            showSoal(soalList.get(currentIndex));
        }
    }

    private void showSoal(Soal soal) {

        numberLabel.setText("Soal "+(currentIndex+1));
        pertanyaanLabel.setText(soal.getPertanyaan());
        opsi1RadioButton.setText(soal.getOpsi1());
        opsi2RadioButton.setText(soal.getOpsi2());
        opsi3RadioButton.setText(soal.getOpsi3());
        opsi4RadioButton.setText(soal.getOpsi4());
        opsi5RadioButton.setText(soal.getOpsi5());

        opsi1RadioButton.setSelected(false);
        opsi2RadioButton.setSelected(false);
        opsi3RadioButton.setSelected(false);
        opsi4RadioButton.setSelected(false);
        opsi5RadioButton.setSelected(false);
    }

    private List<Soal> getSoalList(String idMapel) {
        List<Soal> soalList = new ArrayList<>();
        try (   Connection connection = DbConnector.getConnection()){
            String query = "SELECT * FROM Soal WHERE id_mapel = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1,idMapel);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        Soal soal = new Soal();
                        soal.setIdSoal(resultSet.getString("id_soal"));
                        soal.setId_mapel(resultSet.getString("id_mapel"));
                        soal.setPertanyaan(resultSet.getString("pertanyaan"));
                        soal.setOpsi1(resultSet.getString("opsi1"));
                        soal.setOpsi2(resultSet.getString("opsi2"));
                        soal.setOpsi3(resultSet.getString("opsi3"));
                        soal.setOpsi4(resultSet.getString("opsi4"));
                        soal.setOpsi5(resultSet.getString("opsi5"));
                        soal.setJawabanBenar(resultSet.getString("jawaban_benar"));
                        soal.setSkor(resultSet.getDouble("skor"));

                        soalList.add(soal);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soalList;
    }

}*/
