# USAGE
# Start the server:
#     python run_keras_server.py
# Submit a request via cURL:
#     curl -X POST -F image=@dog.jpg 'http://localhost:5000/predict'
# Submita a request via Python:
#    python simple_request.py

# import the necessary packages
from tensorflow.keras.preprocessing.image import img_to_array
from tensorflow.keras.applications import imagenet_utils
from tensorflow.keras.models import load_model
import cv2
import numpy
from PIL import Image
import numpy as np
import flask
import io
import sys
import magic
import face_recognition as FR
import magic

app = flask.Flask(__name__)
predictList = ['Chaewon', 'Yena', 'Yuri']
def prepare_image(image, target):
    npImage = numpy.array(image)
    print(np.shape(npImage))
    faces = FR.face_locations(npImage, number_of_times_to_upsample=0, model="hog")
    if len(faces) != 1 :
        print('fail to find : multi faces')
        return False
    T, R, B, L = faces[0]
    crop  = npImage[T:B, L:R]
    faceImage = cv2.resize(crop, target, interpolation = cv2.INTER_CUBIC)
    x = np.expand_dims(img_to_array(faceImage), axis=0)
    result = np.vstack([x])
    return (result, T, R, B, L)

@app.route("/predict", methods=["POST"])
def predict():

    data = {"success": False}
    print(flask.request)
    if flask.request.method == "POST":
        if flask.request.files.get("image"):
            image = flask.request.files["image"].read()
            extention = magic.from_buffer(image).split()[0].upper()
            if extention == 'GIF':
                imageObject = Image.open(io.BytesIO(image))
                imageObject.seek(0) 
                imageObject = imageObject.convert('RGB')
                image = np.array(imageObject)
            elif extention != 'JPEG' and extention != 'PNG':
                return flask.jsonify(data)
            else:
                image = Image.open(io.BytesIO(image)).convert('RGB')
            image, T, R, B, L = prepare_image(image, target=(256, 256))
            if image is False:
                return flask.jsonify(data)
            print(np.shape(image))
            model = load_model('./model3.h5')
            model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
            preds = model.predict_classes(image)
            print(preds)
            data["predictions"] = predictList[preds[0]]
            data['top'] = T
            data['right'] = R
            data['bottom'] = B
            data['left'] = L
            data["success"] = True
    return flask.jsonify(data)


if __name__ == "__main__":
    print(("* Loading Keras model and Flask starting server..."
        "please wait until server has fully started"))
    app.run()