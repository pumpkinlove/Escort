package com.miaxis.escort.model.local.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.miaxis.escort.model.entity.TempDeptBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEMP_DEPT_BEAN".
*/
public class TempDeptBeanDao extends AbstractDao<TempDeptBean, Void> {

    public static final String TABLENAME = "TEMP_DEPT_BEAN";

    /**
     * Properties of entity TempDeptBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Selfdeptno = new Property(0, String.class, "selfdeptno", false, "SELFDEPTNO");
        public final static Property Deptno = new Property(1, String.class, "deptno", false, "DEPTNO");
    }


    public TempDeptBeanDao(DaoConfig config) {
        super(config);
    }
    
    public TempDeptBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEMP_DEPT_BEAN\" (" + //
                "\"SELFDEPTNO\" TEXT," + // 0: selfdeptno
                "\"DEPTNO\" TEXT);"); // 1: deptno
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEMP_DEPT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TempDeptBean entity) {
        stmt.clearBindings();
 
        String selfdeptno = entity.getSelfdeptno();
        if (selfdeptno != null) {
            stmt.bindString(1, selfdeptno);
        }
 
        String deptno = entity.getDeptno();
        if (deptno != null) {
            stmt.bindString(2, deptno);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TempDeptBean entity) {
        stmt.clearBindings();
 
        String selfdeptno = entity.getSelfdeptno();
        if (selfdeptno != null) {
            stmt.bindString(1, selfdeptno);
        }
 
        String deptno = entity.getDeptno();
        if (deptno != null) {
            stmt.bindString(2, deptno);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public TempDeptBean readEntity(Cursor cursor, int offset) {
        TempDeptBean entity = new TempDeptBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // selfdeptno
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // deptno
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TempDeptBean entity, int offset) {
        entity.setSelfdeptno(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDeptno(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(TempDeptBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(TempDeptBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(TempDeptBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
