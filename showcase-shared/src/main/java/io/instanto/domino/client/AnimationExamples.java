// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see upstream/showcase-lock.json.
package io.instanto.domino.client;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.animations.Animation;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.style.DisplayCss;
import org.dominokit.domino.ui.style.Style;
import org.dominokit.domino.ui.typography.BlockHeader;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.elements.DivElement;
import static org.dominokit.domino.ui.utils.Domino.*;
public final class AnimationExamples implements org.dominokit.domino.ui.style.DominoCss {
 private final DivElement element=div();

 public HTMLDivElement render(){
for(Transition transition:new Transition[]{Transition.BOUNCE,Transition.FLASH,Transition.PULSE,Transition.SHAKE})element.appendChild(createCard(transition));return element.element();}
    private Card createCard(Transition transition) {

        Card animationCard = Card.create().addCss(dui_blue_grey)
                .appendChild(img("images/animation-bg.jpg").addCss(dui_image_responsive));

        Card card = Card.create(transition.getName(), transition.getStyle() + " animation.")
                .withBody((parent, body) -> body.addCss(dui_light_blue))
                .withHeader((parent, header) -> header.addCss(dui_blue));

        Button animate = Button.create(transition.getName()).addCss(dui_large);
        animate.getClickableElement().addEventListener("click", e ->
                Animation.create(animationCard)
                        .beforeStart(element -> {/*do something here*/})
                        .transition(transition)
                        .duration(1000)
                        .animate());

        Button infiniteAnimate = Button.create("INFINITE")
                .addCss(dui_large);
        infiniteAnimate.getClickableElement().addEventListener("click", e -> {

            Animation animation = Animation.create(animationCard)
                    .transition(transition)
                    .infinite()
                    .duration(1000);
            if (Style.of(animationCard).containsCss("animated")) {
                animation.stop();
                Style.of(animate).setDisplay("inline-block");
                infiniteAnimate.setText("INFINITE");
            } else {
                animation.animate();
                Style.of(animate).setDisplay("none");
                infiniteAnimate.setText("STOP");
            }
        });

        card.appendChild(animationCard)
                .appendChild(div().addCss(dui_flex, dui_justify_center, dui_gap_1)
                        .appendChild(animate)
                        .appendChild(infiniteAnimate)
                );

        return card;
    }
}
