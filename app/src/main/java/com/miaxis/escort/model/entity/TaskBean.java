package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskBean  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String taskcode;
	private String taskseq;
	private String deptno;
	private String carid;
	private String plateno;
	private String taskdate;
	private String begintime;
	private String endtime;
	private String status;
	private String statusName;
	private String exetime;
	private String createuser;
	private String createusername;
	private String opuser;
	private String opusername;
	private String opdate;
	private String tasktype;
	private String tasklevel;
	private String seculevel;
	private String createtime;
	private String carRfid;
	private byte[] carPhoto;

	@Generated(hash = 1011287573)
	public TaskBean(String id, String taskcode, String taskseq, String deptno,
			String carid, String plateno, String taskdate, String begintime,
			String endtime, String status, String statusName, String exetime,
			String createuser, String createusername, String opuser, String opusername,
			String opdate, String tasktype, String tasklevel, String seculevel,
			String createtime, String carRfid, byte[] carPhoto) {
		this.id = id;
		this.taskcode = taskcode;
		this.taskseq = taskseq;
		this.deptno = deptno;
		this.carid = carid;
		this.plateno = plateno;
		this.taskdate = taskdate;
		this.begintime = begintime;
		this.endtime = endtime;
		this.status = status;
		this.statusName = statusName;
		this.exetime = exetime;
		this.createuser = createuser;
		this.createusername = createusername;
		this.opuser = opuser;
		this.opusername = opusername;
		this.opdate = opdate;
		this.tasktype = tasktype;
		this.tasklevel = tasklevel;
		this.seculevel = seculevel;
		this.createtime = createtime;
		this.carRfid = carRfid;
		this.carPhoto = carPhoto;
	}

	@Generated(hash = 1443476586)
	public TaskBean() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskcode() {
		return taskcode;
	}

	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}

	public String getTaskseq() {
		return taskseq;
	}

	public void setTaskseq(String taskseq) {
		this.taskseq = taskseq;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getCarid() {
		return carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getTaskdate() {
		return taskdate;
	}

	public void setTaskdate(String taskdate) {
		this.taskdate = taskdate;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		statusName="";
		if ("1".equals(status)) {
			statusName="已上报";
		} else if ("2".equals(status)) {
			statusName="已审核";
		} else if ("3".equals(status)) {
			statusName="已交接";
		} else if ("4".equals(status)) {
			statusName="已完成";
		} else if ("5".equals(status)) {
			statusName="已注销";
		}
		return statusName;
	}

	public String getExetime() {
		return exetime;
	}

	public void setExetime(String exetime) {
		this.exetime = exetime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	public String getOpuser() {
		return opuser;
	}

	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}

	public String getOpusername() {
		return opusername;
	}

	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public String getTasktype() {
		return tasktype;
	}

	public String getTasktypeName() {
		if (tasktype.equals("1")) {
			if(!tasklevel.equals("1")){
				return "常规接箱";
			}else{
				return "出库";
			}
		} else if (tasktype.equals("2")) {
			if(!tasklevel.equals("1")){
				return "常规送箱";
			}else{
				return "入库";
			}
		} else if (tasktype.equals("3")) {
			return "临时接箱";
		} else if (tasktype.equals("4")) {
			return "临时送箱";
		} else {
			return tasktype;
		}
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getTasklevel() {
		return tasklevel;
	}

	public void setTasklevel(String tasklevel) {
		this.tasklevel = tasklevel;
	}

	public String getSeculevel() {
		return seculevel;
	}

	public void setSeculevel(String seculevel) {
		this.seculevel = seculevel;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCarRfid() {
		return carRfid;
	}

	public void setCarRfid(String carRfid) {
		this.carRfid = carRfid;
	}

	public byte[] getCarPhoto() {
		return carPhoto;
	}

	public void setCarPhoto(byte[] carPhoto) {
		this.carPhoto = carPhoto;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
