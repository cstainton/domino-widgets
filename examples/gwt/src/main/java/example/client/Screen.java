package example.client;
import elemental2.dom.DomGlobal;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.datepicker.Calendar;
public final class Screen {
 public static void mount(){
  TextBox name=TextBox.create("Name").setId("name");
  Button button=Button.create("Greet").setId("greet");
  button.addClickListener(e->DomGlobal.document.body.setAttribute("data-greeting","Hello "+name.getValue()));
  DomGlobal.document.body.appendChild(name.element());DomGlobal.document.body.appendChild(button.element());
  DomGlobal.document.body.appendChild(Calendar.create().element());
  DomGlobal.document.body.setAttribute("data-ready","true");
 }
}