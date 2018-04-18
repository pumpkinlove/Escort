package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TaskEscortBean
{
	@Id(autoincrement = true)
	private Long id;
	private String taskid;
	private String escode;

	@Generated(hash = 2040104073)
	public TaskEscortBean(Long id, String taskid, String escode) {
		this.id = id;
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
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
