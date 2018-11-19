package com.example.jyl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.example.jyl.dao.User;
import com.example.jyl.service.UserService;

@RestController
public class SampleController {
	@Autowired
	private UserService userService;
	private int num=0;
	
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
    
    
    
    @ResponseBody
    @RequestMapping("/page")
    public Object selectPage(Model model){

        Page page=new Page(1,10);
        page = userService.selectUserPage(page, "NORMAL");
        return page;
    }
    
    
    
    /**
     * 分页 PAGE
     */
    @GetMapping("/test")
    public Page<User> test() {
        return userService.selectPage(new Page<User>(0, 2));
    }
    
//    /**
//     * AR 部分测试
//     */
//    @GetMapping("/test1")
//    public Page<User> test1() {
//        User user = new User();
//        System.err.println("删除所有：" + user.delete(null));
//        //user.setId(2017091801L);
//        user.setAccout("test"+num++);
//        user.setType("test");
//        user.setCreateTime(new Date());
//        user.setPhone("13111110000");
//        user.setPassword("123456");
//        user.setNickname("guangqing"+2*num++);
//        user.insert();
//        System.err.println("查询插入结果：" + user.selectById().toString());
//        //user.setNickname("mybatis-plus-ar");
//        System.err.println("更新：" + user.updateById());
//        return user.selectPage(new Page<User>(0, 12), null);
//    }

    /**
     * 增删改查 CRUD
     */
    @GetMapping("/test2")
    public User test2() {
        User user = new User();
        user.setId(123456L);
        user.setAccout("test");
        user.setType("test");
        user.setCreateTime(new Date());
        user.setPhone("13111110000");
        user.setPassword("123456");
        user.setNickname("guangqing");
        System.err.println("删除一条数据：" + userService.deleteById(1L));
        System.err.println("插入一条数据：" + userService.insert(user));
        User user2 = new User();
        user.setId(223456L);
        user.setAccout("test2");
        user.setType("test");
        user.setCreateTime(new Date());
        user.setPhone("13111110000");
        user.setPassword("123456");
        user.setNickname("guangqing");
        boolean result = userService.insert(user);
        // 自动回写的ID
        Long id = user.getId();
        System.err.println("插入一条数据：" + result + ", 插入信息：" + user.toString());
        System.err.println("查询：" + userService.selectById(id).toString());
        Page<User> userListPage = userService.selectPage(new Page<User>(1, 5), new EntityWrapper<>(new User()));
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());
        return userService.selectById(1L);
    }

/*    @GetMapping("testSelect")
    public Object testSelect() {
        Integer start = 0;
        Integer length =10;
        User param = new User();
        //param.setNickname("guangqing2");
        Integer pageNo=getPageNo(start,length);
        Page<User> page =new Page<User>(pageNo,length);
        EntityWrapper<User> ew = new EntityWrapper<User>();
        ew.setEntity(param);
        ew.where("password={0}","123456")
                .like("nickname","guangqing")
                .ge("create_time","2017-09-21 15:50:00");
        userService.selectPage(page, ew);
        DatatablesJSON<User> resJson= new DatatablesJSON<>();
        //resJson.setDraw(draw++);
        resJson.setRecordsTotal(page.getTotal());
        resJson.setRecordsFiltered(page.getTotal());
        resJson.setData(page.getRecords());
        return resJson;
    }*/


/*    @GetMapping("/selectsql")
    public Object getUserBySql() {
        JSONObject result = new JSONObject();
        result.put("records", userService.selectListBySQL());
        return result;
    }*/

    /**
     * 7、分页 size 一页显示数量  current 当前页码
     * 方式一：http://localhost:8080/user/page?size=1&current=1<br>
     * 方式二：http://localhost:8080/user/pagehelper?size=1&current=1<br>
     */

    // 参数模式分页
    @GetMapping("/page")
    public Object page(Page page) {
        return userService.selectPage(page);
    }

    // ThreadLocal 模式分页
    @GetMapping("/pagehelper")
    public Object pagehelper(Page page) {
        PageHelper.setPagination(page);
        page.setRecords(userService.selectList(null));
        page.setTotal(PageHelper.freeTotal());//获取总数并释放资源 也可以 PageHelper.getTotal()
        return page;
    }


    /**
     * 测试事物
     * http://localhost:8080/user/test_transactional<br>
     * 访问如下并未发现插入数据说明事物可靠！！<br>
     * http://localhost:8080/user/test<br>
     * <br>
     * 启动  Application 加上 @EnableTransactionManagement 注解其实可无默认貌似就开启了<br>
     * 需要事物的方法加上 @Transactional 必须的哦！！
     */
    @Transactional
    @GetMapping("/test_transactional")
    public void testTransactional() {
        //userService.insert(new User(1000L, "测试事物", 16, 3));
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }
}
