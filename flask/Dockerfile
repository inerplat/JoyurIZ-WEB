FROM inerplat/joyuriz-flask

WORKDIR "/app"

COPY . .

#CMD ["python", "rest.py"]
CMD ["/bin/bash", "-c", "gunicorn -b 0.0.0.0:5000 -w 1 --threads 2 rest:app" ]
