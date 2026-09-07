// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see upstream/showcase-lock.json.
package io.instanto.domino.client;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.splitpanel.HSplitPanel;
import org.dominokit.domino.ui.splitpanel.SplitPanel;
import org.dominokit.domino.ui.splitpanel.VSplitPanel;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.style.ColorScheme;
import org.dominokit.domino.ui.style.CssClass;
import org.dominokit.domino.ui.typography.BlockHeader;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.elements.DivElement;
import static org.dominokit.domino.ui.utils.Domino.*;
public final class SplitPanelExamples implements org.dominokit.domino.ui.style.DominoCss {
 private final HTMLDivElement element=div().element();
    private final CssClass demo_split_div = () -> "demo-split-div";

 public HTMLDivElement render(){
horizontalSplitPanel();
verticalSplitPanel();
splitPanelMinMax();
multiSplit();
combined();
return element;}
    private void horizontalSplitPanel() {

        element.appendChild(Card.create("HORIZONTAL SPLIT PANEL")
                .appendChild(HSplitPanel.create()
                        .appendChild(SplitPanel.create()
                                .setWidth("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .appendChild(SplitPanel.create()
                                .setWidth("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_d_2)))
                        .addCss(dui_h_96, dui_w_full)
                )
                .element());
    }
    private void verticalSplitPanel() {
        element.appendChild(Card.create("VERTICAL SPLIT PANEL")
                .appendChild(VSplitPanel.create()
                        .appendChild(SplitPanel.create()
                                .setHeight("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .appendChild(SplitPanel.create()
                                .setHeight("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_d_2)))
                        .addCss(dui_h_96, dui_w_full)
                )
                .element());
    }
    private void splitPanelMinMax() {
        element.appendChild(Card.create("SPLIT PANEL MIN & MAX")
                .appendChild(HSplitPanel.create()
                        .appendChild(SplitPanel.create()
                                .setWidth("50%")
                                .setMinPercent(20)
                                .setMaxPercent(70)
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .appendChild(SplitPanel.create()
                                .setWidth("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_d_2)))
                        .addCss(dui_h_96, dui_w_full)
                )
                .appendChild(hr())
                .appendChild(VSplitPanel.create()
                        .appendChild(SplitPanel.create()
                                .setHeight("50%")
                                .setMinPercent(20)
                                .setMaxPercent(70)
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .appendChild(SplitPanel.create()
                                .setHeight("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_d_2)))
                        .addCss(dui_h_96, dui_w_full)
                )
                .element());


    }
    private void multiSplit() {
        element.appendChild(Card.create("MULTI SPLIT")
                .appendChild(HSplitPanel.create()
                        .appendChild(SplitPanel.create()
                                .setWidth("20%")
                                .appendChild(div()
                                        .addCss(demo_split_div, dui_bg_accent_l_2)))
                        .appendChild(SplitPanel.create()
                                .setWidth("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_d_2)))
                        .appendChild(SplitPanel.create()
                                .setWidth("30%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .addCss(dui_h_96, dui_w_full)
                )
                .appendChild(hr())
                .appendChild(VSplitPanel.create()
                        .appendChild(SplitPanel.create()
                                .setHeight("20%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .appendChild(SplitPanel.create()
                                .setHeight("50%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_d_2)))
                        .appendChild(SplitPanel.create()
                                .setHeight("30%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .addCss(dui_h_96, dui_w_full)
                )
                .element());
    }
    private void combined() {
        element.appendChild(Card.create("COMBINED SPLIT PANELS")
                .appendChild(HSplitPanel.create()
                        .appendChild(SplitPanel.create()
                                .setWidth("20%")
                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                        .appendChild(SplitPanel.create()
                                .setWidth("50%")
                                .appendChild(VSplitPanel.create()
                                        .appendChild(SplitPanel.create()
                                                .setHeight("20%")
                                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                                        .appendChild(SplitPanel.create()
                                                .setHeight("50%")
                                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_d_2)))
                                        .appendChild(SplitPanel.create()
                                                .setHeight("30%")
                                                .appendChild(div().addCss(demo_split_div, dui_bg_accent_l_2)))
                                        .addCss(dui_h_full, dui_w_full)
                                ))
                        .appendChild(SplitPanel.create()
                                .setWidth("30%")
                                .appendChild(div().addCss(demo_split_div,dui_bg_accent_l_2)))
                        .addCss(dui_h_96, dui_w_full)
                )
                .element());


    }
}
