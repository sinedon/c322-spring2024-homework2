package huydpham.Repository;

import huydpham.Model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
    Customer findByUsername(String username);
}