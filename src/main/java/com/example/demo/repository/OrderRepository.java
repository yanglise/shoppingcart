package com.example.demo.repository;

import com.example.demo.domain.Order;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    /**
     * Fetch all orders by session id sorted by the creation date
     *
     * @param sessionId the http session ID
     * @return list of {@link Order} objects
     */
    List<Order> findAllBySessionIdOrderByCreatedDesc(String sessionId);
}
