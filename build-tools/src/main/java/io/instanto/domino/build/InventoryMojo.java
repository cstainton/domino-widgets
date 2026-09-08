package io.instanto.domino.build;

import static io.instanto.domino.build.BuildFiles.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.maven.plugins.annotations.*;

/** Inventory external field and method references from JVM constant pools. */
@Mojo(name = "member-inventory", defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
public final class InventoryMojo extends BuildMojo {
  @Override
  protected void run() throws Exception {
    Path classes = root().resolve("domino-widgets-teavm/target/classes");
    Map<List<String>, Set<String>> refs =
        new TreeMap<>(Comparator.comparing(key -> String.join("\0", key)));
    for (Path path : files(classes))
      if (path.toString().endsWith(".class")) {
        for (var ref : references(path))
          refs.computeIfAbsent(ref, key -> new TreeSet<>())
              .add("domino-widgets-teavm/" + classes.relativize(path));
      }
    require(!refs.isEmpty(), "No compiled widget references; build the widget module first");
    List<Map<String, Object>> rows = new ArrayList<>();
    for (var ref : refs.entrySet()) {
      Map<String, Object> row = new LinkedHashMap<>();
      var names = List.of("kind", "owner", "member", "descriptor");
      for (int i = 0; i < names.size(); i++) row.put(names.get(i), ref.getKey().get(i));
      row.put("callers", ref.getValue());
      rows.add(row);
    }
    json(root().resolve("reports/member-inventory.json"), rows);
    getLog().info(refs.size() + " distinct external member references");
  }

  static List<List<String>> references(Path path) throws Exception {
    try (var data = new DataInputStream(Files.newInputStream(path))) {
      require(data.readInt() == 0xCAFEBABE, "Invalid class file: " + path);
      data.readUnsignedShort();
      data.readUnsignedShort();
      Object[] pool = new Object[data.readUnsignedShort()];
      for (int i = 1; i < pool.length; i++) {
        int tag = data.readUnsignedByte();
        switch (tag) {
          case 1 -> pool[i] = data.readUTF();
          case 7, 8, 16, 19, 20 -> pool[i] = data.readUnsignedShort();
          case 9, 10, 11, 12, 17, 18 ->
              pool[i] = new int[] {tag, data.readUnsignedShort(), data.readUnsignedShort()};
          case 3, 4 -> data.readInt();
          case 5, 6 -> {
            data.readLong();
            i++;
          }
          case 15 -> {
            data.readUnsignedByte();
            data.readUnsignedShort();
          }
          default -> throw new IOException("Unknown constant pool tag " + tag);
        }
      }
      List<List<String>> refs = new ArrayList<>();
      for (Object entry : pool)
        if (entry instanceof int[] ref && ref[0] >= 9 && ref[0] <= 11) {
          String owner = (String) pool[(Integer) pool[ref[1]]];
          if (List.of(
                  "elemental2/", "jsinterop/", "org/gwtproject/", "org/slf4j/", "com/google/gwt/")
              .stream()
              .anyMatch(owner::startsWith)) {
            int[] name = (int[]) pool[ref[2]];
            refs.add(
                List.of(
                    ref[0] == 9 ? "field" : "method",
                    owner.replace('/', '.'),
                    (String) pool[name[1]],
                    (String) pool[name[2]]));
          }
        }
      return refs;
    }
  }
}
