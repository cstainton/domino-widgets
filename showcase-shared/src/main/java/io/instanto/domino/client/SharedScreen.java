package io.instanto.domino.client;

import elemental2.dom.*;
import java.util.Arrays;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.datatable.*;
import org.dominokit.domino.ui.datatable.plugins.selection.SelectionPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.dialogs.Dialog;
import org.dominokit.domino.ui.forms.TextBox;

public final class SharedScreen {
  private static int clicks;

  public static void mount(HTMLElement parent) {
    BindingContracts.run();
    HTMLElement root = (HTMLElement) DomGlobal.document.createElement("section");
    root.id = "screen";
    parent.appendChild(root);
    Button button = Button.create("Count").setId("count");
    EventListener listener =
        e -> {
          clicks++;
          button.setText("Count " + clicks);
        };
    button.addClickListener(listener);
    root.appendChild(button.element());
    root.appendChild(
        Button.create("Remove handler")
            .setId("remove-handler")
            .addClickListener(e -> button.removeClickListener(listener))
            .element());
    TextBox input = TextBox.create("Name").setRequired(true);
    input.element().id = "name-field";
    root.appendChild(input.element());
    input.addChangeListener((oldValue, newValue) -> root.setAttribute("data-value", newValue));
    root.appendChild(
        Button.create("Validate")
            .setId("validate")
            .addClickListener(e -> input.validate())
            .element());
    Dialog dialog = Dialog.create().setAnimate(false);
    dialog
        .getContentBody()
        .appendChild(
            Button.create("Close dialog")
                .setId("close-dialog")
                .addClickListener(e -> dialog.close()));
    root.appendChild(
        Button.create("Open dialog")
            .setId("open-dialog")
            .addClickListener(e -> dialog.open())
            .element());
    TableConfig<String> config = new TableConfig<>();
    config.addColumn(
        ColumnConfig.<String>create("name", "Name")
            .setCellRenderer(cell -> DomGlobal.document.createTextNode(cell.getRecord())));
    config.addPlugin(new SelectionPlugin<>());
    LocalListDataStore<String> store =
        new LocalListDataStore<>(new java.util.ArrayList<>(Arrays.asList("Alpha", "Beta")));
    boolean[] filtering = {false};
    store.setSearchFilter((event, record) -> !filtering[0] || record.equals("Alpha"));
    DataTable<String> table = new DataTable<>(config, store);
    table.element().id = "records";
    root.appendChild(table.element());
    table.load();
    root.appendChild(
        Button.create("Filter Alpha")
            .setId("filter-rows")
            .addClickListener(
                e -> {
                  filtering[0] = true;
                  store.onSearchChanged(
                      new org.dominokit.domino.ui.datatable.events.SearchEvent(
                          java.util.Collections.emptyList()));
                })
            .element());
    root.appendChild(
        Button.create("Clear filter")
            .setId("clear-filter")
            .addClickListener(
                e -> {
                  filtering[0] = false;
                  store.onSearchChanged(
                      new org.dominokit.domino.ui.datatable.events.SearchEvent(
                          java.util.Collections.emptyList()));
                })
            .element());
    root.appendChild(
        Button.create("Select first")
            .setId("select-first")
            .addClickListener(
                e -> {
                  table.getRows().get(0).select();
                  root.setAttribute(
                      "data-selected", String.valueOf(table.getRows().get(0).isSelected()));
                })
            .element());
    root.appendChild(
        Button.create("Update rows")
            .setId("update-rows")
            .addClickListener(
                e -> {
                  store.setData(Arrays.asList("Gamma", "Delta"));
                  store.load();
                })
            .element());
    root.appendChild(
        Button.create("Reattach")
            .setId("reattach")
            .addClickListener(
                e -> {
                  root.removeChild(button.element());
                  root.appendChild(button.element());
                })
            .element());
    root.appendChild(
        Button.create("Reattach screen")
            .setId("reattach-screen")
            .addClickListener(
                e -> {
                  parent.removeChild(root);
                  parent.appendChild(root);
                })
            .element());
    root.setAttribute("data-ready", "true");
  }
}
