package ru.gisbis.userrecordsws.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gisbis.userrecordsws.entity.RecordEntity;

public interface RecordRepository extends R2dbcRepository<RecordEntity, Long> {


    Flux<RecordEntity> findAllByUserId(Long userId);


}
