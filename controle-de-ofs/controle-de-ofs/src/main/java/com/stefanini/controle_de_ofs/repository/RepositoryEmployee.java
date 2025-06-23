package  com.stefanini.controle_de_ofs.repository;

import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryEmployee extends CrudRepository<Employee, Integer> {

//    @Query(value = "SELECT * FROM employees", nativeQuery = true)
//    ResponseEntity<?> findAllEmployees();

    List<Employee> findByStatus(String status);
    Employee findByUser(User user);

//    @Query(value = "SELECT e.id, e.name, e.nome  FROM Employees WHERE  ", nativeQuery = true)
//    Long findEmployeeByRT();

}