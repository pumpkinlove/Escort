package com.miaxis.escort.model.local.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.miaxis.escort.model.entity.TaskEscortBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TASK_ESCORT_BEAN".
*/
public class TaskEscortBeanDao extends AbstractDao<TaskEscortBean, Void> {

    public static final String TABLENAME = "TASK_ESCORT_BEAN";

    /**
     * Properties of entity TaskEscortBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Taskid = new Property(0, String.class, "taskid", false, "TASKID");
        public final static Property Escode = new Property(1, String.class, "escode", false, "ESCODE");
    }


    public TaskEscortBeanDao(DaoConfig config) {
        super(config);
    }
    
    public TaskEscortBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TASK_ESCORT_BEAN\" (" + //
                "\"TASKID\" TEXT," + // 0: taskid
                "\"ESCODE\" TEXT);"); // 1: escode
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TASK_ESCORT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TaskEscortBean entity) {
        stmt.clearBindings();
 
        String taskid = entity.getTaskid();
        if (taskid != null) {
            stmt.bindString(1, taskid);
        }
 
        String escode = entity.getEscode();
        if (escode != null) {
            stmt.bindString(2, escode);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TaskEscortBean entity) {
        stmt.clearBindings();
 
        String taskid = entity.getTaskid();
        if (taskid != null) {
            stmt.bindString(1, taskid);
        }
 
        String escode = entity.getEscode();
        if (escode != null) {
            stmt.bindString(2, escode);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public TaskEscortBean readEntity(Cursor cursor, int offset) {
        TaskEscortBean entity = new TaskEscortBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // taskid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // escode
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TaskEscortBean entity, int offset) {
        entity.setTaskid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEscode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(TaskEscortBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(TaskEscortBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(TaskEscortBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
