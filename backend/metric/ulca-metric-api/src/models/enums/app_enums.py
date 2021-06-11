import enum

class STAGE(enum.Enum):
    SUBMITTED               = 'SUBMITTED'
    VALIDATED               = 'VALIDATED'
    APPROVED                = 'APPROVED'

class CORPUS_TYPE(enum.Enum):
    PARALLEL_CORPUS         = 'parallel-corpus'
    MONOLINGUAL_CORPUS      = 'monolingual-corpus'
    ASR_CORPUS              = 'asr-corpus'
    DOCUMENT_OCR_CORPUS     = 'document-ocr-corpus'

