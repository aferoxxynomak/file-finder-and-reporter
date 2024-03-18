package hu.cordongroup.filefinderandreporter.service;

import hu.cordongroup.filefinderandreporter.dto.AppArguments;
import org.springframework.boot.ApplicationArguments;

public interface AppArgumentsHandler {
    AppArguments handleApplicationArguments(ApplicationArguments args) throws Exception;
}
