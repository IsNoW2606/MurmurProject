package orgl.utility;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagsFinder {
    public static Collection<String> findTag(String message) {
        Matcher matcher = Pattern.compile("#[a-zA-Z0-9.]{5,20}").matcher(message);
        Collection<String> tags = new HashSet<>();
        while (matcher.find()) {
            tags.add(matcher.group());
        }
        return tags;
    }
}
