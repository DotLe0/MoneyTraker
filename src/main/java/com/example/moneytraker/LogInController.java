package com.example.moneytraker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LogInController{

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField logInUsername;
    @FXML
    private PasswordField logInPassword;

    @FXML
    public void LogIn(ActionEvent event) throws IOException
    {
        //Check if the user exists and the password is correct

        //Connect to database
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moneytrackerdb", "root", "rootSql");
            statement = connection.prepareStatement("SELECT user_name, password FROM user_table WHERE user_name = ? AND password = ?");
            statement.setString(1, logInUsername.getText().toString());
            statement.setString(2, logInPassword.getText().toString());
            resultSet = statement.executeQuery();

            //Check if user is found
            if(resultSet.next()) {
                //Switch to dashboard
                System.out.println("Valid username and password");

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dashboard-view.fxml"));
                Parent root = fxmlLoader.load();

                DashboardController dashboardController = fxmlLoader.getController();
                dashboardController.setName(logInUsername.getText().toString());

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Money tracker dashboard page");
                stage.setScene(scene);
                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorect username or password");
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
    public void SwitchToRegisterScene(ActionEvent event) throws IOException
    {
        //switch to register scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Money tracker register page");
        stage.setScene(scene);
        stage.show();
    }

}