import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExceptionTest {
    class a{
        public void f() throws ArithmeticException{
            System.out.println(1/0);
        }
    }
    @Test
    public void testException(){
        a a=new a();

        Method method= null;
        try {
            method = ExceptionTest.a.class.getMethod("f");
            method.invoke(a);
        } catch (Exception e){
            if(e instanceof ArithmeticException){
                System.out.println("success");
            }
        }


    }


}
