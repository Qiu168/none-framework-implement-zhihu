import com.huangTaiQi.www.utils.sql.SQLBuilder;
import org.junit.Test;

public class SQLTest {
    @Test
    public void testSQLBuilder(){
        String sql=new SQLBuilder("user").insert("email").insert("password")
                .insert("username").buildInsert();
         sql=new SQLBuilder("user").select("*").where("email").buildSelect();
        System.out.println(sql);
    }
}
