# Browser assets

The asset artifact is produced only from the exact source archive in `upstream/source-lock.json`.

* Original public CSS, responsive screen styles, MDI styles, fonts and maps are retained.
* `domino-ui.css` is a deterministic concatenation of non-minified component CSS, including nested responsive and MDI CSS. Its location preserves the original `fonts/` and `../fonts/` URL conventions.
* MDI resources identify Material Design Icons 7.4.47. Inter and Open Sans font files are retained from the fork. No external runtime CDN is required.
* The upstream `cached/mdi` copy is accounted for in the complete archive inventory but is not embedded a second time; the public asset copy is used.
* Java backend artifacts contain no CSS or font files. The Maven `check-artifacts` goal checks this during `verify`.
* Generated icon Java sources are already checked into the pinned fork. Ordinary builds disable annotation processing and use those exact inputs. Processor sources and metadata remain in the immutable archive for future reviewed regeneration.

The source archive's Apache licence is retained. Asset-specific provenance remains identifiable in the original resources; a broader licence audit is required before representing all third-party font assets as covered solely by the Java project's licence.
