import enum

class ValidationError(enum.Enum):
    ERR_SENTENCE_TOO_SHORT = 'The text is too short, min word count should be 4'
    ERR_WORD_LENGTH_TOO_SHORT = 'The average word length for the sentence is too small'
