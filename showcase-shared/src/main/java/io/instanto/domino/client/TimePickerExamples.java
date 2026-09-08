// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import java.util.Date;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.forms.TimeBox;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.menu.direction.DropDirection;
import org.dominokit.domino.ui.popover.Popover;
import org.dominokit.domino.ui.timepicker.TimePicker;
import org.dominokit.domino.ui.timepicker.TimeStyle;
import org.dominokit.domino.ui.typography.BlockHeader;

public final class TimePickerExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    inlineTimePicker();
    withHeader();
    withFooter();
    dropdownTimePicker();
    timeBox();
    return element.element();
  }

  private void inlineTimePicker() {
    element.appendChild(
        Card.create("INLINE TIMEPICKER")
            .setCollapsible(true)
            .appendChild(BlockHeader.create("Different locales"))
            .appendChild(
                Row.create()
                    .span4(TimePicker.create())
                    .span4(
                        TimePicker.create(
                            new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                "ar")))
                    .span4(
                        TimePicker.create(
                            new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                "es"))))
            .appendChild(BlockHeader.create("With seconds"))
            .appendChild(
                Row.create()
                    .span4(TimePicker.create().setShowSeconds(true))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .setShowSeconds(true))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .setShowSeconds(true)))
            .appendChild(BlockHeader.create("24 hours style"))
            .appendChild(
                Row.create()
                    .span4(TimePicker.create().setTimeStyle(TimeStyle._24))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .setTimeStyle(TimeStyle._24))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .setTimeStyle(TimeStyle._24))));
  }

  private void withHeader() {
    element.appendChild(
        Card.create("INLINE TIMEPICKER", "With header")
            .setCollapsible(true)
            .appendChild(BlockHeader.create("Different locales"))
            .appendChild(
                Row.create()
                    .span4(TimePicker.create().withHeader())
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .withHeader()
                            .addCss(dui_accent_blue))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .withHeader()
                            .addCss(dui_accent_teal)))
            .appendChild(BlockHeader.create("With seconds"))
            .appendChild(
                Row.create()
                    .span4(TimePicker.create().setShowSeconds(true).withHeader())
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .setShowSeconds(true)
                            .withHeader()
                            .addCss(dui_accent_blue))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .setShowSeconds(true)
                            .withHeader()
                            .addCss(dui_accent_teal)))
            .appendChild(BlockHeader.create("24 hours style"))
            .appendChild(
                Row.create()
                    .span4(TimePicker.create().setTimeStyle(TimeStyle._24).withHeader())
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .setTimeStyle(TimeStyle._24)
                            .withHeader()
                            .addCss(dui_accent_blue))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .setTimeStyle(TimeStyle._24)
                            .withHeader()
                            .addCss(dui_accent_teal))));
  }

  private void withFooter() {
    element.appendChild(
        Card.create("INLINE TIMEPICKER", "With footer")
            .setCollapsible(true)
            .appendChild(BlockHeader.create("Different locales"))
            .appendChild(
                Row.create()
                    .span4(
                        TimePicker.create()
                            .withHeader()
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date())))))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .withHeader()
                            .addCss(dui_accent_blue)
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date())))))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .withHeader()
                            .addCss(dui_accent_teal)
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date()))))))
            .appendChild(BlockHeader.create("With seconds"))
            .appendChild(
                Row.create()
                    .span4(
                        TimePicker.create()
                            .setShowSeconds(true)
                            .withHeader()
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date())))))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .setShowSeconds(true)
                            .withHeader()
                            .addCss(dui_accent_blue)
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date())))))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .setShowSeconds(true)
                            .withHeader()
                            .addCss(dui_accent_teal)
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date()))))))
            .appendChild(BlockHeader.create("24 hours style"))
            .appendChild(
                Row.create()
                    .span4(
                        TimePicker.create()
                            .setTimeStyle(TimeStyle._24)
                            .withHeader()
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date())))))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .setTimeStyle(TimeStyle._24)
                            .withHeader()
                            .addCss(dui_accent_blue)
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date())))))
                    .span4(
                        TimePicker.create(
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .setTimeStyle(TimeStyle._24)
                            .withHeader()
                            .addCss(dui_accent_teal)
                            .withFooter(
                                (timePicker, footer) ->
                                    footer
                                        .addCss(dui_flex, dui_justify_center)
                                        .appendChild(
                                            Button.create(Icons.clock_outline(), "NOW")
                                                .addClickListener(
                                                    evt -> timePicker.setDate(new Date())))))));
  }

  private void dropdownTimePicker() {
    element.appendChild(
        Card.create("DROP DOWN")
            .setCollapsible(true)
            .appendChild(
                Row.create()
                    .span2(
                        Button.create(Icons.calendar(), "Pick time")
                            .apply(
                                button -> {
                                  Popover.create(button)
                                      .setPosition(DropDirection.BEST_MIDDLE_UP_DOWN)
                                      .appendChild(TimePicker.create().withHeader());
                                }))
                    .span2(
                        Button.create(Icons.calendar(), "Pick time")
                            .apply(
                                button -> {
                                  Popover.create(button)
                                      .setPosition(DropDirection.BEST_SIDE_UP_DOWN)
                                      .appendChild(TimePicker.create().withHeader());
                                }))
                    .span2(
                        Button.create(Icons.calendar(), "Pick time")
                            .apply(
                                button -> {
                                  Popover.create(button)
                                      .setPosition(DropDirection.BEST_MIDDLE_SIDE)
                                      .appendChild(TimePicker.create().withHeader());
                                }))
                    .span2(
                        Button.create(Icons.calendar(), "Pick time")
                            .apply(
                                button -> {
                                  Popover.create(button)
                                      .setPosition(DropDirection.MIDDLE_SCREEN)
                                      .appendChild(TimePicker.create().withHeader());
                                }))
                    .span2(
                        Button.create(Icons.calendar(), "Pick time")
                            .apply(
                                button -> {
                                  Popover.create(button)
                                      .setModal(true)
                                      .setPosition(DropDirection.MIDDLE_SCREEN)
                                      .appendChild(TimePicker.create().withHeader());
                                }))
                    .span2(
                        Button.create(Icons.calendar(), "Pick time")
                            .apply(
                                button -> {
                                  Popover.create(button)
                                      .setPosition(DropDirection.BEST_FIT_SIDE)
                                      .appendChild(TimePicker.create().withHeader());
                                }))));
  }

  private void timeBox() {
    element.appendChild(
        Card.create("TIME BOX")
            .setCollapsible(true)
            .appendChild(
                Row.create()
                    .span4(TimeBox.create("Default"))
                    .span4(
                        TimeBox.create(
                                "With pattern",
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "ar"))
                            .setPattern("HH:mm:ss")
                            .withPopover((parent, popover) -> popover.addCss(dui_accent_blue))
                            .withTimePicker((parent, timePicker) -> timePicker.withHeader()))
                    .span4(
                        TimeBox.create(
                                "With parse strict",
                                new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo(
                                    "es"))
                            .setPattern("HH-mm-ss")
                            .setParseStrict(true)
                            .withPopover((parent, popover) -> popover.addCss(dui_accent_teal))
                            .withTimePicker((parent, timePicker) -> timePicker.withHeader()))));
  }
}
