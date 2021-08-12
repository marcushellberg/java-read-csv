package com.example.application.views.csv;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.util.SharedUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@PageTitle("CSV")
@Route(value = "csv", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class CSVView extends VerticalLayout {
    Grid<String[]> grid = new Grid<>();

    public CSVView() {
        var importButton = new Button("Import from classpath", e -> readFromClasspath());
        var buffer = new MemoryBuffer();
        var upload = new Upload(buffer);
        upload.addSucceededListener(e -> {
           displayCsv(buffer.getInputStream());
        });
        add(grid,
            new HorizontalLayout(importButton, upload)
        );
    }

    private void readFromClasspath(){
        displayCsv(getClass().getClassLoader().getResourceAsStream("contacts.csv"));
    }

    private void displayCsv(InputStream resourceAsStream) {
        var parser = new CSVParserBuilder().withSeparator(';').build();
        var reader = new CSVReaderBuilder(new InputStreamReader(resourceAsStream)).withCSVParser(parser).build();

        try {
            var entries = reader.readAll();
            
            var headers = entries.get(0);

            for (int i = 0; i < headers.length; i++) {
                int colIndex = i;
                grid.addColumn(row -> row[colIndex])
                    .setHeader(SharedUtil.camelCaseToHumanFriendly(headers[colIndex]));
            }

            grid.setItems(entries.subList(1, entries.size()));
            
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

}
