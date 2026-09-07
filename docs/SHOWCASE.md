# Shared Domino showcase

The standalone launchers expose the same gallery and examples on GWT and TeaVM. Use the Buttons, Forms and Dialogs navigation links; the Table & lifecycle contracts page contains the four-component behavioral fixture.

The examples come from [DominoKit/domino-ui-demo](https://github.com/DominoKit/domino-ui-demo), pinned to its `version-2` branch commit in [showcase-lock.json](../upstream/showcase-lock.json). That branch targets Domino UI 2.0.0-RC3, close to the pinned 2.1 fork. The original source files are retained and checksummed under `upstream/showcase/`.

Selected example methods retain their widget construction and behavior. The standalone adapters replace the surrounding Domino MVP view, GWT asynchronous loader and generated source-display resources with a plain shared Java launcher. This removes the showcase application's framework dependencies from the widget compatibility experiment. The widget libraries themselves have no showcase dependencies.

| Original page | Included examples |
|---|---|
| Buttons | Simple buttons, sizes, disabled buttons, basic groups |
| Basic forms | Text inputs, textareas, checkboxes |
| Dialogs | Original message and alert dialog examples, including custom content |
| Datatable | Independent deterministic contract fixture; original contact-provider examples remain pending |

The newer [DominoKit/domino-showcases](https://github.com/DominoKit/domino-showcases) project uses current snapshot Domino UI/Brix and optional Pro dependencies. It is inventoried as a future source rather than assumed compatible with this older source pin. The original TeaVM fork's `domino-ui-demo` is a different small direct-DOM demo.

This is an initial gallery extraction, not a port of every original showcase page. Remaining menus, advanced forms, datatable plugins and other categories should be added with the same shared-source and browser-contract approach. Browser reports cover the exact included interactions; merely displaying an example does not establish all of its behaviors.

Provenance: the original demo repository did not include a root license file at the selected commit. The extracted examples retain explicit upstream attribution; the widget library's Apache-2.0 license should not be interpreted as newly licensing upstream demo material.
