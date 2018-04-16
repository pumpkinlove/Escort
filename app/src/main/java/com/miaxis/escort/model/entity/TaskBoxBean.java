package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskBoxBean
{
	private String taskid;
	private String boxcode;
	private String money;

	@Generated(hash = 42689691)
	public TaskBoxBean(String taskid, String boxcode, String money) {
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
}
