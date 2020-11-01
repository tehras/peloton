package com.github.tehras.data

import com.github.tehras.data.data.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PelotonApi {
    @GET("/api/user/{userName}")
    suspend fun user(@Path("userName") userName: String): User

    @GET("/api/user/{userId}/followers")
    suspend fun followers(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 50
    ): FollowersResponse

    @GET("/api/user/{userId}/following")
    suspend fun following(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 50
    ): FollowersResponse

    @GET("/api/user/{userId}/workouts")
    suspend fun workouts(
        @Path("userId") userId: String,
        @Query("joins") joins: String = "peloton.ride",
        @Query("limit") limit: Int = 100,
        @Query("page") page: Int = 0,
        @Query("sort_by") sortBy: String = "-created"
    ): WorkoutsResponse

    @GET("/api/workout/{workoutId}")
    suspend fun workout(
        @Path("workoutId") workoutId: String,
        @Query("joins") joins: String = "peloton"
    ): WorkoutDetailsResponse

    @GET("/api/workout/{workoutId}/performance_graph")
    suspend fun workoutPerformance(
        @Path("workoutId") workoutId: String
    ): WorkoutDetailsPerformanceResponse

    @GET("/api/ride/{rideId}/details")
    suspend fun rideDetails(
        @Path("rideId") rideId: String
    ): RideDetailsResponse

    @GET("/api/instructor")
    suspend fun instructors(
        @Query("limit") limit: Int = 100
    ): InstructorResponse
}