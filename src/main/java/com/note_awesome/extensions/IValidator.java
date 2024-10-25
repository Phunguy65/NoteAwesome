package com.note_awesome.extensions;

public interface IValidator<TValue, TError> {
    public Result<TValue, TError> validate(TValue value);
}
