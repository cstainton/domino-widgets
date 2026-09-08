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
import org.dominokit.domino.ui.datatable.plugins.pagination.AdvancedPaginationPlugin;
import org.dominokit.domino.ui.datatable.plugins.pagination.ScrollingPaginationPlugin;
import org.dominokit.domino.ui.datatable.plugins.pagination.SimplePaginationPlugin;
import org.dominokit.domino.ui.datatable.plugins.pagination.SortPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.icons.lib.Icons;

public final class PaginationPluginExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    simplePagination();
    scrollingPagination();
    advancedPagination();
    return element.element();
  }

  private void simplePagination() {
    SimplePaginationPlugin<Contact> simplePaginationPlugin =
        new SimplePaginationPlugin<>(10); // page size
    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .setTextAlign(CellTextAlign.RIGHT)
                .sortable()
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
                .sortable()
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
                .sortable()
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
                    }));

    tableConfig
        .addPlugin(new SortPlugin<>())
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>())
                .addActionElement(new HeaderBarPlugin.ClearSearch<>())
                .addActionElement(
                    new HeaderBarPlugin.SearchTableAction<Contact>()
                        .withSearchBox(
                            (parent, searchBox) -> {
                              searchBox.addCss(dui_max_w_64, dui_bg_dominant_d_1, dui_rounded_md);
                            })))
        .addPlugin(simplePaginationPlugin);

    LocalListDataStore<Contact> localListDataStore = new LocalListDataStore<>();
    localListDataStore.setRecordsSorter(new ContactSorter());
    localListDataStore.setSearchFilter(new ContactSearchFilter());
    localListDataStore.setPagination(simplePaginationPlugin.getSimplePagination());
    DataTable<Contact> table = new DataTable<>(tableConfig, localListDataStore);

    element.appendChild(
        Card.create(
                "SIMPLE PAGINATION",
                "Simple pagination plugin allows the table to fire pagination events helpful for"
                    + " the datasource")
            .setCollapsible(true)
            .appendChild(table)
            .element());

    localListDataStore.setData(ContactsProvider.instance.subList(50));
  }

  private void scrollingPagination() {
    ScrollingPaginationPlugin<Contact> scrollingPagination =
        new ScrollingPaginationPlugin<>(10, 5); // page size
    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .setTextAlign(CellTextAlign.RIGHT)
                .sortable()
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
                .sortable()
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
                .sortable()
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
                    }));

    tableConfig
        .addPlugin(new SortPlugin<>())
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>())
                .addActionElement(new HeaderBarPlugin.ClearSearch<>())
                .addActionElement(
                    new HeaderBarPlugin.SearchTableAction<Contact>()
                        .withSearchBox(
                            (parent, searchBox) -> {
                              searchBox.addCss(dui_max_w_64, dui_bg_dominant_d_1, dui_rounded_md);
                            })))
        .addPlugin(scrollingPagination);

    LocalListDataStore<Contact> localListDataStore = new LocalListDataStore<>();
    localListDataStore.setRecordsSorter(new ContactSorter());
    localListDataStore.setSearchFilter(new ContactSearchFilter());
    localListDataStore.setPagination(scrollingPagination.getPagination());
    DataTable<Contact> table = new DataTable<>(tableConfig, localListDataStore);

    element.appendChild(
        Card.create(
                "SCROLLING PAGINATION",
                "Scrolling pagination plugin allows navigation through a set of page at a time in"
                    + " datatable")
            .setCollapsible(true)
            .appendChild(table)
            .element());

    localListDataStore.setData(ContactsProvider.instance.subList(50));
  }

  private void advancedPagination() {
    AdvancedPaginationPlugin<Contact> advancedPagination =
        new AdvancedPaginationPlugin<>(10); // page size
    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .setTextAlign(CellTextAlign.RIGHT)
                .sortable()
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
                .sortable()
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
                .sortable()
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
                    }));

    tableConfig
        .addPlugin(new SortPlugin<>())
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>())
                .addActionElement(new HeaderBarPlugin.ClearSearch<>())
                .addActionElement(
                    new HeaderBarPlugin.SearchTableAction<Contact>()
                        .withSearchBox(
                            (parent, searchBox) -> {
                              searchBox.addCss(dui_max_w_64, dui_bg_dominant_d_1, dui_rounded_md);
                            })))
        .addPlugin(advancedPagination);

    LocalListDataStore<Contact> localListDataStore = new LocalListDataStore<>();
    localListDataStore.setRecordsSorter(new ContactSorter());
    localListDataStore.setSearchFilter(new ContactSearchFilter());
    localListDataStore.setPagination(advancedPagination.getPagination());
    DataTable<Contact> table = new DataTable<>(tableConfig, localListDataStore);

    element.appendChild(
        Card.create(
                "ADVANCED PAGINATION",
                "Advanced pagination plugin allows navigation through pages from a dropdown list")
            .setCollapsible(true)
            .appendChild(table)
            .element());

    localListDataStore.setData(ContactsProvider.instance.subList(50));
  }
}
