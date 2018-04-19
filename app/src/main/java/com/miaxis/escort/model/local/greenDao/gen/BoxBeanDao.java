package com.miaxis.escort.model.local.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.miaxis.escort.model.entity.BoxBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOX_BEAN".
*/
public class BoxBeanDao extends AbstractDao<BoxBean, String> {

    public static final String TABLENAME = "BOX_BEAN";

    /**
     * Properties of entity BoxBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Boxcode = new Property(1, String.class, "boxcode", false, "BOXCODE");
        public final static Property Boxname = new Property(2, String.class, "boxname", false, "BOXNAME");
        public final static Property Deptno = new Property(3, String.class, "deptno", false, "DEPTNO");
        public final static Property Rfid = new Property(4, String.class, "rfid", false, "RFID");
        public final static Property Opuser = new Property(5, String.class, "opuser", false, "OPUSER");
        public final static Property Opusername = new Property(6, String.class, "opusername", false, "OPUSERNAME");
        public final static Property Opdate = new Property(7, String.class, "opdate", false, "OPDATE");
        public final static Property Money = new Property(8, String.class, "money", false, "MONEY");
        public final static Property Status = new Property(9, String.class, "status", false, "STATUS");
        public final static Property StatusName = new Property(10, String.class, "statusName", false, "STATUS_NAME");
        public final static Property CheckStatus = new Property(11, String.class, "checkStatus", false, "CHECK_STATUS");
        public final static Property CheckStatusName = new Property(12, String.class, "checkStatusName", false, "CHECK_STATUS_NAME");
    }


    public BoxBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BoxBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOX_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"BOXCODE\" TEXT," + // 1: boxcode
                "\"BOXNAME\" TEXT," + // 2: boxname
                "\"DEPTNO\" TEXT," + // 3: deptno
                "\"RFID\" TEXT," + // 4: rfid
                "\"OPUSER\" TEXT," + // 5: opuser
                "\"OPUSERNAME\" TEXT," + // 6: opusername
                "\"OPDATE\" TEXT," + // 7: opdate
                "\"MONEY\" TEXT," + // 8: money
                "\"STATUS\" TEXT," + // 9: status
                "\"STATUS_NAME\" TEXT," + // 10: statusName
                "\"CHECK_STATUS\" TEXT," + // 11: checkStatus
                "\"CHECK_STATUS_NAME\" TEXT);"); // 12: checkStatusName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOX_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BoxBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String boxcode = entity.getBoxcode();
        if (boxcode != null) {
            stmt.bindString(2, boxcode);
        }
 
        String boxname = entity.getBoxname();
        if (boxname != null) {
            stmt.bindString(3, boxname);
        }
 
        String deptno = entity.getDeptno();
        if (deptno != null) {
            stmt.bindString(4, deptno);
        }
 
        String rfid = entity.getRfid();
        if (rfid != null) {
            stmt.bindString(5, rfid);
        }
 
        String opuser = entity.getOpuser();
        if (opuser != null) {
            stmt.bindString(6, opuser);
        }
 
        String opusername = entity.getOpusername();
        if (opusername != null) {
            stmt.bindString(7, opusername);
        }
 
        String opdate = entity.getOpdate();
        if (opdate != null) {
            stmt.bindString(8, opdate);
        }
 
        String money = entity.getMoney();
        if (money != null) {
            stmt.bindString(9, money);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(10, status);
        }
 
        String statusName = entity.getStatusName();
        if (statusName != null) {
            stmt.bindString(11, statusName);
        }
 
        String checkStatus = entity.getCheckStatus();
        if (checkStatus != null) {
            stmt.bindString(12, checkStatus);
        }
 
        String checkStatusName = entity.getCheckStatusName();
        if (checkStatusName != null) {
            stmt.bindString(13, checkStatusName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BoxBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String boxcode = entity.getBoxcode();
        if (boxcode != null) {
            stmt.bindString(2, boxcode);
        }
 
        String boxname = entity.getBoxname();
        if (boxname != null) {
            stmt.bindString(3, boxname);
        }
 
        String deptno = entity.getDeptno();
        if (deptno != null) {
            stmt.bindString(4, deptno);
        }
 
        String rfid = entity.getRfid();
        if (rfid != null) {
            stmt.bindString(5, rfid);
        }
 
        String opuser = entity.getOpuser();
        if (opuser != null) {
            stmt.bindString(6, opuser);
        }
 
        String opusername = entity.getOpusername();
        if (opusername != null) {
            stmt.bindString(7, opusername);
        }
 
        String opdate = entity.getOpdate();
        if (opdate != null) {
            stmt.bindString(8, opdate);
        }
 
        String money = entity.getMoney();
        if (money != null) {
            stmt.bindString(9, money);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(10, status);
        }
 
        String statusName = entity.getStatusName();
        if (statusName != null) {
            stmt.bindString(11, statusName);
        }
 
        String checkStatus = entity.getCheckStatus();
        if (checkStatus != null) {
            stmt.bindString(12, checkStatus);
        }
 
        String checkStatusName = entity.getCheckStatusName();
        if (checkStatusName != null) {
            stmt.bindString(13, checkStatusName);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public BoxBean readEntity(Cursor cursor, int offset) {
        BoxBean entity = new BoxBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // boxcode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // boxname
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // deptno
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // rfid
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // opuser
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // opusername
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // opdate
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // money
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // status
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // statusName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // checkStatus
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // checkStatusName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BoxBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setBoxcode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBoxname(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDeptno(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRfid(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOpuser(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOpusername(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOpdate(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMoney(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setStatus(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStatusName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCheckStatus(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCheckStatusName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final String updateKeyAfterInsert(BoxBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(BoxBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BoxBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}