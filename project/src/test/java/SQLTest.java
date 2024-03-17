
import com.huangTaiQi.www.model.entity.UserEntity;
import com.my_framework.www.db.orm.sql.FieldNameUtil;
import org.junit.Test;


public class SQLTest {



    @Test
    public void testSQLBuilder() throws Exception {
//        String sql=new SQLBuilder("user").insert("email").insert("password")
//                .insert("username").buildInsert();
//         sql=new SQLBuilder("user").select("*").where("email").buildSelect();
//         String sql=new SQLBuilder("question")
//                .count("*").buildSelect();
//        String sql=new SQLBuilder("question")
//                .select("*")
//                .limit(10)
//                .offset(20)
//                .buildSelect();
//        String sql=new SQLBuilder("user")
//                .select("*")
//                .blurWhere("username")
//                .buildSelect();
//        System.out.println(sql);
//        String sql=new SQLBuilder("chat_users A")
//                .select("A.id","A.chatroom_id","A.state","A.uid","A.role","A.logout_at","B.username","B.avatar")
//                .join("user B","A.uid == B.id").where("username").buildSelect();
//        String sql=new SQLBuilder("message m")
//                .select("m.mid","m.uid","u.username","m.content","m.published_at","u.avatar")
//                .join("user u","u.id = m.uid")
//                .where("published_at >")
//                .orderBy("published_at")
//                .buildSelect();
//        String sql=new SQLBuilder("chat_users A")
//                .select("A.chatroom_id")
//                .select("B.name")
//                .join("chatroom B","A.chatroom_id=B.id")
//                .buildSelect();
//        BaseDao baseDao=new BaseDao(DataBaseUtil.getConnection());
//        List<ChatRoom> messages = baseDao.selectByParams(sql, ChatRoom.class);
//        System.out.println(messages);
//        AnswerDao answerDao=new AnswerDao();
//        answerDao.getUserAnswer("1");
//        GenericApplicationContext applicationContext = new GenericApplicationContext("application.properties");
//        CommentServiceImpl commentServiceImpl = (CommentServiceImpl) applicationContext.getBean("commentServiceImpl");
//        commentServiceImpl.getCommentTree("1","asc");
//        String sql=new SQLBuilder("comment")
//                .insert("content")
//                .insert("pid")
//                .insert("top_id")
//                .buildInsert();
//        System.out.println(sql);

//        BaseDao baseDao = new BaseDao(DataBaseUtil.getConnection());
//        Long lastInsertId =baseDao.selectOne("SELECT CAST(LAST_INSERT_ID() AS UNSIGNED) AS id",IIDD.class).id.longValue();
//        System.out.println(lastInsertId);
//        String sql=new SQLBuilder("follow A")
//                .select("A.id","A.user_id","A.followee_id","A.create_time")
//                .join("follow B","A.user_id=B.followee_id","A.followee_id=B.user_id")
//                .buildSelect();
//        System.out.println(sql);
//        String sql=new SQLBuilder("like")
//                .select("*")
//                .where("user_id")
//                .where("answer_id")
//                .buildSelect();
//        System.out.println(sql);
//        LikeDao likeDao = new LikeDao();
//        likeDao.selectLike(1L,"1");
//        LikeDao LikeDao=new LikeDao();
//        LikeDao.addLike(1L,"1");
//        String sql=new SQLBuilder("question")
//                .select("*")
//                .where("title")
//                .whereIn("id",10)
//                .buildSelect();
//        System.out.println(sql);
//        ArrayList<Long> integers = new ArrayList<>();
//        integers.add(1L);
//        integers.add(2L);
//        QuestionDao questionDao=new QuestionDao();
//        questionDao.getQuestionByIds(integers);



        String id= FieldNameUtil.getDBName(UserEntity::getCreateTime);
        System.out.println(id);
    }
}
