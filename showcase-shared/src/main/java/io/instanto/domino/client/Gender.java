// Original showcase helper; see upstream/showcase-lock.json.
package io.instanto.domino.client;

public enum Gender {
    female("Female"), male("Male");

    private String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
