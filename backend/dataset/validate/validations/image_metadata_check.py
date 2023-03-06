from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_ocr
from PIL import Image
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class ImageMetadataCheck(BaseValidator):
    """
    Verifies the format of the image file and 
    verifies the bounding box vertices to be within image dimensions
    """

    def execute(self, request):
        log.info('----Executing the Image metadata check----')
        try:
            if request["datasetType"] == dataset_type_ocr:
                image_file = request['record']['fileLocation']
                with Image.open(image_file) as img:
                    if 'format' in request['record'].keys():
                        if img.format.lower() != request['record']['format'].lower():
                            return {"message": "Image format does not match with the one specified", "code": "IMAGE_FORMAT_MISMATCH", "status": "FAILED"}
                    width, height = img.size
                    box_vertices = request['record']['boundingBox']['vertices']
                    for vertex in box_vertices:
                        if vertex['x'] > width or vertex['y'] > height:
                            return {"message": "Bounding Box vertices invalid: Values do not lie within the dimensions of image", "code": "INVALID_BOUNDING_BOX", "status": "FAILED"}

            log.info('----image metadata check  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while executing Image metadata check: {str(e)}")
            return {"message": "Exception while executing Image metadata check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}

# Log config
dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})