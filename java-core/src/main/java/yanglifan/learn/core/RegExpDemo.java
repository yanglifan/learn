package yanglifan.learn.core;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yang Lifan
 */
public class RegExpDemo {

    @Test
    public void group() {
        String value = "{\"data\":{\"remark\":{\"color\":\"#bd9f5e\",\"value\":\"xxx\"},\"keyword5\":{\"color\":\"#000000\",\"value\":\"${group_time}\"},\"keyword1\":{\"color\":\"#000000\",\"value\":\"${group_name}\"},\"first\":{\"color\":\"#bd9f5e\",\"value\":\"xxx\"},\"keyword2\":{\"color\":\"#000000\",\"value\":\"$math.div($group_price,100)\"},\"keyword3\":{\"color\":\"#000000\",\"value\":\"#if(!${group_leader})else${group_leader}#end\"},\"keyword4\":{\"color\":\"#000000\",\"value\":\"${group_lack}\"}},\"template_id\":\"zPlJP5k4-CWBLtPhBum328b9P5OfrewK8LudMjjwfMw\",\"touser\":\"${unionId}\",\"type\":\"1\",\"wechat_id\":\"gh_f206338003a1\",\"touser_type\":\"union_id\",\"url\":\"http://iqiyi.cn/cmxgn\"}";

        Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
