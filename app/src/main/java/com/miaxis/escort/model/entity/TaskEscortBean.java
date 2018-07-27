package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.DaoException;
import com.miaxis.escort.model.local.greenDao.gen.DaoSession;
import com.miaxis.escort.model.local.greenDao.gen.TaskEscortBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.EscortBeanDao;

@Entity
public class TaskEscortBean implements Serializable
{
	private static final long serialVersionUID = 7716768613612031876L;
	@Id(autoincrement = true)
	private Long id;
	private String taskid;
	private String escode;
	@ToOne(joinProperty = "escode")
	private EscortBean escortBean;
	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;
	/** Used for active entity operations. */
	@Generated(hash = 1560195627)
	private transient TaskEscortBeanDao myDao;
	@Generated(hash = 2040104073)
	public TaskEscortBean(Long id, String taskid, String escode) {
		this.id = id;
		this.taskid = taskid;
		this.escode = escode;
	}
	@Generated(hash = 1109836887)
	public TaskEscortBean() {
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
	public String getEscode() {
		return this.escode;
	}
	public void setEscode(String escode) {
		this.escode = escode;
	}
	@Generated(hash = 1903151015)
	private transient String escortBean__resolvedKey;
	/** To-one relationship, resolved on first access. */
	@Generated(hash = 1842868241)
	public EscortBean getEscortBean() {
		String __key = this.escode;
		if (escortBean__resolvedKey == null || escortBean__resolvedKey != __key) {
			final DaoSession daoSession = this.daoSession;
			if (daoSession == null) {
				throw new DaoException("Entity is detached from DAO context");
			}
			EscortBeanDao targetDao = daoSession.getEscortBeanDao();
			EscortBean escortBeanNew = targetDao.load(__key);
			synchronized (this) {
				escortBean = escortBeanNew;
				escortBean__resolvedKey = __key;
			}
		}
		return escortBean;
	}
	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 580248282)
	public void setEscortBean(EscortBean escortBean) {
		synchronized (this) {
			this.escortBean = escortBean;
			escode = escortBean == null ? null : escortBean.getEscortno();
			escortBean__resolvedKey = escode;
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
	@Generated(hash = 1691434158)
	public void __setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
		myDao = daoSession != null ? daoSession.getTaskEscortBeanDao() : null;
	}

}
