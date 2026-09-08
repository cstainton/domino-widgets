// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.tabs.FillItem;
import org.dominokit.domino.ui.tabs.HeaderDirection;
import org.dominokit.domino.ui.tabs.Tab;
import org.dominokit.domino.ui.tabs.TabsAlign;
import org.dominokit.domino.ui.tabs.TabsDirection;
import org.dominokit.domino.ui.tabs.TabsHeaderAlign;
import org.dominokit.domino.ui.tabs.TabsPanel;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PostfixAddOn;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public final class TabsExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();
  private static final String SAMPLE_TEXT =
      "Lorem ipsum dolor sit amet, ut duo atqui exerci dicunt, ius impedit mediocritatem an. Pri ut"
          + " tation electram moderatius. Per te suavitate democritum. Duis nemore probatus ne quo,"
          + " ad liber essent aliquid pro. Et eos nusquam accumsan, vide mentitum fabellas ne est,"
          + " eu munere gubergren sadipscing mel.";

  public HTMLDivElement render() {
    basicSample();
    iconsOnly();
    withIconsAndTextSample();
    tabsAlignment();
    closableTabsSample();
    materialDesignColorsSample();
    backgroundSample();
    initDifferentContentSample();
    withAnimation();
    return element.element();
  }

  private void basicSample() {
    element.appendChild(
        Card.create(
                "EXAMPLE TAB",
                "Add quick, dynamic tab functionality to transition through panes of local content")
            .appendChild(
                TabsPanel.create()
                    .appendChild(
                        Tab.create("HOME")
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create("PROFILE")
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create("MESSAGES")
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate())
                    .appendChild(
                        Tab.create("SETTINGS")
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT)))));
  }

  private void iconsOnly() {
    element.appendChild(
        Card.create("TABS WITH ONLY ICON TITLE")
            .appendChild(
                TabsPanel.create()
                    .appendChild(
                        Tab.create(Icons.home())
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.face_man())
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.email())
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate())
                    .appendChild(
                        Tab.create(Icons.cog())
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT)))));
  }

  private void withIconsAndTextSample() {
    element.appendChild(
        Card.create("TABS WITH ICON TITLE")
            .appendChild(
                TabsPanel.create()
                    .appendChild(PrefixAddOn.of(Icons.menu().clickable()))
                    .appendChild(
                        Tab.create(Icons.home(), " HOME")
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.face_man(), " PROFILE")
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.email(), " MESSAGES")
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate())
                    .appendChild(
                        Tab.create(Icons.cog(), " SETTINGS")
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(FillItem.create())
                    .appendChild(PostfixAddOn.of(Icons.dots_vertical().clickable()))));
  }

  private void tabsAlignment() {
    element.appendChild(
        Card.create("TABS ALIGNMENT")
            .appendChild(
                TabsPanel.create()
                    .setTabsAlign(TabsAlign.START)
                    .appendChild(
                        Tab.create(Icons.home(), " HOME")
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.face_man(), " PROFILE")
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.email(), " MESSAGES")
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate())
                    .appendChild(
                        Tab.create(Icons.cog(), " SETTINGS")
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT))))
            .appendChild(
                TabsPanel.create()
                    .setTabsAlign(TabsAlign.CENTER)
                    .appendChild(
                        Tab.create(Icons.home(), " HOME")
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.face_man(), " PROFILE")
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.email(), " MESSAGES")
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate())
                    .appendChild(
                        Tab.create(Icons.cog(), " SETTINGS")
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT))))
            .appendChild(
                TabsPanel.create()
                    .setTabsAlign(TabsAlign.END)
                    .appendChild(
                        Tab.create(Icons.home(), " HOME")
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.face_man(), " PROFILE")
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create(Icons.email(), " MESSAGES")
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate())
                    .appendChild(
                        Tab.create(Icons.cog(), " SETTINGS")
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT)))));
  }

  private void closableTabsSample() {
    element.appendChild(
        Card.create("CLOSABLE TABS")
            .appendChild(
                TabsPanel.create()
                    .appendChild(
                        Tab.create(Icons.home(), " HOME")
                            .withHeader(
                                (parent, header) ->
                                    header.appendChild(
                                        PostfixAddOn.of(
                                            Badge.create("15+").addCss(dui_rounded_full))))
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .setClosable(true))
                    .appendChild(
                        Tab.create(Icons.face_man(), " PROFILE")
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .setClosable(true))
                    .appendChild(
                        Tab.create(Icons.email(), " MESSAGES")
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate()
                            .setClosable(true))
                    .appendChild(
                        Tab.create(Icons.cog(), " SETTINGS")
                            .withHeader(
                                (parent, header) ->
                                    header.appendChild(
                                        PostfixAddOn.of(
                                            Badge.create("new").addCss(dui_rounded_full))))
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .setClosable(true))));
  }

  private void materialDesignColorsSample() {
    element.appendChild(
        Card.create("TABS WITH MATERIAL DESIGN COLORS", "You can use Material Design Colors")
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6()
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))
                                    .addCss(dui_accent_pink))
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))
                                    .addCss(dui_accent_teal))
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))
                                    .addCss(dui_accent_purple)))
                    .appendChild(
                        Column.span6()
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))
                                    .addCss(dui_accent_red))
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))
                                    .addCss(dui_accent_orange))
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))
                                    .addCss(dui_accent_blue_grey)))));
  }

  private void backgroundSample() {
    element.appendChild(
        Card.create(
                "TABS WITH MATERIAL DESIGN BACKGROUNDS", "You can use Material Design backgrounds")
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6()
                            .appendChild(
                                TabsPanel.create()
                                    .addCss(dui_indigo)
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS")))
                            .appendChild(
                                TabsPanel.create()
                                    .addCss(dui_red)
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS")))
                            .appendChild(
                                TabsPanel.create()
                                    .addCss(dui_pink)
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))))
                    .appendChild(
                        Column.span6()
                            .appendChild(
                                TabsPanel.create()
                                    .addCss(dui_green)
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS")))
                            .appendChild(
                                TabsPanel.create()
                                    .addCss(dui_purple)
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS")))
                            .appendChild(
                                TabsPanel.create()
                                    .addCss(dui_lime)
                                    .appendChild(Tab.create("HOME"))
                                    .appendChild(Tab.create("PROFILE"))
                                    .appendChild(Tab.create("MESSAGES").activate())
                                    .appendChild(Tab.create("SETTINGS"))))));
  }

  private void initDifferentContentSample() {
    DivElement contentContainer =
        div()
            .appendChild(BlockHeader.create("TAB CONTENT"))
            .addCss(dui_m_12, dui_p_6)
            .addCss(dui_elevation_1);
    element.appendChild(
        Card.create("TABS WITH CONTENT CONTAINER", "Tabs can have different content container")
            .appendChild(
                TabsPanel.create()
                    .setContentContainer(contentContainer)
                    .appendChild(
                        Tab.create("HOME")
                            .appendChild(b().textContent("Home Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create("PROFILE")
                            .appendChild(b().textContent("Profile Content"))
                            .appendChild(p(SAMPLE_TEXT)))
                    .appendChild(
                        Tab.create("MESSAGES")
                            .appendChild(b().textContent("Messages Content"))
                            .appendChild(p(SAMPLE_TEXT))
                            .activate())
                    .appendChild(
                        Tab.create("SETTINGS")
                            .appendChild(b().textContent("Settings Content"))
                            .appendChild(p(SAMPLE_TEXT))))
            .appendChild(contentContainer));
  }

  private void withAnimation() {
    element.appendChild(
        Card.create("TABS WITH CUSTOM ANIMATIONS", "Animate the tabs content when they show up.")
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6()
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(
                                        Tab.create(Icons.home(), " HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.face_man(), " PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.email(), " MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create(Icons.cog(), " SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .setTransition(Transition.ROTATE_IN_UP_LEFT)))
                    .appendChild(
                        Column.span6()
                            .appendChild(
                                TabsPanel.create()
                                    .appendChild(
                                        Tab.create(Icons.home(), " HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.face_man(), " PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.email(), " MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create(Icons.cog(), " SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .setTransition(Transition.FADE_IN_RIGHT))))
            .element());
  }

  private void Tabs() {
    element.appendChild(
        Card.create("VERTICAL TABS")
            .appendChild(BlockHeader.create("TABS ALIGNMENT"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setTabsAlign(TabsAlign.START)
                                    .appendChild(
                                        Tab.create("HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create("SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setTabsAlign(TabsAlign.CENTER)
                                    .appendChild(
                                        Tab.create("HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create("SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setTabsAlign(TabsAlign.END)
                                    .appendChild(
                                        Tab.create("HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create("SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT))))))
            .appendChild(hr())
            .appendChild(BlockHeader.create("TABS HEADER ALIGNMENT"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setTabHeaderAlign(TabsHeaderAlign.LEFT)
                                    .appendChild(
                                        Tab.create("HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create("SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setTabHeaderAlign(TabsHeaderAlign.CENTER)
                                    .appendChild(
                                        Tab.create("HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create("SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setTabHeaderAlign(TabsHeaderAlign.RIGHT)
                                    .appendChild(
                                        Tab.create("HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create("MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create("SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT))))))
            .appendChild(hr())
            .appendChild(BlockHeader.create("HEADER DIRECTION"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .appendChild(
                                        Tab.create(Icons.home(), "HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.face_man(), "PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.email(), "MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create(Icons.cog(), "SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setHeaderDirection(HeaderDirection.VERTICAL)
                                    .appendChild(
                                        Tab.create(Icons.home(), "HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.face_man(), "PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.email(), "MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create(Icons.cog(), "SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))))
                    .appendChild(
                        Column.span4()
                            .appendChild(
                                TabsPanel.create()
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setHeaderDirection(HeaderDirection.VERTICAL_REVERSED)
                                    .appendChild(
                                        Tab.create(Icons.home(), "HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.face_man(), "PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.email(), "MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(
                                        Tab.create(Icons.cog(), "SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT))))))
            .appendChild(hr())
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span12()
                            .appendChild(
                                TabsPanel.create()
                                    .addCss(dui_h_96)
                                    .setTabsDirection(TabsDirection.VERTICAL)
                                    .setTabsAlign(TabsAlign.START)
                                    .setTransition(Transition.FADE_IN)
                                    .appendChild(
                                        Tab.create(Icons.home(), "HOME")
                                            .appendChild(b().textContent("Home Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.face_man(), "PROFILE")
                                            .appendChild(b().textContent("Profile Content"))
                                            .appendChild(p(SAMPLE_TEXT)))
                                    .appendChild(
                                        Tab.create(Icons.email(), "MESSAGES")
                                            .appendChild(b().textContent("Messages Content"))
                                            .appendChild(p(SAMPLE_TEXT))
                                            .activate())
                                    .appendChild(FillItem.create())
                                    .appendChild(
                                        Tab.create(Icons.cog(), "SETTINGS")
                                            .appendChild(b().textContent("Settings Content"))
                                            .appendChild(p(SAMPLE_TEXT)))))));
  }
}
