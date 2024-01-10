import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.my_framework.www.core.context.Impl.GenericApplicationContext;
import org.junit.Test;

public class IOCBeanTest {
    public GenericApplicationContext applicationContext=new GenericApplicationContext("application.properties","com.huangTaiQi.www");


    @Test
    public void test(){
        UserServiceImpl userService =(UserServiceImpl) applicationContext.getBean("userServiceImpl");
        System.out.println(userService);
    }
}
