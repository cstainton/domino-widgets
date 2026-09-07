# Consumer migration

A source/import inventory of the Verrai and Sarto checkouts found these remaining consumers:

| Consumer | Prepared change / status |
|---|---|
| Sarto Running Club `MembersEventsPage` | Original `datepicker.Calendar`, `CalendarDay`, plugin and listener APIs; `.element()` with two identity casts to the framework's TeaVM DOM; matched assets JAR |
| Sarto TMS Domino client | No old Domino widget imports; unused `verrai-widgets-domino` dependency removed in migration branch |
| Verrai demo `WidgetContractTest` | Old Button framework contract still needs a thin template/value adapter and a replacement regression |
| Verrai parent/BOM/module entries | Keep until consumer migration is verified; do not remove the implementation prematurely |

The Running Club changes are isolated on local Sarto branch `codex/standalone-domino-calendar`, based on `dbe32ec85`, preserving unrelated working-checkout changes. Java compilation and generated template/CDI/RPC processing pass. Full TeaVM application verification is blocked: both the modified and untouched baselines fail on the same missing `ExecutorService`, `CompletionException`, `AuthenticationJson`, persistence model/runtime classes and `StompRpcTransport.StompClient`. The migration branch documents the exact build command and required application regressions.

Standalone browser contracts cover the calendar's day/month navigation and the shared compatibility boundaries. They do not replace the blocked application's RPC, navigation and event-marker regressions. No full application migration or retirement is claimed.

## API directions for remaining wrappers

| Old Verrai API | Original API / migration work |
|---|---|
| `Button` | `org.dominokit.domino.ui.button.Button`; template attachment and framework listener contract |
| Text/value inputs | Original forms plus framework value/validation adapter |
| `Modal` | `dialogs.Dialog`; translate API and verify application focus assumptions |
| `TableWidget` | `datatable.DataTable`, stores and plugins; migrate data/selection/filter/pagination behavior |
| Calendar | Original datepicker APIs; representative migration prepared above |
| Other wrappers | Assess each consumer's actual behaviors; they are not drop-in API equivalents |
| Bootstrap wrappers | Separate migration to standalone `bootstrap-widgets` |

Framework adapters belong in the consuming framework and must not recreate widget rendering or state. The standalone artifacts have no Verrai/Sarto/CDI dependencies.
