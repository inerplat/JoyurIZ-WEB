FROM kumatea/pytorch:1.9.0-py38-full AS installer

RUN apt update
RUN apt install build-essential cmake -y
RUN apt install libgtk-3-dev -y
RUN apt install libboost-all-dev -y

RUN pip install --no-cache-dir dlib
RUN pip install --no-cache-dir flask
RUN pip install --no-cache-dir flask-restx
RUN pip install --no-cache-dir pillow face_recognition
RUN pip install --no-cache-dir gunicorn

FROM installer

WORKDIR "/app"

COPY ./joyuriz ./joyuriz
COPY ./app.py ./
COPY ./models ./models

CMD ["python", "app.py"]
#CMD ["/bin/bash", "-c", "gunicorn -b 0.0.0.0:5000 -w 4 app" ]
