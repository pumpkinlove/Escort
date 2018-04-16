package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskEscortBean
{
	private String taskid;
	private String escode;

	@Generated(hash = 2077837719)
	public TaskEscortBean(String taskid, String escode) {
		this.taskid = taskid;
		this.escode = escode;
	}
	@Generated(hash = 1109836887)
	public TaskEscortBean() {
	}
	
	public String getTaskid()
	{
		return taskid;
	}
	public void setTaskid(String taskid)
	{
		this.taskid = taskid;
	}
	public String getEscode()
	{
		return escode;
	}
	public void setEscode(String escode)
	{
		this.escode = escode;
	}
}
