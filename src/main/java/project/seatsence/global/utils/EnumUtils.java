package project.seatsence.global.utils;

import static project.seatsence.global.code.ResponseCode.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.ReservationUnit;

public class EnumUtils {

    public static <T extends Enum<T>> String getStringFromEnumList(List<T> enumList) {
        if (enumList == null || enumList.isEmpty()) return null;
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
        if (Category.class.isAssignableFrom(enumClass)) {
            for (T enumConstant : enumClass.getEnumConstants()) {
                if (enumConstant instanceof Category
                        && ((Category) enumConstant).getValue().equalsIgnoreCase(enumString)) {
                    return enumConstant;
                }
            }
        } else if (ReservationUnit.class.isAssignableFrom(enumClass)) {
            for (T enumConstant : enumClass.getEnumConstants()) {
                if (enumConstant instanceof ReservationUnit
                        && ((ReservationUnit) enumConstant)
                                .getValue()
                                .equalsIgnoreCase(enumString)) {
                    return enumConstant;
                }
            }
        }
        throw new BaseException(ENUM_VALUE_NOT_FOUND);
    }
}
