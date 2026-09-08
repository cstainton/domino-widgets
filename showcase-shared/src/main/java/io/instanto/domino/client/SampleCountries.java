package io.instanto.domino.client;

import elemental2.core.Global;
import elemental2.core.JsArray;
import java.util.ArrayList;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

/** Decode the pinned showcase JSON without its Jackson annotation processor. */
public final class SampleCountries {
  public static Countries create() {
    JsPropertyMap<Object> json = Js.cast(Global.JSON.parse(Countries.COUNTRIES));
    JsArray<JsPropertyMap<Object>> records = Js.cast(json.get("countries"));
    ArrayList<Country> countries = new ArrayList<>();
    for (int i = 0; i < records.length; i++) {
      var data = records.getAt(i);
      Country country = new Country();
      country.setName(Js.asString(data.get("name")));
      JsArray<String> names = Js.cast(data.get("cities"));
      ArrayList<String> cities = new ArrayList<>();
      for (int j = 0; j < names.length; j++) cities.add(names.getAt(j));
      country.setCities(cities);
      countries.add(country);
    }
    Countries result = new Countries();
    result.setCountries(countries);
    return result;
  }
}
