import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class StringTest {
    private void add(Integer integer){
        integer=integer+1;
    }
    private void delete(Map<Integer,String> map){
        map.remove(1);
    }
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
//        String s="dashbjkdlansbdkhjskalndjbkhhasojiopdkaanjbkjsckjkad";
//        String p="ojiopdk";
//        int i = ScanText.kmpSearch(s, p);
//        System.out.println(i);
//        String replace = s.replace(p, "123");
//        System.out.println(replace);
//        System.out.println("当前工作目录是：" + System.getProperty("user.dir"));

//        Method[] method= UserControllerImpl.class.getMethods();
//        for (Method method1 : method) {
//            System.out.println(method1.getParameters()[0].getName());
//        }
        Integer i=1;
        add(i);
        System.out.println(i);
        Map<Integer,String> map=new HashMap<>();
        map.put(1,"1");
        map.put(2,"2");
        System.out.println(map);
        delete(map);
        System.out.println(map);
    }
}
