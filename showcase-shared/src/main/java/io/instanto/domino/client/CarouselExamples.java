// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.carousel.Carousel;
import org.dominokit.domino.ui.carousel.Slide;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;

public final class CarouselExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    basicSample();
    return element.element();
  }

  private void basicSample() {
    element.appendChild(
        Row.create()
            .appendChild(
                Column.span6()
                    .appendChild(
                        Card.create()
                            .appendChild(
                                Carousel.create()
                                    .appendChild(Slide.create("images/image-gallery/11.jpg"))
                                    .appendChild(Slide.create("images/image-gallery/12.jpg"))
                                    .appendChild(Slide.create("images/image-gallery/19.jpg"))
                                    .appendChild(Slide.create("images/image-gallery/9.jpg"))
                                    .appendChild(Slide.create("images/image-gallery/6.jpg")))))
            .appendChild(
                Column.span6()
                    .appendChild(
                        Card.create()
                            .appendChild(
                                Carousel.create()
                                    .appendChild(
                                        Slide.create(img("images/image-gallery/11.jpg"))
                                            .setLabel("Slide 1")
                                            .setDescription("First slide description"))
                                    .appendChild(
                                        Slide.create(img("images/image-gallery/12.jpg"))
                                            .setLabel("Slide 2")
                                            .setDescription("Second slide description"))
                                    .appendChild(
                                        Slide.create(img("images/image-gallery/19.jpg"))
                                            .setLabel("Slide 3")
                                            .setDescription("Third slide description"))
                                    .appendChild(
                                        Slide.create(img("images/image-gallery/9.jpg"))
                                            .setLabel("Slide 4")
                                            .setDescription("Fourth slide description"))
                                    .appendChild(
                                        Slide.create(img("images/image-gallery/6.jpg"))
                                            .setLabel("Slide 5")
                                            .setDescription("Fifth slide description"))
                                    .startAutoSlide(3000)))));
  }
}
