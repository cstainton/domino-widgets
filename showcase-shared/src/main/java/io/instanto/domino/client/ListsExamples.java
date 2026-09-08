// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.lists.ListGroup;
import org.dominokit.domino.ui.style.BooleanCssClass;

public final class ListsExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    listSample();
    return element.element();
  }

  private void listSample() {

    ListGroup<Contact> singleSelectList;
    ListGroup<Contact> multiSelectList;
    element.appendChild(
        Card.create()
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6()
                            .appendChild(h(4).textContent("Single select"))
                            .appendChild(
                                singleSelectList =
                                    ListGroup.<Contact>create()
                                        .setItemRenderer(
                                            (listGroup, item) -> {
                                              item.setSelectable(true)
                                                  .appendChild(
                                                      div()
                                                          .addCss(
                                                              dui_flex,
                                                              dui_items_center,
                                                              dui_h_16,
                                                              BooleanCssClass.of(
                                                                  dui_opacity_50,
                                                                  !item.getValue().isActive()))
                                                          .appendChild(
                                                              span()
                                                                  .addCss(
                                                                      dui_h_full,
                                                                      dui_w_1,
                                                                      dui_self_stretch,
                                                                      ContactUiUtils.getColor(
                                                                              item.getValue())
                                                                          .getBackground()))
                                                          .appendChild(
                                                              img(item.getValue().getPicture())
                                                                  .addCss(dui_h_16, dui_w_16)
                                                                  .setOrRemoveCssProperty(
                                                                      "filter",
                                                                      "grayscale(100%)",
                                                                      imageElement ->
                                                                          !item.getValue()
                                                                              .isActive()))
                                                          .appendChild(
                                                              span()
                                                                  .textContent(
                                                                      item.getValue().getName())
                                                                  .addCss(dui_grow_1, dui_p_1))
                                                          .appendChild(
                                                              Badge.create(
                                                                      String.valueOf(
                                                                          item.getValue()
                                                                              .getBalance()))
                                                                  .addCss(
                                                                      ContactUiUtils.getColor(
                                                                              item.getValue())
                                                                          .getCss(),
                                                                      dui_fg_white,
                                                                      dui_order_none))
                                                          .appendChild(
                                                              ContactUiUtils.getGenderElement(
                                                                  item.getValue())))
                                                  .setDisabled(!item.getValue().isActive())
                                                  .addSelectionListener(
                                                      (source, selection) -> {
                                                        item.addCss(
                                                            BooleanCssClass.of(
                                                                dui_bg_accent_l_4,
                                                                selection.contains(item)));
                                                      });
                                            })))
                    .appendChild(
                        Column.span6()
                            .appendChild(h(4).textContent("Multi select"))
                            .appendChild(
                                multiSelectList =
                                    ListGroup.<Contact>create()
                                        .setMultiSelect(true)
                                        .setItemRenderer(
                                            (listGroup, item) -> {
                                              item.setSelectable(true)
                                                  .appendChild(
                                                      div()
                                                          .addCss(
                                                              dui_flex,
                                                              dui_items_center,
                                                              dui_h_16,
                                                              BooleanCssClass.of(
                                                                  dui_opacity_50,
                                                                  !item.getValue().isActive()))
                                                          .appendChild(
                                                              span()
                                                                  .addCss(
                                                                      dui_h_full,
                                                                      dui_w_1,
                                                                      dui_self_stretch,
                                                                      ContactUiUtils.getColor(
                                                                              item.getValue())
                                                                          .getBackground()))
                                                          .appendChild(
                                                              img(item.getValue().getPicture())
                                                                  .addCss(dui_h_16, dui_w_16)
                                                                  .setOrRemoveCssProperty(
                                                                      "filter",
                                                                      "grayscale(100%)",
                                                                      imageElement ->
                                                                          !item.getValue()
                                                                              .isActive()))
                                                          .appendChild(
                                                              span()
                                                                  .textContent(
                                                                      item.getValue().getName())
                                                                  .addCss(dui_grow_1, dui_p_1))
                                                          .appendChild(
                                                              Badge.create(
                                                                      String.valueOf(
                                                                          item.getValue()
                                                                              .getBalance()))
                                                                  .addCss(
                                                                      ContactUiUtils.getColor(
                                                                              item.getValue())
                                                                          .getCss(),
                                                                      dui_fg_white,
                                                                      dui_order_none))
                                                          .appendChild(
                                                              ContactUiUtils.getGenderElement(
                                                                  item.getValue())))
                                                  .setDisabled(!item.getValue().isActive())
                                                  .addSelectionListener(
                                                      (source, selection) -> {
                                                        item.addCss(
                                                            BooleanCssClass.of(
                                                                dui_bg_accent_l_4,
                                                                selection.contains(item)));
                                                      });
                                            }))))
            .element());

    singleSelectList.setItems(SampleContacts.create());
    multiSelectList.setItems(SampleContacts.create());
  }
}
