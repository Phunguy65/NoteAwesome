module com.note_awesome {
    requires java.compiler;
    requires transitive org.controlsfx.controls;
    requires com.h2database;
    requires java.sql;
    requires jakarta.persistence;
    requires spring.context;
    requires spring.orm;
    requires spring.core;
    requires spring.jdbc;
    requires spring.boot;
    requires spring.beans;
    requires spring.data.jpa;
    requires spring.tx;
    requires org.hibernate.orm.core;
    requires org.hibernate.orm.envers;
    requires spring.data.commons;
    requires spring.boot.autoconfigure;
    requires org.antlr.antlr4.runtime;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires org.fxmisc.flowless;
    requires org.scenicview.scenicview;
    requires javafx.web;
    requires javafx.swing;
    requires spring.security.crypto;

    opens com.note_awesome to javafx.fxml, spring.core;
    exports com.note_awesome;

    opens com.note_awesome.views to javafx.fxml;
    exports com.note_awesome.views;

    opens com.note_awesome.core.repositories.note to spring.core;
    exports com.note_awesome.core.repositories.note;

    opens com.note_awesome.core.entities.note to org.hibernate.orm.core, spring.core;
    exports com.note_awesome.core.entities.note;

    exports com.note_awesome.core.entities;
    opens com.note_awesome.core.entities to org.hibernate.orm.core, spring.core;

    exports com.note_awesome.services;
    opens com.note_awesome.services to spring.core;

    exports com.note_awesome.extensions;

    exports com.note_awesome.controllers;
    opens com.note_awesome.controllers to javafx.fxml, spring.core;
    exports com.note_awesome.views.core_editors;
    opens com.note_awesome.views.core_editors to javafx.fxml;
    exports com.note_awesome.views.note_views;
    opens com.note_awesome.views.note_views to javafx.fxml;

}