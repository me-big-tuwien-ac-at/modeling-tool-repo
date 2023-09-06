FROM python:3.7-slim

SHELL ["/bin/bash", "-c"] 

WORKDIR /

COPY . .

RUN apt update && apt install gfortran -y && apt install build-essential -y

RUN pip install --upgrade pip

RUN python -m venv CMAI_VENV
RUN source CMAI_VENV/bin/activate
RUN pip install -r requirements.txt
RUN python manage.py makemigrations
RUN python manage.py migrate
# RUN python manage.py createsuperuser

EXPOSE 80

CMD [ "python", "manage.py" , "runserver", "0.0.0.0:80"]
