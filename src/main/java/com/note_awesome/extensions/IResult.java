package com.note_awesome.extensions;

import java.util.function.Function;

public interface IResult<TValue, TError> {
    TValue getValue();

    boolean isSuccess();

    TError getError();

    <TResult> TResult Match(Function<TValue, TResult> onSuccess, Function<TError, TResult> onFailure);
}
