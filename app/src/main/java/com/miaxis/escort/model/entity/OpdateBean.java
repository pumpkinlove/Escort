package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OpdateBean
{
	@Id
	private String id;
	private String opdate; /* 操作时间 */

	@Generated(hash = 189686609)
	public OpdateBean(String id, String opdate) {
		this.id = id;
		this.opdate = opdate;
	}
	@Generated(hash = 306256296)
	public OpdateBean() {
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
