package com.example.application.views.csv;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("CSV")
@Route(value = "csv", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class CSVView extends Div {

    public CSVView() {
        addClassName("c-sv-view");
        add(new Text("Content placeholder"));
    }

}
