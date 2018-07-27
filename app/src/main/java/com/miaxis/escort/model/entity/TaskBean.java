package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.DaoException;
import com.miaxis.escort.model.local.greenDao.gen.DaoSession;
import com.miaxis.escort.model.local.greenDao.gen.TaskEscortBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.TaskBoxBeanDao;
import com.miaxis.escort.model.local.greenDao.gen.TaskBeanDao;

@Entity
public class TaskBean  implements Serializable {

	private static final long serialVersionUID = -5493109912641557743L;
	@Id
	private String id;
	private String taskcode;
	private String taskseq;
	private String deptno;
	private String carid;
	private String plateno;
	private String taskdate;
	private String begintime;
	private String endtime;
	private String status;
	private String statusName;
	private String exetime;
	private String createuser;
	private String createusername;
	private String opuser;
	private String opusername;
	private String opdate;
	private String tasktype;
	private String tasklevel;
	private String seculevel;
	private String createtime;
	private String carRfid;
	private String carPhoto;
    @ToMany(joinProperties = {@JoinProperty(name = "id", referencedName = "taskid")})
    private List<TaskBoxBean> boxList;
    @ToMany(joinProperties = {@JoinProperty(name = "id", referencedName = "taskid")})
    private List<TaskEscortBean> escortList;
				/** Used to resolve relations */
				@Generated(hash = 2040040024)
				private transient DaoSession daoSession;
				/** Used for active entity operations. */
				@Generated(hash = 281453257)
				private transient TaskBeanDao myDao;



				@Generated(hash = 297599945)
				public TaskBean(String id, String taskcode, String taskseq, String deptno, String carid,
						String plateno, String taskdate, String begintime, String endtime, String status,
						String statusName, String exetime, String createuser, String createusername, String opuser,
						String opusername, String opdate, String tasktype, String tasklevel, String seculevel,
						String createtime, String carRfid, String carPhoto) {
					this.id = id;
					this.taskcode = taskcode;
					this.taskseq = taskseq;
					this.deptno = deptno;
					this.carid = carid;
					this.plateno = plateno;
					this.taskdate = taskdate;
					this.begintime = begintime;
					this.endtime = endtime;
					this.status = status;
					this.statusName = statusName;
					this.exetime = exetime;
					this.createuser = createuser;
					this.createusername = createusername;
					this.opuser = opuser;
					this.opusername = opusername;
					this.opdate = opdate;
					this.tasktype = tasktype;
					this.tasklevel = tasklevel;
					this.seculevel = seculevel;
					this.createtime = createtime;
					this.carRfid = carRfid;
					this.carPhoto = carPhoto;
				}
				@Generated(hash = 1443476586)
				public TaskBean() {
				}


				
				public String getId() {
					return this.id;
				}
				public void setId(String id) {
					this.id = id;
				}
				public String getTaskcode() {
					return this.taskcode;
				}
				public void setTaskcode(String taskcode) {
					this.taskcode = taskcode;
				}
				public String getTaskseq() {
					return this.taskseq;
				}
				public void setTaskseq(String taskseq) {
					this.taskseq = taskseq;
				}
				public String getDeptno() {
					return this.deptno;
				}
				public void setDeptno(String deptno) {
					this.deptno = deptno;
				}
				public String getCarid() {
					return this.carid;
				}
				public void setCarid(String carid) {
					this.carid = carid;
				}
				public String getPlateno() {
					return this.plateno;
				}
				public void setPlateno(String plateno) {
					this.plateno = plateno;
				}
				public String getTaskdate() {
					return this.taskdate;
				}
				public void setTaskdate(String taskdate) {
					this.taskdate = taskdate;
				}
				public String getBegintime() {
					return this.begintime;
				}
				public void setBegintime(String begintime) {
					this.begintime = begintime;
				}
				public String getEndtime() {
					return this.endtime;
				}
				public void setEndtime(String endtime) {
					this.endtime = endtime;
				}
				public String getStatus() {
					return this.status;
				}
				public void setStatus(String status) {
					this.status = status;
				}
				public String getStatusName() {
					statusName="";
					if ("1".equals(status)) {
						statusName="已上报";
					} else if ("2".equals(status)) {
						statusName="已审核";
					} else if ("3".equals(status)) {
						statusName="已交接";
					} else if ("4".equals(status)) {
						statusName="已完成";
					} else if ("5".equals(status)) {
						statusName="已注销";
					}
					return this.statusName;
				}
				public void setStatusName(String statusName) {
					this.statusName = statusName;
				}
				public String getExetime() {
					return this.exetime;
				}
				public void setExetime(String exetime) {
					this.exetime = exetime;
				}
				public String getCreateuser() {
					return this.createuser;
				}
				public void setCreateuser(String createuser) {
					this.createuser = createuser;
				}
				public String getCreateusername() {
					return this.createusername;
				}
				public void setCreateusername(String createusername) {
					this.createusername = createusername;
				}
				public String getOpuser() {
					return this.opuser;
				}
				public void setOpuser(String opuser) {
					this.opuser = opuser;
				}
				public String getOpusername() {
					return this.opusername;
				}
				public void setOpusername(String opusername) {
					this.opusername = opusername;
				}
				public String getOpdate() {
					return this.opdate;
				}
				public void setOpdate(String opdate) {
					this.opdate = opdate;
				}
				public String getTasktype() {
					return this.tasktype;
				}
				public void setTasktype(String tasktype) {
					this.tasktype = tasktype;
				}

