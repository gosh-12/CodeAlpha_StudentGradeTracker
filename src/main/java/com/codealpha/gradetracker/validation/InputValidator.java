package com.codealpha.gradetracker.validation;

public final class InputValidator {

    public static final int MIN_MARK = 0;
    public static final int MAX_MARK = 100;

    private InputValidator() {
    }

    public static boolean isValidMark(int mark) {
        return mark >= MIN_MARK && mark <= MAX_MARK;
    }

    public static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
