package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TaskBoxBean
{
	@Id(autoincrement = true)
	private Long id;
	private String taskid;
	private String boxcode;
	private String money;

	@Generated(hash = 1142817401)
	public TaskBoxBean(Long id, String taskid, String boxcode, String money) {
		this.id = id;
		this.taskid = taskid;
		this.boxcode = boxcode;
		this.money = money;
	}
	@Generated(hash = 1278325001)
	public TaskBoxBean() {
	}
	
	public String getTaskid()
	{
		return taskid;
	}
	public void setTaskid(String taskid)
	{
		this.taskid = taskid;
	}
	public String getBoxcode()
	{
		return boxcode;
	}
	public void setBoxcode(String boxcode)
	{
		this.boxcode = boxcode;
	}
	public String getMoney()
	{
		return money;
	}
	public void setMoney(String money)
	{
		this.money = money;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
