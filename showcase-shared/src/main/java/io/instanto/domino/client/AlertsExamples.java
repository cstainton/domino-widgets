// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.alerts.Alert;
import org.dominokit.domino.ui.cards.Card;

public final class AlertsExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final HTMLDivElement element = div().element();

  public HTMLDivElement render() {
    basicAlerts();
    customBackground();
    dismissibleAlerts();
    linksInAlerts();
    return element;
  }

  private void basicAlerts() {
    element.appendChild(
        Card.create("BASIC ALERTS", "Use one of the pre-customized alert types.")
            .appendChild(
                Alert.success()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Well done! "))
                    .appendChild("You successfully read this important alert message."))
            .appendChild(
                Alert.info()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Heads up! "))
                    .appendChild("This alert needs your attention, but it's not super important."))
            .appendChild(
                Alert.warning()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Warning! "))
                    .appendChild("Better check yourself, you're not looking too good."))
            .appendChild(
                Alert.error()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Oh snap! "))
                    .appendChild("Change a few things up and try submitting again."))
            .element());
  }

  private void customBackground() {
    element.appendChild(
        Card.create("MATERIAL DESIGN ALERTS", "ou can use material design colors backgrounds")
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_pink, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id"))
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_orange, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id"))
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_teal, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id"))
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_green, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id"))
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_red, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id"))
            .element());
  }

  private void dismissibleAlerts() {

    element.appendChild(
        Card.create(
                "DISMISSIBLE ALERTS", "Add a close button to any alert by making it dismissible")
            .appendChild(
                Alert.warning()
                    .addCss(dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id")
                    .dismissible())
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_pink, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id")
                    .dismissible())
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_teal, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id")
                    .dismissible())
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_green, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id")
                    .dismissible())
            .element());
  }

  private void linksInAlerts() {
    element.appendChild(
        Card.create(
                "LINKS IN ALERTS",
                "Use the appendLink utility class to quickly provide matching colored links within"
                    + " any alert.")
            .appendChild(
                Alert.success()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Well done! "))
                    .appendChild("You successfully read ")
                    .appendChild(a().appendChild("important alert message.")))
            .appendChild(
                Alert.info()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Heads up! "))
                    .appendChild("This ")
                    .appendChild(a().appendChild("alert needs your attention, "))
                    .appendChild("but it's not super important."))
            .appendChild(
                Alert.warning()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Warning! "))
                    .appendChild("Better check yourself, ")
                    .appendChild(a().appendChild("you're not looking too good.")))
            .appendChild(
                Alert.error()
                    .addCss(dui_m_b_4)
                    .appendChild(strong().textContent("Oh snap!    "))
                    .appendChild(a().appendChild("Change a few things up"))
                    .appendChild(" and try submitting again."))
            .appendChild(
                Alert.create()
                    .addCss(dui_bg_pink, dui_fg_white, dui_m_b_4)
                    .appendChild(
                        "Lorem ipsum dolor sit amet, id fugit tollit pro, illud nostrud aliquando"
                            + " ad est, quo esse dolorum id ")
                    .appendChild(a().appendChild("alert link.")))
            .element());
  }
}
