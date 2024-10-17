package com.note_awesome.extensions;

import java.util.function.Function;

public class Result<TValue> {

    private final TValue value;
    private final Error error;
    private final boolean isSuccess;

    private Result(TValue value) {
        this.value = value;
        this.error = Error.none();
        this.isSuccess = true;
    }

    private Result(Error error) {
        this.value = null;
        this.error = error;
        this.isSuccess = false;
    }


    public static <TValue> Result<TValue> success(TValue value) {
        return new Result<>(value);
    }

    public static <TValue> Result<TValue> failure(String message, String description) {
        return new Result<>(new Error(message, description));
    }

    public static <TValue> Result<TValue> failure(Error error) {
        return new Result<>(error);
    }

    public <TResult> TResult Match(Function<TValue, TResult> onSuccess, Function<Error, TResult> onFailure) {
        return isSuccess ? onSuccess.apply(value) : onFailure.apply(error);
    }
}

