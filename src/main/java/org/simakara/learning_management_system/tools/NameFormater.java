package org.simakara.learning_management_system.tools;

public class NameFormater {

    public static String format(String anyName) {
        char[] chars = anyName
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase()
                .toCharArray();

        chars[0] = Character.toTitleCase(chars[0]);

        for (int i = 1; i < chars.length; i++) {
            if (Character.isWhitespace(chars[i-1]) && Character.isAlphabetic(chars[i])) {
                chars[i] = Character.toTitleCase(chars[i]);
            }
        }

        return String.valueOf(chars);
    }
}
