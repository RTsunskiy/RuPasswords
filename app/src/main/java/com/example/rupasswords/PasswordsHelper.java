package com.example.rupasswords;

import android.widget.ImageView;

import java.security.SecureRandom;

public class PasswordsHelper {

    private final String[] russians;
    private final String[] latins;
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_SYMBOLS = "!@#$%^&*()_+-=№;%:?";
    private static SecureRandom random = new SecureRandom();

    PasswordsHelper(String[] russians, String[] latins) {
        this.russians = russians;
        this.latins = latins;

        if (russians.length != latins.length) {
            throw new IllegalArgumentException();
        }
    }

    String convert(CharSequence source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            boolean t = false;
            char c = source.charAt(i);
            String ch = String.valueOf(c);
            for (int j = 0; j < russians.length; j++) {
                if (russians[j].equals(ch)) {
                    result.append(latins[j]);
                    t = true;
                    break;
                }
            }
            if (!t) {
                result.append(ch);
            }
        }

        return result.toString();
    }

    String getRating(int length, ImageView reliabilityIndicator) {
        if (length == 0) {
            reliabilityIndicator.setImageLevel(0);
            return "Сложность пароля";
        } else if (length >= 1 && length <= 2) {
            reliabilityIndicator.setImageLevel(1000);
            return "Совсем плохой";
        } else if (length >= 3 && length <= 4) {
            reliabilityIndicator.setImageLevel(2000);
            return "Не совсем плохой";
        } else if (length >= 5 && length <= 7) {
            reliabilityIndicator.setImageLevel(3000);
            return "Плохой, но сойдет";
        } else if (length >= 8 && length <= 10) {
            reliabilityIndicator.setImageLevel(4000);
            return "С пивом сойдет";
        } else if (length >= 11 && length <= 12) {
            reliabilityIndicator.setImageLevel(5000);
            return "И без пива сойдет";
        } else if (length >= 13 && length <= 16) {
            reliabilityIndicator.setImageLevel(6000);
            return "Нравится пароль";
        } else {
            reliabilityIndicator.setImageLevel(7000);
            return "Мега пароль";
        }
    }

    String generatePassword(int length, boolean caps, boolean numbers, boolean special) {

        StringBuilder DATA_FOR_RANDOM_STRING = new StringBuilder(CHAR_LOWER);
        if (caps) {
            DATA_FOR_RANDOM_STRING.append(CHAR_UPPER); }
        if (numbers) {
            DATA_FOR_RANDOM_STRING.append(NUMBERS); }
        if (special) {
            DATA_FOR_RANDOM_STRING.append(SPECIAL_SYMBOLS); }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.toString().length());
            char rndChar = DATA_FOR_RANDOM_STRING.toString().charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }
}
