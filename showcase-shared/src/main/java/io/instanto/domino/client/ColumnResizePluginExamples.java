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
import org.dominokit.domino.ui.datatable.plugins.column.ResizeColumnMeta;
import org.dominokit.domino.ui.datatable.plugins.column.ResizeColumnsPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.icons.lib.Icons;

public final class ColumnResizePluginExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    resizableColumns();
    return element.element();
  }

  private void resizableColumns() {
    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .setFixed(true)
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .setTextAlign(CellTextAlign.RIGHT)
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getIndex() + 1 + ""))
                .applyMeta(ResizeColumnMeta.create()))
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
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getName()))
                .setWidth("200px")
                .minWidth("100px")
                .maxWidth("300"))
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
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getEmail()))
                .minWidth("50px"))
        .addColumn(
            ColumnConfig.<Contact>create("phone", "Phone")
                .setCellRenderer(
                    cell ->
                        span()
                            .css("ellipsis-text")
                            .textContent(cell.getTableRow().getRecord().getPhone())
                            .setDisplay("inline-block")
                            .element()))
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
            new ResizeColumnsPlugin<Contact>().configure(config -> config.setClipContent(true)));

    LocalListDataStore<Contact> localListDataStore = new LocalListDataStore<>();
    DataTable<Contact> table = new DataTable<>(tableConfig, localListDataStore);

    element.appendChild(
        Card.create("RESIZABLE COLUMNS", "Allow the user to change the size of the table columns.")
            .setCollapsible(true)
            .appendChild(table)
            .element());

    localListDataStore.setData(ContactsProvider.instance.subList(50));
  }
}
