package demo.jpa;

import demo.jpa.dto.CustomerProjection;
import demo.jpa.dto.Customers;
import demo.jpa.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @Author liaozhenglong
 * @Date 2020/4/29 11:17
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATest {


    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void testSave(){
        customerRepository.save(new Customers("Jack", "Bauer"));
        customerRepository.save(new Customers("Chloe", "O'Brian"));
        customerRepository.save(new Customers("Kim", "Bauer"));
        customerRepository.save(new Customers("David", "Palmer"));
        customerRepository.save(new Customers("Michelle", "Dessler"));
        customerRepository.save(new Customers("Bauer", "Dessler"));
    }
    
    @Test
    public void testFindAll(){
        List<Customers> customers = customerRepository.findAll();
        for (Customers customer : customers) {
            System.out.println(customer.toString());
        }
        Customers customers1 = customerRepository.findById(1l).get();
        System.out.println("-------------------------------------------");
        System.out.println(customers1.toString());
        System.out.println("===================getByLastName==================");
        Customers palmer = customerRepository.getByLastName("Palmer");
        System.out.println("plamer=="+palmer.toString());
    }

    @Test
    public void testByAuto(){
        List<Customers> list = customerRepository.findByLastName("Dessler");
        for (Customers customers : list) {
            System.out.println(customers.toString());
        }

        //测试使用PJQL语言的查询
        System.out.println("-------------------getByLastName3----------------------------");
        List<Customers> palmer = customerRepository.getByLastName3("Palmer",new Sort(Sort.Direction.DESC,"id"));
        for (Customers customers : palmer) {
            System.out.println(customers.toString());
        }

        // 使用原生的sql查询数据
        System.out.println("++++++++++++++++++++++++findByName3+++++++++++++++++++++++++++");
        List<Customers> list1 = customerRepository.findByName3("e");
        for (Customers customers : list1) {
            System.out.println(customers.toString());
        }
    }

    @Test
    public void testUpdate(){
//        int i = customerRepository.updateFirstNameByNative("jon", "Jack");
//        System.out.println("ii=="+i);
    }

    @Test
    public void teatfindCustomersPage(){
        Page<Customers> customersPage = customerRepository.findCustomersPage(new PageRequest(0, 2));
        System.out.println(customersPage.getTotalElements()+"--------------");
        System.out.println(customersPage.getTotalPages()+"+++++++++++++++++");
        for (Customers customers : customersPage.getContent()) {
            System.out.println(customers.toString());
        }
    }

    @Test
    public void testFinAllIntoProjection(){
        Collection<CustomerProjection> projections = customerRepository.finAllIntoProjection();
        for (CustomerProjection projection : projections) {
            System.out.println("getFull--->"+projection.getFullName());
            System.out.println("getFirst-->"+projection.getFirstName());
            System.out.println("getLast-->"+projection.getLastName());
            System.out.println("-----------------------------------------------------");
        }
    }
}
