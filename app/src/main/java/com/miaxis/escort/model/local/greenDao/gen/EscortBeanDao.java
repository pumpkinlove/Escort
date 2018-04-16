package com.miaxis.escort.model.local.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.miaxis.escort.model.entity.EscortBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ESCORT_BEAN".
*/
public class EscortBeanDao extends AbstractDao<EscortBean, String> {

    public static final String TABLENAME = "ESCORT_BEAN";

    /**
     * Properties of entity EscortBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Escortno = new Property(1, String.class, "escortno", false, "ESCORTNO");
        public final static Property Idcard = new Property(2, String.class, "idcard", false, "IDCARD");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Phone = new Property(4, String.class, "phone", false, "PHONE");
        public final static Property Finger0 = new Property(5, String.class, "finger0", false, "FINGER0");
        public final static Property Finger1 = new Property(6, String.class, "finger1", false, "FINGER1");
        public final static Property Finger2 = new Property(7, String.class, "finger2", false, "FINGER2");
        public final static Property Finger3 = new Property(8, String.class, "finger3", false, "FINGER3");
        public final static Property Finger4 = new Property(9, String.class, "finger4", false, "FINGER4");
        public final static Property Finger5 = new Property(10, String.class, "finger5", false, "FINGER5");
        public final static Property Finger6 = new Property(11, String.class, "finger6", false, "FINGER6");
        public final static Property Finger7 = new Property(12, String.class, "finger7", false, "FINGER7");
        public final static Property Finger8 = new Property(13, String.class, "finger8", false, "FINGER8");
        public final static Property Finger9 = new Property(14, String.class, "finger9", false, "FINGER9");
        public final static Property Opuser = new Property(15, String.class, "opuser", false, "OPUSER");
        public final static Property Opusername = new Property(16, String.class, "opusername", false, "OPUSERNAME");
        public final static Property Opdate = new Property(17, String.class, "opdate", false, "OPDATE");
        public final static Property Status = new Property(18, String.class, "status", false, "STATUS");
        public final static Property Password = new Property(19, String.class, "password", false, "PASSWORD");
        public final static Property Photo = new Property(20, byte[].class, "photo", false, "PHOTO");
    }


    public EscortBeanDao(DaoConfig config) {
        super(config);
    }
    
    public EscortBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ESCORT_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"ESCORTNO\" TEXT," + // 1: escortno
                "\"IDCARD\" TEXT," + // 2: idcard
                "\"NAME\" TEXT," + // 3: name
                "\"PHONE\" TEXT," + // 4: phone
                "\"FINGER0\" TEXT," + // 5: finger0
                "\"FINGER1\" TEXT," + // 6: finger1
                "\"FINGER2\" TEXT," + // 7: finger2
                "\"FINGER3\" TEXT," + // 8: finger3
                "\"FINGER4\" TEXT," + // 9: finger4
                "\"FINGER5\" TEXT," + // 10: finger5
                "\"FINGER6\" TEXT," + // 11: finger6
                "\"FINGER7\" TEXT," + // 12: finger7
                "\"FINGER8\" TEXT," + // 13: finger8
                "\"FINGER9\" TEXT," + // 14: finger9
                "\"OPUSER\" TEXT," + // 15: opuser
                "\"OPUSERNAME\" TEXT," + // 16: opusername
                "\"OPDATE\" TEXT," + // 17: opdate
                "\"STATUS\" TEXT," + // 18: status
                "\"PASSWORD\" TEXT," + // 19: password
                "\"PHOTO\" BLOB);"); // 20: photo
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ESCORT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, EscortBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String escortno = entity.getEscortno();
        if (escortno != null) {
            stmt.bindString(2, escortno);
        }
 
        String idcard = entity.getIdcard();
        if (idcard != null) {
            stmt.bindString(3, idcard);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String finger0 = entity.getFinger0();
        if (finger0 != null) {
            stmt.bindString(6, finger0);
        }
 
        String finger1 = entity.getFinger1();
        if (finger1 != null) {
            stmt.bindString(7, finger1);
        }
 
        String finger2 = entity.getFinger2();
        if (finger2 != null) {
            stmt.bindString(8, finger2);
        }
 
        String finger3 = entity.getFinger3();
        if (finger3 != null) {
            stmt.bindString(9, finger3);
        }
 
        String finger4 = entity.getFinger4();
        if (finger4 != null) {
            stmt.bindString(10, finger4);
        }
 
        String finger5 = entity.getFinger5();
        if (finger5 != null) {
            stmt.bindString(11, finger5);
        }
 
        String finger6 = entity.getFinger6();
        if (finger6 != null) {
            stmt.bindString(12, finger6);
        }
 
        String finger7 = entity.getFinger7();
        if (finger7 != null) {
            stmt.bindString(13, finger7);
        }
 
        String finger8 = entity.getFinger8();
        if (finger8 != null) {
            stmt.bindString(14, finger8);
        }
 
        String finger9 = entity.getFinger9();
        if (finger9 != null) {
            stmt.bindString(15, finger9);
        }
 
        String opuser = entity.getOpuser();
        if (opuser != null) {
            stmt.bindString(16, opuser);
        }
 
        String opusername = entity.getOpusername();
        if (opusername != null) {
            stmt.bindString(17, opusername);
        }
 
        String opdate = entity.getOpdate();
        if (opdate != null) {
            stmt.bindString(18, opdate);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(19, status);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(20, password);
        }
 
        byte[] photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindBlob(21, photo);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, EscortBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String escortno = entity.getEscortno();
        if (escortno != null) {
            stmt.bindString(2, escortno);
        }
 
        String idcard = entity.getIdcard();
        if (idcard != null) {
            stmt.bindString(3, idcard);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String finger0 = entity.getFinger0();
        if (finger0 != null) {
            stmt.bindString(6, finger0);
        }
 
        String finger1 = entity.getFinger1();
        if (finger1 != null) {
            stmt.bindString(7, finger1);
        }
 
        String finger2 = entity.getFinger2();
        if (finger2 != null) {
            stmt.bindString(8, finger2);
        }
 
        String finger3 = entity.getFinger3();
        if (finger3 != null) {
            stmt.bindString(9, finger3);
        }
 
        String finger4 = entity.getFinger4();
        if (finger4 != null) {
            stmt.bindString(10, finger4);
        }
 
        String finger5 = entity.getFinger5();
        if (finger5 != null) {
            stmt.bindString(11, finger5);
        }
 
        String finger6 = entity.getFinger6();
        if (finger6 != null) {
            stmt.bindString(12, finger6);
        }
 
        String finger7 = entity.getFinger7();
        if (finger7 != null) {
            stmt.bindString(13, finger7);
        }
 
        String finger8 = entity.getFinger8();
        if (finger8 != null) {
            stmt.bindString(14, finger8);
        }
 
        String finger9 = entity.getFinger9();
        if (finger9 != null) {
            stmt.bindString(15, finger9);
        }
 
        String opuser = entity.getOpuser();
        if (opuser != null) {
            stmt.bindString(16, opuser);
        }
 
        String opusername = entity.getOpusername();
        if (opusername != null) {
            stmt.bindString(17, opusername);
        }
 
        String opdate = entity.getOpdate();
        if (opdate != null) {
            stmt.bindString(18, opdate);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(19, status);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(20, password);
        }
 
        byte[] photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindBlob(21, photo);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public EscortBean readEntity(Cursor cursor, int offset) {
        EscortBean entity = new EscortBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // escortno
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // idcard
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // finger0
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // finger1
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // finger2
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // finger3
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // finger4
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // finger5
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // finger6
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // finger7
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // finger8
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // finger9
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // opuser
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // opusername
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // opdate
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // status
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // password
            cursor.isNull(offset + 20) ? null : cursor.getBlob(offset + 20) // photo
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, EscortBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEscortno(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIdcard(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFinger0(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFinger1(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFinger2(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setFinger3(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFinger4(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFinger5(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setFinger6(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFinger7(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFinger8(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setFinger9(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setOpuser(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setOpusername(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setOpdate(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setStatus(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setPassword(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setPhoto(cursor.isNull(offset + 20) ? null : cursor.getBlob(offset + 20));
     }
    
    @Override
    protected final String updateKeyAfterInsert(EscortBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(EscortBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(EscortBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
