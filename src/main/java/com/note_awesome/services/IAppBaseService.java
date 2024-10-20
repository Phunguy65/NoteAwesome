package com.note_awesome.services;

import java.io.IOException;

public interface IAppBaseService extends IInitializable {
    @Override
    void initialize() throws IOException;

    void refresh();
}
