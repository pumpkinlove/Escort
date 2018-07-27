package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskLogBean {
	@Id
	private String id;
	private String taskcode;
	private String logtype;
	private String orgid;
	private String orgno;
	private String escode;
	private String esname;
	private String workno;
	private String workname;
	private String carcode;
	private String plateno;
	private String tasktype;
	private String logdetail;
	private String taskstatus;
	private String exectime;
	private String shiftId;
	private String opdate;
	@Generated(hash = 1465338829)
	public TaskLogBean(String id, String taskcode, String logtype, String orgid,
			String orgno, String escode, String esname, String workno, String workname,
			String carcode, String plateno, String tasktype, String logdetail,
			String taskstatus, String exectime, String shiftId, String opdate) {
		this.id = id;
		this.taskcode = taskcode;
		this.logtype = logtype;
		this.orgid = orgid;
		this.orgno = orgno;
		this.escode = escode;
		this.esname = esname;
		this.workno = workno;
		this.workname = workname;
		this.carcode = carcode;
		this.plateno = plateno;
		this.tasktype = tasktype;
		this.logdetail = logdetail;
		this.taskstatus = taskstatus;
		this.exectime = exectime;
		this.shiftId = shiftId;
		this.opdate = opdate;
	}
	@Generated(hash = 1095263089)
	public TaskLogBean() {
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskcode() {
		return this.taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public String getLogtype() {
		return this.logtype;
	}
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	public String getOrgid() {
		return this.orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgno() {
		return this.orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getEscode() {
		return this.escode;
	}
	public void setEscode(String escode) {
		this.escode = escode;
	}
	public String getEsname() {
		return this.esname;
	}
	public void setEsname(String esname) {
		this.esname = esname;
	}
	public String getWorkno() {
		return this.workno;
	}
	public void setWorkno(String workno) {
		this.workno = workno;
	}
	public String getWorkname() {
		return this.workname;
	}
	public void setWorkname(String workname) {
		this.workname = workname;
	}
	public String getCarcode() {
		return this.carcode;
	}
	public void setCarcode(String carcode) {
		this.carcode = carcode;
	}
	public String getPlateno() {
		return this.plateno;
	}
	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}
	public String getTasktype() {
		return this.tasktype;
	}
	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}
	public String getLogdetail() {
		return this.logdetail;
	}
	public void setLogdetail(String logdetail) {
		this.logdetail = logdetail;
	}
	public String getTaskstatus() {
		return this.taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	public String getExectime() {
		return this.exectime;
	}
	public void setExectime(String exectime) {
		this.exectime = exectime;
	}
	public String getShiftId() {
		return this.shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	public String getOpdate() {
		return this.opdate;
	}
	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

}
