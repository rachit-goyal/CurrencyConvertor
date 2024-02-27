package com.learn.currencyconvertor.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.learn.currencyconvertor.domain.repositroy.CurrencyRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
created by Rachit on 2/21/2024.
 */
@HiltWorker
class CurrencyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: CurrencyRepo,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val resp = repository.getCurrencyDataFromBackGround()
            if (resp.isNullOrEmpty().not()) {
                Result.success()

            } else {
                Result.retry()
            }

        } catch (e: Exception) {
            Result.failure()
        }
    }

}