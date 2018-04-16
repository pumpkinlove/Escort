package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskUpBean
{
	private String taskno;
	private String taskseq;
	private String tasktype;
	private String deptno;
	private String deptno2;
	private String opuser;
	private String opusername;
	private String taskdate;

	@Generated(hash = 1283482434)
	public TaskUpBean(String taskno, String taskseq, String tasktype, String deptno,
			String deptno2, String opuser, String opusername, String taskdate) {
		this.taskno = taskno;
		this.taskseq = taskseq;
		this.tasktype = tasktype;
		this.deptno = deptno;
		this.deptno2 = deptno2;
		this.opuser = opuser;
		this.opusername = opusername;
		this.taskdate = taskdate;
	}
	@Generated(hash = 1687645229)
	public TaskUpBean() {
	}
	
	public String getTaskno()
	{
		return taskno;
	}
	public void setTaskno(String taskno)
	{
		this.taskno = taskno;
	}
	public String getTaskseq()
	{
		if(taskseq == null){
			return "";
		}
		return taskseq;
	}
	public void setTaskseq(String taskseq)
	{
		this.taskseq = taskseq;
	}
	public String getTasktype()
	{
		return tasktype;
	}
	public void setTasktype(String tasktype)
	{
		this.tasktype = tasktype;
	}
	public String getDeptno()
	{
		return deptno;
	}
	public void setDeptno(String deptno)
	{
		this.deptno = deptno;
	}
	public String getDeptno2()
	{
		return deptno2;
	}
	public void setDeptno2(String deptno2)
	{
		this.deptno2 = deptno2;
	}
	public String getOpuser()
	{
		return opuser;
	}
	public void setOpuser(String opuser)
	{
		this.opuser = opuser;
	}
	public String getOpusername()
	{
		return opusername;
	}
	public void setOpusername(String opusername)
	{
		this.opusername = opusername;
	}
	public String getTaskdate()
	{
		return taskdate;
	}
	public void setTaskdate(String taskdate)
	{
		this.taskdate = taskdate;
	}
}
