package org.openblend.fejstbuk.domain;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    private Gender(String label) {
      this.label = label;
    }

    public String getLabel() {
      return this.label;
    }
}
