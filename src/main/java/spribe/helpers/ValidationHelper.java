package spribe.helpers;

public class ValidationHelper {
    public static boolean isNotNullAndNotBlank(Object o) {
        return o != null && !o.toString().isBlank();
    }
}
