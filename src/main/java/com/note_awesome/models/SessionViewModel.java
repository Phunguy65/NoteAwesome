package com.note_awesome.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class SessionViewModel implements ISessionViewModel {

    private LongProperty currentUserId;
    private ObservableList<LongProperty> usrProfIds;
    private LongProperty currentUsrProfId;

    public SessionViewModel() {
        currentUserId = new SimpleLongProperty();
        usrProfIds = FXCollections.observableArrayList();
        currentUsrProfId = new SimpleLongProperty();
    }

    @Override
    public LongProperty getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId.set(currentUserId);
    }

    @Override
    public ObservableList<LongProperty> getUsrProfIds() {
        return usrProfIds;
    }

    public void setUsrProfIds(ObservableList<LongProperty> usrProfIds) {
        this.usrProfIds = usrProfIds;
    }

    @Override
    public LongProperty getCurrentUsrProfId() {
        return currentUsrProfId;
    }

    public void setCurrentUsrProfId(long currentUsrProfId) {
        this.currentUsrProfId.set(currentUsrProfId);
    }

}

