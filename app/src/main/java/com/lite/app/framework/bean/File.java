package com.lite.app.framework.bean;

import java.io.Serializable;

public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private long size;

    private String contentType;

    private byte[] bufferContent;

    private String binaryContent;

    private String base64Content;

    public File(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBufferContent() {
        return bufferContent;
    }

    public void setBufferContent(byte[] bufferContent) {
        this.bufferContent = bufferContent;
    }

    public String getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(String binaryContent) {
        this.binaryContent = binaryContent;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

}
