// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.preloaders.Preloader;

public final class PreloadersExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    sizesSample();
    colorsSample();
    return element.element();
  }

  private void sizesSample() {
    element.appendChild(
        Card.create("PRELOADERS - DIFFERENT SIZES")
            .appendChild(
                div()
                    .addCss(dui_flex, dui_gap_4, dui_items_center)
                    .appendChild(Preloader.create().addCss(dui_xlarge))
                    .appendChild(Preloader.create().addCss(dui_large))
                    .appendChild(Preloader.create().addCss(dui_medium))
                    .appendChild(Preloader.create().addCss(dui_small))
                    .appendChild(Preloader.create().addCss(dui_xsmall))));
  }

  private void colorsSample() {

    element.appendChild(
        Card.create("WITH MATERIAL DESIGN COLORS", "You can use the material design colors.")
            .appendChild(
                div()
                    .addCss(dui_flex, dui_gap_4)
                    .appendChild(Preloader.create().addCss(dui_fg_red))
                    .appendChild(Preloader.create().addCss(dui_fg_black))
                    .appendChild(Preloader.create().addCss(dui_fg_blue_grey))
                    .appendChild(Preloader.create().addCss(dui_fg_blue))
                    .appendChild(Preloader.create().addCss(dui_fg_grey))
                    .appendChild(Preloader.create().addCss(dui_fg_brown))
                    .appendChild(Preloader.create().addCss(dui_fg_deep_orange))
                    .appendChild(Preloader.create().addCss(dui_fg_orange))
                    .appendChild(Preloader.create().addCss(dui_fg_lime))
                    .appendChild(Preloader.create().addCss(dui_fg_light_green))
                    .appendChild(Preloader.create().addCss(dui_fg_teal))
                    .appendChild(Preloader.create().addCss(dui_fg_indigo))));
  }
}
