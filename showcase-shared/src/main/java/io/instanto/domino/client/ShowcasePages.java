package io.instanto.domino.client;

/** Reader-facing navigation for the pinned original showcase samples. */
public final class ShowcasePages {
  public static final String UPSTREAM = "https://dominokit.com/domino-ui/demo/v2/";
  public static final String[] GROUPS = {
    "Components",
    "Forms",
    "Data tables",
    "Navigation & layout",
    "Feedback & overlays",
    "Style & media"
  };
  public static final Page[] ALL = {
    new Page(
        "Components",
        "buttons",
        "Buttons",
        "Explore filled, outlined and text buttons, sizes, groups and dropdowns.",
        "components/buttons",
        "showcase-shared/src/main/java/io/instanto/domino/client/ButtonsExamples.java"),
    new Page(
        "Components",
        "cards",
        "Cards",
        "Compose content with headers, colours, logos and collapsible sections.",
        "components/cards",
        "showcase-shared/src/main/java/io/instanto/domino/client/CardsExamples.java"),
    new Page(
        "Components",
        "chips",
        "Chips",
        "Try removable and selectable chips with icons, letters and images.",
        "components/chips",
        "showcase-shared/src/main/java/io/instanto/domino/client/ChipsExamples.java"),
    new Page(
        "Components",
        "lists",
        "Lists",
        "Present collections as styled lists.",
        "components/lists",
        "showcase-shared/src/main/java/io/instanto/domino/client/ListsExamples.java"),
    new Page(
        "Components",
        "badges",
        "Badges",
        "Add counters and coloured status badges to buttons and lists.",
        "components/badges",
        "showcase-shared/src/main/java/io/instanto/domino/client/BadgesExamples.java"),
    new Page(
        "Components",
        "labels",
        "Labels",
        "Explore text labels and Material colour variations.",
        "components/labels",
        "showcase-shared/src/main/java/io/instanto/domino/client/LabelsExamples.java"),
    new Page(
        "Components",
        "infobox",
        "Info boxes",
        "Highlight information with icons, hover effects and aligned content.",
        "components/infobox",
        "showcase-shared/src/main/java/io/instanto/domino/client/InfoBoxExamples.java"),
    new Page(
        "Forms",
        "forms",
        "Basic forms",
        "Enter text and try text areas, checkboxes, radio buttons and switches.",
        "forms/basic-form-elements",
        "showcase-shared/src/main/java/io/instanto/domino/client/FormsExamples.java"),
    new Page(
        "Forms",
        "inputfields",
        "Input fields",
        "Explore numeric inputs and structured field types.",
        "forms/input-fields",
        "showcase-shared/src/main/java/io/instanto/domino/client/InputFieldsExamples.java"),
    new Page(
        "Forms",
        "formsvalidations",
        "Validation & decoration",
        "Try helper text, icons, word counts, validation and read-only fields.",
        "forms/fields-decoration",
        "showcase-shared/src/main/java/io/instanto/domino/client/FormsValidationsExamples.java"),
    new Page(
        "Forms",
        "advanced-forms",
        "Advanced forms",
        "Use file uploads, tags and country suggestions.",
        "forms/advanced-form-elements",
        "showcase-shared/src/main/java/io/instanto/domino/client/AdvancedFormsExamples.java"),
    new Page(
        "Forms",
        "datepicker",
        "Date picker",
        "Select dates in calendars, dropdowns and date fields, with locale examples.",
        "forms/datepicker",
        "showcase-shared/src/main/java/io/instanto/domino/client/DatePickerExamples.java"),
    new Page(
        "Forms",
        "timepicker",
        "Time picker",
        "Choose a time with inline, dropdown and field controls.",
        "forms/timepicker",
        "showcase-shared/src/main/java/io/instanto/domino/client/TimePickerExamples.java"),
    new Page(
        "Forms",
        "steppers",
        "Steppers",
        "Explore horizontal and vertical steps for guided forms.",
        "forms/steppers",
        "showcase-shared/src/main/java/io/instanto/domino/client/SteppersExamples.java"),
    new Page(
        "Forms",
        "sliders",
        "Sliders",
        "Adjust values with draggable sliders and colour variants.",
        "components/sliders",
        "showcase-shared/src/main/java/io/instanto/domino/client/SlidersExamples.java"),
    new Page(
        "Forms",
        "spin",
        "Spin controls",
        "Step through values with horizontal and vertical controls.",
        "components/spin",
        "showcase-shared/src/main/java/io/instanto/domino/client/SpinSelectExamples.java"),
    new Page(
        "Data tables",
        "table-basic-data-table",
        "Basic table",
        "Browse a contact table with custom cells and sample records.",
        "datatable/basic-table",
        "showcase-shared/src/main/java/io/instanto/domino/client/BasicDataTableExamples.java"),
    new Page(
        "Data tables",
        "table-selection-plugin",
        "Row selection",
        "Try single and multiple row selection.",
        "datatable/selection-plugin",
        "showcase-shared/src/main/java/io/instanto/domino/client/SelectionPluginExamples.java"),
    new Page(
        "Data tables",
        "table-sort-and-search-plugin",
        "Sort & search",
        "Sort columns and search the contact data.",
        "datatable/sort-search-plugin",
        "showcase-shared/src/main/java/io/instanto/domino/client/SortAndSearchPluginExamples.java"),
    new Page(
        "Data tables",
        "table-pagination-plugin",
        "Table pagination",
        "Compare simple, scrolling and advanced pagination.",
        "datatable/pagination-plugin",
        "showcase-shared/src/main/java/io/instanto/domino/client/PaginationPluginExamples.java"),
    new Page(
        "Data tables",
        "table-header-bar-plugin",
        "Header actions",
        "Add a header bar and actions to a table.",
        "datatable/header-bar-plugin",
        "showcase-shared/src/main/java/io/instanto/domino/client/HeaderBarPluginExamples.java"),
    new Page(
        "Data tables",
        "table-column-resize-plugin",
        "Resizable columns",
        "Drag column boundaries to change their widths.",
        "datatable/column-resize-plugin",
        "showcase-shared/src/main/java/io/instanto/domino/client/ColumnResizePluginExamples.java"),
    new Page(
        "Data tables",
        "table-pin-columns-plugin",
        "Pinned columns",
        "Keep columns in view while exploring a wide table.",
        "datatable/column-pin-plugin",
        "showcase-shared/src/main/java/io/instanto/domino/client/PinColumnsPluginExamples.java"),
    new Page(
        "Data tables",
        "table-empty-state-plugin",
        "Empty state",
        "Show a useful message when a table has no records.",
        "datatable/empty-state",
        "showcase-shared/src/main/java/io/instanto/domino/client/EmptyStatePluginExamples.java"),
    new Page(
        "Data tables",
        "table-fixed-data-table",
        "Fixed table",
        "Explore a table with a fixed layout.",
        "datatable/fixed-table",
        "showcase-shared/src/main/java/io/instanto/domino/client/FixedDataTableExamples.java"),
    new Page(
        "Data tables",
        "table-marker-plugin",
        "Row markers",
        "Highlight records with row markers.",
        "datatable/marker-plugin",
        "showcase-shared/src/main/java/io/instanto/domino/client/MarkerPluginExamples.java"),
    new Page(
        "Data tables",
        "table-columns-groups",
        "Column groups",
        "Organise related columns under shared headers, with pinning and resizing.",
        "datatable/column-groups",
        "showcase-shared/src/main/java/io/instanto/domino/client/ColumnsGroupsExamples.java"),
    new Page(
        "Navigation & layout",
        "tabs",
        "Tabs",
        "Switch between panels with text, icons, closable tabs and animation.",
        "components/tabs",
        "showcase-shared/src/main/java/io/instanto/domino/client/TabsExamples.java"),
    new Page(
        "Navigation & layout",
        "menu",
        "Menus",
        "Explore dropdowns, context menus and nested menu actions.",
        "components/menu",
        "showcase-shared/src/main/java/io/instanto/domino/client/MenuExamples.java"),
    new Page(
        "Navigation & layout",
        "tree",
        "Trees",
        "Expand nested branches and explore active and expanded icons.",
        "components/tree",
        "showcase-shared/src/main/java/io/instanto/domino/client/TreeExamples.java"),
    new Page(
        "Navigation & layout",
        "breadcrumb",
        "Breadcrumbs",
        "Show navigation paths with alignment and colour options.",
        "components/breadcrumbs",
        "showcase-shared/src/main/java/io/instanto/domino/client/BreadcrumbExamples.java"),
    new Page(
        "Navigation & layout",
        "pagination",
        "Pagination",
        "Move between pages with numbered, scrolling and previous/next controls.",
        "components/pagination",
        "showcase-shared/src/main/java/io/instanto/domino/client/PaginationExamples.java"),
    new Page(
        "Navigation & layout",
        "collapse",
        "Collapse & accordions",
        "Expand individual sections or grouped accordion panels.",
        "components/collapse",
        "showcase-shared/src/main/java/io/instanto/domino/client/CollapseExamples.java"),
    new Page(
        "Navigation & layout",
        "grids",
        "Grids",
        "Compare responsive column layouts and different grid divisions.",
        "layout/grids",
        "showcase-shared/src/main/java/io/instanto/domino/client/GridsExamples.java"),
    new Page(
        "Navigation & layout",
        "splitPanel",
        "Split panels",
        "Resize horizontal, vertical and combined panels.",
        "layout/split-panel",
        "showcase-shared/src/main/java/io/instanto/domino/client/SplitPanelExamples.java"),
    new Page(
        "Navigation & layout",
        "dnd",
        "Drag & drop",
        "Move items between the original drag-and-drop containers.",
        "components/dnd",
        "showcase-shared/src/main/java/io/instanto/domino/client/DndExamples.java"),
    new Page(
        "Feedback & overlays",
        "alerts",
        "Alerts",
        "Explore contextual alerts, dismissible messages and embedded links.",
        "components/alerts",
        "showcase-shared/src/main/java/io/instanto/domino/client/AlertsExamples.java"),
    new Page(
        "Feedback & overlays",
        "dialogs",
        "Dialogs",
        "Open message, confirmation and custom-content dialogs.",
        "components/dialogs",
        "showcase-shared/src/main/java/io/instanto/domino/client/DialogsExamples.java"),
    new Page(
        "Feedback & overlays",
        "modals",
        "Modals & sheets",
        "Compare modal sizes, sheets, colours and windows.",
        "components/modals",
        "showcase-shared/src/main/java/io/instanto/domino/client/ModalsExamples.java"),
    new Page(
        "Feedback & overlays",
        "notifications",
        "Notifications",
        "Try notification positions, types, colours and animations.",
        "components/notifications",
        "showcase-shared/src/main/java/io/instanto/domino/client/NotificationsExamples.java"),
    new Page(
        "Feedback & overlays",
        "popover",
        "Tooltips & popovers",
        "Reveal contextual help beside an element.",
        "components/tooltips-popover",
        "showcase-shared/src/main/java/io/instanto/domino/client/PopoverExamples.java"),
    new Page(
        "Feedback & overlays",
        "progress",
        "Progress bars",
        "Explore contextual, striped, animated and stacked progress indicators.",
        "components/progress",
        "showcase-shared/src/main/java/io/instanto/domino/client/ProgressExamples.java"),
    new Page(
        "Feedback & overlays",
        "loaders",
        "Loaders",
        "Show a loading state over content.",
        "components/loaders",
        "showcase-shared/src/main/java/io/instanto/domino/client/LoadersExamples.java"),
    new Page(
        "Feedback & overlays",
        "preloaders",
        "Preloaders",
        "Compare loading spinners in different sizes and colours.",
        "components/preloaders",
        "showcase-shared/src/main/java/io/instanto/domino/client/PreloadersExamples.java"),
    new Page(
        "Style & media",
        "typography",
        "Typography",
        "Explore headings, body text, lists, quotations and text styles.",
        "typography",
        "showcase-shared/src/main/java/io/instanto/domino/client/TypographyExamples.java"),
    new Page(
        "Style & media",
        "helpers",
        "Spacing & sizing",
        "Compare height, width, margin and padding utilities.",
        "helpers",
        "showcase-shared/src/main/java/io/instanto/domino/client/HelpersExamples.java"),
    new Page(
        "Style & media",
        "animation",
        "Animation",
        "Try the original animation examples.",
        "animations",
        "showcase-shared/src/main/java/io/instanto/domino/client/AnimationExamples.java"),
    new Page(
        "Style & media",
        "waves",
        "Waves",
        "Explore ripple effects on interactive elements.",
        "components/waves",
        "showcase-shared/src/main/java/io/instanto/domino/client/WavesExamples.java"),
    new Page(
        "Style & media",
        "carousel",
        "Carousel",
        "Browse an image carousel.",
        "components/carousel",
        "showcase-shared/src/main/java/io/instanto/domino/client/CarouselExamples.java"),
    new Page(
        "Style & media",
        "media",
        "Media objects",
        "Arrange images and text with different alignments.",
        "components/media",
        "showcase-shared/src/main/java/io/instanto/domino/client/MediaExamples.java"),
    new Page(
        "Style & media",
        "thumbnails",
        "Thumbnails",
        "Compose image cards with titles, extra content and different arrangements.",
        "components/thumbnails",
        "showcase-shared/src/main/java/io/instanto/domino/client/ThumbnailsExamples.java"),
  };

  public static Page find(String route) {
    for (Page page : ALL) if (page.route.equals(route)) return page;
    return null;
  }

  public static final class Page {
    public final String group, route, title, description, upstream, source;

    private Page(
        String group,
        String route,
        String title,
        String description,
        String upstream,
        String source) {
      this.group = group;
      this.route = route;
      this.title = title;
      this.description = description;
      this.upstream = UPSTREAM + upstream;
      this.source = source;
    }
  }
}
