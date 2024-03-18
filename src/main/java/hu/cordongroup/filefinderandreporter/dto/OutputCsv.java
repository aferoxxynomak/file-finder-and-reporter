package hu.cordongroup.filefinderandreporter.dto;

import java.util.ArrayList;
import java.util.List;

public class OutputCsv {
    private String filename;
    private boolean found;
    private List<String> pathList;

    public OutputCsv() {
    }

    public OutputCsv(String filename, boolean found, List<String> pathList) {
        this.filename = filename;
        this.found = found;
        this.pathList = pathList;
    }

    public OutputCsv(String filename) {
        this.filename = filename;
        this.found = false;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    public void addPathStringToPathList(String path) {
        if (this.pathList == null) {
            this.pathList = new ArrayList<>();
        }
        this.pathList.add(path);
    }

    public String getPathListByString() {
        if (this.pathList == null) {
            return "";
        }
        return String.join("\n", this.pathList);
    }

    @Override
    public String toString() {
        return "OutputCsv{" +
                "filename='" + filename + '\'' +
                ", found=" + found +
                ", pathList=" + pathList +
                '}';
    }
}
