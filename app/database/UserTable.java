package database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.db.Database;

@Singleton
public class UserTable {
    @Inject
    Database db;
}
