package io.instanto.domino.client;

import elemental2.dom.*;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.richtext.RichTextEditor;

/** Additional original widget contract where the old showcase has no suitable standalone page. */
public final class RichTextExamples {
  public static HTMLElement render() {
    HTMLElement root = (HTMLElement) DomGlobal.document.createElement("section");
    root.id = "richtext-example";
    RichTextEditor editor = RichTextEditor.create();
    editor.setValue("<p>Initial content</p>");
    root.appendChild(editor.element());
    root.appendChild(
        Button.create("Read HTML")
            .setId("read-html")
            .addClickListener(e -> root.setAttribute("data-html", editor.getValue()))
            .element());
    root.appendChild(
        Button.create("Reset editor")
            .setId("reset-editor")
            .addClickListener(e -> editor.setValue("<p>Reset content</p>"))
            .element());
    root.setAttribute("data-ready", "true");
    return root;
  }
}
