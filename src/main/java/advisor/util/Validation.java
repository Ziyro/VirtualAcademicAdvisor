package advisor.util;

public final class Validation {
    private Validation() {}

    private static boolean isBack(String v) {
        return v != null && (v.equalsIgnoreCase("b") || v.equalsIgnoreCase("back"));
    }
    private static void throwIfBack(String v) { if (isBack(v == null ? null : v.trim())) throw new BackToMenu(); }

    public static String requireNonBlank(ConsoleIO io, String prompt) {
        while (true) {
            String v = io.ask(prompt);
            throwIfBack(v);
            if (v != null && !v.trim().isBlank()) return v.trim();
            io.warn("Value cannot be blank. Try again or type B to go back.");
        }
    }

    public static String requireDigits(ConsoleIO io, String prompt) {
        while (true) {
            String v = io.ask(prompt);
            throwIfBack(v);
            if (v != null) {
                v = v.trim();
                if (!v.isBlank() && v.matches("\\d+")) return v;
            }
            io.warn("Please enter digits only (e.g., 1234567), or type B to go back.");
        }
    }

    public static String requireName(ConsoleIO io, String prompt) {
        while (true) {
            String v = io.ask(prompt);
            throwIfBack(v);
            if (v != null) {
                v = v.trim().replaceAll("\\s+", " ");
                if (!v.isBlank() && v.matches("(?i)[A-Za-z][A-Za-z'\\-]*(?:\\s+[A-Za-z][A-Za-z'\\-]*)+")) return v;
            }
            io.warn("Please enter a first and last name (letters only; - and ' allowed). Type B to go back.");
        }
    }

    public static double askDoubleInRange(ConsoleIO io, String prompt, double min, double max) {
        while (true) {
            String s = io.ask(prompt);
            throwIfBack(s);
            try {
                double v = Double.parseDouble(s.trim());
                if (v < min || v > max) throw new IllegalArgumentException();
                return v;
            } catch (Exception e) {
                io.warn("Enter a number in [" + min + ", " + max + "], or type B to go back.");
            }
        }
    }
}
