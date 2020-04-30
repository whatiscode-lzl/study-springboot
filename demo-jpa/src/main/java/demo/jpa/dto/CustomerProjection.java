package demo.jpa.dto;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author liaozhenglong
 * @Date 2020/4/29 16:12
 **/
// 查询结果类
public interface CustomerProjection {

    @Value("#{target.firstName+'_'+target.lastName}")
    String getFullName();

    String getFirstName();

    String getLastName();
}
