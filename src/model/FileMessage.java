
package model;

import java.io.Serializable;
import java.util.Date;

public class FileMessage implements Serializable {
    
    private String filename;
    
    private String fileType;
    
    private String originPath;
    
    private Date createdDate;
    
    private int fileSize;
    
    private byte[] content;
    
    private String owner;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "FileMessage{" + "filename=" + filename + ", fileType=" + fileType + ", originPath=" + originPath + ", createdDate=" + createdDate + ", fileSize=" + fileSize + ", content=" + content + ", owner=" + owner + '}';
    }
    
}
