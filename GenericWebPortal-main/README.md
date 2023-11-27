<h1 align="center">GenericWebPortal</h1>
A generic web portal to access records from systematic mapping studies

## How to execute

```commandline
python manage.py runmigrations
python manage.py migrate
python manage.py loaddata all_data_generated.json
python manage.py runserver
```