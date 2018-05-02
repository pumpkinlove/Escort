package com.miaxis.escort.model.entity;

/**
 * �豸ʵ��
 *
 */
public class Equipment {
	private Integer id;
	private String equipmentcode;
	private Integer bankid;
	private String bankno;
	private String mac;
	private String opuser;
	private String opdate;
	private String remark;
	private String opusername;
	private String bankname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEquipmentcode() {
		return equipmentcode;
	}

	public void setEquipmentcode(String equipmentcode) {
		this.equipmentcode = equipmentcode;
	}

	public Integer getBankid() {
		return bankid;
	}

	public void setBankid(Integer bankid) {
		this.bankid = bankid;
	}

	public String getBankno() {
		return bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getOpuser() {
		return opuser;
	}

	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOpusername() {
		return opusername;
	}

	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
}
