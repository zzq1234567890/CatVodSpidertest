package com.github.catvod.utils;

import java.util.List;

public class DownloadInfo {

    private List<long[]> parts;
    private String url;

    public List<long[]> getParts() {
        return parts;
    }

    public void setParts(List<long[]> parts) {
        this.parts = parts;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
