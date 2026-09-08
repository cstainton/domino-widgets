package io.instanto.domino.client;

import elemental2.dom.HTMLElement;

public final class GalleryCatalog {
  public static final java.util.List<String> ROUTES =
      java.util.List.of(
          "buttons",
          "forms",
          "dialogs",
          "alerts",
          "badges",
          "breadcrumb",
          "cards",
          "chips",
          "collapse",
          "grids",
          "helpers",
          "infobox",
          "inputfields",
          "labels",
          "lists",
          "loaders",
          "menu",
          "notifications",
          "pagination",
          "popover",
          "preloaders",
          "progress",
          "sliders",
          "spin",
          "splitPanel",
          "steppers",
          "tabs",
          "timepicker",
          "typography",
          "waves",
          "datepicker",
          "formsvalidations",
          "tree",
          "advanced-forms",
          "animation",
          "carousel",
          "media",
          "thumbnails",
          "modals",
          "dnd",
          "table-basic-data-table",
          "table-selection-plugin",
          "table-sort-and-search-plugin",
          "table-pagination-plugin",
          "table-header-bar-plugin",
          "table-column-resize-plugin",
          "table-pin-columns-plugin",
          "table-empty-state-plugin",
          "table-fixed-data-table",
          "table-marker-plugin",
          "table-columns-groups");

  public static HTMLElement render(String route) {
    if (route.equals("buttons")) return new ButtonsExamples().render();
    if (route.equals("forms")) return new FormsExamples().render();
    if (route.equals("dialogs")) return new DialogsExamples().render();
    if (route.equals("alerts")) return new AlertsExamples().render();
    if (route.equals("badges")) return new BadgesExamples().render();
    if (route.equals("breadcrumb")) return new BreadcrumbExamples().render();
    if (route.equals("cards")) return new CardsExamples().render();
    if (route.equals("chips")) return new ChipsExamples().render();
    if (route.equals("collapse")) return new CollapseExamples().render();
    if (route.equals("grids")) return new GridsExamples().render();
    if (route.equals("helpers")) return new HelpersExamples().render();
    if (route.equals("infobox")) return new InfoBoxExamples().render();
    if (route.equals("inputfields")) return new InputFieldsExamples().render();
    if (route.equals("labels")) return new LabelsExamples().render();
    if (route.equals("lists")) return new ListsExamples().render();
    if (route.equals("loaders")) return new LoadersExamples().render();
    if (route.equals("menu")) return new MenuExamples().render();
    if (route.equals("notifications")) return new NotificationsExamples().render();
    if (route.equals("pagination")) return new PaginationExamples().render();
    if (route.equals("popover")) return new PopoverExamples().render();
    if (route.equals("preloaders")) return new PreloadersExamples().render();
    if (route.equals("progress")) return new ProgressExamples().render();
    if (route.equals("sliders")) return new SlidersExamples().render();
    if (route.equals("spin")) return new SpinSelectExamples().render();
    if (route.equals("splitPanel")) return new SplitPanelExamples().render();
    if (route.equals("steppers")) return new SteppersExamples().render();
    if (route.equals("tabs")) return new TabsExamples().render();
    if (route.equals("timepicker")) return new TimePickerExamples().render();
    if (route.equals("typography")) return new TypographyExamples().render();
    if (route.equals("waves")) return new WavesExamples().render();
    if (route.equals("datepicker")) return new DatePickerExamples().render();
    if (route.equals("formsvalidations")) return new FormsValidationsExamples().render();
    if (route.equals("tree")) return new TreeExamples().render();
    if (route.equals("advanced-forms")) return new AdvancedFormsExamples().render();
    if (route.equals("animation")) return new AnimationExamples().render();
    if (route.equals("carousel")) return new CarouselExamples().render();
    if (route.equals("media")) return new MediaExamples().render();
    if (route.equals("thumbnails")) return new ThumbnailsExamples().render();
    if (route.equals("modals")) return new ModalsExamples().render();
    if (route.equals("dnd")) return new DndExamples().render();
    if (route.equals("table-basic-data-table")) return new BasicDataTableExamples().render();
    if (route.equals("table-selection-plugin")) return new SelectionPluginExamples().render();
    if (route.equals("table-sort-and-search-plugin"))
      return new SortAndSearchPluginExamples().render();
    if (route.equals("table-pagination-plugin")) return new PaginationPluginExamples().render();
    if (route.equals("table-header-bar-plugin")) return new HeaderBarPluginExamples().render();
    if (route.equals("table-column-resize-plugin"))
      return new ColumnResizePluginExamples().render();
    if (route.equals("table-pin-columns-plugin")) return new PinColumnsPluginExamples().render();
    if (route.equals("table-empty-state-plugin")) return new EmptyStatePluginExamples().render();
    if (route.equals("table-fixed-data-table")) return new FixedDataTableExamples().render();
    if (route.equals("table-marker-plugin")) return new MarkerPluginExamples().render();
    if (route.equals("table-columns-groups")) return new ColumnsGroupsExamples().render();
    return null;
  }
}
