from flask import Flask
from flask_restx import Api
from joyuriz.router.api.v1.predict import predict_ns
from joyuriz.router.health import health_ns
from joyuriz.error.handler import FaceError
from werkzeug.exceptions import HTTPException

app = Flask(__name__)

api = Api(app, version='1.0', title='Joyuriz API', description='Joyuriz API', doc=False)

api.add_namespace(predict_ns)
api.add_namespace(health_ns)

@app.errorhandler(Exception)
def handle_error(message):

    response = {
        "predict": "fail",
        "top": 0,
        "right": 0,
        "bottom": 0,
        "left": 0
    }

    if isinstance(message, ValueError) or isinstance(message, TypeError):
        return 'Invalid parameter', 400

    elif isinstance(message, FaceError):
        return response, 204

    elif isinstance(message, HTTPException):
        return message

    else:
        return 'Internal server error', 500

