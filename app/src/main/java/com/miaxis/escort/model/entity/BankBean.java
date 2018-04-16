package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BankBean {
	@Id
	private String id;
	private String bankcode;          	//银行
	private String bankno;  		  	//银行内部编号
	private String bankname;		 	//银行名称
	private String banktype;         	//银行类型
	private String banklevel;			//银行级别
	private String phoneno;				//联系方式
	private String bankaddress;			//网点地址
	private String parentcode;			//父节点
	private String opuser;				//操作人
	private String opdate;				//操作时间
	private String remark;				//备注
	private String subcount;				//子节点数量
	private String opusername;     		//操作人名称
	private String banktypename;   		//银行类型名称
	private String syscode;				//系统编号
	@Generated(hash = 876362959)
	public BankBean(String id, String bankcode, String bankno, String bankname,
			String banktype, String banklevel, String phoneno, String bankaddress,
			String parentcode, String opuser, String opdate, String remark,
			String subcount, String opusername, String banktypename, String syscode) {
		this.id = id;
		this.bankcode = bankcode;
		this.bankno = bankno;
		this.bankname = bankname;
		this.banktype = banktype;
		this.banklevel = banklevel;
		this.phoneno = phoneno;
		this.bankaddress = bankaddress;
		this.parentcode = parentcode;
		this.opuser = opuser;
		this.opdate = opdate;
		this.remark = remark;
		this.subcount = subcount;
		this.opusername = opusername;
		this.banktypename = banktypename;
		this.syscode = syscode;
	}
	@Generated(hash = 1512845509)
	public BankBean() {
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBankno() {
		return bankno;
	}
	public void setBankno(String bankno) {
		this.bankno = bankno;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBanktype() {
		return banktype;
	}
	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}
	public String getBanklevel() {
		return banklevel;
	}
	public void setBanklevel(String banklevel) {
		this.banklevel = banklevel;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getBankaddress() {
		return bankaddress;
	}
	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}
	public String getParentcode() {
		return parentcode;
	}
	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
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
	public String getSubcount() {
		return subcount;
	}
	public void setSubcount(String subcount) {
		this.subcount = subcount;
	}
	public String getOpusername() {
		return opusername;
	}
	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}
	public String getBanktypename() {
		return banktypename;
	}
	public void setBanktypename(String banktypename) {
		this.banktypename = banktypename;
	}
	public String getSyscode() {
		return syscode;
	}
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


}
