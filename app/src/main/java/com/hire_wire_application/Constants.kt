package com.hire_wire_application

import com.hire_wire_application.models.db_models.HireRequest
import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User

typealias ServicesCompletion = (List<Service>) -> Unit

typealias RequestsCompletion = (List<HireRequest>) -> Unit

typealias Completion = () -> Unit

typealias StringCompletion = (String) -> Unit

typealias UserCompletion = (User?) -> Unit

typealias UsersCompletion = (List<User>) -> Unit

val GLOBAL_SERVICES_LAST_UPDATED_KEY = "GLOBAL_SERVICES_LAST_UPDATED_KEY"
val GLOBAL_REQUESTS_LAST_UPDATED_KEY = "GLOBAL_REQUESTS_LAST_UPDATED_KEY"
val GLOBAL_USERS_LAST_UPDATED_KEY = "GLOBAL_USERS_LAST_UPDATED_KEY"
