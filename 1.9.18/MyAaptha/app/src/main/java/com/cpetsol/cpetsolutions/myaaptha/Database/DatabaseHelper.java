package com.cpetsol.cpetsolutions.myaaptha.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.cpetsol.cpetsolutions.myaaptha.dbmodel.DrHomeDTO;
import com.cpetsol.cpetsolutions.myaaptha.dbmodel.UserDataDTO;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 *
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /************************************************
     * Suggested Copy/Paste code. Everything from here to the done block.
     ************************************************/

    private static final String DATABASE_NAME = "myaaptha.db";
    private static final int DATABASE_VERSION = 1;
    static String strFilepath = Environment.getExternalStorageDirectory() + "/MyAaptha/"+ DATABASE_NAME;
    private Dao<UserDataDTO, Integer>  userDataDao;
    private Dao<DrHomeDTO, Integer>  drHomeDto;

    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
		super(context, strFilepath , null , DATABASE_VERSION);
//		Log.i("BD " , "Path -->" + strFilepath);

    }

    /************************************************
     * Suggested Copy/Paste Done
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, UserDataDTO.class);
            TableUtils.createTable(connectionSource, DrHomeDTO.class);
//			TableUtils.createTable(connectionSource, StudentDetails.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, UserDataDTO.class, true);
            TableUtils.dropTable(connectionSource, DrHomeDTO.class, true);
//			TableUtils.dropTable(connectionSource, StudentDetails.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs

    public Dao<UserDataDTO, Integer> getUserDataDao() throws SQLException {
        if (userDataDao == null) {
            userDataDao = getDao(UserDataDTO.class);
        }
        return userDataDao;
    }
    public Dao<DrHomeDTO, Integer> getDrHomeDto() throws SQLException {
        if (drHomeDto == null) {
            drHomeDto = getDao(DrHomeDTO.class);
        }
        return drHomeDto;
    }
}
