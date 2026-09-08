package io.instanto.domino.client;

import elemental2.dom.*;
import java.util.ArrayList;
import java.util.List;

/** Shared public gallery; diagnostic fixtures have explicit, separate routes. */
public final class Showcase {
  private static final String REPO = "https://github.com/cstainton/domino-widgets";

  public static void mount() {
    String query = DomGlobal.location.search;
    String route = query.startsWith("?page=") ? query.substring(6) : "";
    if (route.equals("home")) route = "";
    String compiler = DomGlobal.document.body.getAttribute("data-compiler");
    ShowcasePages.Page page = ShowcasePages.find(route);
    DomGlobal.document.body.appendChild(
        link("Skip to examples", "#showcase-main", "showcase-skip"));
    header(compiler, route);
    sidebar(route);

    HTMLElement main = element("main", "showcase-main");
    main.id = "showcase-main";
    main.setAttribute("tabindex", "-1");
    DomGlobal.document.body.appendChild(main);
    if (route.isEmpty()) {
      home(main);
    } else if (page != null) {
      pageHeading(main, page);
      HTMLElement examples = GalleryCatalog.render(route);
      examples.id = "gallery-examples";
      main.appendChild(examples);
      examples.setAttribute("data-ready", "true");
    } else if (route.equals("contracts")) {
      main.appendChild(text("h1", "Widget behaviour checks", ""));
      main.appendChild(
          text(
              "p",
              "Development fixtures for events, validation, tables and widget lifecycle.",
              "showcase-lead"));
      SharedScreen.mount(main);
    } else if (route.equals("browser-apis")) {
      main.appendChild(text("h1", "Browser API checks", ""));
      main.appendChild(BrowserApis.render());
    } else if (route.equals("richtext")) {
      main.appendChild(text("h1", "Rich text editor", ""));
      main.appendChild(
          text(
              "p",
              "An additional port example: edit the content, inspect its HTML and reset it.",
              "showcase-lead"));
      main.appendChild(RichTextExamples.render());
    } else {
      main.appendChild(text("h1", "Example not found", ""));
      main.appendChild(link("Browse the widget gallery", "?", "showcase-action"));
    }
    HTMLElement footer = element("footer", "showcase-footer");
    footer.appendChild(
        text(
            "p",
            "Widgets by DominoKit and its contributors. Independent TeaVM port with a shared GWT"
                + " build.",
            ""));
    footer.appendChild(link("About this port", REPO, ""));
    footer.appendChild(
        link("Coverage & limitations", REPO + "/blob/main/docs/COMPATIBILITY.md", ""));
    main.appendChild(footer);
    String title =
        page == null
            ? (route.isEmpty() ? "Explore the widgets" : "Development examples")
            : page.title;
    DomGlobal.document.title = title + " · Domino Widgets · " + compiler;
  }

  private static void header(String compiler, String route) {
    HTMLElement header = element("header", "showcase-topbar");
    header.id = "gallery-header";
    HTMLButtonElement menu = (HTMLButtonElement) element("button", "showcase-menu-toggle");
    menu.type = "button";
    menu.textContent = "Browse";
    menu.setAttribute("aria-controls", "showcase-sidebar");
    menu.setAttribute("aria-expanded", "false");
    menu.addEventListener(
        "click",
        event -> {
          boolean open = !"true".equals(menu.getAttribute("aria-expanded"));
          menu.setAttribute("aria-expanded", String.valueOf(open));
          DomGlobal.document.body.setAttribute("data-nav-open", String.valueOf(open));
        });
    header.appendChild(menu);
    header.appendChild(link("Domino Widgets", "?", "showcase-brand"));
    header.appendChild(text("span", compiler + " showcase", "showcase-compiler"));
    HTMLElement links = element("nav", "showcase-top-links");
    links.setAttribute("aria-label", "Project links");
    String peer = DomGlobal.document.body.getAttribute("data-peer-href");
    links.appendChild(
        link(
            "Try " + (compiler.equals("TeaVM") ? "GWT" : "TeaVM"),
            peer + (route.isEmpty() ? "" : "?page=" + route),
            ""));
    links.appendChild(link("Upstream showcase ↗", ShowcasePages.UPSTREAM + "home", ""));
    links.appendChild(link("GitHub ↗", REPO, ""));
    header.appendChild(links);
    DomGlobal.document.body.appendChild(header);
  }

