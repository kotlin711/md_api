package org.example.api.web.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.api.model.dto.DataCountDto;
import org.example.api.model.dto.UserCountDto;
import org.example.api.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface UserMapper extends BaseMapper<User> {




    @Update("update md_user\n" +
            "set  state=ABS(state - 1)\n" +
            "where id=#{id};")
    void  update_state(String id);

    @Select("select if(count(*)>0,true,false) from md_user where email=#{email}")
    boolean email_isexist(String email);

    @Select("select count(*) as value ,'总数' as name from md_user\n" +
            "union  all\n" +
            "select count(*) as value ,'今日注册' as name from md_user where to_days(reg_time) =to_days(now())\n" +
            "union  all\n" +
            "select count(*) as value ,'VIP用户数' as name from md_user where vip !=-1\n" +
            "union  all\n" +
            "select count(*) as value ,'今日VIP注册' as name from md_user where to_days(reg_time) =to_days(now()) and   vip !=-1;;\n")
    List<DataCountDto> user_count();


    @Select("select date(reg_time) as date, count(*)  as 'value' from md_user\n" +
            "where datediff(curdate(), date(reg_time)) < 7\n" +
            "group by date(reg_time)\n" +
            "order by date(reg_time) desc")
    List<UserCountDto> user_count_sevenday();
}
