package com.example.moneytraker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class RegisterController {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField registerUsername;
    @FXML
    private PasswordField registerPassword;

    @FXML
    public void Register(ActionEvent event) throws IOException
    {
        //Register the user into the DB and switch to the dashboard.
        //Connect to DB
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moneytrackerdb", "root", "rootSql");
            //Check if user exist. If it exists throw an error.
            statement = connection.prepareStatement("SELECT user_name FROM user_table WHERE user_name = ?");
            statement.setString(1, registerUsername.getText().toString());
            resultSet = statement.executeQuery();

            if(!resultSet.next()) {
                //Insert new username and password to the DB
                statement = connection.prepareStatement("INSERT INTO user_table (user_name, password) VALUES (?, ?)");
                statement.setString(1, registerUsername.getText().toString());
                statement.setString(2, registerPassword.getText().toString());
                statement.executeUpdate();

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dashboard-view.fxml"));

                DashboardController dashboardController = fxmlLoader.getController();
                dashboardController.setName(registerUsername.getText().toString());
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
                stage.setTitle("Hello, " + registerUsername.getText().toString());
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("There is a user this name, chose another one.");
                alert.show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    public void BackToLogInForm(ActionEvent event) throws IOException
    {
        //Switch to log in scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Money tracker login page");
        stage.setScene(scene);
        stage.show();
    }
}
