package exceptions

import enums.Scopes

class ScopeMissingException(scopes: List<Scopes>) : Exception("A required scope is missing! Current scopes: $scopes")