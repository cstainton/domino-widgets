# Shared Domino showcase

The GWT and TeaVM launchers share 51 original gallery pages containing 167 original sample methods. The **All examples** selector opens every page, plus the browser API and rich text fixtures. The same Java sources run through both compilers.

Examples are pinned to [DominoKit/domino-ui-demo](https://github.com/DominoKit/domino-ui-demo) commit `51e1f75d43179a544c010ca5e88517c93263eeed` (version-2, Domino 2.0.0-RC3). Original Java files, supporting models and used images are retained with SHA-256 hashes in [showcase-lock.json](../upstream/showcase-lock.json). `scripts/generate-showcase.py` deterministically extracts complete methods into framework-free launchers; `--check` fails on adapter drift.

Adapters replace MVP views, generated source-display resources, JSON annotation processors and remote demo services. Contact tables use 100 deterministic sample records. The original country JSON model is decoded through the real Elemental2 JSON API. Image URLs use pinned local assets. Every such source adaptation is recorded explicitly in the lock; no widget implementation is rewritten.

Uploads exercise the original XHR/FormData implementation in browser tests with a controlled server response. GitHub Pages has no upload backend; the public example's upload request cannot persist a file. Media pages render controls and pinned images, but playback/device permissions are not certified.

## Included original pages

Each page receives a render and browser-error check in both backends. Interaction coverage is tracked separately in [COMPATIBILITY.md](COMPATIBILITY.md).

| Route | Original sample methods |
|---|---|
| `buttons` | `initSimpleButtons`, `initButtonSizes`, `initDisabledButtons`, `initButtons`, `initTextButtons`, `initButtonsBasicGroup`, `initButtonsToolbar`, `initSizingGroup`, `initNestingGroup`, `initVerticalGroup`, `initSplitButton`, `initDropDownPosition` |
| `forms` | `initBasicExamples`, `initBasicTextAreaExample`, `initCheckboxExample`, `initRadioExample`, `initSwitchExample` |
| `dialogs` | `sample` |
| `alerts` | `basicAlerts`, `customBackground`, `dismissibleAlerts`, `linksInAlerts` |
| `badges` | `buttonExample`, `buttonExamplesWithMaterialDesignColors`, `listExample` |
| `breadcrumb` | `basicBreadcrumb`, `coloredBreadcrumb`, `breadcrumbWithBackground`, `alignment` |
| `cards` | `cardsWithoutHeaders`, `cardsWithHeaders`, `coloredCards`, `collapsibleCards`, `cardLogo`, `subheader` |
| `chips` | `initSimpleExample`, `initRemovableExample`, `initChipsWithIconsExample`, `initChipsWithImagesExample`, `initChipsWithLettersExample`, `initSelectableChipsExample` |
| `collapse` | `example`, `accordionSample`, `colorFullWithIcons`, `multiOpenItems` |
| `grids` | `grid12Columns`, `grid16Columns`, `grid18Columns`, `grid24Columns`, `grid32Columns` |
| `helpers` | `heightClasses`, `widthClasses`, `marginClasses`, `paddingClasses` |
| `infobox` | `basicInfoBoxes`, `hoverZoomEffect`, `rightAligned` |
| `inputfields` | `initNumberFields`, `initAdvancedFields` |
| `labels` | `initLabels`, `initMaterialLabels` |
| `lists` | `listSample` |
| `loaders` | `initSample` |
| `menu` | `basicMenu`, `basicMenuWithHeaderAndActions`, `basicMenuSubHeader`, `dropMenu`, `contextMenu`, `nestedMenu` |
| `notifications` | `notificationsPosition`, `notificationsTypes`, `withMaterialColors`, `withAnimation` |
| `pagination` | `defaultPagination`, `activePageSample`, `sizesSample`, `initScrollerPagination`, `initAdvancedPagination`, `pagerNexPrevSample` |
| `popover` | `tooltips`, `popover` |
| `preloaders` | `sizesSample`, `colorsSample` |
| `progress` | `basicSample`, `contextualAlternatives`, `stripedSample`, `animatedSample`, `stackedSample`, `materialDesignColors` |
| `sliders` | `initBasic`, `initColors`, `initExample` |
| `spin` | `horizontalSpin`, `verticalSpin` |
| `splitPanel` | `horizontalSplitPanel`, `verticalSplitPanel`, `splitPanelMinMax`, `multiSplit`, `combined` |
| `steppers` | `horizontalStepperTrack`, `verticalStepperTrack`, `horizontalStepper`, `verticalStepper` |
| `tabs` | `basicSample`, `iconsOnly`, `withIconsAndTextSample`, `tabsAlignment`, `closableTabsSample`, `materialDesignColorsSample`, `backgroundSample`, `initDifferentContentSample`, `withAnimation` |
| `timepicker` | `inlineTimePicker`, `withHeader`, `withFooter`, `dropdownTimePicker`, `timeBox` |
| `typography` | `bodyCopy`, `heading`, `textStyles`, `blockqoute`, `lists`, `fontSizes` |
| `waves` | `waves` |
| `datepicker` | `inlineCalendar`, `withHeader`, `withFooter`, `dropdownCalendar`, `dateBox` |
| `formsvalidations` | `initHelperText`, `initIcons`, `initWordCount`, `initValidations`, `initReadOnly` |
| `tree` | `simpleTree`, `nestedTree`, `activeAndExpandIcons` |
| `advanced-forms` | `initFileUploadExample`, `initTagsInputExample`, `initSuggestBoxExample` |
| `animation` |  |
| `carousel` | `basicSample` |
| `media` | `defaultMedia`, `mediaAlignment` |
| `thumbnails` | `basicSample`, `withExtraContentSample`, `withTitle`, `differentDirections` |
| `modals` | `initModalsSize`, `initSheets`, `initModalColor`, `initWindow` |
| `dnd` | `dragAndDrop` |
| `table-basic-data-table` | `basicTable` |
| `table-selection-plugin` | `singleSelectionPlugin`, `multiSelectionPlugin` |
| `table-sort-and-search-plugin` | `sortAndSearch` |
| `table-pagination-plugin` | `simplePagination`, `scrollingPagination`, `advancedPagination` |
| `table-header-bar-plugin` | `tableHeaderBarPlugin` |
| `table-column-resize-plugin` | `resizableColumns` |
| `table-pin-columns-plugin` | `pinColumns` |
| `table-empty-state-plugin` | `emptyState` |
| `table-fixed-data-table` | `basicFixedTable` |
| `table-marker-plugin` | `markerPlugin` |
| `table-columns-groups` | `groupColumns`, `groupPinResizeColumns` |

## Remaining coverage

The original application's framework shell, generated source viewers, and the remaining advanced table examples are not claimed as ported. The newer `domino-showcases` project targets current snapshot Domino/Brix and optional Pro dependencies; it is not the source baseline for this fork.

The original demo repository did not include a root license file at the selected commit. Extracted examples retain attribution; the widget library's Apache-2.0 license does not newly license upstream demo material.
