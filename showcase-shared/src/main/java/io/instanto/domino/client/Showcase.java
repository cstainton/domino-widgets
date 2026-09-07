package io.instanto.domino.client;

import elemental2.dom.*;

/** Shared launcher for the original showcase samples and behavioral fixture. */
public final class Showcase {
  public static void mount() {
    HTMLElement header = (HTMLElement) DomGlobal.document.createElement("header");
    header.id = "gallery-header";
    HTMLElement title = (HTMLElement) DomGlobal.document.createElement("h1");
    title.textContent = "Domino Widgets";
    header.appendChild(title);
    HTMLElement intro = (HTMLElement) DomGlobal.document.createElement("p");
    intro.textContent = "Original Domino examples, shared by GWT and TeaVM.";
    header.appendChild(intro);
    HTMLElement nav = (HTMLElement) DomGlobal.document.createElement("nav");
    String[] routes = {"buttons", "forms", "dialogs", "contracts"};
    String[] labels = {"Buttons", "Forms", "Dialogs", "Table & lifecycle contracts"};
    for (int i = 0; i < routes.length; i++) {
      HTMLAnchorElement link = (HTMLAnchorElement) DomGlobal.document.createElement("a");
      link.href = "?page=" + routes[i];
      link.textContent = labels[i];
      nav.appendChild(link);
    }
    header.appendChild(nav);
    DomGlobal.document.body.appendChild(header);
    String query = DomGlobal.location.search;
    HTMLElement examples;
    if (query.equals("?page=buttons")) examples = new ButtonsExamples().render();
    else if (query.equals("?page=forms")) examples = new FormsExamples().render();
    else if (query.equals("?page=dialogs")) examples = new DialogsExamples().render();
    else {
      SharedScreen.mount();
      return;
    }
    examples.id = "gallery-examples";
    DomGlobal.document.body.appendChild(examples);
    examples.setAttribute("data-ready", "true");
  }
}
