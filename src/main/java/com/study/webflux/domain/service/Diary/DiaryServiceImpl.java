package com.study.webflux.domain.service.Diary;

import com.study.webflux.security.auth.AuthFacade;
import com.study.webflux.entity.Diary;
import com.study.webflux.exception.DiaryNotFoundException;
import com.study.webflux.exception.InvalidTokenException;
import com.study.webflux.exception.UserNotFoundException;
import com.study.webflux.payload.request.DiaryRequest;
import com.study.webflux.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final AuthFacade authFacade;

    @Override
    public Mono<Diary> createDiary(DiaryRequest diaryRequest) {
        final var diary = Diary.from(diaryRequest.getTitle(),
                diaryRequest.getContent(),
                authFacade.getUserName(),
                diaryRequest.getWeather(),
                diaryRequest.getIsLocked());

        return diaryRepository.save(diary);
    }

    @Override
    public Mono<Diary> updateDiary(UUID uuid, DiaryRequest diaryRequest) {
        return diaryRepository.findById(uuid)
                .flatMap(diary -> {
                    diary.setTitle(diaryRequest.getTitle());
                    diary.setContent(diaryRequest.getContent());
                    diary.setWeather(diary.getWeather());
                    diary.setLocked(diaryRequest.getIsLocked());

                    return diaryRepository.save(diary);
                })
                .switchIfEmpty(Mono.error(DiaryNotFoundException::new));
    }

    @Override
    public Mono<Diary> deleteDiary(UUID uuid) {
        return diaryRepository.findById(uuid)
                        .flatMap(diary -> diaryRepository.delete(diary)
                        .switchIfEmpty(Mono.error(DiaryNotFoundException::new))
                        .then(Mono.just(diary)));
    }

    @Override
    public Flux<Diary> getMyDiaryList() {
        return diaryRepository.findByWriterOrderByDate(authFacade.getUserName());
    }

    @Override
    public Flux<Diary> getDiaryList() {
        return diaryRepository.findByLockedIsFalseOrderByDate();
    }

    @Override
    public Mono<Diary> getMyDiary(UUID uuid) {
        return diaryRepository.findById(uuid)
                .flatMap(diary -> {
                    if (Objects.equals(diary.getWriter(), authFacade.getUserName())) {
                        return Mono.just(diary);
                    } else {
                        return Mono.error(InvalidTokenException::new);
                    }
                })
                .switchIfEmpty(Mono.error(DiaryNotFoundException::new));
    }

    @Override
    public Mono<Diary> getDiary(UUID uuid) {
        return diaryRepository.findByIdAndLockedIsFalse(uuid)
                .flatMap(diary -> {
                    if (diary.getLocked()) {
                        return Mono.error(UserNotFoundException::new);
                    } else {
                        return Mono.just(diary);
                    }
                })
                .switchIfEmpty(Mono.error(DiaryNotFoundException::new));
    }
}
