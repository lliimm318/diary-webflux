package com.study.webflux.controller;

import com.study.webflux.domain.service.Diary.DiaryService;
import com.study.webflux.payload.request.DiaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public Mono CreateDiary(@Valid @RequestBody DiaryRequest request) {
        return diaryService.createDiary(request);
    }

    @PutMapping("/{uuid}")
    public Mono UpdateDiary(@PathVariable UUID uuid,
                            @Valid @RequestBody DiaryRequest request) {
        return diaryService.updateDiary(uuid, request);
    }

    @DeleteMapping("/{uuid}")
    public Mono DeleteDiary(@PathVariable UUID uuid) {
        return diaryService.deleteDiary(uuid);
    }

    @GetMapping("/my")
    public Flux getMyDiaryList() {
        return diaryService.getMyDiaryList();
    }

    @GetMapping
    public Flux getDiaryList() {
        return diaryService.getDiaryList();
    }

    @GetMapping("/{uuid}")
    public Mono getMyDiary(@PathVariable UUID uuid) {
        return diaryService.getMyDiary(uuid);
    }

    @GetMapping("/my/{uuid}")
    public Mono getDiary(@PathVariable UUID uuid) {
        return diaryService.getDiary(uuid);
    }

}
