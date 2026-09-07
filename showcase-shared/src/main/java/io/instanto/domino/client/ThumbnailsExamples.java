// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see upstream/showcase-lock.json.
package io.instanto.domino.client;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.thumbnails.Thumbnail;
import org.dominokit.domino.ui.thumbnails.ThumbnailDirection;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.FooterContent;
import org.dominokit.domino.ui.utils.HeaderContent;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.elements.DivElement;
import static org.dominokit.domino.ui.utils.Domino.*;
public final class ThumbnailsExamples implements org.dominokit.domino.ui.style.DominoCss {
 private final DivElement element=div();
    private static final String SAMPLE_TEXT = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s";
 public HTMLDivElement render(){
basicSample();
withExtraContentSample();
withTitle();
differentDirections();
return element.element();}
    private void basicSample() {
        element.appendChild(Card.create("DEFAULT EXAMPLE", "By default, thumbnails are designed to showcase linked images with minimal required markup")
                .appendChild(Row.create()
                        .appendChild(Column.span3().appendChild(Thumbnail.create()
                                .appendChild(img("images/image-gallery/5.jpg")
                                                .addCss(dui_image_responsive))))
                        .appendChild(Column.span3().appendChild(Thumbnail.create()
                                .appendChild(img("images/image-gallery/6.jpg")
                                                .addCss(dui_image_responsive))))
                        .appendChild(Column.span3().appendChild(Thumbnail.create()
                                .appendChild(img("images/image-gallery/7.jpg")
                                                .addCss(dui_image_responsive))))
                        .appendChild(Column.span3().appendChild(Thumbnail.create()
                                .appendChild(img("images/image-gallery/8.jpg")
                                                .addCss(dui_image_responsive)))))
                );


    }
    private void withExtraContentSample() {
        element.appendChild(Card.create("CUSTOM CONTENT", "With a bit of extra markup, it's possible to add any kind of HTML content like headings, paragraphs, or buttons into thumbnails.")
                .appendChild(Row.create()
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(a().appendChild(img("images/image-gallery/1.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(a().appendChild(img("images/image-gallery/2.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(a().appendChild(img("images/image-gallery/3.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(a().appendChild(img("images/image-gallery/4.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                )
        );
    }
    private void withTitle() {
        element.appendChild(Card.create("WITH TITLE")
                .appendChild(Row.create()
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/1.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/2.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/3.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span3()
                                .appendChild(Thumbnail.create()
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/4.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                )
        );
    }
    private void differentDirections() {
        element.appendChild(Card.create("WITH TITLE")
                .appendChild(Row.create()
                        .appendChild(Column.span6()
                                .appendChild(Thumbnail.create()
                                        .setDirection(ThumbnailDirection.COLUMN)
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/1.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span6()
                                .appendChild(Thumbnail.create()
                                        .setDirection(ThumbnailDirection.COLUMN_REVERSE)
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/2.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                )
                .appendChild(Row.create()
                        .appendChild(Column.span6()
                                .appendChild(Thumbnail.create()
                                        .setDirection(ThumbnailDirection.ROW)
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/3.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                        .appendChild(Column.span6()
                                .appendChild(Thumbnail.create()
                                        .setDirection(ThumbnailDirection.ROW_REVERSE)
                                        .appendChild(HeaderContent.of(h(5).textContent("Thumbnail title")))
                                        .appendChild(a().appendChild(img("images/image-gallery/4.jpg")
                                                .addCss(dui_image_responsive)))
                                        .appendChild(FooterContent.of(h(4).textContent("Thumbnail label")))
                                        .appendChild(FooterContent.of(p(SAMPLE_TEXT)))
                                        .appendChild(FooterContent.of(Button.create("BUTTON").addCss(dui_primary)))
                                )
                        )
                )
        );
    }
}
