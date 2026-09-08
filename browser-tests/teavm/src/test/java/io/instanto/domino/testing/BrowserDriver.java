package io.instanto.domino.testing;

import static io.instanto.mockatcha.dom.Expect.expect;
import static org.junit.Assert.*;

import io.instanto.mockatcha.dom.Dom;
import io.instanto.mockatcha.dom.FramedApplication;
import java.util.ArrayList;
import java.util.List;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.dom.html.HTMLElement;

/** Executes the compiled application in a same-origin frame owned by Mockatcha DOM. */
public final class BrowserDriver implements Browser {
  private FramedApplication app;

  public void open(String application, String route) {
    close();
    Dom.reset();
    String file = route.equals("advanced-forms") ? "upload.html" : "index.html";
    app =
        FramedApplication.open(
            "/resources/domino-fixtures/"
                + application
                + "/"
                + file
                + (route.isEmpty() ? "" : "?page=" + route),
            1280,
            900);
    app.awaitReady(p -> p.root() != null, 20000);
  }

  public Element root() {
    return new Node(app.page().root());
  }

  private HTMLElement body() {
    return app.page().root();
  }

  public void waitFor(Runnable assertion) {
    Dom.waitFor(assertion, 10000);
  }

  public void settle() {
    frames(body());
  }

  public String errors() {
    app.assertHealthy();
    return diagnostics(body(), "errors");
  }

  public String console() {
    return diagnostics(body(), "console");
  }

  public String url() {
    return location(body());
  }

  public void back() {
    historyBack(body());
  }

  public String uploadedRequest() {
    return request(uploadEndpoint(body()));
  }

  public boolean stylesheetAvailable() {
    return requestStatus(assetUrl(body())) == 200;
  }

  public void close() {
    if (app != null) {
      app.close();
      app = null;
    }
    Dom.reset();
  }

  private static final class Node implements Element {
    private final org.teavm.jso.dom.xml.Element node;

    Node(org.teavm.jso.dom.xml.Element node) {
      this.node = node;
    }

    public List<Element> all(String selector) {
      var nodes = node.querySelectorAll(selector);
      List<Element> found = new ArrayList<>();
      // Attribute and text queries also cover SVG elements, which are not HTMLElements.
      for (int i = 0; i < nodes.getLength(); i++) found.add(new Node(nodes.item(i)));
      return found;
    }

    public String text() {
      return node.getTextContent();
    }

    public String attr(String name) {
      return node.getAttribute(name);
    }

    public String value() {
      return fieldValue(node);
    }

    public String css(String name) {
      return style(node, name);
    }

    public boolean visible() {
      try {
        expect((HTMLElement) node).toBeVisible();
        return true;
      } catch (AssertionError e) {
        return false;
      }
    }

    public boolean focused() {
      try {
        expect((HTMLElement) node).toHaveFocus();
        return true;
      } catch (AssertionError e) {
        return false;
      }
    }

    public boolean imagesLoaded() {
      return loaded(node);
    }

    public void click() {
      Dom.click((HTMLElement) node);
    }

    public void fill(String text) {
      if (editable(node)) edit(node, text);
      else Dom.type((HTMLElement) node, text);
    }

    public void press(String key) {
      Dom.press((HTMLElement) node, key);
      if (key.equals("Tab")) Dom.blur((HTMLElement) node);
      // DOM key dispatch has no browser default action; WebKit checks real keyboard activation.
      if (key.equals("Enter") && node.getTagName().equalsIgnoreCase("button"))
        Dom.click((HTMLElement) node);
    }

    public void focus() {
      Dom.focus((HTMLElement) node);
    }

    public void upload(String name, String content) {
      files(node, name, content);
    }

    public void finishAnimations() {
      animations(node);
    }

    public Element closest(String selector) {
      org.teavm.jso.dom.xml.Element result = ancestor(node, selector);
      assertNotNull(result);
      return new Node(result);
    }
  }

  @JSBody(
      params = {"el", "field"},
      script = "return (el.ownerDocument.defaultView.__dominoTest[field] || []).join('\\n');")
  private static native String diagnostics(org.teavm.jso.dom.xml.Element el, String field);

  @JSBody(params = "el", script = "return el.ownerDocument.defaultView.location.href;")
  private static native String location(org.teavm.jso.dom.xml.Element el);

  @JSBody(params = "el", script = "el.ownerDocument.defaultView.history.back();")
  private static native void historyBack(org.teavm.jso.dom.xml.Element el);

