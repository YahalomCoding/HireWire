package com.hire_wire_application

import com.hire_wire_application.models.db_models.Service
import com.hire_wire_application.models.db_models.User

typealias ServicesCompletion = (List<Service>) -> Unit

typealias Completion = () -> Unit

typealias StringCompletion = (String) -> Unit

typealias UserCompletion = (User?) -> Unit
