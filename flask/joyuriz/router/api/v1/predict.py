from flask import request
from flask_restx import Namespace, Resource
from joyuriz.service.image import ImageManager
from joyuriz.service.model import ModelManager
from joyuriz.service.cnn import CNN
import base64
predict_ns = Namespace('Predict', path='/api/v1', description='predict')

swagger_parser = predict_ns.parser()

model = ModelManager(CNN, 'models/predict.pt')


@predict_ns.route('/predict')
class Predict(Resource):
    """ POST로 받은 이미지를 분류하는 API """

    @predict_ns.doc(responses={
        200: 'OK',
        204: 'Fail to find a face',
        400: 'Bad Request',
        500: 'Internal Server Error'
    })
    @predict_ns.expect(swagger_parser)
    def post(self):
        if "image" not in request.files:
            raise ValueError
        
        input_image = request.files["image"].read()
        if("Content-Transfer-Encoding" in request.headers and request.headers["Content-Transfer-Encoding"] == "base64"):
            input_image = base64.decodebytes(input_image)
        manager = ImageManager(input_image)

        tensor_image = manager.find_face().crop_image().to_tensor().get_image()

        predicted = model.predict(tensor_image)

        face_box = manager.get_face()
        response = {
            "predict": predicted,
            "top": face_box[0],
            "right": face_box[1],
            "bottom": face_box[2],
            "left": face_box[3]
        }
        print(predicted)
        return response, 200
