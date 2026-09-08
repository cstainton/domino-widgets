package io.instanto.domino.testing;

import static org.junit.Assert.*;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.FilePayload;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Runs the shared feature assertions with real WebKit input on the JVM. */
public final class BrowserDriver implements Browser {
  private Playwright playwright;
  private com.microsoft.playwright.Browser browser;
  private Page page;
  private final List<String> failures = new ArrayList<>();

  public void open(String application, String route) {
    close();
    playwright = Playwright.create();
    browser = playwright.webkit().launch();
    page =
        browser.newPage(
            new com.microsoft.playwright.Browser.NewPageOptions()
                .setViewportSize(1280, 900)
                .setLocale("en-US")
                .setTimezoneId("UTC"));
    page.setDefaultTimeout(10000);
    failures.clear();
    page.onPageError(failures::add);
    page.onResponse(
        response -> {
          if (response.status() >= 400)
            failures.add("HTTP " + response.status() + " " + response.url());
        });
    page.navigate(
        FixtureServer.start().base()
            + application
            + "/index.html"
            + (route.isEmpty() ? "" : "?page=" + route));
  }

  public Element root() {
    return new Node(page.locator("body"));
  }

  public void waitFor(Runnable assertion) {
    AssertionError[] last = {null};
    try {
      page.waitForCondition(
          () -> {
            try {
              assertion.run();
              return true;
            } catch (AssertionError e) {
              last[0] = e;
              return false;
            }
          });
    } catch (TimeoutError timeout) {
      if (last[0] != null) throw last[0];
      throw timeout;
    }
    assertion.run();
  }

  public void settle() {
    page.evaluate(
        "() => new Promise(resolve => requestAnimationFrame(() => requestAnimationFrame(resolve)))");
  }

  public String errors() {
    String diagnostics = (String) page.evaluate("window.__dominoTest.errors.join('\\n')");
    return String.join("\n", failures) + diagnostics;
  }

  public String console() {
    return (String) page.evaluate("window.__dominoTest.console.join('\\n')");
  }

  public String url() {
    return page.url();
  }

  public void back() {
    page.goBack();
  }

  public String uploadedRequest() {
    return page.request().get(FixtureServer.start().base() + "last-upload").text();
  }

  public boolean stylesheetAvailable() {
    return page.request()
        .get(
            (String)
                page.evaluate(
                    "new URL('domino-widgets/css/domino-ui/domino-ui.css',document.baseURI).href"))
        .ok();
  }

  public void close() {
    if (playwright != null) {
      playwright.close();
      playwright = null;
    }
  }

  private static final class Node implements Element {
    private final Locator node;

    Node(Locator node) {
      this.node = node;
    }

    public List<Element> all(String selector) {
      List<Element> found = new ArrayList<>();
      for (Locator item : node.locator(selector).all()) found.add(new Node(item));
      return found;
    }

    public String text() {
      return node.textContent();
    }

    public String attr(String name) {
      return node.getAttribute(name);
    }

    public String value() {
      return node.inputValue();
    }

    public String css(String name) {
      return (String) node.evaluate("(el,name)=>getComputedStyle(el).getPropertyValue(name)", name);
    }

    public boolean visible() {
      return node.isVisible();
    }

    public boolean focused() {
      return (Boolean) node.evaluate("el=>el===el.ownerDocument.activeElement");
    }

    public boolean imagesLoaded() {
      return (Boolean)
          node.evaluate(
              "el=>Array.from(el.querySelectorAll('img')).every(i=>!i.src||(i.complete&&i.naturalWidth>0))");
    }

    public void click() {
      node.click();
    }

    public void fill(String text) {
      node.fill(text);
    }

    public void press(String key) {
      node.press(key);
    }

    public void focus() {
      node.focus();
    }

    public void upload(String name, String content) {
      node.setInputFiles(
          new FilePayload(name, "text/plain", content.getBytes(StandardCharsets.UTF_8)));
    }

    public void finishAnimations() {
      node.evaluate("el=>Promise.all(el.getAnimations().map(a=>a.finished))");
    }

    public Element closest(String selector) {
      return new Node(node.locator("xpath=ancestor-or-self::" + selector + "[1]"));
    }
  }
}
