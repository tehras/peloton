package com.github.tehras.data.di

import com.github.tehras.data.AuthApi
import com.github.tehras.data.PelotonApi
import com.github.tehras.data.auth.AuthRepo
import com.github.tehras.data.auth.RealAuthRepo
import com.github.tehras.data.client.RetrofitClient
import com.github.tehras.data.client.RetrofitClient.AuthQualifier
import com.github.tehras.data.client.RetrofitClient.UnauthQualifier
import com.github.tehras.data.followers.FollowersRepo
import com.github.tehras.data.followers.RealFollowersRepo
import com.github.tehras.data.instructor.InstructorRepo
import com.github.tehras.data.instructor.RealInstructorRepo
import com.github.tehras.data.overview.RealOverviewRepo
import com.github.tehras.data.overview.OverviewRepo
import com.github.tehras.data.workout.RealWorkoutRepo
import com.github.tehras.data.workout.WorkoutRepo
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>(AuthQualifier).create(PelotonApi::class.java) }
    single { get<Retrofit>(UnauthQualifier).create(AuthApi::class.java) }

    single<AuthRepo> { RealAuthRepo(get(), get()) }
    single<InstructorRepo> { RealInstructorRepo(get()) }
    factory<OverviewRepo> { RealOverviewRepo(get(), get()) }
    factory<FollowersRepo> { RealFollowersRepo(get()) }
    factory<WorkoutRepo> { RealWorkoutRepo(get()) }
}

@ExperimentalSerializationApi
val retrofitModule = module {
    single(qualifier = AuthQualifier) {
        RetrofitClient.createAuthClient(get())
    }
    single(qualifier = UnauthQualifier) {
        RetrofitClient.createUnauthClient()
    }
}