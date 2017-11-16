package io.github.btpka3.nexus.clean.snapshot.client.api;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class RepoContent {

    List<Item> data;

    public static class Item {
        private String resourceURI;
        private String relativePath;
        private String text;
        private boolean leaf;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS z")
        private Date lastModified;
        private long sizeOnDisk;

        public String getResourceURI() {
            return resourceURI;
        }

        public void setResourceURI(String resourceURI) {
            this.resourceURI = resourceURI;
        }

        public String getRelativePath() {
            return relativePath;
        }

        public void setRelativePath(String relativePath) {
            this.relativePath = relativePath;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public Date getLastModified() {
            return lastModified;
        }

        public void setLastModified(Date lastModified) {
            this.lastModified = lastModified;
        }

        public long getSizeOnDisk() {
            return sizeOnDisk;
        }

        public void setSizeOnDisk(long sizeOnDisk) {
            this.sizeOnDisk = sizeOnDisk;
        }
    }

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }
}
