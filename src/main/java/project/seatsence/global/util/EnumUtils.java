package project.seatsence.global.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumUtils {

    public static <T extends Enum<T>> String getStringFromEnumList(List<T> enumList) {
        return enumList.stream().map(Enum::name).collect(Collectors.joining(", "));
    }

    public static <T extends Enum<T>> List<T> getEnumListFromString(
            String enumListAsString, Class<T> enumClass) {
        return Arrays.stream(enumListAsString.split(","))
                .map(String::trim)
                .map(name -> Enum.valueOf(enumClass, name))
                .collect(Collectors.toList());
    }

    public static <T extends Enum<T>> T getEnumFromString(String enumString, Class<T> enumClass) {
        return Enum.valueOf(enumClass, enumString);
    }
}
