package me.test.first.spring.boot.swagger

import org.springframework.core.io.ByteArrayResource

/**
 * @see org.grails.core.io.MockFileResource
 */
class MockFileResource extends ByteArrayResource {

    private String fileName;

    public MockFileResource(String fileName, String contents) throws UnsupportedEncodingException {
        super(contents.getBytes("UTF-8"));
        this.fileName = fileName;
    }

    public MockFileResource(String fileName, String contents, String encoding) throws UnsupportedEncodingException {
        super(contents.getBytes(encoding));
        this.fileName = fileName;
    }

    @Override
    public String getFilename() {
        return fileName;
    }
}