				public String getTasklevel() {
					return this.tasklevel;
				}
				public void setTasklevel(String tasklevel) {
					this.tasklevel = tasklevel;
				}
				public String getSeculevel() {
					return this.seculevel;
				}
				public void setSeculevel(String seculevel) {
					this.seculevel = seculevel;
				}
				public String getCreatetime() {
					return this.createtime;
				}
				public void setCreatetime(String createtime) {
					this.createtime = createtime;
				}
				public String getCarRfid() {
					return this.carRfid;
				}
				public void setCarRfid(String carRfid) {
					this.carRfid = carRfid;
				}
				public String getCarPhoto() {
					return this.carPhoto;
				}
				public void setCarPhoto(String carPhoto) {
					this.carPhoto = carPhoto;
				}
				/**
				 * To-many relationship, resolved on first access (and after reset).
				 * Changes to to-many relations are not persisted, make changes to the target entity.
				 */
				@Generated(hash = 2073891666)
				public List<TaskBoxBean> getBoxList() {
					if (boxList == null) {
						final DaoSession daoSession = this.daoSession;
						if (daoSession == null) {
							throw new DaoException("Entity is detached from DAO context");
						}
						TaskBoxBeanDao targetDao = daoSession.getTaskBoxBeanDao();
						List<TaskBoxBean> boxListNew = targetDao._queryTaskBean_BoxList(id);
						synchronized (this) {
							if (boxList == null) {
								boxList = boxListNew;
							}
						}
					}
					return boxList;
				}
				/** Resets a to-many relationship, making the next get call to query for a fresh result. */
				@Generated(hash = 1232352855)
				public synchronized void resetBoxList() {
					boxList = null;
				}
				/**
				 * To-many relationship, resolved on first access (and after reset).
				 * Changes to to-many relations are not persisted, make changes to the target entity.
				 */
				@Generated(hash = 474043253)
				public List<TaskEscortBean> getEscortList() {
					if (escortList == null) {
						final DaoSession daoSession = this.daoSession;
						if (daoSession == null) {
							throw new DaoException("Entity is detached from DAO context");
						}
						TaskEscortBeanDao targetDao = daoSession.getTaskEscortBeanDao();
						List<TaskEscortBean> escortListNew = targetDao._queryTaskBean_EscortList(id);
						synchronized (this) {
							if (escortList == null) {
								escortList = escortListNew;
							}
						}
					}
					return escortList;
				}
				/** Resets a to-many relationship, making the next get call to query for a fresh result. */
				@Generated(hash = 331647117)
				public synchronized void resetEscortList() {
					escortList = null;
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
				@Generated(hash = 1769608413)
				public void __setDaoSession(DaoSession daoSession) {
					this.daoSession = daoSession;
					myDao = daoSession != null ? daoSession.getTaskBeanDao() : null;
				}
				
}
