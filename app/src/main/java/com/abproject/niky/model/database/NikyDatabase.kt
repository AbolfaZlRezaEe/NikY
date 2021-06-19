package com.abproject.niky.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abproject.niky.model.model.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class NikyDatabase : RoomDatabase() {

}