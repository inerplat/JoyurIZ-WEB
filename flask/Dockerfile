FROM inerplat/joyuriz-flask-base

WORKDIR "/app"

COPY ./joyuriz ./joyuriz
#COPY ./models ./models
COPY ./app.py ./

CMD ["/bin/bash", "-c", "gunicorn -b 0.0.0.0:5000 -w 2 app:app --log-level debug" ]
#CMD ["python", "app.py"]
