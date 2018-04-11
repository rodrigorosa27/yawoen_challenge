package com.neoway.persistence.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("companies")
public class Company {

    @Id
    @Property("id")
    private ObjectId id;

    private String name;

    @Indexed(options = @IndexOptions(unique = true))
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
