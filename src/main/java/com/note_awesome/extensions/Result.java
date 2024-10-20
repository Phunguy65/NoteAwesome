package com.note_awesome.extensions;

import java.util.Optional;
import java.util.function.Function;

public class Result<TValue, TError> implements IResult<TValue, TError> {
    private final TValue value;
    private final TError error;

    private Result(TValue value, TError error) {
        this.value = value;
        this.error = error;
    }

    public static <TValue, TError> Result<TValue, TError> success(TValue value) {
        return new Result<>(value, null);
    }

    public static <TValue, TError> Result<TValue, TError> failure(TError error) {
        return new Result<>(null, error);
    }

    @Override
    public TValue getValue() {
        return value;
    }

    @Override
    public boolean isSuccess() {
        return value != null;
    }

    @Override
    public TError getError() {
        return error;
    }
    
    @Override
    public <TResult> TResult Match(Function<TValue, TResult> onSuccess, Function<TError, TResult> onFailure) {
        return isSuccess() ? onSuccess.apply(value) : onFailure.apply(error);
    }

}
