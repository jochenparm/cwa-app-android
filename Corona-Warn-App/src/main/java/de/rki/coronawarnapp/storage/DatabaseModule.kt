package de.rki.coronawarnapp.storage

import android.content.Context
import dagger.Module
import dagger.Provides
import de.rki.coronawarnapp.crash.CrashReportDao
import de.rki.coronawarnapp.storage.tracing.TracingIntervalDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    // TODO: Replace every manually instantiation of database and daos with injection otherwise @Singleton crashes the app

    // @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    // @Singleton
    @Provides
    fun provideExposureSummaryDao(db: AppDatabase): ExposureSummaryDao = db.exposureSummaryDao()

    // @Singleton
    @Provides
    fun provideTracingIntervalDao(db: AppDatabase): TracingIntervalDao = db.tracingIntervalDao()

    @Singleton
    @Provides
    fun provideCrashReportDao(db: AppDatabase): CrashReportDao = db.crashReportDao()
}
