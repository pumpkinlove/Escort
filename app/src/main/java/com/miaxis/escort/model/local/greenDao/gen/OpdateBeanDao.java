package com.miaxis.escort.model.local.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.miaxis.escort.model.entity.OpdateBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OPDATE_BEAN".
*/
public class OpdateBeanDao extends AbstractDao<OpdateBean, String> {

    public static final String TABLENAME = "OPDATE_BEAN";

    /**
     * Properties of entity OpdateBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Opdate = new Property(1, String.class, "opdate", false, "OPDATE");
    }


    public OpdateBeanDao(DaoConfig config) {
        super(config);
    }
    
    public OpdateBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OPDATE_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"OPDATE\" TEXT);"); // 1: opdate
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OPDATE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OpdateBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String opdate = entity.getOpdate();
        if (opdate != null) {
            stmt.bindString(2, opdate);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OpdateBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String opdate = entity.getOpdate();
        if (opdate != null) {
            stmt.bindString(2, opdate);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public OpdateBean readEntity(Cursor cursor, int offset) {
        OpdateBean entity = new OpdateBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // opdate
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OpdateBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setOpdate(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final String updateKeyAfterInsert(OpdateBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(OpdateBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(OpdateBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
