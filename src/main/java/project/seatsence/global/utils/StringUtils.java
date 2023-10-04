package project.seatsence.global.utils;

public class StringUtils {

    public static String makeRandomString() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 62);
            if (random < 10) {
                randomString.append(random);
            } else if (random > 35) {
                randomString.append((char) (random + 61));
            } else {
                randomString.append((char) (random + 55));
            }
        }
        return randomString.toString();
    }
}
