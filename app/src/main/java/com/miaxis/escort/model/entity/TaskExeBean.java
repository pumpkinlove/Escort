package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class TaskExeBean implements Serializable{

	private static final long serialVersionUID = 5344500230356169702L;
	@Id(autoincrement = true)
    private Long id;
	private String taskno;
	private String tasktype;
	private String deptno;
	private String workno;
	private String workname;
	private String escode1;
	private String esname1;
	private String escode2;
	private String esname2;
	private String carcode;
	private String plateno;
	private String tasktime;
	private String status;
	private String boxes;
	@Generated(hash = 539121695)
	public TaskExeBean(Long id, String taskno, String tasktype, String deptno,
			String workno, String workname, String escode1, String esname1,
			String escode2, String esname2, String carcode, String plateno,
			String tasktime, String status, String boxes) {
		this.id = id;
		this.taskno = taskno;
		this.tasktype = tasktype;
		this.deptno = deptno;
		this.workno = workno;
		this.workname = workname;
		this.escode1 = escode1;
		this.esname1 = esname1;
		this.escode2 = escode2;
		this.esname2 = esname2;
		this.carcode = carcode;
		this.plateno = plateno;
		this.tasktime = tasktime;
		this.status = status;
		this.boxes = boxes;
	}
	@Generated(hash = 1880158953)
	public TaskExeBean() {
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaskno() {
		return this.taskno;
	}
	public void setTaskno(String taskno) {
		this.taskno = taskno;
	}
	public String getTasktype() {
		return this.tasktype;
	}
	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}
	public String getDeptno() {
		return this.deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
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
	public String getEscode1() {
		return this.escode1;
	}
	public void setEscode1(String escode1) {
		this.escode1 = escode1;
	}
	public String getEsname1() {
		return this.esname1;
	}
	public void setEsname1(String esname1) {
		this.esname1 = esname1;
	}
	public String getEscode2() {
		return this.escode2;
	}
	public void setEscode2(String escode2) {
		this.escode2 = escode2;
	}
	public String getEsname2() {
		return this.esname2;
	}
	public void setEsname2(String esname2) {
		this.esname2 = esname2;
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
	public String getTasktime() {
		return this.tasktime;
	}
	public void setTasktime(String tasktime) {
		this.tasktime = tasktime;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBoxes() {
		return this.boxes;
	}
	public void setBoxes(String boxes) {
		this.boxes = boxes;
	}

}
