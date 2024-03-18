package hu.cordongroup.filefinderandreporter.service;

import hu.cordongroup.filefinderandreporter.dto.AppArguments;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

@Service
public class AppArgumentsHandlerImpl implements AppArgumentsHandler {
    @Override
    public AppArguments handleApplicationArguments(ApplicationArguments args) throws Exception {
        String inputFile = args.getNonOptionArgs().get(0);
        boolean findOnlyOnCDrive = args.getOptionNames().contains("c");
        boolean withHiddenFolders = args.getOptionNames().contains("r");
        boolean hideProgress = args.getOptionNames().contains("p");
        return new AppArguments(inputFile, findOnlyOnCDrive, withHiddenFolders, hideProgress);
    }
}
