// Original showcase helper; see upstream/showcase-lock.json.
package io.instanto.domino.client;

import java.util.Comparator;
import org.dominokit.domino.ui.datatable.plugins.pagination.SortDirection;
import org.dominokit.domino.ui.datatable.store.RecordsSorter;

public class ContactSorter implements RecordsSorter<Contact> {
  @Override
  public Comparator<Contact> onSortChange(String sortBy, SortDirection sortDirection) {

    if ("firstName".equals(sortBy)) {
      if (SortDirection.ASC.equals(sortDirection)) {
        return Comparator.comparing(Contact::getName);
      } else {
        return (o1, o2) -> o2.getName().compareTo(o1.getName());
      }
    }

    if ("balance".equals(sortBy)) {
      if (SortDirection.ASC.equals(sortDirection)) {
        return Comparator.comparingDouble(Contact::getBalance);
      } else {
        return (o1, o2) -> Double.compare(o2.getBalance(), o1.getBalance());
      }
    }

    if ("id".equals(sortBy)) {
      if (SortDirection.ASC.equals(sortDirection)) {
        return Comparator.comparingDouble(Contact::getIndex);
      } else {
        return (o1, o2) -> Double.compare(o2.getIndex(), o1.getIndex());
      }
    }

    return (o1, o2) -> 0;
  }
}
