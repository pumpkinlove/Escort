package com.miaxis.escort.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

@Entity
public class WorkerBean  implements Serializable
{
    private static final long serialVersionUID = -7417097781809707180L;

    @Unique
	private String id;
    @Id
	private String workno; /* 员工号 */
	private String idcard; /* 身份证号 */
	private String deptno; /* 机构号 */
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
	private String status; /* 上传状态  0 已上传， 1 未上传， 2 未上传*/
	@Generated(hash = 691834747)
	public WorkerBean(String id, String workno, String idcard, String deptno,
			String name, String phone, String finger0, String finger1, String finger2,
			String finger3, String finger4, String finger5, String finger6,
			String finger7, String finger8, String finger9, String opuser,
			String opusername, String opdate, String status) {
		this.id = id;
		this.workno = workno;
		this.idcard = idcard;
		this.deptno = deptno;
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
	}
	@Generated(hash = 1128361111)
	public WorkerBean() {
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkno() {
		return this.workno;
	}
	public void setWorkno(String workno) {
		this.workno = workno;
	}
	public String getIdcard() {
		return this.idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getDeptno() {
		return this.deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
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
	public String getFinger(int i) {
		if (i == 0) {
		    return getFinger0();
        } else if (i == 1) {
		    return getFinger1();
        } else if (i == 2) {
            return getFinger2();
        } else if (i == 3) {
            return getFinger3();
        } else if (i == 4) {
            return getFinger4();
        } else if (i == 5) {
            return getFinger5();
        } else if (i == 6) {
            return getFinger6();
        } else if (i == 7) {
            return getFinger7();
        } else if (i == 8) {
            return getFinger8();
        } else if (i == 9) {
            return getFinger9();
        }
        return null;
	}
}
