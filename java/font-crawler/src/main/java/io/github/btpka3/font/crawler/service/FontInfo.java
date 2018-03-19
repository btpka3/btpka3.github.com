package io.github.btpka3.font.crawler.service;


import lombok.Data;

import java.util.List;

@Data
public class FontInfo {

    private String name;
    private String by;
    private List<String> categories;
    private String downloadUrl;
}
