package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskExeBean {
	private String taskcode;
	private String tasktype;
	private String deptno;
	private String workercode;
	private String workername;
	private String escode1;
	private String esname1;
	private String escode2;
	private String esname2;
	private String carcode;
	private String plateno;
	private String tasktime;
	private String status;
	private String boxes;

	@Generated(hash = 829204160)
	public TaskExeBean(String taskcode, String tasktype, String deptno,
			String workercode, String workername, String escode1, String esname1,
			String escode2, String esname2, String carcode, String plateno,
			String tasktime, String status, String boxes) {
		this.taskcode = taskcode;
		this.tasktype = tasktype;
		this.deptno = deptno;
		this.workercode = workercode;
		this.workername = workername;
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

	public String getTaskcode() {
		return taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public String getTasktype()	{
		return tasktype;
	}
	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}
	public String getDeptno() {
		return deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	public String getWorkercode() {
		return workercode;
	}
	public void setWorkercode(String workercode) {
		this.workercode = workercode;
	}
	public String getWorkername() {
		return workername;
	}
	public void setWorkername(String workername) {
		this.workername = workername;
	}
	public String getEscode1() {
		return escode1;
	}
	public void setEscode1(String escode1) {
		this.escode1 = escode1;
	}
	public String getEsname1() {
		return esname1;
	}
	public void setEsname1(String esname1) {
		this.esname1 = esname1;
	}
	public String getEscode2() {
		return escode2;
	}
	public void setEscode2(String escode2) {
		this.escode2 = escode2;
	}
	public String getEsname2() {
		return esname2;
	}
	public void setEsname2(String esname2) {
		this.esname2 = esname2;
	}
	public String getCarcode() {
		return carcode;
	}
	public void setCarcode(String carcode) {
		this.carcode = carcode;
	}
	public String getPlateno() {
		return plateno;
	}
	public void setPlateno(String plateno) {
		this.plateno = plateno; 
	}
	public String getTasktime() {
		return tasktime;
	}
	public void setTasktime(String tasktime) {
		this.tasktime = tasktime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBoxes() {return boxes;}
	public void setBoxes(String boxes) {
		this.boxes = boxes;
	}
}
