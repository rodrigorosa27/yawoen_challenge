package com.neoway.persistence.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

@Entity("companies")
public class Company {

    @Id
    @Property("id")
    private ObjectId id;

    @Indexed
    private String name;

    @Indexed
    private String zip;

    private String website;

    public final ObjectId getId() {
        return id;
    }

    public final void setId(final ObjectId theId) {
        this.id = theId;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getZip() {
        return zip;
    }

    public final void setZip(String zip) {
        this.zip = zip;
    }

    public final String getWebsite() {
        return website;
    }

    public final void setWebsite(String website) {
        this.website = website;
    }
}