  private static void sidebar(String route) {
    HTMLElement aside = element("aside", "showcase-sidebar");
    aside.id = "showcase-sidebar";
    aside.appendChild(text("p", "THE WIDGET GALLERY", "showcase-eyebrow"));
    HTMLInputElement search = (HTMLInputElement) element("input", "showcase-search");
    search.type = "search";
    search.placeholder = "Find a widget…";
    search.setAttribute("aria-label", "Find a widget");
    aside.appendChild(search);
    HTMLElement nav = element("nav", "showcase-navigation");
    nav.setAttribute("aria-label", "Widget examples");
    HTMLAnchorElement home = link("Overview", "?", "showcase-nav-home");
    if (route.isEmpty()) home.setAttribute("aria-current", "page");
    nav.appendChild(home);
    List<HTMLElement> groups = new ArrayList<>();
    for (String group : ShowcasePages.GROUPS) {
      HTMLElement details = element("details", "showcase-nav-group");
      // Keep the active group open; the home page starts with the core components.
      if ((route.isEmpty() && group.equals("Components"))
          || (ShowcasePages.find(route) != null && ShowcasePages.find(route).group.equals(group))) {
        details.setAttribute("open", "");
      }
      details.appendChild(text("summary", group, ""));
      for (ShowcasePages.Page page : ShowcasePages.ALL) {
        if (!page.group.equals(group)) continue;
        HTMLAnchorElement item = link(page.title, "?page=" + page.route, "showcase-nav-item");
        item.setAttribute(
            "data-search", (page.title + " " + page.description + " " + group).toLowerCase());
        if (page.route.equals(route)) item.setAttribute("aria-current", "page");
        details.appendChild(item);
      }
      groups.add(details);
      nav.appendChild(details);
    }
    HTMLElement empty =
        text("p", "No matching widgets. Try another name.", "showcase-search-empty");
    empty.setAttribute("hidden", "");
    nav.appendChild(empty);
    search.addEventListener(
        "input",
        event -> {
          String term = search.value.trim().toLowerCase();
          int total = 0;
          for (HTMLElement group : groups) {
            NodeList<Element> items = group.querySelectorAll(".showcase-nav-item");
            int matches = 0;
            for (int i = 0; i < items.length; i++) {
              Element item = items.getAt(i);
              boolean match = item.getAttribute("data-search").contains(term);
              if (match) {
                item.removeAttribute("hidden");
                matches++;
              } else item.setAttribute("hidden", "");
            }
            if (matches == 0) group.setAttribute("hidden", "");
            else group.removeAttribute("hidden");
            if (!term.isEmpty()) group.setAttribute("open", "");
            total += matches;
          }
          if (total == 0) empty.removeAttribute("hidden");
          else empty.setAttribute("hidden", "");
        });
    aside.appendChild(nav);
    HTMLElement extra = element("details", "showcase-extra");
    extra.appendChild(text("summary", "Development examples", ""));
    extra.appendChild(link("Rich text editor", "?page=richtext", "showcase-nav-item"));
    extra.appendChild(link("Widget behaviour checks", "?page=contracts", "showcase-nav-item"));
    extra.appendChild(link("Browser API checks", "?page=browser-apis", "showcase-nav-item"));
    aside.appendChild(extra);
    DomGlobal.document.body.appendChild(aside);
  }

  private static void pageHeading(HTMLElement main, ShowcasePages.Page page) {
    HTMLElement heading = element("section", "showcase-page-heading");
    heading.appendChild(text("p", page.group, "showcase-eyebrow"));
    heading.appendChild(text("h1", page.title, ""));
    heading.appendChild(text("p", page.description, "showcase-lead"));
    HTMLElement actions = element("div", "showcase-page-actions");
    actions.appendChild(link("Compare with upstream ↗", page.upstream, "showcase-action"));
    actions.appendChild(
        link("Java example ↗", REPO + "/blob/main/" + page.source, "showcase-secondary"));
    heading.appendChild(actions);
    heading.appendChild(
        text(
            "p",
            "Adapted from DominoKit’s version-2 examples. The live upstream demo may use a newer"
                + " release.",
            "showcase-note"));
    if (page.route.equals("advanced-forms")) {
      heading.appendChild(
          text(
              "p",
              "Uploads need a server endpoint. This static showcase cannot save files.",
              "showcase-notice"));
    }
    main.appendChild(heading);
  }

