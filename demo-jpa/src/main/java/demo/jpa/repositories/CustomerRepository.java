package demo.jpa.repositories;


import demo.jpa.dto.CustomerProjection;
import demo.jpa.dto.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.hibernate.jpa.QueryHints.HINT_COMMENT;

public interface CustomerRepository extends JpaRepository<Customers, Long>, JpaSpecificationExecutor<Customers> {

    List<Customers>  findByLastName(String lastName);


    //使用JPQL语言查询
    @QueryHints(value = { @QueryHint(name = HINT_COMMENT, value = "a query for pageable")})
    @Query("select c from Customers c where c.lastName=?1")
    Customers getByLastName(String lastName);

//    //使用JPQL语言查询
//    @Query("select c from Customers c where c.lastName=:map.get('')")
//    Customers getByLastName1(@Param("map") Map map);
    @Query("select c from Customers c where c.lastName=:lastName")
    Customers getByLastName2(@Param("lastName") String lastName);
    //排序
    @Query("select c from Customers c")
    List<Customers> getByLastName3(@Param("lastName") String lastName,Sort sort);
    // 使用原生的sql查询
    @Query(nativeQuery = true,
           value = "select c.first_name,c.id,c.last_name from customers c where c.first_name like concat('%',?1,'%')")
    List<Customers> findByName3(String name);

    // 跟新
//    @Query("update Customers c set c.firstName=?2 where c.firstName=?1")
//    int updateFirstName(String preName,String aftName);
    @Modifying
    @Transactional
    @Query("update Customers c set c.firstName=?2 where c.firstName=?1")
    int updateFirstName(String preName,String aftName);

//    @Query("update customers c set c.first_name=?2 where c.first_name=?1")
//    int updateFirstNameByNative(String preName,String aftName);

    // 分页查询
    @Query("SELECT c from Customers c")
    Page<Customers> findCustomersPage(Pageable pageable);

    @Query("select c.firstName as firstName,c.lastName as lastName from Customers c")
    Collection<CustomerProjection> finAllIntoProjection();
}