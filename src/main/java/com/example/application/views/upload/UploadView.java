package com.example.application.views.upload;

import com.example.application.controllers.Extract;
import com.example.application.controllers.SQLServerExtractor;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;

@PageTitle("DataWarehouse SDSI")
@Route(value = "upload")
public class UploadView extends VerticalLayout {
    public UploadView(){
        Label label = new Label("Data Warehouse SDSI");
        label.getStyle().set("font-size", "30px").set("font-weight", "bold");
        Label label_upload = new Label("Upload Data to Azure");
        label_upload.getStyle().set("font-size", "26px").set("font-weight", "bold");

        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        setPadding(true);

        Image imageAzure = new Image("images/azure.png", "Azure Image");
        imageAzure.setWidth(300, Unit.PIXELS);

        add(label, label_upload, imageAzure);

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setAlignItems(Alignment.CENTER);
        add(row1);
        TextField server_name = new TextField("Server Name");
        TextField database_name = new TextField("Database Name");
        TextField database_user = new TextField("User");
        row1.add(server_name, database_name, database_user);

        HorizontalLayout row2 = new HorizontalLayout();
        add(row2);
        row2.setAlignItems(Alignment.CENTER);
        PasswordField database_password = new PasswordField("Password");
        TextField table_name = new TextField("Table Name");
        row2.add(database_password, table_name);

        Button ConnectSQLButton = new Button("Connect");
        add(ConnectSQLButton);

        ConnectSQLButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        Div text = new Div(new Text("Failed to connect to the database"));
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });
        HorizontalLayout layoutNotification = new HorizontalLayout(text, closeButton);
        layoutNotification.setAlignItems(Alignment.CENTER);
        notification.add(layoutNotification);
        ConnectSQLButton.addClickListener(clickEvent -> {

            try {
                Extract.connectToAzure(
                        server_name.getValue(),
                        database_name.getValue(),
                        database_user.getValue(),
                        database_password.getValue()
                );
                if (table_name.isEmpty()){
                    throw new SQLException();
                }
                Notification notificationSuccess = Notification
                        .show("Successfully connected to the database");
                notificationSuccess.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                remove(ConnectSQLButton);
                Button uploadButton = new Button("Upload");
                uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                        ButtonVariant.LUMO_SUCCESS);
                add(uploadButton);
                uploadButton.addClickListener(clickEvent2 -> {
                    try {
                        Extract.uploadToCloud(table_name.getValue());
                        remove(row1, row2, uploadButton);
                        Label label_success = new Label("Data successfully uploaded to Azure \uD83D\uDE0E");
                        label_success.getStyle().set("font-size", "26px").set("font-weight", "bold");
                        add(label_success);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
                notification.open();
            }
        });
    }
}
