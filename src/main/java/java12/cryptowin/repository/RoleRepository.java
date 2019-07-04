package java12.cryptowin.repository;

import java12.cryptowin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("from Role r where r.role = :name")
    Role getRoleByName(@Param("name") String name);
}