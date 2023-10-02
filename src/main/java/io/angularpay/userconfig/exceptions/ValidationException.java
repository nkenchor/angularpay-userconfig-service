package io.angularpay.userconfig.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ValidationException extends Exception {
    private final List<ErrorObject> errors;
}