  @JSBody(
      params = "el",
      script = "return el.ownerDocument.baseURI.replace(/[^/]*$/, 'last-upload');")
  private static native String uploadEndpoint(org.teavm.jso.dom.xml.Element el);

  @JSBody(
      params = "el",
      script =
          "return new URL('domino-widgets/css/domino-ui/domino-ui.css', el.ownerDocument.baseURI).href;")
  private static native String assetUrl(org.teavm.jso.dom.xml.Element el);

  @JSBody(params = "el", script = "return el.value || '';")
  private static native String fieldValue(org.teavm.jso.dom.xml.Element el);

  @JSBody(
      params = {"el", "name"},
      script = "return el.ownerDocument.defaultView.getComputedStyle(el).getPropertyValue(name);")
  private static native String style(org.teavm.jso.dom.xml.Element el, String name);

  @JSBody(
      params = "el",
      script =
          "return Array.from(el.querySelectorAll('img')).every(i => !i.src || (i.complete && i.naturalWidth > 0));")
  private static native boolean loaded(org.teavm.jso.dom.xml.Element el);

  @JSBody(params = "el", script = "return el.isContentEditable;")
  private static native boolean editable(org.teavm.jso.dom.xml.Element el);

  @JSBody(
      params = {"el", "text"},
      script =
          "el.focus(); el.textContent=text; el.dispatchEvent(new el.ownerDocument.defaultView.InputEvent('input',{bubbles:true,inputType:'insertText',data:text}));")
  private static native void edit(org.teavm.jso.dom.xml.Element el, String text);

  @JSBody(
      params = {"el", "selector"},
      script = "return el.closest(selector);")
  private static native org.teavm.jso.dom.xml.Element ancestor(
      org.teavm.jso.dom.xml.Element el, String selector);

  @JSBody(
      params = {"el", "name", "content"},
      script =
          "const w=el.ownerDocument.defaultView; const dt=new w.DataTransfer(); dt.items.add(new w.File([content],name,{type:'text/plain'})); el.files=dt.files; el.dispatchEvent(new w.Event('input',{bubbles:true})); el.dispatchEvent(new w.Event('change',{bubbles:true}));")
  private static native void files(org.teavm.jso.dom.xml.Element el, String name, String content);

  @JSFunctor
  private interface Done extends JSObject {
    void done(String value);
  }

  @Async
  private static native void frames(org.teavm.jso.dom.xml.Element el);

  private static void frames(org.teavm.jso.dom.xml.Element el, AsyncCallback<Void> callback) {
    nextFrames(el, value -> callback.complete(null));
  }

  @JSBody(
      params = {"el", "done"},
      script =
          "const w=el.ownerDocument.defaultView; w.requestAnimationFrame(()=>w.requestAnimationFrame(()=>done('')));")
  private static native void nextFrames(org.teavm.jso.dom.xml.Element el, Done done);

  @Async
  private static native void animations(org.teavm.jso.dom.xml.Element el);

  private static void animations(org.teavm.jso.dom.xml.Element el, AsyncCallback<Void> callback) {
    animationEnd(el, value -> callback.complete(null));
  }

  @JSBody(
      params = {"el", "done"},
      script =
          "Promise.all(el.getAnimations().map(a=>a.finished.catch(()=>{}))).then(()=>done(''));")
  private static native void animationEnd(org.teavm.jso.dom.xml.Element el, Done done);

  @Async
  private static native String request(String url);

  private static void request(String url, AsyncCallback<String> callback) {
    fetchText(url, callback::complete, error -> callback.error(new AssertionError(error)));
  }

  @JSBody(
      params = {"url", "done", "failed"},
      script =
          "fetch(url).then(r=>{if(!r.ok)throw Error('HTTP '+r.status);return r.text();}).then(done,e=>failed(String(e)));")
  private static native void fetchText(String url, Done done, Done failed);

  @Async
  private static native int requestStatus(String url);

  private static void requestStatus(String url, AsyncCallback<Integer> callback) {
    fetchStatus(
        url,
        value -> callback.complete(Integer.parseInt(value)),
        error -> callback.error(new AssertionError(error)));
  }

  @JSBody(
      params = {"url", "done", "failed"},
      script = "fetch(url).then(r=>done(String(r.status)),e=>failed(String(e)));")
  private static native void fetchStatus(String url, Done done, Done failed);
}
