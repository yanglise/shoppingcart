package com.example.demo.repository;

import com.example.demo.domain.Cart;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    /**
     * Find shopping cart by http session ID
     *
     * @param sessionId the http session ID from client
     * @return the {@link Cart} object wrapped in {@link Optional}
     */
    Optional<Cart> findBySessionId(String sessionId);
}
