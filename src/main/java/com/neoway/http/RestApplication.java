package com.neoway.http;

import com.neoway.http.company.CompanyService;
import com.neoway.persistence.database.MorphiaService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class RestApplication extends Application {

    private final MorphiaService service = new MorphiaService();

    @Override
    public final Set<Object> getSingletons() {
        final Set<Object> set = new HashSet<>();

        set.add(new CompanyService(service));
        return set;
    }
}
