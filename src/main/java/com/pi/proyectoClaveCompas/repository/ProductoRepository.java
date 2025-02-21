/* package com.pi.proyectoClaveCompas.repository;

import com.pi.proyectoClaveCompas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = "SELECT * FROM productos ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Producto> findRandomProducts();

    // @Query(value = "SELECT * FROM productos ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
    // List<Producto> findRandomProducts();

}
*/