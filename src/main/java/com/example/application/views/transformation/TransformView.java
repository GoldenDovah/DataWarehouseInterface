package com.example.application.views.transformation;

import com.example.application.controllers.DataWarehouse;
import com.example.application.model.Person;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("DataWarehouse SDSI")
@Route(value = "transform")
public class TransformView extends VerticalLayout {

    public TransformView(){
        Label label = new Label("Data Warehouse SDSI");
        label.getStyle().set("font-size", "30px").set("font-weight", "bold");
        Label label_extraction = new Label("Data Transformation");
        label_extraction.getStyle().set("font-size", "26px").set("font-weight", "bold");

        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        setPadding(true);

        add(label, label_extraction);
        Grid<Person> grid = new Grid<>(Person.class, false);
        grid.addColumn(Person::getFirstName).setHeader("First name");
        grid.addColumn(Person::getLastName).setHeader("Last name");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getPhone).setHeader("Phone number");


        List<Person> people = DataWarehouse.getPersonData();
        grid.setItems(people);
        add(grid);

        HorizontalLayout layoutButtons = new HorizontalLayout();
        add(layoutButtons);
        Button uploadButton = new Button("Upload to Azure");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);
        Button transformButton = new Button("Transform Data");
        transformButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);
        layoutButtons.add(uploadButton, transformButton);
    }
}
