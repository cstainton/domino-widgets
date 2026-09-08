// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.datatable.CellTextAlign;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.plugins.header.HeaderBarPlugin;
import org.dominokit.domino.ui.datatable.plugins.selection.SelectionPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.notifications.Notification;
import org.dominokit.domino.ui.typography.BlockHeader;

public final class SelectionPluginExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    singleSelectionPlugin();
    multiSelectionPlugin();
    return element.element();
  }

  private void singleSelectionPlugin() {
    TableConfig<Contact> singleSelectionTableConfig = new TableConfig<>();
    singleSelectionTableConfig
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .setTextAlign(CellTextAlign.RIGHT)
                .setCellRenderer(
                    cell1 -> text(cell1.getTableRow().getRecord().getIndex() + 1 + "")))
        .addColumn(
            ColumnConfig.<Contact>create("status", "Status")
                .setTextAlign(CellTextAlign.CENTER)
                .setCellRenderer(
                    cell1 -> {
                      if (cell1.getTableRow().getRecord().isActive()) {
                        return Icons.check_circle().addCss(dui_fg_green_d_3).element();
                      } else {
                        return Icons.close_circle().addCss(dui_fg_red_d_3).element();
                      }
                    }))
        .addColumn(
            ColumnConfig.<Contact>create("firstName", "First name")
                .setCellRenderer(cell1 -> text(cell1.getTableRow().getRecord().getName())))
        .addColumn(
            ColumnConfig.<Contact>create("gender", "Gender")
                .setCellRenderer(cell1 -> ContactUiUtils.getGenderElement(cell1.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("eyeColor", "Eye color")
                .setCellRenderer(cell1 -> ContactUiUtils.getEyeColorElement(cell1.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("balance", "Balance")
                .setCellRenderer(
                    cellInfo1 -> ContactUiUtils.getBalanceElement(cellInfo1.getRecord())))
        .addColumn(
            ColumnConfig.<Contact>create("email", "Email")
                .setCellRenderer(cell1 -> text(cell1.getTableRow().getRecord().getEmail())))
        .addColumn(
            ColumnConfig.<Contact>create("phone", "Phone")
                .setCellRenderer(cell1 -> text(cell1.getTableRow().getRecord().getPhone())))
        .addColumn(
            ColumnConfig.<Contact>create("badges", "Badges")
                .setCellRenderer(
                    cell1 -> {
                      if (cell1.getTableRow().getRecord().getAge() < 35) {
                        return Badge.create("Young").addCss(dui_green, dui_float_none).element();
                      }
                      return text("");
                    }))
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>()));
    singleSelectionTableConfig.setMultiSelect(false);
    singleSelectionTableConfig.addPlugin(new SelectionPlugin<>());
    LocalListDataStore<Contact> singleLocalStore = new LocalListDataStore<>();
    DataTable<Contact> singleSelectionTable =
        new DataTable<>(singleSelectionTableConfig, singleLocalStore);
    singleSelectionTable.addSelectionListener(
        (selectedTableRows, selectedRecords) -> {
          Notification.create(String.valueOf(selectedRecords.size())).show();
        });

    element.appendChild(
        Card.create(
                "SELECTION PLUGIN",
                "Enable row selection by adding the selection plugin, pass different selection"
                    + " style colors in the constructor.")
            .setCollapsible(true)
            .appendChild(BlockHeader.create("SINGLE SELECTION"))
            .appendChild(singleSelectionTable.element())
            .element());

    singleLocalStore.setData(ContactsProvider.instance.subList());
    singleLocalStore.load();
  }

  private void multiSelectionPlugin() {

    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .setTextAlign(CellTextAlign.RIGHT)
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getIndex() + 1 + "")))
        .addColumn(
            ColumnConfig.<Contact>create("status", "Status")
                .setTextAlign(CellTextAlign.CENTER)
                .setCellRenderer(
                    cell -> {
                      if (cell.getTableRow().getRecord().isActive()) {
                        return Icons.check_circle().addCss(dui_fg_green_d_3).element();
                      } else {
                        return Icons.close_circle().addCss(dui_fg_red_d_3).element();
                      }
                    }))
        .addColumn(
            ColumnConfig.<Contact>create("firstName", "First name")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getName())))
        .addColumn(
            ColumnConfig.<Contact>create("gender", "Gender")
                .setCellRenderer(cell -> ContactUiUtils.getGenderElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("eyeColor", "Eye color")
                .setCellRenderer(cell -> ContactUiUtils.getEyeColorElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("balance", "Balance")
                .setCellRenderer(
                    cellInfo -> ContactUiUtils.getBalanceElement(cellInfo.getRecord())))
        .addColumn(
            ColumnConfig.<Contact>create("email", "Email")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getEmail())))
        .addColumn(
            ColumnConfig.<Contact>create("phone", "Phone")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getPhone())))
        .addColumn(
            ColumnConfig.<Contact>create("badges", "Badges")
                .setCellRenderer(
                    cell -> {
                      if (cell.getTableRow().getRecord().getAge() < 35) {
                        return Badge.create("Young").addCss(dui_green, dui_float_none).element();
                      }
                      return text("");
                    }))
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>()));

    tableConfig.addPlugin(new SelectionPlugin<>());
    LocalListDataStore<Contact> multiLocalStore = new LocalListDataStore<>();
    DataTable<Contact> multiSelectionTable = new DataTable<>(tableConfig, multiLocalStore);
    multiSelectionTable.addSelectionListener(
        (selectedTableRows, selectedRecords) -> {
          Notification.create(String.valueOf(selectedRecords.size())).show();
        });

    element.appendChild(
        Card.create(
                "SELECTION PLUGIN",
                "Enable row selection by adding the selection plugin, pass different selection"
                    + " style colors in the constructor.")
            .setCollapsible(true)
            .appendChild(BlockHeader.create("MULTI SELECTION"))
            .appendChild(multiSelectionTable)
            .element());

    multiLocalStore.setData(ContactsProvider.instance.subList());
  }
}
