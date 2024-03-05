package com.example.application.views.helloworld;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
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

@PageTitle("Hello World")
@Route(value = "hello")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;
    private Label label;

    public HelloWorldView() {
        VerticalLayout layout = new VerticalLayout();
        add(layout);
        label = new Label("Data Warehouse SDSI");
        label.getStyle().set("font-size", "30px").set("font-weight", "bold");

        layout.setAlignItems(Alignment.CENTER);
        setWidth("100%");
        setPadding(true);

        layout.add(label);
        HorizontalLayout layout2 = new HorizontalLayout();
        layout.add(layout2);
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
        TextField database_user = new TextField("user");
        PasswordField database_password = new PasswordField("password");
        layoutSQL.add(imageSQL, label_sql, database_name, database_user, database_password);
        Image imageExcel = new Image("images/excel.png", "Excel Image");
        imageExcel.setWidth(150, Unit.PIXELS);
        imageExcel.setHeight(150, Unit.PIXELS);
        Label label_excel = new Label("EXCEL");
        label_excel.getStyle().set("font-size", "22px").set("font-weight", "bold");
        MemoryBuffer bufferExcel = new MemoryBuffer();
        Upload uploadExcel = new Upload(bufferExcel);
        uploadExcel.setDropLabel(new Label("Upload xlsx file here"));
        uploadExcel.setDropAllowed(true);
        uploadExcel.addSucceededListener(event -> {
            InputStream fileData = bufferExcel.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
        });
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
        });
        layoutCSV.add(imageCSV, label_csv, uploadCSV);
    }

}
