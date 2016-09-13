package com.pizza5stars;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.UnsupportedEncodingException;

public class Pizza5StarsConfiguration extends Configuration {
    @JsonProperty
    private final DataSourceFactory database = new DataSourceFactory();
    @NotEmpty
    private final String jwtTokenSecret = "dfwzsdzwh823zebdwdz772632gdsbd";

    public byte[] getJwtTokenSecret() throws UnsupportedEncodingException {
        return jwtTokenSecret.getBytes("UTF-8");
    }

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}