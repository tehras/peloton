package com.github.tehras.peloton.followers.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.data.Follower
import com.github.tehras.data.data.FollowersResponse
import com.github.tehras.data.followers.FollowersRepo
import com.github.tehras.peloton.followers.list.FollowersState.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FollowersListViewModel(
    private val followersRepo: FollowersRepo
) : ViewModel() {
    private val followers = MutableSharedFlow<FollowersResponse>()

    val followersState
        get() = followers.transform<FollowersResponse, FollowersState> { followersResponse ->
            emit(Success(followersResponse.followers))
        }

    fun fetchFollowers(userId: String) {
        viewModelScope.launch {
            followers.emit(followersRepo.fetchFollowers(userId = userId))
        }
    }

    fun fetchFollowing(userId: String) {
        viewModelScope.launch {
            followers.emit(followersRepo.fetchFollowing(userId = userId))
        }
    }
}

sealed class FollowersState {
    object Loading : FollowersState()
    data class Success(
        val followers: List<Follower>
    ) : FollowersState()
}