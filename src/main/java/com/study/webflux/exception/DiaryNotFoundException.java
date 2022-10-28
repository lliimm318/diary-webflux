package com.study.webflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "diary not found")
public class DiaryNotFoundException extends RuntimeException {
}
