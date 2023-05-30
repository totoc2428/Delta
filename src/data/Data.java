package src.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public interface Data {
    public static ArrayList<?> totable(File file) {
        return new ArrayList<>();
    };

    public static HashMap<String, String> toHashMap(File file) {
        return new HashMap<String, String>();
    };
}
