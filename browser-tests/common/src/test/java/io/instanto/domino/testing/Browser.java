package io.instanto.domino.testing;

import java.util.List;

/** Browser operations shared by the TeaVM/Mockatcha and JVM/WebKit drivers. */
public interface Browser extends AutoCloseable {
  void open(String backend, String route);

  Element root();

  void waitFor(Runnable assertion);

  void settle();

  String errors();

  String console();

  String url();

  void back();

  String uploadedRequest();

  boolean stylesheetAvailable();

  void close();

  interface Element {
    List<Element> all(String selector);

    default Element first(String selector) {
      List<Element> matches = all(selector);
      org.junit.Assert.assertFalse("No match for " + selector, matches.isEmpty());
      return matches.get(0);
    }

    default Element text(String text, boolean last) {
      List<Element> matches =
          all("*").stream()
              .filter(e -> e.text().trim().equals(text))
              .filter(e -> e.all("*").stream().noneMatch(child -> child.text().trim().equals(text)))
              .toList();
      org.junit.Assert.assertFalse("No text match for " + text, matches.isEmpty());
      return matches.get(last ? matches.size() - 1 : 0);
    }

    String text();

    String attr(String name);

    String value();

    String css(String name);

    boolean visible();

    boolean focused();

    boolean imagesLoaded();

    void click();

    void fill(String text);

    void press(String key);

    void focus();

    void upload(String name, String content);

    void finishAnimations();

    Element closest(String selector);
  }
}