  /** Landing structure and section names follow DominoKit's current showcase home. */
  private static void home(HTMLElement main) {
    HTMLElement root = element("div", "showcase-home");
    root.id = "showcase-home";
    HTMLElement hero = element("section", "showcase-hero");
    HTMLElement copy = element("div", "showcase-hero-copy");
    copy.appendChild(text("p", "DOMINO UI", "showcase-eyebrow"));
    copy.appendChild(text("h1", "Build polished enterprise interfaces with Domino UI.", ""));
    copy.appendChild(
        text(
            "p",
            "Explore Domino UI’s components, layouts, forms and tables for Java applications. These examples run through our TeaVM port and the shared GWT build.",
            "showcase-lead"));
    copy.appendChild(
        text(
            "p",
            "Try the widgets in context, then follow each example’s upstream link to compare it with DominoKit’s current demo.",
            "showcase-lead"));
    HTMLElement actions = element("div", "showcase-hero-actions");
    actions.appendChild(link("Open Buttons", "?page=buttons", "showcase-action"));
    actions.appendChild(link("Explore Forms", "?page=forms", "showcase-secondary"));
    actions.appendChild(link("View Tables", "?page=table-basic-data-table", "showcase-secondary"));
    copy.appendChild(actions);
    copy.appendChild(link("Visit DominoKit ↗", "https://dominokit.com/", "showcase-hero-caption"));
    hero.appendChild(copy);
    HTMLElement areas = element("div", "showcase-area-grid");
    area(
        areas,
        "Components",
        "Buttons, cards, trees and the pieces that make up an application screen.");
    area(areas, "Forms", "Fields, validation, date pickers and steps for collecting information.");
    area(areas, "Data", "Contact tables with selection, sorting, search and column controls.");
    area(areas, "Resources", "Java examples, upstream comparisons and guidance on the port.");
    hero.appendChild(areas);
    root.appendChild(hero);
    root.appendChild(text("h2", "Featured areas", "showcase-section-title"));
    root.appendChild(
        text(
            "p",
            "Explore a family of widgets, then combine the pieces your screen needs.",
            "showcase-lead"));
    HTMLElement cards = element("div", "showcase-feature-grid");
    feature(
        cards,
        "01",
        "Component workspace",
        "Start with everyday actions and content containers.",
        "buttons",
        "Buttons",
        "alerts",
        "Alerts",
        "cards",
        "Cards");
    feature(
        cards,
        "02",
        "Forms workflow",
        "Build from simple fields towards validation and guided forms.",
        "forms",
        "Basic forms",
        "inputfields",
        "Input fields",
        "steppers",
        "Steppers");
    feature(
        cards,
        "03",
        "Data and tables",
        "Browse records and try the table plugins already included in this port.",
        "table-basic-data-table",
        "Basic table",
        "table-sort-and-search-plugin",
        "Sort & search",
        "table-pagination-plugin",
        "Pagination");
    feature(
        cards,
        "04",
        "Design foundations",
        "Explore type, spacing and responsive columns. The full colour and icon galleries remain upstream.",
        "typography",
        "Typography",
        "helpers",
        "Spacing & sizing",
        "grids",
        "Grids");
    root.appendChild(cards);
    root.appendChild(text("h2", "Next steps", "showcase-section-title"));
    root.appendChild(
        text(
            "p",
            "Move from individual controls to a complete screen, with the original demo alongside you.",
            "showcase-lead"));
    HTMLElement guidance = element("div", "showcase-feature-grid");
    feature(
        guidance,
        "→",
        "Suggested journeys",
        "Arrange the page with grids and split panels, then add navigation and data.",
        "grids",
        "Explore layouts",
        "splitPanel",
        "Split panels",
        "tree",
        "Trees");
    HTMLElement resources = element("article", "showcase-feature");
    resources.appendChild(text("h3", "Resource center", ""));
    resources.appendChild(
        text(
            "p",
            "Use DominoKit’s site for the library’s documentation and full showcase. Our guide explains the examples and behaviour covered by this port.",
            ""));
    resources.appendChild(
        link("Upstream showcase ↗", ShowcasePages.UPSTREAM + "home", "showcase-feature-primary"));
    HTMLElement links = element("div", "showcase-feature-more");
    links.appendChild(
        link("Domino UI docs ↗", "https://dominokit.com/solutions/domino-ui/v2/docs/", ""));
    links.appendChild(link("Port guide ↗", REPO + "/blob/main/docs/SHOWCASE.md", ""));
    resources.appendChild(links);
    guidance.appendChild(resources);
    root.appendChild(guidance);
    main.appendChild(root);
    root.setAttribute("data-ready", "true");
  }

  private static void area(HTMLElement parent, String title, String description) {
    HTMLElement area = element("section", "showcase-area");
    area.appendChild(text("h2", title, ""));
    area.appendChild(text("p", description, ""));
    parent.appendChild(area);
  }

  private static void feature(
      HTMLElement parent,
      String number,
      String title,
      String description,
      String route,
      String action,
      String second,
      String secondLabel,
      String third,
      String thirdLabel) {
    HTMLElement card = element("article", "showcase-feature");
    card.appendChild(text("span", number, "showcase-feature-number"));
    card.appendChild(text("h3", title, ""));
    card.appendChild(text("p", description, ""));
    card.appendChild(link(action + " →", "?page=" + route, "showcase-feature-primary"));
    HTMLElement more = element("div", "showcase-feature-more");
    more.appendChild(link(secondLabel, "?page=" + second, ""));
    more.appendChild(link(thirdLabel, "?page=" + third, ""));
    card.appendChild(more);
    parent.appendChild(card);
  }

  private static HTMLElement element(String tag, String css) {
    HTMLElement element = (HTMLElement) DomGlobal.document.createElement(tag);
    element.className = css;
    return element;
  }

  private static HTMLElement text(String tag, String value, String css) {
    HTMLElement element = element(tag, css);
    element.textContent = value;
    return element;
  }

  private static HTMLAnchorElement link(String title, String href, String css) {
    HTMLAnchorElement anchor = (HTMLAnchorElement) element("a", css);
    anchor.textContent = title;
    anchor.href = href;
    if (href.startsWith("https://")) {
      anchor.target = "_blank";
      anchor.rel = "noopener noreferrer";
    }
    return anchor;
  }
}
