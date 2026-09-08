// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.dnd.DragSource;
import org.dominokit.domino.ui.dnd.Draggable;
import org.dominokit.domino.ui.dnd.DropZone;
import org.dominokit.domino.ui.elements.DivElement;

public final class DndExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    dragAndDrop();
    return element.element();
  }

  private void dragAndDrop() {
    DivElement dropArea =
        div().addCss(dui_flex, dui_items_center, dui_justify_center, dui_h_72, dui_w_72, dui_red);
    DivElement dragArea =
        div()
            .addCss(dui_flex, dui_items_center, dui_justify_center, dui_h_72, dui_w_72, dui_yellow);
    ;

    DivElement draggableElement =
        div()
            .addCss(
                dui_flex,
                dui_items_center,
                dui_justify_center,
                dui_blue,
                dui_h_24,
                dui_w_24,
                dui_m_1)
            .setTextContent("BOX-A");

    DivElement draggableElement1 =
        div()
            .addCss(
                dui_flex,
                dui_items_center,
                dui_justify_center,
                dui_blue,
                dui_h_24,
                dui_w_24,
                dui_m_1)
            .setTextContent("BOX-B");

    dragArea.appendChild(draggableElement).appendChild(draggableElement1);

    DropZone dropZone = new DropZone();
    dropZone.addDropTarget(
        dropArea,
        draggableId -> {
          if (draggableElement.getDominoId().equals(draggableId)) {
            draggableElement.remove();
            dropArea.appendChild(draggableElement);
          } else if (draggableElement1.getDominoId().equals(draggableId)) {
            draggableElement1.remove();
            dropArea.appendChild(draggableElement1);
          }
        });

    DropZone dropZone1 = new DropZone();
    dropZone1.addDropTarget(
        dragArea,
        draggableId -> {
          if (draggableElement.getDominoId().equals(draggableId)) {
            draggableElement.remove();
            dragArea.appendChild(draggableElement);
          } else if (draggableElement1.getDominoId().equals(draggableId)) {
            draggableElement1.remove();
            dragArea.appendChild(draggableElement1);
          }
        });

    DragSource dragSource = new DragSource();
    dragSource.addDraggable(Draggable.of(draggableElement));
    dragSource.addDraggable(Draggable.of(draggableElement1));

    element.appendChild(
        Card.create("BASIC USAGE", "")
            .appendChild(
                div()
                    .addCss(dui_flex, dui_justify_evenly)
                    .appendChild(
                        div()
                            .appendChild(
                                div()
                                    .addCss(dui_flex, dui_flex_col)
                                    .appendChild(
                                        div()
                                            .addCss(dui_font_size_4)
                                            .appendChild(text("Drag zone")))
                                    .appendChild(div().appendChild(dragArea))))
                    .appendChild(
                        div()
                            .appendChild(
                                div()
                                    .addCss(dui_flex, dui_flex_col)
                                    .appendChild(
                                        div()
                                            .addCss(dui_font_size_4)
                                            .appendChild(text("Drop zone")))
                                    .appendChild(div().appendChild(dropArea))))));
  }
}
