FROM python:3.9

WORKDIR "/app"

RUN apt-get update
RUN apt-get install build-essential cmake pkg-config -y
RUN apt-get install libx11-dev libatlas-base-dev -y
RUN apt-get install libgtk-3-dev libboost-python-dev -y

RUN pip install --no-cache-dir torch==1.9.0 torchvision==0.10.0
RUN pip install --no-cache-dir dlib
RUN pip install --no-cache-dir numpy
RUN pip install --no-cache-dir flask
RUN pip install --no-cache-dir flask-restx
RUN pip install --no-cache-dir pillow face_recognition
RUN pip install --no-cache-dir gunicorn
