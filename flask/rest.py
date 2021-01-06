from tensorflow.keras.preprocessing.image import img_to_array
from tensorflow.keras.applications import imagenet_utils
from tensorflow.keras.models import load_model
from tensorflow.keras.backend import clear_session
import numpy
from PIL import Image
import numpy as np
import flask
import io
import sys
import magic
import face_recognition as FR
import magic
import gc
from flask import request
# import tracemalloc
# tracemalloc.start(10)
app = flask.Flask(__name__)
model = load_model('model5.h5')
predictList = ['Chaewon', 'Yena', 'Yuri']

def prepare_image(image, target):
    npImage = numpy.array(image)
    faces = FR.face_locations(npImage, number_of_times_to_upsample=0, model="hog")
    if len(faces) != 1 :
        print('fail to find')
        return (False, 0, 0, 0, 0)
    T, R, B, L = faces[0]
    crop  = npImage[T:B, L:R]
    faceImage = Image.fromarray(crop).resize((256,256), Image.BICUBIC)
    x = np.expand_dims(img_to_array(faceImage), axis=0)
    result = np.vstack([x])
    return (result, T, R, B, L)


@app.route("/predict", methods=["GET"])
def localfile_predict():
    image = None
    filename = request.args.get('filename')
    try:
        with open(filename, "r") as f:
            image = f.read()
    except:
        image = None
    return predict(image)



@app.route("/predict", methods=["POST"])
def formdata_predict():
    image = None
    if flask.request.method == "POST" and flask.request.files.get("image"):
        image = flask.request.files["image"].read()
    return predict(image)


def predict(postImage):
    # time1 = tracemalloc.take_snapshot()
    data = {
        "predict": "fail",
        "top": 0,
        "right": 0,
        "bottom": 0,
        "left": 0
    }
    if postImage is None:
        return data
    extention = magic.from_buffer(postImage).split()[0].upper()
    if extention == 'GIF':
        imageObject = Image.open(io.BytesIO(postImage))
        imageObject.seek(0) 
        imageObject = imageObject.convert('RGB')
        image = np.array(imageObject)
        imageObject.close()
    elif extention != 'JPEG' and extention != 'PNG':
        return flask.jsonify(data)
    else:
        imageObject = Image.open(io.BytesIO(postImage))
        image = imageObject.convert('RGB')
        imageObject.close()
    image, T, R, B, L = prepare_image(image, target=(256, 256))
    if image is False:
        print(data)
        return flask.jsonify(data)

    preds = numpy.argmax(model(image).numpy())
    clear_session()
    image = None
    postImage = None
    data["predict"] = predictList[preds]   
    data['top'] = T
    data['right'] = R
    data['bottom'] = B
    data['left'] = L
    data["success"] = True
    app.logger.info(data)
    # time2 = tracemalloc.take_snapshot()
    # stats = time2.compare_to(time1, 'traceback')
    # top = stats[0]
    # app.logger.info('\n'.join(top.traceback.format()))
    gc.collect()
    return flask.jsonify(data)


if __name__ == "__main__":
    print(("* Loading Keras model and Flask starting server..."
        "please wait until server has fully started"))
    app.run(host='0.0.0.0')