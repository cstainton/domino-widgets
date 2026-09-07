# Consumer migration

A source/import inventory of the Verrai and Sarto checkouts found these remaining consumers:

| Consumer | Prepared change / status |
|---|---|
| Verrai Running Club `MembersEventsPage` | Original `datepicker.Calendar`, `CalendarDay`, plugin and listener APIs; `.element()` with two identity casts to the framework's TeaVM DOM; matched assets JAR |
| Sarto TMS Domino client | No old Domino widget imports; unused `verrai-widgets-domino` dependency removed in migration branch |
| Verrai demo `WidgetContractTest` | Old Button framework contract still needs a thin template/value adapter and a replacement regression |
| Verrai parent/BOM/module entries | Keep until consumer migration is verified; do not remove the implementation prematurely |

Running Club now lives under [`demos/running-club` in the user's Verrai fork](https://github.com/cstainton/verrai/tree/codex/domino-consumer-verification/demos/running-club), commit `e76cace`, included by Verrai's `demo` Maven profile. The complete application was carried over from Sarto commit `0ca3268418d701bb414ba3e254b074ba7d64b6ab`, preserving its DominoKit UI, five services, both hosts and club configurations. Full TeaVM client compilation and Maven verification pass, including seven Chrome tests using the actual events page, generated Verrai template binder, real Caller and original standalone calendar.

The application tests cover event markers, day selection and navigation requests, month reloading, repeated page entry, hidden-page cleanup, stale responses and failed-request recovery. Service responses and navigation are deterministic fixtures; live LDAP/STOMP and complete host end-to-end verification are not claimed. All 143 Domino assets are packaged into the application web bundle and match the standalone assets JAR byte-for-byte. A Maven dependency ban prevents the old widget dependency returning transitively.

The earlier baseline failures are retained in `reports/migration-baseline.json`. Refreshing inconsistent framework snapshots and rebuilding the committed Verrai persistence artifact at `1f1cb93` resolved them; no Verrai source changes were needed. An older cached CDI POM still emits an obsolete-async dependency warning without failing the verified client build. [Application verification and prerequisites](https://github.com/cstainton/verrai/blob/e76cace/demos/running-club/docs/DOMINO-MIGRATION.md) document the exact scope. The separate legacy Verrai wrapper module remains for its existing contract tests.

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

The relocation was rechecked in Verrai: seven calendar browser tests pass and the
143 packaged Domino assets match. Service, client and Spring Boot packaging pass.
The complete Verrai reactor and Cloudflare host still encounter the Sarto API
alignment issues recorded in the application verification notes.
