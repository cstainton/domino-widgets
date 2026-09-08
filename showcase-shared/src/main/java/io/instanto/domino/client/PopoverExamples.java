// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.menu.direction.DropDirection;
import org.dominokit.domino.ui.popover.Popover;
import org.dominokit.domino.ui.utils.PostfixAddOn;

public final class PopoverExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    tooltips();
    popover();
    return element.element();
  }

  private void tooltips() {
    element.appendChild(
        Card.create("TOOLTIPS")
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("TOOLTIP ON RIGHT")
                                    .addCss(dui_bg_accent)
                                    .setTooltip("Tooltip on right", DropDirection.RIGHT_MIDDLE)))
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("TOOLTIP ON TOP")
                                    .addCss(dui_bg_accent)
                                    .setTooltip("Tooltip on top", DropDirection.TOP_MIDDLE)))
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("TOOLTIP ON BOTTOM")
                                    .addCss(dui_bg_accent)
                                    .setTooltip("Tooltip on bottom", DropDirection.BOTTOM_MIDDLE)))
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("TOOLTIP ON LEFT")
                                    .addCss(dui_bg_accent)
                                    .setTooltip("Tooltip on bottom", DropDirection.LEFT_MIDDLE)))));
  }

  private void popover() {
    element.appendChild(
        Card.create("POPOVER")
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("POPOVER ON RIGHT")
                                    .addCss(dui_bg_accent)
                                    .apply(
                                        button -> {
                                          Popover.create(button)
                                              .addCss(dui_bg_accent, dui_rounded_sm)
                                              .setPosition(DropDirection.RIGHT_MIDDLE)
                                              .appendChild(
                                                  Card.create("Popover on right")
                                                      .setIcon(Icons.message_settings_outline())
                                                      .addCss(
                                                          dui_bg_accent,
                                                          dui_fg,
                                                          dui_elevation_0,
                                                          dui_m_2px,
                                                          dui_rounded_sm)
                                                      .appendChild(
                                                          PostfixAddOn.of(
                                                              Icons.dots_vertical().clickable()))
                                                      .appendChild(
                                                          h(4).addCss(dui_m_t_0)
                                                              .appendChild("Headline here"))
                                                      .appendChild(
                                                          p(
                                                              "Vivamus sagittis lacus vel augue"
                                                                  + " laoreet rutrum faucibus.")));
                                        })))
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("POPOVER ON TOP")
                                    .addCss(dui_bg_accent)
                                    .apply(
                                        button -> {
                                          Popover.create(button)
                                              .addCss(dui_bg_accent, dui_rounded_sm)
                                              .setPosition(DropDirection.TOP_MIDDLE)
                                              .appendChild(
                                                  Card.create("Popover on TOP")
                                                      .setIcon(Icons.message_settings_outline())
                                                      .addCss(
                                                          dui_bg_accent,
                                                          dui_fg,
                                                          dui_elevation_0,
                                                          dui_m_2px,
                                                          dui_rounded_sm)
                                                      .appendChild(
                                                          PostfixAddOn.of(
                                                              Icons.dots_vertical().clickable()))
                                                      .appendChild(
                                                          h(4).addCss(dui_m_t_0)
                                                              .appendChild("Headline here"))
                                                      .appendChild(
                                                          p(
                                                              "Vivamus sagittis lacus vel augue"
                                                                  + " laoreet rutrum faucibus.")));
                                        })))
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("POPOVER ON BOTTOM")
                                    .addCss(dui_bg_accent)
                                    .apply(
                                        button -> {
                                          Popover.create(button)
                                              .addCss(dui_bg_accent, dui_rounded_sm)
                                              .setPosition(DropDirection.BOTTOM_MIDDLE)
                                              .appendChild(
                                                  Card.create("Popover on BOTTOM")
                                                      .setIcon(Icons.message_settings_outline())
                                                      .addCss(
                                                          dui_bg_accent,
                                                          dui_fg,
                                                          dui_elevation_0,
                                                          dui_m_2px,
                                                          dui_rounded_sm)
                                                      .appendChild(
                                                          PostfixAddOn.of(
                                                              Icons.dots_vertical().clickable()))
                                                      .appendChild(
                                                          h(4).addCss(dui_m_t_0)
                                                              .appendChild("Headline here"))
                                                      .appendChild(
                                                          p(
                                                              "Vivamus sagittis lacus vel augue"
                                                                  + " laoreet rutrum faucibus.")));
                                        })))
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                Button.create("POPOVER ON LEFT")
                                    .addCss(dui_bg_accent, dui_fg)
                                    .apply(
                                        button -> {
                                          Popover.create(button)
                                              .addCss(dui_bg_accent, dui_rounded_sm)
                                              .setPosition(DropDirection.LEFT_MIDDLE)
                                              .appendChild(
                                                  Card.create("Popover on left")
                                                      .setIcon(Icons.message_settings_outline())
                                                      .addCss(
                                                          dui_bg_accent,
                                                          dui_fg,
                                                          dui_elevation_0,
                                                          dui_m_2px,
                                                          dui_rounded_sm)
                                                      .appendChild(
                                                          PostfixAddOn.of(
                                                              Icons.dots_vertical().clickable()))
                                                      .appendChild(
                                                          h(4).addCss(dui_m_t_0)
                                                              .appendChild("Headline here"))
                                                      .appendChild(
                                                          p(
                                                              "Vivamus sagittis lacus vel augue"
                                                                  + " laoreet rutrum faucibus.")));
                                        })))));
  }
}
