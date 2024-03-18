package hu.cordongroup.filefinderandreporter.dto;

public record AppArguments(String inputFile, boolean findOnlyOnCDrive, boolean withHiddenFolders,
                           boolean hideProgress) {
}
