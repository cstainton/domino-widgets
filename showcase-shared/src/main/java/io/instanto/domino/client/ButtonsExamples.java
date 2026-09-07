// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.grid.Column.Span._12;
import static org.dominokit.domino.ui.grid.Column.Span._3;
import static org.dominokit.domino.ui.grid.Column.Span._6;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.button.LinkButton;
import org.dominokit.domino.ui.button.group.ButtonsGroup;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;

public final class ButtonsExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    initSimpleButtons();
    initButtonSizes();
    initDisabledButtons();
    initButtonsBasicGroup();
    return element.element();
  }

  private void initSimpleButtons() {
    element.appendChild(
        Card.create(
                "BOOTSTRAP DEFAULT BUTTONS",
                "Use any of the available button classes to quickly create a styled button")
            .appendChild(LinkButton.create("DEFAULT").addCss(dui_w_28, dui_m_1))
            .appendChild(Button.create("PRIMARY").addCss(dui_primary, dui_w_28, dui_m_1))
            .appendChild(Button.create("SUCCESS").addCss(dui_success, dui_w_28, dui_m_1))
            .appendChild(Button.create("INFO").addCss(dui_info, dui_w_28, dui_m_1))
            .appendChild(Button.create("WARNING").addCss(dui_warning, dui_w_28, dui_m_1))
            .appendChild(Button.create("DANGER").addCss(dui_error, dui_w_28, dui_m_1))
            .element());
  }

  private void initButtonSizes() {
    element.appendChild(
        Card.create("BUTTON SIZES", "You can resize the buttons")
            .appendChild(
                Row.create()
                    .addCss(dui_m_3)
                    .span12(
                        Row.create()
                            .appendChild(
                                Column.colspan(_3, _3, _6, _12)
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("LARGE")
                                                    .addCss(dui_large, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("DEFAULT").addCss(dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("SMALL")
                                                    .addCss(dui_small, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("XSMALL")
                                                    .addCss(dui_xsmall, dui_w_32, dui_m_1))))
                            .appendChild(
                                Column.colspan(_3, _3, _6, _12)
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("LARGE")
                                                    .addCss(
                                                        dui_primary, dui_large, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("DEFAULT")
                                                    .addCss(dui_primary, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("SMALL")
                                                    .addCss(
                                                        dui_primary, dui_small, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("XSMALL")
                                                    .addCss(
                                                        dui_primary,
                                                        dui_xsmall,
                                                        dui_w_32,
                                                        dui_m_1))))
                            .appendChild(
                                Column.colspan(_3, _3, _6, _12)
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("LARGE")
                                                    .addCss(
                                                        dui_warning, dui_large, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("DEFAULT")
                                                    .addCss(dui_warning, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("SMALL")
                                                    .addCss(
                                                        dui_warning, dui_small, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("XSMALL")
                                                    .addCss(
                                                        dui_warning,
                                                        dui_xsmall,
                                                        dui_w_32,
                                                        dui_m_1))))
                            .appendChild(
                                Column.colspan(_3, _3, _6, _12)
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("LARGE")
                                                    .addCss(
                                                        dui_info, dui_large, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("DEFAULT")
                                                    .addCss(dui_info, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("SMALL")
                                                    .addCss(
                                                        dui_info, dui_small, dui_w_32, dui_m_1)))
                                    .appendChild(
                                        Row.create()
                                            .appendChild(
                                                Button.create("XSMALL")
                                                    .addCss(
                                                        dui_info,
                                                        dui_xsmall,
                                                        dui_w_32,
                                                        dui_m_1))))))
            .element());
  }

  private void initDisabledButtons() {
    element.appendChild(
        Card.create(
                "DISABLED BUTTONS",
                "Make buttons look unclickable by fading them back with opacity")
            .appendChild(Button.create("DEFAULT").addCss(dui_m_1, dui_w_28).disable())
            .appendChild(Button.create("PRIMARY").addCss(dui_primary, dui_m_1, dui_w_28).disable())
            .appendChild(Button.create("INFO").addCss(dui_info, dui_m_1, dui_w_28).disable())
            .appendChild(Button.create("WARNING").addCss(dui_warning, dui_m_1, dui_w_28).disable())
            .appendChild(Button.create("DANGER").addCss(dui_error, dui_m_1, dui_w_28).disable())
            .element());
  }

  private void initButtonsBasicGroup() {
    element.appendChild(
        Card.create("BASIC EXAMPLE", "Create group of buttons")
            .appendChild(
                ButtonsGroup.create()
                    .addCss(dui_m_4)
                    .appendChild(Button.create("LEFT"))
                    .appendChild(Button.create("MIDDLE"))
                    .appendChild(Button.create("RIGHT")))
            .appendChild(
                ButtonsGroup.create()
                    .addCss(dui_primary)
                    .addCss(dui_m_4)
                    .appendChild(Button.create("LEFT"))
                    .appendChild(Button.create("MIDDLE"))
                    .appendChild(Button.create("RIGHT")))
            .appendChild(
                ButtonsGroup.create()
                    .addCss(dui_success)
                    .addCss(dui_m_4)
                    .appendChild(Button.create("LEFT"))
                    .appendChild(Button.create("MIDDLE"))
                    .appendChild(Button.create("RIGHT")))
            .appendChild(
                ButtonsGroup.create()
                    .addCss(dui_info)
                    .addCss(dui_m_4)
                    .appendChild(Button.create("LEFT"))
                    .appendChild(Button.create("MIDDLE"))
                    .appendChild(Button.create("RIGHT")))
            .appendChild(
                ButtonsGroup.create()
                    .addCss(dui_warning)
                    .addCss(dui_m_4)
                    .appendChild(Button.create("LEFT"))
                    .appendChild(Button.create("MIDDLE"))
                    .appendChild(Button.create("RIGHT")))
            .appendChild(
                ButtonsGroup.create()
                    .addCss(dui_error)
                    .addCss(dui_m_4)
                    .appendChild(Button.create("LEFT"))
                    .appendChild(Button.create("MIDDLE"))
                    .appendChild(Button.create("RIGHT")))
            .appendChild(
                ButtonsGroup.create()
                    .addCss(dui_accent)
                    .addCss(dui_m_4)
                    .appendChild(Button.create("LEFT"))
                    .appendChild(Button.create("MIDDLE"))
                    .appendChild(Button.create("RIGHT")))
            .element());
  }
}
