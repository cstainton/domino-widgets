// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see upstream/showcase-lock.json.
package io.instanto.domino.client;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.datepicker.Calendar;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.forms.DateBox;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.menu.direction.DropDirection;
import org.dominokit.domino.ui.popover.Popover;
import org.dominokit.domino.ui.typography.BlockHeader;


import java.util.Date;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.elements.DivElement;
import static org.dominokit.domino.ui.utils.Domino.*;
public final class DatePickerExamples implements org.dominokit.domino.ui.style.DominoCss {
 private final DivElement element=div();

 public HTMLDivElement render(){
inlineCalendar();
withHeader();
withFooter();
dropdownCalendar();
dateBox();
return element.element();}
    private void inlineCalendar() {
        element
                .appendChild(Card.create("INLINE CALENDAR", "Different locales")
                        .setCollapsible(true)
                        .appendChild(Row.create()
                                .span4(Calendar.create())
                                .span4(Calendar.create(new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("ar")))
                                .span4(Calendar.create(new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("es")))
                        )
                );
    }
    private void withHeader() {
        element
                .appendChild(Card.create("INLINE CALENDAR", "With header")
                        .setCollapsible(true)
                        .appendChild(Row.create()
                                .span4(Calendar.create()
                                        .withHeader()
                                )
                                .span4(Calendar.create(new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("ar"))
                                        .addCss(dui_accent_blue)
                                        .withHeader()
                                )
                                .span4(Calendar.create(new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("es"))
                                        .addCss(dui_accent_teal)
                                        .withHeader()
                                )
                        )
                );
    }
    private void withFooter() {
        element
                .appendChild(Card.create("INLINE CALENDAR", "With footer")
                        .setCollapsible(true)
                        .appendChild(Row.create()
                                .span4(Calendar.create()
                                        .withHeader()
                                        .withFooter((calendar, footer) -> footer
                                                .addCss(dui_flex, dui_justify_center)
                                                .appendChild(Button.create(Icons.calendar_today(), "TODAY")
                                                        .addClickListener(evt -> calendar.setDate(new Date()))
                                                )
                                        )
                                )
                                .span4(Calendar.create(new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("ar"))
                                        .addCss(dui_accent_blue)
                                        .withHeader()
                                        .withFooter((calendar, footer) -> footer
                                                .addCss(dui_flex, dui_justify_center)
                                                .appendChild(Button.create(Icons.calendar_today(), "TODAY")
                                                        .addClickListener(evt -> calendar.setDate(new Date()))
                                                )
                                        )
                                )
                                .span4(Calendar.create(new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("es"))
                                        .addCss(dui_accent_teal)
                                        .withHeader()
                                        .withFooter((calendar, footer) -> footer
                                                .addCss(dui_flex, dui_justify_center)
                                                .appendChild(Button.create(Icons.calendar_today(), "TODAY")
                                                        .addClickListener(evt -> calendar.setDate(new Date()))
                                                )
                                        )
                                )
                        )
                );
    }
    private void dropdownCalendar() {
        element
                .appendChild(Card.create("DROP DOWN")
                        .setCollapsible(true)
                        .appendChild(Row.create()
                                .span2(Button.create(Icons.calendar(), "Pick date")
                                        .apply(button -> {
                                            Popover.create(button)
                                                    .setPosition(DropDirection.BEST_MIDDLE_UP_DOWN)
                                                    .appendChild(Calendar.create()
                                                            .withHeader()
                                                    );
                                        })
                                )
                                .span2(Button.create(Icons.calendar(), "Pick date")
                                        .apply(button -> {
                                            Popover.create(button)
                                                    .setPosition(DropDirection.BEST_SIDE_UP_DOWN)
                                                    .appendChild(Calendar.create()
                                                            .withHeader()
                                                    );
                                        })
                                )
                                .span2(Button.create(Icons.calendar(), "Pick date")
                                        .apply(button -> {
                                            Popover.create(button)
                                                    .setPosition(DropDirection.BEST_MIDDLE_SIDE)
                                                    .appendChild(Calendar.create()
                                                            .withHeader()
                                                    );
                                        })
                                )
                                .span2(Button.create(Icons.calendar(), "Pick date")
                                        .apply(button -> {
                                            Popover.create(button)
                                                    .setPosition(DropDirection.MIDDLE_SCREEN)
                                                    .appendChild(Calendar.create()
                                                            .withHeader()
                                                    );
                                        })
                                )
                                .span2(Button.create(Icons.calendar(), "Pick date")
                                        .apply(button -> {
                                            Popover.create(button)
                                                    .setModal(true)
                                                    .setPosition(DropDirection.MIDDLE_SCREEN)
                                                    .appendChild(Calendar.create()
                                                            .withHeader()
                                                    );
                                        })
                                )
                                .span2(Button.create(Icons.calendar(), "Pick date")
                                        .apply(button -> {
                                            Popover.create(button)
                                                    .setPosition(DropDirection.BEST_FIT_SIDE)
                                                    .appendChild(Calendar.create()
                                                            .withHeader()
                                                    );
                                        })
                                )
                        )
                );
    }
    private void dateBox() {
        ;
        element
                .appendChild(Card.create("DATE BOX")
                        .setCollapsible(true)
                        .appendChild(Row.create()
                                .span4(DateBox.create("myDateBox")
                                        .setReadOnly(true)
                                        .setPattern("dd.MM.yyyy")
                                        .setParseStrict(true)
                                        .withPopover((parent, popover) -> popover.addCss(dui_accent_blue)))
                                .span4(DateBox.create("With pattern", new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("ar"))
                                        .setPattern("dd-MM-yyyy")
                                        .withPopover((parent, popover) -> popover.addCss(dui_accent_blue))
                                        .withCalendar((parent, calendar) -> calendar
                                                .withHeader())
                                )
                                .span4(DateBox.create("With parse strict", new org.gwtproject.i18n.shared.cldr.impl.BrowserDateTimeFormatInfo("es"))
                                        .setPattern("dd-MM-yyyy")
                                        .setParseStrict(true)
                                        .withPopover((parent, popover) -> popover.addCss(dui_accent_teal))
                                        .withCalendar((parent, calendar) -> calendar
                                                .withHeader())
                                )
                        )
                );
    }
}
