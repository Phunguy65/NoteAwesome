module com.note_awesome {
    requires java.compiler;
    requires transitive org.controlsfx.controls;
    requires com.h2database;
    requires jakarta.persistence;
    requires spring.context;
    requires spring.orm;
    requires spring.core;
    requires spring.jdbc;
    requires spring.boot;
    requires spring.beans;
    requires spring.data.jpa;
    requires spring.tx;
    requires spring.aop;
    requires org.hibernate.orm.core;
    requires org.hibernate.orm.envers;
    requires spring.data.commons;
    requires spring.boot.autoconfigure;
    requires org.antlr.antlr4.runtime;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires org.fxmisc.flowless;
    requires spring.security.crypto;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.materialicons;
    requires org.apache.commons.io;
    opens com.note_awesome to javafx.fxml, spring.core;
    exports com.note_awesome;
    requires transitive javafx.web;
    requires transitive javafx.swing;
    opens com.note_awesome.views to javafx.fxml;
    exports com.note_awesome.views;
    requires org.scenicview.scenicview;
    requires org.apache.logging.log4j;
    requires org.slf4j;


    opens com.note_awesome.core.repositories.note to spring.core, spring.context, spring.beans;
    exports com.note_awesome.core.repositories.note;

    opens com.note_awesome.core.entities.note to org.hibernate.orm.core, spring.core, spring.context, spring.beans;
    exports com.note_awesome.core.entities.note;

    exports com.note_awesome.core.entities;
    opens com.note_awesome.core.entities to org.hibernate.orm.core, spring.core, spring.context, spring.beans;

    exports com.note_awesome.services;
    opens com.note_awesome.services to spring.core, spring.context, spring.beans;

    exports com.note_awesome.controllers;
    opens com.note_awesome.controllers to javafx.fxml, spring.core, spring.context, spring.beans;

    exports com.note_awesome.views.core_editors;
    opens com.note_awesome.views.core_editors to javafx.fxml;

    exports com.note_awesome.views.note_views;
    opens com.note_awesome.views.note_views to javafx.fxml;

    exports com.note_awesome.services.note_services;
    opens com.note_awesome.services.note_services to spring.core, spring.context, spring.beans;

    exports com.note_awesome.extensions;
    opens com.note_awesome.extensions to spring.core;

    exports com.note_awesome.services.user_services;
    opens com.note_awesome.services.user_services to spring.core, spring.context, spring.beans;

    exports com.note_awesome.services.user_services.validators;
    opens com.note_awesome.services.user_services.validators to spring.core, spring.context, spring.beans;

    opens com.note_awesome.services.note_services.validators to spring.core, spring.context, spring.beans;
    exports com.note_awesome.services.note_services.validators;

    exports com.note_awesome.services.user_profile_services;
    opens com.note_awesome.services.user_profile_services to spring.beans, spring.context, spring.core;

    exports com.note_awesome.services.user_profile_services.validators;
    opens com.note_awesome.services.user_profile_services.validators to spring.beans, spring.context, spring.core;

    exports com.note_awesome.models;
    opens com.note_awesome.models to spring.core, spring.context, spring.beans;

    exports com.note_awesome.interactors;
    opens com.note_awesome.interactors to spring.core, spring.context, spring.beans;
    exports com.note_awesome.services.authentication_services.validators;
    opens com.note_awesome.services.authentication_services.validators to spring.beans, spring.context, spring.core;
    exports com.note_awesome.services.authentication_services;
    opens com.note_awesome.services.authentication_services to spring.beans, spring.context, spring.core;

}