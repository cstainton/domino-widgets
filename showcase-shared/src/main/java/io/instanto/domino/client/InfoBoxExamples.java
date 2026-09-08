// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.counter.Counter;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.infoboxes.InfoBox;
import org.dominokit.domino.ui.typography.BlockHeader;

public final class InfoBoxExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();
  private Counter counter;

  public HTMLDivElement render() {
    basicInfoBoxes();
    hoverZoomEffect();
    rightAligned();
    return element.element();
  }

  private void basicInfoBoxes() {
    element.appendChild(
        BlockHeader.create(
                "BASIC INFO BOX AND COUNTERS",
                "Simple info box without effects, and counters to update the value.")
            .element());

    element.appendChild(
        Row.create()
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.cart(), "NEW ORDERS", "125")
                            .withIcon((infoBox, icon) -> icon.addCss(dui_red))))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.face_man(), "NEW MEMBERS", "257")
                            .withIcon((infoBox, icon) -> icon.addCss(dui_indigo))))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.cart(), "BOOKMARKS", "117")
                            .withIcon((infoBox, icon) -> icon.addCss(dui_purple))))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.star(), "LIKES", "1432")
                            .withIcon((infoBox, icon) -> icon.addCss(dui_deep_purple)))));

    InfoBox new_orders =
        InfoBox.create(Icons.cart(), "NEW ORDERS", "0")
            .setHoverEffect(InfoBox.HoverEffect.ZOOM)
            .withIcon((parent, icon) -> icon.addCss(dui_red));
    InfoBox new_members =
        InfoBox.create(Icons.face_man(), "NEW MEMBERS", "0")
            .setHoverEffect(InfoBox.HoverEffect.ZOOM)
            .withIcon((parent, icon) -> icon.addCss(dui_indigo));
    InfoBox bookmarks =
        InfoBox.create(Icons.cart(), "BOOKMARKS", "0")
            .setHoverEffect(InfoBox.HoverEffect.ZOOM)
            .withIcon((parent, icon) -> icon.addCss(dui_purple));
    InfoBox likes =
        InfoBox.create(Icons.star(), "LIKES", "0")
            .setHoverEffect(InfoBox.HoverEffect.ZOOM)
            .withIcon((parent, icon) -> icon.addCss(dui_deep_purple));

    element.appendChild(
        Row.create()
            .appendChild(Column.span3().appendChild(new_orders))
            .appendChild(Column.span3().appendChild(new_members))
            .appendChild(Column.span3().appendChild(bookmarks))
            .appendChild(Column.span3().appendChild(likes)));

    counter =
        Counter.countFrom(0)
            .countTo(125)
            .every(60)
            .step(5)
            .onCount(
                count -> {
                  new_orders.getInfo().setTextContent(Integer.toString(count));
                  new_members.getInfo().setTextContent(Integer.toString(count));
                  bookmarks.getInfo().setTextContent(Integer.toString(count));
                  likes.getInfo().setTextContent(Integer.toString(count));
                });
  }

  private void hoverZoomEffect() {
    element.appendChild(
        BlockHeader.create("HOVER EFFECTS", "Apply Zoom or Expand effects on hover").element());

    element.appendChild(
        Row.create()
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.email(), "MESSAGES", "15")
                            .addCss(dui_pink)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.devices(), "CPU USAGE", "92%")
                            .addCss(dui_blue)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.alarm(), "ALARM", "07:00 AM")
                            .addCss(dui_amber)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.crosshairs_gps(), "LOCATION", "Jordan")
                            .addCss(dui_deep_purple)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .element());

    element.appendChild(
        Row.create()
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.equalizer(), "BOUNCE RATE", "62%")
                            .withIcon((parent, iconElement) -> iconElement.addCss(dui_teal))
                            .addCss(dui_cyan, dui_rounded_lg)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.airplane_takeoff(), "FLIGHT", "02:59 PM")
                            .withIcon((parent, iconElement) -> iconElement.addCss(dui_green))
                            .addCss(dui_lime, dui_rounded_lg)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.battery_charging_90(), "BATTERY", "Charging")
                            .withIcon((parent, iconElement) -> iconElement.addCss(dui_light_green))
                            .addCss(dui_green, dui_rounded_lg)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.brightness_2(), "BRIGHTNESS RATE", "40%")
                            .withIcon((parent, iconElement) -> iconElement.addCss(dui_deep_orange))
                            .addCss(dui_amber, dui_rounded_lg)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND))));
  }

  private void rightAligned() {
    element.appendChild(
        BlockHeader.create(
                "ICON ALIGN AND COUNTERS",
                "Change icon position, and update info value with counters")
            .element());

    element.appendChild(
        Row.create()
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.email(), "MESSAGES", "15")
                            .withIcon((parent, iconElement) -> iconElement.addCss(dui_fg_pink))
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.devices(), "CPU USAGE", "92%")
                            .withIcon((parent, iconElement) -> iconElement.addCss(dui_fg_blue))
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.alarm(), "ALARM", "07:00 AM")
                            .withIcon((parent, iconElement) -> iconElement.addCss(dui_fg_amber))
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.crosshairs_gps(), "LOCATION", "Jordan")
                            .withIcon(
                                (parent, iconElement) -> iconElement.addCss(dui_fg_deep_purple))
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.ZOOM)))
            .element());

    element.appendChild(
        Row.create()
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.equalizer(), "BOUNCE RATE", "62%")
                            .addCss(dui_teal)
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.airplane_takeoff(), "FLIGHT", "02:59 PM")
                            .addCss(dui_green)
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.battery_charging_30(), "BATTERY", "Charging")
                            .addCss(dui_light_green)
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND)))
            .appendChild(
                Column.span3()
                    .appendChild(
                        InfoBox.create(Icons.brightness_2(), "BRIGHTNESS RATE", "40%")
                            .addCss(dui_lime)
                            .setFlipped(true)
                            .setHoverEffect(InfoBox.HoverEffect.EXPAND))));
  }

  public void restartCounters() {
    counter.startCounting();
  }
}
