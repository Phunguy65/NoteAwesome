package com.note_awesome.services;

import com.note_awesome.extensions.IInitialize;

import java.io.IOException;

public interface IAppBaseService extends IInitialize {
    @Override
    void initialize() throws IOException;

    void refresh();
}
