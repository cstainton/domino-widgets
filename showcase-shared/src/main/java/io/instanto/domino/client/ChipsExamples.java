// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see upstream/showcase-lock.json.
package io.instanto.domino.client;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.chips.ChipsGroup;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.notifications.Notification;
import org.dominokit.domino.ui.style.CompositeCssClass;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.ElementHandler;
import org.dominokit.domino.ui.utils.PrefixAddOn;
import org.dominokit.domino.ui.utils.meta.ValueMeta;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.elements.DivElement;
import static org.dominokit.domino.ui.utils.Domino.*;
public final class ChipsExamples implements org.dominokit.domino.ui.style.DominoCss {
 private final DivElement element=div();
    private Card simpleCard;
    private Card removableCard;
    private Card iconChipsCard;
    private Card imagesChipsCard;
    private Card lettersChipsCard;
    private Card selectableChipsCard;
 public HTMLDivElement render(){
        simpleCard = Card.create("SIMPLE CHIPS");
        removableCard = Card.create("REMOVABLE CHIPS");
        iconChipsCard = Card.create("CHIPS WITH ICONS");
        imagesChipsCard = Card.create("CHIPS WITH IMAGES");
        lettersChipsCard = Card.create("CHIPS WITH LETTERS");
        selectableChipsCard = Card.create("SELECTABLE CHIPS");initSimpleExample();
initRemovableExample();
initChipsWithIconsExample();
initChipsWithImagesExample();
initChipsWithLettersExample();
initSelectableChipsExample();
element.appendChild(simpleCard);
element.appendChild(removableCard);
element.appendChild(iconChipsCard);
element.appendChild(imagesChipsCard);
element.appendChild(lettersChipsCard);
element.appendChild(selectableChipsCard);return element.element();}
    private void initSimpleExample() {
        simpleCard.appendChild(Row.create()
                .appendChild(Column.span12()
                        .appendChild(Chip.create("Sounds good, let's do that!"))
                        .appendChild(Chip.create("Yay! I'll be there").addCss(dui_accent))
                        .appendChild(Chip.create("Hey, how are you?").addCss(dui_orange))
                        .appendChild(Chip.create("You look handsome today <3").addCss(dui_purple))
                        .appendChild(Chip.create("I like the weather today!").addCss(dui_success))
                )
        );
    }
    private void initRemovableExample() {
        removableCard.appendChild(Row.create()
                .appendChild(Column.span12()
                        .appendChild(Chip.create("Restaurants")
                                .addCss(dui_grey)
                                .setRemovable(true)
                        )
                        .appendChild(Chip.create("Coffee shops")
                                .addCss(dui_info)
                                .setRemovable(true)
                        )
                        .appendChild(Chip.create("Libraries")
                                .addCss(dui_warning)
                                .setRemovable(true)
                        )
                        .appendChild(Chip.create("Entertainment")
                                .addCss(dui_brown)
                                .setRemovable(true)
                        )
                        .appendChild(Chip.create("Universities")
                                .addCss(dui_teal)
                                .setRemovable(true)
                        )
                )
        );
    }
    private void initChipsWithIconsExample() {
        iconChipsCard.appendChild(Row.create()
                .appendChild(Column.span12()
                        .appendChild(Chip.create("Add to calendar")
                                .addClickListener(evt -> {
                                    Notification.create("Added to your calendar").addCss(dui_success).show();
                                })
                                .appendChild(PrefixAddOn.of(Icons.calendar_range()))
                        )
                        .appendChild(Chip.create("Bookmark")
                                .addClickListener(evt -> {
                                    Notification.create("Bookmark added").addCss(dui_success).show();
                                })
                                .appendChild(PrefixAddOn.of(Icons.bookmark()))
                        )
                        .appendChild(Chip.create("Set alarm")
                                .addClickListener(evt -> {
                                    Notification.create("Alarm has been set").addCss(dui_success).show();
                                })
                                .appendChild(PrefixAddOn.of(Icons.alarm()))
                        )
                        .appendChild(Chip.create("Get directions")
                                .addClickListener(evt -> {
                                    Notification.create("Directions has been sent to your email").addCss(dui_success).show();
                                })
                                .appendChild(PrefixAddOn.of(Icons.directions()))
                        ))
        );
    }
    private void initChipsWithImagesExample() {
        imagesChipsCard.appendChild(Row.create()
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Schroeder Coleman")
                                .addCss(dui_transparent, dui_border, dui_border_solid, dui_border_indigo)
                                .setImage(img("showcase-image.jpg"))))
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Renee Mcintyre")
                                .addCss(dui_grey)
                                .setImage(img("showcase-image.jpg"))))
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Casey Garza")
                                .addCss(dui_blue)
                                .setImage(img("showcase-image.jpg"))))
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Ferguson Hudson")
                                .addCss(dui_black)
                                .setImage(img("showcase-image.jpg")))
                )
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Serrano Green")
                                .addCss(dui_cyan)
                                .setImage(img("showcase-image.jpg")))
                )
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Camacho Solis")
                                .addCss(dui_blue_grey)
                                .setImage(img("showcase-image.jpg")))
                )
        );
    }
    private void initChipsWithLettersExample() {
        CompositeCssClass border_solid = CompositeCssClass.of(dui_border, dui_border_solid);
        lettersChipsCard.appendChild(Row.create()
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Schroeder Coleman")
                                .addCss(dui_transparent, border_solid, dui_border_indigo)
                                .withAddon((chip, addon) -> addon.addCss(dui_indigo))
                                .setLetters("SC")
                        )
                )
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Renee Mcintyre")
                                .addCss(dui_transparent, border_solid, dui_border_grey)
                                .withAddon((chip, addon) -> addon.addCss(dui_grey))
                                .setLetters("RM")
                        )
                )
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Casey Garza")
                                .addCss(dui_transparent, border_solid, dui_border_blue)
                                .withAddon((chip, addon) -> addon.addCss(dui_blue))
                                .setLetters("CG")
                        )
                )
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Ferguson Hudson")
                                .addCss(dui_transparent, border_solid, dui_border_black)
                                .withAddon((chip, addon) -> addon.addCss(dui_black))
                                .setLetters("FH")
                        )
                )
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Serrano Green")
                                .addCss(dui_transparent, border_solid, dui_border_cyan)
                                .withAddon((chip, addon) -> addon.addCss(dui_cyan))
                                .setLetters("SG")
                        )
                )
                .appendChild(Column.span2()
                        .appendChild(Chip.create("Camacho Solis")
                                .addCss(dui_transparent, border_solid, dui_border_blue_grey)
                                .withAddon((chip, addon) -> addon.addCss(dui_blue_grey))
                                .setLetters("CS")
                        )
                )
        );
    }
    private void initSelectableChipsExample() {
        ElementHandler<Chip> chipSelectionHandler = self -> {
            self
                    .addSelectionListener((source, selection) -> {
                        self.withAddon((parent, addon) -> {
                            addon.clearElement().appendChild(PrefixAddOn.of(Icons.check()));
                        });
                    })
                    .addDeselectionListener((source, selection) -> {
                        self.clearAddOn();
                    });
        };
        Chip tops = Chip.create("Tops")
                .setSelectable(true)
                .addCss(dui_grey)
                .apply(chipSelectionHandler);


        Chip bottoms = Chip.create("Bottoms")
                .setSelectable(true)
                .addCss(dui_grey)
                .apply(chipSelectionHandler);

        Chip shoes = Chip.create("Shoes").setSelectable(true)
                .setSelectable(true)
                .addCss(dui_grey)
                .apply(chipSelectionHandler);

        Chip accessories = Chip.create("Accessories")
                .setSelectable(true)
                .addCss(dui_grey)
                .apply(chipSelectionHandler);

        selectableChipsCard.appendChild(Row.create()
                .appendChild(Column.span12()
                        .appendChild(tops)
                        .appendChild(bottoms)
                        .appendChild(shoes)
                        .appendChild(accessories)
                ));

        selectableChipsCard.appendChild(br());
        selectableChipsCard.appendChild(BlockHeader.create("Choice chips"));

        ChipsGroup chipsGroup = ChipsGroup.create()
                .appendChild(Chip.create("Extra small").applyMeta(ValueMeta.of("EXTRA SMALL")))
                .appendChild(Chip.create("Small").applyMeta(ValueMeta.of("SMALL")))
                .appendChild(Chip.create("Medium").applyMeta(ValueMeta.of("MEDIUM")))
                .appendChild(Chip.create("Large").applyMeta(ValueMeta.of("LARGE")))
                .appendChild(Chip.create("Extra large").applyMeta(ValueMeta.of("EXTREA LARGE")))
                .addCss(dui_teal, dui_ignore_bg);

        selectableChipsCard.appendChild(Row.create()
                .appendChild(Column.span12()
                        .appendChild(chipsGroup
                                .addSelectionListener((source, selection) -> {
                                    ValueMeta.<String>get(chipsGroup.getSelectedChips().get(0))
                                                    .ifPresent(value -> {
                                                        Notification
                                                                .create("Chip [ " + value + " ] is selected")
                                                                .addCss(dui_info)
                                                                .show();
                                                    });
                                })
                                .selectAt(0)))
        );
    }
}
