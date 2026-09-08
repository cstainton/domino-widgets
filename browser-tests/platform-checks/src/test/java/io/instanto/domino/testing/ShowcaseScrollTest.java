package io.instanto.domino.testing;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/** Native input checks: programmatic scrolling would miss overflow-hidden regressions. */
@RunWith(Parameterized.class)
public class ShowcaseScrollTest {
  @Parameterized.Parameters(name = "{0}/{1}")
  public static Collection<Object[]> browsers() {
    return Arrays.asList(
        new Object[][] {
          {"teavm", "chromium"},
          {"teavm", "firefox"},
          {"teavm", "webkit"}
        });
  }

  private static HttpServer server;
  private final String backend;
  private final String engine;
  private Playwright playwright;
  private Browser browser;
  private BrowserContext context;
  private Page page;
  private final List<String> errors = new ArrayList<>();

  public ShowcaseScrollTest(String backend, String engine) {
    this.backend = backend;
    this.engine = engine;
  }

  @BeforeClass
  public static void serve() throws Exception {
    Path root = Path.of(System.getProperty("domino.root")).toAbsolutePath().normalize();
    server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    server.createContext(
        "/",
        exchange -> {
          Path file = root.resolve(exchange.getRequestURI().getPath().substring(1)).normalize();
          if (Files.isDirectory(file)) file = file.resolve("index.html");
          if (!file.startsWith(root) || !Files.isRegularFile(file)) {
            exchange.sendResponseHeaders(404, -1);
            exchange.close();
            return;
          }
          String type = Files.probeContentType(file);
          if (file.toString().endsWith(".js")) type = "text/javascript";
          if (file.toString().endsWith(".css")) type = "text/css";
          exchange
              .getResponseHeaders()
              .set("Content-Type", type == null ? "application/octet-stream" : type);
          exchange.sendResponseHeaders(200, Files.size(file));
          try (var output = exchange.getResponseBody()) {
            Files.copy(file, output);
          }
          exchange.close();
        });
    server.start();
  }

  @AfterClass
  public static void stop() {
    if (server != null) server.stop(0);
  }

  @Before
  public void launch() {
    playwright = Playwright.create();
    BrowserType type =
        switch (engine) {
          case "firefox" -> playwright.firefox();
          case "webkit" -> playwright.webkit();
          default -> playwright.chromium();
        };
    browser = type.launch();
    Browser.NewContextOptions options = new Browser.NewContextOptions().setViewportSize(390, 844);
    if (engine.equals("chromium")) options.setHasTouch(true).setIsMobile(true);
    context = browser.newContext(options);
    page = context.newPage();
    page.setDefaultTimeout(5000);
    page.onPageError(errors::add);
  }

  @After
  public void close() {
    try {
      assertEquals("Uncaught browser errors", List.of(), errors);
    } finally {
      if (playwright != null) playwright.close();
    }
  }

  private void open(String route) {
    page.navigate(
        "http://127.0.0.1:"
            + server.getAddress().getPort()
            + "/showcase-"
            + backend
            + "/target/site/"
            + (route.isEmpty() ? "" : "?page=" + route));
    page.locator(
            route.isEmpty()
                ? "#showcase-home[data-ready=true]"
                : "#gallery-examples[data-ready=true]")
        .waitFor();
  }

  private double scrollY() {
    return ((Number) page.evaluate("window.scrollY")).doubleValue();
  }

  @Test
  public void wheelCanScrollHomeAndLongExamples() {
    for (String route : List.of("", "buttons", "forms")) {
      open(route);
      page.mouse().move(370, 600);
      page.mouse().wheel(0, 550);
      page.waitForCondition(() -> scrollY() > 100);
      page.mouse().wheel(0, -2000);
      page.waitForCondition(() -> scrollY() < 10);
    }
  }

  @Test
  public void touchCanReachTheFooterAndReturnToTheTop() {
    // Chromium exposes native touch gestures through CDP; no synthetic DOM touch events.
    assumeTrue("Native touch injection is Chromium-specific", engine.equals("chromium"));
    for (String route : List.of("", "buttons", "forms")) {
      open(route);
      CDPSession cdp = context.newCDPSession(page);
      JsonObject gesture = new JsonObject();
      gesture.addProperty("x", 370);
      gesture.addProperty("y", 700);
      // Keep the whole swipe inside the mobile viewport, as a finger would move.
      gesture.addProperty("yDistance", -500);
      gesture.addProperty("speed", 800);
      gesture.addProperty("gestureSourceType", "touch");
      cdp.send("Input.synthesizeScrollGesture", gesture);
      page.waitForCondition(() -> scrollY() > 100);
      for (int i = 0; i < 30 && !footerInView(); i++)
        cdp.send("Input.synthesizeScrollGesture", gesture);
      assertTrue("Touch can reach the footer on " + route, footerInView());
      gesture.addProperty("y", 200);
      gesture.addProperty("yDistance", 500);
      for (int i = 0; i < 30 && scrollY() > 10; i++)
        cdp.send("Input.synthesizeScrollGesture", gesture);
      page.waitForCondition(() -> scrollY() < 10);
      cdp.detach();
    }
  }

  @Test
  public void homePresentsTheUpstreamShowcaseStructure() {
    open("");
    assertEquals(
        "Build polished enterprise interfaces with Domino UI.", page.locator("h1").innerText());
    assertEquals(0, page.locator("#screen").count());
    assertTrue(page.locator("#showcase-home").innerText().contains("Featured areas"));
    assertTrue(page.locator("#showcase-home").innerText().contains("Design foundations"));
    assertTrue(page.locator("#showcase-home").innerText().contains("Resource center"));
    page.screenshot(
        new Page.ScreenshotOptions()
            .setPath(Path.of("target/screenshots/" + backend + "-" + engine + "-mobile.png"))
            .setFullPage(true));
    page.setViewportSize(1280, 900);
    page.screenshot(
        new Page.ScreenshotOptions()
            .setPath(Path.of("target/screenshots/" + backend + "-" + engine + "-desktop.png"))
            .setFullPage(true));
  }

  private boolean footerInView() {
    return (Boolean)
        page.locator(".showcase-footer")
            .evaluate("el => el.getBoundingClientRect().bottom <= innerHeight + 2");
  }
}
