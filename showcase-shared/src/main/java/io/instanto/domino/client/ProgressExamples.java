// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.progress.Progress;
import org.dominokit.domino.ui.progress.ProgressBar;
import org.dominokit.domino.ui.utils.DominoTimer;

public final class ProgressExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();
  private ProgressBar movingBar;

  public HTMLDivElement render() {
    basicSample();
    contextualAlternatives();
    stripedSample();
    animatedSample();
    stackedSample();
    materialDesignColors();
    return element.element();
  }

  private void basicSample() {
    element.appendChild(
        Card.create("BASIC EXAMPLES")
            .appendChild(Progress.create().appendChild(ProgressBar.create(100).setValue(90)))
            .appendChild(
                Progress.create().appendChild(ProgressBar.create(100).showText().setValue(60)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100)
                            .showText()
                            .textExpression("{value} out of {maxValue} completed")
                            .setValue(75)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100)
                            .showText()
                            .textExpression("{value} out of {maxValue} completed")
                            .setValue(40)))
            .appendChild(Progress.create().appendChild(movingBar = ProgressBar.create(1000))));

    restartProgress();
  }

  private void contextualAlternatives() {
    element.appendChild(
        Card.create(
                "CONTEXTUAL ALTERNATIVES",
                "Progress bars use some of the same button and alert classes for consistent"
                    + " styles.")
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).showText().addCss(dui_success).setValue(80)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).showText().addCss(dui_warning).setValue(60)))
            .appendChild(
                Progress.create()
                    .appendChild(ProgressBar.create(100).showText().addCss(dui_info).setValue(70)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).showText().addCss(dui_error).setValue(30))));
  }

  private void stripedSample() {
    element.appendChild(
        Card.create("STRIPED", "Uses a gradient to create a striped effect.")
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).striped().addCss(dui_success).setValue(80)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).striped().addCss(dui_warning).setValue(60)))
            .appendChild(
                Progress.create()
                    .appendChild(ProgressBar.create(100).striped().addCss(dui_info).setValue(70)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).striped().addCss(dui_error).setValue(30))));
  }

  private void animatedSample() {
    element.appendChild(
        Card.create("ANIMATED", "Animating the bar will add stripes by default.")
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).animate().addCss(dui_success).setValue(80)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).animate().addCss(dui_warning).setValue(60)))
            .appendChild(
                Progress.create()
                    .appendChild(ProgressBar.create(100).animate().addCss(dui_info).setValue(70)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).animate().addCss(dui_error).setValue(30))));
  }

  private void stackedSample() {
    element.appendChild(
        Card.create("STACKED", "You can stack more than one progress bar in a progress element.")
            .appendChild(
                Progress.create()
                    .appendChild(ProgressBar.create(100).animate().addCss(dui_success).setValue(40))
                    .appendChild(ProgressBar.create(100).addCss(dui_warning).setValue(30))
                    .appendChild(
                        ProgressBar.create(100).striped().addCss(dui_error).setValue(20))));
  }

  private void materialDesignColors() {
    element.appendChild(
        Card.create(
                "WITH MATERIAL DESIGN COLORS",
                "You use material design colors to style the progress bar.")
            .appendChild(
                Progress.create()
                    .appendChild(ProgressBar.create(100).addCss(dui_pink).striped().setValue(90)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100)
                            .showText()
                            .addCss(dui_purple)
                            .striped()
                            .setValue(60)))
            .appendChild(
                Progress.create()
                    .appendChild(ProgressBar.create(100).addCss(dui_teal).striped().setValue(75)))
            .appendChild(
                Progress.create()
                    .appendChild(
                        ProgressBar.create(100).addCss(dui_brown).striped().setValue(40))));
  }

  private void restartProgress() {
    movingBar.setValue(0);
    movingBar.textExpression("{percent}%");
    new DominoTimer() {
      @Override
      public void run() {
        if (movingBar.getValue() >= movingBar.getMaxValue()) {
          movingBar.textExpression("Done");
        } else {
          movingBar.setValue(movingBar.getValue() + 1);
        }
      }
    }.scheduleRepeating(10);
  }
}
