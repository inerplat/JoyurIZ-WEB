FROM pytorch/pytorch:1.7.1-cuda11.0-cudnn8-runtime AS installer

WORKDIR "/app"

RUN apt-get update
RUN apt-get install build-essential cmake pkg-config -y
RUN apt-get install libx11-dev libatlas-base-dev -y
RUN apt-get install libgtk-3-dev libboost-python-dev -y

RUN pip install --no-cache-dir dlib
RUN pip install --no-cache-dir numpy
RUN pip install --no-cache-dir flask
RUN pip install --no-cache-dir flask-restx
RUN pip install --no-cache-dir pillow face_recognition

FROM installer AS run

WORKDIR "/app"

COPY ./joyuriz ./joyuriz
COPY ./app.py ./

CMD ["python", "app.py"]