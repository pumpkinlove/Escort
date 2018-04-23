package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BoxBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4502533188196635811L;
	private String id;
	@Id
	private String boxcode;
	private String boxname;
	private String deptno;
	private String rfid;
	private String opuser; /* 操作员 */
	private String opusername;
	private String opdate; /* 操作时间 */
	private String money;
	/**
	 * 箱包状态：1-库房 2-网点 3-出库途中 4-入库途中 5-调拨途中
	 */
	private String status;
	private String statusName;
	/**
	 * 是否验证：0-未验证 1-已验证
	 */
	private String checkStatus;
	private String checkStatusName;
	@Generated(hash = 1780509862)
	public BoxBean(String id, String boxcode, String boxname, String deptno,
			String rfid, String opuser, String opusername, String opdate, String money,
			String status, String statusName, String checkStatus,
			String checkStatusName) {
		this.id = id;
		this.boxcode = boxcode;
		this.boxname = boxname;
		this.deptno = deptno;
		this.rfid = rfid;
		this.opuser = opuser;
		this.opusername = opusername;
		this.opdate = opdate;
		this.money = money;
		this.status = status;
		this.statusName = statusName;
		this.checkStatus = checkStatus;
		this.checkStatusName = checkStatusName;
	}

	@Generated(hash = 1812715084)
	public BoxBean() {
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBoxcode() {
		return boxcode;
	}

	public void setBoxcode(String boxcode) {
		this.boxcode = boxcode;
	}

	public String getBoxname() {
		return boxname;
	}

	public void setBoxname(String boxname) {
		this.boxname = boxname;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getOpuser() {
		return opuser;
	}

	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}

	public String getOpusername() {
		return opusername;
	}

	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public String getCheckStatusName() {
		checkStatusName = "";
		if ("".equals(checkStatus)) {
			checkStatusName="未验证";
		}
		else if(null==checkStatus) {
			checkStatusName="未验证";
		}
		else if("0".equals(checkStatus)) {
			checkStatusName="未验证";
		}
		else if ("1".equals(checkStatus)) {
			checkStatusName="已验证";
		}
		return checkStatusName;
	}
	
	public void setCheckStatusName(String statusName) {
		this.checkStatusName = statusName;
	}

	public void setCheckStatus(String status) {
		this.checkStatus = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		statusName = "";
		if("1" == status || "1".equals(status)){
			statusName = "库房";
		} else if ("2" == status || "2".equals(status)) {
			statusName = "网点";
		} else if ("3" == status || "3".equals(status)) {
			statusName = "出库途中";
		} else if ("4" == status || "4".equals(status)) {
			statusName = "入库途中";
		} else if ("5" == status || "5".equals(status)) {
			statusName = "调拨途中";
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
