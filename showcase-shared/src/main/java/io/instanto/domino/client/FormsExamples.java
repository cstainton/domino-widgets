// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.grid.Column.Span._2;
import static org.dominokit.domino.ui.grid.Column.Span._6;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.forms.*;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.typography.BlockHeader;

public final class FormsExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    initBasicExamples();
    initBasicTextAreaExample();
    initCheckboxExample();
    return element.element();
  }

  private void initBasicExamples() {
    element.appendChild(
        Card.create("INPUT", "Different sizes and widths.")
            .setCollapsible(true)
            .appendChild(BlockHeader.create("Basic Example"))
            .appendChild(
                Row.create()
                    .span12(TextBox.create().setLabel("User name").setPlaceholder("Username")))
            .appendChild(
                Row.create()
                    .span12(PasswordBox.create().setLabel("Password").setPlaceholder("Password")))
            .appendChild(BlockHeader.create("Different Widths"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6().appendChild(TextBox.create().setPlaceholder("col-sm-6")))
                    .appendChild(
                        Column.span6().appendChild(TextBox.create().setPlaceholder("col-sm-6"))))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span4().appendChild(TextBox.create().setPlaceholder("col-sm-4")))
                    .appendChild(
                        Column.span4().appendChild(TextBox.create().setPlaceholder("col-sm-4")))
                    .appendChild(
                        Column.span4().appendChild(TextBox.create().setPlaceholder("col-sm-4"))))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span3().appendChild(TextBox.create().setPlaceholder("col-sm-3")))
                    .appendChild(
                        Column.span3().appendChild(TextBox.create().setPlaceholder("col-sm-3")))
                    .appendChild(
                        Column.span3().appendChild(TextBox.create().setPlaceholder("col-sm-3")))
                    .appendChild(
                        Column.span3().appendChild(TextBox.create().setPlaceholder("col-sm-3"))))
            .appendChild(BlockHeader.create("Input Status"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span4().appendChild(TextBox.create("Focused").withValue("Focused")))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TextBox.create("Disabled").withValue("disabled").disable()))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TextBox.create("Read only")
                                    .withValue("Sample value")
                                    .setReadOnly(true)))));
  }

  private void initBasicTextAreaExample() {
    element.appendChild(
        Card.create("TEXTAREA")
            .setCollapsible(true)
            .appendChild(BlockHeader.create("Basic Examples"))
            .appendChild(TextAreaBox.create().setPlaceholder("Start typing here..."))
            .appendChild(BlockHeader.create("Auto Growing Vertical Direction"))
            .appendChild(TextAreaBox.create().setPlaceholder("Start typing here...").autoSize())
            .appendChild(BlockHeader.create("Text Area With Label"))
            .appendChild(TextAreaBox.create("Description").autoSize()));
  }

  private void initCheckboxExample() {
    element.appendChild(
        Card.create("CHECKBOX")
            .setCollapsible(true)
            .appendChild(h(5).textContent("Basic Examples"))
            .appendChild(
                Row.create()
                    .appendChild(Column.colspan(_2, _6).appendChild(CheckBox.create("Default")))
                    .appendChild(
                        Column.colspan(_2, _6).appendChild(CheckBox.create("Filled In").filledIn()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("Default - Disabled").check().disable()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("Filled In - Disabled")
                                    .check()
                                    .filledIn()
                                    .disable())))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("Default").setReadOnly(true)))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("Filled In").filledIn().setReadOnly(true)))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("Default - Disabled").check().disable()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("Filled In - Disabled")
                                    .check()
                                    .filledIn()
                                    .disable())))
            .appendChild(h(5).textContent("With Material Design Colors"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.colspan(_2, _6).appendChild(CheckBox.create("ACCENT").check()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("RED").addCss(dui_accent_red).check()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("AMBER").addCss(dui_accent_amber).check()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("DEEP PURPLE")
                                    .addCss(dui_accent_deep_purple)
                                    .check()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("INDIGO").addCss(dui_accent_indigo).check()))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("BLUE").addCss(dui_accent_blue).check())))
            .appendChild(h(5).textContent("With Material Design Colors - Filled In"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(CheckBox.create("ACCENT").check().setFilled(true)))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("RED")
                                    .addCss(dui_accent_red)
                                    .check()
                                    .setFilled(true)))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("AMBER")
                                    .addCss(dui_accent_amber)
                                    .check()
                                    .setFilled(true)))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("DEEP PURPLE")
                                    .addCss(dui_accent_deep_purple)
                                    .check()
                                    .setFilled(true)))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("INDIGO")
                                    .addCss(dui_accent_indigo)
                                    .check()
                                    .setFilled(true)))
                    .appendChild(
                        Column.colspan(_2, _6)
                            .appendChild(
                                CheckBox.create("BLUE")
                                    .addCss(dui_accent_blue)
                                    .check()
                                    .setFilled(true)))));
  }
}
