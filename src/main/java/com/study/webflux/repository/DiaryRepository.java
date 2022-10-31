package com.study.webflux.repository;

import com.study.webflux.entity.Diary;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface DiaryRepository extends ReactiveCrudRepository<Diary, UUID> {

    Flux<Diary> findByWriterOrderByDate(String writer);

    Flux<Diary> findByLockedIsFalseOrderByDate();

    Mono<Diary> findByIdAndLockedIsFalse(UUID uuid);

}
