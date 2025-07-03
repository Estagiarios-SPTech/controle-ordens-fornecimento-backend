package  com.stefanini.controle_de_ofs.repository;


import com.stefanini.controle_de_ofs.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryUser extends CrudRepository<User, Integer> {

    List<User> findAll();

    int countById(int codigo);

    List <User> findByRole(String role);

    @Query(value = "SELECT id, name, email, role FROM user WHERE role = 'Gerente'", nativeQuery = true)
    List <User> findManagers();

    @Query(value = "SELECT id, name, email, role FROM user WHERE role = 'Gerente' AND name = :name", nativeQuery = true)
    User findSpecificManager(String name);

    @Query(value = "SELECT id, name, email, role FROM user WHERE role = 'RT' AND name = :name", nativeQuery = true)
    User findSpecificRT(String name);
}

