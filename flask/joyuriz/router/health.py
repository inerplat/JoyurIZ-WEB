from flask import request
from flask_restx import Namespace, Resource
from joyuriz.service.image import ImageManager
from joyuriz.service.model import ModelManager
from joyuriz.service.cnn import CNN

health_ns = Namespace('Health', path='/model', description='hcheck')

swagger_parser = health_ns.parser()


@health_ns.route('/hcheck')
class Predict(Resource):
    """Health Check"""

    @health_ns.doc(responses={
        200: 'OK',
    })
    @health_ns.expect(swagger_parser)
    def get(self):
        return 'Health Check OK!'
