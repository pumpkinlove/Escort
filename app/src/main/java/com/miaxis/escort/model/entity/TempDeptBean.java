package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TempDeptBean
{
	private String selfdeptno;
	private String deptno;

	@Generated(hash = 388885541)
	public TempDeptBean(String selfdeptno, String deptno) {
		this.selfdeptno = selfdeptno;
		this.deptno = deptno;
	}
	@Generated(hash = 825412434)
	public TempDeptBean() {
	}
	
	public String getSelfdeptno() {
		return selfdeptno;
	}
	public void setSelfdeptno(String selfdeptno) {
		this.selfdeptno = selfdeptno;
	}
	public String getDeptno()
	{
		return deptno;
	}
	public void setDeptno(String deptno)
	{
		this.deptno = deptno;
	}
}
