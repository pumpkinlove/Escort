package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.DaoException;
import com.miaxis.escort.model.local.greenDao.gen.DaoSession;
import com.miaxis.escort.model.local.greenDao.gen.BoxBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.TaskBoxBeanDao;

@Entity
public class TaskBoxBean implements Serializable
{
	private static final long serialVersionUID = -5988623503206634976L;
	@Id(autoincrement = true)
	private Long id;
	private String taskid;
	private String boxcode;
	private String money;
	@ToOne(joinProperty = "boxcode")
	private BoxBean box;
	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;
	/** Used for active entity operations. */
	@Generated(hash = 1956996183)
	private transient TaskBoxBeanDao myDao;
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
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaskid() {
		return this.taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getBoxcode() {
		return this.boxcode;
	}
	public void setBoxcode(String boxcode) {
		this.boxcode = boxcode;
	}
	public String getMoney() {
		return this.money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Generated(hash = 2017668486)
	private transient String box__resolvedKey;
	/** To-one relationship, resolved on first access. */
	@Generated(hash = 1794338654)
	public BoxBean getBox() {
		String __key = this.boxcode;
		if (box__resolvedKey == null || box__resolvedKey != __key) {
			final DaoSession daoSession = this.daoSession;
			if (daoSession == null) {
				throw new DaoException("Entity is detached from DAO context");
			}
			BoxBeanDao targetDao = daoSession.getBoxBeanDao();
			BoxBean boxNew = targetDao.load(__key);
			synchronized (this) {
				box = boxNew;
				box__resolvedKey = __key;
			}
		}
		return box;
	}
	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 1032734798)
	public void setBox(BoxBean box) {
		synchronized (this) {
			this.box = box;
			boxcode = box == null ? null : box.getBoxcode();
			box__resolvedKey = boxcode;
		}
	}
	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 128553479)
	public void delete() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.delete(this);
	}
	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 1942392019)
	public void refresh() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.refresh(this);
	}
	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 713229351)
	public void update() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.update(this);
	}
	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 388663746)
	public void __setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
		myDao = daoSession != null ? daoSession.getTaskBoxBeanDao() : null;
	}

}
