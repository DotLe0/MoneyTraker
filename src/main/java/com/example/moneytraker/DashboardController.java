package com.example.moneytraker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;

public class DashboardController implements Initializable {

    @FXML
    private ChoiceBox<String> expenseCategory;
    @FXML
    private TextField depositAmount;
    @FXML
    private Label helloText;

    String username;

    private String[] expenseOptions = {"Food", "Entertainment", "Bills", "Other"};

    @FXML
    public void AddAmountIntoBalance(ActionEvent event)
    {
        double amount = parseDouble(depositAmount.getText().toString());

    }

    public void setName(String name) {
        username = name;
        helloText.setText("Hello " + username);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        helloText.setText("Hello " + username);
        expenseCategory.getItems().addAll(expenseOptions);
    }
}
