package com.example.application.views.helloworld;

import com.example.application.controllers.Extract;
import com.example.application.controllers.SQLServerExtractor;
import com.example.application.views.transformation.TransformView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.html.Label;

import java.io.InputStream;
import java.sql.SQLException;

@PageTitle("DataWarehouse SDSI")
@Route(value = "hello")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;
    private Label label;
    private boolean sqlConnected = false;
    private boolean csvConnected = false;
    private boolean excelConnected = false;
    private Extract extractor;
    private String CSVName;
    private String ExcelName;

    SQLServerExtractor sqlServerExtractor;

    void enableExtraction(Button extractButton){
        if (sqlConnected && csvConnected && excelConnected)
            extractButton.setEnabled(true);
    }

    public HelloWorldView() {
        VerticalLayout layout = new VerticalLayout();
        add(layout);
        label = new Label("Data Warehouse SDSI");
        label.getStyle().set("font-size", "30px").set("font-weight", "bold");
        Label label_extraction = new Label("Data Extraction");
        label_extraction.getStyle().set("font-size", "26px").set("font-weight", "bold");

        layout.setAlignItems(Alignment.CENTER);
        setWidth("100%");
        setPadding(true);

        layout.add(label, label_extraction);
        HorizontalLayout layout2 = new HorizontalLayout();
        Button extractButton = new Button("Extract");
        extractButton.setEnabled(false);
        extractButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(layout2, extractButton);
        VerticalLayout layoutSQL = new VerticalLayout();
        layoutSQL.setAlignItems(Alignment.CENTER);
        VerticalLayout layoutExcel = new VerticalLayout();
        layoutExcel.setAlignItems(Alignment.CENTER);
        VerticalLayout layoutCSV = new VerticalLayout();
        layoutCSV.setAlignItems(Alignment.CENTER);
        layout2.add(layoutSQL, layoutExcel, layoutCSV);
        Image imageSQL = new Image("images/sql_server.png", "SQL Server Image");
        imageSQL.setWidth(150, Unit.PIXELS);
        imageSQL.setHeight(150, Unit.PIXELS);
        Label label_sql = new Label("SQL Server");
        label_sql.getStyle().set("font-size", "22px").set("font-weight", "bold");
        TextField database_name = new TextField("Database Name");
        TextField database_user = new TextField("User");
        PasswordField database_password = new PasswordField("Password");
        TextField table_name = new TextField("Table Name");
        Button ConnectSQLButton = new Button("Connect");
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
            sqlServerExtractor = new SQLServerExtractor(
                    table_name.getValue(),
                    database_name.getValue(),
                    database_user.getValue(),
                    database_password.getValue()
            );
            try {
                if (table_name.isEmpty()){
                    throw new SQLException();
                }
                sqlServerExtractor.connectToDB();
                sqlConnected = true;
                enableExtraction(extractButton);
                Notification notificationSuccess = Notification
                        .show("Successfully connected to the database");
                notificationSuccess.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
                notification.open();
            }
        });
        layoutSQL.add(imageSQL, label_sql, database_name, database_user, database_password, table_name, ConnectSQLButton);

        Image imageExcel = new Image("images/excel.png", "Excel Image");
        imageExcel.setWidth(150, Unit.PIXELS);
        imageExcel.setHeight(150, Unit.PIXELS);
        Label label_excel = new Label("EXCEL");
        label_excel.getStyle().set("font-size", "22px").set("font-weight", "bold");
        MemoryBuffer bufferExcel = new MemoryBuffer();
        Upload uploadExcel = new Upload(bufferExcel);
        uploadExcel.setDropLabel(new Label("Upload xls file here"));
        uploadExcel.setDropAllowed(true);
        uploadExcel.addSucceededListener(event -> {
            InputStream fileData = bufferExcel.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
            excelConnected = true;
            ExcelName = fileName;
            System.out.println();
            enableExtraction(extractButton);
        });
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);

        layoutExcel.add(imageExcel, label_excel, uploadExcel);
        Image imageCSV = new Image("images/csv.png", "CSV Image");
        imageCSV.setWidth(150, Unit.PIXELS);
        imageCSV.setHeight(150, Unit.PIXELS);
        Label label_csv = new Label("CSV");
        label_csv.getStyle().set("font-size", "22px").set("font-weight", "bold");
        MemoryBuffer bufferCSV = new MemoryBuffer();
        Upload uploadCSV = new Upload(bufferCSV);
        uploadCSV.setDropAllowed(true);
        uploadCSV.setDropLabel(new Label("Upload csv file here"));
        uploadCSV.addSucceededListener(event -> {
            InputStream fileData = bufferExcel.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
            csvConnected = true;
            CSVName = fileName;
            enableExtraction(extractButton);
        });
        layoutCSV.add(imageCSV, label_csv, uploadCSV);
        extractButton.addClickListener(clickEvent -> {
            try {
                Extract.setTableName(table_name.getValue());
                Extract.setDatabaseName(database_name.getValue());
                Extract.setUser(database_user.getValue());
                Extract.setPassword(database_password.getValue());
                Extract.setCSVName(CSVName);
                Extract.setExcelName(ExcelName);
                extractor = new Extract();
                UI.getCurrent().navigate(TransformView.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
