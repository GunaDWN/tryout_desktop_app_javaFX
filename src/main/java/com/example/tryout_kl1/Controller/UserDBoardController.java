package com.example.tryout_kl1.Controller;

import com.example.tryout_kl1.Models.Tryout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDBoardController {

    @FXML
    private Button btnAvailableTO;
    @FXML
    private GridPane pnAvailableTO;
    @FXML
    private Button btnHome;
    @FXML
    private GridPane pnHome;
    @FXML
    private Label usernameLogin;
    @FXML
    private Label idTO;
    @FXML
    private ImageView imageView;

    public void initialize ()
    {
        String firstName = SessionManager.getLoggedInUserFirstName();
        String lastName = SessionManager.getLoggedInUserLastName();
        usernameLogin.setText(firstName+" "+lastName);
    }

    public List <Tryout> getAllTryouts(){

        List<Tryout> tryouts = new ArrayList<>();
        try {
            String query = "SELECT id_tryout, nama_tryout, durasi_tryout,skor_tryout,gambar_tryout FROM TryOut";
            PreparedStatement statement = new DbConnector().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String id = resultSet.getString("id_tryout");
                String name = resultSet.getString("nama_tryout");
                int duration = resultSet.getInt("durasi_tryout");
                double score = resultSet.getDouble("skor_tryout");
                String gambar = resultSet.getString("gambar_tryout");

                Tryout tryout = new Tryout(id, name,duration,score);
                tryout.setGambarTO(gambar);
                tryouts.add(tryout);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tryouts;
    }

    @FXML
    public void handleClick(ActionEvent event) {
        if (event.getSource()==btnAvailableTO)
        {
            pnAvailableTO.toFront();
            List<Tryout> tryouts = getAllTryouts();
            for (Tryout tryout : tryouts){
                idTO.setText("ID TO: " + tryout.getId());
                Image image = new Image(tryout.getGambarTO());
                imageView.setImage(image);
            }
        } else if (event.getSource()==btnHome)
        {
            pnHome.toFront();
        }
    }

}
