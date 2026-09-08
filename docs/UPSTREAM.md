# Upstream update assessment

Checked on 8 September 2026. This is an assessment; the source and binding locks
have not been changed. Exact revisions and commit subjects are retained in the
[machine-readable report](../reports/upstream-status.json).

## Widget sources

The pinned fork is `31404a554cda9a7118c5419b4690e980f67d7d7f`. Upstream
`DominoKit/domino-ui` master is `4f2a86662796089bb28bcfb2208dbb310cc0da52`,
whose POM declares version 2.1.1. The common ancestor is
`21adff68d9ebefd183feef6e1ce706b7b4f7bd33`.

The [GitHub comparison](https://github.com/cstainton/domino-ui/compare/31404a554cda9a7118c5419b4690e980f67d7d7f...DominoKit:4f2a86662796089bb28bcfb2208dbb310cc0da52)
contains 31 upstream commits missing from the pinned fork, and 24 fork-side commits
absent from upstream. Those counts include merges and version updates; they are not
counts of independent widget fixes.

Changes to assess for integration include:

- Date and time inputs: custom formatters and global defaults, 12 AM selection,
  and year selection on mobile.
- Tables: automatic body scrolling, selectable rows, collection filter data,
  scrolling pagination and record-details click propagation.
- Trees: preserving expansion state through filtering and automatic collapse.
- Forms and menus: toggle-button behaviour, upload-icon clicks, select focus,
  tooltip flicker and off-screen submenus.
- Layout: initial split-panel sizes and resizable windows/dialogs.
- Build dependencies: Elemental2 moves from 1.2.3 to 1.3.2, alongside other updates.
  Our compiler baseline already uses GWT 2.13.1, so dependency changes need comparison
  with this repository's POMs rather than copying upstream's build wholesale.

The formatter/global-configuration changes intersect the port's i18n work. The
Elemental2 update changes inputs to binding generation. These need particular review
when bringing the widget and asset sources forward together.

## Changes retained in our fork

The reverse [fork comparison](https://github.com/cstainton/domino-ui/compare/DominoKit:4f2a86662796089bb28bcfb2208dbb310cc0da52...31404a554cda9a7118c5419b4690e980f67d7d7f)
contains 24 commits including merges, build changes and earlier port experiments.
The concrete fixes and additions include:

| Change | Purpose | Scope |
|---|---|---|
| `DominoId` integral timestamp ([commit](https://github.com/cstainton/domino-ui/commit/e7ed739e75b2bbd3d7cb9c1d9d1eba3d11d98e8c)) | Prevent scientific notation from producing invalid CSS selectors | Shared widget source; current upstream master still concatenates the double timestamp |
| `DynamicStyleSheet.getStyleSheet()` ([commit](https://github.com/cstainton/domino-ui/commit/08c1bab)) | Return `styleElement.sheet` instead of an uninitialised field | Shared widget source; current upstream master still returns the unused field |
| Explicit `BrowserDateTimeFormatInfo` locale ([commit](https://github.com/cstainton/domino-ui/commit/31404a554cda9a7118c5419b4690e980f67d7d7f)) | Allow an explicit locale for browser-generated calendar labels | Addition to the fork's compatibility service, with Spanish and Arabic browser contracts |
| `StateHistory.removeListener()` ([commit](https://github.com/cstainton/domino-ui/commit/08c1bab)) | Remove the wrapper whose listener matches the requested listener | Separate earlier TeaVM history port; excluded from this widget distribution |

Other fork work introduces timer/scheduler abstractions, modular editor, SafeHtml
and browser i18n services, earlier TeaVM build configuration and experimental
history/REST/demo modules. These are porting adaptations, not 24 independent widget
bug fixes. The first two fixes above are candidates for separate upstream proposals;
no upstream PR has been opened as part of this assessment.

## Development branch

[Upstream development compared with master](https://github.com/DominoKit/domino-ui/compare/4f2a86662796089bb28bcfb2208dbb310cc0da52...0cb4a784f424d99c30b50ae1c380970f5738f756)
has 11 commits absent from master; master also has two commits absent from development.
The development changes include tooltip dismissal, a default dialog-size option,
RTL application layout, font defaults, built-in themes, card header/footer structure
and accessibility improvements. They deserve a separate integration pass after the
released source baseline, particularly where CSS and layout behaviour change.

## Showcase

The pinned `DominoKit/domino-ui-demo` commit
`51e1f75d43179a544c010ca5e88517c93263eeed` still matches the current `version-2`
branch: [no additional commits](https://github.com/DominoKit/domino-ui-demo/compare/51e1f75d43179a544c010ca5e88517c93263eeed...version-2).
This establishes currency for the branch used by this port, not coverage of every
upstream demo page or every other branch.

## Integration sequence

First update the source fork to the released upstream baseline, reviewing its
existing service adaptations and fixes. Then refresh the archive and source lock,
update binding inputs where needed, regenerate sources and matched assets, and
review API differences. Run development and production builds through both GWT and
TeaVM, all browser contracts and the independent consumers before publishing updated
artifacts. Add targeted interaction checks for the imported fixes.

The [port design](DESIGN.md) explains the source and compatibility boundaries.
