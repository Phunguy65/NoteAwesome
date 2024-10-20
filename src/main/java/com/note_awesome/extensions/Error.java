package com.note_awesome.extensions;

public record Error(String message, String Description) {
    public static Error none() {
        return new Error("", "");
    }
}
