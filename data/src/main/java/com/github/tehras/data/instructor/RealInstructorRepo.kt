package com.github.tehras.data.instructor

import com.github.tehras.data.PelotonApi
import com.github.tehras.data.data.InstructorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealInstructorRepo(
    private val pelotonApi: PelotonApi
) : InstructorRepo {
    private var _instructorResponse: InstructorResponse? = null

    override suspend fun instructors(): InstructorResponse = withContext(Dispatchers.IO) {
        if (_instructorResponse == null) {
            _instructorResponse = pelotonApi.instructors()
        }

        _instructorResponse!!
    }

}