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
    HTMLSelectElement selector=(HTMLSelectElement)DomGlobal.document.createElement("select");
    selector.setAttribute("aria-label","All examples");
    HTMLOptionElement prompt=(HTMLOptionElement)DomGlobal.document.createElement("option");
    prompt.textContent="Browse all examples";prompt.value="";selector.appendChild(prompt);
    java.util.List<String> allRoutes=new java.util.ArrayList<>(GalleryCatalog.ROUTES);
    allRoutes.add("browser-apis");allRoutes.add("richtext");
    for(String route:allRoutes){
      HTMLOptionElement option=(HTMLOptionElement)DomGlobal.document.createElement("option");
      option.value=route;option.textContent=route;selector.appendChild(option);
    }
    selector.addEventListener("change",e->{if(!selector.value.isEmpty())DomGlobal.location.href="?page="+selector.value;});
    nav.appendChild(selector);
    header.appendChild(nav);
    DomGlobal.document.body.appendChild(header);
    String query = DomGlobal.location.search;
    if(query.equals("?page=richtext")){DomGlobal.document.body.appendChild(RichTextExamples.render());return;}
    if(query.equals("?page=browser-apis")){DomGlobal.document.body.appendChild(BrowserApis.render());return;}
    HTMLElement examples=GalleryCatalog.render(query.startsWith("?page=")?query.substring(6):"");
    if(examples==null){SharedScreen.mount();return;}
    examples.id = "gallery-examples";
    DomGlobal.document.body.appendChild(examples);
    examples.setAttribute("data-ready", "true");
  }
}
