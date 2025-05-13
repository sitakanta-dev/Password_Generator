public class Password {
    String value;
    int length;

    public Password(String s) {
        value = s;
        length = s.length();
    }

    public int charType(char c) {
        if (Character.isUpperCase(c)) return 1;
        else if (Character.isLowerCase(c)) return 2;
        else if (Character.isDigit(c)) return 3;
        else return 4; // symbols
    }

    public int passwordStrength() {
        boolean usedUpper = false;
        boolean usedLower = false;
        boolean usedNum = false;
        boolean usedSym = false;

        for (char c : value.toCharArray()) {
            int type = charType(c);
            switch (type) {
                case 1 -> usedUpper = true;
                case 2 -> usedLower = true;
                case 3 -> usedNum = true;
                default -> usedSym = true;
            }
        }

        int score = 0;
        if (usedUpper) score++;
        if (usedLower) score++;
        if (usedNum) score++;
        if (usedSym) score++;
        if (length >= 8) score++;
        if (length >= 16) score++;

        return score;
    }

    public String calculateScore() {
        int score = passwordStrength();
        return switch (score) {
            case 6 -> "This is a very good password :D Check the Useful Information section.";
            case 4, 5 -> "This is a good password :) but you can still do better.";
            case 3 -> "This is a medium password :/ try making it stronger.";
            default -> "This is a weak password :( definitely make a better one.";
        };
    }

    @Override
    public String toString() {
        return value;
    }
}
