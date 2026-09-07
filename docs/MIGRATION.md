# Migration boundary

The old Verrai modules remain in their existing repositories. They must not be removed until their consumers have tested replacements.

| Existing Verrai API | Original-library direction | Outstanding work |
|---|---|---|
| `io.instanto.verrai.domino.Button` | `org.dominokit.domino.ui.button.Button` | Thin template/DI attachment adapter and consumer regression |
| Text inputs / value interfaces | `org.dominokit.domino.ui.forms.TextBox` and shared value listeners | Binding/validation adapter; APIs are not drop-in compatible |
| `Modal` | Original `dialogs.Dialog` | Map old modal methods and verify application focus assumptions |
| `TableWidget` | Original `datatable.DataTable` with plugins/store | Migrate old row, sort, filter and pagination contracts |
| Calendar and date widgets | Original datepicker widgets | Locale parsing, selection and lifecycle browser tests required first |
| Remaining wrappers | Original counterpart, assessed individually | No broad replacement claim until browser coverage exists |
| Verrai Bootstrap consumers | Standalone `bootstrap-widgets` | Separate API migration and application regressions |

The first tranche proves the compiler boundary with four original components. It does not retire the 53-file Verrai Domino implementation, migrate application templates or claim that the larger original widget catalogue has browser parity. A representative framework application migration is the next milestone after the coverage inventory identifies all its required widgets. Framework-specific adapters belong in that framework's repository.
