from abc import ABC, abstractmethod

class BaseHandler(ABC):
    """
    The Base handler class that declares the method to aggregate
    several validation classes to build a chain. The blueprint also
    declares a method for executing the request.
    """
    @abstractmethod
    def execute_next(self, validator):
        pass
    @abstractmethod
    def execute(self, request):
        pass


class BaseValidator(BaseHandler):
    """
    Base class for all validation classes to implement
    default chain of command behaviour
    """

    _next_validator = None
    # _status = ValidationError()

    def execute_next(self, validator):
        self._next_validator = validator

        return validator

    @abstractmethod
    def execute(self, request):
        if self._next_validator:
            return self._next_validator.execute(request)

        return {"message": "Validated", "status": "SUCCESS"}





















        