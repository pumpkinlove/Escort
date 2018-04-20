package com.miaxis.escort.model.entity;

import com.miaxis.escort.util.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EscortBean implements Serializable
{

	private static final long serialVersionUID = -7089709531880954805L;
	@Id
	private String id;
	private String escortno; /* 员工号 */
	private String idcard; /* 身份证号 */
	private String name;   /* 姓名 */
	private String phone;  /* 电话 */
    private String finger0;            //左手拇指
    private String finger1;            //左手食指
    private String finger2;            //左手中指
    private String finger3;            //左手无名指
    private String finger4;            //左手小指
    private String finger5;            //右手拇指
    private String finger6;            //右手食指
    private String finger7;            //右手中指
    private String finger8;            //右手无名指
    private String finger9;            //右手小指

    private String opuser; /*操作员*/
	private String opusername;
	private String opdate; /* 操作时间 */
	private String status ="未验证"; // 状态(0-未验证, 1-已验证)
	/**
	 * 押运员密码
	 */
	private String password;
	private byte[] photo;
	@Generated(hash = 598023511)
	public EscortBean(String id, String escortno, String idcard, String name,
			String phone, String finger0, String finger1, String finger2, String finger3,
			String finger4, String finger5, String finger6, String finger7,
			String finger8, String finger9, String opuser, String opusername,
			String opdate, String status, String password, byte[] photo) {
		this.id = id;
		this.escortno = escortno;
		this.idcard = idcard;
		this.name = name;
		this.phone = phone;
		this.finger0 = finger0;
		this.finger1 = finger1;
		this.finger2 = finger2;
		this.finger3 = finger3;
		this.finger4 = finger4;
		this.finger5 = finger5;
		this.finger6 = finger6;
		this.finger7 = finger7;
		this.finger8 = finger8;
		this.finger9 = finger9;
		this.opuser = opuser;
		this.opusername = opusername;
		this.opdate = opdate;
		this.status = status;
		this.password = password;
		this.photo = photo;
	}
	@Generated(hash = 2047371612)
	public EscortBean() {
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEscortno() {
		return this.escortno;
	}
	public void setEscortno(String escortno) {
		this.escortno = escortno;
	}
	public String getIdcard() {
		return this.idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFinger0() {
		return this.finger0;
	}
	public void setFinger0(String finger0) {
		this.finger0 = finger0;
	}
	public String getFinger1() {
		return this.finger1;
	}
	public void setFinger1(String finger1) {
		this.finger1 = finger1;
	}
	public String getFinger2() {
		return this.finger2;
	}
	public void setFinger2(String finger2) {
		this.finger2 = finger2;
	}
	public String getFinger3() {
		return this.finger3;
	}
	public void setFinger3(String finger3) {
		this.finger3 = finger3;
	}
	public String getFinger4() {
		return this.finger4;
	}
	public void setFinger4(String finger4) {
		this.finger4 = finger4;
	}
	public String getFinger5() {
		return this.finger5;
	}
	public void setFinger5(String finger5) {
		this.finger5 = finger5;
	}
	public String getFinger6() {
		return this.finger6;
	}
	public void setFinger6(String finger6) {
		this.finger6 = finger6;
	}
	public String getFinger7() {
		return this.finger7;
	}
	public void setFinger7(String finger7) {
		this.finger7 = finger7;
	}
	public String getFinger8() {
		return this.finger8;
	}
	public void setFinger8(String finger8) {
		this.finger8 = finger8;
	}
	public String getFinger9() {
		return this.finger9;
	}
	public void setFinger9(String finger9) {
		this.finger9 = finger9;
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
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte[] getPhoto() {
		return this.photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

}
