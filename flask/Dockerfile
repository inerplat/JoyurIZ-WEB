FROM inerplat/joyuriz-flask

WORKDIR "/app"

COPY requirements.txt ./

#RUN pip install -r requirements.txt

COPY . .

CMD ["python", "rest.py"]
#CMD ["/bin/bash", "-c", "gunicorn -b 0.0.0.0:5000 -w 4 rest.py:app" ]