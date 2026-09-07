// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see upstream/showcase-lock.json.
package io.instanto.domino.client;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.media.MediaObject;
import org.dominokit.domino.ui.typography.BlockHeader;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.elements.DivElement;
import static org.dominokit.domino.ui.utils.Domino.*;
public final class MediaExamples implements org.dominokit.domino.ui.style.DominoCss {
 private final DivElement element=div();
    private static final String SAMPLE_TEXT = "Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.";
 public HTMLDivElement render(){
defaultMedia();
mediaAlignment();
return element.element();}
    private void defaultMedia() {
        element.appendChild(Card.create("DEFAULT MEDIA", "The default media displays a media object (images, video, audio) to the left or right of a content block.")
                .appendChild(MediaObject.create()
                        .setHeader("Media heading")
                        .setLeftMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16)))
                        .appendChild(text(SAMPLE_TEXT)))
                .appendChild(MediaObject.create()
                        .setHeader("Media heading")
                        .setLeftMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16)))
                        .appendChild(text(SAMPLE_TEXT))
                        .appendChild(MediaObject.create()
                                .setHeader("Media heading")
                                .setLeftMedia(a()
                                        .appendChild(img("showcase-image.jpg")
                                                .addCss(dui_w_16, dui_h_16)))
                                .appendChild(text(SAMPLE_TEXT))))
                .appendChild(MediaObject.create()
                        .setHeader("Media heading")
                        .setRightMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16)))
                        .appendChild(text(SAMPLE_TEXT)))
                .appendChild(MediaObject.create()
                        .setHeader("Media heading")
                        .setRightMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16)))
                        .setLeftMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16)))
                        .appendChild(text(SAMPLE_TEXT)))
                );

    }
    private void mediaAlignment() {

        element.appendChild(Card.create("MEDIA ALIGNMENT","The images or other media can be aligned top, middle, or bottom. The default is top aligned.")
                .appendChild(MediaObject.create()
                        .setHeader("Media heading")
                        .setLeftMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16, dui_rounded_full)))
                        .appendChild(p(SAMPLE_TEXT))
                        .appendChild(p(SAMPLE_TEXT)))
                .appendChild(MediaObject.create()
                        .setHeader("Media heading")
                        .setLeftMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16, dui_rounded_full)))
                                .withLeftMedia((parent, leftMedia) -> leftMedia.addCss(dui_self_center))
                        .appendChild(p(SAMPLE_TEXT))
                        .appendChild(p(SAMPLE_TEXT)))
                .appendChild(MediaObject.create()
                        .setHeader("Media heading")
                        .setLeftMedia(a()
                                .appendChild(img("showcase-image.jpg")
                                        .addCss(dui_w_16, dui_h_16, dui_rounded_full)))
                        .withLeftMedia((parent, leftMedia) -> leftMedia.addCss(dui_self_end))
                        .appendChild(p(SAMPLE_TEXT))
                        .appendChild(p(SAMPLE_TEXT)))
                );

    }
}
