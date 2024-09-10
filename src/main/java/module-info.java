module com.note_awesome {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.compiler;
    requires transitive org.controlsfx.controls;
    requires javafx.graphics;
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
    
    opens com.note_awesome to javafx.fxml, spring.core;
    exports com.note_awesome;

    opens com.note_awesome.views to javafx.fxml;
    exports com.note_awesome.views;
    
    opens com.note_awesome.models.repositories.note to spring.core;
    exports com.note_awesome.models.repositories.note;

    opens com.note_awesome.models.entities.note to org.hibernate.orm.core, spring.core;
    exports com.note_awesome.models.entities.note;

}