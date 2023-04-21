import com.huangTaiQi.www.utils.ScanText;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import org.junit.Test;



public class StringTest {
    @Test
    public void testToken(){
//        String substring = "token:382364b9-6321-49dc-a2fb-0b4eb5d7d686".substring("token:382364b9-6321-49dc-a2fb-0b4eb5d7d686".lastIndexOf(':')+1);
//        System.out.println(substring);
//        Map<Integer,String> map=new HashMap<>(1);
//        map.put(1,"123");
//        map.put(1,"123");
//        map.put(1,"123");
//        map.put(1,"123");
//        map.put(1,"1456");
//        System.out.println(map.get(1));
        String s="dashbjkdlansbdkhjskalndjbkhhasojiopdkaanjbkjsckjkad";
        String p="ojiopdk";
        int i = ScanText.kmpSearch(s, p);
        System.out.println(i);
        String replace = s.replace(p, "123");
        System.out.println(replace);
    }
}
