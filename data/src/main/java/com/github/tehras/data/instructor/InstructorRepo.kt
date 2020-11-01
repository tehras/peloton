package com.github.tehras.data.instructor

import com.github.tehras.data.data.InstructorResponse

interface InstructorRepo {
    suspend fun instructors(): InstructorResponse
}