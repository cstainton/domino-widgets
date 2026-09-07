// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see upstream/showcase-lock.json.
package io.instanto.domino.client;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.dialogs.AbstractDialog;
import org.dominokit.domino.ui.dialogs.AlertMessageDialog;
import org.dominokit.domino.ui.dialogs.MessageDialog;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.layout.NavBar;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PostfixAddOn;
import org.dominokit.domino.ui.utils.PrefixAddOn;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.elements.DivElement;
import static org.dominokit.domino.ui.utils.Domino.*;
public final class DialogsExamples implements org.dominokit.domino.ui.style.DominoCss {
 private final DivElement element=div();

 public HTMLDivElement render(){
sample();
return element.element();}
    private void sample() {
        MessageDialog basicMessage = MessageDialog.create()
                .setMessage("You have just opened a message dialog.")
                .onConfirm(AbstractDialog::close);

        MessageDialog basicWithHeaderMessage = MessageDialog.create()
                .setTitle("Message title")
                .setMessage("You have just opened a message dialog.")
                .onConfirm(AbstractDialog::close);

        AlertMessageDialog successMessage = AlertMessageDialog.create()
                .setTitle("Operation completed")
                .setIconAnimationDuration(500)
                .setIconStartTransition(Transition.ROTATE_IN)
                .setIconEndTransition(Transition.PULSE)
                .setAlertIcon(Icons.check_circle().addCss(dui_fg_green, dui_font_size_32))
                .setMessage("You have just opened a message dialog.")
                .onConfirm(AbstractDialog::close);

        AlertMessageDialog errorMessage = AlertMessageDialog.create()
                .setTitle("Operation failed!")
                .setIconAnimationDuration(500)
                .setIconStartTransition(Transition.SHAKE)
                .setIconEndTransition(Transition.TADA)
                .setAlertIcon(Icons.alert_circle().addCss(dui_fg_error, dui_font_size_32))
                .setMessage("You have just opened a message dialog.")
                .onConfirm(AbstractDialog::close);

        AlertMessageDialog warningMessage = AlertMessageDialog.create()
                .setTitle("Attention required!")
                .setIconAnimationDuration(500)
                .setIconStartTransition(Transition.PULSE)
                .setIconEndTransition(Transition.FLASH)
                .setAlertIcon(Icons.alert_circle().addCss(dui_fg_warning, dui_font_size_32))
                .setMessage("You have just opened a message dialog.")
                .onConfirm(AbstractDialog::close);

        AlertMessageDialog infoMessage = AlertMessageDialog.create()
                .setTitle("Got an idea?")
                .setIconAnimationDuration(500)
                .setIconStartTransition(Transition.FLASH)
                .setIconEndTransition(Transition.PULSE)
                .setAlertIcon(Icons.lightbulb_on_outline().addCss(dui_fg_info, dui_font_size_32))
                .setMessage("You have just opened a message dialog.")
                .onConfirm(AbstractDialog::close);

        AlertMessageDialog customColorsMessage = AlertMessageDialog.create()
                .addCss(dui_teal)
                .setTitle("Got an idea?")
                .setIconAnimationDuration(500)
                .setIconStartTransition(Transition.FLASH)
                .setIconEndTransition(Transition.PULSE)
                .setAlertIcon(Icons.lightbulb_on_outline().addCss(dui_font_size_32))
                .setMessage("You have just opened a message dialog.")
                .withConfirmButton((parent, button) -> button.addCss(dui_bg_teal_d_2))
                .onConfirm(AbstractDialog::close);

        AlertMessageDialog customContentMessage = AlertMessageDialog.create()
                .withHeader((dialog, header) -> {
                    header
                            .addCss(dui_orange)
                            .appendChild(NavBar.create("Custom header")
                                    .addCss(dui_h_8)
                                    .appendChild(PostfixAddOn.of(Icons.close_circle()
                                                    .clickable()
                                                    .addClickListener(evt -> dialog.close())
                                            )
                                    )
                                    .appendChild(PrefixAddOn.of(Icons.information()))
                            );
                })
                .withContentFooter((parent, footer) -> footer.addCss(dui_bg_orange_l_1, dui_fg_white))
                .withContentBody((parent, body) -> {
                    body
                            .addCss(dui_amber)
                            .appendChild(img("showcase-image.jpg")
                                    .addCss(dui_image_responsive, dui_m_auto, dui_w_32, dui_h_32)
                            );
                })
                .withConfirmButton((parent, button) -> button.addCss(dui_bg_orange_d_2))
                .onConfirm(AbstractDialog::close);

        element
                .appendChild(Card.create("EXAMPLES")
                        .appendChild(Row.create()
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Simple dialog"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> basicMessage.open())
                                        )
                                )
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Simple dialog with header"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> basicWithHeaderMessage.open())
                                        )
                                )
                        )
                        .appendChild(Row.create()
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Success alert"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> successMessage.open())
                                        )
                                )
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Error message"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> errorMessage.open())
                                        )
                                )
                        )
                        .appendChild(Row.create()
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Warning alert"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> warningMessage.open())
                                        )
                                )
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Info alert"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> infoMessage.open())
                                        )
                                )
                        )
                        .appendChild(Row.create()
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Custom colors"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> customColorsMessage.open())
                                        )
                                )
                                .appendChild(Column.span6()
                                        .appendChild(p().textContent("Custom content"))
                                        .appendChild(Button.create(Icons.cursor_default_click(), "CLICK ME")
                                                .addClickListener(evt -> customContentMessage.open())
                                        )
                                )
                        )
                );
    }
}
