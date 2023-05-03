import com.my_framework.www.redis.JedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisPoolTest {
    @Test
    public void testJedis(){
        Jedis jedis = JedisUtils.getJedis();
        jedis.set("name","jack");
//        jedis.hmset("sad", BeanUtil.beanToMap(jedis));
//        jedis.expire("sads",222);
    }
}
