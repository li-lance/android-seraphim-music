package com.seraphim.music.shared.repository

import com.seraphim.music.api.UsersApi
import com.seraphim.music.invoker.infrastructure.HttpResponse
import com.seraphim.music.model.PrivateUserObject
import com.seraphim.music.shared.database.dao.UserDao
import com.seraphim.music.shared.database.entity.UserEntity
import com.seraphim.music.shared.model.Resource
import com.seraphim.music.shared.network.NetworkBoundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepository(private val api: UsersApi, val userDao: UserDao) {
    suspend fun user(): Flow<Resource<UserEntity>> =
        object : NetworkBoundRepository<UserEntity, PrivateUserObject>() {
            override suspend fun saveRemoteData(response: PrivateUserObject) {
                withContext(Dispatchers.IO) {
                    userDao.deleteAllUsers()
                    userDao.insertUser(UserEntity.from(response))
                }
            }

            override fun fetchFromLocal(): Flow<UserEntity> =
                userDao.getFirstUser().map { it ?: UserEntity(id = "-1") }.flowOn(Dispatchers.Main)

            override suspend fun fetchFromRemote(): HttpResponse<PrivateUserObject> {
                return withContext(Dispatchers.Main) {
                    api.getCurrentUsersProfile()
                }
            }

        }.asFlow()
}