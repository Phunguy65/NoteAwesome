package com.note_awesome.models;

import javafx.beans.property.LongProperty;
import javafx.collections.ObservableList;

public interface ISessionViewModel {
    public LongProperty getCurrentUserId();

    public LongProperty getCurrentUsrProfId();

    public ObservableList<LongProperty> getUsrProfIds();
}
