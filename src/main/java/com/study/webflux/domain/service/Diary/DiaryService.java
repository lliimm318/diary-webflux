package com.study.webflux.domain.service.Diary;

import com.study.webflux.entity.Diary;
import com.study.webflux.payload.request.DiaryRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DiaryService {

    Mono<Diary> createDiary(DiaryRequest diaryRequest);

    Mono<Diary> updateDiary(UUID uuid, DiaryRequest diaryRequest);

    Mono<Diary> deleteDiary(UUID uuid);

    Flux<Diary> getMyDiaryList();

    Flux<Diary> getDiaryList();

    Mono<Diary> getMyDiary(UUID uuid);

    Mono<Diary> getDiary(UUID uuid);

}
