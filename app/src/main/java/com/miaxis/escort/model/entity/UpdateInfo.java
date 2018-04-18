package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class UpdateInfo {

	@Id
	private String id;
	private String version;
	private String path;
	private String filename;
	private String opdate;
	private String description;

	@Generated(hash = 1360587615)
	public UpdateInfo(String id, String version, String path, String filename,
			String opdate, String description) {
		this.id = id;
		this.version = version;
		this.path = path;
		this.filename = filename;
		this.opdate = opdate;
		this.description = description;
	}
	@Generated(hash = 826264761)
	public UpdateInfo() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getOpdate() {
		return opdate;
	}
	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
