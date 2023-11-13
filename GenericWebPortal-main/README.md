<h1 align="center">GenericWebPortal</h1>
A generic web portal to access records from systematic mapping studies

## How to's

### Running the server through the command line

```commandline
python manage.py runserver
```

When executed, the server is available at http://127.0.0.1:8000/. To access the modeling tool website, enter 
http://127.0.0.1:8000/modelingtools .


### How to prefill the database

The command below pre-populates the database the data contained within the file, provided in the placeholder 
``<fixturename>``. Execute the command from the content-root.
```commandline
python manage.py loaddata <fixturename>
```

In the case of the Modeling Tool Survey, please execute the following command:
```commandline
python manage.py loaddata all_data.json
```
