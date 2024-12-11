package com.audible.dto;

public class BookFileDTO {

	private String bookFileId;
	private String name;
	private String type;
	private String filePath;
	public String getBookFileId() {
		return bookFileId;
	}
	public void setBookFileId(String bookFileId) {
		this.bookFileId = bookFileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
