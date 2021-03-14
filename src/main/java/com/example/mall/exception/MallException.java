package com.example.mall.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ZY
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MallException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
