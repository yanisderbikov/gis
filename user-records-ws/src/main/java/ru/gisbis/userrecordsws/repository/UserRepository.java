package ru.gisbis.userrecordsws.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.gisbis.userrecordsws.entity.UserEntity;

public interface UserRepository extends ReactiveCrudRepository<UserEntity,Long> {

    Mono<UserEntity> findByName(String name);
}
