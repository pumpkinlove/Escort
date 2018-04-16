package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskReqBean
{
	private String taskcode;
	private String taskseq;
	private String deptno;
	private String taskdate;
	private String tasktype;
	private String opuser;
	private String opusername;
	private String opdate;

	@Generated(hash = 1394581240)
	public TaskReqBean(String taskcode, String taskseq, String deptno,
			String taskdate, String tasktype, String opuser, String opusername,
			String opdate) {
		this.taskcode = taskcode;
		this.taskseq = taskseq;
		this.deptno = deptno;
		this.taskdate = taskdate;
		this.tasktype = tasktype;
		this.opuser = opuser;
		this.opusername = opusername;
		this.opdate = opdate;
	}
	@Generated(hash = 1459528416)
	public TaskReqBean() {
	}
	
	public String getTaskcode()
	{
		return taskcode;
	}
	public void setTaskcode(String taskcode)
	{
		this.taskcode = taskcode;
	}
	public String getTaskseq()
	{
		return taskseq;
	}
	public void setTaskseq(String taskseq)
	{
		this.taskseq = taskseq;
	}
	public String getDeptno()
	{
		return deptno;
	}
	public void setDeptno(String deptno)
	{
		this.deptno = deptno;
	}
	public String getTaskdate()
	{
		return taskdate;
	}
	public void setTaskdate(String taskdate)
	{
		this.taskdate = taskdate;
	}
	public String getTasktype()
	{
		return tasktype;
	}
	public void setTasktype(String tasktype)
	{
		this.tasktype = tasktype;
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
	public String getOpdate()
	{
		return opdate;
	}
	public void setOpdate(String opdate)
	{
		this.opdate = opdate;
	}
}
